package com.hfad.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hfad.tasks.databinding.FragmentTaskBinding

/**
 * A simple [Fragment] subclass.
 */
class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_task, container, false)
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        //get the database's object
        val dao = TaskDatabase.getInstance(application).taskDao
        val viewModelFactory = TaskViewModelFactory(dao)
        //get a viewModel's object
        val viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]

        binding.viewModel = viewModel

        //provides to update the binding.viewModel in layout
        binding.lifecycleOwner = viewLifecycleOwner


        val adapter = TaskItemAdapter { taskId ->
            //Toast.makeText(context, "Clicked task $taskId", Toast.LENGTH_SHORT).show()
            viewModel.onTaskClicked(taskId)
        }
        //connect the RecyclerView with TaskItemAdapter
        binding.taskList.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {

                //add date to the ListAdapter's background list
                adapter.submitList(it)
            }
        })

        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer { taskId ->
            taskId?.let {
                val action = TaskFragmentDirections.actionTaskFragmentToEditTaskFragment(taskId)
                this.findNavController().navigate(action)
                viewModel.onTaskNavigated()
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
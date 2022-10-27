package dev.mfazio.abl.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.mfazio.abl.R

class TeamsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_teams_grid, container, false)

        // Set the adapter
        //smart cast
        if (view is RecyclerView) {
            with(view) {

                //spanCount 2 – The number of columns or rows in the grid
                //GridLayoutManager.VERTICAL orientation – Layout orientation. Should be HORIZONTAL or VERTICAL.
                //reverseLayout – When set to true, layouts from end to start.
                layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

                //set the adapter
                adapter = TeamsGridAdapter(UITeam.allTeams)
            }
        }

        return view
    }
}
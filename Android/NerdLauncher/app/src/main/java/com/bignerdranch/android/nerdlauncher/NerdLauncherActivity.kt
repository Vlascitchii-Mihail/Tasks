package com.bignerdranch.android.nerdlauncher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.content.pm.ResolveInfo
import android.util.Log
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView

private const val TAG = "NerdLauncherActivity"

class NerdLauncherActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nerd_launcher)

        recyclerView = findViewById(R.id.app_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        setupAdapter()
    }

    private fun setupAdapter() {
        val startupIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val activities = packageManager.queryIntentActivities(startupIntent, 0)
        activities.sortWith(Comparator {a, b ->
            String.CASE_INSENSITIVE_ORDER.compare(a.loadLabel(packageManager).toString(),
                b.loadLabel(packageManager).toString())
        })

        Log.i(TAG, "Found ${activities.size}")

        recyclerView.adapter = ActivityAdapter(activities)
    }

    private inner class ActivityHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
//        private val nameTextView = itemView as TextView
        private val nameTextView = itemView.findViewById(R.id.label_text_id) as TextView
        private val appIcon = itemView.findViewById(R.id.label_list_id) as ImageView

        private lateinit var resolveInfo: ResolveInfo

        init {
            nameTextView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val activityInfo = resolveInfo.activityInfo
            val intent = Intent(Intent.ACTION_MAIN).apply {
                setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            val context = view.context
            context.startActivity(intent)
        }

        fun bindActivity(resolveInfo: ResolveInfo) {
            this.resolveInfo = resolveInfo
            val packageManager = itemView.context.packageManager
            val appName = resolveInfo.loadLabel(packageManager).toString()
            nameTextView.text = appName
            appIcon.setImageDrawable(resolveInfo.loadIcon(packageManager))
        }
    }

    private inner class ActivityAdapter(val activities: List<ResolveInfo>): RecyclerView.Adapter<ActivityHolder>() {

        override fun onCreateViewHolder(container: ViewGroup, viewType: Int): ActivityHolder {
//            val layoutInflater = LayoutInflater.from(container.context)
            val view = layoutInflater.inflate(R.layout.activity_icon_list, container, false)
            return ActivityHolder(view)
        }

        override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
            val resolveInfo = activities[position]
            holder.bindActivity(resolveInfo)
        }

        override fun getItemCount(): Int {
            return activities.size
        }
    }
}
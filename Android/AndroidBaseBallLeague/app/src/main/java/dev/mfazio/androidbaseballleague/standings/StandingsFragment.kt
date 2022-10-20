package dev.mfazio.androidbaseballleague.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import dev.mfazio.androidbaseballleague.R

/**
 *Statistics list view
 */
class StandingsFragment: Fragment() {

///ppd

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_standings, container, false)

        if (view is RecyclerView) {
            //ppp
        }

        //ppppppp

        return view
    }
}
package dev.mfazio.androidbaseballleague.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.mfazio.androidbaseballleague.R

class TeamsFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        val view = inflater.inflate(R.layout.fragment_teams_grid, container, false)
        return view
    }
}
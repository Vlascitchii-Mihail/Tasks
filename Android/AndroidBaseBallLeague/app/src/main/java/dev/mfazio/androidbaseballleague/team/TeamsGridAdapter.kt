package dev.mfazio.androidbaseballleague.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.mfazio.androidbaseballleague.R
import dev.mfazio.androidbaseballleague.databinding.TeamsGridItemBinding

/**
 * displays the UITeam objects in the RecyclerView
 */
class TeamsGridAdapter(private val teams: List<UITeam>): RecyclerView.Adapter<TeamsGridAdapter.TeamViewHolder>() {

    //represents a new ViewHolder's object
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
        TeamViewHolder(

            //Utility class to create ViewDataBinding from layouts
            DataBindingUtil.inflate(

                //LayoutInflater - Создает экземпляр XML-файла макета в соответствующих объектах View.
                //from(parent.context) - Obtains the LayoutInflater from the given context.
            LayoutInflater.from(parent.context), R.layout.teams_grid_item, parent, false))

    //bind the UITeam
    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    override fun getItemCount(): Int = teams.size

    /**
     * binds the UITeam objects to the binding object in the teams_grid_item.xml
     * @param binding: TeamsGridItemBinding - exemplar of the teams_grid_item.xml binding
     */
    inner class TeamViewHolder(private val binding: TeamsGridItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UITeam) {
            binding.apply {

                //team - binding object in the teams_grid_item.xml
                team = item

                //executePendingBindings() -  используется, чтобы биндинг не откладывался,
                // а выполнился как можно быстрее. Это критично в случае с RecyclerView.
                executePendingBindings()
            }
        }
    }
}
package be.buithg.etghaifgte.presentation.ui.adapters

import android.graphics.Color
import android.content.res.ColorStateList
import be.buithg.etghaifgte.databinding.MatchItemBinding
import be.buithg.etghaifgte.domain.models.Data


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CricketAdapter(
    private val items: ArrayList<Data>,
    private val onItemClick: (Data) -> Unit
) : RecyclerView.Adapter<CricketAdapter.CricketViewHolder>() {

    private companion object {
        const val MAX_TEAM_LEN = 12
        const val MAX_STATUS_LEN = 15
    }

    private val leagueColors = listOf("#D2F61D", "#F6771D", "#1DF6BC", "#D2F61D")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CricketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MatchItemBinding.inflate(
            inflater,
            parent,
            false
        )
        return CricketViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CricketViewHolder, position: Int) {
        val currentitem = items[position]
        holder.bind(currentitem, position)
        holder.itemView.setOnClickListener { onItemClick(currentitem) }
    }

    inner class CricketViewHolder(
        private val binding: MatchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data, position: Int) {
            val teams = item.teamInfo

            // Display team short names if available, fall back to team list
            val team1 = item.teamInfo.getOrNull(0)?.shortname ?: item.teams.getOrNull(0) ?: ""
            val team2 = item.teamInfo.getOrNull(1)?.shortname ?: item.teams.getOrNull(1) ?: ""
            binding.tvTeams1.text = team1.truncate(MAX_TEAM_LEN)
            binding.tvTeams2.text = " - ${team2.truncate(MAX_TEAM_LEN)}"

            binding.tvTime.text = item.dateTimeGMT
            val ldt = LocalDateTime.parse(item.dateTimeGMT)
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            binding.tvTime.text = ldt.format(timeFormatter)
            binding.tvStatus.text = item.status.truncate(MAX_STATUS_LEN)

            val country = teams.getOrNull(0)?.name ?: item.teams.getOrNull(0) ?: ""
            binding.tvLeague.text = country
            val color = Color.parseColor(leagueColors[position % leagueColors.size])
            binding.tvLeague.backgroundTintList = ColorStateList.valueOf(color)

        }
        private fun String.truncate(max: Int): String =
            if (length > max) take(max) + "…" else this
    }
    }


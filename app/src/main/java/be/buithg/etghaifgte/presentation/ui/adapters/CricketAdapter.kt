package be.buithg.etghaifgte.presentation.ui.adapters

import android.graphics.ColorSpace.match
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

    class CricketAdapter(
        private val items: ArrayList<Data>,
        private val onItemClick: (Data) -> Unit
    ) : RecyclerView.Adapter<CricketAdapter.CricketViewHolder>() {

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
            holder.bind(currentitem)
            holder.itemView.setOnClickListener { onItemClick(currentitem) }
        }

        inner class CricketViewHolder(
            private val binding: MatchItemBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Data) {
                // Display team short names if available, fall back to team list
                val team1 = item.teamInfo.getOrNull(0)?.shortname ?: item.teams.getOrNull(0) ?: ""
                val team2 = item.teamInfo.getOrNull(1)?.shortname ?: item.teams.getOrNull(1) ?: ""

                binding.tvTeams1.text = team1.truncate(12)
                binding.tvTeams2.text = team2.truncate(12)

                binding.tvTime.text = item.dateTimeGMT
                binding.tvLeague.text = item.venue
                binding.tvStatus.text = item.status.truncate(15)


                val country = teams.getOrNull(0)?.name ?: item.teams.getOrNull(0) ?: ""
                binding.tvLeague.text = country

            }

            private fun String.truncate(max: Int): String =
                if (length > max) take(max) + "…" else this
        }
    }

package be.buithg.etghaifgte.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.buithg.etghaifgte.data.local.entity.PredictionEntity
import be.buithg.etghaifgte.databinding.ItemHistoryPredictionBinding
import be.buithg.etghaifgte.databinding.ItemPredictionBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
class PredictionsAdapter(
    private val items: List<PredictionEntity>
) : RecyclerView.Adapter<PredictionsAdapter.PredictionViewHolder>() {

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    inner class PredictionViewHolder(
        private val binding: ItemPredictionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PredictionEntity) {
            // 1) Try to parse the full ISO‑8601 date‑time string
            val dt = runCatching { LocalDateTime.parse(item.dateTime) }.getOrNull()

            // 2) Format separately for time and date, with a safe fallback
            binding.textTime.text = dt?.format(timeFormatter) ?: item.dateTime
            binding.textDate.text = dt?.format(dateFormatter) ?: item.dateTime

            // 3) The rest of your fields
            binding.textPrediction.text = item.pick // or "Pick: ${item.pick}"

            binding.textTeams.text = "${item.teamA} - ${item.teamB}"

            if (item.upcoming == 1) {
                binding.textPrediction.text = "Upcoming"
                binding.imageResult.setImageResource(be.buithg.etghaifgte.R.drawable.icon_upcoming)
            } else {
                val result = when (item.wonMatches) {
                    1 -> if (item.pick == item.teamA) "Win" else "Lose"
                    2 -> if (item.pick == item.teamB) "Win" else "Lose"
                    else -> "Draw"
                }
                binding.textPrediction.text = "Your Pick: $result"

                val imageRes = if (result == "Lose") {
                    be.buithg.etghaifgte.R.drawable.ic_check_red
                } else {
                    be.buithg.etghaifgte.R.drawable.ic_check_green
                }
                binding.imageResult.setImageResource(imageRes)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPredictionBinding.inflate(inflater, parent, false)
        return PredictionViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

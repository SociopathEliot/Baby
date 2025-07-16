package be.buithg.etghaifgte.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.buithg.etghaifgte.data.local.entity.PredictionEntity
import be.buithg.etghaifgte.databinding.ItemHistoryPredictionBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryAdapter(
    private val items: List<PredictionEntity>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    inner class HistoryViewHolder(private val binding: ItemHistoryPredictionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PredictionEntity) {
            val dt = runCatching { LocalDateTime.parse(item.dateTime) }.getOrNull()

            binding.textTime.text = dt?.format(timeFormatter) ?: item.dateTime
            binding.textDate.text = dt?.format(dateFormatter) ?: item.dateTime

            binding.textTeams.text = "${item.teamA} - ${item.teamB}"

            val result = if (item.upcoming == 1) {
                "Upcoming"
            } else {
                when (item.wonMatches) {
                    1 -> if (item.pick == item.teamA) "Wins" else "Lose"
                    2 -> if (item.pick == item.teamB) "Wins" else "Lose"
                    else -> "Draw"
                }
            }
            binding.textPick.text = "Pick: ${item.pick} $result"
            binding.textResult.text = result
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryPredictionBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
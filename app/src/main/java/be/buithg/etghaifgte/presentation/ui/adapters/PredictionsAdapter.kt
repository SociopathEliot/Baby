package be.buithg.etghaifgte.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.buithg.etghaifgte.data.local.entity.PredictionEntity
import be.buithg.etghaifgte.databinding.ItemHistoryPredictionBinding

class PredictionsAdapter(
    private val items: List<PredictionEntity>
) : RecyclerView.Adapter<PredictionsAdapter.PredictionViewHolder>() {

    inner class PredictionViewHolder(
        private val binding: ItemHistoryPredictionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PredictionEntity) {
            binding.textTime.text = item.dateTime
            binding.textDate.text = ""
            binding.textTeams.text = "${item.teamA}  ${item.teamB}"
            binding.textPick.text = "Pick: ${item.pick}"
            binding.textResult.text = ""
            binding.btnViewDetails.text = ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryPredictionBinding.inflate(inflater, parent, false)
        return PredictionViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

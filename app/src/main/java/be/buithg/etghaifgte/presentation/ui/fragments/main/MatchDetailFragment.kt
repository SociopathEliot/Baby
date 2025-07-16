package be.buithg.etghaifgte.presentation.ui.fragments.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.buithg.etghaifgte.R
import be.buithg.etghaifgte.databinding.DialogPredictWinnerBinding
import be.buithg.etghaifgte.databinding.FragmentMatchDetailBinding
import be.buithg.etghaifgte.domain.models.Data
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import be.buithg.etghaifgte.data.local.entity.PredictionEntity
import be.buithg.etghaifgte.presentation.viewmodel.PredictionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MatchDetailFragment : Fragment() {

    companion object {
        private const val ARG_MATCH = "arg_match"

        fun newInstance(match: Data): MatchDetailFragment =
            MatchDetailFragment().apply {
                arguments = bundleOf(ARG_MATCH to match)
            }
    }

    private lateinit var binding: FragmentMatchDetailBinding
    private val predictionsViewModel: PredictionsViewModel by viewModels()
    private var selectedTeam: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val match = arguments?.getSerializable(ARG_MATCH) as? Data
        match?.let { bindMatch(it) }

        binding.btnMakeForecast.setOnClickListener {
            val dialog = Dialog(requireContext())
            val dialogBinding = DialogPredictWinnerBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val team1 = match?.teamInfo?.getOrNull(0)?.shortname ?: match?.teams?.getOrNull(0) ?: ""
            val team2 = match?.teamInfo?.getOrNull(1)?.shortname ?: match?.teams?.getOrNull(1) ?: ""
            dialogBinding.teamAText.text = team1
            dialogBinding.teamBText.text = team2
            dialogBinding.cardTeamA.setOnClickListener { selectedTeam = team1 }
            dialogBinding.cardTeamB.setOnClickListener { selectedTeam = team2 }

            dialogBinding.btnClose.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btnSubmit.setOnClickListener {
                val pick = selectedTeam ?: return@setOnClickListener
                val entity = PredictionEntity(
                    teamA = team1,
                    teamB = team2,
                    dateTime = match?.dateTimeGMT ?: "",
                    pick = pick,
                    predicted = 1,
                    corrects = 0,
                    upcoming = 1,
                    wonMatches = 0
                )
                predictionsViewModel.addPrediction(entity)
                dialog.dismiss()
            }

            dialog.show()
        }

    }

    private fun bindMatch(match: Data) {
        val formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")

        val date = runCatching { LocalDate.parse(match.date) }.getOrNull()
        val time = runCatching { LocalDateTime.parse(match.dateTimeGMT) }.getOrNull()

        binding.tvDateValue.text = date?.format(formatterDate) ?: match.date
        binding.tvTimeValue.text = time?.format(formatterTime) ?: match.dateTimeGMT

        val teams = match.teamInfo
        val team1 = teams.getOrNull(0)?.shortname ?: match.teams.getOrNull(0) ?: ""
        val team2 = teams.getOrNull(1)?.shortname ?: match.teams.getOrNull(1) ?: ""
        binding.teamTitle.text = "$team1  $team2"

        binding.statusText.text = match.status

        val venueParts = match.venue.split(",").map { it.trim() }
        val stadium = venueParts.getOrNull(0) ?: match.venue
        val city = venueParts.getOrNull(1) ?: ""

        binding.tvStadiumValue.text = stadium
        binding.tvCityValue.text = city

        val country = teams.getOrNull(0)?.name ?: match.teams.getOrNull(0) ?: ""
        binding.tvCountryValue.text = country

        binding.tvMatchTypeValue.text = match.matchType.uppercase()
    }
}
package be.buithg.etghaifgte.presentation.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import be.buithg.etghaifgte.R
import androidx.fragment.app.viewModels
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import be.buithg.etghaifgte.databinding.FragmentAchievementsBinding
import be.buithg.etghaifgte.data.local.entity.PredictionEntity
import be.buithg.etghaifgte.presentation.viewmodel.PredictionsViewModel
import be.buithg.etghaifgte.utils.Constants.ACHIEVEMENTS_COUNT_KEY
import be.buithg.etghaifgte.utils.Constants.ACHIEVEMENT_FIRST_WIN_KEY
import be.buithg.etghaifgte.utils.Constants.ACHIEVEMENT_STREAK_KEY
import be.buithg.etghaifgte.utils.Constants.ACHIEVEMENT_TOURNAMENT_KEY
import be.buithg.etghaifgte.utils.Constants.getSharedPreferences

@AndroidEntryPoint
class AchievementsFragment : Fragment() {

    private lateinit var  binding: FragmentAchievementsBinding
    private val viewModel: PredictionsViewModel by viewModels()

    private fun isWin(item: PredictionEntity): Boolean {
        return when (item.wonMatches) {
            1 -> item.pick == item.teamA
            2 -> item.pick == item.teamB
            else -> false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAchievementsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.matchScheduleFragment)
            }
        })

        binding.btnHelp.setOnClickListener {
            findNavController().navigate(R.id.tutorialFragment)
        }

        viewModel.predictions.observe(viewLifecycleOwner) { list ->
            updateAchievements(list)
        }
        viewModel.loadPredictions()
    }

    private fun updateAchievements(list: List<PredictionEntity>) {
        val prefs = context?.getSharedPreferences() ?: return
        var achievements = prefs.getInt(ACHIEVEMENTS_COUNT_KEY, 0)

        // Tournament prediction achievement
        val madePrediction = list.isNotEmpty()
        val tournamentDone = prefs.getBoolean(ACHIEVEMENT_TOURNAMENT_KEY, false)
        if (madePrediction && !tournamentDone) {
            achievements++
            prefs.edit {
                putBoolean(ACHIEVEMENT_TOURNAMENT_KEY, true)
                putInt(ACHIEVEMENTS_COUNT_KEY, achievements)
            }
        }
        val progressTournament = if (madePrediction) 100 else 0
        binding.progressIndicator2.progress = progressTournament
        binding.textPercent2.text = "$progressTournament % completed"

        // First win achievement
        val winExists = list.any { isWin(it) }
        val firstWinDone = prefs.getBoolean(ACHIEVEMENT_FIRST_WIN_KEY, false)
        if (winExists && !firstWinDone) {
            achievements++
            prefs.edit {
                putBoolean(ACHIEVEMENT_FIRST_WIN_KEY, true)
                putInt(ACHIEVEMENTS_COUNT_KEY, achievements)
            }
        }
        val progressWin = if (winExists) 100 else 0
        binding.progressIndicator3.progress = progressWin
        binding.textPercent3.text = "$progressWin % completed"

        // Ten wins in a row achievement
        val completed = list.filter { it.upcoming == 0 }
        var streak = 0
        var maxStreak = 0
        completed.forEach { item ->
            if (isWin(item)) {
                streak++
                if (streak > maxStreak) maxStreak = streak
            } else {
                streak = 0
            }
        }
        val progressStreak = (maxStreak.coerceAtMost(10) * 10)
        binding.progressIndicator.progress = progressStreak
        binding.textPercent.text = "$progressStreak % completed"
        val streakDone = prefs.getBoolean(ACHIEVEMENT_STREAK_KEY, false)
        if (maxStreak >= 10 && !streakDone) {
            achievements++
            prefs.edit {
                putBoolean(ACHIEVEMENT_STREAK_KEY, true)
                putInt(ACHIEVEMENTS_COUNT_KEY, achievements)
            }
        }

        binding.achievementsText.text = achievements.toString()
    }

}
package be.buithg.etghaifgte.presentation.ui.fragments.main

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.buithg.etghaifgte.R
import be.buithg.etghaifgte.databinding.FragmentMatchScheduleBinding
import be.buithg.etghaifgte.domain.models.Data
import be.buithg.etghaifgte.presentation.ui.adapters.CricketAdapter
import be.buithg.etghaifgte.presentation.viewmodel.MatchScheduleViewModel

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.button.MaterialButton
import androidx.navigation.fragment.findNavController
import be.buithg.etghaifgte.presentation.ui.fragments.main.MatchScheduleFragmentDirections
import be.buithg.etghaifgte.presentation.viewmodel.PredictionsViewModel
import be.buithg.etghaifgte.utils.NetworkUtils.isInternetAvailable

import java.time.LocalDate

@AndroidEntryPoint
class MatchScheduleFragment : Fragment() {

    private lateinit var binding: FragmentMatchScheduleBinding
    private val viewModel: MatchScheduleViewModel by viewModels()
    private val predictionsViewModel: PredictionsViewModel by viewModels()
    private lateinit var buttons: List<MaterialButton>
    private lateinit var adapter: CricketAdapter
    private var allMatches: List<Data> = emptyList()
    private var selectedBtn: MaterialButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireContext().isInternetAvailable()) {
            viewModel.loadMatches("80112a77-1b12-4356-94a5-806e6db2dc64")
        } else {
            Log.e("FFFF", "No Internet connection")
        }

        binding.btnHelp.setOnClickListener {
            findNavController().navigate(R.id.tutorialFragment)
        }

        viewModel.matches.observe(viewLifecycleOwner) { list ->
            allMatches = list
            filterAndDisplay(selectedBtn ?: binding.btnToday)

        }

        predictionsViewModel.predictedCount.observe(viewLifecycleOwner) {
            binding.tvPredictedCount.text = it.toString()
        }
        predictionsViewModel.upcomingCount.observe(viewLifecycleOwner) {
            binding.tvUpcomingCount.text = it.toString().padStart(2, '0')
        }
        predictionsViewModel.wonCount.observe(viewLifecycleOwner) {
            binding.tvWonCount.text = it.toString().padStart(2, '0')
        }
        predictionsViewModel.loadPredictions()

        buttons = listOf(
            binding.btnYesterday,
            binding.btnToday,
            binding.btnTomorrow
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                selectedBtn = button
                updateSelection(button)
                filterAndDisplay(button)
            }
        }

        // по умолчанию — Today выбран
        selectedBtn = binding.btnToday
        updateSelection(binding.btnToday)
    }
    private fun updateSelection(selectedButton: MaterialButton) {
        buttons.forEach { button ->
            val isSelected = button == selectedButton

            // Цвет фона: от текущего к целевому
            val fromColor = (button.backgroundTintList?.defaultColor ?: Color.TRANSPARENT)
            val toColor = if (isSelected) Color.parseColor("#FFCE01") else Color.TRANSPARENT

            ValueAnimator.ofArgb(fromColor, toColor).apply {
                duration = 250
                addUpdateListener { animator ->
                    val color = animator.animatedValue as Int
                    button.backgroundTintList = ColorStateList.valueOf(color)
                }
                start()
            }

            // Цвет текста: от текущего к целевому
            val fromTextColor = (button.currentTextColor)
            val toTextColor = if (isSelected) Color.BLACK else Color.WHITE

            ValueAnimator.ofArgb(fromTextColor, toTextColor).apply {
                duration = 250
                addUpdateListener { animator ->
                    button.setTextColor(animator.animatedValue as Int)
                }
                start()
            }
        }
    }

    private fun filterAndDisplay(button: MaterialButton) {
        val selectedDate = when (button.id) {
            R.id.btnYesterday -> LocalDate.now().minusDays(1)
            R.id.btnTomorrow -> LocalDate.now().plusDays(1)
            else -> LocalDate.now()
        }
        val filtered = allMatches.filter {
            runCatching { LocalDate.parse(it.date) }.getOrNull() == selectedDate
        }
        adapter = CricketAdapter(ArrayList(filtered)) { match ->
            val action =
                MatchScheduleFragmentDirections.actionMatchScheduleFragmentToMatchDetailFragment(match)
            findNavController().navigate(action)
        }

        binding.recyclerMatcher.adapter = adapter
    }
}
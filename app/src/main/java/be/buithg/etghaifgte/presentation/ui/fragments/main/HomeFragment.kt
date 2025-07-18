package be.buithg.etghaifgte.presentation.ui.fragments.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import be.buithg.etghaifgte.R
import be.buithg.etghaifgte.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //todo home fragment logic

        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        binding.bottomNav.navHome.setOnClickListener {
            navController.navigate(R.id.matchScheduleFragment)
        }
        binding.bottomNav.navPredictions.setOnClickListener {
            navController.navigate(R.id.predictionsFragment)
        }
        binding.bottomNav.navHistory.setOnClickListener {
            navController.navigate(R.id.predictionHistoryFragment)
        }
        binding.bottomNav.navStats.setOnClickListener {
            navController.navigate(R.id.statsFragment)
        }
        binding.bottomNav.navBlog.setOnClickListener {
            navController.navigate(R.id.blogFragment)
        }
        binding.bottomNav.navAchievements.setOnClickListener {
            navController.navigate(R.id.achievementsFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateSelection(destination.id)
        }

    }

    private fun updateSelection(id: Int) {
        val map = mapOf(
            R.id.matchScheduleFragment to binding.bottomNav.navHome,
            R.id.predictionsFragment to binding.bottomNav.navPredictions,
            R.id.predictionHistoryFragment to binding.bottomNav.navHistory,
            R.id.statsFragment to binding.bottomNav.navStats,
            R.id.blogFragment to binding.bottomNav.navBlog,
            R.id.achievementsFragment to binding.bottomNav.navAchievements,
        )
        map.forEach { (dest, view) ->
            view.isSelected = dest == id
        }
    }
}
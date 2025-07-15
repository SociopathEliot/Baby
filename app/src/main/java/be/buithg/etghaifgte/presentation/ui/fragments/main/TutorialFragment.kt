package be.buithg.etghaifgte.presentation.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.buithg.etghaifgte.R
import be.buithg.etghaifgte.databinding.FragmentTutorialBinding


class TutorialFragment : Fragment() {

    private lateinit var binding: FragmentTutorialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorialBinding.inflate(inflater,container,false)
        return binding.root
    }


}
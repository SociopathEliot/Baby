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
import be.buithg.etghaifgte.databinding.FragmentMatchScheduleBinding

class MatchDetailFragment : Fragment() {

    private lateinit var binding: FragmentMatchDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMakeForecast.setOnClickListener {
            val dialog = Dialog(requireContext())
            val dialogBinding = DialogPredictWinnerBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialogBinding.btnClose.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btnSubmit.setOnClickListener {
                // TODO: handle submit logic here
                dialog.dismiss()
            }

            dialog.show()
        }

    }
}
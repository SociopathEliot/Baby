package be.buithg.etghaifgte.presentation.ui.fragments.main

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.buithg.etghaifgte.R
import be.buithg.etghaifgte.databinding.FragmentMatchScheduleBinding
import com.google.android.material.button.MaterialButton

class MatchScheduleFragment : Fragment() {

    private lateinit var binding: FragmentMatchScheduleBinding
    private lateinit var buttons: List<MaterialButton>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttons = listOf(
            binding.btnYesterday,
            binding.btnToday,
            binding.btnTomorrow
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                updateSelection(button)
            }
        }

        // по умолчанию — Today выбран
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
}
package be.buithg.etghaifgte.presentation.ui.fragments.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var progress: Float = 0f

    private val bgPaint = Paint().apply {
        color = Color.DKGRAY
        strokeWidth = 20f
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val progressPaint = Paint().apply {
        color = Color.YELLOW
        strokeWidth = 20f
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val circlePaint = Paint().apply {
        color = Color.YELLOW
        isAntiAlias = true
        style = Paint.Style.FILL
        setShadowLayer(20f, 0f, 0f, Color.GREEN)
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    fun setProgress(value: Float) {
        progress = value.coerceIn(0f, 1f)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val padding = 40f
        val startX = padding
        val endX = width - padding
        val centerY = height / 2f
        val lineLength = endX - startX

        val progressX = startX + lineLength * progress

        canvas.drawLine(startX, centerY, endX, centerY, bgPaint)
        canvas.drawLine(startX, centerY, progressX, centerY, progressPaint)
        canvas.drawCircle(progressX, centerY, 15f, circlePaint)
    }
}

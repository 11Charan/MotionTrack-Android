package com.example.motiontrack

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MotionView(
    context: Context,
    attrs: AttributeSet?
) : View(context, attrs) {

    private val circlePaint = Paint()
    private val linePaint = Paint()
    private val borderPaint = Paint()
    private var movementX = 0f
    private var movementY = 0f

    init {
        circlePaint.color = Color.rgb(37, 99, 235)
        circlePaint.isAntiAlias = true

        linePaint.color = Color.LTGRAY
        linePaint.strokeWidth = 2f
        linePaint.isAntiAlias = true

        borderPaint.color = Color.GRAY
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = 3f
        borderPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        canvas.drawRect(
            2f,
            2f,
            width - 2f,
            height - 2f,
            borderPaint
        )

        canvas.drawLine(
            centerX,
            0f,
            centerX,
            height.toFloat(),
            linePaint
        )

        canvas.drawLine(
            0f,
            centerY,
            width.toFloat(),
            centerY,
            linePaint
        )

        var x = centerX + movementX * 15
        var y = centerY - movementY * 15

        x = x.coerceIn(40f, width - 40f)
        y = y.coerceIn(40f, height - 40f)

        canvas.drawCircle(
            x,
            y,
            32f,
            circlePaint
        )
    }

    fun updateMovement(x: Float, y: Float) {
        movementX = x
        movementY = y
        invalidate()
    }
}
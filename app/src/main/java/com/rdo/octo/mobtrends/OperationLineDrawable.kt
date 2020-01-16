package com.rdo.octo.mobtrends

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class OperationLineDrawable(
    private val paint: Paint,
    private val width: Int,
    private val height: Int,
    private val gradientPaint: Paint
) : Drawable() {

    private var points: List<Int> = emptyList()
    private var scale = 0f
    private var pointsToDraw = emptyList<Float>()

    fun setPoints(points: List<Int>) {
        this.points = points
        pointsToDraw = points.map { it * scale }
        callback?.invalidateDrawable(this)
    }

    override fun draw(canvas: Canvas) {
        // Draw the line
        val path = Path()
        val roundPath = Path()
        val max = points.max() ?: 0
        if (points.isNotEmpty()) {
            path.moveTo(0f, (0.95f * height.toFloat() * (pointsToDraw[0] * scale) / (max)) + 65)
            pointsToDraw.forEachIndexed { index, point ->
                val x = 0.92f * (width.toFloat() * index / (points.size - 1))
                val y = (0.85f * height.toFloat() * (max - point) / (max)) + 65
                path.lineTo(x, y)
            }
            val asReversed = pointsToDraw.asReversed()
            asReversed.forEachIndexed { index, point ->
                val x = 0.92f * (width.toFloat() * (points.size - index - 1) / (points.size - 1))
                val y = (0.85f * height.toFloat() * (max - point) / (max)) + 80
                if (index == 0) {
                    roundPath.moveTo(x, y)
                }
                path.lineTo(x, y)
            }
            path.close()
            canvas.drawPath(path, paint)


            // Draw the little circle at the end of line
            val innerRadius = 20
            val outerRadius = 40
            val centerX = 0.92f * width
            val centerY = (0.85f * height.toFloat() * (max - (pointsToDraw[points.size - 1])) / (max)) + 80
            for (i in (-PI * 100).toInt()..(PI * 100).toInt()) {
                val angle = i / 100.toDouble()
                val x = (cos(angle) * innerRadius / 2f) + centerX
                val y = (innerRadius / -2f * sin(angle)) + centerY
                roundPath.lineTo(x.toFloat(), y.toFloat())
            }
            for (i in (-PI * 100).toInt()..(PI * 100).toInt()) {
                val angle = i / 100.toDouble()
                val x = (cos(angle) * outerRadius / 2f) + centerX
                val y = (outerRadius / -2f * sin(angle)) + centerY
                roundPath.lineTo(x.toFloat(), y.toFloat())
            }
            roundPath.close()
            canvas.drawPath(roundPath, paint)

            // Draw under the line
            val underPath = Path()
            underPath.moveTo(0f, height.toFloat())
            pointsToDraw.forEachIndexed { index, point ->
                val x = 0.92f * (width.toFloat() * index / (points.size - 1))
                val y = (0.85f * height.toFloat() * (max - point) / (max)) + 65
                underPath.lineTo(x, y)
            }
            underPath.lineTo(centerX + innerRadius / 2, (0.85f * height.toFloat() * (max - (pointsToDraw[points.size - 1] * scale)) / (max)) + 65)
            underPath.lineTo(centerX + innerRadius / 2, height.toFloat())
            underPath.lineTo(0f, height.toFloat())
            underPath.close()
            canvas.drawPath(underPath, gradientPaint)
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getIntrinsicWidth() = width

    override fun getIntrinsicHeight() = height

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    fun setScale(scale: Float) {
        this.scale = scale
        pointsToDraw = points.map { it * scale }
        callback?.invalidateDrawable(this)
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}
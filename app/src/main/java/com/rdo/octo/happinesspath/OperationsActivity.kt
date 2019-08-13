package com.rdo.octo.happinesspath

import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.line_container.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class OperationsActivity : AppCompatActivity() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 4f
    }

    private val gradientPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 4f
    }

    private lateinit var operationLineDrawable: OperationLineDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operations)

        paint.color = ContextCompat.getColor(this, android.R.color.white)
        paint.setStrokeCap(Paint.Cap.ROUND)

        val colorTop = ContextCompat.getColor(this, android.R.color.white)
        val colorBottom = ContextCompat.getColor(this, android.R.color.transparent)

        lineContainer.post {
            gradientPaint.shader = LinearGradient(
                0f,
                0f,
                0f,
                lineContainer.height.toFloat(),
                colorTop,
                colorBottom,
                Shader.TileMode.REPEAT
            )
            operationLineDrawable =
                OperationLineDrawable(
                    paint,
                    lineContainer.width,
                    lineContainer.height,
                    gradientPaint
                )
            lineContainer.background = operationLineDrawable
            operationLineDrawable.setPoints(
                listOf(120, 100, 134, 345, 890, 300, 500, 432, 999, 666, 700, 1000, 1200, 1500)
            )
        }

    }
}

class OperationLineDrawable(
    private val paint: Paint,
    private val width: Int,
    private val height: Int,
    private val gradientPaint: Paint
) : Drawable() {

    private var points: List<Int> = emptyList()

    fun setPoints(points: List<Int>) {
        this.points = points
        callback?.invalidateDrawable(this)
    }

    override fun draw(canvas: Canvas) {
        // Draw the line
        val path = Path()
        val roundPath = Path()
        val max = points.max() ?: 0
        val min = points.min() ?: 0
        if (points.isNotEmpty()) {
            path.moveTo(0f, (0.95f * height.toFloat() * points[0] / (max)) + 65)
            points.forEachIndexed { index, point ->
                val x = 0.92f * (width.toFloat() * index / (points.size - 1))
                val y = (0.85f * height.toFloat() * (max - point) / (max)) + 65
                path.lineTo(x, y)
            }
            val asReversed = points.asReversed()
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
            val centerY =
                (0.85f * height.toFloat() * (max - points[points.size - 1]) / (max)) - 80 + (innerRadius / 2)
            for (i in (-PI * 100).toInt()..(PI * 100).toInt()) {
                val angle = i / 100.toDouble()
                val x = (cos(angle) * innerRadius / 2f) + centerX
                val y = (innerRadius / -2f * sin(angle)) - centerY
                roundPath.lineTo(x.toFloat(), y.toFloat())
            }
            for (i in (-PI * 100).toInt()..(PI * 100).toInt()) {
                val angle = i / 100.toDouble()
                val x = (cos(angle) * outerRadius / 2f) + centerX
                val y = (outerRadius / -2f * sin(angle)) - centerY
                roundPath.lineTo(x.toFloat(), y.toFloat())
            }
            roundPath.close()
            canvas.drawPath(roundPath, paint)

            // Draw under the line
            val underPath = Path()
            underPath.moveTo(0f, height.toFloat())
            points.forEachIndexed { index, point ->
                val x = 0.92f * (width.toFloat() * index / (points.size - 1))
                val y = (0.85f * height.toFloat() * (max - point) / (max)) + 65
                underPath.lineTo(x, y)
            }
            underPath.lineTo(centerX + innerRadius / 2, (0.85f * height.toFloat() * (max - points[points.size - 1]) / (max)) + 65)
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

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}
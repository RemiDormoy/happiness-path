package com.rdo.octo.happinesspath

import android.animation.ObjectAnimator
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.Bundle
import android.os.Handler
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import kotlinx.android.synthetic.main.line_container.*
import kotlinx.android.synthetic.main.operation_scroll_content.*

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

        initLine()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = HORIZONTAL
        walletsRecyclerView.layoutManager = linearLayoutManager
        walletsRecyclerView.adapter = WalletsAdapter()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = VERTICAL
        operationsRecyclerView.layoutManager = layoutManager
        operationsRecyclerView.adapter = OperationsAdapter()

    }

    private fun initLine() {
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

        Handler().postDelayed({
            val animator = ObjectAnimator.ofFloat(0f, 1f)
            animator.duration = 1000
            animator.interpolator = BounceInterpolator()
            animator.addUpdateListener {
                operationLineDrawable.setScale(it.animatedValue as Float)
            }
            animator.start()
        }, 500)
    }
}


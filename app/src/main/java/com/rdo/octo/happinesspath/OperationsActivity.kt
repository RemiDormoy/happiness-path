package com.rdo.octo.happinesspath

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import kotlinx.android.synthetic.main.activity_operations.*
import kotlinx.android.synthetic.main.line_container.lineContainer
import kotlinx.android.synthetic.main.operation_header_collapsed.*
import kotlinx.android.synthetic.main.operation_scroll_content.*

class OperationsActivity : BottomSheetActivity() {

    val operationsAdapter : OperationsAdapter by lazy {  OperationsAdapter(::openCompleteProfilePopUp) }


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

        initContent()

        addAnimation()

        initButtons()

    }

    private fun openCompleteProfilePopUp() {
        openBottomSheetMwahaha()
    }

    private fun initButtons() {
        val white = ContextCompat.getColor(this, android.R.color.white)
        val black = ContextCompat.getColor(this, R.color.alizouzBlack)

        button.isSelected = true
        button2.isSelected = false
        button3.isSelected = false
        button.setTextColor(white)
        button3.setTextColor(black)
        button2.setTextColor(black)

        button.setOnClickListener {
            button.isSelected = true
            button2.isSelected = false
            button3.isSelected = false
            button.setTextColor(white)
            button3.setTextColor(black)
            button2.setTextColor(black)
            operationsAdapter.filter(0)
        }

        button2.setOnClickListener {
            button2.isSelected = true
            button.isSelected = false
            button3.isSelected = false
            button2.setTextColor(white)
            button.setTextColor(black)
            button3.setTextColor(black)
            operationsAdapter.filter(1)
        }

        button3.setOnClickListener {
            button3.isSelected = true
            button2.isSelected = false
            button.isSelected = false
            button3.setTextColor(white)
            button.setTextColor(black)
            button2.setTextColor(black)
            operationsAdapter.filter(2)
        }
    }

    private fun addAnimation() {
        Handler().postDelayed({
            operationsScrollView.viewTreeObserver.addOnScrollChangedListener {
                yolo()
            }
        }, 200)

    }

    private fun yolo() {
        val scroll = operationsScrollView.scrollY.toFloat()
        val progress = maxOf(0f, minOf(1f, scroll / 500f))
        operationHeaderContainer.progress = progress
        lineContainer.alpha = 1 - progress
    }

    private fun initContent() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = HORIZONTAL
        walletsRecyclerView.layoutManager = linearLayoutManager
        walletsRecyclerView.adapter = WalletsAdapter()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = VERTICAL
        operationsRecyclerView.layoutManager = layoutManager
        operationsRecyclerView.adapter = operationsAdapter
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
            //lineContainer.background = operationLineDrawable
            /*operationLineDrawable.setPoints(
                listOf(120, 100, 134, 345, 890, 300, 500, 432, 999, 666, 700, 1000, 1200, 1500)
            )*/
        }

        /*Handler().postDelayed({
            val animator = ObjectAnimator.ofFloat(0f, 1f)
            animator.duration = 1000
            animator.interpolator = BounceInterpolator()
            animator.addUpdateListener {
                operationLineDrawable.setScale(it.animatedValue as Float)
            }
            animator.start()
        }, 500)*/
    }
}


package com.rdo.octo.happinesspath

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.activity_operations.*
import kotlinx.android.synthetic.main.cell_end_of_operations.*
import kotlinx.android.synthetic.main.line_container.lineContainer
import kotlinx.android.synthetic.main.operation_header.*
import kotlinx.android.synthetic.main.operation_scroll_content.*
import java.util.*
import android.net.Uri

class OperationsActivity : BottomSheetActivity() {

    val operationsAdapter : OperationsAdapter by lazy {  OperationsAdapter(::openCompleteProfilePopUp, ::humaneClick) }


    private lateinit var lastScrollTime: Date

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
        imageView2.setOnClickListener {
            popUpAlpha.visibility = VISIBLE
            popUpCard.visibility = VISIBLE
            popUpAlpha.setOnClickListener {
                popUpAlpha.visibility = GONE
                popUpCard.visibility = GONE
            }
        }

        discoverOctoButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.octo.com"))
            startActivity(browserIntent)
        }

        if (intent.getBooleanExtra("showPopUp", false)) {
            popUpAlpha.visibility = VISIBLE
            popUpCard.visibility = VISIBLE
            popUpAlpha.setOnClickListener {
                popUpAlpha.visibility = GONE
                popUpCard.visibility = GONE
            }
        }

        initLine()

        initContent()

        addAnimation()

        initButtons()

        imageView.setOnClickListener {
            drawerRoot.openDrawer(GravityCompat.START)
        }

    }

    private fun openCompleteProfilePopUp() {
        openBottomSheetMwahaha(Pattern.AUGMENTED_LIST)
    }

    private fun humaneClick() {
        openBottomSheetMwahaha(Pattern.HUMANE_DESIGN)
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
                if (!operationsScrollView.canScrollVertically(1)) {
                    lottienEndView.visibility = VISIBLE
                    lottienEndView.playAnimation()
                }
            }
        }, 200)

    }

    private fun showNotifs() {
        operationsAdapter.showNotifs(true)
    }

    private fun hideNotifs() {
        operationsAdapter.showNotifs(false)
    }

    private fun yolo() {
        val date = Date()
        lastScrollTime = date
        hideNotifs()
        Handler().postDelayed({
            if(lastScrollTime.time == date.time) {
                showNotifs()
            }
        }, 300)
        val scroll = operationsScrollView.scrollY.toFloat()
        val progress = maxOf(0f, minOf(1f, scroll / 500f))
        lineContainer.alpha = 1 - progress
        bitchView.alpha = 1 - progress
        operationHeaderCardView.translationY = -progress * fakeView.height - imageView2.height
        imageView.translationY = progress * fakeView.height
        imageView2.translationY = progress * fakeView.height
        textView2.translationY = progress * fakeView.height
        textView3.translationY = progress * fakeView.height
    }

    private fun initContent() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = HORIZONTAL
        walletsRecyclerView.layoutManager = linearLayoutManager
        walletsRecyclerView.adapter = WalletsAdapter( {
            startActivity(Intent(this, TransferActivity::class.java))
            finish()
        }, {
          openBottomSheetMwahaha(Pattern.SIMPLE_ACCESS)
        })

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


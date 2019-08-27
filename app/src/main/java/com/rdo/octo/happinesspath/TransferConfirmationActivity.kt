package com.rdo.octo.happinesspath

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.activity_transfer_confirmation.*
import kotlinx.android.synthetic.main.bottom_sheet_content.*
import kotlin.math.abs

class TransferConfirmationActivity : AppCompatActivity() {

    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)
        returnCardView.alpha = 0f
        sendTextView.alpha = 0f
        Handler().postDelayed({
            returnCardView.animate().alpha(1f).start()
            sendTextView.animate().alpha(1f).start()
            sendingTextView.animate().alpha(0f).start()
            lottieNotification.visibility = VISIBLE
        }, 1500)
        button9.setOnClickListener {
            finish()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            patternContentTextView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
        behavior = BottomSheetBehavior.from(bottom_sheet)
        lottieNotification.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
                bottom_sheet_black_background.alpha = p1 * 0.5f
            }

            override fun onStateChanged(p0: View, state: Int) {
                if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottom_sheet_black_background.visibility = GONE
                } else {
                    bottom_sheet_black_background.visibility = VISIBLE
                }
            }

        })
        bottom_sheet_black_background.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onBackPressed() {
        if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }
    }
}
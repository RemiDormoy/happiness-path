package com.rdo.octo.happinesspath

import android.os.Build
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.activity_transfer_confirmation.*
import kotlinx.android.synthetic.main.bottom_sheet_content.*

abstract class BottomSheetActivity : AppCompatActivity() {

    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_bottom_sheet)
        rootViewToInflate.removeAllViews()
        LayoutInflater.from(this).inflate(layoutResID, rootViewToInflate)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            patternContentTextView.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
        }
        behavior = BottomSheetBehavior.from(bottom_sheet)
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
                bottom_sheet_black_background.alpha = p1 * 0.5f
            }

            override fun onStateChanged(p0: View, state: Int) {
                if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottom_sheet_black_background.visibility = View.GONE
                } else {
                    bottom_sheet_black_background.visibility = View.VISIBLE
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

    protected fun openBottomSheetMwahaha() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}
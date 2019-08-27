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

class TransferConfirmationActivity : BottomSheetActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_confirmation)
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
        lottieNotification.setOnClickListener {
            openBottomSheetMwahaha()
        }
    }
}
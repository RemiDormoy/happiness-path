package com.rdo.octo.happinesspath

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_transfer_confirmation.*

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
            startActivity(Intent(this, OperationsActivity::class.java))
            finish()
        }
        lottieNotificationAnimation.setOnClickListener {
            openBottomSheetMwahaha(Pattern.KEEP_THEM_WAITING)
        }
        lottieNotification.setOnClickListener {
            openBottomSheetMwahaha(Pattern.GRATIFICATION)
        }
    }
}
package com.rdo.octo.happinesspath

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_transfer_confirmation.*

class TransferConfirmationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_confirmation)
        returnCardView.alpha = 0f
        sendTextView.alpha = 0f
        Handler().postDelayed({
            returnCardView.animate().alpha(1f).start()
            sendTextView.animate().alpha(1f).start()
            sendingTextView.animate().alpha(0f).start()
        }, 1500)
        button9.setOnClickListener {
            finish()
        }
    }
}
package com.rdo.octo.happinesspath

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_transfer_confirmation.*

class TransferConfirmationActivity : BottomSheetActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_confirmation)
        returnCardView.alpha = 0f
        intent.getStringExtra("amount")?.let {
            amountConfirmationTextView.text = it
        }
        sendTextView.alpha = 0f
        Handler().postDelayed({
            returnCardView.animate().alpha(1f).start()
            sendTextView.animate().alpha(1f).start()
            sendingTextView.animate().alpha(0f).start()
            lottieNotification.visibility = VISIBLE
            amountConfirmationTextView.visibility = GONE
        }, 2500)
        button9.setOnClickListener {
            startActivity(Intent(this, OperationsActivity::class.java).putExtra("showPopUp", true))
            finish()
        }
        lottieNotificationAnimation.setOnClickListener {
            openBottomSheetMwahaha(Pattern.KEEP_THEM_WAITING)
        }
        lottieNotification.setOnClickListener {
            openBottomSheetMwahaha(Pattern.GRATIFICATION)
        }
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Confirmation")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Confirmation")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Confirmation")
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, OperationsActivity::class.java))
        super.onBackPressed()
    }
}
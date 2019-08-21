package com.rdo.octo.happinesspath

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_transfer.*
import kotlinx.android.synthetic.main.transfer_amount.*
import kotlinx.android.synthetic.main.transfer_confirm.*
import kotlinx.android.synthetic.main.transfer_contact.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class TransferActivity: AppCompatActivity() {

    private val contactAdapter: ContactAdapter by lazy { ContactAdapter(::click) }
    private var step = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
        cardView2.post {
            cardView2.translationY = cardView2.height.toFloat()
        }
        cardView3.post {
            cardView3.translationY = cardView3.height.toFloat()
        }

        selectedContactContainer1.visibility = INVISIBLE
        transferAmountContinueButton.setOnClickListener {
            val animator = ValueAnimator.ofFloat(cardView3.height.toFloat(), 0f)
            animator.duration = 500
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener {
                cardView3.translationY = it.animatedValue as Float
                val progress = 1f - (it.animatedValue as Float) / cardView3.height.toFloat()
                transferAmountContainer.progress = progress
                amountEditText.isEnabled = false
            }
            animator.start()
        }
        transferConfirmButton.setOnClickListener {
            initFingerprint()
        }
        contactRecyclerView.layoutManager = LinearLayoutManager(this)
        contactRecyclerView.adapter = contactAdapter
    }

    private fun click(contact: Contact, y: Float) {
        step = 1
        selectedContactImageView.setImageResource(contact.picture)
        selectedContactNameTextView.text = contact.name
        val startTranslation = y + contactRecyclerView.y
        selectedContactContainer1.translationY = startTranslation
        selectedContactContainer1.visibility = VISIBLE
        val animator = ValueAnimator.ofFloat(cardView2.height.toFloat(), 0f)
        animator.duration = 500
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener {
            cardView2.translationY = it.animatedValue as Float
            val progress = 1f - (it.animatedValue as Float) / cardView3.height.toFloat()
            selectedContactContainer1.translationY = startTranslation * (1 - progress)
            contactRecyclerView.alpha = 1 - progress
        }
        animator.start()
    }


    override fun onBackPressed() {
        when (step) {
            1 -> returnToCard1()
            else -> super.onBackPressed()
        }
    }

    private fun returnToCard1() {
        step = 0
        selectedContactContainer1.visibility = INVISIBLE
        val animator = ValueAnimator.ofFloat(0f, cardView2.height.toFloat())
        animator.duration = 500
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener {
            val progress = (it.animatedValue as Float) / cardView2.height.toFloat()
            cardView2.translationY = it.animatedValue as Float
            contactRecyclerView.alpha = progress
        }
        animator.start()
    }

    private fun initFingerprint() {
        val biometricPrompt = BiometricPrompt(
            this,
            Executors.newSingleThreadExecutor(),
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@TransferActivity,
                            "Called when an unrecoverable error has been encountered and the operation is complete.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@TransferActivity,
                            "Called when a biometric is valid but not recognized.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Confirmer le virement")
            .setNegativeButtonText("Annuler")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
}
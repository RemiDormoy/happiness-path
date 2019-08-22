package com.rdo.octo.happinesspath

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import kotlinx.android.synthetic.main.activity_transfer.*
import kotlinx.android.synthetic.main.transfer_amount.*
import kotlinx.android.synthetic.main.transfer_confirm.*
import kotlinx.android.synthetic.main.transfer_contact.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import android.text.Editable
import android.text.TextWatcher
import android.view.View.*


class TransferActivity : AppCompatActivity() {

    private val contactAdapter: ContactAdapter by lazy { ContactAdapter(::click) }
    private val addedContactAdapter: AddedContactAdapter by lazy {
        AddedContactAdapter(
            ::showButtonContact,
            ::hideButtonContact
        )
    }
    private var step = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
        cardView2.post {
            cardView2.translationY = cardView2.height.toFloat()
        }
        cardView3.post {
            cardView3.translationY = cardView3.height.toFloat()
            addedContacts.scaleY = 0f
            addedContacts.scaleX = 0f
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
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = HORIZONTAL
        addedContactsRecyclerView.layoutManager = linearLayoutManager
        addedContactsRecyclerView.adapter = addedContactAdapter

        goToAmountButton.setOnClickListener {
            step = 1
            val animator = ValueAnimator.ofFloat(cardView2.height.toFloat(), 0f)
            animator.duration = 500
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener {
                cardView2.translationY = it.animatedValue as Float
                val progress = 1f - (it.animatedValue as Float) / cardView2.height.toFloat()
                contactCardContainer.progress = progress
            }
            val black = ContextCompat.getColor(this, R.color.alizouzBlack)
            val white = ContextCompat.getColor(this, android.R.color.white)
            val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), black, white)
            colorAnimation.duration = 300
            Handler().postDelayed({
                yoloText.visibility = GONE
                yoloImage.visibility = GONE
            }, 300)
            colorAnimation.addUpdateListener {
                goToAmountButton.setBackgroundColor(it.animatedValue as Int)
            }
            animator.start()
            colorAnimation.start()
            amountEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    val textWithout = amountEditText.getText().toString().replace(" €", "")
                    val newText = textWithout + " €"
                    if (newText != amountEditText.getText().toString()) {
                        amountEditText.setText(newText)
                        amountEditText.setSelection(newText.length - 2)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })

            reasonEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    reasonCountTextView.text = "${reasonEditText.text.length}/120"
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun click(contact: Contact, y: Float) {
        if (contact.isChecked) {
            addedContactAdapter.add(contact)
        } else {
            addedContactAdapter.remove(contact)
        }
    }


    private fun showButtonContact() {
        addedContacts.animate().scaleX(1f).scaleY(1f).start()
    }

    private fun hideButtonContact() {
        addedContacts.animate().scaleX(0f).scaleY(0f).start()
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
        yoloText.visibility = VISIBLE
        yoloImage.visibility = VISIBLE
        val animator = ValueAnimator.ofFloat(0f, cardView2.height.toFloat())
        animator.duration = 500
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener {
            val progress = (it.animatedValue as Float) / cardView2.height.toFloat()
            cardView2.translationY = it.animatedValue as Float
            contactRecyclerView.alpha = progress
            textView.alpha = progress
        }
        contactCardContainer.progress = 0f
        goToAmountButton.setBackgroundResource(R.drawable.border_button_background_selected)
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
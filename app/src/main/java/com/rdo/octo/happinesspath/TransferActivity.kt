package com.rdo.octo.happinesspath

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
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
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import androidx.core.view.GestureDetectorCompat
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import com.google.firebase.analytics.FirebaseAnalytics
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

class TransferActivity : BottomSheetActivity() {

    private val contactAdapter: ContactAdapter by lazy { ContactAdapter(::click) }
    private val addedContactAdapter: AddedContactAdapter by lazy {
        AddedContactAdapter(
            ::showButtonContact,
            ::hideButtonContact
        )
    }
    private var step = 0
    private var scroll = 0f
    private var isReasonCollapsed = false
    private var isKeyboardOpen = false
    private lateinit var detector: GestureDetectorCompat
    private lateinit var detector1: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Virements")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Virements")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Virements")
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

        cardView2.post {
            cardView2.translationY = cardView2.height.toFloat()
        }
        cardView3.post {
            cardView3.translationY = cardView3.height.toFloat()
            addedContacts.scaleY = 0f
            addedContacts.scaleX = 0f
        }
        KeyboardVisibilityEvent.setEventListener(this) { isOpen -> onKeyboardEvent(isOpen) }
        selectedContactContainer1.visibility = INVISIBLE
        transferConfirmButton.setOnClickListener {
            initFingerprint()
        }

        lottieNotificationConfirm.setOnClickListener {
            openBottomSheetMwahaha(Pattern.FINGERPRINT)
        }
        lottieNotificationGesture.setOnClickListener {
            openBottomSheetMwahaha(Pattern.GESTURES)
        }
        lottieNotificationBottom.setOnClickListener {
            openBottomSheetMwahaha(Pattern.BOTTOM)
        }
        lottieNotificationInfinite.setOnClickListener {
            openBottomSheetMwahaha(Pattern.INFINITE_NAV)
        }

        textView.setOnClickListener {
            if (step > 1) {
                returnToCard2()
            }
            if (step > 0) {
                returnToCard1()
            }
        }
        textView10.setOnClickListener {
            if (step > 1) {
                returnToCard2()
            }
        }

        detector = GestureDetectorCompat(this, object : GestureDetector.OnGestureListener {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                return true
            }

            override fun onShowPress(e: MotionEvent?) {
                // Do nothing
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return false
            }

            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val scroll = e2.y - e1.y
                if (scroll > 250) {
                    returnToCard2()
                }
                return true
            }

            override fun onLongPress(e: MotionEvent?) {
                // Do nothing
            }
        })
        detector1 = GestureDetectorCompat(this, object : GestureDetector.OnGestureListener {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                return true
            }

            override fun onShowPress(e: MotionEvent?) {
                // Do nothing
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return false
            }

            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val scroll = e2.y - e1.y
                if (scroll > 250) {
                    returnToCard1()
                }
                return true
            }

            override fun onLongPress(e: MotionEvent?) {
                // Do nothing
            }
        })
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
            val white = Color.TRANSPARENT
            val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), black, white)
            colorAnimation.duration = 300
            Handler().postDelayed({
                yoloText.visibility = GONE
                yoloImage.visibility = GONE

            }, 500)
            colorAnimation.addUpdateListener {
                goToAmountButton.setBackgroundColor(it.animatedValue as Int)
                addedContacts.setBackgroundColor(it.animatedValue as Int)
            }
            reasonEditText.hint =
                "Virement à ${addedContactAdapter.list.joinToString(", ") { it.name }}"
            animator.start()
            colorAnimation.start()
            amountEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (amountEditText.getText().isBlank()) {
                        return
                    }
                    val textWithout = amountEditText.getText().toString().replace("€", "").trim()
                    if (textWithout.isBlank()) {
                        amountEditText.setText("")
                        disableButton()
                    } else {
                        val newText = "$textWithout €"
                        if (newText != amountEditText.text.toString()) {
                            amountEditText.setText(newText)
                            amountEditText.setSelection(newText.length - 2)
                        }
                        enableAmountButton()
                        fakeTextView.text = amountEditText.text
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
            amountEditText.requestFocus()
            Handler().postDelayed({
                showKeyboard()
            }, 500)

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
        transferConfirmRoot.setOnTouchListener { v, event ->
            detector.onTouchEvent(event)
        }
        transferAmountContainer.setOnTouchListener { v, event ->
            detector1.onTouchEvent(event)
        }
    }

    private fun onKeyboardEvent(isOpen: Boolean) {
        if (step == 2) {
            toggleReason(isOpen)
        }
        this.isKeyboardOpen = isOpen
    }

    private fun toggleReason(hasFocus: Boolean) {
        val animator = if (hasFocus) {
            ValueAnimator.ofFloat(0f, 1f)
        } else {
            ValueAnimator.ofFloat(1f, 0f)
        }
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            transferCardsRootToAnim.progress = value
        }
        animator.interpolator = DecelerateInterpolator()
        animator.start()
        isReasonCollapsed = hasFocus
    }

    private fun enableAmountButton() {
        transferAmountContinueButton.setOnClickListener {
            Handler().postDelayed({
                step = 2
            }, 800)
            hideKeyboard()
            val animator = ValueAnimator.ofFloat(cardView3.height.toFloat(), 0f)
            animator.duration = 500
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener {
                cardView3.translationY = it.animatedValue as Float
                val progress = 1f - (it.animatedValue as Float) / cardView3.height.toFloat()
                transferAmountContainer.progress = progress
                amountEditText.requestFocus()
                amountEditText.isEnabled = false
            }
            animator.startDelay = 1500
            animator.startDelay = 500
            animator.start()
        }
        transferAmountContinueButton.setBackgroundResource(R.drawable.border_button_background_selected)
    }

    private fun disableButton() {
        transferAmountContinueButton.setOnClickListener {}
        transferAmountContinueButton.setBackgroundResource(R.drawable.border_button_background_disabled)
    }

    private fun click(contact: Contact, y: Float) {
        if (step > 0) return
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
            2 -> returnToCard2()
            else -> {
                startActivity(Intent(this, OperationsActivity::class.java))
                super.onBackPressed()
            }
        }
    }

    private fun returnToCard2() {
        step = 1
        transferAmountContinueButton.visibility = VISIBLE
        hideKeyboard()
        if (this.isKeyboardOpen) {
            hideKeyboard()
            toggleReason(false)
            Handler().postDelayed({
                actualyReturnToCard2()
            }, 300)
        } else {
            actualyReturnToCard2()
        }
    }

    private fun actualyReturnToCard2() {
        val animator = ValueAnimator.ofFloat(0f, cardView3.height.toFloat())
        animator.duration = 500
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener {
            cardView3.translationY = it.animatedValue as Float
        }
        transferAmountContainer.progress = 0f
        amountEditText.isEnabled = true
        animator.start()
    }

    private fun returnToCard1() {
        step = 0
        if (this.isKeyboardOpen) {
            hideKeyboard()
            Handler().postDelayed({
                actualyReturnToCard1()
            }, 300)
        } else {
            actualyReturnToCard1()
        }
    }

    private fun actualyReturnToCard1() {
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
                            "L'identification a échoué, veuillez réessayer",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(
                        Intent(
                            this@TransferActivity,
                            TransferConfirmationActivity::class.java
                        ).putExtra("amount", amountEditText.text.toString())
                    )
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@TransferActivity,
                            "L'identification a échoué, veuillez réessayer",
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

@SuppressLint("ServiceCast")
fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showKeyboard() {
    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}
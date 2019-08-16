package com.rdo.octo.happinesspath

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import kotlinx.android.synthetic.main.activity_simple_auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class SimpleAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_auth)
        button4.setOnClickListener {
            animateToPlaceFinger()
        }
        button5.setOnClickListener {
            animateToLogin()
        }
        button6.setOnClickListener {
            animateToInit()
        }
        fingerprintLottieView.postDelayed({
            animateToPlaceFinger()
        }, 500)

    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            initFingerprint()
        }, 500)
    }

    private fun animateToPlaceFinger() {
        val animator = ValueAnimator.ofFloat(0f, 0.15f)
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            val progress = it.animatedValue as Float
            fingerprintLottieView.progress = progress
        }
        animator.duration = 2000
        animator.start()
    }

    private fun animateToLogin() {
        val animator = ValueAnimator.ofFloat(0.15f, 0.6f)
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            val progress = it.animatedValue as Float
            fingerprintLottieView.progress = progress
        }
        animator.duration = 2000
        animator.start()
    }

    private fun animateToInit() {
        val animator = ValueAnimator.ofFloat(0.6f, 1.1f)
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            val progress = it.animatedValue as Float
            if (progress > 1f) {
                fingerprintLottieView.progress = 1f
            } else {
                fingerprintLottieView.progress = progress
            }
        }
        animator.duration = 2000
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
                            this@SimpleAuthActivity,
                            "Called when an unrecoverable error has been encountered and the operation is complete.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@SimpleAuthActivity,
                            "Called when a biometric is recognized.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@SimpleAuthActivity,
                            "Called when a biometric is valid but not recognized.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authenticate with fingerprint")
            .setNegativeButtonText("Proceed with login/password")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
}
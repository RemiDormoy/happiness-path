package com.rdo.octo.happinesspath

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        quitSplashButton.setOnClickListener {
            startActivity(Intent(this, OperationsActivity::class.java))
            finish()
        }
    }

}
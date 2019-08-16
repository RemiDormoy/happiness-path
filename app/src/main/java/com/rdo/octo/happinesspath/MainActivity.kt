package com.rdo.octo.happinesspath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        augmentedListTextView.setOnClickListener {
            startActivity(Intent(this, OperationsActivity::class.java))
        }
        simpleAuthTextView.setOnClickListener {
            startActivity(Intent(this, SimpleAuthActivity::class.java))
        }
    }
}

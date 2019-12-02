package com.rdo.octo.happinesspath

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            splashLogoImageView.animate().setDuration(300).scaleY(1.25f).scaleX(1.25f).withEndAction {
                splashLogoImageView.animate().setDuration(300).scaleY(1f).scaleX(1f).withEndAction {
                    splashLogoImageView.animate().rotation(30f).setDuration(300).scaleY(1.25f).scaleX(1.25f)
                        .withEndAction {
                            splashLogoImageView.animate().rotation(0f).setDuration(300).scaleY(0f).scaleX(0f).withEndAction {
                                splashViewPager.alpha = 0f
                                splashViewPager.visibility = VISIBLE
                                splashViewPager.animate().alpha(1f).start()
                                splashViewPager.adapter = SplashViewPagerAdapter(supportFragmentManager)
                                quitSplashButton.alpha = 0f
                                quitSplashButton.visibility = VISIBLE
                                quitSplashButton.animate().alpha(1f).start()
                            }
                        }.start()
                }.start()
            }.start()
        }, 1500)
        quitSplashButton.setOnClickListener {
            startActivity(Intent(this, OperationsActivity::class.java))
            finish()
        }
    }

}

class SplashViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        return SplashFragment(position)
    }

    override fun getCount(): Int {
        return 2
    }

}

class SplashFragment(private val position: Int) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val res = if (position == 1) {
            R.layout.fragment_splash_tuto_2
        } else {
            R.layout.fragment_splash_tuto
        }
        return inflater.inflate(res, container, false)
    }
}

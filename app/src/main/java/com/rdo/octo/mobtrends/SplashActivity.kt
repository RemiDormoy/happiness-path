package com.rdo.octo.mobtrends

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
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_splash_tuto.*

class SplashActivity : AppCompatActivity() {

    val adapter = SplashViewPagerAdapter(supportFragmentManager) {
        if (splashViewPager.currentItem == 2) {
            startActivity(Intent(this, OperationsActivity::class.java))
            finish()
        } else {
            splashViewPager.setCurrentItem(splashViewPager.currentItem + 1, true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Glide.with(this)
            .load(R.drawable.logo_octo_splash)
            .into(logoOctoImageView)
        Handler().postDelayed({
            splashLogoImageView.animate().setDuration(300).scaleY(1.25f).scaleX(1.25f)
                .withEndAction {
                    splashLogoImageView.animate().setDuration(300).scaleY(1f).scaleX(1f)
                        .withEndAction {
                            splashLogoImageView.animate().rotation(30f).setDuration(300)
                                .scaleY(1.25f).scaleX(1.25f)
                                .withEndAction {
                                    logoOctoImageView.animate().scaleY(0f).scaleX(0f).setDuration(300).start()
                                    splashLogoImageView.animate().rotation(0f).setDuration(300)
                                        .scaleY(0f).scaleX(0f).withEndAction {
                                            splashViewPager.alpha = 0f
                                            splashViewPager.visibility = VISIBLE
                                            splashViewPager.animate().alpha(1f).start()
                                            splashViewPager.adapter = adapter
                                            splashPageIndicator.alpha = 0f
                                            splashPageIndicator.visibility = VISIBLE
                                            splashPageIndicator.animate().alpha(1f).start()
                                        }
                                }.start()
                        }.start()
                }.start()
        }, 1000)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Splash")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Splash")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Splash")
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

}

class SplashViewPagerAdapter(fragmentManager: FragmentManager, private val buttonCallback: () -> Unit) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        return SplashFragment(position, buttonCallback)
    }

    override fun getCount(): Int {
        return 3
    }

}

class SplashFragment(private val position: Int, private val buttonCallback: () -> Unit) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_splash_tuto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = when (position) {
            2 -> "Lancez vous !"
            1 -> "Expérimentez"
            else -> "Découvrez"
        }
        val content = when (position) {
            2 -> "Appropriez-vous une ou plusieurs de ces tendances pour proposer une expérience mobile 5 étoiles ! "
            1 -> "Promenez vous sur l’application et cliquez sur les pastilles pour en apprendre plus"
            else -> "Découvrez nos dix tendances de conception mobile en 2020, présentées  à travers un parcours bancaire."
        }
        val image = when (position) {
            2 -> R.drawable.ic_diamond
            1 -> R.drawable.ic_notification
            else -> R.drawable.ic_loupe
        }
        val buttonTitle = when (position) {
            2 -> "C'est parti !"
            1 -> "Suivant"
            else -> "Suivant"
        }
        tutoButtonTextView.text = buttonTitle
        tutoImageView.setImageResource(image)
        titleTextView.text = title
        contentTextView.text = content
        tutoButtonTextView.setOnClickListener {
            buttonCallback()
        }

    }
}

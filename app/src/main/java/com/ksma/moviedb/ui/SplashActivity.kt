package com.ksma.moviedb.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.ksma.moviedb.MainActivity
import com.ksma.moviedb.R
import com.ksma.moviedb.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var animationDrawable: AnimationDrawable

    private lateinit var tvVersion: TextView
    private lateinit var ctrLayout: ConstraintLayout
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tvVersion = binding.tvVersion
        ctrLayout = binding.ctrLayout

        animationDrawable = ctrLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(1000)
        animationDrawable.setExitFadeDuration(1000)

        if (!animationDrawable.isRunning) {
            animationDrawable.start()
            Handler().postDelayed({
                startActivity(MainActivity.newIntent(this))
                finish()
                overridePendingTransition(
                    R.anim.anim_fade_in, R.anim.anim_fade_out
                )
            }, 1500)
        }

    }

    override fun onPause() {
        super.onPause()
        if (animationDrawable.isRunning) {
            animationDrawable.stop()
        }
    }
}
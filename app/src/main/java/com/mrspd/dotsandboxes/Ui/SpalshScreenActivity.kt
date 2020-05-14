package com.mrspd.dotsandboxes.Ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.mrspd.dotsandboxes.R
import com.mrspd.dotsandboxes.Services.MusicService
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_spalsh_screen.*


class SpalshScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_spalsh_screen)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startService(Intent(this, MusicService::class.java))
        val animation1: Animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        val animation2: Animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation_start)
        val animation3: Animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation_exit)


        start.animation = animation2
        exit.animation = animation3
        Handler().postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this, HomeActivity::class.java)
            startActivity(mainIntent)

            AcTrans.Builder(this).performNoAnimation()
            finish()
        }, 1700)
        imageViewsplash.animation = animation1



    }

}
package com.mrspd.dotsandboxes.Ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.graphics.Typeface.BOLD_ITALIC
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mrspd.dotsandboxes.R
import com.mrspd.dotsandboxes.Services.MusicService
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_home.*


@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {
    @SuppressLint("CheckResult", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)


//////////////////////////////////////////////////
        Toasty.Config.getInstance()
                .setToastTypeface(Typeface.createFromAsset(assets, "font.ttf"))
                .allowQueue(true)
                .apply();
        Toasty.custom(this@HomeActivity, R.string.custom_string, resources.getDrawable(R.drawable.app_icon),
                R.color.highlight1, R.color.green, Toasty.LENGTH_SHORT, false, true).show()
        Toasty.Config.reset()
//////////////////////////////////////////////////
        start.setOnClickListener {
            intent = Intent(applicationContext, ChooseModeAndGridActivity::class.java)
            startActivity(intent)
        }
        exit.setOnClickListener {
            finish()
            stopService(Intent(this, MusicService::class.java))
        }




    }

    private fun getFormattedMessage(): CharSequence? {
        val prefix = "Formatted "
        val highlight = "bold italic"
        val suffix = " text"
        val ssb = SpannableStringBuilder(prefix).append(highlight).append(suffix)
        val prefixLen = prefix.length
        ssb.setSpan(StyleSpan(BOLD_ITALIC),
                prefixLen, prefixLen + highlight.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return ssb
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, MusicService::class.java))
    }
}
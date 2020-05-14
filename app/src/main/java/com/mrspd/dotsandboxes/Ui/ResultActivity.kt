package com.mrspd.dotsandboxes.Ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.mrspd.dotsandboxes.R
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val bundle1 = intent.extras
       val winnername = bundle1!!.getString("winnername").toString()

        tvWinner.text = "Winner is $winnername"
        btHome.setOnClickListener {
            finish()
        }


    }
}

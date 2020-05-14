package com.mrspd.dotsandboxes.Ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mrspd.dotsandboxes.R
import kotlinx.android.synthetic.main.activity_choose_mode_and_grid.*

class ChooseModeAndGridActivity : AppCompatActivity() {
    val bundle: Bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_mode_and_grid)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        var mode = "M"
        var gridsize = "5"
        igsolo.setOnClickListener {
            mode = "Solo"
            gridsize = "5"
            goToNextActivity(gridsize, mode) }
        igmultiplayer.setOnClickListener {
            mode = "MultiPlayer"
            gridSize.visibility = View.VISIBLE
            buttongrid3x3.visibility = View.VISIBLE
            buttongrid4x4.visibility = View.VISIBLE
            buttongrid5x5.visibility = View.VISIBLE
            igChosseMode.visibility = View.GONE
            igsolo.visibility = View.GONE
            igmultiplayer.visibility = View.GONE

            buttongrid3x3.setOnClickListener {
                gridsize = "3"
                goToNextActivity(gridsize, mode)
            }
            buttongrid4x4.setOnClickListener {
                gridsize = "4"
                goToNextActivity(gridsize, mode)

            }
            buttongrid5x5.setOnClickListener {
                gridsize = "5"
                goToNextActivity(gridsize, mode)

            }
        }
    }

    private fun goToNextActivity(gridsize: String, mode: String) {
//                 d("SPD","hna bhai its working")
        intent = Intent(this, SlectPlayersActivity::class.java)
        bundle.putString("Mode", mode)
        bundle.putString("GridSize", gridsize)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()

    }


}

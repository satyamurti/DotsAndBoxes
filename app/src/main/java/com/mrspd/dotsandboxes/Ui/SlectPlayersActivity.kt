package com.mrspd.dotsandboxes.Ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mrspd.dotsandboxes.Adapter.Adapters
import com.mrspd.dotsandboxes.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_slect_players.*


@Suppress("DEPRECATION")
class SlectPlayersActivity : AppCompatActivity() {
    var Mode: String = ""
    var GrideSize: String = ""
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slect_players)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
//////
        Toasty.Config.getInstance()
                .setToastTypeface(Typeface.createFromAsset(assets, "font.ttf"))
                .allowQueue(false)
                .apply();
        Toasty.normal(this, "Select Your Favorite Hero \uD83D\uDE0E ").show()
        Toasty.Config.reset()
// //


        var Hack = 0
        val profilelist = intArrayOf(R.drawable.superhero_1,
                R.drawable.superhero_2, R.drawable.superhero_3, R.drawable.superhero_4,
                R.drawable.superhero_5, R.drawable.superhero_6, R.drawable.superhero_7,
                R.drawable.superhero_8, R.drawable.superhero_9, R.drawable.superhero_10,
                R.drawable.superhero_33, R.drawable.superhero_12, R.drawable.superhero_13,
                R.drawable.superhero_45, R.drawable.superhero_18, R.drawable.superhero_14,
                R.drawable.superhero_35, R.drawable.superhero_19, R.drawable.superhero_15,
                R.drawable.superhero_17, R.drawable.superhero_17, R.drawable.superhero_16)

        val bundle1 = intent.extras
        Mode = bundle1!!.getString("Mode").toString()
        GrideSize = bundle1.getString("GridSize").toString()


        val customAdapter = Adapters(applicationContext, profilelist)
        gridview.adapter = customAdapter
        intent = Intent(this, GameActivity::class.java)
        val bundle: Bundle = Bundle()
        gridview.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            when (Hack) {
                0 -> {

                    val alert = AlertDialog.Builder(this@SlectPlayersActivity)

                    val edittext = EditText(this@SlectPlayersActivity)
                    edittext.hint = "Enter Name"
                    edittext.maxLines = 1


                    val layout = FrameLayout(this@SlectPlayersActivity)

    //set padding in parent layout
                    layout.setPaddingRelative(45, 15, 45, 0)

                    alert.setTitle(title)

                    layout.addView(edittext)

                    alert.setView(layout)

                    alert.setPositiveButton("OK", DialogInterface.OnClickListener {

                        dialog, which ->
                        run {

                            tvname1.visibility = View.VISIBLE
                            tvSuggestion.visibility = View.INVISIBLE
                            val qName = edittext.text.toString()
                            sup1.setImageResource(profilelist[position])
                            tvname1.text = qName
                            bundle.putInt("profileId1", profilelist[position])
                            bundle.putString("player1Name", qName)
                            bundle.putInt("PlayerNum", Hack +1)
                            if(Mode =="Solo"){
                                bundle.putString("Mode", Mode)
                                bundle.putString("GridSize", GrideSize)
                                bundle.putInt("PlayerNum", Hack)
                                intent.putExtras(bundle)
                                startActivity(intent)
                                finish()
                            }

                        }


                    })
                    alert.setNegativeButton("Cancel", DialogInterface.OnClickListener {

                        dialog, which ->
                        run {
                            dialog.dismiss()
                        }

                    })

                    alert.show()
                    Hack =1
                }
                1 -> {
                    val alert = AlertDialog.Builder(this@SlectPlayersActivity)
                    btGo.visibility = View.VISIBLE
                    tvname2.visibility = View.VISIBLE
                    val edittext = EditText(this@SlectPlayersActivity)
                    edittext.hint = "Enter Name"
                    edittext.maxLines = 1

                    val layout = FrameLayout(this@SlectPlayersActivity)

                    layout.setPaddingRelative(45, 15, 45, 0)

                    alert.setTitle(title)

                    layout.addView(edittext)

                    alert.setView(layout)

                    alert.setPositiveButton("OK", DialogInterface.OnClickListener {

                        dialog, which ->
                        run {

                            val qName = edittext.text.toString()
                            sup2.setImageResource(profilelist[position])
                            tvname2.text = qName

                            bundle.putInt("profileId2", profilelist[position])
                            bundle.putString("player2Name", qName)
                            bundle.putInt("PlayerNum", 2)

                        }


                    })
                    alert.setNegativeButton("Cancel", DialogInterface.OnClickListener {

                        dialog, which ->
                        run {
                            dialog.dismiss()
                        }

                    })

                    alert.show()
                    Hack =2
                }
                2 -> {
                    val alert = AlertDialog.Builder(this@SlectPlayersActivity)

                    val edittext = EditText(this@SlectPlayersActivity)
                    edittext.hint = "Enter Name"
                    edittext.maxLines = 1

                    val layout = FrameLayout(this@SlectPlayersActivity)

                    //set padding in parent layout
                    layout.setPaddingRelative(45, 15, 45, 0)

                    alert.setTitle(title)

                    layout.addView(edittext)

                    alert.setView(layout)

                    alert.setPositiveButton("OK", DialogInterface.OnClickListener {

                        dialog, which ->
                        run {

                            val qName = edittext.text.toString()
                            sup3.setImageResource(profilelist[position])
                            tvname3.visibility = View.VISIBLE
                            tvname3.text = qName

                            bundle.putInt("profileId3", profilelist[position])
                            bundle.putString("player3Name", qName)
                            bundle.putInt("PlayerNum", 3)
                        }


                    })
                    alert.setNegativeButton("Cancel", DialogInterface.OnClickListener {

                        dialog, which ->
                        run {
                            dialog.dismiss()
                        }

                    })

                    alert.show()
                    Hack =3

                }
                3 -> {
                    val alert = AlertDialog.Builder(this@SlectPlayersActivity)

                    val edittext = EditText(this@SlectPlayersActivity)
                    edittext.hint = "Enter Name"
                    edittext.maxLines = 1

                    val layout = FrameLayout(this@SlectPlayersActivity)

                    //set padding in parent layout
                    layout.setPaddingRelative(45, 15, 45, 0)

                    alert.setTitle(title)

                    layout.addView(edittext)

                    alert.setView(layout)

                    alert.setPositiveButton("OK", DialogInterface.OnClickListener {

                        dialog, which ->
                        run {

                            val qName = edittext.text.toString()
                            sup4.setImageResource(profilelist[position])
                            tvname4.visibility = View.VISIBLE
                            tvname4.text = qName

                            bundle.putInt("profileId4", profilelist[position])
                            bundle.putString("player4Name", qName)
                            bundle.putString("Mode", Mode)
                            bundle.putString("GridSize", GrideSize)
                            bundle.putInt("PlayerNum", 4)
                            intent.putExtras(bundle)
                            startActivity(intent)
                            finish()
                        }


                    })
                    alert.setNegativeButton("Cancel", DialogInterface.OnClickListener {

                        dialog, which ->
                        run {
                            dialog.dismiss()
                        }

                    })

                    alert.show()


                }
            }

            btGo.setOnClickListener {
                bundle.putString("Mode", Mode)
                bundle.putString("GridSize", GrideSize)
                bundle.putInt("PlayerNum", Hack)
                intent.putExtras(bundle)
                startActivity(intent)
                finish()

            }
        }
    }
}
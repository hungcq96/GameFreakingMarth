package com.hung.freakingmath

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import com.hung.freakingmath.databinding.ActivityMainBinding
import com.hung.freakingmath.databinding.ActivityPlayBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var random = Random()
    private var soA = 0
    private var soB = 0
    private var total = 0
    private var score = 0
    private var currentProcess = 0


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        binding.tvName.text = "Hello $name"

        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_itemsacle)

        binding.imCheck.startAnimation(animation)
        binding.imFalse.startAnimation(animation)

        play()

        binding.imCheck.setOnClickListener {
            if (soA + soB == total) {
                score++
                binding.tvScore.text.toString()
                play()
            } else {
                currentProcess = 0
                showDialog()
            }
        }

        binding.imFalse.setOnClickListener {
            if (soA + soB != total) {
                score++
                binding.tvScore.text.toString()
                play()
            } else {
                currentProcess = 0
                showDialog()
            }
        }
    }
    private fun play() {
        soA = random.nextInt(10)
        soB = random.nextInt(10)

        val resultIndex = random.nextInt(2)

        binding.tvSoA.text = soA.toString()
        binding.tvSoB.text = soB.toString()
        if (resultIndex == 0){
            total = random.nextInt(10)
            binding.tvResult.text = total.toString()
        }else{
            total = soA+soB
            binding.tvResult.text = total.toString()
        }
        currentProcess = binding.progressBar.max

        Thread{
            while (currentProcess>0){
                runOnUiThread {
                    currentProcess -= 5
                    binding.progressBar.progress = currentProcess
                }
                Thread.sleep(2000)
            }
            runOnUiThread{
                showDialog()
            }
        }.start()
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog)

        val tvScore = dialog.findViewById(R.id.tv_score_dialog) as TextView
        val btnPlayAgain = dialog.findViewById(R.id.btn_play_again) as Button

        tvScore.text = "Your Score : $score"
        btnPlayAgain.setOnClickListener {
            val intent = Intent(this, ActivityPlayBinding::class.java)
            startActivity(intent)
        }

        dialog.setCancelable(false)
        dialog.show()
    }
}
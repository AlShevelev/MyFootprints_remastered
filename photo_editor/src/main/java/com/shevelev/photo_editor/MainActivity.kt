package com.shevelev.photo_editor

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shevelev.photo_editor.cross_activity_communication.CrossActivityCommunicator
import com.shevelev.photo_editor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CrossActivityCommunicator.bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_test_port)

        binding.brightnessButton.setOnClickListener {
            startActivityForResult(Intent(this, BrightnessActivity::class.java), BrightnessActivity.REQUEST)
        }

        binding.contrastButton.setOnClickListener {
            startActivityForResult(Intent(this, ContrastActivity::class.java), ContrastActivity.REQUEST)
        }

        binding.saturationButton.setOnClickListener {
            startActivityForResult(Intent(this, SaturationActivity::class.java), SaturationActivity.REQUEST)
        }

        binding.whiteBalanceButton.setOnClickListener {
            startActivityForResult(Intent(this, WhiteBalanceActivity::class.java), WhiteBalanceActivity.REQUEST)
        }

        binding.combinedButton.setOnClickListener {
            startActivityForResult(Intent(this, CombinedActivity::class.java), CombinedActivity.REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK) {
            return
        }

        when(requestCode) {
            BrightnessActivity.REQUEST -> {
                binding.resultImage.setImageBitmap(CrossActivityCommunicator.bitmap)
            }
            ContrastActivity.REQUEST -> {
                binding.resultImage.setImageBitmap(CrossActivityCommunicator.bitmap)
            }
            SaturationActivity.REQUEST -> {
                binding.resultImage.setImageBitmap(CrossActivityCommunicator.bitmap)
            }
            WhiteBalanceActivity.REQUEST -> {
                binding.resultImage.setImageBitmap(CrossActivityCommunicator.bitmap)
            }
            CombinedActivity.REQUEST -> {
                binding.resultImage.setImageBitmap(CrossActivityCommunicator.bitmap)
            }
        }
    }
}

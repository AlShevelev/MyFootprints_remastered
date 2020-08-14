package com.shevelev.photo_editor

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shevelev.photo_editor.cross_activity_communication.CrossActivityCommunicator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CrossActivityCommunicator.bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_test_port)

        brightnessButton.setOnClickListener {
            startActivityForResult(Intent(this, BrightnessActivity::class.java), BrightnessActivity.REQUEST)
        }

        contrastButton.setOnClickListener {
            startActivityForResult(Intent(this, ContrastActivity::class.java), ContrastActivity.REQUEST)
        }

        saturationButton.setOnClickListener {
            startActivityForResult(Intent(this, SaturationActivity::class.java), SaturationActivity.REQUEST)
        }

        whiteBalanceButton.setOnClickListener {
            startActivityForResult(Intent(this, WhiteBalanceActivity::class.java), WhiteBalanceActivity.REQUEST)
        }

        combinedButton.setOnClickListener {
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
                resultImage.setImageBitmap(CrossActivityCommunicator.bitmap)
            }
            ContrastActivity.REQUEST -> {
                resultImage.setImageBitmap(CrossActivityCommunicator.bitmap)
            }
            SaturationActivity.REQUEST -> {
                resultImage.setImageBitmap(CrossActivityCommunicator.bitmap)
            }
            WhiteBalanceActivity.REQUEST -> {
                resultImage.setImageBitmap(CrossActivityCommunicator.bitmap)
            }
            CombinedActivity.REQUEST -> {
                resultImage.setImageBitmap(CrossActivityCommunicator.bitmap)
            }
        }
    }
}

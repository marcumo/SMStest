package com.marcoarturo.smstest

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val requestReceiveSms = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.RECEIVE_SMS)
        != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.RECEIVE_SMS),
                requestReceiveSms)

        }

    }


}

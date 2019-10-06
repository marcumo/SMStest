package com.marcoarturo.smstest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.widget.Toast
import okhttp3.*
import java.io.IOException

class SmsReceiver : BroadcastReceiver() {

    private val client = OkHttpClient()

    override fun onReceive(context: Context, intent: Intent) {

        val extras = intent.extras

        if (extras != null) {
            val sms = extras.get("pdus") as Array<Any>

            for (i in sms.indices) {
                val format = extras.getString("format")

                var smsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    SmsMessage.createFromPdu(sms[i] as ByteArray, format)
                } else {
                    SmsMessage.createFromPdu(sms[i] as ByteArray)
                }

                val phoneNumber = smsMessage.originatingAddress
                val messageText = smsMessage.messageBody.toString()


                //val url = "67.205.146.198:8085/gis/app/notificaciones"
                //val resp = firefly.httpClient().get("$url?celular=$phoneNumber&body=$messageText").asyncSubmit()
                //val stat = resp.status
                Toast.makeText(
                    context,
                    "phoneNumber: $phoneNumber\n" +
                            "messageText: $messageText",
                    Toast.LENGTH_SHORT
                ).show()

                //-------------------------------------------

                run("http://67.205.146.198:8085/gis/app/notificaciones?celular=$phoneNumber&body=$messageText")

            }
        }
    }
    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })
    }
}
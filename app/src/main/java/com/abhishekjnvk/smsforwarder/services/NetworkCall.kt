package com.abhishekjnvk.smsforwarder.services

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException


class NetworkCall  {
    fun saveToServer(from:String,content:String,backendurl:String){
        Log.d(  "CUSTOM_LOG", "Sending Message to "+backendurl)
        Thread {
            val endPoint= "$backendurl/v1/sms"
            val client = OkHttpClient().newBuilder()
                .build()
            val mediaType: MediaType? = "application/x-www-form-urlencoded".toMediaTypeOrNull()
            val body = RequestBody.create(mediaType, "content=$content&from=$from")
            val request: Request = Request.Builder()
                .url(endPoint)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    Log.d("CUSTOM_LOG","Message Sent")
                }
            })
        }.start()

    }
}
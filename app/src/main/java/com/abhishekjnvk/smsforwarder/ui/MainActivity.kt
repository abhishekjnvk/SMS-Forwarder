package com.abhishekjnvk.smsforwarder.ui

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.abhishekjnvk.smsforwarder.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val editTextBackend = findViewById<EditText>(R.id.backend_url)
        val backendUrl = sharedPreferences.getString("backendURL", "https://abhishekjnvk.loca.lt")
        editTextBackend.setText(backendUrl)
        val saveUrlBtn=findViewById<Button>(R.id.save_btn);

        requestPermissions(
            arrayOf(
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS
            ), 0
        )

        saveUrlBtn.setOnClickListener{
            val url=editTextBackend.text
            sharedPreferences.edit().putString("backendURL", url.toString()).apply()
            Toast.makeText(this, "Backend URL: $url", Toast.LENGTH_SHORT).show()
            Log.d("CUSTOM_LOG", "BACKEND URL: $url")
        }

    }
}
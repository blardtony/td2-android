package com.example.tuto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textIntent : TextView = findViewById(R.id.intentId);
        textIntent.text = intent.getStringExtra("MESSAGE");
    }
}
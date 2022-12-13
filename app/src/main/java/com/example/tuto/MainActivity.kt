package com.example.tuto

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sign

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var step : Int = 1
        var counter: Int = 0
        val counterText : TextView = findViewById(R.id.textCount)
        counterText.text = counter.toString()

        val counterButton : Button = findViewById(R.id.click)

        counterButton.setOnClickListener{
            counter = counter.plus(step)
            counterText.text = counter.toString()
        }

        val resetButton : Button = findViewById(R.id.reset)

        resetButton.setOnClickListener {
            counter = 0
            counterText.text = counter.toString()
        }
        val reverseButton : Button = findViewById(R.id.reverse)

        reverseButton.setOnClickListener {
            step.sign
        }

        val goToButton : Button = findViewById(R.id.redirect)

        goToButton.setOnClickListener{
            val explicitIntent : Intent = Intent(this@MainActivity, SecondActivity::class.java)
            explicitIntent.putExtra("MESSAGE", "Test message")
            startActivity(explicitIntent)
        }

        val openUrlButton : Button = findViewById(R.id.openUrl)
        val baseUrl : String = "https://jsonplaceholder.typicode.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        openUrlButton.setOnClickListener {
            val retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(jsonConverter)
                .build()
            val service = retrofitClient.create(WebServiceInterface::class.java)

            val callback : Callback<List<TodoModel>> = object : Callback<List<TodoModel>> {
                override fun onResponse(
                    call: Call<List<TodoModel>>,
                    response: Response<List<TodoModel>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { newData ->
                            Toast.makeText(
                                this@MainActivity,
                                "Nb todos = ${newData.size}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else {
                        Log.d("Response not successful", "")
                    }
                }

                override fun onFailure(call: Call<List<TodoModel>>, t: Throwable) {
                    Log.d("xxx", "Webservice error : " + t.message)
                }

            }

            service.getAllTodos().enqueue(callback)
        }
    }
}
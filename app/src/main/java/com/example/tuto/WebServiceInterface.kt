package com.example.tuto

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceInterface {

    @GET("todos")
    fun getAllTodos() : Call<List<TodoModel>>

    @GET("todos")
    fun getAllTodosByUser(@Query("userId") userId : Int) : Call<List<TodoModel>>
}
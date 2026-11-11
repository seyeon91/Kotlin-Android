package com.androidbook.helloworld

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        scopeLet()
        scopeRun()
        scopeWith()
        scopeApply()
        scopeAlso()
    }

    fun scopeLet(){
        val name: String? = "Kotlin"
        name?.let{
            println("이름의 길이는 ${it.length}입니다.")
        }
    }

    fun scopeRun(){
        val name: String? = "Hello, Kotlin"
        val messageLength = name?.run{
           println("이름은 $this 입니다.")
            length
        }
        println("길이: $messageLength")
    }

    fun scopeWith(){
        val result = with(StringBuilder()){
            append("Hello, ")
            append("Kotlin!")
            toString()
        }
        println(result)
    }

    fun scopeApply() {
        val person = Person().apply {
            name = "John"
            age = 25
        }
        println(person.name)
    }

    fun scopeAlso(){
        val number = mutableListOf(1, 2, 3).also{
            println("리스트의 크기: ${it.size}")
        }
        println(number)
    }
}

class Person {
    var name: String = ""
    var age: Int = 0
}
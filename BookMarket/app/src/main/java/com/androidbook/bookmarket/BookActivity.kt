package com.androidbook.bookmarket

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookActivity : AppCompatActivity() {

    lateinit var imgObj: ImageView
    lateinit var bookidObj: TextView
    lateinit var nameObj: TextView
    lateinit var priceObj: TextView
    lateinit var dateObj: TextView
    lateinit var writerObj: TextView
    lateinit var descriptionObj: TextView
    lateinit var categoryObj: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.book)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgObj = findViewById(R.id.imageView1)
        bookidObj = findViewById(R.id.book_tv01)
        nameObj = findViewById(R.id.book_tv02)
        priceObj = findViewById(R.id.book_tv03)
        dateObj = findViewById(R.id.book_tv04)
        writerObj = findViewById(R.id.book_tv05)
        descriptionObj = findViewById(R.id.book_tv06)
        categoryObj = findViewById(R.id.book_tv07)

        val intent = getIntent()
        val bookid = intent.getStringExtra("bookid")
        bookidObj.text = bookid
        nameObj.text = intent.getStringExtra("name")
        priceObj.text = intent.getStringExtra("price")
        dateObj.text = intent.getStringExtra("date")
        writerObj.text = intent.getStringExtra("writer")
        descriptionObj.text = intent.getStringExtra("description")
        categoryObj.text = intent.getStringExtra("category")

        when (bookid) {
            "BOOK1234" -> imgObj.setImageResource(R.drawable.books_book1)
            "BOOK1235" -> imgObj.setImageResource(R.drawable.books_book2)
            "BOOK1236" -> imgObj.setImageResource(R.drawable.books_book3)
            "BOOK1237" -> imgObj.setImageResource(R.drawable.books_book4)
        }
    }
}
package com.androidbook.bookmarket.customerinquiry

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.androidbook.bookmarket.R

class DetailBoardActivity : AppCompatActivity() {

    private lateinit var tvTitleObj: TextView
    private lateinit var tvContentObj: TextView
    private lateinit var tvUserIDObj: TextView
    private lateinit var tvDateObj: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.boarddetail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        tvTitleObj = findViewById(R.id.textView_Title)
        tvUserIDObj = findViewById(R.id.textView_UserID)
        tvContentObj = findViewById(R.id.textView_Content)
        tvDateObj = findViewById(R.id.textView_Date)

        tvTitleObj.text = intent.getStringExtra("title")
        tvUserIDObj.text = intent.getStringExtra("userid")
        tvContentObj.text = intent.getStringExtra("content")
        tvDateObj.text = intent.getStringExtra("date")

        button = findViewById(R.id.button)
        button.setOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
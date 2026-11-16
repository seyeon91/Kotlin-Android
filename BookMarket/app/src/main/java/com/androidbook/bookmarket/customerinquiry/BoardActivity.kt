package com.androidbook.bookmarket.customerinquiry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.androidbook.bookmarket.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class BoardActivity : AppCompatActivity() {

    private lateinit var btWriteObj: Button
    private lateinit var listView: ListView
    private lateinit var adapter: MyAdapter
    private val itemList = ArrayList<MyItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.board)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        btWriteObj = findViewById(R.id.writeButton)
        listView = findViewById(R.id.listView)

        btWriteObj.setOnClickListener {
            val pref = getSharedPreferences("prefile", Context.MODE_PRIVATE)
            val userId = pref.getString("id", null)
            if (userId == null) {
                Toast.makeText(applicationContext, "로그인해 주세요", Toast.LENGTH_SHORT).show()
            }
        }

        adapter = MyAdapter(itemList)
        listView.adapter = adapter

        itemListJson()

        listView.setOnItemClickListener { adapterView, _, position, _ ->
            val itemValue = adapterView.getItemAtPosition(position) as MyItem
            val intent = Intent(applicationContext, DetailBoardActivity::class.java)
            intent.putExtra("title", itemValue.title)
            intent.putExtra("userid", itemValue.id)
            intent.putExtra("content", itemValue.content)
            intent.putExtra("date", itemValue.date)
            startActivity(intent)
        }
    }

    private fun itemListJson() {
        val url = "http://10.0.2.2/selectBoard.php"
        val request = Request.Builder().url(url).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "서버 연결 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string() ?: ""
                runOnUiThread {
                    if (!response.isSuccessful) {
                        Toast.makeText(applicationContext, "네트워크 문제 발생", Toast.LENGTH_SHORT).show()
                        return@runOnUiThread
                    }
                    try {
                        val jsonObject = JSONObject(body)
                        if (jsonObject.optInt("success") != 1) {
                            Toast.makeText(applicationContext, "게시글이 없습니다", Toast.LENGTH_SHORT).show()
                            return@runOnUiThread
                        }
                        val jsonArray = jsonObject.getJSONArray("result")
                        itemList.clear()
                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            itemList.add(
                                MyItem(
                                    obj.getInt("no"),
                                    obj.getString("id"),
                                    obj.getString("title"),
                                    obj.getString("content"),
                                    obj.getString("date")
                                )
                            )
                        }
                        adapter.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        Toast.makeText(applicationContext, "응답 처리 오류", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
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
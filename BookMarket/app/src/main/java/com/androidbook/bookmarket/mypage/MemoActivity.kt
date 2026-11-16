package com.androidbook.bookmarket.mypage

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SearchView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.androidbook.bookmarket.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MemoActivity : AppCompatActivity() {

    lateinit var db: DatabaseHelperMemo
    var selectItemPos: Int = -1
    lateinit var lv: ListView
    lateinit var adapter: SimpleAdapter
    lateinit var memoList: ArrayList<HashMap<String, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.memo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        db = DatabaseHelperMemo(this)
        lv = findViewById(R.id.memo_list)
        val insert: Button = findViewById(R.id.btnInsert)
        val update: Button = findViewById(R.id.btnUpdate)
        val delete: Button = findViewById(R.id.btnDelete)

        readData()

        lv.setOnItemClickListener { _, _, position, _ ->
            selectItemPos = position
        }

        insert.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.activity_memo_dialog, null)
            builder.setView(dialogView)
            val dialog = builder.show()

            val dlginsert: Button = dialogView.findViewById(R.id.insert)
            val dlgcancel: Button = dialogView.findViewById(R.id.cancel)

            dlginsert.setOnClickListener {
                val title: EditText = dialogView.findViewById(R.id.title)
                val content: EditText = dialogView.findViewById(R.id.content)
                db.insertData(today(), title.text.toString(), content.text.toString())
                dialog.dismiss()
                readData()
            }
            dlgcancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        delete.setOnClickListener {
            if (selectItemPos < 0 || selectItemPos >= memoList.size) {
                Toast.makeText(applicationContext, "항목을 선택하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val alertDialog = android.app.AlertDialog.Builder(this@MemoActivity)
            alertDialog.setTitle("메모장 삭제")
            alertDialog.setMessage("선택한 메모를 삭제하겠습니까?")
            alertDialog.setIcon(R.drawable.icon_cart)

            val listener = DialogInterface.OnClickListener { dialogInterface, i ->
                when (i) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        val id: Int = memoList[selectItemPos]["id"].toString().toInt()
                        db.deleteData(id)
                        readData()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> dialogInterface.cancel()
                }
            }
            alertDialog.setPositiveButton("예", listener)
            alertDialog.setNegativeButton("아니요", listener)
            alertDialog.show()
        }

        update.setOnClickListener {
            if (selectItemPos < 0 || selectItemPos >= memoList.size) {
                Toast.makeText(applicationContext, "항목을 선택하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.activity_memo_dialog, null)
            builder.setView(dialogView)

            val dlgtitle: EditText = dialogView.findViewById(R.id.title)
            val dlgcontent: EditText = dialogView.findViewById(R.id.content)
            val dlginsert: Button = dialogView.findViewById(R.id.insert)
            val dlgcancel: Button = dialogView.findViewById(R.id.cancel)
            dlginsert.text = "수정"

            dlgtitle.setText(memoList[selectItemPos]["title"].toString())
            dlgcontent.setText(memoList[selectItemPos]["content"].toString())

            val dialog = builder.show()

            dlginsert.setOnClickListener {
                val id: Int = memoList[selectItemPos]["id"].toString().toInt()
                db.updateData(today(), dlgtitle.text.toString(), dlgcontent.text.toString(), id)
                dialog.dismiss()
                readData()
            }
            dlgcancel.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    fun today(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    fun readData() {
        memoList = db.readData()
        showList()
    }

    fun showList() {
        adapter = SimpleAdapter(
            this@MemoActivity,
            memoList,
            R.layout.activity_memo_item,
            arrayOf("date", "title", "content"),
            intArrayOf(R.id.tvDate, R.id.tvTitle, R.id.tvContent)
        )
        lv.adapter = adapter
        selectItemPos = -1
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "검색어를 입력하세요"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                memoList = db.searchReadData(query.toString())
                showList()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                readData()
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}
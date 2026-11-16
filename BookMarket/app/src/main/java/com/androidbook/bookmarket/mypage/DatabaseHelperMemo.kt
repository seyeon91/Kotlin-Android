package com.androidbook.bookmarket.mypage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelperMemo(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        const val DATABASE_NAME = "Memo.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "MemoTABLE"

        const val KEY_ID = "id"
        const val KEY_DATE = "date"
        const val KEY_TITLE = "title"
        const val KEY_CONTENT = "content"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_DATE + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT" + ")")
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(p0)
    }

    fun insertData(date: String, title: String, content: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_DATE, date)
        cv.put(KEY_TITLE, title)
        cv.put(KEY_CONTENT, content)
        db.insert(TABLE_NAME, null, cv)
    }

    fun readData(): ArrayList<HashMap<String, String>> {
        val db = this.writableDatabase
        val memoList: ArrayList<HashMap<String, String>> = ArrayList()
        val cursor = db.rawQuery("SELECT id, date, title, content FROM " + TABLE_NAME, null)
        while (cursor.moveToNext()) {
            val memo: HashMap<String, String> = HashMap()
            memo["id"] = cursor.getInt(0).toString()
            memo["date"] = cursor.getString(1)
            memo["title"] = cursor.getString(2)
            memo["content"] = cursor.getString(3)
            memoList.add(memo)
        }
        cursor.close()
        return memoList
    }

    fun updateData(date: String, title: String, content: String, id: Int): Int {
        val db = this.writableDatabase
        val cVals = ContentValues()
        cVals.put(KEY_DATE, date)
        cVals.put(KEY_TITLE, title)
        cVals.put(KEY_CONTENT, content)
        return db.update(TABLE_NAME, cVals, "$KEY_ID = ?", arrayOf(id.toString()))
    }

    fun deleteData(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(id.toString()))
    }

    fun deleteAllData() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    fun searchReadData(content: String): ArrayList<HashMap<String, String>> {
        val db = this.writableDatabase
        val memoList: ArrayList<HashMap<String, String>> = ArrayList()
        val cursor = db.query(TABLE_NAME, null, "$KEY_CONTENT LIKE ?", arrayOf("%$content%"), null, null, null)
        while (cursor.moveToNext()) {
            val memo: HashMap<String, String> = HashMap()
            memo["id"] = cursor.getString(0)
            memo["date"] = cursor.getString(1)
            memo["title"] = cursor.getString(2)
            memo["content"] = cursor.getString(3)
            memoList.add(memo)
        }
        cursor.close()
        return memoList
    }
}
package com.androidbook.bookmarket.mypage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.androidbook.bookmarket.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import java.io.File
import java.io.FileOutputStream

class MyPageActivity : AppCompatActivity() {

    lateinit var memoObj: Button

    lateinit var profileImg: ImageView
    private val profileFileName = "profile.jpg"

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            saveProfile(bitmap)
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            contentResolver.openInputStream(uri)?.use { input ->
                val bitmap = BitmapFactory.decodeStream(input)
                if (bitmap != null) saveProfile(bitmap)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mypage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        memoObj = findViewById(R.id.meno)
        memoObj.setOnClickListener {
            val intent = Intent(this, MemoActivity::class.java)
            startActivity(intent)
        }

        profileImg = findViewById(R.id.loginimg)
        val profileBtn: ImageButton = findViewById(R.id.btnProfile)
        loadProfile()

        profileBtn.setOnClickListener {
            showProfileDialog()
        }
    }

    fun showProfileDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_profile, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()

        val layoutCamera: View = dialogView.findViewById(R.id.layoutCamera)
        val layoutGallery: View = dialogView.findViewById(R.id.layoutGallery)

        layoutCamera.setOnClickListener {
            dialog.dismiss()
            cameraLauncher.launch(null)
        }
        layoutGallery.setOnClickListener {
            dialog.dismiss()
            galleryLauncher.launch("image/*")
        }
    }

    fun saveProfile(bitmap: Bitmap) {
        val scaled = resizeBitmap(bitmap, 600)
        val file = File(filesDir, profileFileName)
        FileOutputStream(file).use { out ->
            scaled.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        profileImg.setImageBitmap(scaled)
    }

    fun loadProfile() {
        val file = File(filesDir, profileFileName)
        if (file.exists()) {
            profileImg.setImageBitmap(BitmapFactory.decodeFile(file.absolutePath))
        }
    }

    fun resizeBitmap(bitmap: Bitmap, maxSize: Int): Bitmap {
        val ratio = maxOf(bitmap.width, bitmap.height).toFloat() / maxSize
        if (ratio <= 1f) return bitmap
        return Bitmap.createScaledBitmap(bitmap, (bitmap.width / ratio).toInt(), (bitmap.height / ratio).toInt(), true)
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
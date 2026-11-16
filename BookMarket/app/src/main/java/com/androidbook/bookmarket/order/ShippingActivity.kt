package com.androidbook.bookmarket.order

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.androidbook.bookmarket.R
import com.androidbook.bookmarket.cart.CartActivity
import java.util.Calendar

class ShippingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val etName: EditText = findViewById(R.id.editTextName)
        val etDate: EditText = findViewById(R.id.editTextDate)
        val etPhone: EditText = findViewById(R.id.editTextPhone)
        val etZipcode: EditText = findViewById(R.id.editTextZipcode)
        val etAddress: EditText = findViewById(R.id.editTextAddress)

        val btCancel: Button = findViewById(R.id.buttonCancel)
        val btSubmit: Button = findViewById(R.id.buttonSubmit)

        etDate.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            val listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                val date = y.toString() + "년 " + (m + 1) + "월 " + d.toString() + "일"
                etDate.setText(date)
            }

            val pickerDialog = DatePickerDialog(this, listener, year, month, day)
            pickerDialog.setTitle("DatePickerDialog")
            pickerDialog.show()
        }

        btSubmit.setOnClickListener {
            val intent = Intent(this@ShippingActivity, OrderActivity::class.java)
            intent.putExtra("name", etName.text.toString())
            intent.putExtra("date", etDate.text.toString())
            intent.putExtra("phone", etPhone.text.toString())
            intent.putExtra("zipcode", etZipcode.text.toString())
            intent.putExtra("address", etAddress.text.toString())
            startActivity(intent)
        }

        btCancel.setOnClickListener {
            val intent = Intent(this@ShippingActivity, CartActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
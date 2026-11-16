package com.androidbook.bookmarket.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidbook.bookmarket.R
import com.androidbook.bookmarket.model.Book
import com.androidbook.bookmarket.model.CartRepository.cartBooks

class OrderAdapter(private val context: Context) : RecyclerView.Adapter<OrderAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var orderTitle: TextView = itemView.findViewById(R.id.title)
        var orderid: TextView = itemView.findViewById(R.id.id)
        var orderPrice: TextView = itemView.findViewById(R.id.price)
        var orderQuantity: TextView = itemView.findViewById(R.id.quantity)
        var orderSum: TextView = itemView.findViewById(R.id.sum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_order_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val book: Book = cartBooks[position]
        holder.orderTitle.text = book.name
        holder.orderid.text = book.bookid
        holder.orderPrice.text = book.price.toString()
        holder.orderQuantity.text = book.quantity.toString()
        holder.orderSum.text = (book.price * book.quantity).toString()
    }

    override fun getItemCount(): Int {
        return cartBooks.size
    }
}
package com.androidbook.bookmarket.model

object CartRepository {

    val cartBooks: ArrayList<Book> = ArrayList<Book>()

    fun addCartItems(book: Book) {
        var cnt = 0
        for (i in 0 until cartBooks.size) {
            val goodsQnt = cartBooks[i]
            if (goodsQnt.bookid == book.bookid) {
                cnt++
                goodsQnt.quantity += 1
            }
        }
        if (cnt == 0) {
            book.quantity = 1
            book.isCheck = true
            cartBooks.add(book)
        }
    }

    fun countCartItems(): Int {
        var totalQuantity = 0
        for (i in cartBooks.indices) {
            totalQuantity += cartBooks[i].quantity
        }
        return totalQuantity
    }

    fun grandTotalCartItems(): Int {
        var totalPrice = 0
        for (i in cartBooks.indices) {
            if (cartBooks[i].isCheck)
                totalPrice += cartBooks[i].price * cartBooks[i].quantity
        }
        return totalPrice
    }
}
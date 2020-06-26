package com.example.testapp

import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("Order")
class Order: ParseObject() {

    companion object ShoppingCart {

        var products = arrayListOf<Product>()
        var amount: Int = 0
        private val order: ParseObject = ParseObject.create("Order")

        fun addToOrder(product: Product)
        {
            products.add(product)
            amount += product.getInt("price")
        }

        fun delFromOrder(product: Product)
        {
            products.remove(product)
            amount -= product.getInt("price")
        }

        fun saveOrderAsTitle()
        {
            val productsTitle = products.map {it.getString("title")}
            order.put("amount", amount)
            order.put("products", productsTitle)
            order.saveInBackground()
        }

        fun saveOrderAsJSON()
        {
            order.put("amount", amount)
            order.put("products", products)
            order.saveInBackground()
        }
    }
}
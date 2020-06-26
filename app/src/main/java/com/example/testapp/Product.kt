package com.example.testapp

import android.util.Log
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.parse.*

@ParseClassName("Product")
class Product: ParseObject() {

    companion object ProductFun{

        fun getProductList(): ArrayList<Product>
        {
            var products = ArrayList<Product>()

            val query = ParseQuery.getQuery<Product>("Product")
            products = query.find() as ArrayList<Product>

            return products
        }

        fun refreshProducts(dataSet: ArrayList<Product>, adapter: RecyclerView.Adapter<*>)
        {
            var products = ArrayList<Product>()

            ParseQuery.getQuery(Product::class.java)
                .fromNetwork()
                .setLimit(100)
                .findInBackground(FindCallback { results, error ->

                    if (error == null)
                    {
                        dataSet.clear()
                        dataSet.addAll(results)
                        adapter.notifyDataSetChanged()
                    }
                    else
                    {
                        Log.d( "refreshAlert","getAllSubscriptions() :: callback error (" + error.code + ") = " + error.localizedMessage)
                    }

                })

        }
    }
}
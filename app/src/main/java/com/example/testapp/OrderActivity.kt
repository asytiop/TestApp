package com.example.testapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataSet: ArrayList<Product>
    private lateinit var totalView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_menu)

        dataSet = Order.products
        viewAdapter = OrderAdapter(dataSet)
        viewManager = LinearLayoutManager(this)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_cart).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        totalView = findViewById<TextView>(R.id.textTotal)
        totalView.text = Order.amount.toString()

        val fabCart: View = findViewById(R.id.fabCart)
        fabCart.setOnClickListener { view ->
            Order.saveOrderAsTitle()
            Toast.makeText(this, "Titres sauvegardés", Toast.LENGTH_SHORT).show()
        }

        val fabCartJSON: View = findViewById(R.id.fabCartJSON)
        fabCartJSON.setOnClickListener { view ->
            Order.saveOrderAsJSON()
            Toast.makeText(this, "Produits sauvegardés", Toast.LENGTH_SHORT).show()
        }
    }

    val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT)
    {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            Order.delFromOrder(dataSet[viewHolder.adapterPosition])
            this@OrderActivity.runOnUiThread { totalView.text = Order.amount.toString() }
            viewAdapter.notifyItemRemoved(viewHolder.adapterPosition)
        }

    }
}
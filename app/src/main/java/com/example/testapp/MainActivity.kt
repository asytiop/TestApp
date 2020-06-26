package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.Parse
import com.parse.ParseObject
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataSet: ArrayList<Product>
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ParseObject.registerSubclass(Product::class.java)
        ParseObject.registerSubclass(Order::class.java)

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id)) // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .enableLocalDataStore()
                .build()
        )

        this.toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(this.toolbar)

        dataSet = Product.getProductList()

        viewManager = LinearLayoutManager(this)
        viewAdapter = MainAdapter(dataSet)
        viewAdapter.notifyDataSetChanged()

        val itemDecor = DividerItemDecoration(this, (viewManager as LinearLayoutManager).orientation)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
            addItemDecoration(itemDecor)
        }

        swipeRefreshLayout.setOnRefreshListener{
            fetchProducts()
            swipeRefreshLayout.isRefreshing = false
        }


    }

    private fun fetchProducts()
    {
        swipeRefreshLayout.isRefreshing = true
        Product.refreshProducts(dataSet, viewAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.btns_appbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when(item.itemId){
            R.id.cart_btn ->{
                val intent = Intent(this, OrderActivity::class.java)
                this.startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
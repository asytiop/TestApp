package com.example.testapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.parse.ParseFile

class DetailProductActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var imgView: ImageView
    private lateinit var txtContent: TextView
    private lateinit var fab: View

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produit)



        val product: Product? = intent.extras?.getParcelable("selected_product")

        title = findViewById(R.id.textDetail)
        imgView = findViewById(R.id.imageView)
        txtContent = findViewById(R.id.textContent)
        fab = findViewById(R.id.fab)

        title.text = product?.getString("title")
        txtContent.text = product!!.get("content") as String
        val img: ParseFile? = product.getParseFile("picture")
        val imgUrl: String? = img?.url
        val imgUri : Uri = Uri.parse(imgUrl)
        Glide.with(this)
            .load(imgUri)
            .placeholder(R.drawable.ic_baseline_block_24)
            .into(imgView)


        fab.setOnClickListener {
            Order.addToOrder(product)
            Toast.makeText(this, "Ajouté au panier", Toast.LENGTH_SHORT).show()
            Log.d("addCart", Order.products[0].getString("title")!!)
        }

    }
}
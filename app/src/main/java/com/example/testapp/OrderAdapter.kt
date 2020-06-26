package com.example.testapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.parse.ParseFile

class OrderAdapter(private val dataSet: ArrayList<Product>): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        val textView: TextView
        val imgView: ImageView

        init
        {
            textView = v.findViewById(R.id.cart_item_text) as TextView
            imgView = v.findViewById(R.id.cart_item_image) as ImageView
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder
    {
        // Create a new view.
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cart_recycle_item, viewGroup, false)

        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
    {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.textView.text = dataSet[position].getString("title")

        val img: ParseFile? = dataSet[position].getParseFile("picture")
        val imgUrl: String? = img?.url
        val imgUri : Uri = Uri.parse(imgUrl)
        Glide.with(viewHolder.itemView.context)
            .load(imgUri)
            .placeholder(R.drawable.ic_baseline_block_24)
            .into(viewHolder.imgView)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
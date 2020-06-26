package com.example.testapp

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.parse.ParseFile

class MainAdapter(private val dataSet: ArrayList<Product>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textView: TextView
        val imgView: ImageView
        val cl : ConstraintLayout = v.findViewById(R.id.itemLayout)

        init {
            textView = v.findViewById(R.id.recycle_item_text)
            imgView = v.findViewById(R.id.recycle_item_image)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycle_item, viewGroup, false)

        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.textView.text = dataSet[position].getString("title")

        val img: ParseFile? = dataSet[position].getParseFile("picture")
        val imgUrl: String? = img?.url
        val imgUri : Uri = Uri.parse(imgUrl)
        Glide.with(viewHolder.itemView.context)
            .load(imgUri)
            .override(600,600)
            .error(R.drawable.ic_baseline_block_24)
            .placeholder(R.drawable.ic_baseline_timelapse_24)
            .fitCenter()
            .into(viewHolder.imgView)

        viewHolder.cl.setOnClickListener(){
                val intent = Intent(viewHolder.itemView.context, DetailProductActivity::class.java)

                intent.putExtra("selected_product",dataSet[position])
                viewHolder.itemView.context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
package com.example.myapplication.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.Activities.DetailActivity
import com.example.myapplication.Adapter.BestFoodsAdapter.viewholder
import com.example.myapplication.Domain.Foods
import com.example.myapplication.R


class BestFoodsAdapter(var items: ArrayList<Foods>) : RecyclerView.Adapter<viewholder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        context = parent.context
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_best_deal, parent, false)
        return viewholder(inflate)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.titleTxt.text = items[position].Title
        holder.priceTxt.text = "$" + items[position]?.Price
        holder.timeTxt.text = items[position]?.TimeValue.toString() + " min"
        holder.starTxt.text = "" + items[position]?.Star
        Glide.with(context!!)
            .load(items[position]?.ImagePath)
            .transform(CenterCrop(), RoundedCorners(30))
            .into(holder.pic)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", items[holder.adapterPosition])
            context!!.startActivities(arrayOf(intent))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTxt: TextView
        var priceTxt: TextView
        var starTxt: TextView
        var timeTxt: TextView
        var pic: ImageView

        init {
            titleTxt = itemView.findViewById(R.id.titleTxt)
            priceTxt = itemView.findViewById(R.id.priceTxt)
            starTxt = itemView.findViewById(R.id.starTxt)
            timeTxt = itemView.findViewById(R.id.timeTxt)
            pic = itemView.findViewById(R.id.pic)
        }
    }
}
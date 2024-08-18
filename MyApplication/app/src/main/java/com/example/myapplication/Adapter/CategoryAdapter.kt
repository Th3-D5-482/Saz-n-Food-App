package com.example.myapplication.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Domain.Category
import com.example.myapplication.Activities.ListFoodsActivity
import com.example.myapplication.R

class CategoryAdapter(items: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.viewholder>() {
    var items: ArrayList<Category>
    var context: Context? = null

    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        context = parent.context
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category, parent, false)
        return viewholder(inflate)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.titleTxt.setText(items[holder.adapterPosition].Name)
        when (position) {
            0 -> {
                holder.pic.setBackgroundResource(R.drawable.cat_0_background)
            }

            1 -> {
                holder.pic.setBackgroundResource(R.drawable.cat_1_background)
            }

            2 -> {
                holder.pic.setBackgroundResource(R.drawable.cat_2_background)
            }

            3 -> {
                holder.pic.setBackgroundResource(R.drawable.cat_3_background)
            }

            4 -> {
                holder.pic.setBackgroundResource(R.drawable.cat_4_background)
            }

            5 -> {
                holder.pic.setBackgroundResource(R.drawable.cat_5_background)
            }

            6 -> {
                holder.pic.setBackgroundResource(R.drawable.cat_6_background)
            }

            7 -> {
                holder.pic.setBackgroundResource(R.drawable.cat_7_background)
            }
        }
        val drawableResourceId = context!!.resources.getIdentifier(
            items[holder.adapterPosition].ImagePath,
            "drawable",
            holder.itemView.context.packageName
        )
        Glide.with(context!!)
            .load(drawableResourceId)
            .into(holder.pic)
        holder.itemView.setOnClickListener {
            val category: Category = items[holder.adapterPosition]
            val intent = Intent(
                context,
                ListFoodsActivity::class.java
            )
            intent.putExtra("Category", category)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTxt: TextView
        var pic: ImageView

        init {
            titleTxt = itemView.findViewById<TextView>(R.id.catNameTxt)
            pic = itemView.findViewById<ImageView>(R.id.imgCat)
        }
    }
}
package com.example.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.Domain.Foods
import com.example.myapplication.Helpers.ChangeNumberItemsListener
import com.example.myapplication.Helpers.ManagmentCart
import com.example.myapplication.R

class CartAdapter(private val list: ArrayList<Foods?>, private val context: Context, private val changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val managmentCart: ManagmentCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_cart, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position]?.let { holder.bind(it, position) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.titleTxt)
        private val pic: ImageView = itemView.findViewById(R.id.pic)
        private val feeEachItem: TextView = itemView.findViewById(R.id.feeEachItem)
        private val plusItem: TextView = itemView.findViewById(R.id.plusCartBtn)
        private val minusItem: TextView = itemView.findViewById(R.id.minusCartBtn)
        private val totalEachItem: TextView = itemView.findViewById(R.id.totalEachItem)
        private val num: TextView = itemView.findViewById(R.id.numberItemTxt)

        fun bind(food: Foods, position: Int) {
            title.text = food.Title
            feeEachItem.text = "$${food.numberInCart * food.Price}"
            totalEachItem.text = "${food.numberInCart} * $${food.Price}"
            num.text = food.numberInCart.toString()
            Glide.with(itemView.context)
                .load(food.ImagePath)
                .transform(CenterCrop(), RoundedCorners(30))
                .into(pic)
            plusItem.setOnClickListener {
                managmentCart.plusNumberItem(list, position, object : ChangeNumberItemsListener {
                    override fun change() {
                        notifyDataSetChanged()
                        changeNumberItemsListener.change()
                    }
                })
            }
            minusItem.setOnClickListener {
                managmentCart.minusNumberItem(list, position, object : ChangeNumberItemsListener {
                    override fun change() {
                        notifyDataSetChanged()
                        changeNumberItemsListener.change()
                    }
                })
            }
        }
    }
}

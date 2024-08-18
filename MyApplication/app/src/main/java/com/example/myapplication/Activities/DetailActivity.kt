package com.example.myapplication.Activities

import android.R
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.myapplication.Domain.Foods
import com.example.myapplication.Helpers.ManagmentCart
import com.example.myapplication.Helpers.ManagmentFavorite
import com.example.myapplication.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    var binding: ActivityDetailBinding? = null
    private var num = 1
    private var obj: Foods? = null
    private var managmentCart: ManagmentCart? = null
    private var managmentFavorite: ManagmentFavorite? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.getRoot())
        window.statusBarColor = resources.getColor(R.color.black)
        managmentCart = ManagmentCart(this);
        managmentFavorite = ManagmentFavorite(this);
        intentExtra
        setVaraible()
    }

    private fun setVaraible() {
        binding?.backBtn?.setOnClickListener { v -> finish() }
        Glide.with(this@DetailActivity)
            .load(obj!!.ImagePath)
            .into(binding!!.pic)
        binding?.priceTxt?.setText("$" + obj!!.Price)
        binding?.titleTxt?.setText(obj!!.Title)
        binding?.descriptionTxt?.setText(obj!!.Description)
        binding?.rateTxt?.setText(obj!!.Star.toString() + " Rating")
        binding?.ratingBar?.setRating(obj!!.Star.toFloat())
        binding?.totalTxt?.setText((num + obj!!.Price).toString() + " $")
        binding?.plusBtn?.setOnClickListener {
            num = num + 1
            binding?.numTxt?.text = "$num "
            binding?.totalTxt?.text = "$${num * obj!!.Price}"
        }

        binding?.minusBtn?.setOnClickListener {
            if (num > 1) {
                num = num - 1
                binding?.numTxt?.text = "$num "
                binding?.totalTxt?.text = "$${num * obj!!.Price}"
            }
        }
        binding?.addBtn?.setOnClickListener {
            obj?.let {
                it.numberInCart = num
                managmentCart?.insertFood(it)
            }
        }
       binding?.favBtn?.setOnClickListener{
          obj?.let {
              //it.numberInFavorite = num
              managmentFavorite?.insertFavorite(it)
          }
       }
    }

    private val intentExtra: Unit
        private get() {
            obj = intent.getSerializableExtra("object") as Foods?
        }
}
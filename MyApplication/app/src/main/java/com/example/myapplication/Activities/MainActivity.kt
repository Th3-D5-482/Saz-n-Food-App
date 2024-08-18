package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.BestFoodsAdapter
import com.example.myapplication.Adapter.CategoryAdapter
import com.example.myapplication.Domain.Category
import com.example.myapplication.Domain.Foods
import com.example.myapplication.Domain.Location
import com.example.myapplication.Domain.Price
import com.example.myapplication.Domain.Time
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class MainActivity : BaseActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initLocation()
        initTime()
        initPrice()
        initBestFood()
        initCategory()
        setVariable()
    }
    private fun setVariable() {
        binding?.logoutBtn?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, IntroActivity::class.java))
        }
        binding?.SearchBtn?.setOnClickListener {
            val text = binding?.searchEdt?.text.toString()
            if (text.isNotEmpty()) {
                val intent = Intent(this@MainActivity, SearchActivity::class.java).also {
                    it.putExtra("CategoryName",text)
                    it.putExtra("text", text)
                    it.putExtra("isSearch", true)
                    startActivity(it)
                }
            }
        }
        binding!!.CartBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    CartActivity::class.java
                )
            )
        }
        binding!!.favBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity,FavoriteActivity::class.java))
        }
        binding!!.profileBtn.setOnClickListener {
            val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            val name = sharedPreferences.getString("name", "")
            val email = sharedPreferences.getString("email", "")
            val password = sharedPreferences.getString("password", "")
            val phonenumber = sharedPreferences.getString("phonenumber", " ")
            val intent = Intent(
                this@MainActivity,
                ProfileActivity::class.java
            )
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.putExtra("phonenumber", phonenumber)
            startActivity(intent)
        }
    }

    private fun initBestFood() {
        val myRef = database?.getReference("Foods")
        binding!!.progressBarBestFood.visibility = View.VISIBLE
        val list = ArrayList<Foods>()
        val query = myRef?.orderByChild("BestFood")?.equalTo(true)
        query?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Foods::class.java)?.let { list.add(it) }
                    }
                    if (list.size > 0) {
                        binding?.BestFoodView?.layoutManager = LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        val adapter: RecyclerView.Adapter<*> = BestFoodsAdapter(list)
                        binding?.BestFoodView?.adapter = adapter
                    }
                    binding!!.progressBarBestFood.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun initCategory() {
        val myRef = database!!.getReference("Category")
        binding?.progressBarCategory?.setVisibility(View.VISIBLE)
        val list = ArrayList<Category>()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Category::class.java)?.let { list.add(it) }
                    }
                    if (list.size > 0) {
                        binding!!.Categoryview.layoutManager = GridLayoutManager(this@MainActivity, 4)
                        val adapter: RecyclerView.Adapter<*> = CategoryAdapter(list)
                        binding!!.Categoryview.adapter = adapter
                    }
                    binding?.progressBarCategory?.setVisibility(View.GONE)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun initLocation() {
        val myRef = database?.getReference("Location")
        val list = ArrayList<Location>()
        myRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Location::class.java)?.let { list.add(it) }
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding?.LocationSp?.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun initTime() {
        val myRef = database?.getReference("Time")
        val list = ArrayList<Time>()
        myRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Time::class.java)?.let { list.add(it) }
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding?.TimeSp?.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun initPrice() {
        val myRef = database?.getReference("Price")
        val list = ArrayList<Price>()
        myRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Price::class.java)?.let { list.add(it) }
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding?.PriceSp?.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
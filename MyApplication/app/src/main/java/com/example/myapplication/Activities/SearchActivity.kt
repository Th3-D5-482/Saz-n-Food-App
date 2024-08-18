package com.example.myapplication.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityListFoodsBinding
import com.example.myapplication.Adapters.FoodListAdapter
import com.example.myapplication.Domain.Foods
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class SearchActivity : BaseActivity() {
    var binding: ActivityListFoodsBinding? = null
    private var adapterListFood: RecyclerView.Adapter<*>? = null
    private var category = 0
    private var searchText: String? = null
    private var categoryName: String? = null
    private var isSearch = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodsBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        intentExtra
        initList(category)
    }

    private fun initList(categoryId: Int) {
        val myRef = database!!.getReference("Foods")
        binding?.progressBar?.setVisibility(View.VISIBLE)
        val list = ArrayList<Foods>()
        val query: Query
        query = if (isSearch) {
            myRef.orderByChild("Title").startAt(searchText).endAt(searchText + '\uf8ff')
        } else {
            myRef.orderByChild("CategoryId").equalTo(categoryId.toDouble())
        }
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding?.progressBar?.visibility = View.GONE
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Foods::class.java)?.let { list.add(it) }
                    }
                    if (list.size > 0) {
                        binding?.foodListView?.setLayoutManager(
                            GridLayoutManager(
                                this@SearchActivity,
                                2
                            )
                        )
                        adapterListFood = FoodListAdapter(list)
                        binding?.foodListView?.setAdapter(adapterListFood)
                    }
                    else
                    {
                        binding?.emptyTxt?.visibility = View.GONE;
                    }
                }
                else
                {
                    binding?.emptyTxt?.visibility = View.GONE;
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private val intentExtra: Unit
        private get() {
            category = intent.getIntExtra("Category", 0)
            categoryName = intent.getStringExtra("CategoryName")
            searchText = intent.getStringExtra("text")
            isSearch = intent.getBooleanExtra("isSearch", false)
            binding?.titleText?.setText(categoryName)
            binding?.backBtn?.setOnClickListener { v -> finish() }
        }
}
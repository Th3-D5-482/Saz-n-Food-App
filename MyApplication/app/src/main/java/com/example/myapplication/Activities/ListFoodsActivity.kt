package com.example.myapplication.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.FoodListAdapter
import com.example.myapplication.Domain.Category
import com.example.myapplication.Domain.Foods
import com.example.myapplication.databinding.ActivityListFoodsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class ListFoodsActivity : BaseActivity() {
    var binding: ActivityListFoodsBinding? = null
    private var adapterListFood: RecyclerView.Adapter<*>? = null
    private var category: Category? = null
    private var searchText: String? = null
    private var isSearch = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodsBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        intentExtra
        initList(category!!.Id)
    }

    private fun initList(categoryIdParam: Int) {
        val myRef: DatabaseReference = database!!.getReference("Foods")
        binding!!.progressBar.visibility = View.VISIBLE
        val list: ArrayList<Foods> = ArrayList<Foods>()
        val query: Query
        query = if (isSearch) {
            myRef.orderByChild("Title").startAt(searchText).endAt(searchText + '\uf8ff')
        } else {
            myRef.orderByChild("CategoryId").equalTo(categoryIdParam.toDouble())
        }
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.getChildren()) {
                        issue.getValue<Foods?>(Foods::class.java)?.let { list.add(it) }
                    }
                    if (list.size > 0) {
                        binding?.foodListView?.layoutManager =
                            GridLayoutManager(this@ListFoodsActivity, 2)
                        adapterListFood = FoodListAdapter(list)
                        binding?.foodListView?.adapter = adapterListFood
                    }
                    binding!!.progressBar.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private val intentExtra: Unit
        private get() {
            category = intent.getParcelableExtra("Category")
            searchText = intent.getStringExtra("text")
            isSearch = intent.getBooleanExtra("isSearch", false)
            binding!!.titleText.text = category!!.Name
            binding!!.backBtn.setOnClickListener { v: View? -> finish() }
        }
}
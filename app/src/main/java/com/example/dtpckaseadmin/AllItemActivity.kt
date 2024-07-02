package com.example.dtpckaseadmin

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dtpckaseadmin.adapter.MenuItemAdapter
import com.example.dtpckaseadmin.databinding.ActivityAllItemBinding
import com.example.dtpckaseadmin.model.AllProduct
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuItems: ArrayList<AllProduct> = ArrayList()
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()

        binding.backButton.setOnClickListener {
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val productRef: DatabaseReference = database.reference.child("all products")

        //fetch data from database
        productRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear exisiting data before populating
                menuItems.clear()

                //loop for throiugh each food item
                for (productSnapShot in snapshot.children) {
                    val menuItem = productSnapShot.getValue(AllProduct::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }


        })

    }
    private fun setAdapter() {
     val adapter = MenuItemAdapter(this@AllItemActivity,menuItems,databaseReference)
      binding.ProductoRecyclerView.layoutManager = LinearLayoutManager(this)
      binding.ProductoRecyclerView.adapter = adapter

//    }

    }
}

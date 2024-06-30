package com.example.dtpckaseadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dtpckaseadmin.adapter.DeliveryAdapter
import com.example.dtpckaseadmin.adapter.PendingOrderAdapter
import com.example.dtpckaseadmin.databinding.ActivityPendingOrderBinding
import com.example.dtpckaseadmin.databinding.PendingOrdersItemBinding

class PendingOrderActivity : AppCompatActivity() {
private lateinit var binding: ActivityPendingOrderBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()

        }

        val orderedCustomerName = arrayListOf(
            "Pedro Mo",
            "Tyler Durden",
            "Mike Aranguren",
        )
        val orderedQuantity = arrayListOf(
            "8",
            "6",
            "5",
        )
        val orderedProductoImage = arrayListOf(R.drawable.dragonball1,R.drawable.ccsc, R.drawable.dsdwd)
        val adapter = PendingOrderAdapter(orderedCustomerName, orderedQuantity,orderedProductoImage, this)
        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
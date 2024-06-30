package com.example.dtpckaseadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dtpckaseadmin.adapter.AddItemAdapter
import com.example.dtpckaseadmin.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val productoName = listOf("product01", "product02", "product03", "product04", "product05","product06")
        val productoItemPrice = listOf("S/.150.00", "S/.120.00", "S/.190.00", "S/.90.00", "S/.200.00","S/.124.00")
        val productoItemImage = listOf(
            R.drawable.dragonball1,
            R.drawable.ccdsc,
            R.drawable.dsdwd,
            R.drawable.dsadad,
            R.drawable.aasas,
            R.drawable.ccdsc
        )
        binding.backButton.setOnClickListener {
            finish()
        }
        val adapter = AddItemAdapter(ArrayList(productoName), ArrayList(productoItemPrice), ArrayList(productoItemImage))
        binding.ProductoRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.ProductoRecyclerView.adapter = adapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
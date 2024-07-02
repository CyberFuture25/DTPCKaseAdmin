package com.example.dtpckaseadmin.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dtpckaseadmin.databinding.ItemItemBinding
import com.example.dtpckaseadmin.model.AllProduct
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllProduct>,
    databaseReference: DatabaseReference

) : RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {
private val itemQuantities = IntArray(menuList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuList.size
        inner class AddItemViewHolder(private val binding: ItemItemBinding) :RecyclerView.ViewHolder(binding.root){
         fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = menuList[position]
                val uriString = menuItem.productImage
                val uri = Uri.parse(uriString)
                productonameTextView.text=menuItem.productName
                precioTextView.text=menuItem.productPrice
                Glide.with(context).load(uri).into(productoImageView)

                quantityTextView.text=quantity.toString()

                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }
                deleteButton.setOnClickListener {
                    deleteQuantity(position)

                }
                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }

            }
         }

            private fun increaseQuantity(position: Int) {
                if(itemQuantities[position]<10){
                    itemQuantities[position]++
                    binding.quantityTextView.text=itemQuantities[position].toString()
                }

            }
            private fun decreaseQuantity(position: Int) {
                if(itemQuantities[position] > 1){
                    itemQuantities[position]--
                    binding.quantityTextView.text=itemQuantities[position].toString()
                }
            }
            private fun deleteQuantity(position: Int){
                menuList.removeAt(position)
                menuList.removeAt(position)
                menuList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,menuList.size)

            }
        }


}

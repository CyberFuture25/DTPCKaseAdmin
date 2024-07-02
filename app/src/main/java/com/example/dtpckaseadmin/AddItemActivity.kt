package com.example.dtpckaseadmin

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dtpckaseadmin.databinding.ActivityAddItemBinding
import com.example.dtpckaseadmin.model.AllProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    //Producto Item Details

    private lateinit var productName : String
    private lateinit var productCategory : String
    private lateinit var productPrice : String
    private lateinit var productDescription: String
    private  var productImageUri : Uri? = null
    //Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase



    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

            //initializr Firebase
        auth = FirebaseAuth.getInstance()
        //Initializr Firebase database instance
        database = FirebaseDatabase.getInstance()

        binding.AddItemButton.setOnClickListener {
            //get data from field
            productName = binding.productName.text.toString().trim()
            productCategory = binding.productCategory.text.toString().trim()
            productPrice = binding.productPrice.text.toString().trim()
            productDescription = binding.productDescription.text.toString().trim()

            if(!(productName.isBlank()||productCategory.isBlank()||productPrice.isBlank()||productDescription.isBlank())) {
                uploadData()
                Toast.makeText(this, "Item agregado correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this,"Complete todo los campos", Toast.LENGTH_SHORT).show()
            }

        }
binding.selectImage.setOnClickListener {
    pickImage.launch("image/*")
}

        binding.backButton.setOnClickListener {
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun uploadData() {
        //get a reference to the "all products" node in the database
        val AllProductRef: DatabaseReference = database.getReference("all products")
        //Generate a unique key for the all products item
        val newItemKey: String? = AllProductRef.push().key

        if (productImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("product_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(productImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    //create a new product item
                    val newItem = AllProduct(
                        productName = productName,
                        productCategory = productCategory,
                        productPrice = productPrice,
                        productImage = downloadUrl.toString(),
                        productDescription = productDescription,
                    )
                    newItemKey?.let { key ->
                        AllProductRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "Archivo subido correctamente", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

            }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al subir el archivo", Toast.LENGTH_SHORT).show()
                }
        }
                else{
                    Toast.makeText(this,"Seleccione una imagen",Toast.LENGTH_SHORT).show()
                }

    }
     private val pickImage =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                if (uri != null) {
                    binding.selectedImage.setImageURI(uri)
                    productImageUri = uri
                }
            }
    }





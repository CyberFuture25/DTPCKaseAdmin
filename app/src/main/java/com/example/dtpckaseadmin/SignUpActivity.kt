package com.example.dtpckaseadmin

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dtpckaseadmin.databinding.ActivitySignUpBinding
import com.example.dtpckaseadmin.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {

    private lateinit var nameOfAdmin: String
    private lateinit var userName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth : FirebaseAuth
    private lateinit var database: DatabaseReference




    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        //Initialize Firebase Auth
        auth = Firebase.auth
        database = Firebase.database.reference



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.createUserButton.setOnClickListener {
            //get text from edittext
            nameOfAdmin=binding.name.text.toString().trim()
            userName=binding.userNm.text.toString().trim()
            email=binding.emailOrPhone.text.toString().trim()
            password=binding.password.text.toString().trim()

            if(userName.isBlank() || nameOfAdmin.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this,"Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()

            }else{
                createAccount(email,password)
            }



        }
        binding.alreadyHaveAccountButton.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }


    }

    private fun createAccount(email: String, password: String) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Cuenta creada exitosamente", Toast.LENGTH_SHORT).show()
                    saveUserData()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"Creación de cuenta fallida", Toast.LENGTH_SHORT).show()
                    Log.d("Account", "creacionCuenta: Fallida",task.exception)
                }
            }

    }
// save data in to database
    private fun saveUserData() {
        //get text from edittext
        nameOfAdmin=binding.name.text.toString().trim()
        userName=binding.userNm.text.toString().trim()
        email=binding.emailOrPhone.text.toString().trim()
        password=binding.password.text.toString().trim()
        val user= UserModel(userName,nameOfAdmin, email, password)
        val  userId : String = FirebaseAuth.getInstance().currentUser!!.uid
    //save user data Firebase database
        database.child("user").child(userId).setValue(user)

    }
}
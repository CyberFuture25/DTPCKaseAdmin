package com.example.dtpckaseadmin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dtpckaseadmin.databinding.ActivityLoginBinding
import com.example.dtpckaseadmin.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class LoginActivity : AppCompatActivity() {
    private var nameOfAdmin: String ?=null
    private var userName: String ?=null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
//    private lateinit var googleSignInClient: GoogleSignInClient


    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
//        val GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        //Initialize Firebase Auth
        auth = Firebase.auth
        //Initialize Firebase Database
        database = Firebase.database.reference

//      googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.loginButton.setOnClickListener {
            //get text from edittext

            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                createUserAccount(email, password)

            }

        }
        binding.dontHaveAccountButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                updateUi(user)
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(this, "Creacion de Usuario & Login Exitosa", Toast.LENGTH_SHORT).show()
                        saveUserData()
                        updateUi(user)

                    } else {
                        Toast.makeText(this, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                        Log.d("Account", "createUserAccount: Autenticacion fallida", task.exception)
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        //get text from edittext
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(userName,nameOfAdmin,email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).setValue(user)
        }

    }
    //Check if user is already logged in
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
        private fun updateUi(user: FirebaseUser?) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

package com.grupotres.tpfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val admin = Login();

        setContentView(R.layout.activity_main)

        myPreferences = MyPreferences(this)

        val username: EditText = findViewById(R.id.username)
        val password: EditText = findViewById(R.id.password)
        val keepSession: CheckBox = findViewById(R.id.keepSession)
        val loginButton: Button = findViewById(R.id.btnLogin)
        val forgotPassword: TextView = findViewById(R.id.forgotPassword)

        loginButton.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (admin.validateCredentials(user, pass)) {
                val newLogin = Login(user, pass, false)
                myPreferences.saveLogin(newLogin, keepSession.isChecked)
                checkUserStatus()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        forgotPassword.setOnClickListener {
            val intent = Intent(this, RecoveryActivity::class.java)
            startActivity(intent)
        }

        checkSession()
    }



    private fun checkSession() {
        val user = myPreferences.getLogin()
        val session = myPreferences.getSession()
        if (user != null && session) {
            checkUserStatus()
        }
    }

    private fun checkUserStatus() {
        val user = myPreferences.getLogin()
        if (user != null) {
            if (user.passwordUpdate) {
                val intent = Intent(this, ChangePasswordActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
    }

}

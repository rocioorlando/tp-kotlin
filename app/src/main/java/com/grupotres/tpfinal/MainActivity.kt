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

        //Cargamos el layout que contiene el listview
        setContentView(R.layout.activity_main)

        myPreferences = MyPreferences(this)

        val username: EditText = findViewById(R.id.username)
        val password: EditText = findViewById(R.id.password)
        val keepSession: CheckBox = findViewById(R.id.keepSession)
        val loginButton: Button = findViewById(R.id.loginButton)
        val forgotPassword: TextView = findViewById(R.id.forgotPassword)

        loginButton.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()

            if (validateCredentials(username, password)) {
                //creo mi usuario
                val user = User(username, password)
                //lo guardo
                myPreferences.saveUser(user, keepSession.isChecked)

                //Aca tengo que llamar a la pantalla de inicio
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        forgotPassword.setOnClickListener {
            val intent = Intent(this, RecoveryActivity::class.java)
            startActivity(intent)
        }

        //INICIA SOLO SI YA ESTA EN MY PREFERENCES
        checkSession()
    }

    private fun checkSession() {
        val user = myPreferences.getUser()
        val session = myPreferences.getSession()
        if (user != null && session) {
            //Ingreso a la aplicacion directamente
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun validateCredentials(username: String, password: String): Boolean {
        // Hay que ver acá como valido las credenciales ??
        return username == "admin" && password == "1234" // Ejemplo simple
    }
}

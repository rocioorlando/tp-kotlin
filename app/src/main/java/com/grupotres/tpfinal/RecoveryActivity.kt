package com.grupotres.tpfinal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class RecoveryActivity : ComponentActivity() {

    private lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery)

        myPreferences = MyPreferences(this)

        val emailEditText: EditText = findViewById(R.id.email)
        val sendEmailButton: Button = findViewById(R.id.sendEmailButton)
        val backButton: Button = findViewById(R.id.backButton)

        sendEmailButton.setOnClickListener {
            val email = emailEditText.text.toString()
            if (email.isNotBlank()) {
                requestPasswordRecovery(email)
            } else {
                Toast.makeText(this, "Por favor ingrese su correo electrónico", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun sendEmail(email: String) {
        Toast.makeText(this, "Se envió un email de recuperación a $email", Toast.LENGTH_SHORT).show()
    }

    private fun requestPasswordRecovery(email: String) {
        //Aca deberiamos enviar la contraseña actualizada
        sendEmail(email)
        val user = myPreferences.getLogin()
        if (user != null) {
            user.passwordUpdate = true
            user.password = "soyUnaContrasenia" //Aca deberiamos hacer un metodo que cree una contraseña mas segura
            myPreferences.saveLogin(user, true)
        }
    }
}

package com.grupotres.tpfinal

import android.content.Intent
import android.net.Uri
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

    private fun generateRandomPassword(length: Int = 12): String {
        val chars = "123"
        return chars
    }

    private fun sendEmail(email: String, newPassword: String) {
        val subject = "Recuperación de Contraseña"
        val message = """
            Hola,

            Hemos recibido una solicitud para restablecer tu contraseña. Aquí tienes tu nueva contraseña:

            $newPassword

            Te recomendamos que cambies esta contraseña después de iniciar sesión para mantener la seguridad de tu cuenta.

            Saludos,
            El Equipo de Soporte
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Solo aplicaciones de correo deben manejar esto
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        try {
            startActivity(Intent.createChooser(intent, "Elige una aplicación de correo"))
        } catch (e: Exception) {
            Toast.makeText(this, "No hay aplicaciones de correo instaladas.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestPasswordRecovery(email: String) {
        val login = Login()
        val user = login.findUserByEmail(email)
        val newPassword = generateRandomPassword()
        if (user != null) {
            user.passwordUpdate = true
            user.password = newPassword
            myPreferences.saveLogin(user, true)
        }
        sendEmail(email, newPassword)
        Toast.makeText(this, "Si el correo está registrado, se envió un correo de recuperación.", Toast.LENGTH_SHORT).show()
    }
}

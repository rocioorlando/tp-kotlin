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
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*()-_=+"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }


    //Envio de correo con INTENT (utilizamos para enviar una aplicación de correo que se
    //encuentre instalada en el teléfono).
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

        // trimIndent es una función de Kotlin que se utiliza para eliminar la indentación de un bloque de texto.
        // Esta función permite que el texto se alinee correctamente cuando se utiliza en un literal de cadena de varias líneas (multiline string)

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
        // Generar una nueva contraseña aleatoria
        val newPassword = generateRandomPassword()
        sendEmail(email, newPassword)
        val user = myPreferences.getLogin()
        if (user != null) {
            user.passwordUpdate = true
            user.password = newPassword // Asignar la nueva contraseña generada
            myPreferences.saveLogin(user, true)
        }
    }




}

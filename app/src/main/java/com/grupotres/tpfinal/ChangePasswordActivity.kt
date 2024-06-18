package com.grupotres.tpfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class ChangePasswordActivity : ComponentActivity() {

    private lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Establece el contenido de la actividad a un diseño específico
        setContentView(R.layout.activity_change_password)

        // this : contexto actual
        myPreferences = MyPreferences(this)

        // Obtiene referencias a los elementos de la interfaz de usuario (campos de texto y botones) del layout activity_change_password utilizando findViewById.
        val currentPassword: EditText = findViewById(R.id.currentPassword)
        val newPassword: EditText = findViewById(R.id.newPassword)
        val confirmPassword: EditText = findViewById(R.id.confirmPassword)
        val btnChangePassword: Button = findViewById(R.id.btnChangePassword)
        val btnBack: Button = findViewById(R.id.btnBack)

        // listener, solo se llama cuando hace click
        btnChangePassword.setOnClickListener {
            val currentPassword = currentPassword.text.toString()
            val newPassword = newPassword.text.toString()
            val confirmPassword = confirmPassword.text.toString()

            if (validatePasswords(currentPassword, newPassword, confirmPassword)) {
                updatePassword(newPassword)
            }
        }

        // cuando se hace click finaliza la actividad
        btnBack.setOnClickListener {
            finish()
        }
    }

    // Este método valida las contraseñas. Comprueba si el usuario existe, si la contraseña actual es correcta y 
    // si la nueva contraseña coincide con la confirmación. Muestra mensajes Toast en caso de errores.
    private fun validatePasswords(currentPassword: String, newPassword: String, confirmPassword: String): Boolean {
        val user = myPreferences.getLogin()
        return when {
            user == null -> {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                false
            }
            user.password != currentPassword -> {
                Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show()
                false
            }
            newPassword != confirmPassword -> {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    // Este método actualiza la contraseña del usuario. Si el usuario existe, actualiza su contraseña y 
    // guarda los cambios en MyPreferences. Muestra un Toast indicando que la contraseña se cambió exitosamente, 
    // y luego inicia MainActivity y finaliza la actividad actual.
    private fun updatePassword(newPassword: String) {
        val user = myPreferences.getLogin()
        var session = myPreferences.getSession();
        if (user != null) {
            user.password = newPassword
            user.passwordUpdate = false
            myPreferences.saveLogin(user, session)
            Toast.makeText(this, "La contraseña se cambió exitosamente", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

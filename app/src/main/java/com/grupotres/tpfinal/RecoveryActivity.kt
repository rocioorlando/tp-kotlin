package com.grupotres.tpfinal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class RecoveryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery)

        val emailEditText: EditText = findViewById(R.id.email)
        val sendEmailButton: Button = findViewById(R.id.sendEmailButton)
        val backButton: Button = findViewById(R.id.backButton)

        sendEmailButton.setOnClickListener {
            val email = emailEditText.text.toString()
            // tenemos que hacer el envio de mail
            Toast.makeText(this, "Se envió un mail a $email", Toast.LENGTH_SHORT).show()
        }

        backButton.setOnClickListener {
            //vuelvo a main activity
            finish()
        }
    }
}

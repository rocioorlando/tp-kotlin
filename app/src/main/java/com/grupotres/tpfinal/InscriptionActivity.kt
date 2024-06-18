package com.grupotres.tpfinal

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity

class InscriptionActivity : ComponentActivity() {

    private lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        myPreferences = MyPreferences(this)

        val spinnerSubject: Spinner = findViewById(R.id.subjects)
        val spinnerCalls: Spinner = findViewById(R.id.calls)
        val btnSend: Button = findViewById(R.id.btnSend)
        val btnViewInscriptions: Button = findViewById(R.id.btnViewInscriptions)
        val btnBackHome: Button = findViewById(R.id.btnBackHome)

        // se definen los array de datos
        val subjects = arrayOf("Programacion I", "Estadística", "Arquitectura", "Inglés")
        val calls = arrayOf("Primer llamado", "Segundo llamado", "Tercer llamado")

        // ArrayAdapter se usa para llenar los Spinner con los datos de los array
        // ArrayAdapter(this, android.R.layout.simple_spinner_item, subjects) crea un adaptador para el 
        // Spinner de materias, usando un diseño de lista simple proporcionado por Android (android.R.layout.simple_spinner_item).
        val adapterSubject = ArrayAdapter(this, android.R.layout.simple_spinner_item, subjects)
        val adapterCalls = ArrayAdapter(this, android.R.layout.simple_spinner_item, calls)

        // setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) establece el diseño que 
        // se utilizará para mostrar las opciones desplegables del Spinner. En este caso es el predeterminado de Android
        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterCalls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // spinnerSubject.adapter = adapterSubject asigna el adaptador de materias al Spinner de materias (spinnerSubject).
        spinnerSubject.adapter = adapterSubject
        spinnerCalls.adapter = adapterCalls

        btnSend.setOnClickListener {
            val selectedSubject = spinnerSubject.selectedItem.toString()
            val selectedCall = spinnerCalls.selectedItem.toString()

            val inscription = "$selectedSubject - $selectedCall"
            myPreferences.saveInscription(inscription)

            sendEmail(inscription)

            Toast.makeText(this, "Inscripción enviada", Toast.LENGTH_SHORT).show()
        }

        btnViewInscriptions.setOnClickListener {
            startActivity(Intent(this, MyInscriptionActivity::class.java))
            finish()
        }

        btnBackHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun sendEmail(inscription: String) {
        // vamo a ver como hacemos
    }
}

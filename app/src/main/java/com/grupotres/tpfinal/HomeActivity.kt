package com.grupotres.tpfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView: Este método se utiliza para establecer el diseño de la actividad
        // R.layout.activity_home: R es una clase generada automáticamente por Android que contiene referencias a todos los recursos de la aplicación.
        setContentView(R.layout.activity_home)

        val btnExamsInscription: Button = findViewById(R.id.btnExamsInscription)
        val btnMyInscriptions: Button = findViewById(R.id.btnMyInscriptions)
        val btnStudentProfile: Button = findViewById(R.id.btnStudentProfile)
        val btnAPIs: Button = findViewById(R.id.btnAPIs)

        btnExamsInscription.setOnClickListener {
            val intent = Intent(this, InscriptionActivity::class.java)
            startActivity(intent)
        }

        btnMyInscriptions.setOnClickListener {
            val intent = Intent(this, MyInscriptionActivity::class.java)
            startActivity(intent)
        }

        btnStudentProfile.setOnClickListener {

        }

        btnAPIs.setOnClickListener {

        }
    }
}

package com.grupotres.tpfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.grupotres.tpfinal.APIActivity

class HomeActivity : ComponentActivity() {

    private lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establecemos el diseño de la actividad
        setContentView(R.layout.activity_home)

        myPreferences = MyPreferences(this)

        val btnExamsInscription: Button = findViewById(R.id.btnExamsInscription)
        val btnMyInscriptions: Button = findViewById(R.id.btnMyInscriptions)
        val btnStudentProfile: Button = findViewById(R.id.btnStudentProfile)
        val btnAPIs: Button = findViewById(R.id.btnAPIs)
        val btnLogout: Button = findViewById(R.id.btnLogout)

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
            val intent = Intent(this, APIActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            myPreferences.clearLogin()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

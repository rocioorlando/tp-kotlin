package com.grupotres.tpfinal

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity

class MyInscriptionActivity : ComponentActivity() {

    private lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_inscription)

        myPreferences = MyPreferences(this)

        val listViewInscriptions: ListView = findViewById(R.id.listViewInscription)
        val textNoInscriptions: TextView = findViewById(R.id.textNoInscriptions)
        val btnBackHome: Button = findViewById(R.id.btnBackHome)

        val inscriptions = myPreferences.getInscriptions()

        if (inscriptions.isEmpty()) {
            listViewInscriptions.visibility = ListView.GONE
            textNoInscriptions.visibility = TextView.VISIBLE
        } else {
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, inscriptions)
            listViewInscriptions.adapter = adapter
        }

        btnBackHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}

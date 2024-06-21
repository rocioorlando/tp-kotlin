package com.grupotres.tpfinal

import CharacterAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIActivity : ComponentActivity() {

    // Declaramos las variables para los elementos de la interfaz
    private lateinit var spinnerName: Spinner
    private lateinit var spinnerStatus: Spinner
    private lateinit var btnSend: Button
    private lateinit var btnBackHome: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)

        // Inicializamos las vistas de la interfaz
        spinnerName = findViewById(R.id.spinnerName)
        spinnerStatus = findViewById(R.id.spinnerStatus)
        btnSend = findViewById(R.id.btnSend)
        btnBackHome = findViewById(R.id.btnBackHome)
        recyclerView = findViewById(R.id.recyclerViewCharacters)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configuramos Retrofit para realizar solicitudes HTTP
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Creamos el servicio de la API
        val service = retrofit.create(RickAndMortyApiService::class.java)

        // Realizamos una solicitud inicial para obtener todos los personajes
        service.getCharacters(null, null, null, null, null).enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                if (response.isSuccessful) {
                    // Obtenemos los personajes de la respuesta
                    val characters = response.body()?.results ?: emptyList()

                    // Obtenemos los nombres y estados de los personajes para los Spinners
                    val names = characters.map { it.name }
                    val statuses = characters.map { it.status }.distinct()

                    // Configuramos los Spinners con los datos obtenidos
                    setupSpinner(spinnerName, names)
                    setupSpinner(spinnerStatus, statuses)

                    // Configuramos el adaptador del RecyclerView con los personajes
                    characterAdapter = CharacterAdapter(characters)
                    recyclerView.adapter = characterAdapter
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                // Manejar el error
                Toast.makeText(applicationContext, "Algo salió mal", Toast.LENGTH_SHORT).show()
            }
        })

        // Configuramos el botón de enviar para realizar una nueva solicitud con los filtros seleccionados
        btnSend.setOnClickListener {
            val name = spinnerName.selectedItem?.toString()
            val status = spinnerStatus.selectedItem?.toString()

            service.getCharacters(name, status, null, null, null).enqueue(object : Callback<CharacterResponse> {
                override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                    if (response.isSuccessful) {
                        // Actualizamos el adaptador del RecyclerView con los nuevos personajes filtrados
                        val characters = response.body()?.results ?: emptyList()
                        characterAdapter = CharacterAdapter(characters)
                        recyclerView.adapter = characterAdapter
                    } else {
                        // Si no se encontraron resultados, mostramos un mensaje
                        recyclerView.adapter = null
                        Toast.makeText(applicationContext, "No se encontraron resultados", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    // Manejar el error
                    Toast.makeText(applicationContext, "Algo salió mal", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Configuramos el botón para volver a la pantalla de inicio
        btnBackHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    // Función para configurar un Spinner con los datos proporcionados
    private fun setupSpinner(spinner: Spinner, data: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}

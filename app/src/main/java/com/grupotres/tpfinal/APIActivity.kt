package com.grupotres.tpfinal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIActivity : ComponentActivity() {

    private lateinit var spinnerName: Spinner
    private lateinit var spinnerStatus: Spinner
    private lateinit var btnSend: Button
    private lateinit var btnBackHome: Button
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)

        spinnerName = findViewById(R.id.spinnerName)
        spinnerStatus = findViewById(R.id.spinnerStatus)
        btnSend = findViewById(R.id.btnSend)
        btnBackHome = findViewById(R.id.btnBackHome)
        listView = findViewById(R.id.listViewCharacters)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RickAndMortyApiService::class.java)

        service.getCharacters(null, null, null, null, null).enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                if (response.isSuccessful) {
                    val characters = response.body()?.results ?: emptyList()
                    val names = characters.map { it.name }
                    val statuses = characters.map { it.status }.distinct()

                    setupSpinner(spinnerName, names)
                    setupSpinner(spinnerStatus, statuses)

                    setupListView(characters)
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Algo salió mal", Toast.LENGTH_SHORT).show()
            }
        })

        btnSend.setOnClickListener {
            val name = spinnerName.selectedItem?.toString()
            val status = spinnerStatus.selectedItem?.toString()

            service.getCharacters(name, status, null, null, null).enqueue(object : Callback<CharacterResponse> {
                override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                    if (response.isSuccessful) {
                        val characters = response.body()?.results ?: emptyList()
                        setupListView(characters)
                    } else {
                        listView.adapter = null
                        Toast.makeText(applicationContext, "No se encontraron resultados", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Algo salió mal", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btnBackHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupSpinner(spinner: Spinner, data: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setupListView(characters: List<Character>) {
        val adapter = object : ArrayAdapter<Character>(this, R.layout.card_character, characters) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.card_character, parent, false)
                val character = getItem(position)

                val textName = view.findViewById<TextView>(R.id.textName)
                val textStatus = view.findViewById<TextView>(R.id.textStatus)
                val textSpecies = view.findViewById<TextView>(R.id.textSpecies)
                val textType = view.findViewById<TextView>(R.id.textType)
                val textGender = view.findViewById<TextView>(R.id.textGender)
                val imageCharacter = view.findViewById<ImageView>(R.id.imageCharacter)

                textName.text = character?.name
                textStatus.text = character?.status
                textSpecies.text = character?.species
                textType.text = character?.type
                textGender.text = character?.gender

                Glide.with(view).load(character?.image).into(imageCharacter)

                return view
            }
        }

        listView.adapter = adapter
    }
}

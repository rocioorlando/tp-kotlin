package com.grupotres.tpfinal

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class ProfileActivity : ComponentActivity() {

    private lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        myPreferences = MyPreferences(this)

        val firstNameEditText: EditText = findViewById(R.id.firstName)
        val lastNameEditText: EditText = findViewById(R.id.lastName)
        val dobEditText: EditText = findViewById(R.id.dob)
        val documentTypeSpinner: Spinner = findViewById(R.id.documentType)
        val documentNumberEditText: EditText = findViewById(R.id.documentNumber)
        val saveButton: Button = findViewById(R.id.saveButton)
        val backButton: Button = findViewById(R.id.backButton)

        val documentTypes = arrayOf("DNI", "Pasaporte")
        documentTypeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, documentTypes)

        saveButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val dob = dobEditText.text.toString()
            val documentType = documentTypeSpinner.selectedItem.toString()
            val documentNumber = documentNumberEditText.text.toString()

            if (validateFields(firstName, lastName, dob, documentNumber)) {
                val userProfile = Profile(firstName, lastName, dob, documentType, documentNumber)
                myPreferences.saveUserProfile(userProfile)
                Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish()
        }

        loadUserProfile()
    }

    private fun validateFields(firstName: String, lastName: String, dob: String, documentNumber: String): Boolean {
        return firstName.isNotBlank() && lastName.isNotBlank() && dob.isNotBlank() && documentNumber.isNotBlank()
    }

    private fun loadUserProfile() {
        val profile = myPreferences.getUserProfile()
        if (profile != null) {
            findViewById<EditText>(R.id.firstName).setText(profile.firstName)
            findViewById<EditText>(R.id.lastName).setText(profile.lastName)
            findViewById<EditText>(R.id.dob).setText(profile.dob)
            val documentTypeSpinner: Spinner = findViewById(R.id.documentType)
            val documentTypes = arrayOf("DNI", "Pasaporte")
            documentTypeSpinner.setSelection(documentTypes.indexOf(profile.documentType))
            findViewById<EditText>(R.id.documentNumber).setText(profile.documentNumber)
        }
    }
}

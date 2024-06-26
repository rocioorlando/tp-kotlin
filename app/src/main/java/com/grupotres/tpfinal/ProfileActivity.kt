package com.grupotres.tpfinal

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfileActivity : ComponentActivity() {

    private lateinit var myPreferences: MyPreferences
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val calendar = Calendar.getInstance()

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

            val (isValid, message) = validateFields(firstName, lastName, dob, documentNumber)
            if (isValid) {
                val userProfile = Profile(firstName, lastName, dob, documentType, documentNumber)
                myPreferences.saveUserProfile(userProfile)
                Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish()
        }

        loadUserProfile()
    }

    private fun validateFields(firstName: String, lastName: String, dob: String, documentNumber: String): Pair<Boolean, String> {
        if (firstName.isBlank()) return Pair(false, "El nombre no puede estar vacío")
        if (lastName.isBlank()) return Pair(false, "El apellido no puede estar vacío")
        if (dob.isBlank()) return Pair(false, "La fecha de nacimiento no puede estar vacía")
        if (!isValidAge(dob)) return Pair(false, "El usuario debe tener al menos 13 años")
        if (documentNumber.isBlank()) return Pair(false, "El número de documento no puede estar vacío")
        if (!isDocumentNumberValid(documentNumber)) return Pair(false, "El número de documento debe tener 8 dígitos")

        return Pair(true, "")
    }

    private fun isValidAge(dob: String): Boolean {
        val dateOfBirth: Date = try {
            dateFormat.parse(dob)
        } catch (e: Exception) {
            return false
        }

        // Calculamos la fecha mínima para tener al menos 13 años
        calendar.time = dateOfBirth
        calendar.add(Calendar.YEAR, 13)
        val minDateForAge = calendar.time

        // Comparamos con la fecha actual
        val currentDate = Date()
        return currentDate >= minDateForAge
    }

    private fun isDocumentNumberValid(documentNumber: String): Boolean {
        return documentNumber.length == 8 && documentNumber.all { it.isDigit() }
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

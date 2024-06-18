package com.grupotres.tpfinal

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveLogin(user: Login, keepSession: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putString("user", gson.toJson(user))
        editor.putBoolean("isSessionActive", keepSession)
        editor.apply()
    }

    fun getLogin(): Login? {
        val json = sharedPreferences.getString("user", null)
        return gson.fromJson(json, Login::class.java)
    }

    fun getSession(): Boolean{
        val session = sharedPreferences.getBoolean("isSessionActive", false)
        return session
    }

    fun clearLogin() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun saveInscription(inscription: String) {
        val inscriptions = getInscriptions().toMutableList()
        inscriptions.add(inscription)
        val editor = sharedPreferences.edit()
        editor.putString("inscriptions", gson.toJson(inscriptions))
        editor.apply()
    }

    fun getInscriptions(): List<String> {
        val json = sharedPreferences.getString("inscriptions", null)
        return if (json != null) {
            // se define el tipo de datos que se espera deserializar usando TypeToken. 
            // En este caso, TypeToken<List<String>>() indica que esperamos una lista de string.
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}

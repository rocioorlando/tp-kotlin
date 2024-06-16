package com.grupotres.tpfinal

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class MyPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUser(user: User, keepSession: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putString("user", gson.toJson(user))
        editor.putBoolean("isSessionActive", keepSession)
        editor.apply()
    }

    fun getUser(): User? {
        val json = sharedPreferences.getString("user", null)
        return gson.fromJson(json, User::class.java)
    }

    fun getSession(): Boolean{
        val session = sharedPreferences.getBoolean("isSessionActive", false)
        return session
    }

    fun clearUser() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}

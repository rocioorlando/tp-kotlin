package com.grupotres.tpfinal

class Login {
    var username: String
    var password: String
    var passwordUpdate: Boolean

    constructor() {
        this.username = ""
        this.password = ""
        this.passwordUpdate = false
    }

    constructor(username: String, password: String, passwordUpdate: Boolean) {
        this.username = username
        this.password = password
        this.passwordUpdate = passwordUpdate
    }

    fun getAllUser(): List<Login> {
        val userOne = Login("bmelgarejo", "123", false)
        val userTwo = Login("rocio", "123", false)
        val lista = listOf(userOne, userTwo)
        return lista
    }

    fun validateCredentials(username: String, password: String): Boolean {
        return getAllUser().any { it.username == username && it.password == password }
    }

}

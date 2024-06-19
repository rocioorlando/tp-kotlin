package com.grupotres.tpfinal

class Login {
    var username: String
    var password: String
    var email: String
    var passwordUpdate: Boolean

    constructor() {
        this.username = ""
        this.password = ""
        this.email = ""
        this.passwordUpdate = false
    }

    constructor(username: String, password: String, email: String, passwordUpdate: Boolean) {
        this.username = username
        this.password = password
        this.email = email
        this.passwordUpdate = passwordUpdate
    }

    fun getAllUser(): List<Login> {
        val userOne = Login("bmelgarejo", "123", "bmelgarejo@example.com", false)
        val userTwo = Login("rocio", "123", "rocio@example.com", false)
        val lista = listOf(userOne, userTwo)
        return lista
    }

    fun validateCredentials(username: String, password: String): Boolean {
        return getAllUser().any { it.username == username && it.password == password }
    }

    fun findUserByEmail(email: String): Login? {
        return getAllUser().find { it.email == email }
    }
}

package com.example.pin_it.API

    data class Problema(
        val id: Int,
        val latitude: Float,
        val longitude: Float,
        val titulo: String,
        val descricao: String,
        val imagem: String,
        val user_id: Int
)
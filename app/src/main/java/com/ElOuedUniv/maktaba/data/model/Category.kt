package com.ElOuedUniv.maktaba.data.model

// TODO: Complete the Category data class implementation
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val name: String,
    val description: String,
    val iconRes: Int
)
package com.example.data.network.profilephotomodel

data class Item(
    val album_id: Int,
    val date: Int,
    val has_tags: Boolean,
    val id: Int,
    val owner_id: Int,
    val sizes: List<Size>,
    val square_crop: String,
    val text: String
)
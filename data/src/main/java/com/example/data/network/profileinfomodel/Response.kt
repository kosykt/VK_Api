package com.example.data.network.profileinfomodel

data class Response(
    val bdate: String,
    val bdate_visibility: Int,
    val city: City,
    val country: Country,
    val first_name: String,
    val home_town: String,
    val id: Int,
    val last_name: String,
    val phone: String,
    val relation: Int,
    val relation_partner: RelationPartner,
    val sex: Int,
    val status: String
)
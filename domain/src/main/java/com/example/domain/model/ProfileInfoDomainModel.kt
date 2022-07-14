package com.example.domain.model

data class ProfileInfoDomainModel(
    val id: String,
    val bDate: String,
    val dDateVisibility: String,
    val firstName: String,
    val lastName: String,
    val homeTown: String,
    val city: String,
    val country: String,
    val phone: String,
    val relation: String,
    val sex: String,
    val status: String,
    val relationPartnerId: String,
    val relationPartnerFirstName: String,
    val relationPartnerLastName: String,
    val relationPartnerIsClosed: Boolean,
    val relationPartnerCanAccessClosed: Boolean,
): DomainModel()

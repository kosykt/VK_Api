package com.example.data

import com.example.data.network.profileinfomodel.ProfileInfoDTO
import com.example.domain.model.ProfileInfoDomainModel

fun ProfileInfoDTO.toProfileInfoDomainModel() = ProfileInfoDomainModel(
    id = this.response.id.toString(),
    bDate = this.response.bdate,
    dDateVisibility = this.response.bdate_visibility.toString(),
    firstName = this.response.first_name,
    lastName = this.response.last_name,
    homeTown = this.response.home_town,
    city = this.response.city.title,
    country = this.response.country.title,
    phone = this.response.phone,
    relation = this.response.relation.toString(),
    sex = this.response.sex.toString(),
    status = this.response.status,
    relationPartnerId = this.response.relation_partner.id.toString(),
    relationPartnerFirstName = this.response.relation_partner.first_name,
    relationPartnerLastName = this.response.relation_partner.last_name,
    relationPartnerIsClosed = this.response.relation_partner.is_closed,
    relationPartnerCanAccessClosed = this.response.relation_partner.can_access_closed,
)
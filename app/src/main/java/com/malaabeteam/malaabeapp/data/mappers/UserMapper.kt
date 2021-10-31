package com.malaabeteam.malaabeapp.data.mappers

import com.malaabeteam.malaabeapp.data.model.Gender
import com.malaabeteam.malaabeapp.data.model.User
import com.malaabeteam.malaabeapp.data.model.UserAddress
import com.malaabeteam.network.model.AddressDto
import com.malaabeteam.network.model.UserDto
import javax.inject.Inject

class UserMapper @Inject constructor() {

  fun fromNetwork(dto: UserDto) =
    User(
        userId = dto.userId,
        firstname = dto.firstname ?: "",
        lastname = dto.lastname ?: "",
        email = dto.email ?: "",
        phone = dto.phone ?: "",
        gender = dto.gender ?: "",
        role = dto.userRole ?: "",
        isDeleted = dto.isDeleted ?: false,
        cni = dto.cni ?: "",
        address = mapUserAddress(dto.address),
        addresses = dto.addresses?.map { mapUserAddress(it) } ?: emptyList(),
        password = dto.password ?: "",
        localisation = dto.localisation ?: ""
    )

  fun mapUserAddress(dto: AddressDto?) = UserAddress(
    dto?.addrType ?: "",
    dto?.address1 ?: "",
    dto?.city ?: ""
  )

  private fun genderFor(apiName: String?) = when (apiName) {
    Gender.MALE.apiName -> Gender.MALE
    Gender.FEMALE.apiName -> Gender.FEMALE
    else -> Gender.OTHER
  }
}

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
      id = dto.userId,
      firstName = dto.firstName ?: "",
      lastName = dto.lastName ?: "",
      imageUrl = dto.imageUrl ?: "",
      email = dto.email ?: "",
      username = dto.username ?: "",
      gender = genderFor(dto.gender),
      role = dto.userRole ?: "",
      isActive = dto.isActive ?: false,
      mobile = dto.mobile ?: "",
      alertsOn = dto.alertsOn ?: false,
      address = mapUserAddress(dto.address),
      addresses = dto.addresses?.map { mapUserAddress(it) } ?: emptyList(),
      rating = 0.0,
      resetPassword = dto.resetPassword ?: false,
      password = dto.password ?: ""
    )

  fun mapUserAddress(dto: AddressDto?) = UserAddress(
    dto?.id ?: -1,
    dto?.addrType ?: "",
    dto?.address1 ?: "",
    dto?.address2 ?: "",
    dto?.firstName ?: "",
    dto?.lastName ?: "",
    dto?.city ?: "",
    dto?.state ?: "",
    dto?.zipCode ?: ""
  )

  private fun genderFor(apiName: String?) = when (apiName) {
    Gender.MALE.apiName -> Gender.MALE
    Gender.FEMALE.apiName -> Gender.FEMALE
    else -> Gender.OTHER
  }
}

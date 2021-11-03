package com.malaabeteam.malaabeapp.data.mappers

import com.malaabeteam.malaabeapp.data.model.User
import com.malaabeteam.malaabeapp.data.model.UserAddress
import com.malaabeteam.network.model.AddressDto
import com.malaabeteam.network.model.UserDto
import javax.inject.Inject

class UserMapper @Inject constructor() {

  fun fromNetwork(dto: UserDto?) =
    User(
        _id = dto?._id ?: "",
        firstname = dto?.firstname ?: "Mouhameth",
        lastname = dto?.lastname ?: "Tall",
        email = dto?.email ?: "motall@ept.sn",
        phone = dto?.phone ?: "775621245",
        gender = dto?.gender ?: "M",
        role = dto?.role ?: "seeker",
        cni = dto?.cni ?: "1392 2006 011 68"
    )

  fun mapUserAddress(dto: AddressDto?) = UserAddress(
    dto?.addrType ?: "",
    dto?.address1 ?: "",
    dto?.city ?: ""
  )

}

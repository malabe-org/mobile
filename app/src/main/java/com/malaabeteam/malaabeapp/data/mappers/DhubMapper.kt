package com.malaabeteam.malaabeapp.data.mappers

import com.malaabeteam.malaabeapp.data.model.Dhubs
import com.malaabeteam.network.model.DhubsDto
import javax.inject.Inject

class DhubMapper @Inject constructor(
) {
  fun fromNetwork(dhubs: DhubsDto) =
    Dhubs(
    dhHubs = dhubs.dhHubs
  )
}

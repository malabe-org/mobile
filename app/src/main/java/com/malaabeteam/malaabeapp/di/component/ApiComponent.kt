package com.malaabeteam.malaabeapp.di.component

import com.malaabeteam.network.di.RetrofitModule
import dagger.Component

@Component(modules = [RetrofitModule::class])
interface ApiComponent

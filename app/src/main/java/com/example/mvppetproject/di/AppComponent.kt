package com.example.mvppetproject.di

import com.example.mvppetproject.MainActivity
import com.example.mvppetproject.base.BaseFragment
import com.example.mvppetproject.di.module.LocalNavigationModule
import com.example.mvppetproject.di.module.NavigationModule
import com.example.mvppetproject.di.module.NetworkModule
import com.example.mvppetproject.homeMVP.view.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NavigationModule::class,
        LocalNavigationModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: HomeFragment)

    fun inject(fragment: BaseFragment)
}
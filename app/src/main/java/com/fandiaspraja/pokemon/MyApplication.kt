package com.fandiaspraja.pokemon

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.work.Configuration
import com.fandiaspraja.pokemon.core.data.source.repository.PokemonRepository
import com.fandiaspraja.pokemon.core.di.dataStoreModule
import com.fandiaspraja.pokemon.core.di.databaseModule
import com.fandiaspraja.pokemon.core.di.networkModule
import com.fandiaspraja.pokemon.core.di.repositoryModule
import com.fandiaspraja.pokemon.di.useCaseModule
import com.fandiaspraja.pokemon.di.viewModelModule
import org.koin.android.ext.android.inject

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.get
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level



class MyApplication: Application()  {



    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    dataStoreModule
                )

            )
        }
    }
}
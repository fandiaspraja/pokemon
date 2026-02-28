package com.fandiaspraja.pokemon.core.di

import androidx.room.Room
import com.fandiaspraja.pokemon.core.data.source.repository.PokemonRepository
import com.fandiaspraja.pokemon.core.data.source.local.LocalDataSource
import com.fandiaspraja.pokemon.core.data.source.local.datastore.SessionDataStore
import com.fandiaspraja.pokemon.core.data.source.local.room.PokemonDatabase
import com.fandiaspraja.pokemon.core.data.source.remote.RemoteDataSource
import com.fandiaspraja.pokemon.core.data.source.remote.network.ApiService
import com.fandiaspraja.pokemon.core.data.source.repository.SessionRepository
import com.fandiaspraja.pokemon.core.domain.repository.IPokemonRepository
import com.fandiaspraja.pokemon.core.domain.repository.ISessionRepository
import com.fandiaspraja.pokemon.core.domain.usecase.SessionInteractor
import com.fandiaspraja.pokemon.core.domain.usecase.SessionUseCase
import com.fandiaspraja.pokemon.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<PokemonDatabase>().userDao() }
    factory { get<PokemonDatabase>().pokemonDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java, "Pokemon.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }


}



val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IPokemonRepository> { PokemonRepository(get(), get(), get(), get()) }
}

val dataStoreModule = module {

    // DataStore
    single { SessionDataStore(androidContext()) }

    // Repository
    single<ISessionRepository> { SessionRepository(get()) }

    // UseCase
    factory<SessionUseCase> { SessionInteractor(get()) }
}
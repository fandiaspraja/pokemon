package com.fandiaspraja.pokemon.di



import com.fandiaspraja.pokemon.core.domain.usecase.PokemonInteractor
import com.fandiaspraja.pokemon.core.domain.usecase.PokemonUseCase
import com.fandiaspraja.pokemon.core.domain.usecase.SessionInteractor
import com.fandiaspraja.pokemon.core.domain.usecase.SessionUseCase
import com.fandiaspraja.pokemon.ui.detail.DetailViewModel
import com.fandiaspraja.pokemon.ui.home.HomeViewModel
import com.fandiaspraja.pokemon.ui.login.LoginViewModel
import com.fandiaspraja.pokemon.ui.profile.ProfileViewModel
import com.fandiaspraja.pokemon.ui.register.RegisterViewModel
import com.fandiaspraja.pokemon.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<PokemonUseCase> { PokemonInteractor(get()) }
    factory<SessionUseCase> { SessionInteractor(get()) }

}

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}

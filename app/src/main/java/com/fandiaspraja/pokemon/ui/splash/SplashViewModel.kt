package com.fandiaspraja.pokemon.ui.splash

import androidx.lifecycle.ViewModel
import com.fandiaspraja.pokemon.core.domain.usecase.SessionUseCase

class SplashViewModel(
    private val sessionUseCase: SessionUseCase
) : ViewModel() {

    val isLogin = sessionUseCase.isLogin()
}
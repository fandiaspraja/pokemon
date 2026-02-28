package com.fandiaspraja.pokemon.core.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.fandiaspraja.pokemon.core.data.source.repository.PokemonRepository

class PokemonWorkerFactory(
    private val repo: PokemonRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            PokemonSyncWorker::class.java.name -> {
                PokemonSyncWorker(appContext, workerParameters)
            }
            else -> null
        }
    }
}
package com.fandiaspraja.pokemon.core.data.worker

import android.content.Context
import androidx.work.WorkerParameters
import androidx.work.CoroutineWorker
import com.fandiaspraja.pokemon.core.data.source.repository.PokemonRepository
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class PokemonSyncWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params), KoinComponent {

    private val repo: PokemonRepository by inject()


    override suspend fun doWork(): Result {
        return try {
            repo.refresh()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
package com.fandiaspraja.pokemon.core.data.scheduler

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.fandiaspraja.pokemon.core.data.worker.PokemonSyncWorker
import java.util.concurrent.TimeUnit

object SyncScheduler {

    fun start(context: Context) {

        val request = PeriodicWorkRequestBuilder<PokemonSyncWorker>(
            1, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "pokemon_sync",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )

        // initial sync langsung
        WorkManager.getInstance(context)
            .enqueue(OneTimeWorkRequest.from(PokemonSyncWorker::class.java))
    }
}
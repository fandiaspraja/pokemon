package com.fandiaspraja.pokemon.core.data.source.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LOG_TAG
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.fandiaspraja.pokemon.core.utils.DataMapper
import com.fandiaspraja.pokemon.core.data.NetworkBoundResource
import com.fandiaspraja.pokemon.core.data.Resource
import com.fandiaspraja.pokemon.core.data.mediator.PokemonRemoteMediator
import com.fandiaspraja.pokemon.core.data.source.local.LocalDataSource
import com.fandiaspraja.pokemon.core.data.source.remote.RemoteDataSource
import com.fandiaspraja.pokemon.core.data.source.remote.network.ApiResponse
import com.fandiaspraja.pokemon.core.data.source.remote.response.PokemonDetailResponse
import com.fandiaspraja.pokemon.core.data.source.remote.response.PokemonResponse
import com.fandiaspraja.pokemon.core.domain.model.Pokemon
import com.fandiaspraja.pokemon.core.domain.model.PokemonDetail
import com.fandiaspraja.pokemon.core.domain.model.User
import com.fandiaspraja.pokemon.core.domain.repository.IPokemonRepository
import com.fandiaspraja.pokemon.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class PokemonRepository (
    private val remoteDataSource : RemoteDataSource,
    private val localDataSource : LocalDataSource,
    private val appExecutors: AppExecutors
) : IPokemonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemons(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            remoteMediator = PokemonRemoteMediator(
                remote = remoteDataSource,
                local = localDataSource
            ),
            pagingSourceFactory = {
                localDataSource.getAllPokemons()
            }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                Log.d("PokemonRepository", "entitas: $entity")

                DataMapper.mapEntityToDomainPokemon(entity)
            }
        }
    }

    override fun getPokemonDetail(name: String): Flow<Resource<PokemonDetail>> = flow {
        emit(Resource.Loading())

        try {
            val response = remoteDataSource.getPokemonDetail(name).first()

            when (response) {
                is ApiResponse.Success -> {
                    val domain = DataMapper.mapResponseToDomainPokemonDetail(response.data)
                    emit(Resource.Success(domain))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("Data kosong"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }


    override suspend fun insertUser(user: User) {
        val entity = DataMapper.mapDomainToEntityUser(user)
        localDataSource.insertUser(user = entity)
//        appExecutors.diskIO().execute { localDataSource.insertUser(user = entity)  }

    }

    override fun getAllUsers(): Flow<List<User>> {
        return localDataSource.getAllUsers()
            .map { DataMapper.mapListEntityToDomainUsers(it) }
    }

    override fun getUserById(id: Int): Flow<User?> {
        return localDataSource.getUserById(id)
            .map { it?.let { DataMapper.mapEntityToDomainUser(it) } }
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): User? {
        val entity = localDataSource.loginUser(email, password)
        return entity?.let { DataMapper.mapEntityToDomainUser(it) }
    }

    override fun getAllPokemons(): Flow<List<Pokemon>> {
        TODO("Not yet implemented")
    }

    override fun getPokemonByName(name: String): Flow<Pokemon?> {
        return localDataSource.getPokemonByName(name)
            .map { it?.let { DataMapper.mapEntityToDomainPokemon(it) } }
    }

    override suspend fun insertAllPokemon(data: List<Pokemon>) {
        TODO("Not yet implemented")

    }

    override suspend fun clearAllPokemon() {
        TODO("Not yet implemented")
    }
}
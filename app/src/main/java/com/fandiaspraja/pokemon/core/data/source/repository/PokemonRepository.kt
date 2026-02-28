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
//    override fun getPokemonRemote(
//        limit: Int,
//        offset: Int
//    ): Flow<Resource<List<Pokemon>>> =
//        object : NetworkBoundResource<List<Pokemon>, List<PokemonResponse>>(appExecutors) {
//            override fun loadFromDB(): Flow<List<Pokemon>> {
//
//                return localDataSource.getAllLocalPokemon().map {
//                    Log.d("PokemonRepository", "loadFromDB: ${it.size}")
//
//                    DataMapper.mapListEntityToDomainPokemons(it) }
//            }
//
//            override fun shouldFetch(data: List<Pokemon>?): Boolean =
//                data == null || data.isEmpty()
//
//            override suspend fun createCall(): Flow<ApiResponse<List<PokemonResponse>>> {
//                return remoteDataSource.getPokemons(limit = limit, offset = offset).map { apiResponse ->
//                    // Hapus 'as' dan biarkan 'when' menangani tipe secara otomatis
//                    when (apiResponse) {
//                        is ApiResponse.Success -> {
//                            // 1. Ambil list dari response (yang mungkin masih nullable)
//                            val nullableList = apiResponse.data
//
//                            // 2. Filter item yang null dari list.
//                            //    Ini PENTING untuk memastikan tipenya menjadi List<PokemonResponse>
//                            val nonNullList = nullableList()
//
//                            Log.d("PokemonRepository", "pokemonList from network: ${nonNullList.size} items")
//
//                            // 3. Kirim list yang sudah bersih dari null
//                            ApiResponse.Success(nonNullList)
//                        }
//
//                        is ApiResponse.Error -> {
//                            ApiResponse.Error(apiResponse.errorMessage)
//                        }
//
//                        is ApiResponse.Empty -> {
//                            ApiResponse.Empty
//                        }
//                    }
//                }
//            }
//
//            override suspend fun saveCallResult(data: List<PokemonResponse>) {
////                Log.d("PokemonRepository", "saveCallResul tResponse: ${data.size} items")
////                val tourismList = DataMapper.mapListResponseToEntityPokemons(data)
////                Log.d("PokemonRepository", "saveCallResult: ${tourismList.size} items")
////                localDataSource.insertAllPokemon(tourismList)
//            }
//        }.asFlow()

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


//    override fun getPokemonDetail(name: String): Flow<Resource<PokemonDetail>> =
//        object : NetworkBoundResource<PokemonDetail, PokemonDetailResponse>(appExecutors) {
//
//            override fun loadFromDB(): Flow<PokemonDetail> {
//                return flowOf()
////                return localDataSource.getPokemonByName(name)
////                    .map { entity ->
////                        DataMapper.mapEntityToDomainPokemonDetail(entity)
////                    }
//            }
//
//            override fun shouldFetch(data: PokemonDetail?): Boolean {
//                return true
////                data == null // atau tambahkan expired logic
//            }
//
//            override suspend fun createCall(): Flow<ApiResponse<PokemonDetailResponse>> {
//                return remoteDataSource.getPokemonDetail(name)
//            }
//
//            override suspend fun saveCallResult(data: PokemonDetailResponse) {
////                val entity = DataMapper.mapResponseToEntityPokemonDetail(data)
////                localDataSource.insertPokemonDetail(entity)
//            }
//
//        }.asFlow()


//    =
//    object : NetworkBoundResource<PokemonDetail, PokemonDetailResponse>(appExecutors) {
//
//        override fun loadFromDB(): Flow<PokemonDetail> {
//            return localDataSource.getPokemonByName(name)
//                .map { DataMapper.mapEntityToDomainDetail(it) }
//        }
//
//        override fun shouldFetch(data: PokemonDetail?): Boolean = data == null
//
//        override suspend fun createCall(): Flow<ApiResponse<PokemonDetailResponse>> {
//            return remoteDataSource.getPokemonDetail(name)
//        }
//
//        override suspend fun saveCallResult(data: PokemonDetailResponse) {
//            val entity = DataMapper.mapDetailResponseToEntity(data)
//            localDataSource.insertPokemonDetail(entity)
//        }
//
//    }.asFlow()

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
package com.fandiaspraja.pokemon.core.data

import com.fandiaspraja.pokemon.core.data.source.remote.network.ApiResponse
import com.fandiaspraja.pokemon.core.utils.AppExecutors
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}

//abstract class NetworkBoundResource<ResultType, ResponseType> {
//
//    fun asFlow(): Flow<Resource<ResultType>> = flow {
//        emit(Resource.Loading())
//        try {
//            when (val apiResponse = createCall().first()) { // <-- collect first emission
//                is ApiResponse.Success -> {
//                    val mappedData = mapResponse(apiResponse.data)
//                    emit(Resource.Success(mappedData))
//                }
//                is ApiResponse.Empty -> {
//                    emit(Resource.Success(emptyData()))
//                }
//                is ApiResponse.Error -> {
//                    emit(Resource.Error(apiResponse.errorMessage))
//                }
//            }
//        } catch (e: Exception) {
//            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
//        }
//    }
//
//    // Now createCall returns a Flow
//    protected abstract suspend fun createCall(): Flow<ApiResponse<ResponseType>>
//
//    protected abstract fun mapResponse(data: ResponseType): ResultType
//
//    protected open fun emptyData(): ResultType {
//        throw NotImplementedError("Provide empty data handling if needed")
//    }
//}

//abstract class NetworkBoundResource<ResultType, RequestType> {
//
//    private var result: Flow<Resource<ResultType>> = flow {
//        emit(Resource.Loading())
//        val dbSource = loadFromDB().first()
//        if (shouldFetch(dbSource)) {
//            emit(Resource.Loading())
//            when (val apiResponse = createCall().first()) {
//                is ApiResponse.Success -> {
//                    saveCallResult(apiResponse.data)
//                    emitAll(loadFromDB().map { Resource.Success(it) })
//                }
//                is ApiResponse.Empty -> {
//                    emitAll(loadFromDB().map { Resource.Success(it) })
//                }
//                is ApiResponse.Error -> {
//                    onFetchFailed()
//                    emit(Resource.Error(apiResponse.errorMessage))
//                }
//            }
//        } else {
//            emitAll(loadFromDB().map { Resource.Success(it) })
//        }
//    }
//
//    protected open fun onFetchFailed() {}
//
//    protected abstract fun loadFromDB(): Flow<ResultType>
//
//    protected abstract fun shouldFetch(data: ResultType?): Boolean
//
//    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>
//
//    protected abstract suspend fun saveCallResult(data: RequestType)
//
//    fun asFlow(): Flow<Resource<ResultType>> = result
//}
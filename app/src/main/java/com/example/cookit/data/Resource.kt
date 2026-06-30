package com.example.cookit.data

/**
 * Wrapper exposing the result of a NetworkBoundResource-style load.
 *
 * [Success.fromCache] tags the cache-first emission (favorite shown from Room) so the UI can
 * distinguish stale/offline data from a fresh network result.
 */
sealed interface Resource<out T> {
    object Loading : Resource<Nothing>

    data class Success<out T>(val data: T, val fromCache: Boolean = false) : Resource<T>

    sealed interface Error : Resource<Nothing> {
        object OfflineNoCache : Error
        data class Network(val message: String?) : Error
    }
}

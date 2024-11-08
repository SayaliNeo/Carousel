package com.app.carousel.domain.model

class Resource<T>(val status: ResourceState, val data: T? = null, val error: String? = null) {

    override fun toString(): String {
        return "Resource(status=$status, data=$data, error=$error)"
    }

    companion object {

        fun <T> loading(): Resource<T> {
            return Resource(ResourceState.LOADING)
        }

        fun <T> error(e: Throwable): Resource<T> {
            return Resource(ResourceState.ERROR, error = e.message)
        }

        fun <T> success(data: T? = null): Resource<T> {
            return Resource(ResourceState.SUCCESS, data = data)
        }
    }
}

enum class ResourceState {
    LOADING,
    SUCCESS,
    ERROR
}

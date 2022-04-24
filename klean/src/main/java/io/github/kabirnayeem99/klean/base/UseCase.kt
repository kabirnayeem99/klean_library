package io.github.kabirnayeem99.klean.base

import io.github.kabirnayeem99.klean.wrappers.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Resource<Type>

    operator fun invoke(
        params: Params,
        scope: CoroutineScope,
        onResult: (Resource<Type>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }

}
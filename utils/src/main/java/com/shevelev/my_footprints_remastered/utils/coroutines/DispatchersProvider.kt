package com.shevelev.my_footprints_remastered.utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    val uiDispatcher: CoroutineDispatcher
    val calculationsDispatcher: CoroutineDispatcher
    val ioDispatcher: CoroutineDispatcher
}
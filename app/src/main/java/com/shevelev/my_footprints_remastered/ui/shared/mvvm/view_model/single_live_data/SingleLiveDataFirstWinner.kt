package com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.single_live_data

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 *
 *
 * This implementation lets to pass a very first value to a very first observer
 * It't very useful when we send a value in a constructor of ViewModel
 *
 * @note from here: https://github.com/googlesamples/android-architecture/blob/dev-todo-mvvm-live/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/SingleLiveEvent.java
 */
class SingleLiveDataFirstWinner<T> : SingleLiveDataBase<T>() {
    private var firstValue = true

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)

        if(firstValue) {
            wrapper.newValue()
            firstValue = false
        }

        super.observe(owner, wrapper)
    }
}
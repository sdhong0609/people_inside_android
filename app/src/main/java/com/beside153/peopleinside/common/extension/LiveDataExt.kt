package com.beside153.peopleinside.common.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.util.EventObserver

fun <T> LiveData<Event<T>>.eventObserve(
    lifecycleOwner: LifecycleOwner,
    onEventUnhandledContent: (T) -> Unit
) {
    observe(
        lifecycleOwner,
        EventObserver {
            onEventUnhandledContent(it)
        }
    )
}

package com.herve.library.commonlibrary.utils.lifecycler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

public class RxLifecycleUtils {

    private RxLifecycleUtils() {
        throw new IllegalStateException("Can't instance the RxLifecycleUtils");
    }

    public static <T> AutoDisposeConverter<T> bindLifecycle(
            LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner));
    }

    public static <T> AutoDisposeConverter<T> bindLifecycle(
            LifecycleOwner lifecycleOwner, Lifecycle.Event untilEvent) {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner, untilEvent));
    }
}
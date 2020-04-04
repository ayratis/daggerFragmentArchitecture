package com.ayratis.dagger_fragment_arch.my_fragment

import androidx.lifecycle.ViewModel
import com.ayratis.dagger_fragment_arch.system.navigtation.Router
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MyViewModel @Inject constructor(
    @Named("argId") private val argId: Int,
    private val router: Router
): ViewModel() {
    private val titleRelay = BehaviorRelay.createDefault("id: $argId")
    val title: Observable<String> = titleRelay

    fun onNextClick() {
        router.proceedCommand(MyNavCommand.Next(argId + 1))
    }
}
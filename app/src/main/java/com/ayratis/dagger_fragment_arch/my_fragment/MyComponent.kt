package com.ayratis.dagger_fragment_arch.my_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ayratis.dagger_fragment_arch.system.ViewModelKey
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap
import javax.inject.Named

@Subcomponent(modules = [MyComponent.MyModule::class])
interface MyComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @Named("argId") @BindsInstance argId: Int
        ): MyComponent
    }

    @Module
    abstract class MyModule {

        @Binds
        @IntoMap
        @ViewModelKey(MyViewModel::class)
        abstract fun bindMyViewModel(myViewModel: MyViewModel): ViewModel
    }
}
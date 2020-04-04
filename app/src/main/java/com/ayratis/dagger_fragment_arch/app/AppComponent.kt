package com.ayratis.dagger_fragment_arch.app

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.ayratis.dagger_fragment_arch.flow.FlowComponent
import com.ayratis.dagger_fragment_arch.flow.FlowFragment
import com.ayratis.dagger_fragment_arch.system.AppFragmentFactory
import com.ayratis.dagger_fragment_arch.system.AppViewModelFactory
import com.ayratis.dagger_fragment_arch.system.FragmentKey
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Singleton
@Component(modules = [AppComponent.AppModule::class])
interface AppComponent {

    fun fragmentFactory(): FragmentFactory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    @Module(subcomponents = [FlowComponent::class])
    abstract class AppModule {

        @Binds
        abstract fun bindViewModelFactory(viewModelFactory: AppViewModelFactory): ViewModelProvider.Factory

        @Binds
        abstract fun bindFragmentFactory(fragmentFactory: AppFragmentFactory): FragmentFactory

        @Binds
        @IntoMap
        @FragmentKey(FlowFragment::class)
        abstract fun bindFlowFragment(fragment: FlowFragment): Fragment

//        companion object {
//            @Provides
//            @Singleton
//            fun provideFlowComponentManager(
//                flowComponentFactory: FlowComponent.Factory
//            ): FlowComponentManager = FlowComponent.Manager(flowComponentFactory)
//        }
    }
}
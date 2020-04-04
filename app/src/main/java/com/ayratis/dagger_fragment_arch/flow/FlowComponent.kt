package com.ayratis.dagger_fragment_arch.flow

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.ayratis.dagger_fragment_arch.my_fragment.MyComponent
import com.ayratis.dagger_fragment_arch.my_fragment.MyFragment
import com.ayratis.dagger_fragment_arch.system.FragmentKey
import com.ayratis.dagger_fragment_arch.system.navigtation.Coordinator
import com.ayratis.dagger_fragment_arch.system.navigtation.Router
import dagger.*
import dagger.multibindings.IntoMap
import javax.inject.Named

@FlowScope
@Subcomponent(modules = [FlowComponent.FlowModule::class])
interface FlowComponent {

    fun fragmentFactory(): FragmentFactory
    fun coordinator(): Coordinator

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named("flowName") name: String
        ): FlowComponent
    }

    class Manager(private val flowComponentFactory: Factory) {
        private var component: FlowComponent? = null

        fun openComponent(name: String): FlowComponent {
            if (component == null) {
                component = flowComponentFactory.create(name)
            }
            return component!!
        }

        fun closeComponent() {
            component = null
        }
    }

    @Module(subcomponents = [MyComponent::class])
    abstract class FlowModule {

        @Binds
        @IntoMap
        @FragmentKey(MyFragment::class)
        abstract fun bindMyFragment(fragment: MyFragment): Fragment

        companion object {
            @Provides
            @FlowScope
            fun provideMyRouter(): Coordinator = Coordinator()

            @Provides
            @FlowScope
            fun router(coordinator: Coordinator): Router = coordinator.router
        }
    }
}
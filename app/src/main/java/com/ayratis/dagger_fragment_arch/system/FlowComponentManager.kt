package com.ayratis.dagger_fragment_arch.system

import com.ayratis.dagger_fragment_arch.flow.FlowComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlowComponentManager @Inject constructor(
    private val flowComponentFactory: FlowComponent.Factory
){
    private val components = HashMap<String, FlowComponent>()

    fun openComponent(componentKey: String, name: String): FlowComponent {
        val existingComponent = components[componentKey]
        if (existingComponent != null) {
            return existingComponent
        } else {
            val newComponent = flowComponentFactory.create(name)
            components[componentKey] = newComponent
            return newComponent
        }
    }

    fun closeComponent(componentKey: String) {
        components.remove(componentKey)
    }
}
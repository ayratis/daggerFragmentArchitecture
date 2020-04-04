package com.ayratis.dagger_fragment_arch.system.navigtation

import java.util.*


class Coordinator {

    private var pendingCommands = LinkedList<NavCommand>()
    private var navigator: Navigator? = null

    val router = object : Router {
        override fun proceedCommand(navCommand: NavCommand) {
            if (navigator != null) {
                navigator?.navigate(navCommand)
            } else {
                pendingCommands.add(navCommand)
            }
        }
    }

    fun setNavigator(navigator: Navigator) {
        this.navigator = navigator
        while (pendingCommands.isNotEmpty()) {
            if (this.navigator != null) {
                @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                this.navigator?.navigate(pendingCommands.poll())
            } else {
                break
            }
        }
    }

    fun removeNavigator() {
        navigator = null
    }
}
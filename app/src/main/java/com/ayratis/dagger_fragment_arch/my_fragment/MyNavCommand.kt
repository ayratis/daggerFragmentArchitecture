package com.ayratis.dagger_fragment_arch.my_fragment

import com.ayratis.dagger_fragment_arch.system.navigtation.NavCommand

sealed class MyNavCommand: NavCommand {
    data class Next(val id: Int): MyNavCommand()
}
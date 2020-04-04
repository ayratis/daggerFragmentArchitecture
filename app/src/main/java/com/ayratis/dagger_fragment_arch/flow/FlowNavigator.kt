package com.ayratis.dagger_fragment_arch.flow

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.ayratis.dagger_fragment_arch.R
import com.ayratis.dagger_fragment_arch.my_fragment.MyFragment
import com.ayratis.dagger_fragment_arch.my_fragment.MyNavCommand
import com.ayratis.dagger_fragment_arch.system.navigtation.NavCommand
import com.ayratis.dagger_fragment_arch.system.navigtation.Navigator

class FlowNavigator(private val fragmentManager: FragmentManager) : Navigator {

    override fun navigate(navCommand: NavCommand) {
        when (navCommand) {
            is MyNavCommand.Next -> {
                fragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainer,
                        MyFragment::class.java,
                        Bundle().apply { putInt(MyFragment.ARG_ID, navCommand.id) }
                    )
                    .addToBackStack(MyFragment::class.java.canonicalName)
                    .commit()
            }
        }
    }

    fun showInitialScreen() {
        fragmentManager.beginTransaction().replace(
            R.id.fragmentContainer,
            MyFragment::class.java,
            Bundle().apply { putInt(MyFragment.ARG_ID, 1) }
        ).commitNow()
    }

    fun onBackPressed() {
        fragmentManager.popBackStack()
    }
}
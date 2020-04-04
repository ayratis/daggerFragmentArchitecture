package com.ayratis.dagger_fragment_arch.flow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentFactory
import com.ayratis.dagger_fragment_arch.R
import com.ayratis.dagger_fragment_arch.system.BaseFragment
import com.ayratis.dagger_fragment_arch.system.FlowComponentManager
import com.ayratis.dagger_fragment_arch.system.navigtation.Coordinator
import kotlinx.android.synthetic.main.fragment_flow.*
import javax.inject.Inject

class FlowFragment @Inject constructor(
    private val flowComponentManager: FlowComponentManager
) : BaseFragment(R.layout.fragment_flow) {

    companion object {
        const val ARG_FLOW_NAME = "arg flow name"
        const val ARG_FLOW_COLOR = "arg flow color"
    }

    private val flowName: String by lazy {
        requireArguments().getString(ARG_FLOW_NAME, "")
    }
    private val flowColor: Int by lazy {
        requireArguments().getInt(ARG_FLOW_COLOR)
    }
    private val flowComponent: FlowComponent by lazy {
        flowComponentManager.openComponent(fragmentInstanceKey, flowName)
    }
    private val fragmentFactory: FragmentFactory by lazy {
        flowComponent.fragmentFactory()
    }
    private val coordinator: Coordinator by lazy {
        flowComponent.coordinator()
    }
    private val navigator: FlowNavigator by lazy {
        FlowNavigator(childFragmentManager)
    }

    override fun onFirstCreate() {
        childFragmentManager.fragmentFactory = fragmentFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.showInitialScreen()
        }
        flowTitleTextView.text = flowName
        flowLayout.setBackgroundColor(flowColor)
    }

    override fun onBackPressed() {
        navigator.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        coordinator.setNavigator(navigator)
    }

    override fun onPause() {
        coordinator.removeNavigator()
        super.onPause()
    }

    override fun onCleared() {
        flowComponentManager.closeComponent(fragmentInstanceKey)
    }
}
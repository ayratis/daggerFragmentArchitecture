package com.ayratis.dagger_fragment_arch.my_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ayratis.dagger_fragment_arch.R
import com.ayratis.dagger_fragment_arch.system.BaseFragment
import kotlinx.android.synthetic.main.fragment.*
import javax.inject.Inject

class MyFragment @Inject constructor(
    private val myComponentFactory: MyComponent.Factory
) : BaseFragment(R.layout.fragment) {

    companion object {
        const val ARG_ID = "arg num"
    }

    private val argId: Int by lazy {
        requireArguments().getInt(ARG_ID)
    }
    private val viewModel: MyViewModel by viewModels {
        myComponentFactory.create(argId).viewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextButton.setOnClickListener { viewModel.onNextClick() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.title
            .subscribe { fragmentTitleTextView.text = it }
            .disposeOnPause()
    }
}
package com.ayratis.dagger_fragment_arch.system

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment(@LayoutRes containerLayoutId: Int) : Fragment(containerLayoutId) {

    companion object {
        private const val STATE_SCOPE_NAME = "state_scope_name"
    }

    private var instanceStateSaved: Boolean = false
    private val disposeOnPauseDisposables = CompositeDisposable()
    lateinit var fragmentInstanceKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentInstanceKey = savedInstanceState?.getString(STATE_SCOPE_NAME)
            ?: "${javaClass.simpleName}_${hashCode()}"
        onFirstCreate()
        super.onCreate(savedInstanceState)
    }

    protected open fun onFirstCreate() {
    }

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
        outState.putString(STATE_SCOPE_NAME, fragmentInstanceKey)
    }

    override fun onPause() {
        disposeOnPauseDisposables.clear()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needCloseScope()) {
            //destroy this fragment with scope
            onCleared()
        }
    }

    //This is android, baby!
    private fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved) //because isRemoving == true for fragment in backstack on screen rotation
                || ((parentFragment as? BaseFragment)?.isRealRemoving() ?: false)

    //It will be valid only for 'onDestroy()' method
    private fun needCloseScope(): Boolean =
        when {
            activity?.isChangingConfigurations == true -> false
            activity?.isFinishing == true -> true
            else -> isRealRemoving()
        }

    protected open fun onCleared() {}
    open fun onBackPressed() {}

    protected fun Disposable.disposeOnPause() {
        disposeOnPauseDisposables.add(this)
    }
}
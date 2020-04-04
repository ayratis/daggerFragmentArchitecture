package com.ayratis.dagger_fragment_arch.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ayratis.dagger_fragment_arch.R
import com.ayratis.dagger_fragment_arch.flow.FlowFragment
import com.ayratis.dagger_fragment_arch.system.BaseFragment

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG_FLOW_1 = "flow1"
        private const val TAG_FLOW_2 = "flow2"
    }

    private val appComponent by lazy { (applicationContext as App).appComponent }

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = appComponent.fragmentFactory()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
//            selectTab(TAG_FLOW_1)
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    FlowFragment::class.java,
                    createFlowFragmentBundle(TAG_FLOW_1),
                    TAG_FLOW_1
                ).commitNow()

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer2,
                    FlowFragment::class.java,
                    createFlowFragmentBundle(TAG_FLOW_2),
                    TAG_FLOW_2
                ).commitNow()
        }
//        flow1Button.setOnClickListener { selectTab(TAG_FLOW_1) }
//        flow2Button.setOnClickListener { selectTab(TAG_FLOW_2) }
    }

    private fun selectTab(tag: String) {
        val newFragment = supportFragmentManager.findFragmentByTag(tag)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) {
            return
        }
        supportFragmentManager.beginTransaction().apply {
            if (newFragment == null) {
                add(
                    R.id.fragmentContainer,
                    FlowFragment::class.java,
                    createFlowFragmentBundle(tag),
                    tag
                )
            }
            currentFragment?.let { detach(it) }
            newFragment?.let { attach(it) }
        }.commitNow()
    }

    private fun createFlowFragmentBundle(tag: String) =
        Bundle().apply {
            putString(FlowFragment.ARG_FLOW_NAME, tag)
            putInt(
                FlowFragment.ARG_FLOW_COLOR,
                ContextCompat.getColor(
                    this@MainActivity,
                    if (tag == TAG_FLOW_1) R.color.colorAccent
                    else R.color.colorPrimary
                )
            )
        }

    override fun onBackPressed() {
        currentFragment?.onBackPressed()
    }
}

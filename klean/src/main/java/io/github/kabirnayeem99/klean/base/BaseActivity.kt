package io.github.kabirnayeem99.klean.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.github.kabirnayeem99.klean.R

private const val TAG = "BaseActivity"

/**
 * This abstract [BaseActivity] class defines all the common behaviour of all the activities
 * of this application and this abstract class,
 * have abstract methods for the differences,
 * which can be overridden in the actual implementations.
 *
 * Also this makes implementing [ViewDataBinding] a lot easier.
 */
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: B

    @get:LayoutRes
    protected abstract val layout: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        binding = DataBindingUtil.setContentView(this, layout)
        binding.lifecycleOwner = this
    }

}


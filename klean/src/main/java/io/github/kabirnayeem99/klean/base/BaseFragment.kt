package io.github.kabirnayeem99.klean.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.github.kabirnayeem99.klean.ktx.hideKeyboard

/**
 * This abstract [BaseFragment] class defines all the common behaviour of all the fragments
 * of this application and this abstract class,
 * have abstract methods for the differences,
 * which can be overridden in the actual implementations.
 *
 * Also this makes implementing [ViewDataBinding] a lot easier.
 */
abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    // by making binding null, we are making sure that, it is not leaking
    private var _binding: B? = null
    val binding
        get() = _binding!!


    @get:LayoutRes
    protected abstract val layout: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate<B>(inflater, layout, container, false)
                .also { currentBinding ->
                    currentBinding.lifecycleOwner = this.viewLifecycleOwner
                }

        return binding.root
    }


    override fun onDestroyView() {
        binding.root.hideKeyboard()
        super.onDestroyView()
        _binding = null
    }
}
package io.github.kabirnayeem99.klean.ktx

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import io.github.kabirnayeem99.klean.utility.CROSS_FADE_DURATION

private const val TAG = "View"

fun View.hideKeyboard(): Boolean {
    return try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
        Log.e(TAG, "hideKeyboard: ${ignored.localizedMessage}")
        false
    }

}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}


/**
 * A Kotlin extension function that is used to clear the text of a TextView.
 *
 * @receiver [TextView]
 */
fun TextView.clear() {
    text = ""
}

/**
 * Used to hide or show the view with animation.
 *
 * @receiver [View]
 * @param visibility Visibility
 */
fun View.viewVisibility(visibility: Int, transitionTime: Long = CROSS_FADE_DURATION) {
    val transition: Transition = Fade()
    transition.duration = transitionTime
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    this.visibility = visibility
}


fun View.slideDown() {
    animate()
        .translationY(height.toFloat())
        .alpha(0f)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // superfluous restoration
                visibility = View.GONE
                alpha = 1f
                translationY = 0f
            }
        })
}


fun View.slideUp() {
    visibility = View.VISIBLE
    alpha = 0f
    if (height > 0) {
        slideUpNow()
    } else {
        post { slideUpNow() }
    }
}

private fun View.slideUpNow() {
    translationY = height.toFloat()
    animate()
        .translationY(0f)
        .alpha(1f)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.VISIBLE
                alpha = 1f
            }
        })
}

fun View.onThrottledClick(
    throttleDelay: Long = 500L,
    onClick: (View) -> Unit
) {
    setOnClickListener {
        onClick(this)
        isClickable = false
        postDelayed({ isClickable = true }, throttleDelay)
    }
}


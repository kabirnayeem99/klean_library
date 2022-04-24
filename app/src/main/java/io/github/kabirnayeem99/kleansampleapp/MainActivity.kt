package io.github.kabirnayeem99.kleansampleapp

import android.os.Bundle
import android.widget.ImageView
import com.chayangkoon.champ.glide.ktx.load
import io.github.kabirnayeem99.klean.base.BaseActivity
import io.github.kabirnayeem99.kleansampleapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ivImage = findViewById<ImageView>(R.id.iv_image)

        ivImage.load("https://www.erblicken.com/wp-content/uploads/2016/06/Funny_Gabs.jpg")
    }

    override val layout: Int
        get() = R.layout.activity_main
}
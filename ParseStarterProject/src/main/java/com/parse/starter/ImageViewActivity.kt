package com.parse.starter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_image_view.*

class ImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)
        var p = intent.extras
        var username = p.getString("Username")
        showPhotos(getApplicationContext(),username,linearLayoutImages)
        setTitle("$username's feed")
    }

}


package com.example.testings.ui.news

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.testings.R
import com.squareup.picasso.Picasso


class ImageActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_news_open_image)

        val toolbar = supportActionBar
        toolbar?.title = "Изображение"
        toolbar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setBackgroundDrawable(ColorDrawable(Color.BLACK))

        val intent: Intent = getIntent()
        Picasso.get()
            .load(intent.getStringExtra("ImageUrl"))
            .into(findViewById<ImageView>(R.id.ImageActivity_image))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
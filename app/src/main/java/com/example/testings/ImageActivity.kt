package com.example.testings

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso


class ImageActivity: AppCompatActivity() {

    private var imageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_news_open_image)

        val toolbar = supportActionBar
        toolbar?.title = "Изображение"
        toolbar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setBackgroundDrawable(ColorDrawable(Color.BLACK))


        val intent: Intent = getIntent()
        imageUrl = intent.getStringExtra("ImageUrl") as String
        if (imageUrl.startsWith("http://sibsu.ru/wp-content/uploads/")){
            Picasso.get()
                .load(imageUrl)
                .into(findViewById<ImageView>(R.id.ImageActivity_image))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
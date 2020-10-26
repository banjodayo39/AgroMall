package com.banjodayo.agromall.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun loadCircularImage(context: Context, imageView: ImageView, imageUrl: String?, placeHolder: Int) {
    if (imageUrl != null && imageUrl.isNotEmpty()) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(placeHolder)
            .apply(RequestOptions.circleCropTransform())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    } else {
        imageView.setImageResource(placeHolder)
    }
}

fun loadImage(context: Context, imageView: ImageView, imageUrl: String?, placeHolder: Int) {
    if (imageUrl != null && imageUrl.isNotEmpty()) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(placeHolder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    } else {
        imageView.setImageResource(placeHolder)
    }
}
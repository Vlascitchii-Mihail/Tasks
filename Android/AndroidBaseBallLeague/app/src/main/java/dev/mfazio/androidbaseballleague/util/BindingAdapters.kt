package dev.mfazio.androidbaseballleague.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 *overload the android:src and set the source of the image to the ImageView
 */
@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resourceId: Int) {
    imageView.setImageResource(resourceId)
}
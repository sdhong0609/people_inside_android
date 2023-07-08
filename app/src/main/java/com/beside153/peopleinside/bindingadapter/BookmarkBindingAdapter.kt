package com.beside153.peopleinside.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.beside153.peopleinside.R

@BindingAdapter("bookmarkImg")
fun ImageView.bookmarkImg(bookmarked: Boolean) {
    if (bookmarked) {
        setImageResource(R.drawable.ic_bookmark_filled_gray)
    } else {
        setImageResource(
            R.drawable.ic_bookmark_empty_gray
        )
    }
}

@BindingAdapter("bookmarkWhiteImg")
fun ImageView.bookmarkWhiteImg(bookmarked: Boolean) {
    if (bookmarked) {
        setImageResource(R.drawable.ic_bookmark_filled)
    } else {
        setImageResource(
            R.drawable.ic_bookmark_empty
        )
    }
}

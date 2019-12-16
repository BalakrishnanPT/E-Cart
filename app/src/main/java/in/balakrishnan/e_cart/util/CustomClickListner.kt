package `in`.balakrishnan.e_cart.util

import android.view.View

interface CustomClickListner<T> {
    fun onClick(v: View, data: T)
}
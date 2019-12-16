package `in`.balakrishnan.e_cart.util

import `in`.balakrishnan.e_cart.productListing.ProductViewModel
import `in`.balakrishnan.e_cart.repo.model.Product
import android.graphics.Color
import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide


object BindAdapter {
    private val TAG = javaClass.name
    /**
     * `profileImage` binding adapter is used to bind image view with view
     * At the time of binding image view with url, this function will be called
     * Glide will download and set the image
     *
     * @param view     ImageView where image should be loaded
     * @param contact url for fetching the image
     */
    @JvmStatic
    @androidx.databinding.BindingAdapter("profileImage")
    fun loadImage(view: ImageView, contact: Product) {
        // If there is url available, Add condition to load image from given url

        view.generateImage("A")

//        Glide.with(view.context)
//            .load(
//
//            )
//            .circleCrop()
//            .fitCenter()
//            .into(view)
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("loadImage")
    fun loadImages(view: ImageView, product: Product) {
        // If there is url available, Add condition to load image from given url


        Glide.with(view.context)
            .load(
                product.thumb
            )
            .useAnimationPool(true)
            .fitCenter()
            .into(view)
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("originalPrice")
    fun setPrice(view: TextView, cproduct: Product) {
        view.setVisible(cproduct.price != cproduct.special)
        view.text = cproduct.price
        view.paintFlags = view.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

    }

   @JvmStatic
    @androidx.databinding.BindingAdapter("discountPrice")
    fun setDiscountPrice(view: TextView, cproduct: Product) {
        view.text = cproduct.special
    }


    @JvmStatic
    @androidx.databinding.BindingAdapter(value = ["cviewModel", "cproduct"], requireAll = true)
    fun setText(view: TextView, cviewModel: ProductViewModel, cproduct: Product) {
        cviewModel.cartDao.getQuantity(cproduct.id)
            .observe(view.context as LifecycleOwner, Observer {
                if (it != null) {
                    view.setVisible(it > 0)
                    view.text = "Quantity : $it"
                } else
                    view.setVisible(false)
            })
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter(value = ["pviewModel", "pproduct"], requireAll = true)
    fun setTextPVisibility(view: View, pviewModel: ProductViewModel, pproduct: Product) {
        pviewModel.cartDao.getQuantity(pproduct.id)
            .observe(view.context as LifecycleOwner, Observer {
                if (it != null)
                    view.setVisible(it > 0)
                else
                    view.setVisible(false)
            })
    }


    @JvmStatic
    @androidx.databinding.BindingAdapter(value = ["nviewModel", "nproduct"], requireAll = true)
    fun setTextNVisibility(view: View, nviewModel: ProductViewModel, nproduct: Product) {
        nviewModel.cartDao.getQuantity(nproduct.id)
            .observe(view.context as LifecycleOwner, Observer {
                if (it != null)
                    view.setVisible(it < 0)
                else
                    view.setVisible(true)
            })
    }

    @androidx.databinding.BindingAdapter(value = ["original", "search", "owner"], requireAll = true)
    @JvmStatic
    fun setTextIF(
        view: TextView,
        original: String,
        search: LiveData<String>,
        owner: LifecycleOwner?
    ) {
        if (owner != null)
            search.observe(owner, Observer {
                val value = search.value
                val spannable = SpannableString(original)
                if (value != null && !value.isNullOrBlankOrNullString()) {
                    val indexOf = original.indexOf(value, 0, true)
                    if (indexOf >= 0)
                        spannable.setSpan(
                            BackgroundColorSpan(Color.YELLOW),
                            indexOf, indexOf + value.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                }
                view.text = spannable
            })
        else {
            view.text = original
        }

    }


}

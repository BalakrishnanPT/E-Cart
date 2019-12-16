package `in`.balakrishnan.e_cart.cart

import `in`.balakrishnan.e_cart.R
import `in`.balakrishnan.e_cart.databinding.ActivityMainBinding
import `in`.balakrishnan.e_cart.productListing.ProductAdapter
import `in`.balakrishnan.e_cart.productListing.ProductViewModel
import `in`.balakrishnan.e_cart.repo.model.Contact
import `in`.balakrishnan.e_cart.repo.model.Product
import `in`.balakrishnan.e_cart.util.AppConstants
import `in`.balakrishnan.e_cart.util.CustomClickListner
import `in`.balakrishnan.e_cart.util.setVisible
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var productAdapter: ProductAdapter
    private var productsList: MutableList<Product> = arrayListOf()
    private val viewModel: ProductViewModel by lazy {
        ViewModelProviders.of(this)[ProductViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
            )


        binding.lifecycleOwner = this

        productAdapter = ProductAdapter(viewModel,
            object : CustomClickListner<Contact> {
                override fun onClick(v: View, data: Contact) {
                }
            })

        binding.rvProducts.adapter = productAdapter

        // update the recyclerview based on the livedata
        viewModel.cartDao.getProducts().observe(this, Observer {
            productsList = it as MutableList<Product>
            setListingVisibility(productsList.size > 0)
            productAdapter.updateContacts(productsList)
            rv_products.recycledViewPool.clear()
        })

    }

    /**
     * Helper function for setting List's visibility
     */
    private fun setListingVisibility(b: Boolean) {
        if (!b) {
            val message = if (viewModel.isCartDataAvailable()) AppConstants.errorNoItem
            else AppConstants.errorNoInternet
            flEs.findViewById<TextView>(R.id.esTv).text = message
        }
        rv_products.setVisible(b)
        flEs.setVisible(!b)
    }

}

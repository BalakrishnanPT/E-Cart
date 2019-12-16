package `in`.balakrishnan.e_cart.productListing

import `in`.balakrishnan.e_cart.R
import `in`.balakrishnan.e_cart.cart.CartActivity
import `in`.balakrishnan.e_cart.databinding.ActivityMainBinding
import `in`.balakrishnan.e_cart.repo.model.Contact
import `in`.balakrishnan.e_cart.repo.model.Product
import `in`.balakrishnan.e_cart.util.*
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.name
    private lateinit var binding: ActivityMainBinding
    lateinit var productAdapter: ProductAdapter
    private var contactsList: MutableList<Product> = arrayListOf()
    private val viewModel: ProductViewModel by lazy {
        ViewModelProviders.of(this)[ProductViewModel::class.java]
    }

    private val connectionStateMonitor: ConnectionStateMonitor by lazy {
        ConnectionStateMonitor(application)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cart -> {
                startActivity(Intent(this@MainActivity, CartActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
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
//        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)

        binding.rvProducts.adapter = productAdapter

        // update the recyclerview based on the livedata
        viewModel.allProducts.observe(this, Observer {
            contactsList = it as MutableList<Product>
            setListingVisibility(contactsList.size > 0)
            productAdapter.updateContacts(contactsList)
            rv_products.recycledViewPool.clear()
        })

        setUpNetWorkStateListener()

    }

    /**
     * This is the method that handles auto reload if data is not present and internet connection is available for the first time
     */
    private fun setUpNetWorkStateListener() {
        Transformations.distinctUntilChanged(connectionStateMonitor).observe(this, Observer {
            log("connectionStateMonitor called $it")
            if (it) {
                flEs.findViewById<TextView>(R.id.esTv).text = AppConstants.errorNoSearchResult
                if (!viewModel.isAPICallMade)
                    viewModel.makeAPICall()
                setListingVisibility(true)
            } else {
                if (viewModel.isDataAvailableOffline()) {
                    setListingVisibility(true)
                    if (viewModel.isDataLoadedFromDB())
                        this@MainActivity.logToast("No internet, Loaded from Local")
                } else {
                    setListingVisibility(false)
                }
            }
        })
    }

    /**
     * Helper function for setting List's visibility
     */
    private fun setListingVisibility(b: Boolean) {
        if (!b) {
            val message = if (viewModel.isDataAvailableOffline()) AppConstants.errorNoSearchResult
            else AppConstants.errorNoInternet
            flEs.findViewById<TextView>(R.id.esTv).text = message
        }
        rv_products.setVisible(b)
        flEs.setVisible(!b)
    }

}

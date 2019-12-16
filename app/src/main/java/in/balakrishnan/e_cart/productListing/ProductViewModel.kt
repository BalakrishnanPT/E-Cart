package `in`.balakrishnan.e_cart.productListing

import `in`.balakrishnan.e_cart.Injection
import `in`.balakrishnan.e_cart.repo.local.CartDao
import `in`.balakrishnan.e_cart.repo.local.ContactDatabase
import `in`.balakrishnan.e_cart.repo.local.LocalRepository
import `in`.balakrishnan.e_cart.repo.model.Cart
import `in`.balakrishnan.e_cart.repo.model.Product
import `in`.balakrishnan.e_cart.repo.model.Status
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = javaClass.name
    var isAPICallMade = false

    /**
     * The ViewModel maintains a reference to the repository to get data.
     */
    private val repository: LocalRepository
    /**
     * LiveData gives us updated words when they change.
     */
    val allProducts: MediatorLiveData<List<Product>> = MediatorLiveData()

    val cartDao: CartDao;

    init {
        // Gets reference to ContactDao from ContactDatabase to construct
        // the correct LocalRepository.
        val productDao = ContactDatabase.getDatabase(application).productDao()
        cartDao = ContactDatabase.getDatabase(application).cartDao()
        repository = LocalRepository(productDao)
        allProducts.addSource(repository.allContacts) {
            allProducts.value = it
        }
    }

    /**
     * This function is used to check if the Data is loaded either from Local DB or API
     */
    fun isDataLoadedFromDB(): Boolean {
        return !isAPICallMade
    }

    /**
     * This function is used to check if Data is available in Local DB
     */
    fun isDataAvailableOffline(): Boolean {
        var isAvailable = false
        runBlocking {
            isAvailable = withContext(Dispatchers.Default) {
                repository.getOffline()
            }
        }
        return isAvailable
    }

    fun isCartDataAvailable():Boolean{
        var isAvailable = false
        runBlocking {
            isAvailable = withContext(Dispatchers.Default) {
                cartDao.getProductsImm().isNotEmpty()
            }
        }
        return isAvailable
    }


    /**
     * This function triggers the API call for contacts
     */
    fun makeAPICall() {
        isAPICallMade = true
        viewModelScope.launch {
            val response = Injection.provideRepository().getProducts()
            if (response.status == Status.SUCCESS) {
                val listA: List<Product>? = response.data
                val listB: List<Product>? = repository.allContacts.value
                if (listA == null || listA.isEmpty()) {
                    repository.delete()
                    repository.insert(listA!!)
                } else if (listB != null)
                    if (!listA.containsAll(listB) || !listB.containsAll(listA)) {
                        repository.delete()
                        repository.insert(listA)
                    }

            } else
                Log.d(TAG, "Error Please handle ${response.error} ")

        }
    }

    fun updateCart(product: Product, type: Int) {
        val gson = GsonBuilder().create()
        viewModelScope.launch {
            val value = cartDao.getQuantityImm(product.id)
            if (value != null && value > 0) {
                val i: Int = if (type == 1) {
                    value + 1
                } else {
                    value - 1
                }
                if (i > 0)
                    cartDao.updateQuantity(product.id, i)
                else
                    cartDao.delete(product.id)
                Log.d(TAG, "updated $i")
            } else {
                Log.d(TAG, "added ")
                cartDao.insert(Cart(0, product = product, cartQuantity = 1))
            }
        }
    }

}
package `in`.balakrishnan.e_cart.repo.local

import `in`.balakrishnan.e_cart.repo.model.Contact
import `in`.balakrishnan.e_cart.repo.model.Product
import androidx.lifecycle.LiveData

class LocalRepository(val productDao: ProductDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    var allContacts: LiveData<List<Product>> = productDao.getProducts()


    fun getProducts(): LiveData<List<Product>> {
        return productDao.getProducts()
    }

    fun getOffline(): Boolean {
        val alphabetizedContact = productDao.getData()
        return alphabetizedContact > 0
    }

    suspend fun insert(word: List<Product>) {
        productDao.insert(word)
    }

    suspend fun delete() {
        productDao.deleteAll()
    }

}
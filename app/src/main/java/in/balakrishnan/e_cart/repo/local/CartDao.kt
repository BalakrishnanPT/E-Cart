package `in`.balakrishnan.e_cart.repo.local

import `in`.balakrishnan.e_cart.repo.model.Cart
import `in`.balakrishnan.e_cart.repo.model.Product
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDao {

    @Query("SELECT * from Cart")
    fun getProducts(): LiveData<List<Product>>

    @Query("SELECT * from Cart")
    suspend fun getProductsImm(): List<Product>

    @Query("SELECT cartQuantity from Cart where id=:i")
    fun getQuantity(i: Int): LiveData<Int>

    @Query("SELECT cartQuantity from Cart where id=:i")
    suspend fun getQuantityImm(i: Int): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cart: List<Cart>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cart: Cart)

    @Query("UPDATE Cart set cartQuantity =:quanlity where id=:i")
    suspend fun updateQuantity(i: Int, quanlity: Int)


    @Query("DELETE FROM Cart")
    suspend fun deleteAll()

    @Query("Delete FROM CART where id=:product")
    suspend fun delete(product: Int)


}
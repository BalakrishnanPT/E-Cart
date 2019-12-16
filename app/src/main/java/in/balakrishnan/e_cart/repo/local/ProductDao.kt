package `in`.balakrishnan.e_cart.repo.local

import `in`.balakrishnan.e_cart.repo.model.Contact
import `in`.balakrishnan.e_cart.repo.model.Product
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Query("SELECT * from Product")
    fun getProducts(): LiveData<List<Product>>

    @Query("SELECT COUNT(name) from Product ")
    fun getData(): Integer

    @Query("SELECT * from Product WHERE name like :name ORDER BY name ASC ")
    fun search(name: String): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: List<Product>)

    @Query("DELETE FROM Product")
    suspend fun deleteAll()


}
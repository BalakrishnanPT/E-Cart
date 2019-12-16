package `in`.balakrishnan.e_cart.repo.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

/**
 * Created by BalaKrishnan
 */

@Keep
@Entity
data class Product(
    val name: String,
    @PrimaryKey
    val id: Int,
    val product_id: Int,
    val sku: String,
    val image: String,
    val thumb: String,
    val zoom_thumb: String,
    val options: List<String>,
    val description: String,
    val href: String,
    val quantity: Int,
    val images: List<String>,
    val price: String,
    val special: String
)
package `in`.balakrishnan.e_cart.repo.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by BalaKrishnan
 */
@Entity
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val cartId: Int,
    @Embedded
    val product: Product,
    val cartQuantity: Int
)
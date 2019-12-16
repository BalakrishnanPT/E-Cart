package `in`.balakrishnan.e_cart.repo.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Company(
    val bs: String = "",
    val catchPhrase: String = "",
    @SerializedName("name")
    val cName: String = ""
)
package `in`.balakrishnan.e_cart.repo.remote

import `in`.balakrishnan.e_cart.repo.model.Contact
import `in`.balakrishnan.e_cart.repo.model.Product
import `in`.balakrishnan.e_cart.repo.model.Products
import retrofit2.http.GET


interface RemoteAPI {
    @GET("users")
    suspend fun getContacts(): List<Contact>

    @GET("5def7b172f000063008e0aa2/")
    suspend fun getProducts(): Products
}
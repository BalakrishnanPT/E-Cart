package `in`.balakrishnan.e_cart.repo.remote

import `in`.balakrishnan.e_cart.repo.model.Contact
import `in`.balakrishnan.e_cart.repo.model.Either
import `in`.balakrishnan.e_cart.repo.model.Product

interface IRemoteRepository {
  suspend fun getContacts(): Either<List<Contact>>
  suspend fun getProducts(): Either<List<Product>>
}
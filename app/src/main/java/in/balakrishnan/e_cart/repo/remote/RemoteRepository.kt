package `in`.balakrishnan.e_cart.repo.remote

import `in`.balakrishnan.e_cart.Injection
import `in`.balakrishnan.e_cart.repo.model.Contact
import `in`.balakrishnan.e_cart.repo.model.Either
import `in`.balakrishnan.e_cart.repo.model.Product


object RemoteRepository : IRemoteRepository {

    private val api = Injection.provideRemoteAPI()

    override suspend fun getContacts(): Either<List<Contact>> {
        return Either.success(api.getContacts())

    }

    override suspend fun getProducts(): Either<List<Product>> {
        return Either.success(api.getProducts().products)
    }

}
package `in`.balakrishnan.e_cart.productListing

import `in`.balakrishnan.e_cart.BR
import `in`.balakrishnan.e_cart.R
import `in`.balakrishnan.e_cart.repo.model.Contact
import `in`.balakrishnan.e_cart.repo.model.Product
import `in`.balakrishnan.e_cart.util.AutoUpdatableAdapter
import `in`.balakrishnan.e_cart.util.CustomClickListner
import `in`.balakrishnan.e_cart.util.setVisible
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates


class ProductAdapter(
    private val viewModel: ProductViewModel,
    private val listener: CustomClickListner<Contact>
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(),
    AutoUpdatableAdapter {

    var contacts: List<Product> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    private val TAG = javaClass.name
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                inflater,
                R.layout.item_product,
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun getItemCount() = contacts.size
    /**
     *updates the list and list automatically deploys diffutil
     */
    fun updateContacts(contacts: List<Product>) {
        this.contacts = contacts
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = contacts[position]
        holder.bind(product)
//        viewModel.cartDao.getQuantity(product.id)
//            .observe(holder.itemView.context as LifecycleOwner, Observer {
//                Log.d(TAG, "called $it ")
//                if (it != null) {
//                    val layout = holder.itemView.findViewById<ConstraintLayout>(R.id.container)
//                    holder.itemView.findViewById<ImageButton>(R.id.ivProductAdd)
//                        .setVisible(it > 0, layout)
//                    holder.itemView.findViewById<ImageButton>(R.id.ivProductRemove)
//                        .setVisible(it > 0, layout)
//                    val tvQuantity = holder.itemView.findViewById<TextView>(R.id.tvCartDetails)
//                    if (it > 0)
//                        tvQuantity.setText("Quantity $it")
//                    tvQuantity
//                        .setVisible(it > 0, layout)
//                    holder.itemView.findViewById<Button>(R.id.btnAddToCart)
//                        .setVisible(it < 1, layout)
//                } else {
//                    val layout = holder.itemView.findViewById<ConstraintLayout>(R.id.container)
//                    holder.itemView.findViewById<ImageButton>(R.id.ivProductAdd)
//                        .setVisible(false, layout)
//                    holder.itemView.findViewById<ImageButton>(R.id.ivProductRemove)
//                        .setVisible(false, layout)
//                    val tvQuantity = holder.itemView.findViewById<TextView>(R.id.tvCartDetails)
//                    tvQuantity
//                        .setVisible(false, layout)
//                    holder.itemView.findViewById<Button>(R.id.btnAddToCart)
//                        .setVisible(true, layout)
//                }
//            })
        holder.itemView.findViewById<ImageButton>(R.id.ivProductAdd).setOnClickListener {
            viewModel.updateCart(product, 1)
        }
        holder.itemView.findViewById<Button>(R.id.btnAddToCart).setOnClickListener {
            viewModel.updateCart(product, 1)
        }

        holder.itemView.findViewById<ImageButton>(R.id.ivProductRemove).setOnClickListener {
            viewModel.updateCart(product, 2)
        }

    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Product) {
            this.binding.setVariable(BR.item, contact)
            this.binding.setVariable(BR.listener, listener)
            this.binding.setVariable(BR.productViewModel, viewModel)
            this.binding.executePendingBindings()
        }
    }

}
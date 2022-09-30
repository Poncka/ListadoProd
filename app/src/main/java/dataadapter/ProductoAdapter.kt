package dataadapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.ViewGroup
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.earl.listadoprod.databinding.ItemlistaBinding
import dataclass.Producto

class ProductoAdapter(val listProd: List<Producto>, private val clickItem: (Producto) -> Unit, private val eliminarItemClick: (Int) -> Unit, private val actualizarItemClick: (Int) -> Unit):

        RecyclerView.Adapter<ProductoAdapter.ProductoHolder>()
        {
        inner class ProductoHolder(val binding: ItemlistaBinding):
            RecyclerView.ViewHolder(binding.root) {
                fun cargar(producto: Producto, clickItem: (Producto) -> Unit,
                           eliminarItemClick: (Int) -> Unit,
                           actualizarItemClick: (Int) -> Unit)
                {
                    with(binding){
                        tvCodProd.text = producto.id.toString()
                        tvNombreProd.text = producto.nombre
                        tvPrecioProd.text = producto.precio.toString()

                        //Elementos
                        itemView.setOnClickListener{clickItem(producto)}
                        binding.btnEliminar.setOnClickListener { eliminarItemClick(adapterPosition)}
                        binding.btnEditar.setOnClickListener { actualizarItemClick(adapterPosition)}
                    }
                }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.cargar(listProd[position], clickItem, eliminarItemClick, actualizarItemClick)
    }

    override fun getItemCount(): Int = listProd.size
    }
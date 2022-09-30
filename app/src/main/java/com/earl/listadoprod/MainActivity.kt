package com.earl.listadoprod

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.earl.listadoprod.databinding.ActivityMainBinding
import dataadapter.ProductoAdapter
import dataclass.Producto
import java.lang.Exception

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        start()
    }

    private fun clean()
    {
        with(binding)
        {
            etID.setText("")
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun addProd()
    {
        with(binding)
        {
            try
            {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)
            }catch(ex: Exception){
                Toast.makeText(this@MainActivity, "Error: ${ex.toString()}",
                Toast.LENGTH_LONG).show()
            }

            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd,
                {producto -> itemSeleccionado(producto) },
                {position -> eliminarItem(position)},
                {position -> editarItem(position)})

            clean()
        }
    }

    private fun start(){
        binding.btnAgregar.setOnClickListener{ addProd() }
        binding.btnLimpiar.setOnClickListener{ clean() }
    }

    //}Editar el texto
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    //Item seleccionado dentro de la lista
    fun itemSeleccionado(producto: Producto)
    {
        with(binding)
        {
            etID.text = producto.id.toString().toEditable()
            etNombreProd.text = producto.nombre.toEditable()
            etPrecio.text = producto.precio.toString().toEditable()
        }
    }

    fun eliminarItem(position:Int)
    {
        val msg = AlertDialog.Builder(this)
        msg.setTitle("Eliminar")
        msg.setMessage("Si elimina este elemento ya no se podrá recuperar")
        msg.setPositiveButton("Eliminar", DialogInterface.OnClickListener
        {_,_ ->
            with(binding)
            {
                listaProd.removeAt(position)
                rcvLista.adapter?.notifyItemRemoved(position)
                clean()
            }
        })
        msg.setNegativeButton("Cancelar", DialogInterface.OnClickListener()
        {_,_ ->
            Toast.makeText(this,"Cancelado con exito", Toast.LENGTH_SHORT).show()
            clean()
        })
        msg.show()
    }

    //Editamos el producto
    fun editarItem(position: Int)
    {
        try
        {
            with(binding)
            {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.set(position, prod)
                rcvLista.adapter?.notifyItemChanged(position)
            }
        }catch (ex: Exception){
            Toast.makeText(this@MainActivity, "Los campos no pueden quedar vacios, revise e intente nuevamente", Toast.LENGTH_SHORT).show()
        }
    }

}









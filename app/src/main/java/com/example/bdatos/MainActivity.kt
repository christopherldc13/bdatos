package com.example.bdatos
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var txtCodigo:EditText?=null
    var txtDescripcion:EditText?=null
    var txtPrecio:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtCodigo=findViewById(R.id.txtCodigo)
        txtDescripcion=findViewById(R.id.txtDescripcion)
        txtPrecio=findViewById(R.id.txtPrecio)
    }

    // Función de Insertar
    fun insertar(view:View){
        var con=SQLite(this,"tienda",null,1)
        var baseDatos=con.writableDatabase

        var codigo=txtCodigo?.text.toString()
        var descripcion=txtDescripcion?.text.toString()
        var precio=txtPrecio?.text.toString()

        if(codigo.isEmpty()==false && descripcion.isEmpty()==false && precio.isEmpty()==false){
            var registro=ContentValues()
            registro.put("codigo",codigo)
            registro.put("descripcion",descripcion)
            registro.put("precio",precio)
            baseDatos.insert("productos",null,registro)
            txtCodigo?.setText("")
            txtDescripcion?.setText("")
            txtPrecio?.setText("")
            Toast.makeText(this,"Se insertado exitosamente",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Los campos deben tener texto",Toast.LENGTH_LONG).show()
        }
    }

    // Función de Buscar
    fun buscar (view: View){
        val con = SQLite (this, "tienda", null, 1)
        val baseDatos = con.writableDatabase
        val codigo = txtCodigo?.text.toString()

        if (codigo.isEmpty()==false){
            val fila = baseDatos.rawQuery("select descripcion, precio from productos where codigo = '$codigo'", null)
            if (fila.moveToFirst()==true){
                txtDescripcion?.setText(fila.getString(0))
                txtPrecio?.setText(fila.getString(1))
                baseDatos.close()
            } else {
                txtDescripcion?.setText("")
                txtPrecio?.setText("")
                Toast.makeText(this, "No se encontraron registros", Toast.LENGTH_LONG).show()
            }
        }

    }

    // Función de Eliminar
    fun eliminar (view: View) {
        val con = SQLite(this, "tienda", null, 1)
        val baseDatos = con.writableDatabase
        val codigo = txtCodigo?.text.toString()

        if (codigo.isEmpty()==false){
            val cant = baseDatos.delete("productos", "codigo='"+codigo+"'",null)
            if (cant>0){
                Toast.makeText(this, "El producto fue eliminado", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "El producto no se encontro", Toast.LENGTH_LONG).show()
            }
            txtCodigo?.setText("")
            txtPrecio?.setText("")
            txtDescripcion?.setText("")
        } else {
            Toast.makeText(this, "El campo codigo debe de tener texto", Toast.LENGTH_LONG).show()
        }
    }

    //Función de Editar
    fun editar (view: View) {
        val con = SQLite(this, "tienda", null, 1)
        val baseDatos = con.writableDatabase
        val codigo = txtCodigo?.text.toString()
        val descipcion = txtDescripcion?.text.toString()
        val precio = txtPrecio?.text.toString()

        if (!codigo.isEmpty()&&!descipcion.isEmpty()&&!precio.isEmpty()){
            var registro = ContentValues()
            registro.put("codigo", codigo)
            registro.put("descripcion", descipcion)
            registro.put("precio", precio)

            var cant = baseDatos.update("productos", registro, "codigo='$codigo'", null)

            if (cant>0){
                Toast.makeText(this, "El registro se a editado o actualizado exitosamente", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "El registro no fue encontrado", Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_LONG).show()
        }
    }

}

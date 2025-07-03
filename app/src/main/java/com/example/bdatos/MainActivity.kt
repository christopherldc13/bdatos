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
}
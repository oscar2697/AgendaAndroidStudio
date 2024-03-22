package com.example.agenda2024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.agenda2024.R.id
import org.json.JSONException
import org.json.JSONObject

class MainActivity2 : AppCompatActivity() {
    val codigos = ArrayList<String>()

    lateinit var txtcedula: EditText
    lateinit var txtnombre: EditText
    lateinit var txtapellido: EditText
    lateinit var txtclave: EditText
    lateinit var txtcorreo: EditText
    lateinit var txtcodigo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        txtcedula = findViewById<EditText>(R.id.txt_cedula)
        txtnombre = findViewById<EditText>(R.id.txt_nombre)
        txtapellido = findViewById<EditText>(R.id.txt_apellido)
        txtclave = findViewById<EditText>(R.id.txt_clave)
        txtcorreo = findViewById<EditText>(R.id.txt_correo)
        txtcodigo = findViewById<EditText>(R.id.txt_dato)

        val lista = findViewById<ListView>(R.id.lista)
        lista.setOnItemClickListener{adapterView, view, i, l ->
            //Toast.makeText(applicationContext, codigos[i], Toast.LENGTH_LONG).show()
            consultarDatos(codigos[i])
        }

        val btnconsultar = findViewById<Button>(R.id.btn_consultar)
        btnconsultar.setOnClickListener {
            consultar(lista)
        }

        val btninsertar = findViewById<Button>(R.id.btn_insertar)
        btninsertar.setOnClickListener {
            insertar(txtcedula.text.toString(), txtnombre.text.toString(), txtapellido.text.toString(), txtclave.text.toString(), txtcorreo.text.toString() )
        }

        val btndato = findViewById<Button>(R.id.btn_dato)
        btndato.setOnClickListener {
            consultarDatos(txtcodigo.text.toString())
        }

        val btneliminar = findViewById<Button>(R.id.btn_eliminar)
        btneliminar.setOnClickListener {
            eliminar(txtcodigo.text.toString())
            consultar(lista)
        }

        val btnactualizar = findViewById<Button>(R.id.btn_actualizar)
        btnactualizar.setOnClickListener {
            actualizar()
        }
    }

    private fun insertar(cedula: String, nombre: String, apellido: String, clave: String, correo: String){
        val url = "http://10.0.2.2/Ws_Agenda2024/crud_persona.php"
        val datos = JSONObject()

        datos.put("accion", "Insertar")
        datos.put("cedula", cedula)
        datos.put("nombre", nombre)
        datos.put("apellido", apellido)
        datos.put("clave", clave)
        datos.put("mail", correo)

        val rq = Volley.newRequestQueue(this)
        val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
                Response.Listener { s ->
                    try {
                        val obj = (s)
                        if(obj.getBoolean("estado")){
                            Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e:JSONException){
                        Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }
            )
        rq.add(jsor)
    }

    private fun actualizar(){
        val url = "http://10.0.2.2/Ws_Agenda2024/crud_persona.php"
        val datos = JSONObject()

        datos.put("accion", "Actualizar")
        datos.put("cedula", txtcedula.text.toString())
        datos.put("nombre", txtnombre.text.toString())
        datos.put("apellido", txtapellido.text.toString())
        datos.put("clave", txtclave.text.toString())
        datos.put("mail", txtcorreo.text.toString())
        datos.put("codigo", txtcodigo.text.toString())

        val rq = Volley.newRequestQueue(this)
        val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
            Response.Listener { s ->
                try {
                    val obj = (s)
                    if(obj.getBoolean("estado")){
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e:JSONException){
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }
        )
        rq.add(jsor)
    }

    private fun eliminar(codigo: String){
        val url = "http://10.0.2.2/Ws_Agenda2024/crud_persona.php"
        val datos = JSONObject()

        datos.put("accion", "Eliminar")
        datos.put("codigo", codigo)

        val rq = Volley.newRequestQueue(this)
        val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
            Response.Listener { s ->
                try {
                    val obj = (s)
                    if(obj.getBoolean("estado")){
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e:JSONException){
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }
        )
        rq.add(jsor)
    }

    private fun consultarDatos(codigo: String){
        val url = "http://10.0.2.2/Ws_Agenda2024/crud_persona.php"
        val datos = JSONObject()
        datos.put("accion", "consultarDatos")
        datos.put("codigo", codigo)

        val rq = Volley.newRequestQueue(this)
        val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
                Response.Listener { s ->
                    try {
                        val obj = (s)

                        if(obj.getBoolean("estado")){
                            val array = obj.getJSONArray("persona")
                            val dato = array.getJSONObject(0)

                            txtcodigo.setText(dato.getString("codigo"))
                            txtcedula.setText(dato.getString("cedula"))
                            txtnombre.setText(dato.getString("nombre"))
                            txtapellido.setText(dato.getString("apellido"))
                            txtcorreo.setText(dato.getString("correo"))
                            txtclave.setText(dato.getString("clave"))
                            //Toast.makeText(applicationContext, dato.getString("nombre") + " " + dato.getString("apellido"), Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                    }
                }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }
            )
        rq.add(jsor)
    }

    private fun consultar(lista:ListView){
        val al = ArrayList<String>()
        al.clear()
        val url = "http://10.0.2.2/Ws_Agenda2024/crud_persona.php"
        val datos = JSONObject()

        datos.put("accion", "consultar")

        val rq = Volley.newRequestQueue(this)
        val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
            Response.Listener{ s ->
                try {
                    val obj = (s)
                    Toast.makeText(applicationContext, obj.getBoolean("estado").toString(), Toast.LENGTH_LONG).show()

                    if(obj.getBoolean("estado")){
                        val array = obj.getJSONArray("personas")

                        for(i in 0..array.length() -1){
                            val fila = array.getJSONObject(i)
                            codigos.add(fila.getString("codigo"))
                            al.add(fila.getString("cedula") + " " +fila.getString("nombre") + " " + fila.getString("apellido"))
                        }

                        val la = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al)
                        lista.adapter = la
                    }else {
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                    }
                }catch (e: JSONException){
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_SHORT).show() }
        )
        rq.add(jsor)
    }
}
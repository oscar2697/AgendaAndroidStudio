package com.example.agenda2024

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnlogin = findViewById<Button>(R.id.btn_login)

        btnlogin.setOnClickListener{
            val cedula = findViewById<EditText>(R.id.ci_persona).text.toString()
            val clave = findViewById<EditText>(R.id.clave).text.toString()
            login(cedula , clave )
        }
    }

    private fun login(cedula: String, clave: String){
        val url = "http://10.0.2.2/Ws_Agenda2024/crud_persona.php"
        val datos = JSONObject()

        datos.put("accion", "consultarDato")
        datos.put("cedula", cedula)
        datos.put("clave", clave)

        val rq = Volley.newRequestQueue(this)
        val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
            Response.Listener{s ->
                try {
                    val obj = (s)

                    if(obj.getBoolean("estado")){
                        val intent = Intent(this, MainActivity2::class.java)
                        startActivity(intent)
                        finish()
                        //val array = obj.getJSONArray("persona")
                        //val fila = array.getJSONObject(0)
                        //Toast.makeText(applicationContext, fila.getString("cedula") + " " +fila.getString("nombre") + " " + fila.getString("apellido"), Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_SHORT).show()
                    }
                }catch (e:JSONException){
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_SHORT).show() })
        rq.add(jsor)
    }
}
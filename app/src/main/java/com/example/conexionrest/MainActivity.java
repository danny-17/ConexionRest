package com.example.conexionrest;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.conexionrest.model.Marcas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listViewMarcas);
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,datos);
        listView.setAdapter(arrayAdapter);
        obtenerDatos();
    }

    private void obtenerDatos(){
        String url="https://parallelum.com.br/fipe/api/v1/carros/marcas";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            //Recibe la informacion
            //Pasar Json a Marca
                pasarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //maneja el error
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        //Aqui pasamos la peticion del web service a la cola del Volley
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void pasarJson(JSONArray array){
        for (int i=0;i<array.length();i++){

            JSONObject json=null;
            Marcas marca = new Marcas();

            try {
                json=array.getJSONObject(i);
                marca.setCodigo(json.getInt("codigo"));
                marca.setNome(json.getString("nome"));
                //Clase instanciada y seteada datos del web service
                //Agregar los datos al arrayList enlazados con la Lista
                datos.add(marca.getNome());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
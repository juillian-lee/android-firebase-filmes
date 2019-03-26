package com.android.juillianlee.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.loadFilmes();
    }

    private void loadFilmes() {
        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        RequestQueue queue = Volley.newRequestQueue(this);
        ListView listView = findViewById(R.id.lista);
        final ArrayAdapter<Filme> adapter = new ArrayAdapter<Filme>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        String url = "https://swapi.co/api/films/?search=&format=json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Type listType = new TypeToken<ArrayList<Filme>>(){}.getType();
                            JSONObject json = new JSONObject(response);
                            JSONArray results = json.getJSONArray("results");
                            List<Filme> filmes = gson.fromJson(results.toString(), listType);
                            adapter.addAll(filmes);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Falha ao recuperar o json");
                    }
                });

        queue.add(stringRequest);
    }

    public void onClickLogout(View view) {
        this.firebaseAuth.signOut();
        callActivity(MainActivity.class);
    }

    private void callActivity(Class newActivity) {
        Intent newIntent = new Intent(HomeActivity.this,newActivity);
        startActivity(newIntent);
        finish();
    }
}

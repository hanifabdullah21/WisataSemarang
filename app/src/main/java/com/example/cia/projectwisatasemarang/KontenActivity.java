package com.example.cia.projectwisatasemarang;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cia.projectwisatasemarang.Adapter.RecyclerViewAdapter;
import com.example.cia.projectwisatasemarang.Json.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KontenActivity extends AppCompatActivity {
	RecyclerView rvWisata;

	public String HTTP_JSON_URL;

	public static String NAMA = "nama";
	public static String GAMBAR = "gambar";
	public static String ALAMAT = "alamat";
	public static String DESKRIPSI = "deskripsi";
	public static String EVENT = "event";
	public static String LATITUDE = "latitude";
	public static String LONGITUDE = "longitude";


	JsonArrayRequest requestJsonArray;
	RequestQueue requestQueue;
	RecyclerView.LayoutManager layoutManager;
	RecyclerViewAdapter adapter;

	List<Result> resultList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_konten);

		HTTP_JSON_URL = getIntent().getStringExtra("url");

		resultList = new ArrayList<>();

		rvWisata = (RecyclerView) findViewById(R.id.rv_wisata);

		rvWisata.setHasFixedSize(true);
		layoutManager = new GridLayoutManager(this,2);
		rvWisata.setLayoutManager(layoutManager);

		JSON_HTTP_CALL();

	}

	public void JSON_HTTP_CALL() {
		requestJsonArray = new JsonArrayRequest(HTTP_JSON_URL,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						ParseJSonResponse(response);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(KontenActivity.this, "Tidak ada koneksi", Toast.LENGTH_SHORT).show();
					}
				});
		requestQueue = Volley.newRequestQueue(KontenActivity.this);
		requestQueue.add(requestJsonArray);
	}

	public void ParseJSonResponse(JSONArray jsonArray) {
		for (int i = 0; i < jsonArray.length(); i++) {

			Result GetData = new Result();

			JSONObject json;
			try {
				json = jsonArray.getJSONObject(i);

				GetData.setNama(json.getString(NAMA));
				GetData.setGambar(json.getString(GAMBAR));
				GetData.setAlamat(json.getString(ALAMAT));
				GetData.setDetail(json.getString(DESKRIPSI));
				GetData.setEvent(json.getString(EVENT));
				GetData.setLatitude(json.getDouble(LATITUDE));
				GetData.setLongitude(json.getDouble(LONGITUDE));

			} catch (JSONException e) {
				e.printStackTrace();
			}
			resultList.add(GetData);
		}

		adapter = new RecyclerViewAdapter(resultList, this);

		rvWisata.setAdapter(adapter);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_search, menu);
		MenuItem item = menu.findItem(R.id.search);

		SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
		searchView.setIconifiedByDefault(true);

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query)
			{
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				adapter.getFilter().filter(newText);
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}
}

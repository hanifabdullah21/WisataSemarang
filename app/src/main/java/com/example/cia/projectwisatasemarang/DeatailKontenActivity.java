package com.example.cia.projectwisatasemarang;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cia.projectwisatasemarang.Adapter.RecyclerViewAdapterKuliner;
import com.example.cia.projectwisatasemarang.Json.Result;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class DeatailKontenActivity extends AppCompatActivity {
	static double PI_RAD = Math.PI / 180.0;

	private static String HTTP_JSON_URL_KULINER = "https://seputarwisatasemarang.000webhostapp.com/api/data/getkuliner.php";
	private static String HTTP_JSON_URL_PENGINAPAN = "https://seputarwisatasemarang.000webhostapp.com/api/data/getpenginapan.php";
	private static String NAMA = "nama";
	private static String GAMBAR = "gambar";
	private static String ALAMAT = "alamat";
	private static String DESKRIPSI = "deskripsi";
	public static String LATITUDE = "latitude";
	public static String LONGITUDE = "longitude";

	ImageView mivGambarWisata;
	TextView mtvNamaWisata, mtvAlamatWisata, mtvDeskripsiWisata, mtvEventWisata;
	Button mbtnMaps;

	RecyclerView mrvKuliner, mrvHotel;

	TextView kuliner, hotel;
	Boolean showKulinerHotel = false;

	String getGambarWisata,getNamaWisata,getAlamatWisata,getDeskripsiWisata,getEventWisata;
	Double getLatitudeWisata, getLongitudeWisata;

	Double distance;

	JsonArrayRequest requestJSONArrayKuliner, requestJSONARrayHotel;
	RequestQueue requestQueueKuliner, requestQueueHotel;

	RecyclerView.LayoutManager layoutManagerKuliner, layoutManagerPenginapan;

	RecyclerViewAdapterKuliner recyclerViewAdapterKuliner, recyclerViewAdapterHotel;

	List<Result> listKuliner, listHotel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_content);

		Intent i = getIntent();
		getGambarWisata    = i.getStringExtra("gambar");
		getNamaWisata      = i.getStringExtra("nama");
		getAlamatWisata    = i.getStringExtra("alamat");
		getDeskripsiWisata = i.getStringExtra("deskripsi");
		getEventWisata 	   = i.getStringExtra("event");
		getLatitudeWisata  = i.getDoubleExtra("lat", 0);
		getLongitudeWisata = i.getDoubleExtra("long", 0);

		mivGambarWisata    = (ImageView) findViewById(R.id.iv_gambar_wisata);
		mtvNamaWisata 	   = (TextView) findViewById(R.id.tv_nama_wisata);
		mtvAlamatWisata    = (TextView) findViewById(R.id.tv_alamat_wisata);
		mtvDeskripsiWisata = (TextView) findViewById(R.id.tv_deskripsi_wisata);
		mtvEventWisata 	   = (TextView) findViewById(R.id.tv_event_wisata);

		mtvNamaWisata.setText(getNamaWisata);
		mtvAlamatWisata.setText(getAlamatWisata);
		mtvDeskripsiWisata.setText(getDeskripsiWisata);
		mtvEventWisata.setText(getEventWisata);
		Picasso.with(this).load(getGambarWisata).into(mivGambarWisata);

		listKuliner = new ArrayList<>();
		listHotel = new ArrayList<>();

		mrvKuliner = (RecyclerView) findViewById(R.id.rv_kuliner);
		mrvHotel   = (RecyclerView) findViewById(R.id.rv_hotel);

		mrvKuliner.setHasFixedSize(true);
		layoutManagerKuliner = new LinearLayoutManager(this);
		mrvKuliner.setLayoutManager(layoutManagerKuliner);

		mrvHotel.setHasFixedSize(true);
		layoutManagerPenginapan = new LinearLayoutManager(this);
		mrvHotel.setLayoutManager(layoutManagerPenginapan);

		mbtnMaps = (Button) findViewById(R.id.bt_go_maps);
		mbtnMaps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=+" + getNamaWisata + "+Semarang"));
				startActivity(intent);
			}
		});

		JSON_HTTP_KULINER_CALL();
		JSON_HTTP_HOTEL_CALL();

		kuliner = (TextView) findViewById(R.id.tvKuliner);
		kuliner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(showKulinerHotel==true){
					mrvKuliner.setVisibility(View.VISIBLE);
					mrvHotel.setVisibility(View.GONE);
					kuliner.setBackgroundColor(Color.parseColor("#FF199EB1"));
					hotel.setBackgroundColor(Color.parseColor("#1dbbd2"));
					showKulinerHotel = false;
				}
			}
		});

		hotel = (TextView) findViewById(R.id.tvHotel);
		hotel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(showKulinerHotel==false){
					mrvKuliner.setVisibility(View.GONE);
					mrvHotel.setVisibility(View.VISIBLE);
					kuliner.setBackgroundColor(Color.parseColor("#1dbbd2"));
					hotel.setBackgroundColor(Color.parseColor("#FF199EB1"));
					showKulinerHotel = true;
				}
			}
		});
	}

	public void JSON_HTTP_KULINER_CALL() {
		requestJSONArrayKuliner = new JsonArrayRequest(HTTP_JSON_URL_KULINER,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						parseJSonResponseKuliner(response);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(DeatailKontenActivity.this, "Tidak Ada Koneksi", Toast.LENGTH_SHORT).show();
					}
				});
		requestQueueKuliner = Volley.newRequestQueue(DeatailKontenActivity.this);
		requestQueueKuliner.add(requestJSONArrayKuliner);
	}

	public void JSON_HTTP_HOTEL_CALL() {
		requestJSONARrayHotel = new JsonArrayRequest(HTTP_JSON_URL_PENGINAPAN,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						ParseJSonResponsePenginapan(response);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(DeatailKontenActivity.this, "Tidak Ada Koneksi", Toast.LENGTH_SHORT).show();
					}
				});
		requestQueueHotel = Volley.newRequestQueue(DeatailKontenActivity.this);
		requestQueueHotel.add(requestJSONARrayHotel);
	}

	public void parseJSonResponseKuliner(JSONArray array) {
		for (int i = 0; i < array.length(); i++) {

			Result getDataKuliner = new Result();

			JSONObject json = null;
			try {
				json = array.getJSONObject(i);

				getDataKuliner.setNama(json.getString(NAMA));
				getDataKuliner.setGambar(json.getString(GAMBAR));
				getDataKuliner.setAlamat(json.getString(ALAMAT));
				getDataKuliner.setDetail(json.getString(DESKRIPSI));
				getDataKuliner.setLatitude(json.getDouble(LATITUDE));
				getDataKuliner.setLongitude(json.getDouble(LONGITUDE));

				getDistance(getLatitudeWisata, getLongitudeWisata, json.getDouble(LATITUDE), json.getDouble(LONGITUDE));

			} catch (JSONException e) {
				e.printStackTrace();
			}

			//menampilkan kuliner dengan jarak dibawah 1km
			if (distance <= 1) {
				listKuliner.add(getDataKuliner);
			}
		}

		recyclerViewAdapterKuliner = new RecyclerViewAdapterKuliner(listKuliner, this);
		mrvKuliner.setAdapter(recyclerViewAdapterKuliner);
	}

	public void ParseJSonResponsePenginapan(JSONArray array) {
		for (int i = 0; i < array.length(); i++) {

			Result GetDataPenginapan = new Result();

			JSONObject json = null;
			try {
				json = array.getJSONObject(i);

				GetDataPenginapan.setNama(json.getString(NAMA));
				GetDataPenginapan.setGambar(json.getString(GAMBAR));
				GetDataPenginapan.setAlamat(json.getString(ALAMAT));
				GetDataPenginapan.setDetail(json.getString(DESKRIPSI));
				GetDataPenginapan.setLatitude(json.getDouble(LATITUDE));
				GetDataPenginapan.setLongitude(json.getDouble(LONGITUDE));

				getDistance(getLatitudeWisata, getLongitudeWisata, json.getDouble(LATITUDE), json.getDouble(LONGITUDE));

			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (distance <= 1) {
				listHotel.add(GetDataPenginapan);
			}
		}
		recyclerViewAdapterHotel = new RecyclerViewAdapterKuliner(listHotel, this);
		mrvHotel.setAdapter(recyclerViewAdapterHotel);
	}

	//menghitung jarak tempat wisata dengan tempat kuliner/hotel
	public Double getDistance(Double firstLat, Double firstLong, Double secondLat, Double secondLong) {
		double phi1 = firstLat * PI_RAD;
		double phi2 = secondLat * PI_RAD;
		double lam1 = firstLong * PI_RAD;
		double lam2 = secondLong * PI_RAD;

		distance = 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));

		return distance;
	}
}

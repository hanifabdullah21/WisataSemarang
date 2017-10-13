package com.example.cia.projectwisatasemarang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivityKulinerHotel extends AppCompatActivity {

	private TextView namaKulinerHotel;
	private TextView deskripsiKulinerHotel;
	private TextView alamatKulinerHotel;
	private ImageView gambarKulinerHotel;

	String mNama, mGambar, mDeskripsi, mAlamat;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_kuliner_hotel);
		initView();

		Intent i = getIntent();
		mGambar = i.getStringExtra("gambarKH");
		mNama = i.getStringExtra("namaKH");
		mAlamat = i.getStringExtra("alamatKH");
		mDeskripsi = i.getStringExtra("deskripsiKH");

		setValue();
	}

	private void initView() {
		namaKulinerHotel = (TextView) findViewById(R.id.namaKulinerHotel);
		deskripsiKulinerHotel = (TextView) findViewById(R.id.deskripsiKulinerHotel);
		alamatKulinerHotel = (TextView) findViewById(R.id.alamatKulinerHotel);
		gambarKulinerHotel = (ImageView) findViewById(R.id.gambarKulinerHotel);
	}

	private void setValue(){
		Picasso.with(this).load(mGambar).into(gambarKulinerHotel);
		namaKulinerHotel.setText(mNama);
		alamatKulinerHotel.setText(mAlamat);
		deskripsiKulinerHotel.setText(mDeskripsi);
	}
}

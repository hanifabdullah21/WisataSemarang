package com.example.cia.projectwisatasemarang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;


public class HomeActivity extends AppCompatActivity {
	private CardView cardWisataAlam;
	private CardView cardWisata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initView();
		cardWisata.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//url api Wisata
				String url = "https://seputarwisatasemarang.000webhostapp.com/api/data/getwisata.php";
				Intent goDaftarwisata = new Intent(HomeActivity.this, KontenActivity.class);
				//melempar variabel 'url' ke activity tujuan
				goDaftarwisata.putExtra("url", url);
				startActivity(goDaftarwisata);
			}
		});

		cardWisataAlam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//url api Wisata Alam
				String url = "https://seputarwisatasemarang.000webhostapp.com/api/data/getwisataalam.php";
				Intent goDaftarwisata = new Intent(HomeActivity.this, KontenActivity.class);
				//melempar variabel 'url' ke activity tujuan
				goDaftarwisata.putExtra("url", url);
				startActivity(goDaftarwisata);
			}
		});

	}

	private void initView() {
		cardWisataAlam = (CardView) findViewById(R.id.card_wisata_alam);
		cardWisata = (CardView) findViewById(R.id.card_wisata);
	}
}

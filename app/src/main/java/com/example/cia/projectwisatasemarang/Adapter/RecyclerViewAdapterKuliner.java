package com.example.cia.projectwisatasemarang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.cia.projectwisatasemarang.DeatailKontenActivity;
import com.example.cia.projectwisatasemarang.DetailActivityKulinerHotel;
import com.example.cia.projectwisatasemarang.Json.Result;
import com.example.cia.projectwisatasemarang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 09/10/2017.
 */

public class RecyclerViewAdapterKuliner extends RecyclerView.Adapter<RecyclerViewAdapterKuliner.ViewHolder> implements Filterable {

	private Context context;
	private List<Result> results;
	private List<Result> searchResult;

	public RecyclerViewAdapterKuliner(List<Result> getResult, Context context) {
		this.context = context;
		this.results = getResult;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kuliner_hotel, parent, false);
		ViewHolder holder = new ViewHolder(v);
		v.setOnClickListener(clickListener);
		v.setTag(holder);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.itemNamaKulinerHotel.setText(results.get(position).getNama());
	}

	View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			ViewHolder vholder = (ViewHolder) view.getTag();
			int position = vholder.getPosition();
			Intent intent = new Intent(context, DetailActivityKulinerHotel.class);
			intent.putExtra("gambarKH", results.get(position).getGambar());
			intent.putExtra("namaKH", results.get(position).getNama());
			intent.putExtra("alamatKH", results.get(position).getAlamat());
			intent.putExtra("deskripsiKH",results.get(position).getDetail());

			// Start SingleItemView Class
			context.startActivity(intent);
		}
	};

	@Override
	public int getItemCount() {
		return results.size();
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				final FilterResults oReturn = new FilterResults();
				final List<Result> results2 = new ArrayList<Result>();
				if (searchResult == null)
					searchResult = results;
				if (charSequence != null){
					if (results != null & searchResult.size()>0){
						for (final Result g : searchResult){
							if (g.getNama().toLowerCase().contains(charSequence.toString()))results2.add(g);
						}
					}
					oReturn.values = results2;
				}
				return oReturn;
			}

			@Override
			protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
				results = (ArrayList<Result>)filterResults.values;
				notifyDataSetChanged();
			}
		};
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView itemNamaKulinerHotel;

		public ViewHolder(View itemView) {
			super(itemView);
			itemNamaKulinerHotel = (TextView)itemView.findViewById(R.id.item_nama_kuliner_hotel);
		}
	}
}
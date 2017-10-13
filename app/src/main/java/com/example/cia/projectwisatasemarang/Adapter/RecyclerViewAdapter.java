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
import com.example.cia.projectwisatasemarang.Json.Result;
import com.example.cia.projectwisatasemarang.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cia on 04/10/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Result> results;
    private List<Result> searchResult;

    public RecyclerViewAdapter(List<Result> getResult, Context context) {
        this.context = context;
        this.results = getResult;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(clickListener);
        v.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(context).load(results.get(position).getGambar()).into(holder.itemGambarWisata);
        holder.itemNamaWisata.setText(results.get(position).getNama());
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ViewHolder vholder = (ViewHolder) view.getTag();
            int position = vholder.getPosition();

            Intent intent = new Intent(context, DeatailKontenActivity.class);

            intent.putExtra("gambar", results.get(position).getGambar());
            intent.putExtra("nama", results.get(position).getNama());
            intent.putExtra("alamat", results.get(position).getAlamat());
            intent.putExtra("deskripsi",results.get(position).getDetail());
            intent.putExtra("event", results.get(position).getEvent());
            intent.putExtra("lat", results.get(position).getLatitude());
            intent.putExtra("long", results.get(position).getLongitude());

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

        ImageView itemGambarWisata;
        TextView itemNamaWisata;

        public ViewHolder(View itemView) {
            super(itemView);
            itemGambarWisata = (ImageView)itemView.findViewById(R.id.item_gambar_wisata);
            itemNamaWisata = (TextView)itemView.findViewById(R.id.item_nama_wisata);
        }
    }
}


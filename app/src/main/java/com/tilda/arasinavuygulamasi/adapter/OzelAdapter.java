package com.tilda.arasinavuygulamasi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tilda.arasinavuygulamasi.model.Film;
import com.tilda.arasinavuygulamasi.R;

import java.util.ArrayList;

public class OzelAdapter extends BaseAdapter {
    Context context;
    ArrayList<Film> filmler;
    LayoutInflater layoutInflater;

    public OzelAdapter(Context context, ArrayList<Film> filmler) {
        this.context = context;
        this.filmler = filmler;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filmler.size();
    }

    @Override
    public Object getItem(int position) {
        return filmler.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.satir, parent, false);

        TextView satir_title = convertView.findViewById(R.id.satir_title);
        TextView satir_imdbid = convertView.findViewById(R.id.satir_imdbid);
        TextView satir_year = convertView.findViewById(R.id.satir_year);
        TextView satir_type = convertView.findViewById(R.id.satir_type);
        ImageView satir_resim = convertView.findViewById(R.id.satir_resim);

        Film f = filmler.get(position);
        satir_title.setText(f.getTitle());
        satir_imdbid.setText(f.getImdbID());
        satir_year.setText(f.getYear());
        satir_type.setText(f.getType());
        Picasso.get().load(f.getPoster()).into(satir_resim);

        return convertView;
    }
}

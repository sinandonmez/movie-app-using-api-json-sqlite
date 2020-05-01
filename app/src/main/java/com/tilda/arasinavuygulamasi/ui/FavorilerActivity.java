package com.tilda.arasinavuygulamasi.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tilda.arasinavuygulamasi.adapter.OzelAdapter;
import com.tilda.arasinavuygulamasi.database.Veritabani;
import com.tilda.arasinavuygulamasi.model.Film;
import com.tilda.arasinavuygulamasi.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FavorilerActivity extends AppCompatActivity {
    ArrayList<Film> filmler;
    OzelAdapter ozelAdapter;
    ListView listView_favori;

    String kullanici_adi;//Girişten gelen kullanıcı adı

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoriler);
        setTitle("Favoriler");;

        //Girişten MainActivity aracılığı ile gelen kullanıcı adı alınıyor.
        kullanici_adi = getIntent().getStringExtra("kullanici_adi");

        listView_favori = findViewById(R.id.listView3);

        Veritabani vt = new Veritabani(this);
        filmler = vt.filmleriListele();
        vt.close();

        ozelAdapter = new OzelAdapter(this, filmler);
        listView_favori.setAdapter(ozelAdapter);

        listView_favori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Film secilenFilm = filmler.get(position);


                AlertDialog.Builder mesaj = new AlertDialog.Builder(FavorilerActivity.this);
                mesaj.setIcon(R.drawable.icon_share);
                mesaj.setTitle("Paylaş");
                mesaj.setMessage("Bu filmi paylaşmak istiyor musunuz?");
                mesaj.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Paylas gorev = new Paylas();
                        gorev.execute(kullanici_adi, secilenFilm.getImdbID(), secilenFilm.getTitle());
                    }
                });
                mesaj.setNegativeButton("Hayır", null);
                mesaj.create().show();

            }
        });
    }

    class Paylas extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL("http://www.tildaweb.com/film/filmPaylas.php?kullanici_adi="+strings[0]+"&imdb_id="+strings[1]+"&film_adi="+strings[2]);
                HttpURLConnection baglanti = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(baglanti.getInputStream()));
                String sonuc = "";
                String satir = "";
                while((satir=br.readLine())!=null){
                    sonuc+=satir;
                }
                br.close();
                baglanti.disconnect();
                return sonuc;
            } catch (Exception e) {
                System.out.println("Hata doInBackground :" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(FavorilerActivity.this, "Sonuç : " + s, Toast.LENGTH_SHORT).show();
        }
    }
}

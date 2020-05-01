package com.tilda.arasinavuygulamasi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.tilda.arasinavuygulamasi.adapter.OzelAdapter_Paylasim;
import com.tilda.arasinavuygulamasi.model.Film;
import com.tilda.arasinavuygulamasi.R;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PaylasimlarActivity extends AppCompatActivity {
    ArrayList<Film> filmler;
    OzelAdapter_Paylasim ozelAdapter;
    ListView listView_paylasim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paylasimlar);

        setTitle("Paylaşılan Filmler");

        listView_paylasim = findViewById(R.id.listView4);

        filmler = new ArrayList<>();
        ozelAdapter = new OzelAdapter_Paylasim(this, filmler);
        listView_paylasim.setAdapter(ozelAdapter);

        FilmleriIndir filmleriIndir = new FilmleriIndir();
        filmleriIndir.execute();
    }

    class FilmleriIndir extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://www.tildaweb.com/film/paylasilanFilmler.php");
                System.out.println(url.toString());
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line=br.readLine())!=null){
                    sb.append(line);
                }
                br.close();
                c.disconnect();
                return sb.toString();
            } catch (Exception e) {
                System.out.println("Hata (doInBackground) : " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("Alınan JSON verisi = " + s);
            if(s!=null) {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    Gson gson = new Gson();
                    Film f;

                    filmler.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        f = gson.fromJson(jsonArray.getJSONObject(i).toString(), Film.class);
                        filmler.add(f);
                    }
                    ozelAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    System.out.println("Hata : " + e.getMessage());
                }
            }
        }
    }
}

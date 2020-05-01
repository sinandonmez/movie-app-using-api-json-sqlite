package com.tilda.arasinavuygulamasi.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tilda.arasinavuygulamasi.adapter.OzelAdapter;
import com.tilda.arasinavuygulamasi.database.Veritabani;
import com.tilda.arasinavuygulamasi.model.Film;
import com.tilda.arasinavuygulamasi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AramaActivity extends AppCompatActivity {
    ArrayList<Film> filmler;
    OzelAdapter ozelAdapter;
    ListView listView_arama;
    EditText edittext_arama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arama);

        setTitle("Film Ara");

        listView_arama = findViewById(R.id.listView2);
        edittext_arama = findViewById(R.id.editText);

        filmler = new ArrayList<>();
        ozelAdapter = new OzelAdapter(this, filmler);
        listView_arama.setAdapter(ozelAdapter);

        edittext_arama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println(s.toString());
                if (s.toString().length() >= 3) {
                    AramaYap gorev = new AramaYap();
                    gorev.execute(s.toString());
                }
            }
        });

        listView_arama.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Film secilenFilm = filmler.get(position);

                AlertDialog.Builder mesaj = new AlertDialog.Builder(AramaActivity.this);
                mesaj.setTitle("Favorilere Kaydet");
                mesaj.setMessage("Bu filmi favoriler bölümüne eklensin mi?");
                mesaj.setIcon(R.drawable.icon_fav);
                mesaj.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Veritabani vt = new Veritabani(AramaActivity.this);
                        long cevap = vt.filmEkle(secilenFilm);
                        Toast.makeText(AramaActivity.this, "Seçilen film favorilere eklendi.", Toast.LENGTH_SHORT).show();
                        vt.close();
                    }
                });
                mesaj.setNegativeButton("Hayır", null);
                mesaj.create().show();
            }
        });
    }

    class AramaYap extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                String api_key = "b490bf62";//Sizin API KEYiniz
                URL url = new URL("http://www.omdbapi.com/?s="+strings[0]+"&page=1&apikey=" + api_key);
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
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("Search");

                    //1.YÖNTEM - JSONObject
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject filmJSON = jsonArray.getJSONObject(i);
                        String imdbId = filmJSON.getString("imdbID");
                        String title = filmJSON.getString("Title");
                        String type = filmJSON.getString("Type");
                        String year = filmJSON.getString("Year");
                        String poster = filmJSON.getString("Poster");
                        Film f = new Film(0,null, imdbId, title, type, year, poster);
                        filmler.add(f);
                    }

                    //2.YÖNTEM - GSON
//                    Gson gson = new Gson();
//                    filmler.clear();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        Film f = gson.fromJson(jsonArray.getJSONObject(i).toString(), Film.class);
//                        filmler.add(f);
//                    }

                    ozelAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    System.out.println("Hata : " + e.getMessage());
                }
            }
        }
    }
}

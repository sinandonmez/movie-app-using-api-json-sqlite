package com.tilda.arasinavuygulamasi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tilda.arasinavuygulamasi.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GirisActivity extends AppCompatActivity {
    EditText edittext_kullanici, edittext_sifre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        edittext_kullanici = findViewById(R.id.eKullaniciAdi);
        edittext_sifre = findViewById(R.id.eSifre);

        setTitle("Giriş");
    }

    public void btnKaydol(View v){
        String kullanici = edittext_kullanici.getText().toString();
        String sifre = edittext_sifre.getText().toString();

        if(kullanici.equals("") || sifre.equals("")) {
            Toast.makeText(this, "Eksik bilgi girdiniz.", Toast.LENGTH_SHORT).show();
        }
        else{
            Kayit gorev = new Kayit();
            gorev.execute(kullanici, sifre);
        }
    }
    public void btnGiris(View v){
        String kullanici = edittext_kullanici.getText().toString();
        String sifre = edittext_sifre.getText().toString();

        if(kullanici.equals("") || sifre.equals("")) {
            Toast.makeText(this, "Eksik bilgi girdiniz.", Toast.LENGTH_SHORT).show();
        }
        else{
            Giris gorev = new Giris();
            gorev.execute(kullanici, sifre);
        }
    }

    class Kayit extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL("http://www.tildaweb.com/film/kullaniciEkle.php?kullanici_adi="+strings[0]+"&sifre="+strings[1]);
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
            Toast.makeText(GirisActivity.this, "Sonuç : " + s, Toast.LENGTH_SHORT).show();
        }
    }

    class Giris extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL("http://www.tildaweb.com/film/giris.php?kullanici_adi="+strings[0]+"&sifre="+strings[1]);
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
            Toast.makeText(GirisActivity.this, "Sonuç : " + s, Toast.LENGTH_SHORT).show();
            if(s.equals("true")){

                Intent i = new Intent(GirisActivity.this, MainActivity.class);
                i.putExtra("kullanici_adi", edittext_kullanici.getText().toString());
                startActivity(i);

                //Kutuları temizle
                edittext_kullanici.setText("");
                edittext_sifre.setText("");
                finish();
            }
        }
    }
}

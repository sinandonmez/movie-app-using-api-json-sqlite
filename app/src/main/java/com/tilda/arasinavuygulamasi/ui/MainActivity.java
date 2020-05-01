package com.tilda.arasinavuygulamasi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tilda.arasinavuygulamasi.R;

public class MainActivity extends AppCompatActivity {

    String kullanici_adi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Girişten gelen kullanıcı adı. Favoriler activity sine gönderilecek.
        kullanici_adi = getIntent().getStringExtra("kullanici_adi");

        setTitle("Ana Menü");
    }

    public void btnArama(View v){
        Intent i = new Intent(this, AramaActivity.class);
        startActivity(i);
    }
    public void btnFavoriler(View v){
        Intent i = new Intent(this, FavorilerActivity.class);
        i.putExtra("kullanici_adi", kullanici_adi);
        startActivity(i);
    }
    public void btnPaylasilanlar(View v){
        Intent i = new Intent(this, PaylasimlarActivity.class);
        startActivity(i);
    }
    public void btnCikis(View v){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

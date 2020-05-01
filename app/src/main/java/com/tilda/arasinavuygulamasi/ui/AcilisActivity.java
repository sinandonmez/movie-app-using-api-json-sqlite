package com.tilda.arasinavuygulamasi.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.tilda.arasinavuygulamasi.R;

public class AcilisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acilis);
        if(internetBaglantisiniKontrolEt()) {
            CountDownTimer timer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Intent i = new Intent(AcilisActivity.this, GirisActivity.class);
                    startActivity(i);
                    finish();
                }
            };
            timer.start();
        }
        else{
            AlertDialog.Builder mesaj = new AlertDialog.Builder(this);
            mesaj.setMessage("İnternet bağlantısı yok!");
            mesaj.setPositiveButton("Kapat", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            mesaj.create().show();
        }

    }

    private boolean internetBaglantisiniKontrolEt() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
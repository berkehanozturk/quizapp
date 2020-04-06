package com.sagancompany.denemesinif;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class kazandin extends AppCompatActivity {
int skor;
TextView puan;
    TextView puan2;
Button callback;
String chooser;
    InterstitialAd interstitialAd;
    private List<film> films =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kazandin);
        MobileAds.initialize(kazandin.this,"ca-app-pub-2261379780093081~7818574567");

        interstitialAd = new InterstitialAd(kazandin.this);
        interstitialAd.setAdUnitId("ca-app-pub-2261379780093081/6980969765");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        skor = getIntent().getIntExtra("puan", skor);
        Log.d("alo", "onCreate: kazandin "+ skor);
        chooser = getIntent().getStringExtra("chooser");
        Log.d("alo", "chooser: "+chooser);
        puan2 = findViewById(R.id.kazandin);
        puan=findViewById(R.id.puanSkoru);
        Log.d("alo", "skor değer,: "+skor);
        puan.setText("Puanınız "+String.valueOf(skor));
      switch (skor){
          case 0:
              puan2.setText("Başarabilirsin İnanıyorum");
              break;


          case 1:
              puan2.setText("Biraz daha zorlamalısın");
              break;

          case 2:
              puan2.setText("Biraz daha gayret et");
              break;

          case 3:
              puan2.setText("Yavas yavas olucak");

          case 4:
              puan2.setText("Harika gidiyorsun");
              break;

          case 5:
              puan2.setText("Böyle devam et");
              break;

          case 6:
              puan2.setText("Çok güzel");
              break;

          case 7:
              puan2.setText("Sen başarılı bir cocuksun");
              break;

              default:
                  puan2.setText("Mükemmel!!!!");



      }


        Bundle extra = getIntent().getBundleExtra("extra");
        films = (ArrayList<film>) extra.getSerializable("objects");


        callback=findViewById(R.id.callBackButton);
        callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(films.size()!=0 ){
            if (chooser.equalsIgnoreCase("dogru")){
                if (isNetworkConnected() ==true){
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                    startsecond();


                }
                interstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {

                        Log.d("alo", "onCreate: butona bastıın kazandın");

                        Bundle extra2 = new Bundle();
                        extra2.putSerializable("objects", (Serializable) films);
                        Intent kazandin1 = new Intent(kazandin.this, dogruActivity.class);
                        kazandin1.putExtra("extra", extra2);
                        startActivity(kazandin1);
                        finish(); //fin
                        interstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                });
                    }
            else if (chooser.equalsIgnoreCase("soru")){
                Log.d("alo", "onCreate: butona bastıın kazandın");

                if (isNetworkConnected() ==true){
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                    startthird();


                }
                interstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {

                        Log.d("alo", "onCreate: butona bastıın kazandın");

                        Bundle extra2 = new Bundle();
                        extra2.putSerializable("objects", (Serializable) films);
                        Intent kazandin1 = new Intent(kazandin.this, soruAcitivtity.class);
                        kazandin1.putExtra("extra", extra2);
                        startActivity(kazandin1);
                        finish(); //fin
                        interstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                });
            }



                }
                else{
                    if (chooser.equalsIgnoreCase("dogru")){

                        Intent resgame = new Intent(kazandin.this , MainActivity.class);
                        finish();
                    }
                    else if (chooser.equalsIgnoreCase("soru")){
                        Intent resgame = new Intent(kazandin.this , MainActivity.class);
                        finish();
                    }

                }


            }
        });

    }
    public  void startsecond(){
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }
        else {

            Bundle extra2 = new Bundle();
            extra2.putSerializable("objects", (Serializable) films);
            Intent kazandin1 = new Intent(kazandin.this, dogruActivity.class);
            kazandin1.putExtra("extra", extra2);
            startActivity(kazandin1);
            finish(); //fin
            interstitialAd.loadAd(new AdRequest.Builder().build());
        }

    }
    public  void startthird(){
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }
        else {

            Bundle extra2 = new Bundle();
            extra2.putSerializable("objects", (Serializable) films);
            Intent kazandin1 = new Intent(kazandin.this, soruAcitivtity.class);
            kazandin1.putExtra("extra", extra2);
            startActivity(kazandin1);
            finish(); //fin
            interstitialAd.loadAd(new AdRequest.Builder().build());
        }

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}

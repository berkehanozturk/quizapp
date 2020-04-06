package com.sagancompany.denemesinif;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class dogruActivity extends AppCompatActivity {
    private List<film> films = new ArrayList<>();
    private List<Button> Buttons = new ArrayList<>();

    TextView soruView;
    TextView puanView;
    int puan = 0;
    Button cevapA;
    Button cevapB;
    Button back;
    String dogruCevap;
    int randomint;MediaPlayer mp;
    MediaPlayer mp2;
    int counter = 0;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogru);
        adView = findViewById(R.id.adViev2);


        MobileAds.initialize(dogruActivity.this,"ca-app-pub-2261379780093081~7818574567");
        AdRequest adRequest=new  AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Bundle extra = getIntent().getBundleExtra("extra");
        films = (ArrayList<film>) extra.getSerializable("objects");
        //Log.d("alo", "onCreate: "+films);
        soruView = findViewById(R.id.questionText);
        cevapA = findViewById(R.id.answerA);
        back = findViewById(R.id.backbutton1);
        cevapB = findViewById(R.id.answerB);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        puan = getIntent().getIntExtra("puan", puan);
        counter = getIntent().getIntExtra("counter", counter);

        puanView = findViewById(R.id.puan);
        puanView.setText( "Puan:"+puan);
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp = MediaPlayer.create(this, R.raw.applause);
        mp2 = new MediaPlayer();
        mp2.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp2 = MediaPlayer.create(this, R.raw.wrong);
        counter++;
        if (films.size() != 0 && counter != 10) {


            Log.d("alo", "onCreate: counter " + counter);
            Log.d("alo", "onCreate: counter film " + films.size());
            Random r = new Random();
            randomint = r.nextInt(films.size());
            Log.d("alo", "film sayisi: " + films.size());



            soruView.setText(films.get(randomint).title);
            cevapA.setText(films.get(randomint).getCevapsSiklari()[1].toString());
            cevapB.setText(films.get(randomint).getCevapsSiklari()[2].toString());
            Buttons.add(cevapA);
            Buttons.add(cevapB);

            dogruCevap = films.get(randomint).getCevapsSiklari()[3].toString();
            Log.d("alo", "soruda budur  :" + films.get(randomint).getTitle().toString());

            Log.d("alo", "doggru cevap budur :" + dogruCevap );
            Log.d("alo", "doggru cevap uzunlugu budur:" + dogruCevap.length());
            Log.d("alo", "şıka nın sonucu :" + films.get(randomint).getCevapsSiklari()[1].toString() );
            Log.d("alo", "şıka nın uzunlugu:" + cevapA.getText().length());

            Log.d("alo", "şıkb nın sonucu:" + films.get(randomint).getCevapsSiklari()[2].toString() );
            Log.d("alo", "şıkb nın uzunlugu:" + cevapB.getText().length());

            cevapA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cevapA.getText().toString().equalsIgnoreCase(dogruCevap)) {
                        mp.start();
                        puan++;
                        puanView.setText("Puan: "+puan);
                        cevapA.setBackgroundResource(R.drawable.customgreen);
                        cevapA.setClickable(false);
                        cevapB.setClickable(false);

                        //Intent refresh = new Intent(soruAcitivtity.this, soruAcitivtity.class);

                        // refresh.putExtra("puan",puan);
                        films.remove(randomint);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1500);
                                    Intent refresh = new Intent(dogruActivity.this, dogruActivity.class);
                                    Bundle extra = new Bundle();
                                    extra.putSerializable("objects", (Serializable) films);
                                    refresh.putExtra("extra", extra);
                                    refresh.putExtra("puan", puan);
                                    refresh.putExtra("counter", counter);
                                    startActivity(refresh);//Start the same Activity
                                    finish(); //fin
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                    } else {
                        mp2.start();
                        cevapA.setBackgroundResource(R.drawable.customred);
                        cevapA.setClickable(false);
                        cevapB.setClickable(false);

                        for (int i = 0; i < Buttons.size(); i++) {
                            if (Buttons.get(i).getText().toString().equalsIgnoreCase(dogruCevap)) {
                                Buttons.get(i).setBackgroundResource(R.drawable.customgreen);

                            }
                        }films.remove(randomint);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1500);
                                    Intent refresh = new Intent(dogruActivity.this, dogruActivity.class);
                                    Bundle extra = new Bundle();
                                    extra.putSerializable("objects", (Serializable) films);
                                    refresh.putExtra("extra", extra);
                                    refresh.putExtra("puan", puan);
                                    refresh.putExtra("counter", counter);

                                    startActivity(refresh);//Start the same Activity
                                    finish(); //fin
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        //  cevapA.setVisibility(View.INVISIBLE);

                    }
                }
            });

            cevapB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cevapB.getText().toString().equalsIgnoreCase(dogruCevap)) {
                        mp.start();
                        puan++;
                        cevapB.setBackgroundResource(R.drawable.customgreen);

                        puanView.setText( "Puan:"+puan);
                        cevapA.setClickable(false);
                        cevapB.setClickable(false);

                        films.remove(randomint);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1500);
                                    Intent refresh = new Intent(dogruActivity.this, dogruActivity.class);
                                    Bundle extra = new Bundle();
                                    extra.putSerializable("objects", (Serializable) films);
                                    refresh.putExtra("extra", extra);
                                    refresh.putExtra("puan", puan);
                                    refresh.putExtra("counter", counter);

                                    startActivity(refresh);//Start the same Activity
                                    finish(); //fin
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else {
                        mp2.start();
                        cevapB.setBackgroundResource(R.drawable.customred);
                        cevapA.setClickable(false);
                        cevapB.setClickable(false);

                        for (int i = 0; i < Buttons.size(); i++) {
                            if (Buttons.get(i).getText().toString().equalsIgnoreCase(dogruCevap)) {
                                Buttons.get(i).setBackgroundResource(R.drawable.customgreen);

                            }
                        }
                        films.remove(randomint);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    Thread.sleep(1500);
                                    Intent refresh = new Intent(dogruActivity.this, dogruActivity.class);
                                    Bundle extra = new Bundle();
                                    extra.putSerializable("objects", (Serializable) films);
                                    refresh.putExtra("extra", extra);
                                    refresh.putExtra("puan", puan);
                                    refresh.putExtra("counter", counter);

                                    startActivity(refresh);//Start the same Activity
                                    finish(); //fin
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            });

        }
        else{
            if(films.size() != 1 ){
                Bundle extra2 = new Bundle();
                extra2.putSerializable("objects", (Serializable) films);
                Intent kazandin1 = new Intent(dogruActivity.this, kazandin.class);
                kazandin1.putExtra("extra", extra2);
                kazandin1.putExtra("chooser","dogru");
                kazandin1.putExtra("puan", puan);
                startActivity(kazandin1);
                finish(); //fin
            }


        }
    }
}
package com.sagancompany.denemesinif;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class soruAcitivtity extends AppCompatActivity {
    private List<film> films =new ArrayList<>();
    private List<Button> Buttons =new ArrayList<>();

    String dizi[];
    String cevapdizi [];
    String cevapSiklari2 [];
    TextView soruView;
    TextView puanView;

    Button cevapA ;
    Button cevapB;
    Button back;

    String dogruCevap;
    int randomint;
    Button cevapC ;
    int puan=0;
    int counter = 0;
    AudioManager audioManager;
    MediaPlayer mp;
    MediaPlayer mp2;
    InterstitialAd interstitialAd;
    AdView adView;
    AdView adView2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_acitivtity);
        soruView = findViewById(R.id.questionText);
        cevapA = findViewById(R.id.answerA);
        cevapB = findViewById(R.id.answerB);
        cevapC = findViewById(R.id.answerC);
        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        puan = getIntent().getIntExtra("puan", puan);
        Bundle extra = getIntent().getBundleExtra("extra");
        counter = getIntent().getIntExtra("counter", counter);
        adView = findViewById(R.id.adViev3);


        MobileAds.initialize(soruAcitivtity.this,"ca-app-pub-2261379780093081~7818574567");
        AdRequest adRequest=new  AdRequest.Builder().build();
        adView.loadAd(adRequest);



        films = (ArrayList<film>) extra.getSerializable("objects");
        Log.d("alo", "onCreate: " + films);
        puanView = findViewById(R.id.puan);
        puanView.setText("Puan: "+puan);
        mp=new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp=MediaPlayer.create(this,R.raw.applause);
        mp2=new MediaPlayer();
        mp2.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp2=MediaPlayer.create(this,R.raw.wrong);
        counter++;
        Log.d("alo", "onCreate: filmsizı"+films.size());
        if (films.size() != 0 && counter !=10 ) {


            Log.d("alo", "onCreate: counter "+counter);
            Log.d("alo", "onCreate: counter film "+films.size());
        Random r = new Random();
        randomint = r.nextInt(films.size());
        Log.d("alo", "film sayisi: " + films.size());


        soruView.setText(films.get(randomint).title);
        cevapA.setText(films.get(randomint).getCevapsSiklari()[1].toString());
        cevapB.setText(films.get(randomint).getCevapsSiklari()[2].toString());
        cevapC.setText(films.get(randomint).getCevapsSiklari()[3].toString());
        Buttons.add(cevapA);
        Buttons.add(cevapB);
        Buttons.add(cevapC);

        dogruCevap = films.get(randomint).getCevapsSiklari()[4].toString();
        Log.d("alo", "doggru:" + dogruCevap + "selam");
        Log.d("alo", "doggru:" + dogruCevap.length());
        Log.d("alo", "şıka:" + films.get(randomint).getCevapsSiklari()[1].toString() + "selam1");

        Log.d("alo", "şıka:" + films.get(randomint).getCevapsSiklari()[1].toString() + "selam");
        Log.d("alo", "şıkb:" + films.get(randomint).getCevapsSiklari()[2].toString() + "selam");
        Log.d("alo", "şıkc:" + films.get(randomint).getCevapsSiklari()[3].toString() + "selam");
        Log.d("alo", "şıka:" + cevapA.getText().length());
        Log.d("alo", "şıkb:" + cevapB.getText().length());
        Log.d("alo", "şıkc:" + cevapC.getText().length());
        cevapA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cevapA.getText().toString().equalsIgnoreCase(dogruCevap)) {
                    mp.start();
                    puan++;
                    puanView.setText("Puan:"+puan);
                    cevapA.setBackgroundResource(R.drawable.customgreen);
                 //  cevapA.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    cevapA.setClickable(false);
                    cevapB.setClickable(false);
                    cevapC.setClickable(false);
                    //Intent refresh = new Intent(soruAcitivtity.this, soruAcitivtity.class);

                    // refresh.putExtra("puan",puan);
                    films.remove(randomint);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                cevapA.setClickable(false);
                                cevapB.setClickable(false);
                                cevapC.setClickable(false);
                                Thread.sleep(1500);
                                Intent refresh = new Intent(soruAcitivtity.this, soruAcitivtity.class);
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
                    cevapC.setClickable(false);
                    for (int i = 0; i < Buttons.size(); i++) {
                        if (Buttons.get(i).getText().toString().equalsIgnoreCase(dogruCevap)) {
                            Buttons.get(i).setBackgroundResource(R.drawable.customgreen);

                        }
                    }films.remove(randomint);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                cevapA.setClickable(false);
                                cevapB.setClickable(false);
                                cevapC.setClickable(false);
                                Thread.sleep(1500);
                                Intent refresh = new Intent(soruAcitivtity.this, soruAcitivtity.class);
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

                    puanView.setText("Puan:"+puan);
                    cevapA.setClickable(false);
                    cevapB.setClickable(false);
                    cevapC.setClickable(false);
                    films.remove(randomint);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                cevapA.setClickable(false);
                                cevapB.setClickable(false);
                                cevapC.setClickable(false);
                                Thread.sleep(1500);
                                Intent refresh = new Intent(soruAcitivtity.this, soruAcitivtity.class);
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
                    cevapC.setClickable(false);                    //  cevapA.setVisibility(View.INVISIBLE);
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
                                cevapA.setClickable(false);
                                cevapB.setClickable(false);
                                cevapC.setClickable(false);
                                Thread.sleep(1500);
                                Intent refresh = new Intent(soruAcitivtity.this, soruAcitivtity.class);
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
        cevapC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cevapC.getText().toString().equalsIgnoreCase(dogruCevap)) {
                    mp.start();

                    puan++;
                    cevapC.setBackgroundResource(R.drawable.customgreen);

                    puanView.setText("Puan:"+puan);
                    cevapA.setClickable(false);
                    cevapB.setClickable(false);
                    cevapC.setClickable(false);
                    films.remove(randomint);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                cevapA.setClickable(false);
                                cevapB.setClickable(false);
                                cevapC.setClickable(false);
                                Thread.sleep(1500);
                                Intent refresh = new Intent(soruAcitivtity.this, soruAcitivtity.class);
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
                    cevapC.setBackgroundResource(R.drawable.customred);
                    cevapA.setClickable(false);
                    cevapB.setClickable(false);
                    cevapC.setClickable(false);
                    //  cevapA.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < Buttons.size(); i++) {
                        if (Buttons.get(i).getText().toString().equalsIgnoreCase(dogruCevap)) {
                            Buttons.get(i).setBackgroundResource(R.drawable.customgreen);

                        }
                    }
                    films.remove(randomint);
                    //save();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                cevapA.setClickable(false);
                                cevapB.setClickable(false);
                                cevapC.setClickable(false);
                                Thread.sleep(1500);
                                Intent refresh = new Intent(soruAcitivtity.this, soruAcitivtity.class);
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
                Intent kazandin1 = new Intent(soruAcitivtity.this, kazandin.class);
                kazandin1.putExtra("extra", extra2);
                kazandin1.putExtra("chooser","soru");

                kazandin1.putExtra("puan", puan);
                startActivity(kazandin1);
                finish();


            }


        }
    }

 private  void  save(){
     SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
     SharedPreferences.Editor editor = sharedPreferences.edit();
     Gson gson = new Gson();
     String json = gson.toJson(films);
     editor.putString("task list", json);
     editor.apply();
 }




}

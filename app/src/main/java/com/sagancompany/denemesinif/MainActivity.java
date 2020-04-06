package com.sagancompany.denemesinif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<film> films =new ArrayList<>();
    private List<film> filmsTrueFalse =new ArrayList<>();
    Button soru;
    Button dogru;
    String dizi[];
    String cevapdizi [];
    String TrueFalseDizi[];
    String AnswerDizi [];
    String cevapSiklari2 [];
    TextView soruView;
    TextView cevapView ;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    InterstitialAd interstitialAd;
    AdView adView;
    AdView adView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adView2 = findViewById(R.id.adViev2);

        MobileAds.initialize(MainActivity.this,"ca-app-pub-2261379780093081~7818574567");


        AdRequest adRequest2=new  AdRequest.Builder().build();
        adView2.loadAd(adRequest2);
        interstitialAd = new InterstitialAd(MainActivity.this);
        interstitialAd.setAdUnitId("ca-app-pub-2261379780093081/8660882949");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        //exampleList.add(new ExampleItem(R.drawable.okuldegis1, "birinci sınıflar için bilgi yarışması", "çoktan seçmeli sorular "));
        //exampleList.add(new ExampleItem(R.drawable.dogrudegis, "birinci sınıflar için doğru yanlış", "doğru yanlış soruları"));
       // exampleList.add(new ExampleItem(R.drawable.ic_sun, "birinci sınıflar için", "Line 6"));
      //  mRecyclerView = findViewById(R.id.recycler);
       // mRecyclerView.setHasFixedSize(true);
       // layoutManager = new LinearLayoutManager(this);
        //mAdapter = new ExampleAdapter(exampleList);
     //   mRecyclerView.setLayoutManager(layoutManager);
      //  mRecyclerView.setAdapter(mAdapter);
        soru=findViewById(R.id.soruButton);
        dogru = findViewById(R.id.dogruButton);
           try {
            readWeatherData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ReadTrueFalse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    soru.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isNetworkConnected() ==true){
                interstitialAd.loadAd(new AdRequest.Builder().build());
                startsecond();


                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    Log.d("alo", "ggosteremedik: ");
                }



            }
            interstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdClosed() {

                    Bundle extra = new Bundle();
                    extra.putSerializable("objects", (Serializable) films);
                    Intent yeni = new Intent(MainActivity.this, soruAcitivtity.class);
                    yeni.putExtra("extra", extra);
                    startActivity(yeni);
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });


        }
    });
        dogru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected() ==true){
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                    startthird();





                }
                interstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {

                        Bundle extra = new Bundle();
                        extra.putSerializable("objects", (Serializable) filmsTrueFalse);
                        Intent yeni = new Intent(MainActivity.this, dogruActivity.class);
                        yeni.putExtra("extra", extra);
                        startActivity(yeni);
                        interstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                });
            }
        });



        /*mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("alo", "onItemClick: ");

                Toast.makeText(getApplicationContext(),exampleList.get(position).getmText1(), LENGTH_LONG).show();
                if (exampleList.get(position).getmText1().equalsIgnoreCase("birinci sınıflar için bilgi yarışması"))
                {
                    Bundle extra = new Bundle();
                    extra.putSerializable("objects", (Serializable) films);
                    Intent yeni = new Intent(MainActivity.this, soruAcitivtity.class);
                    yeni.putExtra("extra", extra);
                    startActivity(yeni);

                }
                else if(exampleList.get(position).getmText1().equalsIgnoreCase("birinci sınıflar için doğru yanlış")){

                    Bundle extra = new Bundle();
                    extra.putSerializable("objects",(Serializable) filmsTrueFalse);
                    Intent yeni = new Intent(MainActivity.this,dogruActivity.class);
                    yeni.putExtra("extra",extra);
                    startActivity(yeni);


                }
                //ChangeItem(position,"clicked");
            }
        });

         */





       // soruView.setText(films.get(2).title.toString());
       // cevapView.setText(films.get(2).getCevapsSiklari()[4].toString());
        for (int i = 0; i <films.size(); i++) {
            Log.d("main", "onCreate: "+films.get(i).getCevapsSiklari()[4]);
        }




    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    public  void startsecond(){
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }
        else {

            Bundle extra = new Bundle();
            extra.putSerializable("objects", (Serializable) films);
            Intent yeni = new Intent(MainActivity.this, soruAcitivtity.class);
            yeni.putExtra("extra", extra);
            startActivity(yeni);
        }

    }
    public  void startthird(){
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }
        else {

            Bundle extra = new Bundle();
            extra.putSerializable("objects", (Serializable) filmsTrueFalse);
            Intent yeni = new Intent(MainActivity.this, dogruActivity.class);
            yeni.putExtra("extra", extra);
            startActivity(yeni);
        }

    }

    private  void readWeatherData() throws IOException {
        InputStream is= getResources().openRawResource(R.raw.sinif);
        BufferedReader reader =new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line;
        int count=0;
        line = reader.readLine();
        while (line!=null){
           Log.d("alo", "burda patlıyor: "+line);

            film myfilm=new film();
           dizi= line.split("cevap");
            //Log.d("alo", "readWeatherData1: "+dizi[0]);
            //Log.d("alo", "readWeatherData2: "+dizi[1]);
            cevapdizi = dizi[1].split("-");
            for (int i = 0; i <cevapdizi.length-1 ; i++) {
                cevapdizi[i]=cevapdizi[i].substring(0,cevapdizi[i].length()-1);
            }
            Log.d("alo", "readWeatherData: "+cevapdizi[0]+"  cevap dizi A şıkkı "+cevapdizi[1]+"cevap dizi B şıkkı "+cevapdizi[2]+"cevapdizi C sıkkı" +cevapdizi[3]+"dogru cevap "+cevapdizi[4]);
            myfilm.setCevapsSiklari(cevapdizi);
            myfilm.setTitle(dizi[0]);
           // Log.d("alo", "film klassı cevabı "+ myfilm.getCevapsSiklari()[1]);
            films.add(myfilm);
            line = reader.readLine();
        }

    }
    private  void ReadTrueFalse() throws IOException {
        InputStream is= getResources().openRawResource(R.raw.dogruyalnis);
        BufferedReader reader =new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line;
        int count=0;
        line = reader.readLine();
        while (line!=null){
            // Log.d("alo", "burda patlıyor: "+line);

            film myfilm1=new film();
            TrueFalseDizi= line.split("cevap");
            //Log.d("alo", "readWeatherData1: "+dizi[0]);
            //Log.d("alo", "readWeatherData2: "+dizi[1]);
            AnswerDizi = TrueFalseDizi[1].split("-");
            for (int i = 0; i <AnswerDizi.length-1 ; i++) {
                AnswerDizi[i]=AnswerDizi[i].substring(0,AnswerDizi[i].length()-1);
            }
            Log.d("alo", "ReadTrueFalse: "+TrueFalseDizi[0]);
            Log.d("alo", "ReadTrueFalse: "+AnswerDizi[0]+"  cevap dizi A şıkkı "+AnswerDizi[1]+" cevap dizi B şıkkı "+AnswerDizi[2]+" cevapdizi C sıkkı" +AnswerDizi[3]);
            myfilm1.setCevapsSiklari(AnswerDizi);
            myfilm1.setTitle(TrueFalseDizi[0]);
            // Log.d("alo", "film klassı cevabı "+ myfilm.getCevapsSiklari()[1]);
            filmsTrueFalse.add(myfilm1);
            line = reader.readLine();
        }
    }
}

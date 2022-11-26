package com.dotcont.app_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityPrincipal extends AppCompatActivity implements View.OnClickListener {

    Button start,paouse, reset;
    boolean isOn=false;
    TextView Crono;
    Thread cronos;
    int mili = 0, seg = 0, min = 0;
    Handler h=new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        start = (Button) findViewById(R.id.start);
        paouse = (Button) findViewById(R.id.pausa);
        reset = (Button) findViewById(R.id.reset);
        Crono = (TextView) findViewById(R.id.TiempoTV);

        start.setOnClickListener(this);
        paouse.setOnClickListener(this);
        reset.setOnClickListener(this);

        cronos = new Thread (new Runnable() {

            @Override
            public void run() {
                while (true){
                    if(isOn){
                        try{
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        mili++;
                        if(mili==999){
                            seg++;
                            mili=0;
                        }
                        if (seg==59){
                            min++;
                            seg = 0;
                        }
                        h.post(new Runnable() {

                            @Override
                            public void run() {
                                String m = "", s = "", mi = "";
                                if(mili < 10){
                                    m ="00" + mili;
                                } else if (mili < 100){
                                    m = "0" + mili;
                                } else {
                                    m = "" + mili;
                                }
                                if (seg < 10){
                                    s = "0" + seg;
                                } else {
                                    s = "" + seg;
                                }
                                if (min < 10){
                                    mi = "0" + min;
                                }else {
                                    mi = "" + min;
                                }
                                Crono.setText(mi+" : " +s+" : "+m+" ");
                            }
                        });
                    }
                }
            }
        });
        cronos.start();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.start:
                isOn = true;
                break;
            case R.id.pausa:
                isOn = false;
                break;
            case R.id.reset:
                isOn = false;
                mili = 0;
                seg = 0;
                min = 0;
                Crono.setText("00 : 00 : 000");
                break;
        }
    }
}
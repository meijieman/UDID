package com.major.udid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

@SuppressWarnings("all")
public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv1 = findViewById(R.id.tv_1);
        final TextView tv2 = findViewById(R.id.tv_2);
        final TextView tv3 = findViewById(R.id.tv_3);
        final TextView tv4 = findViewById(R.id.tv_4);

        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv1.setText("android id: " + DeviceUtil.getAndroidId(MainActivity.this));
                tv1.append("\ndevice id: " + DeviceUtil.getDeviceId(MainActivity.this));
                tv1.append("\ndevice id2: " + DeviceUtil.getDeviceId2(MainActivity.this));
                tv1.append("\nwlan mac: " + DeviceUtil.getWLANMAC(MainActivity.this) + ", " + DeviceUtil.getWLANMACShell());
                tv1.append("\nbt mac: " + DeviceUtil.getBTMAC());
                tv1.append("\npseudo id: " + DeviceUtil.getPseudoID());

                tv2.setText(DeviceUtil.getBuildInfo());

                tv3.setText("DeviceUuidFactory: " + new DeviceUuidFactory(MainActivity.this).getDeviceUuid().toString());

                tv4.setText("gen id" + Installation.id(MainActivity.this));

                Log.i("tag_uc", "" + tv1.getText());
                Log.i("tag_uc", "" + tv2.getText());
                Log.i("tag_uc", "" + tv3.getText());
                Log.i("tag_uc", "" + tv4.getText());
            }
        });

    }
}

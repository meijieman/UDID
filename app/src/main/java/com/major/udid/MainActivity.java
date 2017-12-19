package com.major.udid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv1 = findViewById(R.id.tv_1);
        final TextView tv2 = findViewById(R.id.tv_2);
        final TextView tv3 = findViewById(R.id.tv_3);

        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setText("imei: " + UniqueIdUtil.getIMEI(MainActivity.this));
                tv1.append("\nandroid id: " + UniqueIdUtil.getAndroidId(MainActivity.this));
                tv1.append("\nwlan mac: " + UniqueIdUtil.getWLANMAC(MainActivity.this));
                tv1.append("\nbt mac: " + UniqueIdUtil.getBTMAC());
                tv1.append("\npseudo id: " + UniqueIdUtil.getPseudoID());

                tv2.setText(UniqueIdUtil.getBuildInfo());

                tv3.setText("DeviceUuidFactory: " + new DeviceUuidFactory(MainActivity.this).getDeviceUuid().toString());

                Log.i("tag_uc", "" + tv1.getText());
                Log.i("tag_uc", "" + tv2.getText());
                Log.i("tag_uc", "" + tv3.getText());
            }
        });

    }
}

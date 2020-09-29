package com.sslabtzuchi;

import androidx.appcompat.app.AppCompatActivity;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements OnLocationsUpdatedListener, OnGoToLocationStatusChangedListener, OnDistanceToLocationChangedListener {
    private Button B1,B2;
    private Robot robot;
    private List<String> test1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        View appTheme = findViewById(R.id.all_1);
//        appTheme.getBackground().setAlpha(100);
        ImageView img = findViewById(R.id.Img_bk);
        img.setAlpha(0.7f);
        Button Btmp = findViewById(R.id.b1);
        Btmp.setAlpha(0.6f);
        Btmp = findViewById(R.id.b2);
        Btmp.setAlpha(0.6f);
        robot = Robot.getInstance();
        robot.addOnLocationsUpdatedListener(this);
        robot.addOnGoToLocationStatusChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        test1 = robot.getLocations();

        B1 = (Button) findViewById(R.id.b1);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TtsRequest temp = TtsRequest.create("中文測試",true);
//                robot.speak(temp);
//                test1 = robot.getLocations();
//                Log.d("test2",test1.toString());
//                Log.d("test3",test1.get(0).toString());
//                robot.goTo(test1.get(0).toString());
//               B1.setText("ttt");
//                TtsRequest temp = TtsRequest.create("中文測試",true);
//                robot.speak(temp);
                robot.goTo("home base");

            }
        });
        B2 = (Button) findViewById(R.id.b2);
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                robot.goTo("302");

            }
        });
    }

    @Override
    public void onLocationsUpdated(List<String> list) {

    }
    @Override
    public void onGoToLocationStatusChanged(String s, String s1, int i, String s2) {
        Log.d("s1",s1);
        Log.d("s2",s2);
        if (s1.equals("complete"))
        {
//            TtsRequest temp = TtsRequest.create("中文測試",true);
//            robot.speak(temp);
            Log.d("test","finish");
        }
    }

    @Override
    public void onDistanceToLocationChanged(@NotNull Map<String, Float> map) {

    }
}
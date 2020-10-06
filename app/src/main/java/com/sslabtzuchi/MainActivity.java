package com.sslabtzuchi;

import androidx.appcompat.app.AppCompatActivity;

import com.robotemi.sdk.BatteryData;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnBatteryStatusChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener;
import com.robotemi.sdk.navigation.model.SafetyLevel;
import com.robotemi.*;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements OnLocationsUpdatedListener, OnBatteryStatusChangedListener, OnGoToLocationStatusChangedListener, OnDistanceToLocationChangedListener {
    private Button B1,B2,B3;
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
        Btmp = findViewById(R.id.b3);
        Btmp.setAlpha(0.6f);
        robot = Robot.getInstance();
        robot.addOnLocationsUpdatedListener(this);
        robot.addOnGoToLocationStatusChangedListener(this);
        robot.addOnBatteryStatusChangedListener(this);
        Log.d("safe",robot.getNavigationSafety().getValue());
        robot.setNavigationSafety(SafetyLevel.MEDIUM);
        Log.d("safe2",robot.getNavigationSafety().getValue());
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
             //   Log.d("test",Integer.toString(robot.getLocations().size()));//2
                robot.goTo("302");
            }
        });
        B3 = (Button) findViewById(R.id.b3);
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Log.d("test",Integer.toString(robot.getLocations().size()));//2
                robot.goTo("elevator");

//                Log.d("batt",Integer.toString((robot.getBatteryData().component1())));
//               SafetyLevel safe =  robot.getNavigationSafety().getValue();
//                Log.d("safe",robot.getNavigationSafety().getValue());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        robot.removeOnLocationsUpdateListener(this);
        robot.removeOnGoToLocationStatusChangedListener(this);
        robot.removeOnDistanceToLocationChangedListener(this);
        robot.removeOnBatteryStatusChangedListener(this);

    }

    @Override
    public void onLocationsUpdated(List<String> list) {

    }
    @Override
    public void onGoToLocationStatusChanged(String s, String s1, int i, String s2) {
        Log.v("s1",s1);
        Log.v("s2",s2);
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

    @Override
    public void onBatteryStatusChanged(@Nullable BatteryData batteryData) {
//        Log.d("test","change");
//        if (robot.getBatteryData().component1()<=85) {
//            robot.goTo("home base");
//            Log.d("test","change92");
//        }
    }
}
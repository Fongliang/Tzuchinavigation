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
import com.robotemi.sdk.permission.OnRequestPermissionResultListener;
import com.robotemi.sdk.permission.Permission;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnRequestPermissionResultListener, OnLocationsUpdatedListener, OnBatteryStatusChangedListener, OnGoToLocationStatusChangedListener, OnDistanceToLocationChangedListener {
    private Button B1,B2,B3,B4;
    TtsRequest goat = TtsRequest.create("前往目的地",true);
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
        robot.addOnRequestPermissionResultListener(this);
        robot.requestToBeKioskApp();
//        Permission setting = Permission.SETTINGS;
//        List <Permission> Authority = new ArrayList<>();
//        Authority.add(setting);
//        robot.requestPermissions(Authority,1);
//        Log.d("setting",Integer.toString(robot.checkSelfPermission(Permission.SETTINGS)));
//        Log.d("kiosk",Boolean.toString(robot.isSelectedKioskApp()));
        Log.d("safe",robot.getNavigationSafety().getValue());
        robot.setNavigationSafety(SafetyLevel.MEDIUM);
        Log.d("safe2",robot.getNavigationSafety().getValue());
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

        Toast.makeText(this, robot.getNavigationSafety().getValue(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, Integer.toString(robot.checkSelfPermission(Permission.SETTINGS)), Toast.LENGTH_LONG).show();
        Toast.makeText(this, Boolean.toString(robot.isSelectedKioskApp()), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        test1 = robot.getLocations();
        B4 = (Button) findViewById(R.id.b4);
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);

            }
        });
        B1 = (Button) findViewById(R.id.b1);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                robot.speak(goat);
                try{
                    // delay 0.8 second
                    Thread.sleep(800);

                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
//                test1 = robot.getLocations();
//                Log.d("test2",test1.toString());
//                Log.d("test3",test1.get(0).toString());
//                robot.goTo(test1.get(0).toString());
//               B1.setText("ttt");
                robot.goTo("home base");

            }
        });
        B2 = (Button) findViewById(R.id.b2);
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Log.d("test",Integer.toString(robot.getLocations().size()));//2
                robot.speak(goat);
                robot.goTo("302");
            }
        });
        B3 = (Button) findViewById(R.id.b3);
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                robot.speak(goat);

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
        robot.removeOnRequestPermissionResultListener(this);

    }

    @Override
    public void onLocationsUpdated(List<String> list) {

    }
    @Override
    public void onGoToLocationStatusChanged(String s, String s1, int i, String s2) {
        TtsRequest temp;
        if (s1.equals("complete"))
        {
            temp = TtsRequest.create("到達目的地",true);
            robot.speak(temp);
        }
        else if (s1.equals("going"))
        {
            temp = TtsRequest.create("機器人移動中，請小心",true);
            robot.speak(temp);
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

    @Override
    public void onRequestPermissionResult(@NotNull Permission permission, int i, int i1) {
        Log.d("setting-r",Integer.toString(robot.checkSelfPermission(Permission.SETTINGS)));
        Log.d("kiosk-r",Boolean.toString(robot.isSelectedKioskApp()));
        Log.d("safe-r",robot.getNavigationSafety().getValue());
        robot.setNavigationSafety(SafetyLevel.MEDIUM);
        Log.d("safe2-r",robot.getNavigationSafety().getValue());
    }
}
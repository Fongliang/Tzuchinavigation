package com.sslabtzuchi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.robotemi.sdk.BatteryData;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnBatteryStatusChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener;
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener;
import com.robotemi.sdk.navigation.model.Position;
import com.robotemi.sdk.navigation.model.SafetyLevel;
import com.robotemi.*;
import com.robotemi.sdk.permission.OnRequestPermissionResultListener;
import com.robotemi.sdk.permission.Permission;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnRequestPermissionResultListener ,OnCurrentPositionChangedListener, OnLocationsUpdatedListener, OnBatteryStatusChangedListener, OnGoToLocationStatusChangedListener, OnDistanceToLocationChangedListener {
    private Button B1,B2,B3,B4;
//    TtsRequest goat = TtsRequest.create("前往目的地",false);
    private Robot robot;
    int speak_count =0;
    private List<String> test1;
    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            TtsRequest temp = TtsRequest.create("機器人移動中，請小心", false);
            robot.speak(temp);
            handler.postDelayed(this, 6000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img = findViewById(R.id.Img_bk);
        img.setAlpha(0.7f);
        Button Btmp = findViewById(R.id.b1);
        Btmp.setAlpha(0.6f);
        Btmp = findViewById(R.id.b2);
        Btmp.setAlpha(0.6f);
        Btmp = findViewById(R.id.b3);
        Btmp.setAlpha(0.6f);
        Btmp = findViewById(R.id.b4);
        Btmp.setAlpha(0.6f);
        robot = Robot.getInstance();
        robot.addOnLocationsUpdatedListener(this);
        robot.addOnDistanceToLocationChangedListener(this);
        robot.addOnGoToLocationStatusChangedListener(this);
        robot.addOnBatteryStatusChangedListener(this);
        robot.addOnRequestPermissionResultListener(this);
//        robot.requestToBeKioskApp();
//        Permission setting = Permission.SETTINGS;
//        List <Permission> Authority = new ArrayList<>();
//        Authority.add(setting);
//        robot.requestPermissions(Authority,1);
//        Log.d("setting",Integer.toString(robot.checkSelfPermission(Permission.SETTINGS)));
//        Log.d("kiosk",Boolean.toString(robot.isSelectedKioskApp()));
        B1 = (Button) findViewById(R.id.b1);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                robot.goTo("home base");

            }
        });

//        Toast.makeText(this, robot.getNavigationSafety().getValue(), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        test1 = robot.getLocations();
        B4 = (Button) findViewById(R.id.b4);
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("battery",Integer.toString(robot.getBatteryData().component1()));
//                finish();
//                System.exit(0);


            }
        });
        B1 = (Button) findViewById(R.id.b1);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try{
//                    // delay 0.8 second
//                    Thread.sleep(800);
//
//                } catch(InterruptedException e) {
//                    e.printStackTrace();
//                }
//               B1.setText("ttt");
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
        Log.d("status",s1);
        String tmp =s;
        if (s.equals("home base"))
            tmp = "充電樁";
        String Tmplocation = "到達"+tmp;
        TtsRequest temp;
        if (s1.equals("complete"))
        {
            temp = TtsRequest.create(Tmplocation,false);
            robot.speak(temp);
            speak_count = 0;
            handler.removeCallbacks(runnable);
        }
        else if (s1.equals("start"))
        {
            Tmplocation = "前往"+tmp;
            temp = TtsRequest.create(Tmplocation,false);
            robot.speak(temp);
            speak_count = 0;
            handler.removeCallbacks(runnable);
        }
        else if (s1.equals("going"))
        {
            robot.speak(TtsRequest.create("機器人移動中，請小心", false));
            handler.postDelayed(runnable, 6000);
            speak_count=1;
        }
        else if (s1.equals("abort"))
        {
            handler.removeCallbacks(runnable);
        }
//        if (speak_count ==1)
//        {
//            handler.postDelayed(runnable, 5000);
//        }
    }

    @Override
    public void onDistanceToLocationChanged(@NotNull Map<String, Float> map) {

    }

    @Override
    public void onBatteryStatusChanged(@Nullable BatteryData batteryData) {
        if (robot.getBatteryData().component1()<=93)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("電池電量將耗盡");
            builder.setMessage("電池電量剩下15%請盡快充電，電量剩下10%時自動前往充電樁");
            builder.setIcon(R.drawable.battery);


            builder.setPositiveButton("立刻前往充電", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    robot.goTo("home base");
                }
            });

            builder.setNegativeButton("稍後再充電", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
//        Log.d("test","change");
        if (robot.getBatteryData().component1()<=10) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("電量剩下10%即將前往充電樁！");
            builder.setMessage("電量剩下10%即將前往充電樁！");
            builder.setIcon(R.drawable.battery);
            builder.setCancelable(true);
            final AlertDialog dlg = builder.create();
            dlg.show();
            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                public void run() {
                    Intent intent1 = new Intent();
                    intent1.setClass(MainActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                    dlg.dismiss();
                    robot.goTo("home base");
                    t.cancel();
                }
            }, 3500);
        }
    }

    @Override
    public void onRequestPermissionResult(@NotNull Permission permission, int i, int i1) {
        Log.d("setting-r",Integer.toString(robot.checkSelfPermission(Permission.SETTINGS)));
        Log.d("kiosk-r",Boolean.toString(robot.isSelectedKioskApp()));
        Log.d("safe-r",robot.getNavigationSafety().getValue());
        robot.setNavigationSafety(SafetyLevel.MEDIUM);
        Log.d("safe2-r",robot.getNavigationSafety().getValue());
    }

    @Override
    public void onCurrentPositionChanged(@NotNull Position position) {

    }
}
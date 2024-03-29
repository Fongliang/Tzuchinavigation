package com.sslabtzuchi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.robotemi.sdk.BatteryData;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnBatteryStatusChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnTelepresenceEventChangedListener;
import com.robotemi.sdk.model.CallEventModel;
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener;
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener;
import com.robotemi.sdk.navigation.model.Position;
import com.robotemi.sdk.navigation.model.SafetyLevel;
import com.robotemi.*;
import com.robotemi.sdk.permission.OnRequestPermissionResultListener;
import com.robotemi.sdk.permission.Permission;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.widget.TextView;
import android.widget.Toast;

import taobe.tec.jcc.JChineseConvertor;
public class MainActivity extends AppCompatActivity implements OnTelepresenceEventChangedListener,OnRequestPermissionResultListener ,OnCurrentPositionChangedListener, OnLocationsUpdatedListener, OnBatteryStatusChangedListener, OnGoToLocationStatusChangedListener, OnDistanceToLocationChangedListener {
    private Button B1,B2,B3,B4,B5,B6;
    int sslab = 0,tf= 0;
    private MediaPlayer player;
    String langtrans = "";
    int canceltmp = 0, firsttime =0;
    // TtsRequest goat = TtsRequest.create("前往目的地",false);
    private Robot robot;
    int speak_count =0;
    private List<String> test1;
    Handler handler=new Handler();
    // translate English to Chinese
    void ENtoZH(String speechtext)
    {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.CHINESE)
                        .build();
        final Translator englishGermanTranslator =
                Translation.getClient(options);
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        englishGermanTranslator.downloadModelIfNeeded(conditions);
        englishGermanTranslator.translate(speechtext)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                Log.d("stt123",translatedText);
                                try {
                                    JChineseConvertor jChineseConvertor = JChineseConvertor
                                            .getInstance();
                                    translatedText = jChineseConvertor.s2t(translatedText);
                                    Log.d("stt456",translatedText);
                                    robot.speak(TtsRequest.create(translatedText,false));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                            }
                        });
    }
    //--

    void ZHtoEN(String speechtext)
    {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.CHINESE)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();
        final Translator englishGermanTranslator =
                Translation.getClient(options);
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        englishGermanTranslator.downloadModelIfNeeded(conditions);
        englishGermanTranslator.translate(speechtext)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                Log.d("stt123",translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error.
                                // ...
                            }
                        });
    }

    //guide start repeat alarm
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if(player == null)
            {
                player = new MediaPlayer();
            }
            player.reset();
            try {
                player.setDataSource("sdcard/careful.mp3");
                player.prepare();
                player.start();
            } catch (Exception ex) {

            }
//            try{
//                Thread.sleep(5000);
//                robot.speak(TtsRequest.create("機器人移動中，請小心", false));
//            } catch(InterruptedException e) {
//                e.printStackTrace();
//            }

            handler.postDelayed(this, 5000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sslab = 1;
        tf = 0;
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
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
        Btmp = findViewById(R.id.b5);
        Btmp.setAlpha(0.6f);
        Btmp = findViewById(R.id.b6);
        Btmp.setAlpha(0.6f);
        robot = Robot.getInstance();
        robot.addOnLocationsUpdatedListener(this);
        robot.addOnDistanceToLocationChangedListener(this);
        robot.addOnGoToLocationStatusChangedListener(this);
        robot.addOnBatteryStatusChangedListener(this);
        robot.addOnRequestPermissionResultListener(this);
        robot.addOnTelepresenceEventChangedListener(this);
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
        sslab = 1;
        tf = 0;
        test1 = robot.getLocations();
        B6 = (Button) findViewById(R.id.b6);
        B6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Robot.getInstance().startTelepresence("Chen","0c8bc1fbacb21115b4492625e035c404"); //android
                Log.d("user",robot.getAllContact().toString());
            }
        });
        B5 = (Button) findViewById(R.id.b5);
        B5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Languagetranslate.class);
                startActivity(intent);
            }
        });
        B4 = (Button) findViewById(R.id.b4);
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tele---
//                Log.d("user",robot.getAllContact().toString());
//                Log.d("admin",robot.getAdminInfo().toString());
                //robot.speak(TtsRequest.create(Originaltext,false));
                //robot.startTelepresence("Jhewei","f126c1f2a6cf53b8b8770ab82dbacedc");
                //robot.startTelepresence("Jin","46a37cc49fdf493b27045b4155c764f6"); //iphone
//                robot.startTelepresence("嚴","3f7b52cbdcdc3f77ecd0883f68ad097f"); //android
                //---
                //翻譯

//                TranslatorOptions options =
//                        new TranslatorOptions.Builder()
//                                .setSourceLanguage(TranslateLanguage.ENGLISH)
//                                .setTargetLanguage(TranslateLanguage.CHINESE)
//                                .build();
//                final Translator englishGermanTranslator =
//                        Translation.getClient(options);
//                DownloadConditions conditions = new DownloadConditions.Builder()
//                        .requireWifi()
//                        .build();
//                englishGermanTranslator.downloadModelIfNeeded(conditions);
//                englishGermanTranslator.translate(langtrans)
//                        .addOnSuccessListener(
//                                new OnSuccessListener<String>() {
//                                    @Override
//                                    public void onSuccess(@NonNull String translatedText) {
//                                        // Translation successful.
//                                        Log.d("123",translatedText);
//                                    }
//                                })
//                        .addOnFailureListener(
//                                new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        // Error.
//                                        // ...
//                                    }
//                                });
                //-----
                robot.showAppList();
                System.exit(0);
                //台語
//                if(player == null)
//                {
//                    player = new MediaPlayer();
//                }
//                player.reset();
//                try {
//                    player.setDataSource("sdcard/careful.mp3");
//                    player.prepare();
//                    player.start();
//                } catch (Exception ex) {
//
//                }
                //----

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
                robot.goTo("入口");
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



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
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
//        String tmp =s;
////        if (s.equals("home base"))
////            tmp = "充電樁";
        if (sslab==1&&tf==0) {
            TtsRequest temp;
            if (s1.equals("complete")) {
                speak_count = 0;
                firsttime = 0;
                handler.removeCallbacks(runnable);
                try {
                    Thread.sleep(1000);
                    if (player == null) {
                        player = new MediaPlayer();
                    }
                    player.reset();
                    try {
                        player.setDataSource("sdcard/arrive.mp3");
                        player.prepare();
                        player.start();
                    } catch (Exception ex) {

                    }
                } catch (InterruptedException e) {

                }

//                try {
//                    // delay 3.5 second
//                    Thread.sleep(4500);
//                    robot.speak(TtsRequest.create("到達目的地", false));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            } else if (s1.equals("start")) {
                speak_count = 0;
                handler.removeCallbacks(runnable);
                if (player == null) {
                    player = new MediaPlayer();
                }
                player.reset();
                try {
                    player.setDataSource("sdcard/go.mp3");
                    player.prepare();
                    player.start();
                } catch (Exception ex) {

                }
//                try {
//                    // delay 3.5 second
//                    Thread.sleep(3500);
//                    robot.speak(TtsRequest.create("前往目的地", false));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            } else if (s1.equals("going")) {
//            robot.speak(TtsRequest.create("機器人移動中，請小心", false));
                if (player == null) {
                    player = new MediaPlayer();
                }
                player.reset();
                try {
                    player.setDataSource("sdcard/careful.mp3");
                    player.prepare();
                    player.start();
                } catch (Exception ex) {

                }
//                try {
//                    Thread.sleep(5000);
//                    robot.speak(TtsRequest.create("機器人移動中，請小心", false));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                if (firsttime == 0) {
                    firsttime = 1;
                    handler.postDelayed(runnable, 4200);
                }
                speak_count = 1;
            } else if (s1.equals("abort")) {
                firsttime = 0;
                handler.removeCallbacks(runnable);
            }
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
        if (robot.getBatteryData().component1()==15)
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("電池電量將耗盡");
            builder.setMessage("電池電量剩下15%請盡快充電，電量剩下10%時自動前往充電樁");
            builder.setIcon(R.drawable.battery);


            builder.setPositiveButton("立刻前往充電", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    robot.goTo("home base");
                    canceltmp = 1;

                }
            });

            builder.setNegativeButton("稍後再充電", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            if (canceltmp ==1)
            {
                alert.cancel();
                canceltmp = 0;
            }
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
            TtsRequest tmp = TtsRequest.create("電量即將耗盡，前往充電樁",false);
            robot.speak(tmp);
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

    @Override
    public void onTelepresenceEventChanged(@NotNull CallEventModel callEventModel) {
        Log.d("tele3",Integer.toString(callEventModel.component3()));
        Log.d("tele1",callEventModel.component1());
        if (callEventModel.component3()==1)
        {
            Log.d("tele3",Integer.toString(callEventModel.component3()));
        }
    }
}
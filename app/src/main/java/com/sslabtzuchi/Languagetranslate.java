package com.sslabtzuchi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;

import java.io.IOException;

import taobe.tec.jcc.JChineseConvertor;

public class Languagetranslate extends AppCompatActivity {
    Button home,tran,tran2,tts,tts2,tran3,tran4;
    //印尼翻中文
    void INtoZH (String speechtext)
    {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.INDONESIAN)
                        .setTargetLanguage(TranslateLanguage.CHINESE)
                        .build();
        final Translator englishChineseTranslator =
                Translation.getClient(options);
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        englishChineseTranslator.downloadModelIfNeeded(conditions);
        englishChineseTranslator.translate(speechtext)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                Log.d("stt123",translatedText);
                                try {
                                    TextView result = (TextView) findViewById(R.id.LanTranslate2);
                                    JChineseConvertor jChineseConvertor = JChineseConvertor
                                            .getInstance();
                                    translatedText = jChineseConvertor.s2t(translatedText);
                                    Log.d("stt456",translatedText);
//                                    TextView result1 = (TextView)findViewById(R.id.tranlatetext);
//                                    result1.setText(translatedText);
                                    result.setText(translatedText);
                                    //  robot.speak(TtsRequest.create(translatedText,false));

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
    //中文翻印尼
    void ZHtoIN (String speechtext)
    {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.CHINESE)
                        .setTargetLanguage(TranslateLanguage.INDONESIAN)
                        .build();
        final Translator englishChineseTranslator =
                Translation.getClient(options);
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        englishChineseTranslator.downloadModelIfNeeded(conditions);
        englishChineseTranslator.translate(speechtext)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                Log.d("stt123",translatedText);
                                try {
                                    TextView result = (TextView) findViewById(R.id.LanTranslate2);
                                    JChineseConvertor jChineseConvertor = JChineseConvertor
                                            .getInstance();
                                    translatedText = jChineseConvertor.s2t(translatedText);
                                    Log.d("stt456",translatedText);
//                                    TextView result1 = (TextView)findViewById(R.id.tranlatetext);
//                                    result1.setText(translatedText);
                                    result.setText(translatedText);
                                    //  robot.speak(TtsRequest.create(translatedText,false));

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

    //英翻中
    void ENtoZH(String speechtext)
    {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.CHINESE)
                        .build();
        final Translator englishChineseTranslator =
                Translation.getClient(options);
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        englishChineseTranslator.downloadModelIfNeeded(conditions);
        englishChineseTranslator.translate(speechtext)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                Log.d("stt123",translatedText);
                                try {
                                    TextView result = (TextView) findViewById(R.id.LanTranslate2);
                                    JChineseConvertor jChineseConvertor = JChineseConvertor
                                            .getInstance();
                                    translatedText = jChineseConvertor.s2t(translatedText);
                                    Log.d("stt456",translatedText);
//                                    TextView result1 = (TextView)findViewById(R.id.tranlatetext);
//                                    result1.setText(translatedText);
                                    result.setText(translatedText);
                                  //  robot.speak(TtsRequest.create(translatedText,false));

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

    //中翻英
    void ZHtoEN(String speechtext)
    {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.CHINESE)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();
        final Translator chineseEnglishTranslator =
                Translation.getClient(options);
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        chineseEnglishTranslator.downloadModelIfNeeded(conditions);
        chineseEnglishTranslator.translate(speechtext)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                // Translation successful.
                                TextView result2 = (TextView) findViewById(R.id.LanTranslate2);
                                Log.d("stt123",translatedText);
//                                TextView result2 = (TextView)findViewById(R.id.TextView);
//                                result2.setText(translatedText);
                                result2.setText(translatedText);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languagetranslate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        home = (Button) findViewById(R.id.home);
        tran = (Button) findViewById(R.id.tran);
        tran2 = (Button) findViewById(R.id.tran2);
        tran3 = (Button) findViewById(R.id.tran3);
        tran4 = (Button) findViewById(R.id.tran4);
        tts = (Button) findViewById(R.id.tts);
        tts2 = (Button) findViewById(R.id.tts2);
        //回主頁面
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Languagetranslate.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //中翻英
        tran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText data = (EditText) findViewById(R.id.LanTranslate1);
                ZHtoEN(data.getText().toString());
            }
        });
        ///英翻中
        tran2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText data = (EditText) findViewById(R.id.LanTranslate1);
                ENtoZH(data.getText().toString());
            }
        });
        //中翻印尼
        tran3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText data = (EditText) findViewById(R.id.LanTranslate1);
                ZHtoIN(data.getText().toString());
            }
        });
        //印尼翻中文
        tran4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText data = (EditText) findViewById(R.id.LanTranslate1);
                INtoZH(data.getText().toString());
            }
        });

        //
        tts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText data = (EditText) findViewById(R.id.LanTranslate1);
                Robot.getInstance().speak(TtsRequest.create(data.getText().toString(),false));
            }
        });
        tts2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView resultText = (TextView) findViewById(R.id.LanTranslate2);
                Robot.getInstance().speak(TtsRequest.create(resultText.getText().toString(),false));
            }
        });
    }
}
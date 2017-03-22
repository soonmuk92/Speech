package com.example.a510.speech;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    protected Button btSpeech;
    protected TextView txSpeech;
    private static final int SPEECH_CODE = 1234;//private=변수 접근 불가능(public=외부 접근 가능), static=메모리를 한번만 할당, final=상수
    //protected=외부접근 가능(상속을 받았을 시), 외부접근 불가능(상속 외)
    protected TextToSpeech tts;

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.KOREAN);
            tts.setPitch(0.6f);
            tts.setSpeechRate(1f);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String strSpeech = arrayList.get(0);
                txSpeech.setText(strSpeech);
                tts.speak(strSpeech, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btSpeech = (Button) findViewById(R.id.btSpeech);
        txSpeech = (TextView) findViewById(R.id.txSpeech);

        btSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);//음성을 인식한 대로 움직임

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);//음성인식의 의미를 웹에서 분석하여 움직임

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);

                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Recognizeing...");
                startActivityForResult(intent, SPEECH_CODE);
            }
        });
        tts = new TextToSpeech(this, this);
    }
}
package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);

        inicializarSeekBar();
    }

    private void inicializarSeekBar(){
        seekVolume = findViewById(R.id.seekVolume);

        //configurar o audio manager
        //referencias: https://developer.android.com/reference/android/media/AudioManager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);  //recupera serviços do sistema

        //recupera os valores de volume maximo e o volume atual

        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //configurar o valor máximo para o seekbar
        seekVolume.setMax(volumeMaximo);

        //configurar o volume atual para o seekbar
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,AudioManager.FLAG_SHOW_UI); // FLAG = 0, não exibe nada
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//
            }
        });

    }


    public void executarSom(View view){

        if (mediaPlayer!= null) {

            mediaPlayer.start();
        }


    }

    public void pausarSom(View view){

        if (mediaPlayer.isPlaying()) {

            mediaPlayer.pause();
        }

    }

    public void pararSom(View view){

        if (mediaPlayer.isPlaying()) {

            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);

        }

    }

    protected void onDestroy(){
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release(); // libera recursos de media que estejam executando, removendo da memoria
            mediaPlayer = null;
        }

    }


    @Override //utilizando metodos de ciclo de vida de um activity
    protected void onStop(){
        super.onStop();

        if (mediaPlayer.isPlaying()) {

            mediaPlayer.pause();
        }
    }



}

package com.example.pac_uf2_garridotrilloroberto;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;


public class Servicio_musica extends Service {
    // campos de clase
    MediaPlayer musica = null;


    @Override
    public void onCreate() {
        super.onCreate();
        musica = MediaPlayer.create(this, R.raw.ryanjones);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //musica.prepare();
        musica.start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (musica.isPlaying()) musica.stop();
        musica.release();
        musica = null;
    }


}


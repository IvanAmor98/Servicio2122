package com.example.servicio2122;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button arrancar = (Button) findViewById(R.id.boton_arrancar);
        arrancar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Crea el servicio
                Intent intent = new Intent(MainActivity.this,
                        ServicioMusica.class);
                intent.setAction("start");
                startService(intent);
            }
        });

        Button detener = (Button) findViewById(R.id.boton_detener);
        //Detiene el servicio
        detener.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this,
                        ServicioMusica.class));
            }
        });

        //Creo intent para iniciar el servicio
        Intent intentStart = new Intent(this, ServicioMusica.class);
        //Añado accion
        intentStart.setAction("start");
        PendingIntent pendingIntentStart = PendingIntent.getService(this, 0, intentStart, 0);

        //Creo intent para parar el servicio
        Intent intentStop = new Intent(this, ServicioMusica.class);
        //Añado accion
        intentStop.setAction("stop");
        PendingIntent pendingIntentStop = PendingIntent.getService(this, 0, intentStop, 0);

        //Creo la notificacion
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Channel1")
                .setSmallIcon(R.drawable.service_notificatuion)
                .setContentTitle("Reproduccion")
                .setContentText("Servicio de reproduccion en ejecucion")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //Añado dos acciones (botones) cada uno con su intent
                .addAction(R.drawable.start_notificatuion, "Start", pendingIntentStart) // #0
                .addAction(R.drawable.pause_notificatuion, "Stop", pendingIntentStop);  // #1

        //Inicio la notificacion
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1000, builder.build());
    }
}
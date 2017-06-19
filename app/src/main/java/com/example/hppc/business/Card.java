package com.example.hppc.business;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Card extends AppCompatActivity {


    String url = "tel:0338200578";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        ImageButton btn_search = (ImageButton) findViewById(R.id.imageButton2);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Card.this, Search.class);

                startActivity(i);
            }
        });


        ImageButton btn_camera = (ImageButton) findViewById(R.id.imageButton);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final int REQUEST_IMAGE_CAPTURE = 1;
                capturePhoto(REQUEST_IMAGE_CAPTURE);
            }
        });


        ImageButton btn_mail=(ImageButton) findViewById(R.id.btn_mail);
        ImageButton btn_map=(ImageButton) findViewById(R.id.btn_map);
        final ImageButton btn_call=(ImageButton) findViewById(R.id.btn_call);
        btn_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

                startActivity(Intent.createChooser(intent, "Send Email"));

            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                if ( ContextCompat.checkSelfPermission (Card.this,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED)
                {
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Card.this,Map_Activity.class);
                //finish();  //Kill the activity from which you will go to next activity
                startActivity(i);


            }
        });






    }








    public void capturePhoto(int REQUEST_IMAGE_CAPTURE) {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        //System.out.println("HIMANSHU");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
        }
    }




}

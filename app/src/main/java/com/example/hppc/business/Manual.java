package com.example.hppc.business;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import static com.example.hppc.business.R.id.add;
import static com.example.hppc.business.R.id.imgView;

public class Manual extends AppCompatActivity {


    private Button upload;
    String username;
    private int RESULT_LOAD_IMAGE =1;
    private int SELECT_PICTURE=1;
    private String userChoosenTask;
    private static final String URL = "http://192.168.11.14/test/Insert.php";
    private int SELECT_FILE;
    private int REQUEST_CAMERA;
    private ImageView imgView;
    private Button btn_save;
    private EditText name;
    private EditText company_name;
    private EditText position;
    private EditText department;
    private EditText address;
    private EditText email;
    private EditText Url;
    private EditText Telephone1;
    private EditText Telephone2;
    private EditText Mobile;
    private EditText Fax;
    private EditText date;
    private RequestQueue requestQueue;
    private StringRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        upload=(Button)findViewById(R.id.button3);
        btn_save=(Button) findViewById(R.id.button);
        name=(EditText) findViewById(R.id.editText);
        company_name=(EditText) findViewById(R.id.editText2);
        department=(EditText) findViewById(R.id.editText4);
        Telephone1=(EditText) findViewById(R.id.editText5);
        Mobile=(EditText) findViewById(R.id.editText6);
        email=(EditText) findViewById(R.id.editText7);
        Url=(EditText) findViewById(R.id.editText8);
        position=(EditText) findViewById(R.id.editText9);
        date=(EditText) findViewById(R.id.editText10);
        address=(EditText) findViewById(R.id.editText11);
        username = getIntent().getStringExtra("USERNAME");



        upload.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                selectImage();
            }

        });

        imgView=(ImageView) findViewById(R.id.imgView);
        requestQueue = Volley.newRequestQueue(this);

        btn_save.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Log.e("Himanshu","Tolani");

                Toast.makeText(getApplicationContext(),"Himanshu",Toast.LENGTH_SHORT).show();
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(),"Record Added",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Front_activity.class));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        VolleyLog.e("VOLLEY", error.getMessage());
                        Toast.makeText(getApplicationContext(),"YESssd",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("employee_id",username);
                        hashMap.put("name",name.getText().toString());
                        hashMap.put("company_name",company_name.getText().toString());
                        hashMap.put("address",address.getText().toString());
                        hashMap.put("telephone1",Telephone1.getText().toString());
                        hashMap.put("telephone2","9646456964");
                        hashMap.put("mobile",Mobile.getText().toString());
                        hashMap.put("Fax","9666464646544");
                        hashMap.put("department",department.getText().toString());
                        hashMap.put("position",position.getText().toString());
                        hashMap.put("date",date.getText().toString());
                        hashMap.put("URL",Url.getText().toString());
                        hashMap.put("email",email.getText().toString());

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }

        });


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }



    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Manual.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Manual.this);
                if (items[item].equals("Take Photo"))
                {
                    userChoosenTask="Take Photo";
                    if(result) {
                        REQUEST_CAMERA = 1;

                    }       cameraIntent();
                }
                else if (items[item].equals("Choose from Library")) {
                        userChoosenTask = "Choose from Library";
                        if (result) {
                            SELECT_FILE = 1;
                            galleryIntent();
                        }
                    }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imgView.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        //Log.e("Tolani","Tolani");
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        //Log.e("Himanshu","Himanshu");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),"PLEASE",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),"YOYO",Toast.LENGTH_SHORT).show();
        }
        imgView.setImageBitmap(thumbnail);
    }





}

package com.esprit.intro.miniprojet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Inscriptionmedecin extends AppCompatActivity {
    EditText nom,prenom,info,tel,login,password;
    ImageView img;
    int codeactivityrequest=999;
    Bitmap bitmap;
    boolean loginOk,passwordOk,nomOk,prenomOk,telOk,specialiteOk;
    boolean diplomeChoisie;
    boolean containsDigit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscritmedecin2);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        nom=(EditText)findViewById(R.id.nommedecin);
        prenom=(EditText)findViewById(R.id.prenommedecin);
        info=(EditText)findViewById(R.id.infomedecin);
        tel=(EditText)findViewById(R.id.numtelmedecin);
        login=(EditText)findViewById(R.id.loginmedecin);
        password=(EditText)findViewById(R.id.passwordmedecin);
        Button ajouter= (Button) findViewById(R.id.ajoutermedecin);
        img=(ImageView)findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Inscriptionmedecin.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},codeactivityrequest);
            }
        });
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomtxt=nom.getText().toString().replace(" ","%20");
                String prenomtxt=prenom.getText().toString().replace(" ","%20");
                String infotxt=info.getText().toString().replace(" ","%20");
                String teltxt=tel.getText().toString().replace(" ","%20");
                String logintxt=login.getText().toString().replace(" ","%20");
                String paswordtxt=password.getText().toString().replace(" ","%20");

                if (diplomeChoisie==false){
                    Toast.makeText(getApplicationContext(),"veuillez choisir une photo de diplome",Toast.LENGTH_LONG).show();
                }

                if (info.getText().toString().equals("")) {
                    info.setError("enter la specialité");
                    info.requestFocus();
                    specialiteOk = false;
                } else if (containsDigit(info.getText().toString()) == true) {
                    info.setError("la specialité ne doit pas contenir de chiffres");
                    info.requestFocus();
                    specialiteOk = false;
                } else {
                    specialiteOk = true;
                }

                if (tel.getText().toString().equals("")) {
                    tel.setError("enter telephone de cabinet");
                    tel.requestFocus();
                    telOk = false;
                } else if (!android.text.TextUtils.isDigitsOnly(tel.getText().toString())) {
                    tel.setError("telephone doit contenir que des chiffres");
                    tel.requestFocus();
                    telOk = false;
                } else {
                    telOk = true;
                }

                if (prenom.getText().toString().equals("")){
                    prenom.setError("enter votre prenom");
                    prenom.requestFocus();
                    prenomOk=false;
                }
                else if(containsDigit(prenom.getText().toString())==true){
                    prenom.setError("nom ne doit pas contenir de chiffres");
                    prenom.requestFocus();
                    prenomOk=false;
                }
                else{
                    prenomOk=true;
                }

                if (nom.getText().toString().equals("")){
                    nom.setError("enter votre nom");
                    nom.requestFocus();
                    nomOk=false;
                }
                else if(containsDigit(nom.getText().toString())==true){
                    nom.setError("nom ne doit pas contenir de chiffres");
                    nom.requestFocus();
                    nomOk=false;
                }
                else{
                    nomOk=true;
                }

                if (password.getText().toString().equals("")){
                    password.setError("enter votre mot de passe");
                    password.requestFocus();
                    passwordOk=false;
                }else{
                    passwordOk=true;
                }

                if (login.getText().toString().equals("")){
                    login.setError("enter votre login");
                    login.requestFocus();
                    loginOk=false;
                }else{
                    System.out.println("http://"+ActivityLogin.ip+"/medichelper/LoginVerif.php?login="+login.getText().toString());
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ActivityLogin.ip+"/medichelper/LoginVerif.php?login="+login.getText().toString(), new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonarray = null;
                            System.out.println(response);
                            try {
                                jsonarray = new JSONArray(response);

                                if (jsonarray.length()==0) {
                                    loginOk=true;
                                }
                                else{
                                    login.setError("login existe deja");
                                    login.requestFocus();
                                    loginOk=false;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("That didn't work!");
                            System.out.println(error.toString());
                        }
                    });

                    queue.add(stringRequest);

                    //loginOk=true;
                }


                if(diplomeChoisie&loginOk&passwordOk&nomOk&prenomOk&telOk&specialiteOk ) {


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ActivityLogin.ip + "/medichelper/ajoutermedecin.php?login=" + logintxt + "&password=" + paswordtxt + "&nom=" + nomtxt + "&numtel=" + teltxt + "&prenom=" + prenomtxt + "&info=" + infotxt + "", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println("it works");
                            Toast.makeText(getApplicationContext(), "Vous êtes bien inscrit",
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Inscriptionmedecin.this, ActivityLogin.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "ereur:" + error.toString(), Toast.LENGTH_LONG).show();
                            System.out.print(error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<>();
                            String imageData = imageToString(bitmap);
                            params.put("image", imageData);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Inscriptionmedecin.this);
                    requestQueue.add(stringRequest);

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==codeactivityrequest) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent inent = new Intent(Intent.ACTION_PICK);
                inent.setType("image/*");
                startActivityForResult(Intent.createChooser(inent, "select Image"), codeactivityrequest);

            }
            else{

                Toast.makeText(getApplicationContext(),"you don't have permission",Toast.LENGTH_LONG).show();

            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        diplomeChoisie=true;
        if(requestCode==codeactivityrequest&&resultCode==RESULT_OK&&data!=null) {
            Uri filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap=BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String imageToString(Bitmap bitmap){

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes=outputStream.toByteArray();
        String encodedimage=Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedimage;
    }


    public final boolean containsDigit(String s) {
        containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }



    }


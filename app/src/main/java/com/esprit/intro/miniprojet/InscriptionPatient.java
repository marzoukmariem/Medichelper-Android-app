package com.esprit.intro.miniprojet;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class InscriptionPatient extends AppCompatActivity {
    Button ajouter,upload;
    CircleImageView img;
    int codeactivityrequest=999;
    EditText login,nom,prenom,password,tel,cin,role,adresse;
    boolean loginOk,nomOk,prenomOk,passwordOk,telOk,cinOk,adresseOk,dateOk;
    boolean containsDigit;
    boolean pictureChosen;
    //String urlupload="http://192.168.1.16/upload.php";
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final    Calendar myCalendar = Calendar.getInstance();
        setContentView(R.layout.inscritpatient2);
        ajouter = (Button) findViewById(R.id.ajouter);
        //upload = (Button) findViewById(R.id.uploadimage);
        img=(CircleImageView)findViewById(R.id.imageView);
        login=(EditText)findViewById(R.id.login);
        nom=(EditText)findViewById(R.id.nom);
        prenom=(EditText)findViewById(R.id.prenommedecin);
        password=(EditText)findViewById(R.id.password);
        tel=(EditText)findViewById(R.id.numtel);
        adresse=(EditText)findViewById(R.id.adresse);
        cin=(EditText)findViewById(R.id.cin);
        final ImageView datebtn=(ImageView)findViewById(R.id.datebtn);
        final   EditText edittext= (EditText) findViewById(R.id.editText3);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            private void updateLabel() {
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateOk=true;
                edittext.setText(sdf.format(myCalendar.getTime()));
                //  String date1=edittext.getText().toString();
              /*
                try {
                  //  Date d1 = sdf.parse(date1);
                  //  Date d2=Calendar.getInstance().getTime();

                 //   long difference = Math.abs(d2.getTime()-d1.getTime());
                 //   long differenceDates = difference / (24 * 60 * 60 * 1000);
                    // edittext1.setText(Long.toString(differenceDates/365));
                  //  age1=Long.toString(differenceDates/365);

                }catch  (Exception exception) {
                    Log.e("DIDN'T WORK", "exception " + exception);
                }

*/


            }
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        datebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(InscriptionPatient.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(InscriptionPatient.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},codeactivityrequest);
            }
        });

//upload  image to server
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String logintxt=login.getText().toString().replace(" ","%20");
                final String passwordtxt=password.getText().toString().replace(" ","%20");
                final String nomtxt=nom.getText().toString().replace(" ","%20");
                final String prenomtxt=prenom.getText().toString().replace(" ","%20");
                final String cintxt=cin.getText().toString().replace(" ","%20");
                final String numteltxt=tel.getText().toString().replace(" ","%20");
                final String adressetxt=adresse.getText().toString().replace(" ","%20");

                if (pictureChosen==false){
                    Toast.makeText(getApplicationContext(),"veuillez choisir une photo",Toast.LENGTH_LONG).show();
                }

                if (edittext.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"veuillez choisir une date",Toast.LENGTH_LONG).show();
                }

                if (cin.getText().toString().equals("")){
                    cin.setError("enter votre prefession ");
                    cin.requestFocus();
                    cinOk=false;
                }else{
                    cinOk=true;
                }


                if (password.getText().toString().equals("")){
                    password.setError("enter votre mot de passe ");
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

                if (adresse.getText().toString().equals("")){
                    adresse.setError("enter votre adresse");
                    adresse.requestFocus();
                    adresseOk=false;
                }else{
                    adresseOk=true;
                }

                if (tel.getText().toString().equals("")){
                    tel.setError("enter votre numero");
                    tel.requestFocus();
                    telOk=false;
                }
                else if (!android.text.TextUtils.isDigitsOnly(tel.getText().toString())){
                    tel.setError("le numero doit contenir que des chiffres");
                    tel.requestFocus();
                    telOk=false;
                }
                else{
                    telOk=true;
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



                if (dateOk & cinOk & loginOk & passwordOk & adresseOk & telOk & nomOk & prenomOk & pictureChosen) {


                    final String datetxt = edittext.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ActivityLogin.ip + "/medichelper/upload.php?datenaiss=" + datetxt + "&login=" + logintxt + "&password=" + passwordtxt + "&nom=" + nomtxt + "&numtel=" + numteltxt + "&prenom=" + prenomtxt + "&adresse=" + adressetxt + "&cin=" + cintxt + "", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Vous êtes bien inscrit",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(InscriptionPatient.this, ActivityLogin.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

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

                    RequestQueue requestQueue = Volley.newRequestQueue(InscriptionPatient.this);
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
        pictureChosen=true;
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


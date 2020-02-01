package com.esprit.intro.miniprojet;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.picasso.Picasso;

public class ActivityUpdateProfil__PatientActivity extends AppCompatActivity {
    int codeactivityrequest=999;
    Bitmap bitmap;
    CircleImageView img2;
    EditText login,nom,prenom,password,tel,cin,role,adresse,dateNaissance;
    Button update;

    boolean loginOk,nomOk,prenomOk,passwordOk,telOk,cinOk,adresseOk,dateOk;
    boolean containsDigit;

    String lastLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil___patient);

        update= (Button)findViewById(R.id.btnEditOK);
        login=(EditText)findViewById(R.id.etEditLogin);
        nom=(EditText)findViewById(R.id.etEditNom);
        prenom=(EditText)findViewById(R.id.etEditPrenom);
        password=(EditText)findViewById(R.id.etEditPassword);
        tel=(EditText)findViewById(R.id.etEditNumero);
        adresse=(EditText)findViewById(R.id.etEditAddress);
        cin=(EditText)findViewById(R.id.etEditCin);
        dateNaissance=(EditText) findViewById(R.id.etEditdateDeNaissante) ;

        img2=(CircleImageView)findViewById(R.id.userconnecteModif);
        final RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/getInnfopatient.php?idpt=" + ActivityLogin.patientConnecté.getId(),
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonarray = null;
                        System.out.println(response);
                        try {
                            jsonarray = new JSONArray(response);

                            if (jsonarray.length() == 0) {
                                Toast.makeText(getApplicationContext(), "vide!", Toast.LENGTH_LONG).show();
                            } else {

                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                     String urlimg=jsonobject.getString("UrlImage_Patient");
                                    Picasso.with(getApplicationContext()).load("http://"+ActivityLogin.ip+"/medichelper/"+urlimg).into(img2);
                                    tel.setText(jsonobject.getString("NumTel_User"));
                                   nom.setText(jsonobject.getString("Nom_User"));
                                   prenom.setText(jsonobject.getString("Prenom_User"));
                                   cin.setText(jsonobject.getString("Cin_Patient"));
                                   adresse.setText(jsonobject.getString("Adresse_Patient"));
                                    dateNaissance.setText(jsonobject.getString("DateNaissance_User").substring(0,10));
                                    login.setText(jsonobject.getString("Login_User"));
                                    lastLogin=jsonobject.getString("Login_User");
                                    password.setText(jsonobject.getString("Password_User"));


                                }


                            }  } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("That didn't work!");

            }

        });

        queue.add(stringRequest);



        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ActivityUpdateProfil__PatientActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},codeactivityrequest);
            }
        });
        final ImageView datebtn=(ImageView)findViewById(R.id.datebtn);
        final    Calendar myCalendar = Calendar.getInstance();
        final EditText edittext= (EditText) findViewById(R.id.etEditdateDeNaissante);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            private void updateLabel() {
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateOk=true;
                edittext.setText(sdf.format(myCalendar.getTime()));


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

                new DatePickerDialog(ActivityUpdateProfil__PatientActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nom1 = nom.getText().toString().replace(" ", "%20");
                String prenom1 = prenom.getText().toString().replace(" ", "%20");
                String address1 = adresse.getText().toString().replace(" ", "%20");
                String login1 = login.getText().toString().replace(" ", "%20");
                String pw = password.getText().toString().replace(" ", "%20");
                String cin1 = cin.getText().toString().replace(" ", "%20");
                String tel1 = tel.getText().toString().replace(" ", "%20");


                if (edittext.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "veuillez choisir une date", Toast.LENGTH_LONG).show();
                }

                if (cin.getText().toString().equals("")) {
                    cin.setError("enter votre prefession ");
                    cin.requestFocus();
                    cinOk = false;
                } else {
                    cinOk = true;
                }


                if (password.getText().toString().equals("")) {
                    password.setError("enter votre mot de passe ");
                    password.requestFocus();
                    passwordOk = false;
                } else {
                    passwordOk = true;
                }


                if (login.getText().toString().equals("")) {
                    login.setError("enter votre login");
                    login.requestFocus();
                    loginOk = false;
                }
                else if(login.getText().toString().equals(lastLogin)){
                    loginOk=true;
                }
                else {
                    System.out.println("http://" + ActivityLogin.ip + "/medichelper/LoginVerif.php?login=" + login.getText().toString());
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/LoginVerif.php?login=" + login.getText().toString(), new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonarray = null;
                            System.out.println(response);
                            try {
                                jsonarray = new JSONArray(response);

                                if (jsonarray.length() == 0) {
                                    loginOk = true;
                                } else {
                                    login.setError("login existe deja");
                                    login.requestFocus();
                                    loginOk = false;
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

                if (adresse.getText().toString().equals("")) {
                    adresse.setError("enter votre adresse");
                    adresse.requestFocus();
                    adresseOk = false;
                } else {
                    adresseOk = true;
                }

                if (tel.getText().toString().equals("")) {
                    tel.setError("enter votre numero");
                    tel.requestFocus();
                    telOk = false;
                } else if (!android.text.TextUtils.isDigitsOnly(tel.getText().toString())) {
                    tel.setError("le numero doit contenir que des chiffres");
                    tel.requestFocus();
                    telOk = false;
                } else {
                    telOk = true;
                }

                if (prenom.getText().toString().equals("")) {
                    prenom.setError("enter votre prenom");
                    prenom.requestFocus();
                    prenomOk = false;
                } else if (containsDigit(prenom.getText().toString()) == true) {
                    prenom.setError("nom ne doit pas contenir de chiffres");
                    prenom.requestFocus();
                    prenomOk = false;
                } else {
                    prenomOk = true;
                }

                if (nom.getText().toString().equals("")) {
                    nom.setError("enter votre nom");
                    nom.requestFocus();
                    nomOk = false;
                } else if (containsDigit(nom.getText().toString()) == true) {
                    nom.setError("nom ne doit pas contenir de chiffres");
                    nom.requestFocus();
                    nomOk = false;
                } else {
                    nomOk = true;
                }


                if ( cinOk & loginOk & passwordOk & adresseOk & telOk & nomOk & prenomOk ) {


                    final String datetxt = edittext.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ActivityLogin.ip + "/medichelper/updatePatient.php?Id_User=" + ActivityLogin.patientConnecté.getId() + "&nom=" + nom1 + "&prenom=" + prenom1 + "&numtel=" + tel1 + "&login=" + login1 + "&pw=" + pw + "&login=" + login1 + "&cin=" + cin1 + "&addresse=" + address1 + "&datenaiss=" + datetxt, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.print(response.toString());
                            Toast.makeText(getApplicationContext(), "Informtions ont été bien mis à jours", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ActivityUpdateProfil__PatientActivity.this, parametreprofil.class));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "erreur",
                                    Toast.LENGTH_LONG).show();
                            System.out.println("That didn't work!");


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
                    RequestQueue requestQueue = Volley.newRequestQueue(ActivityUpdateProfil__PatientActivity.this);
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

        if(requestCode==codeactivityrequest&&resultCode==RESULT_OK&&data!=null) {
            Uri filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap=BitmapFactory.decodeStream(inputStream);
                img2.setImageBitmap(bitmap);
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

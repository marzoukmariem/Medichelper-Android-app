package com.esprit.intro.miniprojet;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.intro.miniprojet.ChatData;
import com.esprit.intro.miniprojet.ConversationRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Conversation extends BaseActivity {
    private ArrayList<String> text1=new ArrayList<>();
    private ArrayList<String> type1=new ArrayList<>();
    private ArrayList<String> time1=new ArrayList<>();
    final ArrayList<String> intTest =new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ConversationRecyclerView mAdapter;
    private EditText text;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        final Intent intent = getIntent();

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        getSupportActionBar().setTitle(intent.getStringExtra("Nomcab"));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ActivityLogin.ip+"/medichelper/getallMsgsOfCOnnectedPatientWithHisDoctor.php?Userid="+ActivityLogin.patientConnecté.getId()+"&idcabinet="+ProfilPatient_PatientActivity.idCabinet,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonarray = null;
                        System.out.println(response);
                        try {
                            jsonarray = new JSONArray(response);

                            if (jsonarray.length()==0) {
                                System.out.println("erreurJsonVide");

                                //setupToolbarWithUpNav(R.id.toolbar,ProfilPatient_PatientActivity.nomCabinet , R.drawable.ic_action_back);

                                //Nomcab


                                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                                mRecyclerView.setHasFixedSize(true);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                mAdapter = new ConversationRecyclerView(getApplicationContext(),setData());
                                mRecyclerView.setAdapter(mAdapter);
                                mRecyclerView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount());
                                    }
                                }, 1000);
                            }
                            else{

                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String idUser = jsonobject.getString("FK_User_ID");
                                    String msg = jsonobject.getString("detail_Msg");
                                    System.out.println("msg="+msg);
                                    System.out.println("idUser="+idUser);
                                    text1.add(msg);
                                    if (idUser.equals(ActivityLogin.patientConnecté.getId().toString())){
                                        type1.add("2");
                                    }else{
                                        type1.add("1");
                                    }

                                    time1.add("");

                                }
                                List<ChatData> data = new ArrayList<>();
                                String text[] = {"waaaaa", "waaaaaaa"};
                                String time[] = {"", ""};
                                String type[] = { "2", "2"};

                                for (int i=0; i<text.length; i++){
                                    ChatData item = new ChatData();
                                    item.setType(type[i]);
                                    item.setText(text[i]);
                                    item.setTime(time[i]);

                                    data.add(item);
                                }

                                //setupToolbarWithUpNav(R.id.toolbar,ProfilPatient_PatientActivity.nomCabinet , R.drawable.home2);
                                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                                mRecyclerView.setHasFixedSize(true);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                mAdapter = new ConversationRecyclerView(getApplicationContext(),setData());
                                mRecyclerView.setAdapter(mAdapter);
                                mRecyclerView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                                    }
                                }, 1000);
                            }




                        } catch (JSONException e) {
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




        text = (EditText) findViewById(R.id.et_message);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                    }
                }, 500);
            }
        });
        send = (Button) findViewById(R.id.bt_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!text.getText().toString().equals("")){

                    System.out.println("http://" + ActivityLogin.ip + "/medichelper/ajoutermsg2.php?detail_Msg="+text.getText().toString().replace(" ","%20")+"&FK_User_ID="+ActivityLogin.patientConnecté.getId()+"&idcabinet="+ProfilPatient_PatientActivity.idCabinet);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ActivityLogin.ip + "/medichelper/ajoutermsg2.php?detail_Msg="+text.getText().toString().replace(" ","%20")+"&FK_User_ID="+ActivityLogin.patientConnecté.getId()+"&idcabinet="+ProfilPatient_PatientActivity.idCabinet,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    System.out.println("it works");
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("That didn't work!");
                        }
                    });

                    queue.add(stringRequest);
                    List<ChatData> data = new ArrayList<ChatData>();
                    ChatData item = new ChatData();
                    item.setType("2");
                    item.setTime("");
                    item.setText(text.getText().toString());
                    data.add(item);
                    mAdapter.addItem(data);
                    mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() -1);
                    text.setText("");
                }
            }
        });
    }

    public List<ChatData> setData(){
        List<ChatData> data = new ArrayList<>();


        data = new ArrayList<>();
        String text[] = { "waaaaaaa"};
        String time[] = {""};
        String type[] = { "2"};

        for (int i=0; i<text1.size(); i++){
            ChatData item = new ChatData();
            item.setType(type1.get(i));
            item.setText(text1.get(i));
            item.setTime(time1.get(i));

            data.add(item);
        }
        System.out.println("wa2");
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_userphoto, menu);
        return false;
    }
}

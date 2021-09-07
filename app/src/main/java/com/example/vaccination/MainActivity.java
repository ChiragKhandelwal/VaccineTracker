package com.example.vaccination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.VoiceInteractor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etSearch;
    Button btnSearch;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<CentreModel> centreModels;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etSearch=findViewById(R.id.etPincode);
        btnSearch=findViewById(R.id.btnsearch);
        recyclerView=findViewById(R.id.RecyclerView);
        progressBar=findViewById(R.id.progress);
        centreModels=new ArrayList<>();


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String pin=etSearch.getText().toString();
                if(pin.length()!=6) Toast.makeText(MainActivity.this,"Enter A Valid Pincode",Toast.LENGTH_SHORT).show();
                else{
                    centreModels.clear();
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                   int mMonth = c.get(Calendar.MONTH);
                   int mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                progressBar.setVisibility(View.VISIBLE);
                                String date=year+"-"+monthOfYear+"-"+dayOfMonth;
                                getAppointment(pin,date);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                    }

    }
});
    }
    public void getAppointment(String pinCode,String date){
        String url="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=" + pinCode + "&date=" + date;
        RequestQueue queue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.INVISIBLE);
                try{
                    JSONArray jsonArray=response.getJSONArray("centers");
                    if(jsonArray.length()==0) Toast.makeText(MainActivity.this,"No vaccination Centres available",Toast.LENGTH_SHORT).show();
                    else {
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String name=object.getString("name");
                            String address=object.getString("address");
                            String startTime=object.getString("from");
                            String endTime=object.getString("to");
                            String fee_type=object.getString("fee_type");
                            JSONArray sessionArray=object.getJSONArray("session");
                            JSONObject sessionObject=sessionArray.getJSONObject(0);
                            int remaining=sessionObject.getInt("available_capacity");
                            int min_age=sessionObject.getInt("minimum_age_limit");
                            String vaccine_name=sessionObject.getString("vaccine");
                            CentreModel model=new CentreModel(name,address,startTime,endTime,vaccine_name,fee_type,min_age,remaining);
                            centreModels.add(model);
                        }
                        adapter=new Adapter(centreModels);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(adapter);
                    }
                }
                catch (Exception e){}
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }
}
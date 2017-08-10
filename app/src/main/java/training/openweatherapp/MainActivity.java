package training.openweatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String formattedDate;
    long day;
    double max;
    double min;
    Button goButton;
    EditText city;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TempAdapter adapter;
    private List<Temps> tempsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goButton = (Button) findViewById(R.id.ButtView);
        city = (EditText) findViewById(R.id.EdtxView);
        recyclerView = (RecyclerView) findViewById(R.id.rvItem);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempsData = new ArrayList<>();

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                String cityName = city.getText().toString();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + cityName + "&units=metric&cnt=16&APPID=e6d4e5c203ecacfc9be32d77f26126b7", new JSONObject(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Volley:Response ", "" + response.toString());
                        JSONObject jsonResponse;
                        try {
                            /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                            jsonResponse = new JSONObject(String.valueOf(response));
                            JSONArray list = jsonResponse.getJSONArray("list");
                            int lengthJsonArr = list.length();

                            for (int i = 0; i < lengthJsonArr; i++) {
                                /****** Get Object for each JSON node.***********/
                                JSONObject firstIndex = list.getJSONObject(i);
                                day = Long.parseLong(firstIndex.optString("dt"));
                                JSONObject temp = firstIndex.getJSONObject("temp");
                                max = temp.getDouble("max");
                                min = temp.getDouble("min");
                                //convert unix time to dd-MM-yyyy format.
                                Date date = new Date(day * 1000L); // *1000 is to convert seconds to milliseconds
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // the format of your date
                                formattedDate = sdf.format(date);
                                Log.i("JSON parse", String.valueOf(formattedDate));
                                Log.i("JSON parse", String.valueOf(day));
                                Log.i("JSON parse", String.valueOf(max));
                                Log.i("JSON parse", String.valueOf(min));
                                Temps myTemp = new Temps(formattedDate, String.valueOf(max), String.valueOf(min));
                                tempsData.add(myTemp);
                                mLayoutManager = new LinearLayoutManager(MainActivity.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                adapter = new TempAdapter(MainActivity.this, tempsData);
                                recyclerView.setAdapter(adapter);

                            }


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        // Error callback
                        Log.e("Error", e.toString());
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("charset", "utf-8");
                        params.put("charset", "utf-8");
                        params.put("Accept", "application/json");
                        return params;
                    }
                };

                requestQueue.add(jsonObjectRequest);


            }


        });

    }
}
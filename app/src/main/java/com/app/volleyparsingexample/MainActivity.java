package com.app.volleyparsingexample;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.volleyparsingexample.utils.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_EMPLOYEE_ID = "employee_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DOB = "dob";
    private static final String KEY_DESIGNATION = "designation";
    private static final String KEY_CONTACT_NUMBER = "contact_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SALARY = "salary";
    private static final String NEW_LINE = "\n\n";
    private TextView mTxtDisplay;
    private ProgressDialog pDialog;
    private Button buttonObj, buttonArray, btnPostformData, btnPostObj;
    private String obj_url = "http://api.androiddeft.com/volley/json_object.json";
    private String array_url = "http://api.androiddeft.com/volley/json_array.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        mTxtDisplay = (TextView) findViewById(R.id.txtDisplay);
        buttonObj = (Button) findViewById(R.id.btnObj);
        buttonArray = (Button) findViewById(R.id.btnArray);
        btnPostformData = findViewById(R.id.btnPostformData);
        btnPostObj = findViewById(R.id.btnPostObj);
        buttonObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLoader();
                loadJsonObj();
            }
        });
        buttonArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLoader();
                loadJsonArray();
            }
        });
        btnPostformData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postParamRequest();
            }
        });
        btnPostObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postParamObjectRequest();
            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void loadJsonObj() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, obj_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Parse the JSON response
                            Integer employeeId = response.getInt(KEY_EMPLOYEE_ID);
                            String name = response.getString(KEY_NAME);
                            String dob = response.getString(KEY_DOB);
                            String designation = response.getString(KEY_DESIGNATION);
                            String contactNumber = response.getString(KEY_CONTACT_NUMBER);
                            String email = response.getString(KEY_EMAIL);
                            String salary = response.getString(KEY_SALARY);

                            //Create String out of the Parsed JSON
                            StringBuilder textViewData = new StringBuilder().append("Employee Id: ")
                                    .append(employeeId.toString()).append(NEW_LINE);
                            textViewData.append("Name: ").append(name).append(NEW_LINE);
                            textViewData.append("Date of Birth: ").append(dob).append(NEW_LINE);
                            textViewData.append("Designation: ").append(designation).append(NEW_LINE);
                            textViewData.append("Contact Number: ").append(contactNumber).append(NEW_LINE);
                            textViewData.append("Email: ").append(email).append(NEW_LINE);
                            textViewData.append("Salary: ").append(salary).append(NEW_LINE);

                            //Populate textView with the response
                            mTxtDisplay.setText(textViewData.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private void loadJsonArray() {
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, array_url, null, new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray responseArray) {
                        pDialog.dismiss();
                        try {
                            StringBuilder textViewData = new StringBuilder();
                            Log.i("textViewData", "++" + textViewData);
                            //Parse the JSON response array by iterating over it
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject response = responseArray.getJSONObject(i);
                                Integer employeeId = response.getInt(KEY_EMPLOYEE_ID);
                                String name = response.getString(KEY_NAME);
                                String dob = response.getString(KEY_DOB);
                                String designation = response.getString(KEY_DESIGNATION);
                                String contactNumber = response.getString(KEY_CONTACT_NUMBER);
                                String email = response.getString(KEY_EMAIL);
                                String salary = response.getString(KEY_SALARY);

                                //Create String out of the Parsed JSON

                                textViewData.append("Employee Id: ")
                                        .append(employeeId.toString()).append(NEW_LINE);
                                textViewData.append("Name: ").append(name).append(NEW_LINE);
                                textViewData.append("Date of Birth: ").append(dob).append(NEW_LINE);
                                textViewData.append("Designation: ").append(designation).append(NEW_LINE);
                                textViewData.append("Contact Number: ").append(contactNumber).append(NEW_LINE);
                                textViewData.append("Email: ").append(email).append(NEW_LINE);
                                textViewData.append("Salary: ").append(salary).append(NEW_LINE);
                                textViewData.append(NEW_LINE);
                            }

                            //Populate textView with the response
                            mTxtDisplay.setText(textViewData.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /*
     * Passing Json Request with Post parameters and response in the JsonObjectRequest
     * */
    private void postParamRequest() {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        //this is the url where you want to send the request
        //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
        String url = "https://reqres.in/api/users?";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        try {
                            JSONObject obj = new JSONObject(response);
                            String name = obj.getString("name");
                            String email = obj.getString("email");
                            String createdAt = obj.getString("createdAt");
                            Toast.makeText(MainActivity.this, "Name: " + name, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mTxtDisplay.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTxtDisplay.setText("That didn't work!");
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", "ds123");
                params.put("email", "ds123@gmail.com");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    /*
     * Passing Json Request with Json Object and response in the json string
     * */
    private void postParamObjectRequest() {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JSONObject json = new JSONObject();
        try {
            json.put("name", "ds123");
            json.put("email", "ds123@gmail.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "https://reqres.in/api/users?";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("name");
                            Toast.makeText(MainActivity.this, "Name " + name, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mTxtDisplay.setText("String Response : " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTxtDisplay.setText("Error getting response");
            }
        });
        //jsonObjectRequest.setTag(REQ_TAG);
        queue.add(jsonObjectRequest);
    }

}

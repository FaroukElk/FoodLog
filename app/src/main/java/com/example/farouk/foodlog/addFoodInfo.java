package com.example.farouk.foodlog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class addFoodInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_info);
    }

    public void sendFoodInfo(View view) { //Sends food information back to Main Activity
        Intent intent = new Intent(this, MainActivity.class);
        EditText foodText = (EditText) findViewById(R.id.food);
        EditText calsText = (EditText) findViewById(R.id.cals);
        String foodItem = foodText.getText().toString();
        String calsAmount = calsText.getText().toString();
        intent.putExtra("food", foodItem);
        intent.putExtra("cals", calsAmount);
        setResult(RESULT_OK, intent);
        finish();
    }

}

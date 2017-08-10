package com.example.farouk.foodlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{


    SharedPreferences sharedPref;

    Calendar today;

    FoodHandler myMeals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        myMeals = new FoodHandler(this);
        today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        String lastDateAccess = sharedPref.getString("today", "none");
        String calorieGoal = sharedPref.getString("goal", "none");
        final TextView calGoalText = (TextView) findViewById(R.id.calGoal);
        final TextView calRemainingText = (TextView) findViewById(R.id.calRemaining);
        if (calorieGoal.equals("none")){ //If the user is opening the app for the first time set calorie goal
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Calorie Goal");
            builder.setMessage("Please enter your daily calorie goal");
            final EditText calInput = new EditText(this);
            calInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(calInput);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String dailyGoal = calInput.getText().toString();
                    myMeals.calGoal = Integer.parseInt(dailyGoal);
                    SharedPreferences.Editor edit = sharedPref.edit();
                    edit.putString("goal", calInput.getText().toString());
                    edit.apply();
                    calGoalText.setText(dailyGoal);
                    calRemainingText.setText(dailyGoal);
                }
            });
            builder.show();
        }
        else{ //Load the user's calorie goal
            int calGoal = Integer.parseInt(calorieGoal);
            calGoalText.setText(calorieGoal);
            myMeals.calRemaining.setText(calorieGoal);
            myMeals.calGoal = calGoal;
            myMeals.totalRemaining = calGoal;
        }
        if (String.valueOf(today).equals(lastDateAccess)){ //If the user is opening the app on the same day then load their previous food information
            myMeals.readBfastItems();
            myMeals.readLunchItems();
            myMeals.readDinnerItems();
            myMeals.readSnackItems();
        }
        else{ //New day clear previous day's data
            File filesDir = this.getFilesDir();
            try{
                new File(filesDir, "bfastFile.txt").delete();
                new File(filesDir, "lunchFile.txt").delete();
                new File(filesDir, "dinnerFile.txt").delete();
                new File(filesDir, "snackFile.txt").delete();
            }
            catch (Exception e){
                Log.e("tag", e.getMessage());
            }
        }
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onStop(){
        super.onStop();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("today", String.valueOf(today)); //saves the last date the app was accessed
        editor.apply();
    }

    public void addFoodBfast(View view) { //Send user to food information intent
        Intent intentBfast = new Intent(this, addFoodInfo.class);
        startActivityForResult(intentBfast, 0);
    }

    public void addFoodLunch(View view) {
        Intent intentLunch = new Intent(this, addFoodInfo.class);
        startActivityForResult(intentLunch, 1);
    }

    public void addFoodDinner(View view) {
        Intent intentDinner = new Intent(this, addFoodInfo.class);
        startActivityForResult(intentDinner, 2);
    }

    public void addFoodSnacks(View view) {
        Intent intentSnacks = new Intent(this, addFoodInfo.class);
        startActivityForResult(intentSnacks, 3);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) { //Add food info depending on which meal button was pushed
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String foodItem = data.getStringExtra("food");
            String calsAmount = data.getStringExtra("cals");
            myMeals.totalCals += Integer.parseInt(calsAmount);
            myMeals.totalRemaining = myMeals.calGoal - myMeals.totalCals;
            myMeals.calConsumed.setText(String.valueOf(myMeals.totalCals));
            myMeals.calRemaining.setText(String.valueOf(myMeals.totalRemaining));
            switch (requestCode) {
                case 0:
                    myMeals.writeBfastItems(foodItem, calsAmount);
                    myMeals.addBfastItems(foodItem, calsAmount);
                    break;
                case 1:
                    myMeals.writeLunchItems(foodItem, calsAmount);
                    myMeals.addLunchItems(foodItem, calsAmount);
                    break;
                case 2:
                    myMeals.writeDinnerItems(foodItem, calsAmount);
                    myMeals.addDinnerItems(foodItem, calsAmount);
                    break;
                case 3:
                    myMeals.writeSnackItems(foodItem, calsAmount);
                    myMeals.addSnackItems(foodItem, calsAmount);
                    break;
            }
        }
    }
}







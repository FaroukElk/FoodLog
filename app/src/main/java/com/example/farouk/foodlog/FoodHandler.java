package com.example.farouk.foodlog;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.farouk.foodlog.R.id.bfastCals;
import static com.example.farouk.foodlog.R.id.bfastLayout;
import static com.example.farouk.foodlog.R.id.calConsumed;
import static com.example.farouk.foodlog.R.id.calGoal;
import static com.example.farouk.foodlog.R.id.calRemaining;
import static com.example.farouk.foodlog.R.id.lunchCals;

/**
 * Created by Farouk on 12/14/2016.
 */

class FoodHandler {

    private LinearLayout bfastLayout;
    private Integer bfastCals;
    private TextView bfastCalsText;
    private Context context;
    private LinearLayout lunchLayout;
    private Integer lunchCals;
    private TextView lunchCalsText;

    private LinearLayout dinnerLayout;
    private Integer dinnerCals;
    private TextView dinnerCalsText;

    private LinearLayout snackLayout;
    private Integer snackCals;
    private TextView snackCalsText;
    private TextView calGoalText;
    Integer calGoal;
    TextView calConsumed;
    TextView calRemaining;

    Integer totalCals;
    Integer totalRemaining;

    FoodHandler(Context context) {
        this.context = context;
        bfastLayout = (LinearLayout) ((AppCompatActivity) context).findViewById(R.id.bfastLayout);
        bfastCals = 0;
        bfastCalsText = (TextView) ((AppCompatActivity) context).findViewById(R.id.bfastCals);
        calConsumed = (TextView) ((AppCompatActivity) context).findViewById(R.id.calConsumed);
        calRemaining = (TextView) ((AppCompatActivity) context).findViewById(R.id.calRemaining);
        calGoalText = (TextView) ((AppCompatActivity) context).findViewById(R.id.calGoal);
        lunchCals = 0;
        dinnerCals = 0;
        snackCals = 0;
        totalCals = 0;
        lunchLayout = (LinearLayout) ((AppCompatActivity) context).findViewById(R.id.lunchLayout);
        dinnerLayout = (LinearLayout) ((AppCompatActivity) context).findViewById(R.id.dinnerLayout);
        snackLayout = (LinearLayout) ((AppCompatActivity) context).findViewById(R.id.snackLayout);
        calGoal = Integer.parseInt(calGoalText.getText().toString());
        totalRemaining = calGoal;
        lunchCalsText = (TextView) ((AppCompatActivity) context).findViewById(R.id.lunchCals);
        dinnerCalsText = (TextView) ((AppCompatActivity) context).findViewById(R.id.dinnerCals);
        snackCalsText = (TextView) ((AppCompatActivity) context).findViewById(R.id.snackCals);
    }

    void writeBfastItems(String food, String cals) {//Save each meal's food information
        FileOutputStream outputStream;
        String foodAndCals = new StringBuilder(food).append(",").append(cals).toString();
        try {
            File filesDir = context.getFilesDir();
            File bfastFile = new File(filesDir, "bfastFile.txt");
            if (!bfastFile.exists()) {
                bfastFile.createNewFile();
            }
            outputStream = context.openFileOutput("bfastFile.txt", context.MODE_APPEND);
            outputStream.write(foodAndCals.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeLunchItems(String food, String cals) {
        FileOutputStream outputStream;
        String foodAndCals = new StringBuilder(food).append(",").append(cals).toString();
        try {
            File filesDir = context.getFilesDir();
            File lunchFile = new File(filesDir, "lunchFile.txt");
            if(!lunchFile.exists()){
                lunchFile.createNewFile();
            }
            outputStream = context.openFileOutput("lunchFile.txt", Context.MODE_APPEND);
            outputStream.write(foodAndCals.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeDinnerItems(String food, String cals) {
        FileOutputStream outputStream;
        String foodAndCals = new StringBuilder(food).append(",").append(cals).toString();
        try {
            File filesDir = context.getFilesDir();
            File dinnerFile = new File(filesDir, "dinnerFile.txt");
            if(!dinnerFile.exists()){
                dinnerFile.createNewFile();
            }
            outputStream = context.openFileOutput("dinnerFile.txt", Context.MODE_APPEND);
            outputStream.write(foodAndCals.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeSnackItems(String food, String cals) {
        FileOutputStream outputStream;
        String foodAndCals = new StringBuilder(food).append(",").append(cals).toString();
        try {
            File filesDir = context.getFilesDir();
            File snackFile = new File(filesDir, "snackFile.txt");
            if(!snackFile.exists()){
                snackFile.createNewFile();
            }
            outputStream = context.openFileOutput("snackFile.txt", Context.MODE_APPEND);
            outputStream.write(foodAndCals.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readBfastItems() { //read the food information from the same day
        String food;
        String cals;
        try {
            File filesDir = context.getFilesDir();
            File bfastFile = new File(filesDir, "bfastFile.txt");
            FileInputStream fis = new FileInputStream(bfastFile);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String[] s = new String[2];
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                s = line.split(",");
                food = s[0];
                cals = s[1];
                totalCals += Integer.parseInt(cals);
                totalRemaining = calGoal - totalCals;
                calRemaining.setText(String.valueOf(totalRemaining));
                calConsumed.setText(String.valueOf(totalCals));
                addBfastItems(food, cals);
            }
            fis.close();
            isr.close();
            bufferedReader.close();
        } catch (IOException e) {

        }
    }

    void readLunchItems() {
        String food;
        String cals;
        try {
            File filesDir = context.getFilesDir();
            File lunchFile = new File(filesDir, "lunchFile.txt");
            FileInputStream fis = new FileInputStream(lunchFile);
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String[] s = new String[2];
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                s = line.split(",");
                food = s[0];
                cals = s[1];
                totalCals += Integer.parseInt(cals);
                totalRemaining = calGoal - totalCals;
                calRemaining.setText(String.valueOf(totalRemaining));
                calConsumed.setText(String.valueOf(totalCals));
                addLunchItems(food, cals);
            }
            fis.close();
            isr.close();
            bufferedReader.close();
        } catch (IOException e) {

        }
    }

    void readDinnerItems() {
        String food;
        String cals;
        try {
            File filesDir = context.getFilesDir();
            File dinnerFile = new File(filesDir, "dinnerFile.txt");
            FileInputStream fis = new FileInputStream(dinnerFile);
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String[] s = new String[2];
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                s = line.split(",");
                food = s[0];
                cals = s[1];
                totalCals += Integer.parseInt(cals);
                totalRemaining = calGoal - totalCals;
                calRemaining.setText(String.valueOf(totalRemaining));
                calConsumed.setText(String.valueOf(totalCals));
                addDinnerItems(food, cals);
            }
            fis.close();
            isr.close();
            bufferedReader.close();
        } catch (IOException e) {

        }
    }

    void readSnackItems() {
        String food;
        String cals;
        try {
            File filesDir = context.getFilesDir();
            File snackFile = new File(filesDir, "snackFile.txt");
            FileInputStream fis = new FileInputStream(snackFile);
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String[] s = new String[2];
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                s = line.split(",");
                food = s[0];
                cals = s[1];
                totalCals += Integer.parseInt(cals);
                totalRemaining = calGoal - totalCals;
                calRemaining.setText(String.valueOf(totalRemaining));
                calConsumed.setText(String.valueOf(totalCals));
                addSnackItems(food, cals);
            }
            fis.close();
            isr.close();
            bufferedReader.close();
        } catch (IOException e) {

        }
    }

    void addBfastItems(String food, String cals) {
        bfastCals += Integer.parseInt(cals);
        bfastCalsText.setText(String.valueOf(bfastCals));
        View view = LayoutInflater.from(context).inflate(R.layout.itemlayout, null);
        TextView foodView = (TextView) view.findViewById(R.id.food);
        TextView calsView = (TextView) view.findViewById(R.id.calories);
        foodView.setText(food);
        calsView.setText(cals);
        bfastLayout.addView(view);
    }

    void addLunchItems(String food, String cals){
        lunchCals += Integer.parseInt(cals);
        lunchCalsText.setText(String.valueOf(lunchCals));
        View view = LayoutInflater.from(context).inflate(R.layout.itemlayout, null);
        TextView foodView = (TextView) view.findViewById(R.id.food);
        TextView calsView = (TextView) view.findViewById(R.id.calories);
        foodView.setText(food);
        calsView.setText(cals);
        lunchLayout.addView(view);
    }
    void addDinnerItems(String food, String cals){
        dinnerCals += Integer.parseInt(cals);
        dinnerCalsText.setText(String.valueOf(dinnerCals));
        View view = LayoutInflater.from(context).inflate(R.layout.itemlayout, null);
        TextView foodView = (TextView) view.findViewById(R.id.food);
        TextView calsView = (TextView) view.findViewById(R.id.calories);
        foodView.setText(food);
        calsView.setText(cals);
        dinnerLayout.addView(view);
    }
    void addSnackItems(String food, String cals){
        snackCals += Integer.parseInt(cals);
        snackCalsText.setText(String.valueOf(snackCals));
        View view = LayoutInflater.from(context).inflate(R.layout.itemlayout, null);
        TextView foodView = (TextView) view.findViewById(R.id.food);
        TextView calsView = (TextView) view.findViewById(R.id.calories);
        foodView.setText(food);
        calsView.setText(cals);
        snackLayout.addView(view);
    }

}

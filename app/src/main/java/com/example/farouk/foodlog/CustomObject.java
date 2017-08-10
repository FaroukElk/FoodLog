package com.example.farouk.foodlog;

/**
 * Created by Farouk on 12/10/2016.
 */

class CustomObject {
    private String food;
    private String cals;

    CustomObject(String food, String cals){
        this.food = food;
        this.cals = cals;
    }

    String getFood(){
        return food;
    }

    String getCals(){
        return cals;
    }
}

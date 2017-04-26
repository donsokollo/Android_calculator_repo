package com.dm.chebotarskyi.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Stack;
import java.util.ArrayList;

public class SimpleActivity extends AppCompatActivity {




    private TextView mSimpDisplay;
    private TextView mSimpSmallDisplay;

    public void setmSimpDisplay(String setter){
        mSimpDisplay.setText(setter);
    }
    public void clearmSimpDisplay(){
        mSimpDisplay.setText("");
    }
    public String getmSimpDisplay(){
        return this.mSimpDisplay.getText().toString();
    }

    public void setmSimpSmallDisplay(String setter){
        mSimpSmallDisplay.setText(setter);
    }
    public String getmSimpSmallDisplay(){
        return this.mSimpSmallDisplay.getText().toString();
    }

    private CalcLogic logic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_calculator);

        logic = new CalcLogic();
        mSimpDisplay = (TextView) findViewById(R.id.simple_displayText);
        mSimpSmallDisplay = (TextView) findViewById(R.id.simple_smalldisplayText);
    }


    public void onBkspClick(View v){

        logic.clearLastDigit();
        setDisplay();
    }

    public void onSimpleCclick(View v){
        logic.clearAll();
        setDisplay();
    }

    public void onOperatorClick(View v){
        Button btn = (Button) v;
        logic.makeOperation(((Button) v).getText().toString());
        setDisplay();
    }

    public void onPmClick(View v) {

        logic.negateCurrNumb();
        setDisplay();
    }


        public void onNumberClick(View v) {
            Button btn = (Button) v;
            logic.addDigit(btn.getText().toString());
            setDisplay();

        }

    private  void setDisplay(){
        String[] temp =logic.setDispText();
        mSimpDisplay.setText(temp[0]);
        mSimpSmallDisplay.setText(temp[1]);
    }



}






package com.dm.chebotarskyi.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdvancedActivity extends AppCompatActivity {



    private TextView mAdvDisplay;
    private TextView mAdvSmallDisplay;

    public void setmSimpDisplay(String setter){
        mAdvDisplay.setText(setter);
    }
    public void clearmSimpDisplay(){
        mAdvDisplay.setText("");
    }
    public String getmSimpDisplay(){
        return this.mAdvDisplay.getText().toString();
    }

    public void setmSimpSmallDisplay(String setter){
        mAdvSmallDisplay.setText(setter);
    }
    public String getmSimpSmallDisplay(){
        return this.mAdvSmallDisplay.getText().toString();
    }

    private CalcLogic logic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_calculator);

        logic = new CalcLogic(this);


        mAdvDisplay = (TextView) findViewById(R.id.simple_displayText);
        mAdvSmallDisplay = (TextView) findViewById(R.id.simple_smalldisplayText);


    }


    public void onBkspClick(View v){
        logic.clearLastDigit();
    }

    public void onSimpleCclick(View v){
        logic.clearAll();
    }

    public void onOperatorClick(View v){
        Button btn = (Button) v;
        logic.makeOperation(((Button) v).getText().toString());
    }

    public void onPmClick(View v) {
        logic.negateCurrNumb();
    }


    public void onNumberClick(View v) {
        Button btn = (Button) v;
        logic.addDigit(btn.getText().toString());
    }

    public void onFunClick(View v) {
        Button btn = (Button) v;
        logic.makeFun(btn.getText().toString());
    }

}

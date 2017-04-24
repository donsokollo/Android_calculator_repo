package com.dm.chebotarskyi.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

}

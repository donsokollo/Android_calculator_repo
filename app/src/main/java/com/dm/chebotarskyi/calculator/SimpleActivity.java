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




    private TextView mSimpledisplayText;
    private TextView mSimpleSmalldisplayText;





    private Stack<StackItem> stack = new Stack<>();

    private ArrayList stackList = new ArrayList();
    private CalcLogic logic;

    public Stack<StackItem> getStack() {
        return stack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_calculator);

        logic = new CalcLogic();



        mSimpledisplayText = (TextView) findViewById(R.id.simple_displayText);
        mSimpleSmalldisplayText = (TextView) findViewById(R.id.simple_smalldisplayText);



        mSimpleBkspBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(mSimpledisplayText.getText().toString().length()>1){
                mSimpledisplayText.setText(mSimpledisplayText.getText().toString()
                        .substring(0,mSimpledisplayText.getText().toString().length()-1));
            } else mSimpledisplayText.setText("");

            }
        });






        public void onNumberClick(View v) {
            Button btn = (Button) v;
            if (btn.getText().equals(".")){
                if(logic.firstDot()){
                    logic.addDigit((String) btn.getText());
                } else ;
                mSimpledisplayText.setText(btn.getText());
            }else{
                logic.addDigit(btn.getText().toString());
            }
        }





    }




}

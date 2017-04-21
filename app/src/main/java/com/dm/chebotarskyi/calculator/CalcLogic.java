package com.dm.chebotarskyi.calculator;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.ArrayDeque;


/**
 * Created by 800881 on 3/30/2017.
 */



public class CalcLogic {
    private CurrNumber numb = new CurrNumber();

    private ArrayDeque<StackItem> stack= new ArrayDeque<StackItem>();
    private SimpleActivity act;
    private int lastOpPriority =0;



    public  CalcLogic(){
        CurrNumber Numb = new CurrNumber();
        SimpleActivity act = new SimpleActivity();
    }

    public  CalcLogic(SimpleActivity Act){
        this.act = Act;
    }


    /*
    uzyc ArrayDeque na stack!!!

     */

    class CurrNumber{

        boolean dotPresent=false;
        String number = "";

        public boolean firstDot(){
            return !this.dotPresent;
        }
        public void addDot(){
            dotPresent=true;
            number+=".";        }
        public void addDigit(String digit){
            number+=digit;
        }
        public void subDigit(){
            number=number.substring(0,number.length()-1);
        }
        public String getNumber(){
            return this.number;
        }

        public Number makeNumber(){
            float fullNumb = Float.parseFloat(number);
            Number numb = new Number(fullNumb);
            clearNumber();
            return numb;
        }

        public void clearNumber(){
            this.number="";
            this.dotPresent=false;
        }

    }

    public boolean firstDot(){
        if (numb.firstDot()){
            return true;
        } else {
            numb.addDot();
            return false;
        }
    }

    public void addDigit(String digit){
        if (digit.equals(".")){
            if(numb.firstDot()){
                numb.addDot();
                if (numb.getNumber().isEmpty()){
                    act.setmSimpDisplay("0"+digit);
                } else act.setmSimpDisplay(act.getmSimpDisplay()+digit);
            }

        } else {
            numb.addDigit(digit);
            act.setmSimpDisplay(act.getmSimpDisplay()+digit);
        }

    }

    public  Number makeNumb(){
        return  numb.makeNumber();
    }

    public void clearLastDigit(){
        if (!act.getmSimpDisplay().isEmpty()){
            numb.subDigit();
            act.setmSimpDisplay(act.getmSimpDisplay().substring(0,act.getmSimpDisplay().length()-1));
        }

    }

    public void clearAll(){
        numb.clearNumber();
        stack.clear();
        act.setmSimpDisplay("");
        act.setmSimpSmallDisplay("");
    }



    public void makeOperation(String operator){

       // String pobranyTekst = mWyswietlacz.getText().toString();
        Operator op = new Operator(operator); //making operator

        if (numb.getNumber().isEmpty()){
            //do nothing
        } else if (lastOpPriority > op.checkPriority()) {
            stack.push(makeNumb());

            while (lastOpPriority > op.checkPriority()) {
                float firstNumb = stack.pollLast().getNumber();
                String lastOper = stack.pollLast().getOperator();
                float secondNumb = stack.pollLast().getNumber();

                if (stack.isEmpty()) {
                    this.lastOpPriority = 0;
                } else this.lastOpPriority = stack.peekLast().checkPriority();


                Number res = new Number();
                switch (lastOper) {
                    case "-":
                        res.setNumber(secondNumb - firstNumb);
                        break;
                    case "+":
                        res.setNumber(secondNumb + firstNumb);
                        break;
                    case "*":
                        res.setNumber(secondNumb * firstNumb);
                        break;
                    case "/":
                        res.setNumber(secondNumb / firstNumb);
                        break;
                }

                stack.addLast(res);
                act.setmSimpDisplay(Float.toString(res.getNumber()));
            }
        }   else if(lastOpPriority <= op.checkPriority()){
            act.clearmSimpDisplay();
            stack.push(makeNumb());
            act.setmSimpSmallDisplay(act.getmSimpSmallDisplay() + Float.toString(stack.peek().getNumber()));
            stack.push(op);
            act.setmSimpSmallDisplay(act.getmSimpSmallDisplay() + stack.peek().getOperator());
            lastOpPriority = op.checkPriority();
        }





    }

}

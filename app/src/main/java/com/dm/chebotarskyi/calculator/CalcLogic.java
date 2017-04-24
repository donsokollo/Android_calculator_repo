package com.dm.chebotarskyi.calculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.ArrayDeque;


/**
 * Created by 800881 on 3/30/2017.
 */



public class CalcLogic {
    private CurrNumber numb = new CurrNumber();

    private ArrayDeque<StackItem> stack= new ArrayDeque<StackItem>();
    private SimpleActivity act;
    private AdvancedActivity actA;
    private int lastOpPriority =0;




    public  CalcLogic(SimpleActivity Act){
        this.act = Act;
    }

    public  CalcLogic(AdvancedActivity Act){
        this.actA = Act;
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
        public void setNumber(String number){this.number=number;}

        public Number makeNumber(){
            if (number.equals("0.")){
                return new Number(0);
            } else {
                float fullNumb = Float.parseFloat(number);
                Number numb = new Number(fullNumb);
                clearNumber();
                return numb;
            }
        }

        public void clearNumber(){
            this.number="";
            this.dotPresent=false;
        }

    }



    public void setDispText(){
       String simSmallDisp = "";
        Iterator<StackItem>  it= stack.descendingIterator();

        while (it.hasNext()  ){
            StackItem temp =it.next();
            simSmallDisp+=temp.getItem();

        }
        act.setmSimpSmallDisplay(simSmallDisp);
        if (stack.peek().isNumber()){
            act.setmSimpDisplay(stack.peek().getItem());
        }
    }

    public void makeFun(String fun){
        //to do!!!!!!!!!!!
    }

    public void addDigit(String digit){
        if (!stack.isEmpty() && stack.peek().isNumber()){
            stack.pop();  //if after "=" sign we enter new number it vanishes
            lastOpPriority=0;
        }
        if (digit.equals(".")){
            if(numb.firstDot()){
                if (numb.getNumber().isEmpty()){
                    numb.addDigit("0");
                }
                numb.addDot();
                act.setmSimpDisplay(numb.getNumber());
            }
        } else {
            numb.addDigit(digit);
            act.setmSimpDisplay(numb.getNumber());
        }

    }

    public  Number makeNumb(){
        return  numb.makeNumber();
    }

    public void clearLastDigit(){
        if (!act.getmSimpDisplay().isEmpty() && !numb.getNumber().isEmpty()){
            numb.subDigit();
            act.setmSimpDisplay(act.getmSimpDisplay().substring(0,act.getmSimpDisplay().length()-1));
        }

    }

    public void clearAll(){
        numb.clearNumber();
        stack.clear();
        this.lastOpPriority=0;
        act.setmSimpDisplay("");
        act.setmSimpSmallDisplay("");
    }


    private void simplifyStack(Operator op, boolean simplifyAll) {

        boolean stackEmpty = false;

        while (  (!stackEmpty && simplifyAll) || (!stackEmpty && lastOpPriority >= op.checkPriority())) {

            String firstNumb = stack.pop().getItem();//nummb
            String lastOper = stack.pop().getItem();//oper
            String secondNumb = stack.pop().getItem(); //numb

            if (stack.isEmpty()) {

                stackEmpty = true;
            }


            Number res = new Number();
            switch (lastOper) {
                case "-":
                    res.setNumber(Float.parseFloat(secondNumb) - Float.parseFloat(firstNumb));
                    break;
                case "+":
                    res.setNumber(Float.parseFloat(secondNumb) + Float.parseFloat(firstNumb));
                    break;
                case "*":
                    res.setNumber(Float.parseFloat(secondNumb) * Float.parseFloat(firstNumb));
                    break;
                case "/":
                    res.setNumber(Float.parseFloat(secondNumb) / Float.parseFloat(firstNumb));
                    break;
            }


            stack.push(res);
            this.lastOpPriority = op.checkPriority();


            setDispText();
        }
        if (stackEmpty && !simplifyAll) {

            stack.push(op);//!!!!!???
        }
    }

    public void negateCurrNumb() {
        if (!numb.getNumber().isEmpty()) {
            numb.setNumber(
                    Float.toString(-1 * Float.parseFloat(numb.getNumber()))
            );
            act.setmSimpDisplay(numb.getNumber());
        } else if (!stack.isEmpty() && stack.peek().isNumber()){
            numb.setNumber(
                    Float.toString(-1 * Float.parseFloat(stack.pop().getItem()))
                    );
            act.setmSimpDisplay(numb.getNumber());
            lastOpPriority=0;
        }

    }

        public void makeOperation(String operator){

        Operator op = new Operator(operator); //making operator

        if (op.getItem().equals("=")){
            if (!stack.isEmpty()){
                if (!numb.getNumber().isEmpty()){
                    stack.push(makeNumb());
                    simplifyStack(new Operator("^"), true);
                }

            }
        } else if (numb.getNumber().isEmpty()){
            if (!stack.isEmpty() ){
                if(stack.peek().isNumber()){
                    stack.push(op);
                    lastOpPriority=op.checkPriority();
                }
            }
        } else if (lastOpPriority >= op.checkPriority()) {
            stack.push(makeNumb());

                simplifyStack(op, false);

        }   else if(lastOpPriority < op.checkPriority()){

            stack.push(makeNumb());
            act.setmSimpDisplay(stack.peek().getItem()); //setting the regular display
            stack.push(op);
            lastOpPriority = op.checkPriority();
            setDispText();


        }






    }

}

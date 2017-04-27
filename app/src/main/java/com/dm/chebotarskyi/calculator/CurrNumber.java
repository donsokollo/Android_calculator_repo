package com.dm.chebotarskyi.calculator;

/**
 * Created by Sokol on 2017-04-26.
 */

public class CurrNumber {


    boolean dotPresent = false;
    String number = "";

    public boolean firstDot() {
        return !this.dotPresent;
    }

    public void addDot() {
        dotPresent = true;
        number += ".";
    }

    public void addDigit(String digit) {
        number += digit;
    }

    public void subDigit() {
        number = number.substring(0, number.length() - 1);
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Number makeNumber() {
        if (number.equals("0.")) {
            clearNumber();
            return new Number(0);
        } else {
            float fullNumb = Float.parseFloat(number);
            Number numb = new Number(fullNumb);
            clearNumber();
            return numb;
        }
    }

    public void clearNumber() {
        this.number = "";
        this.dotPresent = false;
    }
}

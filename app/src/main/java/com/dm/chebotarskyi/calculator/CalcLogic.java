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
    private ArrayDeque<StackItem> stack = new ArrayDeque<StackItem>();
    private int lastOpPriority = 0;


    public CalcLogic() {
    }


    public String[] setDispText() {
        String simSmallDisp = "";
        String simDisp = "";
        Iterator<StackItem> it = stack.descendingIterator();

        while (it.hasNext()) {
            StackItem temp = it.next();
            simSmallDisp += temp.getItem();

        }
        if (!numb.getNumber().isEmpty()) {
            simDisp = numb.getNumber();
        } else if (!stack.isEmpty() && stack.peek().isNumber()) {
            simDisp = stack.peek().getItem();
        }
        return new String[]{simDisp, simSmallDisp};
    }

    private void calcFun(String fun, float num) {
        float res = 0;
        boolean validOp = true;
        switch (fun) {
            case "sin":
                res = (float) Math.sin(num);
                break;
            case "cos":
                res = (float) Math.cos(num);
                break;
            case "log":
                if (num >= 0) {
                    res = (float) Math.log10(num);
                } else validOp = false;
                break;
            case "ln":
                if (num >= 0) {
                    res = (float) Math.log(num);
                } else validOp = false;
                break;
            case "x^2":
                res = (float) Math.pow(num, 2);
                break;
            case "sqrt":
                if (num >= 0) {
                    res = (float) Math.sqrt(num);
                } else validOp = false;
                break;
            case "tan":
                if (num % 2 != Math.PI) {
                    res = (float) Math.sqrt(num);
                } else validOp = false;
                break;
        }

        if (validOp) {
            stack.push(new Number(res));
        } else numb.setNumber(Float.toString(num));

    }


    public void makeFun(String fun) {
        float num = 0;

        if (!numb.getNumber().isEmpty()) {
            num = Float.parseFloat(numb.getNumber());
            numb.clearNumber();
            calcFun(fun, num);
        } else if (!stack.isEmpty() && stack.peek().isNumber()) {
            num = Float.parseFloat(stack.pop().getItem());
            calcFun(fun, num);
        }

    }

    public void addDigit(String digit) {
        if (!stack.isEmpty() && stack.peek().isNumber()) {
            stack.pop();  //if after "=" sign we enter new number it vanishes
            lastOpPriority = 0;
        }
        if (digit.equals(".")) {
            if (numb.firstDot()) {
                if (numb.getNumber().isEmpty()) {
                    numb.addDigit("0");
                }
                numb.addDot();
            }
        } else {
            numb.addDigit(digit);
        }
    }

    public Number makeNumb() {
        return numb.makeNumber();
    }

    public void clearLastDigit() {
        if (!numb.getNumber().isEmpty()) {
            numb.subDigit();
        }
    }

    public void clearAll() {
        numb.clearNumber();
        stack.clear();
        this.lastOpPriority = 0;
    }


    private void simplifyStack(Operator op, boolean simplifyAll) {
        boolean stackEmpty = false;
        while ((!stackEmpty && simplifyAll) || (!stackEmpty && lastOpPriority >= op.checkPriority())) {
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
                case "^":
                    res.setNumber((float) (Math.pow(Double.parseDouble(secondNumb), Double.parseDouble(firstNumb))));
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

        } else if (!stack.isEmpty() && stack.peek().isNumber()) {
            numb.setNumber(
                    Float.toString(-1 * Float.parseFloat(stack.pop().getItem()))
            );
            lastOpPriority = 0;
        }
    }

    public void makeOperation(String operator) {
        Operator op = new Operator(operator); //making operator

        if (op.getItem().equals("=")) {
            if (!stack.isEmpty()) {
                if (!numb.getNumber().isEmpty()) {
                    stack.push(makeNumb());
                    simplifyStack(new Operator("^"), true);
                } else simplifyStack(new Operator("^"), true);

            }
        } else if (numb.getNumber().isEmpty()) {
            if (!stack.isEmpty()) {
                if (stack.peek().isNumber()) {
                    stack.push(op);
                    lastOpPriority = op.checkPriority();
                }
            }
        } else if (lastOpPriority >= op.checkPriority()) {
            stack.push(makeNumb());
            simplifyStack(op, false);
        } else if (lastOpPriority < op.checkPriority()) {
            stack.push(makeNumb());
            stack.push(op);
            lastOpPriority = op.checkPriority();
            setDispText();
        }
    }
}

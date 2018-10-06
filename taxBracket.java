package com.company;
import java.util.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class taxBracket {
    public void start(){
        System.out.println("Tax stuff");
        predisposedTax();
    }
    private void predisposedTax(){
        double income=85000;
        TreeMap<Double,taxInfo> taxBrackets=new TreeMap<>(); //treemaps are sorted maps

        double [][] arr={{10000.0,10.0,0},{20000.0,20.0,0},{40000.0,30.0,0},{80000.0,40.0,0},{Double.MAX_VALUE,50.0,0}};

        initTaxBracket(arr);
        taxInfo [] taxArr=new taxInfo[arr.length];
        for(int i=0;i<arr.length;i++){
            taxArr[i]=new taxInfo(arr[i][0],arr[i][1],arr[i][2]);
        }
        double taxes=predisposedTaxableIncome(income,taxArr);

        System.out.println(taxes);
    }
    private void initTaxBracket(double[][] arr){
        double tempTax=0;
        double lowerIndex=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i][0]!=Double.MAX_VALUE){
                tempTax+=(arr[i][0]-lowerIndex)*(arr[i][1]/100);
                lowerIndex=arr[i][0];
                System.out.println("pre:"+tempTax);
                arr[i][2]=tempTax;
            }
        }
    }
    private double predisposedTaxableIncome(double income,taxInfo []taxBracket){
        int index=binarySearchTax(income,taxBracket,0,taxBracket.length-1);
        System.out.print("index:"+index+"\n");
        double totalTax=0;
        double leftover=income-taxBracket[index].upperBound;
        if(leftover>0){
            totalTax+=taxBracket[index].predisposedIncome+leftover*(taxBracket[index+1].rate)/100;
        }else{
            totalTax+=income*(taxBracket[index].rate)/100;
        }
        return totalTax;
    }
    private int binarySearchTax(double income,taxInfo []taxBracket,int l,int r){
        int mid=l+(r-l)/2;
        System.out.println("mid:"+mid+",l:"+l+",r:"+r);
        if(r-l==1){
            if(taxBracket[r].upperBound<income) {
                return r;
            }else{
                return l;
            }
        }else if(r==l){
            return l;
        }
        if(r>=1){
            if(taxBracket[mid].upperBound==income){
                return mid;
            }
            if(taxBracket[mid].upperBound>income){
                return binarySearchTax(income,taxBracket,l,mid-1);
            }
            if(taxBracket[mid].upperBound<income) {
                return binarySearchTax(income, taxBracket, mid , r);
            }
        }
        return -1;
//        for(int i=0;i<taxBracket.length;i++){
//            System.out.print(taxBracket[i].upperBound+","+taxBracket[i].rate+","+taxBracket[i].predisposedIncome+"\n");
//        }
//        return 0;
    }
    private void iterativeTax(){
        double income=27000;

        TreeMap<Double,Double> taxBrackets=new TreeMap<>(); //treemaps are sorted maps
        taxBrackets.put(10000.0,10.0);
        taxBrackets.put(20000.0,20.0);
        taxBrackets.put(40000.0,30.0);
        taxBrackets.put(80000.0,40.0);
        taxBrackets.put(Double.MAX_VALUE,50.0);
        double taxes=taxableIncome(income,taxBrackets);

        System.out.println(taxes);
    }
    private double taxableIncome(double income, TreeMap<Double,Double> taxBracket){
        Set set=taxBracket.entrySet();
        Iterator i=set.iterator();
        double incomeTax=0;
        double range=0;
        double lowerLimit=0;
        double interestRange=0;
        while(i.hasNext() && income>0){
            Map.Entry me=(Map.Entry)i.next();
            range=Double.valueOf( (Double)me.getKey())-lowerLimit;
            if(income>range){
                income-=range;
                interestRange=range*Double.valueOf( (Double)me.getValue())/100;
            }else{
                interestRange=income*Double.valueOf( (Double)me.getValue())/100;
                income=0;
            }
            lowerLimit=Double.valueOf( (Double)me.getKey());
            System.out.print(me.getKey() + ": ");
            System.out.print(me.getValue()+",");
            System.out.print(interestRange+",");
            incomeTax+=interestRange;
            System.out.println(incomeTax);
        }
        return incomeTax;
    }
}

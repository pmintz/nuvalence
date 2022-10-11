package com.rectangles.nuvalence.test;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.naming.InterruptedNamingException;
import java.util.ArrayList;
import java.util.List;

@ShellComponent
public class Test {

    @ShellMethod
    public void Test(@ShellOption(arity = 3) int[] points){
        List<Integer[]> test = new ArrayList<>();
        List<Integer[]> test2 = new ArrayList<>();
        /*Integer[] testArr = new Integer[]{1,1};
        Integer[] testArr2 = new Integer[]{2,2};
        test.add(testArr);
        test.add(testArr2);
        List<Integer[]> test2 = new ArrayList<>();
        Integer[] testArr3 = new Integer[]{3,3};
        Integer[] testArr4 = new Integer[]{4,4};
        test2.add(testArr);
        test2.add(testArr2);

        testArr4[0] = 1;
        testArr4[1] = 1;*/

        //System.out.println(points[0]);

        test.add(new Integer[]{points[0], points[1]});
        //test2.add(points);
        Integer[] pointArr = test.get(0);
        //Integer[] pointArr2 = test2.get(0);

        //System.out.println(pointArr[0]);
        //System.out.println(pointArr2[0]+ " "+pointArr2[1]);

        System.out.println(pointArr[0]);
        System.out.println(pointArr[1]);



    }
}

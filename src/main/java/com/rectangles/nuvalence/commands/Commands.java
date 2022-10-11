package com.rectangles.nuvalence.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@ShellComponent
public class Commands {

    List<Integer[]> arrList = new ArrayList<>();
    List<Integer[]> arrList2 = new ArrayList<>();
    String rectangleName1;
    String rectangleName2;

    @ShellMethod void nameFirstRectangle(String name){
        rectangleName1 = name.trim();
    }

    @ShellMethod void nameSecondRectangle(String name){
        rectangleName2 = name.trim();
    }

    @ShellMethod("Enter two numbers, which will become one point of a rectangle, along with an String type option.  " +
            "The option is the name of the rectangle a point belongs to.  Here is an example: " +
            "add-point 2,2 --name rect1")
    public String addPoint(@ShellOption(arity=2) Integer[] points, @ShellOption String name) {

       String nameTrimmed = name.trim();

        String result="";

        if(rectangleName1.isEmpty() || rectangleName2.isEmpty()) {
            return "Must name rectangles first.  Use name-first-rectangle and name-second-rectangle commands.";
        }

        if(nameTrimmed.equals(rectangleName1)){
            arrList.add(new Integer[]{points[0], points[1]});
        }
        if (nameTrimmed.equals(rectangleName2)) {
            arrList2.add(new Integer[]{points[0], points[1]});
        }

        if(arrList.size() == 4 && arrList2.size() ==4){
            if(rectangleDetermination(arrList, arrList2)){
                result = intersectionContainAdjacent(arrList, arrList2);
            };
        }
        return result;
    }

    public static boolean rectangleDetermination(List<Integer[]> arrList1, List<Integer[]> arrList2){
        if(!distinctPoints(arrList1) && !distinctPoints(arrList2))
            return false;
        else
        return true;
    }

    public static String intersectionContainAdjacent(List<Integer[]> arrList1, List<Integer[]> arrList2){
        String result = "";
        result = containment(arrList1, arrList2);
        if(!result.isEmpty()){
            return result;
        }
        result = adjacent(arrList1, arrList2);
        result += intersection(arrList1,arrList2);

        if(result.isEmpty()){
            return "No containment.  No intersecting or adjacent edges";
        }else{
            return result;
        }

    }

    public static boolean distinctPoints(List<Integer[]> arrList){

        int pointCounter = 0;
        for(Integer[] point : arrList){
            for(Integer[] point2 : arrList){
                if(point[0] == point2[0]);
                    pointCounter++;
                if(point[1] == point2[1])
                    pointCounter++;

            }
        }

        if(pointCounter == 24)
            return true;
        else
            return false;
    }

    static public String adjacent(List<Integer[]> arrList1, List<Integer[]> arrList2){
        String adjacent = "";
        int xAdjacentCounter = 0;
        int yAdjacentCounter = 0;
        for(Integer[] arr1: arrList1) {
            for (Integer[] arr2 : arrList2) {
                if (arr1[0] == arr2[0])
                    xAdjacentCounter++;
                if (arr1[1] == arr2[1])
                    yAdjacentCounter++;
            }

        }

        if(xAdjacentCounter == 2 || yAdjacentCounter == 2)
            adjacent = "Rectangle has Adjacent sides";

        return adjacent;
    }

    static public String containment(List<Integer[]> arrList1, List<Integer[]> arrList2){
        boolean containment = false;
        List<Integer[]> arr1List1SmallestBiggest = smallestBiggest(arrList1);
        List<Integer[]> arr2List1SmallestBiggest = smallestBiggest(arrList2);

        Integer[] arr1Smallest = arr1List1SmallestBiggest.get(0);
        Integer[] arr2Smallest = arr2List1SmallestBiggest.get(0);
        Integer[] arr1Largest= arr1List1SmallestBiggest.get(1);
        Integer[] arr2Largest = arr2List1SmallestBiggest.get(1);

        Integer[] arr1DiffSmallBig = new Integer[]{arr1Largest[0]-arr1Smallest[0],arr1Largest[1]-arr1Smallest[1]};
        Integer[] arr2DiffSmallBig = new Integer[]{arr2Largest[0]-arr2Smallest[0],arr2Largest[1]-arr2Smallest[1]};

        if((arr1DiffSmallBig[0] + arr1DiffSmallBig[1]) > (arr2DiffSmallBig[0]+arr2DiffSmallBig[1])){
            if((arr1DiffSmallBig[0] - arr2DiffSmallBig[0] >=2) && (arr1DiffSmallBig[1] - arr2DiffSmallBig[1] >=2)){
                if((arr2Smallest[0] - arr1Smallest[0] >=1) &&(arr2Smallest[1] - arr1Smallest[1] >=1)){
                    if((arr1Largest[0] - arr2Largest[0] >=1) &&(arr1Largest[1] - arr2Largest[1]  >=1)){
                        containment = true;
                    }

                }
            }
        } else {
            if ((arr2DiffSmallBig[0] - arr1DiffSmallBig[0] >= 2) && (arr2DiffSmallBig[1] - arr1DiffSmallBig[1] >= 2)) {
                if ((arr1Smallest[0] - arr2Smallest[0] >= 1) && (arr1Smallest[1] - arr2Smallest[1] >= 1)) {
                    if ((arr2Largest[0] - arr1Largest[0] >= 1) && (arr2Largest[1] - arr1Largest[1] >= 1)) {
                        containment = true;
                    }

                }
            }
        }
        if(containment){
            return "One rectangle is fully contained within the other.  ";
        }else{
            return "";
        }

    }

    static public String intersection(List<Integer[]> arrList1, List<Integer[]> arrList2){
        String intersection="";
        List<Integer[]> arr1List1SmallestBiggest = smallestBiggest(arrList1);
        List<Integer[]> arr2List1SmallestBiggest = smallestBiggest(arrList2);

        Integer[] arr1Smallest = arr1List1SmallestBiggest.get(0);
        Integer[] arr2Smallest = arr2List1SmallestBiggest.get(0);
        Integer[] arr1Largest= arr1List1SmallestBiggest.get(1);
        Integer[] arr2Largest = arr2List1SmallestBiggest.get(1);

        int arr1Counter=0;
        List<Integer[]> arr1IntersectionPoints = new ArrayList<>();

        for(Integer[] arr1: arrList1){
            if((arr1[0] > arr2Smallest[0] && arr1[0] < arr2Largest[0]) &&
                    (arr1[1] > arr2Smallest[1] && arr1[1] < arr2Largest[1]) ){
                arr1IntersectionPoints.add(new Integer[]{arr1[0], arr1[1]});
                arr1Counter++;
                if(arr1Counter == 2){
                    arr1IntersectionPoints.add(new Integer[]{arr1[0], arr1[1]});
                }
            }

        }

        int arr2Counter=0;
        List<Integer[]> arr2IntersectionPoints = new ArrayList<>();

        for(Integer[] arr2: arrList2) {
            if ((arr2[0] > arr1Smallest[0] && arr2[0] < arr1Largest[0]) &&
                    (arr2[1] > arr1Smallest[1] && arr2[1] < arr1Largest[1])) {
                arr2IntersectionPoints.add(new Integer[]{arr2[0], arr2[1]});
                arr2Counter++;
                if (arr2Counter == 2) {
                    arr2IntersectionPoints.add(new Integer[]{arr2[0], arr2[1]});
                }
            }
        }

        if((arr1IntersectionPoints.size() == 2)){
            intersection = "Intersection at " + arr1IntersectionPoints.get(0) + " and "
                    + arr1IntersectionPoints.get(1);
        } else if((arr2IntersectionPoints.size() == 2)){
            intersection = "Intersection at " + arr2IntersectionPoints.get(0) + " and "
                    + arr2IntersectionPoints.get(1);

        }

        return intersection;



    }



    static public List<Integer[]> smallestBiggest(List<Integer[]> arrList){
        List<Integer[]> smallestBiggest = new ArrayList<>();
        Integer[] temp = arrList.get(0);
        for(Integer[] arr : arrList){
            if ((arr[0] <= temp[0]) && (arr[1] <= temp[1])){
                temp[0] = arr[0];
                temp[1] = arr[1];
            }

        }

        smallestBiggest.add(temp);
        Integer[] temp2 = arrList.get(1);
        for(Integer[] arr: arrList){
            if ((arr[0] >= temp2[0]) && (arr[1] >= temp2[1])){
                temp2[0] = arr[0];
                temp2[1] = arr[1];
            }

        }

        smallestBiggest.add(temp2);
        return  smallestBiggest;

    }




}

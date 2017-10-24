import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.*;

public class Main
{
    public static void main(String[] args)
    {
        int size = args.length;
        if(size == 0){
            out.println("-1");
            System.exit(0);
        }

        double[] list = new double[size];
        int[] indexList = new int[size];
        try
        {
            for(int x=0; x<size; x++) {
                list[x] = Double.parseDouble(args[x]);
                indexList[x] = x;
            }
        }
        catch(Exception e)
        {
            out.println("-1");
            System.exit(0);
        }

        Scanner scan = new Scanner(in);
        double key = 0;
        try {
            key = scan.nextDouble();
        }
        catch(Exception e)
        {
            out.println("-1");
            System.exit(0);
        }

        mergeSort(list, indexList,0, size-1);


        int returnValue = binSearch(list, indexList, key);

        if(returnValue != -1)
            returnValue = findDuplicates(list, indexList, returnValue);

        String result = "The key was not found!";

        if(-1<returnValue)
            result = String.valueOf(indexList[returnValue]);


        out.println(result);
    }

    private static int binSearch(double[] aList, int[] indexList, double key)
    {
        int max = aList.length - 1;
        int min = 0;
        int mid;

        while(min <= max)
        {
            mid = (min + max) /2;
            if(Double.compare(aList[mid], key) == 0)
            {
                return mid;
            }
            else if(/*aList[mid] > key */ Double.compare(aList[mid], key) > 0)
            {
                max = mid - 1;
            }
            else if(/*aList[mid] < key*/ Double.compare(aList[mid], key) < 0)
            {
                min = mid + 1;
            }
        }
        return -1;
    }

    private static void mergeSort(double[] list, int[] indexList, int l, int h)
    {
        if(l < h) {
            int mid = ((h - l) / 2) + l;

            mergeSort(list, indexList, l, mid);
            mergeSort(list,indexList,mid+1, h);

            merge(list, indexList,l, mid, h);
        }
    }

    private static void merge(double[] list, int[] indexList, int l, int mid, int h)
    {
        int n1 = mid - l +1;
        int n2 = h - mid;

        double[] listA = new double[n1];
        double[] listB = new double[n2];

        int[] indexA = new int[n1];
        int[] indexB = new int[n2];

        for (int i=0; i < n1; i++)
        {
            listA[i] = list[i+l];
            indexA[i] = indexList[i+l];
        }
        for(int j=0; j < n2; j++)
        {
            listB[j] = list[mid+1+j];
            indexB[j] = indexList[mid+1+j];
        }

        int i=0, j=0, k=l;

        while(i<n1 && j<n2)
        {
            if(/*listA[i] <= listB[j]*/ Double.compare(listA[i], listB[j]) <= 0)
            {
                list[k] = listA[i];
                indexList[k] = indexA[i];
                i++; k++;
            }
            else
            {
                list[k] = listB[j];
                indexList[k] = indexB[j];
                j++; k++;
            }
        }

        while (i < n1)
        {
            list[k] = listA[i];
            indexList[k] = indexA[i];
            i++; k++;
        }

        while(j < n2)
        {
            list[k] = listB[j];
            indexList[k] = indexB[j];
            j++; k++;
        }
    }

    private static int findDuplicates(double[] list, int[] indexList, int ogIndex)
    {
        boolean rightDuplicate = true;
        boolean leftDuplicate = true;

        int pointerIndex = ogIndex;
        int finalIndex = ogIndex;

        do
        {
            ++pointerIndex;
            if(pointerIndex >= list.length)
                break;

            if(Double.compare(list[pointerIndex], list[finalIndex]) == 0)
            {
                if(indexList[pointerIndex] < indexList[finalIndex])
                {
                    finalIndex = pointerIndex;
                }
            }
            else
            {
                rightDuplicate = false;
            }

        }while(rightDuplicate);

        pointerIndex = ogIndex;

        do {

            --pointerIndex;
            if(pointerIndex <= -1)
                break;

            if(Double.compare(list[pointerIndex], list[finalIndex]) == 0)
            {
                if(indexList[pointerIndex] < indexList[finalIndex])
                {
                    finalIndex = pointerIndex;
                }
            }
            else
            {
                leftDuplicate = false;
            }

        }while(leftDuplicate);

        return finalIndex;
    }
}

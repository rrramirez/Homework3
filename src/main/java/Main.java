import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.cli.*;

import static java.lang.System.*;

public class Main
{
    public static void main(String[] args)
    {
        Options options = new Options();

        Option option1 = Option.builder().hasArg().longOpt("type").build();
        Option option2 = Option.builder().hasArg().longOpt("key").build();
        Option option3 = Option.builder().hasArgs().longOpt("list").build();
        options.addOption(option1);
        options.addOption(option2);
        options.addOption(option3);

        String type="";
        String tempKey="";
        String[] tempList=null;
        int size = 0;

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            type = cmd.getOptionValue("type");
            tempKey = cmd.getOptionValue("key");
            tempList = cmd.getOptionValues("list");

            if(type == null)
                throw new ParseException("Argument --type not given");
            if(tempKey == null)
                throw new ParseException("Argument --key not given");
            if(tempList == null)
                throw new ParseException("Argument --list not given");

            for(String x : tempList)
                size++;
        }
        catch(ParseException e)
        {
            out.println("There was an error reading arguments");
            e.printStackTrace();
            exit(0);
        }


        if(!((type.compareTo("i") != 0 && type.compareTo("s") == 0) || (type.compareTo("i") == 0 && type.compareTo("s") != 0)))
        {
            out.println("Unrecognized 'type' argument given. Try 'i' for integer type or 's' for String type.");
            exit(0);
        }

        Comparable[] list = new Comparable[size];


        int[] indexList = new int[size];
        try
        {
            for(int x=0; x<size; x++) {
                list[x] = tempList[x];
                indexList[x] = x;
            }
        }
        catch(Exception e)
        {
            out.println("-1");
            System.exit(0);
        }

        Comparable key = tempKey;

        mergeSort(list, indexList,0, size-1);


        int returnValue = binSearch(list, indexList, key);

        if(returnValue != -1)
            returnValue = findDuplicates(list, indexList, returnValue);

        String result = "The key was not found!";

        if(-1<returnValue)
            result = String.valueOf(indexList[returnValue]);


        out.println(result);
    }

    private static int binSearch(Comparable[] aList, int[] indexList, Comparable key)
    {
        int max = aList.length - 1;
        int min = 0;
        int mid;

        while(min <= max)
        {
            mid = (min + max) /2;
            if(/*Double.compare(aList[mid], key)*/aList[mid].compareTo(key) == 0)
            {
                return mid;
            }
            else if(/*aList[mid] > key*/ aList[mid].compareTo(key) > 0)
            {
                max = mid - 1;
            }
            else if(/*aList[mid] < key*/ aList[mid].compareTo(key) < 0)
            {
                min = mid + 1;
            }
        }
        return -1;
    }

    private static void mergeSort(Comparable[] list, int[] indexList, int l, int h)
    {
        if(l < h) {
            int mid = ((h - l) / 2) + l;

            mergeSort(list, indexList, l, mid);
            mergeSort(list,indexList,mid+1, h);

            merge(list, indexList,l, mid, h);
        }
    }

    private static void merge(Comparable[] list, int[] indexList, int l, int mid, int h)
    {
        int n1 = mid - l +1;
        int n2 = h - mid;

        Comparable[] listA = new Comparable[n1];
        Comparable[] listB = new Comparable[n2];

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
            if(/*listA[i] <= listB[j]*/ listA[i].compareTo(listB[j]) <= 0)
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

    private static int findDuplicates(Comparable[] list, int[] indexList, int ogIndex)
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

            if(/*Double.compare(list[pointerIndex], list[finalIndex])*/ list[pointerIndex].compareTo(list[finalIndex]) == 0)
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

            if(/*Double.compare(list[pointerIndex], list[finalIndex])*/ list[pointerIndex].compareTo(list[finalIndex]) == 0)
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

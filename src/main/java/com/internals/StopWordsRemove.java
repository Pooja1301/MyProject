package com.internals;

import java.util.*;
import java.util.regex.Pattern;
import java.io.*;


public class StopWordsRemove {

    private static final String RESULT_FNAME = "Output.txt";

    public static Boolean isStopWord(String word, String[] stopWords)
    {
        boolean found =false;

        for(String stopword: stopWords){
            if(compareWords(word, stopword) == 0) {
                found = true;
                break;
            }
        }

       return found;
    }

    public static int compareWords(String word1, String word2)
    {
        return word1.compareToIgnoreCase(word2);
    }

    public static String[] readStopWords(String stopWordsFilename)
    {
        String[] stopWords = null;
        try
        {
            Scanner stopWordsFile = new Scanner(new File(stopWordsFilename));
            int numStopWords = stopWordsFile.nextInt();

            stopWords = new String[numStopWords];

            for (int i = 0; i < numStopWords; i++)
                stopWords[i] = stopWordsFile.next();

            stopWordsFile.close();
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        for(String s : stopWords)
            System.out.println(s);


        return stopWords;
    }

    public static void removeStopWords(String textFilename, String[] stopWords)
    {
        String word;

        try
        {
            Scanner textFile = new Scanner(new File(textFilename));
            textFile.useDelimiter(Pattern.compile("[ \n\r\t,.;:?!'\"]+"));

            PrintWriter outFile = new PrintWriter(new File(RESULT_FNAME));

            while (textFile.hasNext())
            {
                word = textFile.next();

                if (isStopWord(word, stopWords))
                    System.out.print(word + " ");
                else
                    outFile.print(word + " ");
            }

            System.out.println("Output File " + RESULT_FNAME);
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

    }

    public static void main(String[] arg)
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Input StopWord File: ");
        String[] stopWords = readStopWords(keyboard.next());

        System.out.print("Input file from which stopword to be removed: ");
        removeStopWords(keyboard.next(), stopWords);

    }
}

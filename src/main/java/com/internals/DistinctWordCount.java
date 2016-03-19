package com.internals;


import com.sun.corba.se.impl.orbutil.ObjectWriter;

import java.io.*;
import java.util.*;


public class DistinctWordCount {

    private static final String RESULT_FNAME = "WordCountOutput.txt";

    public static void main(String[] args) throws IOException {

        String fileName = getFileNameFromUser();
        File file = new File(fileName);
        String[] words = readFile(file);
        Map<String, Integer> wordCount = getWordCountForDocument(words);
        String toSave = convertToString(wordCount);
        saveInOutputFile(toSave, RESULT_FNAME);
    }

    public static String convertToString(Map<String, Integer> wordCount) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String entry: wordCount.keySet()){
            stringBuilder.append(entry+" "+wordCount.get(entry)+"\n");
        }
        return stringBuilder.toString();
    }

    public static void saveInOutputFile(String toSave, String RESULT_FNAME) throws IOException {
        PrintWriter out = new PrintWriter(RESULT_FNAME);
        out.print(toSave);
        out.close();
    }

    private static Map<String, Integer> getWordCountForDocument(String[] words) {

        Map<String, Integer> wordCount = new HashMap<String, Integer>();

        for (String word : words) {
            Integer freq = wordCount.get(word);
            wordCount.put(word, (freq == null) ? 1 : freq + 1);
        }

        return wordCount;
    }

    private static String[] readFile(File file) throws FileNotFoundException {

        String[] words = null;
        String line;

        BufferedReader br = new BufferedReader(new FileReader(file));

        try {
            while((line = br.readLine()) != null){
                line = line.toLowerCase();
                words = line.split("\\s+");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return words;
    }

    private static String getFileNameFromUser() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the file name to process:");
        return br.readLine();
    }
}

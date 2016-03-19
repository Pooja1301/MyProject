package com.internals;


import java.io.*;

public class RemoveWordsBelowThreshold {

    private static final String RESULT_FNAME = "RemoveWordsBelowThresholdOutput.txt";
    private static final int THRESHOLD = 20;

    public static void main(String[] args) throws IOException {

        String fileName = getFileNameFromUser();
        File file = new File(fileName);
        String wordsAndCount = readFile(file);
        String afterRemovingWords = getEntriesAboveThreshold(wordsAndCount);
        saveInOutputFile(afterRemovingWords);
    }

    private static void saveInOutputFile(String afterRemovingWords) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(RESULT_FNAME);
        out.print(afterRemovingWords);
        out.close();

    }

    private static String getEntriesAboveThreshold(String wordsAndCount) throws IOException {

        String word;
        int count;
        String line;

        BufferedReader br = new BufferedReader(new StringReader(wordsAndCount));

        StringBuilder stringBuilder = new StringBuilder();

        while ((line = br.readLine()) != null){
            String splitLine[];
            splitLine = line.split(" ");
            word = splitLine[0];
            count = Integer.parseInt(splitLine[1]);
            if(count >= THRESHOLD)
                stringBuilder.append(word+" "+count+"\n");
        }

        return stringBuilder.toString();
    }

    public static String readFile(File file) throws FileNotFoundException {

        String line;

        BufferedReader br = new BufferedReader(new FileReader(file));

        StringBuilder stringBuilder = new StringBuilder();
        try {
            while((line = br.readLine()) != null){
                line = line.toLowerCase();
                stringBuilder.append(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return stringBuilder.toString();
    }


    public static String getFileNameFromUser() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the file name to process:");
        return br.readLine();
    }

}



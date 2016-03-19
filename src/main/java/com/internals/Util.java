package com.internals;

import java.io.*;

public class Util {
    public static String readFile(File file) throws FileNotFoundException {

        String line;

        BufferedReader br = new BufferedReader(new FileReader(file));

        StringBuilder stringBuilder = new StringBuilder();
        try {
            while((line = br.readLine()) != null){
                line = line.toLowerCase();
                stringBuilder.append(line).append("\n");
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

    public static void saveInOutputFile(String afterRemovingWords, String RESULT_FNAME) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(RESULT_FNAME);
        out.print(afterRemovingWords);
        out.close();

    }
}

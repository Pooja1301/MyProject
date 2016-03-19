package com.internals;

import net.sf.extjwnl.JWNLException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RootWordGenerater {

    private static Map<String, Integer> map;
    private static final String STEMMMER_OUTPUT_FILE = "StemmerOutputFile.txt";

    public static void main(String[] args) throws JWNLException, IOException {
        String fileName = RemoveWordsBelowThreshold.getFileNameFromUser();
        map = new HashMap<String, Integer>();
        Stemmer stemmer = new Stemmer();
        String fileData = RemoveWordsBelowThreshold.readFile(new File(fileName));
        String[] lines = fileData.split("\n");
        for(String string : lines){

            String word = string.split(" ")[0];
            int frequency = Integer.parseInt(string.split(" ")[1]);
            String rootWord = stemmer.StemWordWithWordNet(word);
            if(rootWord==null){
                incrementFrequency(word, frequency);
            }else{
                incrementFrequency(rootWord,frequency);
            }
        }

        String finalString = DistinctWordCount.convertToString(map);
        DistinctWordCount.saveInOutputFile(finalString,STEMMMER_OUTPUT_FILE);

    }

    private static void incrementFrequency(String word, int frequency) {
        Integer oldFrequency = map.get(word);
        if(oldFrequency==null){
            map.put(word,frequency);
        }else{
            map.put(word,oldFrequency+frequency);
        }
    }
}

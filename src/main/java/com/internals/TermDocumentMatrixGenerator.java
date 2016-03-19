package com.internals;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.sf.extjwnl.JWNLException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TermDocumentMatrixGenerator {
    private static Stemmer stemmer;
    private static String term_document = "TermDocument.mat";

    public static void main(String[] args) throws IOException, JWNLException {
        stemmer = new Stemmer();
        String stemmeredData = Util.readFile(new File("StemmerOutputFile.txt"));
        Map<String, Integer> frequencyMap = getFrequencyMap(stemmeredData);

        String originalString = Util.readFile(new File("input.txt"));
        List<SampleData> sampleDataList = getSampleDataList(originalString);
        PostFrequencyMatrix postFrequencyMatrix = getPostFrequencyMatrix(frequencyMap,sampleDataList);
        Util.saveInOutputFile(postFrequencyMatrix.toString(),term_document);
    }

    private static PostFrequencyMatrix getPostFrequencyMatrix(Map<String, Integer> frequencyMap, List<SampleData> sampleDataList) {
        List<String> words = new ArrayList<String>(frequencyMap.keySet());
        List<String> posts = new ArrayList<String>();
        for(SampleData sampleDate : sampleDataList){
            posts.add(sampleDate.getpostorcomment());
        }

        byte[][] frequencyMatrix = new byte[words.size()][posts.size()];
        for(int i=0;i<words.size();i++){
            for(int j=0;j<posts.size();j++){
                if(contains(words.get(i),posts.get(j))){
                    frequencyMatrix[i][j] = 1;
                }else {
                    frequencyMatrix[i][j] = 0;
                }
            }
        }
        return new PostFrequencyMatrix(posts,words,frequencyMatrix);
    }

    private static boolean contains(String s, String s1) {
        String lowerCasedPost = s1.toLowerCase();
        String[] wordsOfPost = lowerCasedPost.split(" ");
        for(String wordOfPost : wordsOfPost){
            String finalWordOfPost = stemmer.StemWordWithWordNet(wordOfPost);
            if(finalWordOfPost==null){
                finalWordOfPost = wordOfPost;
            }
            if(wordOfPost.equals(s)){
                return true;
            }
        }
        return false;
    }

    private static List<SampleData> getSampleDataList(String originalString) throws IOException {
        String[] dataFromDifferentSource = originalString.split("\n\\]\\[\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < dataFromDifferentSource.length; i++) {
            String string = dataFromDifferentSource[i];
            stringBuilder.append(string);
            if(i+1!=dataFromDifferentSource.length){
                stringBuilder.append(",");
            }
        }
        Gson gson = new GsonBuilder().create();
        return Arrays.asList(gson.fromJson(stringBuilder.toString(), SampleData[].class));
    }

    private static Map<String, Integer> getFrequencyMap(String stemmeredData) {
        String[] lines = stemmeredData.split("\n");
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(String string : lines){
            String word = string.split(" ")[0];
            int frequency = Integer.parseInt(string.split(" ")[1]);
            map.put(word,frequency);
        }
        return map;
    }

    private static class PostFrequencyMatrix{
        final List<String> posts;
        final List<String> words;
        final byte[][] frequencyMatrix;

        private PostFrequencyMatrix(List<String> posts, List<String> words, byte[][] frequencyMatrix) {
            this.posts = posts;
            this.words = words;
            this.frequencyMatrix = frequencyMatrix;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0;i<frequencyMatrix.length;i++){
                for(int j=0;j<frequencyMatrix[0].length;j++){
                    stringBuilder.append(frequencyMatrix[i][j]);
                    if(j+1!=frequencyMatrix[0].length){
                        stringBuilder.append(" ");
                    }
                }
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }
}

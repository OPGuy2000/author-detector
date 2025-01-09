import java.util.*;

public class AuthorDetector {
    public static Map<String, Integer> getUnigramFrequency(String text) {
        Map<String, Integer> unigramMap = new HashMap<>();
        String[] words = text.split("\\s+");
        for (String word : words) {
            unigramMap.put(word, unigramMap.getOrDefault(word, 0) + 1);
        }
        return unigramMap;
    }

    public static Map<String, Integer> getBigramFrequency(String text) {
        Map<String, Integer> bigramMap = new HashMap<>();
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length - 1; i++) {
            String bigram = words[i] + " " + words[i + 1];
            bigramMap.put(bigram, bigramMap.getOrDefault(bigram, 0) + 1);
        }
        return bigramMap;
    }

    public static Map<String, Integer> getTrigramFrequency(String text) {
        Map<String, Integer> trigramMap = new HashMap<>();
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length - 2; i++) {
            String trigram = words[i] + " " + words[i + 1] + " " + words[i + 2];
            trigramMap.put(trigram, trigramMap.getOrDefault(trigram, 0) + 1);
        }
        return trigramMap;
    }
}

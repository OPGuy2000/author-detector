import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args){
        String[] trainingDataFiles = {"J.Brodkin.txt", "K.Orland.txt", "J.Ouellette.txt", "J.Timmer.txt", "A.Cunningham.txt"};
        String[] testDataFiles = {"test1.txt", "test2.txt", "test3.txt", "test4.txt", "test5.txt"};


        ArrayList<Map<String, Float>> scoreArr = getScoreArr(trainingDataFiles);

        float[] scores = new float[5];
        
        String testFile = readFile("TestData/" + testDataFiles[2]);
        String[] words = testFile.toLowerCase().split("\\s+");
        for (int i = 0; i < 5; i++) {
            Map<String, Float> scoreMap = scoreArr.get(i);

            
            for (int n = 0; n < words.length; n++) {
                if (scoreMap.containsKey(words[n])) scores[i] += scoreMap.get(words[n]);
            }
        }

        for (int i = 0; i < scores.length; i++) {
            System.out.println(scores[i]);
        }

    }

    public static ArrayList<Map<String, Float>> getScoreArr(String[] trainingDataFiles) {
        ArrayList<Map<String, Integer>> frequencyArr = new ArrayList<Map<String, Integer>>();
        
        for (String file : trainingDataFiles) {
            String data = readFile("TrainingData/" + file);
            frequencyArr.add(getWordFrequencies(data));


        }
        System.out.println(frequencyArr.get(3));

        ArrayList<Map<String, Float>> scoreArr = new ArrayList<Map<String, Float>>();
        
        Integer[] authorWordArr = new Integer[5];
        for (int i = 0; i < 5; i++) {
            authorWordArr[i] = getTotalinAuthor(frequencyArr.get(i));
        }

        for (Map<String, Integer> frequencyMap : frequencyArr) {
            Map<String, Float> scoreMap = new HashMap<>();
            
            for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
                String word = entry.getKey();
                scoreMap.put(word, calculateScore(frequencyMap.get(word), getTotalinAuthor(frequencyMap), getTotalinAll(word, frequencyArr)));
            }

            scoreArr.add(scoreMap);
        }

        return scoreArr;
    } 

    public static int getTotalinAuthor(Map<String, Integer> frequencyMap) {
        int total = 0;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            total += entry.getValue();
        }

        return total;
    }

    public static int getTotalinAll(String word, ArrayList<Map<String, Integer>> frequencyArr) {
        int total = 0;

        for (Map<String, Integer> frequencyMap : frequencyArr) {
            if (frequencyMap.containsKey(word)) {
                total += frequencyMap.get(word);
            }
        }

        return total;
    }

    public static float calculateScore(int freq, int totalInAuthor, int totalInAll) {
        return ((float) freq / totalInAuthor);
    }

    public static Map<String, Integer> getWordFrequencies(String inputString) {
        // Check for null input
        if (inputString == null || inputString.isEmpty()) {
            return new HashMap<>();
        }

        // Convert to lowercase and split words
        String[] words = inputString.toLowerCase().split("\\s+");

        // Create and populate the frequency map
        Map<String, Integer> frequencyMap = new HashMap<>();
        
        for (String word : words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }

        return frequencyMap;
    }

   public static String readFile(String filePath) {
    StringBuilder content = new StringBuilder();
    try {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            
            // Replace hyphens with spaces and filter valid characters
            for (char c : line.replace('-', ' ').toCharArray()) {
                if (Character.isLetter(c) || Character.isSpaceChar(c)) {
                    content.append(c);
                }
            }
            // Add a space after each line to ensure words are separated
            content.append(" ");
        }
        
        scanner.close();
    } catch (FileNotFoundException e) {
        System.out.println("File not found: " + filePath);
        return "";
    }
    
    return content.toString().trim();
}

}   
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AuthorDetectorMain {
    public static void main(String[] args) {

        // Sample training data (replace with actual file reading)
        Map<String, Map<String, Integer>> authorUnigrams = new HashMap<>();
        Map<String, Map<String, Integer>> authorBigrams = new HashMap<>();
        Map<String, Map<String, Integer>> authorTrigrams = new HashMap<>();

        // Example authors
        String[] authors = {"J Brodkin", "K Orland", "J Oullette", "J Timmer", "A Cunningham"};

        String[] trainingTexts = {"J.Brodkin.txt", "K.Orland.txt", "J.Ouellette.txt", "J.Timmer.txt", "A.Cunningham.txt"};
        for (int i = 0; i < trainingTexts.length; i++) {
            trainingTexts[i] = "TrainingData/" + trainingTexts[i];
        }

        String[] testTexts = {"test1.txt", "test2.txt", "test3.txt", "test4.txt", "test5.txt", "test6.txt"};
        for (int i = 0; i < testTexts.length; i++) {
            testTexts[i] = "TestData/" + testTexts[i];
        }

        for (int i = 0; i < authors.length; i++) {
            authorUnigrams.put(authors[i], AuthorDetector.getUnigramFrequency(readFile(trainingTexts[i])));
            authorBigrams.put(authors[i], AuthorDetector.getBigramFrequency(readFile(trainingTexts[i])));
            authorTrigrams.put(authors[i], AuthorDetector.getTrigramFrequency(readFile(trainingTexts[i])));
        }

        // Input text
        for (String inputText : testTexts) {

            Map<String, Integer> inputUnigrams = AuthorDetector.getUnigramFrequency(readFile(inputText));
            Map<String, Integer> inputBigrams = AuthorDetector.getBigramFrequency(readFile(inputText));
            Map<String, Integer> inputTrigrams = AuthorDetector.getTrigramFrequency(readFile(inputText));

            String bestAuthor = null;
            double highestScore = 0;

            double totalScore = 0;

            System.out.println(inputText);

            for (String author : authors) {
                double unigramScore = SimilarityCalculator.calculateSimilarity(inputUnigrams, authorUnigrams.get(author));
                double bigramScore = SimilarityCalculator.calculateSimilarity(inputBigrams, authorBigrams.get(author));
                double trigramScore = SimilarityCalculator.calculateSimilarity(inputTrigrams, authorTrigrams.get(author));

                totalScore = (unigramScore + bigramScore + trigramScore) ;

                System.out.println(author);
                System.out.println(unigramScore + "\tUnigram");
                System.out.println(bigramScore + "\tBigram");
                System.out.println(trigramScore + "\tTrigram");
                System.out.println(totalScore + "\tTotal Score\n\n");


                if (totalScore > highestScore) {
                    highestScore = totalScore;
                    bestAuthor = author;
                }
            }

            System.out.println("The detected author for " + inputText + " is: " + bestAuthor);
            System.out.println("Total score of " + totalScore + "\n");

            System.out.println("=============================================");
        }
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

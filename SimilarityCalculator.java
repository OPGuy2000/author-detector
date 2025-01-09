import java.util.*;

public class SimilarityCalculator {
    public static double calculateSimilarity(Map<String, Integer> map1, Map<String, Integer> map2) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        for (String key : map1.keySet()) {
            dotProduct += map1.getOrDefault(key, 0) * map2.getOrDefault(key, 0);
            magnitude1 += Math.pow(map1.get(key), 2);
        }

        for (int value : map2.values()) {
            magnitude2 += Math.pow(value, 2);
        }

        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);

        if (magnitude1 == 0 || magnitude2 == 0) return 0.0;
        return dotProduct / (magnitude1 * magnitude2);
    }
}

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TermFrequency {

    private static String readFile(final String pathToFile) {
        try {
            return Files.readString(Paths.get(pathToFile), StandardCharsets.UTF_8);
        } catch (final IOException ex) {
            return null;
        }
    }

    private static String cleanText(final String text) {
        return text.replaceAll("[\\W_]+", " ");
    }

    private static String removeSingleCharWords(final String text) {
        return text.replaceAll("\\b\\w{1}\\b", "");
    }

    private static String toLowerCase(final String text) {
        return text.toLowerCase();
    }

    private static String[] splitTextInWords(final String text) {
        return text.split(" ");
    }

    private static String[] removeStopWords(final String[] words, final String[] stopWords) {
        final Set<String> stopWordsSet = Arrays.stream(stopWords).collect(Collectors.toSet());
        stopWordsSet.add("");
        final List<String> filteredWords = Arrays.stream(words).filter(word -> !stopWordsSet.contains(word))
                .collect(Collectors.toList());
        return filteredWords.toArray(new String[0]);
    }
    
    private static HashMap<String, Integer> countWordFrequency(final String[] words) {
        final HashMap<String, Integer> wordsFreqMap = new HashMap<>();

        for (String word : words) {
            if (!wordsFreqMap.containsKey(word))
                wordsFreqMap.put(word, 1);
            else
                wordsFreqMap.replace(word, wordsFreqMap.get(word) + 1);
        }

        return wordsFreqMap;
    }

    private static <T, U> LinkedHashMap<T, U> sortHashMap(final HashMap<T, U> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    private static <T, U> LinkedHashMap<T, U> sliceHashMap(final LinkedHashMap<T, U> map, final Integer maxSize) {
        return map.entrySet()
                .stream()
                .limit(maxSize)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    private static <T, U> String hashMapToString(final LinkedHashMap<T, U> map) {
        final StringBuilder sb = new StringBuilder();
        map.forEach((word, frequency) -> sb.append(word + "  -  " + frequency + "\n"));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(hashMapToString(sliceHashMap(sortHashMap(countWordFrequency(
                removeStopWords(
                        splitTextInWords(toLowerCase(removeSingleCharWords(
                                cleanText(readFile(args[0]))))),
                        splitTextInWords(toLowerCase(
                                cleanText(readFile("stop_words.txt"))))))),
                Integer.parseInt(args[1]))));
    }
}
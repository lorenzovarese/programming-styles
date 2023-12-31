// Kick-Forward Style

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TermFrequency {

    private static void readFile(final String pathToFile, Consumer<String> f) {
        try {
            f.accept(Files.readString(Paths.get(pathToFile), StandardCharsets.UTF_8));
        } catch (final IOException ex) {
            return;
        }
    }

    private static void cleanText(final String text, Consumer<String> f) {
        f.accept(text.replaceAll("[\\W_]+", " "));
    }

    private static void removeSingleCharWords(final String text, Consumer<String> f) {
        f.accept(text.replaceAll("\\b\\w{1}\\b", ""));
    }

    private static void toLowerCase(final String text, Consumer<String> f) {
        f.accept(text.toLowerCase());
    }

    private static void splitTextInWords(final String text, Consumer<String[]> f) {
        f.accept(text.split(" "));
    }

    private static void removeStopWords(final String[] words, final String[] stopWords, Consumer<String[]> f) {
        final Set<String> stopWordsSet = Arrays.stream(stopWords).collect(Collectors.toSet());
        stopWordsSet.add("");
        final List<String> filteredWords = Arrays.stream(words).filter(word -> !stopWordsSet.contains(word))
                .collect(Collectors.toList());
        f.accept(filteredWords.toArray(new String[0]));
    }

    private static void countWordFrequency(final String[] words, Consumer<HashMap<String, Integer>> f) {
        final HashMap<String, Integer> wordsFreqMap = new HashMap<>();

        for (String word : words) {
            if (!wordsFreqMap.containsKey(word))
                wordsFreqMap.put(word, 1);
            else
                wordsFreqMap.replace(word, wordsFreqMap.get(word) + 1);
        }

        f.accept(wordsFreqMap);
    }

    private static <R, S> void sortHashMap(final HashMap<R, S> map, Consumer<LinkedHashMap<R, S>> f) {
        f.accept(map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new)));
    }

    private static <R, S> void sliceHashMap(final LinkedHashMap<R, S> map, final Integer maxSize,
            Consumer<LinkedHashMap<R, S>> f) {
        f.accept(map.entrySet()
                .stream()
                .limit(maxSize)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new)));
    }

    private static <R, S> void hashMapToString(final LinkedHashMap<R, S> map, Consumer<String> f) {
        final StringBuilder sb = new StringBuilder();
        map.forEach((word, frequency) -> sb.append(word + "  -  " + frequency + "\n"));
        f.accept(sb.toString());
    }

    public static void main(String[] args) {
        //TODO: remove stopwords
        readFile(args[0], text -> cleanText(text, cleanedText -> removeSingleCharWords(cleanedText,
                noSingleCharText -> toLowerCase(noSingleCharText, lowerCaseText -> splitTextInWords(lowerCaseText,
                        words -> countWordFrequency(words,
                                wordsFreq -> sortHashMap(wordsFreq,
                                        sortedWordsFreq -> sliceHashMap(sortedWordsFreq, Integer.parseInt(args[1]),
                                                limitWordsFreq -> hashMapToString(limitWordsFreq,
                                                        stringResult -> System.out.println(stringResult))))))))));
    }
}
// Quarantine Style

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TermFrequency {

    public static class TFQuarantine<T> {
        private Supplier<T> funcToRun;

        public TFQuarantine(Supplier<T> funcToRun) {
            this.funcToRun = funcToRun;
        }

        public <R> TFQuarantine<R> map(Function<? super T, ? extends R> f) {
            return new TFQuarantine<>(() -> f.apply(funcToRun.get()));
        }

        public <R> TFQuarantine<R> bind(Function<? super T, TFQuarantine<R>> f) {
            return new TFQuarantine<>(() -> f.apply(funcToRun.get()).run());
        }

        public T run() {
            return funcToRun.get();
        }
    }

    private static TFQuarantine<String> getInputPath(String path) {
        // In this solution accessing args is considered I/O
        return new TFQuarantine<>(() -> path);
    }

    private static TFQuarantine<String> getStopWordsPath() {
        return new TFQuarantine<String>(() -> "stop_words.txt");
    }

    private static TFQuarantine<String> readFile(final String pathToFile) {
        return new TFQuarantine<String>(() -> {
            try {
                return Files.readString(Paths.get(pathToFile), StandardCharsets.UTF_8);
            } catch (final IOException ex) {
                return null;
            }
        });
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

    private static TFQuarantine<String[]> removeStopWords(final String[] words){
        return new TFQuarantine<>(() -> getStopWordsPath()
            .bind(TermFrequency::readFile)
            .map(TermFrequency::cleanText)
            .map(TermFrequency::removeSingleCharWords)
            .map(TermFrequency::toLowerCase)
            .map(TermFrequency::splitTextInWords)
            .map(stopWords -> Arrays.stream(words)
                .filter(word -> !Arrays.asList(stopWords).contains(word))
                .toArray(String[]::new)).run());
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

    private static <T, U> Function<LinkedHashMap<T, U>, LinkedHashMap<T, U>> sliceHashMap(final Integer maxSize) {
        return (final LinkedHashMap<T, U> map) -> map.entrySet()
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

    private static TFQuarantine<String> printResult(final String result) {
        System.out.println(result);
        return new TFQuarantine<String>(() -> result);
    }

    public static void main(String[] args) {

        TFQuarantine<String> quarantine = getInputPath(args[0])
                .bind(TermFrequency::readFile)
                .map(TermFrequency::cleanText)
                .map(TermFrequency::removeSingleCharWords)
                .map(TermFrequency::toLowerCase)
                .map(TermFrequency::splitTextInWords)
                .bind(TermFrequency::removeStopWords)
                .map(TermFrequency::countWordFrequency)
                .map(TermFrequency::sortHashMap)
                .map(TermFrequency.sliceHashMap(Integer.parseInt(args[1])))
                .map(TermFrequency::hashMapToString)
                .bind(TermFrequency::printResult);

        quarantine.run();
    }
}
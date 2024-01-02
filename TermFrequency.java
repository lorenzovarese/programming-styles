// TheOne Style

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TermFrequency {

    public static class TFTheOne<T> {
        private T _value;

        public TFTheOne(T value) {
            this._value = value;
        }

        public <S> TFTheOne<S> bind(Function<T, TFTheOne<S>> f) {
            return f.apply(this._value);
        }

        // Method to print the value of TFTheOne object
        public TFTheOne<T> printTFTheOne() {
            if (_value instanceof Collection) {
                ((Collection<?>) _value).forEach(System.out::println);
            } else if (_value instanceof Map) {
                ((Map<?, ?>) _value).forEach((key, value) -> System.out.println(key + " -> " + value));
            } else if (_value != null && _value.getClass().isArray()) {
                for (int i = 0; i < Array.getLength(_value); i++) {
                    System.out.println(Array.get(_value, i));
                }
            } else {
                System.out.println(_value);
            }
            return this; // Returning the same object for method chaining
        }
    }

    private static TFTheOne<String> readFile(final String pathToFile) {
        try {
            String text = Files.readString(Paths.get(pathToFile), StandardCharsets.UTF_8);
            return new TFTheOne<String>(text);
        } catch (final IOException ex) {
            return null;
        }
    }

    private static TFTheOne<String> cleanText(final String text) {
        return new TFTheOne<String>(text.replaceAll("[\\W_]+", " "));
    }

    private static TFTheOne<String> removeSingleCharWords(final String text) {
        return new TFTheOne<String>(text.replaceAll("\\b\\w{1}\\b", ""));
    }

    private static TFTheOne<String> toLowerCase(final String text) {
        return new TFTheOne<String>(text.toLowerCase());
    }

    private static TFTheOne<String[]> splitTextInWords(final String text) {
        return new TFTheOne<String[]>(text.split(" "));
    }

    private static TFTheOne<String[]> removeStopWords(final String[] words) {
        return new TFTheOne<String>("stop_words.txt")
                .bind(TermFrequency::readFile)
                .bind(TermFrequency::cleanText)
                .bind(TermFrequency::removeSingleCharWords)
                .bind(TermFrequency::toLowerCase)
                .bind(TermFrequency::splitTextInWords)
                .bind((String[] stopWords) -> {
                    final Set<String> stopWordsSet = Arrays.stream(stopWords).collect(Collectors.toSet());
                    stopWordsSet.add("");
                    final List<String> filteredWords = Arrays.stream(words).filter(word -> !stopWordsSet.contains(word))
                            .collect(Collectors.toList());
                    return new TFTheOne<String[]>(filteredWords.toArray(new String[0]));
                });
    }

    private static TFTheOne<HashMap<String, Integer>> countWordFrequency(final String[] words) {
        final HashMap<String, Integer> wordsFreqMap = new HashMap<>();

        for (String word : words) {
            if (!wordsFreqMap.containsKey(word))
                wordsFreqMap.put(word, 1);
            else
                wordsFreqMap.replace(word, wordsFreqMap.get(word) + 1);
        }

        return new TFTheOne<HashMap<String, Integer>>(wordsFreqMap);
    }

    private static <T, U> TFTheOne<LinkedHashMap<T, U>> sortHashMap(final HashMap<T, U> map) {
        return new TFTheOne<LinkedHashMap<T, U>>(map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new)));
    }

    private static <T, U> Function<LinkedHashMap<T, U>, TFTheOne<LinkedHashMap<T, U>>> sliceHashMap(
            final Integer maxSize) {
        return (final LinkedHashMap<T, U> map) -> new TFTheOne<LinkedHashMap<T, U>>(map.entrySet()
                .stream()
                .limit(maxSize)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new)));
    }

    private static <T, U> TFTheOne<String> hashMapToString(final LinkedHashMap<T, U> map) {
        final StringBuilder sb = new StringBuilder();
        map.forEach((word, frequency) -> sb.append(word + "  -  " + frequency + "\n"));
        return new TFTheOne<String>(sb.toString());
    }

    public static void main(String[] args) {
        new TFTheOne<>(args[0])
                .bind(TermFrequency::readFile)
                .bind(TermFrequency::cleanText)
                .bind(TermFrequency::removeSingleCharWords)
                .bind(TermFrequency::toLowerCase)
                .bind(TermFrequency::splitTextInWords)
                .bind(TermFrequency::removeStopWords)
                .bind(TermFrequency::countWordFrequency)
                .bind(TermFrequency::sortHashMap)
                .bind(TermFrequency.sliceHashMap(Integer.parseInt(args[1])))
                .bind(TermFrequency::hashMapToString)
                .printTFTheOne();
    }
}
// Kick-Forward Style

const fs = require("fs");
const { argv } = require("process");

function readFile(pathToFile, func) {
	func(fs.readFileSync(pathToFile, "utf8"));
}

function cleanText(text, func) {
	func(text.replace(/[\W_]+/g, " "));
}

function removeSingleCharWords(text, func) {
	func(text.replace(/\b\w{1}\b/g, ""));
}

function toLowerText(text, func) {
	func(text.toLowerCase());
}

function createWordsArray(text, func) {
	func(text.split(" "));
}

function removeStopWords(words, stopWords, func) {
	func(words.filter((e) => !stopWords.includes(e)));
}

function wordsFrequency(words, func) {
	const wordsFreq = new Map();
	words.forEach((word) => {
		wordsFreq.set(word, (wordsFreq.get(word) ?? 0) + 1);
	});
	func(wordsFreq);
}

function sortMapByFreq(wordsFreqMap, func) {
	const sortedEntries = [...wordsFreqMap.entries()].sort((a, b) => b[1] - a[1]);
	func(new Map(sortedEntries));
}

function limit(maxNumber, words, func) {
	func([...words].slice(0, maxNumber));
}

function wordsFrequencyToString(wordsFreq, func) {
	func(
		wordsFreq.reduce((accumulator, [word, frequency]) => {
			return accumulator + `${word}  -  ${frequency}\n`;
		}, "")
	);
}

readFile(argv[2], (text) =>
	cleanText(text, (cleaned) =>
		removeSingleCharWords(cleaned, (noSingleChar) =>
			toLowerText(noSingleChar, (lowerText) =>
				createWordsArray(lowerText, (words) =>
					readFile("stop_words.txt", (stopWordsText) =>
						cleanText(stopWordsText, (cleanStopWordsText) =>
							removeSingleCharWords(cleanStopWordsText, (noSingleCharStop) =>
								toLowerText(noSingleCharStop, (lowerStopText) =>
									createWordsArray(lowerStopText, (stopWords) =>
										removeStopWords(words, stopWords, (wordsWithoutStopWords) =>
											wordsFrequency(wordsWithoutStopWords, (wordsFreq) =>
												sortMapByFreq(wordsFreq, (sortedWordsFreq) =>
													limit(argv[3], sortedWordsFreq, (limitRes) =>
														wordsFrequencyToString(limitRes, console.log)
													)
												)
											)
										)
									)
								)
							)
						)
					)
				)
			)
		)
	)
);

// Quarantine Style

const fs = require("fs");

class TFQuarantine {
	constructor(func) {
		this._func = func;
	}

	map(f) {
		return new TFQuarantine(() => f(this._func()));
	}

	bind(f) {
		return new TFQuarantine(() => f(this._func()).run());
	}

	run() {
		return this._func();
	}
}

function getInput() {
	return new TFQuarantine(() => process.argv[2]);
}

function getStopWords() {
	return new TFQuarantine(() => "stop_words.txt");
}

function readFile(pathToFile) {
	return new TFQuarantine(() => fs.readFileSync(pathToFile, "utf8"));
}

function cleanText(text) {
	return text.replace(/[\W_]+/g, " ");
}

function removeSingleCharWords(text) {
	return text.replace(/\b\w{1}\b/g, "");
}

function toLowerText(text) {
	return text.toLowerCase();
}

function createWordsArray(text) {
	return text.split(" ");
}

function removeStopWords(words) {
	return getStopWords()
	.bind(readFile)
	.map(cleanText)
	.map(removeSingleCharWords)
	.map(toLowerText)
	.map(createWordsArray)
	.map( (stopWords) =>  words.filter((e) => !stopWords.includes(e)));
}

function wordsFrequency(words) {
	const wordsFreq = new Map();
	words.forEach((word) => {
		wordsFreq.set(word, (wordsFreq.get(word) ?? 0) + 1);
	});
	return wordsFreq;
}

function sortMapByFreq(wordsFreqMap) {
	const sortedEntries = [...wordsFreqMap.entries()].sort((a, b) => b[1] - a[1]);
	return new Map(sortedEntries);
}

function limit(maxNumber) {
	return function (words) {
		return [...words].slice(0, maxNumber);
	};
}

function wordsFrequencyToString(wordsFreq) {
	const wordsArray = Array.from(wordsFreq); // Convert wordsFreq to an array
	return wordsArray.reduce((accumulator, [word, frequency]) => {
		return accumulator + `${word}  -  ${frequency}\n`;
	}, "");
}

function printResult(result) {
	return new TFQuarantine(() => {
		console.log(result);
		return result;
	});
}

const main = getInput()
.bind(readFile)
.map(cleanText)
.map(removeSingleCharWords)
.map(toLowerText)
.map(createWordsArray)
.bind(removeStopWords)
.map(wordsFrequency)
.map(sortMapByFreq)
.map(limit(process.argv[3]))
.map(wordsFrequencyToString)
.bind(printResult);

main.run();

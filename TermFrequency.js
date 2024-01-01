// The One Style

// - Existence of an abstraction to which values can be converted.
// - This abstraction provides operations to (1) wrap around values, so that they become the abstraction; (2) bind itself to functions, to establish sequences of functions.
// - Larger problem is solved as a pipeline of functions bound together.
// - Particularly for The One style, the bind operation simply calls the given function, giving it the value that it holds, and holds on to the returned value.

const fs = require("fs");

class TFTheOne {
	_value;

	constructor(value) {
		this._value = value;
	}

	bind(func) {
		return func(this._value);
	}
}

function readFile(pathToFile) {
	return new TFTheOne(fs.readFileSync(pathToFile, "utf8"));
}

function cleanText(text) {
	return new TFTheOne(text.replace(/[\W_]+/g, " "));
}

function removeSingleCharWords(text) {
	return new TFTheOne(text.replace(/\b\w{1}\b/g, ""));
}

function toLowerText(text) {
	return new TFTheOne(text.toLowerCase());
}

function createWordsArray(text) {
	return new TFTheOne(text.split(" "));
}

function removeStopWords(words) {
	return new TFTheOne("stop_words.txt")
		.bind(readFile)
		.bind(cleanText)
		.bind(toLowerText)
		.bind(createWordsArray)
		.bind(
			(stopWords) => new TFTheOne(words.filter((e) => !stopWords.includes(e)))
		);
}

function wordsFrequency(words) {
	const wordsFreq = new Map();
	words.forEach((word) => {
		wordsFreq.set(word, (wordsFreq.get(word) ?? 0) + 1);
	});
	return new TFTheOne(wordsFreq);
}

function sortMapByFreq(wordsFreqMap) {
	const sortedEntries = [...wordsFreqMap.entries()].sort((a, b) => b[1] - a[1]);
	return new TFTheOne(new Map(sortedEntries));
}

function limit(maxNumber) {
	return function (words) {
		return new TFTheOne([...words].slice(0, maxNumber));
	};
}

function wordsFrequencyToString(wordsFreq) {
	return new TFTheOne(
		wordsFreq.reduce((accumulator, [word, frequency]) => {
			return accumulator + `${word}  -  ${frequency}\n`;
		}, "")
	);
}

new TFTheOne(process.argv[2])
	.bind(readFile)
	.bind(cleanText)
	.bind(removeSingleCharWords)
	.bind(toLowerText)
	.bind(createWordsArray)
	.bind(removeStopWords)
	.bind(wordsFrequency)
	.bind(sortMapByFreq)
	.bind(limit(process.argv[3]))
	.bind(wordsFrequencyToString)
	.bind(console.log);

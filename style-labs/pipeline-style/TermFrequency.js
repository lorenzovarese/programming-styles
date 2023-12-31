// Pipeline Style

const fs = require("fs");

function readFile(pathToFile) {
	return fs.readFileSync(pathToFile, "utf8");
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
	return function (stopWords) {
		return words.filter((e) => !stopWords.includes(e));
	};
}

function wordsFrequency(words) {
	const wordsFreq = new Map();
	words.forEach((word) => {
		wordsFreq.set(word, (wordsFreq.get(word) ?? 0) + 1);
	});
	return wordsFreq;
}

function sortMapByFreq(wordsFreqMap) {
	const sortedEntries = [...wordsFreqMap.entries()].sort(
		(a, b) => (b[1] - a[1])
	);
	return new Map(sortedEntries);
}

function limit(maxNumber) {
	return function (words) {
		return [...words].slice(0, maxNumber);
	};
}

function wordsFrequencyToString(wordsFreq) {
	return wordsFreq.reduce((accumulator, [word, frequency]) => {
		return accumulator + `${word} - ${frequency}\n`;
	}, "");
}

console.log(
	wordsFrequencyToString(
		limit(process.argv[3])(
			sortMapByFreq(
				wordsFrequency(
					removeStopWords(
						createWordsArray(
							toLowerText(
								removeSingleCharWords(cleanText(readFile(process.argv[2])))
							)
						)
					)(
						createWordsArray(toLowerText(cleanText(readFile("stop_words.txt"))))
					)
				)
			)
		)
	)
);

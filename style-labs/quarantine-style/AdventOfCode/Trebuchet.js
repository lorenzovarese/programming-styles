/***
 * Advent Of Code 2023 - 1st December
 *
 *  — Day 1: Trebuchet?! —
 *
 *  Something is wrong with global snow production, and you’ve been selected to take a look.
 * The Elves have even given you a map; on it, they’ve used stars to mark the top fifty locations that are likely to be having problems.
 *
 *  You try to ask why they can’t just use a weather machine (“not powerful enough”) and where they’re even sending you (“the sky”)
 * and why your map looks mostly blank (“you sure ask a lot of questions”) and hang on did you just say the sky
 * (“of course, where do you think snow comes from”) when you realize that the Elves are already loading you into a trebuchet
 * (“please hold still, we need to strap you in”).
 *
 *  As they’re making the final adjustments, they discover that their calibration document (your puzzle input) has been amended by
 * a very young Elf who was apparently just excited to show off her art skills. Consequently, the Elves are having trouble reading
 * the values on the document.
 *
 *  The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value
 * that the Elves now need to recover. On each line, the calibration value can be found by combining the first digit and the last digit
 * (in that order) to form a single two-digit number.
 *
 *  For example:
 *
 *  1abc2
 *  pqr3stu8vwx
 *  a1b2c3d4e5f
 *  treb7uchet
 *
 *  In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.
 *
 *  Consider your entire calibration document. What is the sum of all of the calibration values?
 *
 */

const fs = require("fs");

class TQuarantine {
	_func;

	constructor(func) {
		this._func = func;
	}

	map(func) {
		return new TQuarantine(() => func(this._func()));
	}

	bind(func) {
		return new TQuarantine(() => func(this._func()).run());
	}

	run() {
		return this._func();
	}
}

function getInput() {
	return new TQuarantine(() => process.argv[2]);
}

function readFile(filePath) {
	return new TQuarantine(() => fs.readFileSync(filePath, "utf8"));
}

function toCalibrationRowArray(text) {
	return text.split("\n");
}

function removeEmptyEntry(calibrationArray) {
	return calibrationArray.filter((x) => x.trim().length !== 0);
}

function isCharANumber(char) {
    return !isNaN(+char);
}

function calibrationValueExtractor(calibrationArray) {
	return calibrationArray.map((calibrationEntry) => {
        // ... = Slice the calibration entry in an array of characters
		return [...calibrationEntry].reduce((acc, el) => {
            if(isCharANumber(el)){
                if(acc.length === 0)
                    acc = el + el;
                else 
                    acc = [...acc][0] + el
            }
            return acc;
        }, "");
	});
}

function sumArray(nums) {
	return nums.reduce((acc, curr) => +acc + +curr, 0);
}

function printAndReturn(result) {
	return new TQuarantine(() => {
		console.log(result);
		return result;
	});
}

trebuchet = getInput()
	.bind(readFile)
	.map(toCalibrationRowArray)
	.map(removeEmptyEntry)
	.map(calibrationValueExtractor)
    .map(sumArray)
	.bind(printAndReturn);

trebuchet.run();

nums = [1, 3, 7, 2, -1];
bools = [true, false, false];
funcs = [
	(x) => x.map((el) => el),
	(x) => x.map((el) => 3),
	(x) => x.map((el) => el + 1),
];

// Product (of list of numbers)
console.log(nums.reduce((acc, el) => acc * el, 1));

// Minimum (of list of numbers)
console.log(nums.reduce((acc, el) => (el < acc ? el : acc), nums[0]));

// Maximum (of list of numbers)
console.log(nums.reduce((acc, el) => (el > acc ? el : acc), nums[0]));

// Conjunction (of list of Bollean values)
console.log(bools.reduce((acc, el) => acc && el, true));

// Disjunction (of list of Bollean values)
console.log(bools.reduce((acc, el) => acc || el, false));

// Composition (of list of function values)
console.log(funcs.reduce((acc, el) => el(acc), [1, 2, 3])); //{pipeline: addOne(mapTo3(identity(list)))) }

// Lenght (of list)
console.log(nums.reduce((acc, el) => ++acc, 0));

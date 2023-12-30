// Use named function
function make_multiplier(by) {
	function multilpy(x) {
		return x * by;
	}
	return multilpy;
}

doubler = make_multiplier(2);

console.log(doubler(5)); //10
console.log(doubler(7)); //14

// Use lambda
function make_multiplier(by) {
	return (x) => x * by;
}

doubler = make_multiplier(2);

console.log(doubler(5)); //10
console.log(doubler(7)); //14

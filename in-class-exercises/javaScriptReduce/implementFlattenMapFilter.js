// Implement flatten using reduce
function flatten(list) {
    return list.reduce((acc, el) => {
        if (Array.isArray(el)) acc = acc.concat(flatten(el)); // recursively flatten nested arrays
        else acc.push(el); // add non-array elements to the accumulator
        return acc;
    }, []);
}

res = flatten([1, 2, [3, 4, 5], 6, [7], [8, 9]]);
console.log(res);

// Implement map using reduce
function map2(list, func) {
    return list.reduce((acc, el) => acc.concat(func(el)), []); // apply the function to each element and concatenate the results
}

res = map2(["Hello", "Hi", "Ola"], (e) => e.toLowerCase());
console.log(res);

// Implement filter using reduce
function filter2(list, pred) {
    return list.reduce((acc, el) => (pred(el) ? acc.concat(el) : acc), []); // add elements that satisfy the predicate to the accumulator
}

res = filter2(["Hello", "Hi", "Ola"], (e) => [...e][0] === "H"); // filter elements starting with "H"
console.log(res);

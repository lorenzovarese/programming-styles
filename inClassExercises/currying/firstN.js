function firstN(list, n){
    return list.splice(0, n)
}

console.log(firstN([1,2,3,4,5], 2))

function curriedFistN(n){
    return (list) => list.splice(0,n);
}

console.log(curriedFistN(3)([1,2,3,4,5]))
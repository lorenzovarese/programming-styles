const students = [
	{ name: "Jim", grade: 5 },
	{ name: "Jane", grade: 6 },
];

function extractGrade(student) {
	return student.grade;
}

function map(list, f) {
	const res = new Array();
	list.forEach(element => {
        res.push(f(element))
    });
    return res
}

console.log(map(students, extractGrade))

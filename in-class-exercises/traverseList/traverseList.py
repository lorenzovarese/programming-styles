students = [
    { 'name': "Jim", 'grade': 5 },
    { 'name': "Jane", 'grade': 6 },
]

def extractGrade(student):
    return student['grade']

def map(list, f):
    result = []
    for el in list:
        result.append(f(el))
        
    print(result)  
    
map(students, extractGrade)
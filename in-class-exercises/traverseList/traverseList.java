import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class traverseList {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Jim", 5));
        students.add(new Student("Jane", 6));

        System.out.println(map(students, traverseList::extractGrade));
    }

    public static <T,U> List<U> map(List<T> list, Function<T,U> f){
        final List<U> result = new ArrayList<U>();
        for (T el : list) {
            result.add(f.apply(el));
        }
        return result;
    }

    public static int extractGrade(Student student) {
        return student.getGrade();
    }

    static class Student {
        private String name;
        private int grade;

        public Student(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public int getGrade() {
            return grade;
        }
    }
}

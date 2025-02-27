import java.util.ArrayList;

import java.util.List;


// Custom exception for course full
class CourseFullException extends Exception {
    public CourseFullException(String courseName) {
        super("CourseFullException - The course " + courseName + " is full.");
    }
}

// Custom exception for unmet prerequisites
class PrerequisiteNotMetException extends Exception {
    public PrerequisiteNotMetException(String courseName, String prerequisite) {
        super("PrerequisiteNotMetException - Complete " + prerequisite + " before enrolling in " + courseName + ".");
    }
}

// Course class
class Course {
    private String name;
    private int capacity;
    private List<String> prerequisites;
    private int enrolledStudents;

    public Course(String name, int capacity, List<String> prerequisites) {
        this.name = name;
        this.capacity = capacity;
        this.prerequisites = prerequisites;
        this.enrolledStudents = 0;
    }

    public String getName() {
        return name;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void enroll() throws CourseFullException {
        if (enrolledStudents >= capacity) {
            throw new CourseFullException(name);
        }
        enrolledStudents++;
    }
}

// Student class
class Student {
    private String name;
    private List<String> completedCourses;

    public Student(String name) {
        this.name = name;
        this.completedCourses = new ArrayList<>();
    }

    public void completeCourse(String courseName) {
        completedCourses.add(courseName);
    }

    public boolean hasCompleted(String courseName) {
        return completedCourses.contains(courseName);
    }

    public void enrollInCourse(Course course) throws CourseFullException, PrerequisiteNotMetException {
        for (String prerequisite : course.getPrerequisites()) {
            if (!hasCompleted(prerequisite)) {
                throw new PrerequisiteNotMetException(course.getName(), prerequisite);
            }
        }
        course.enroll();
        System.out.println(name + " successfully enrolled in " + course.getName());
    }
}

// Main class to demonstrate the enrollment system
public class UniversityEnrollmentSystem {
    public static void main(String[] args) {
        // Create courses
        List<String> advancedJavaPrerequisites = new ArrayList<>();
        advancedJavaPrerequisites.add("Core Java");
        Course advancedJava = new Course("Advanced Java", 2, advancedJavaPrerequisites);

        // Create a student
        Student student = new Student("Alice");

        // Try enrolling the student in Advanced Java without completing prerequisites
        try {
            student.enrollInCourse(advancedJava);
        } catch (CourseFullException | PrerequisiteNotMetException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Complete the prerequisite course
        student.completeCourse("Core Java");

        // Try enrolling the student again
        try {
            student.enrollInCourse(advancedJava);
        } catch (CourseFullException | PrerequisiteNotMetException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
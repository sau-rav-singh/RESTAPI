import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import pojo.CourseDetails;

import java.io.File;
import java.util.List;
import java.util.Map;

public class PrintCourses {
    @SneakyThrows
    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/Courses.json");
        CourseDetails courseDetails = objectMapper.readValue(file, CourseDetails.class);
        System.out.println("Instructor: " + courseDetails.getInstructor());
        System.out.println("LinkedIn: " + courseDetails.getLinkedIn());
        System.out.println("Services: " + courseDetails.getServices());
        System.out.println("Expertise: " + courseDetails.getExpertise());
        System.out.println("URL: " + courseDetails.getUrl());

        Map<String, List<CourseDetails.Course>> courses = courseDetails.getCourses();

        courses.forEach((category, courseList) -> {
            System.out.println("Category: " + category);
            courseList.forEach(course -> {
                System.out.println("  Course Title: " + course.getCourseTitle());
                System.out.println("  Price: " + course.getPrice());
            });
        });

    }
}

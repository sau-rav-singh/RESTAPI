import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.DashboardData;

import java.io.File;

public class ComplexJsonPojo {
    public static void main(String[] args) {

        try {
            File jsonFile = new File("src/test/resources/complex.json");
            ObjectMapper objectMapper = new ObjectMapper();
            DashboardData dashboardData = objectMapper.readValue(jsonFile, DashboardData.class);
            System.out.println("Purchase Amount: " + dashboardData.getDashboard().getPurchaseAmount());
            System.out.println("Website: " + dashboardData.getDashboard().getWebsite());
            System.out.println("Courses:");
            for (DashboardData.Course course : dashboardData.getCourses()) {
                System.out.println("Title: " + course.getTitle() + ", Price: " + course.getPrice() + ", Copies: " + course.getCopies());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

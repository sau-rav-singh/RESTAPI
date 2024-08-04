import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.ComplexJson;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ComplexJsonPojoTest {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file =new File("src/test/resources/complex.json");
            ComplexJson.ComplexJsonData complexJsonData = mapper.readValue(file, ComplexJson.ComplexJsonData.class);

            List<ComplexJson.Course> courses = complexJsonData.getCourses();
            System.out.println("Count of all the courses is " + courses.size());

            ComplexJson.Dashboard dashboard = complexJsonData.getDashboard();
            int totalAmount = dashboard.getPurchaseAmount();
            System.out.println("Total Amount: " + totalAmount);

            int totalCalculatedAmount = 0;

            for (ComplexJson.Course course : courses) {
                String title = course.getTitle();
                int price = course.getPrice();
                int copies = course.getCopies();
                totalCalculatedAmount += price * copies;

                System.out.println("Title: " + title + ", Price: " + price);

                if ("RPA".equals(title)) {
                    System.out.println("Copies of RPA sold are " + copies);
                }

                ComplexJson.Sales sales = course.getSales();
                System.out.println("Udemy Sales of " + title + " is " + sales.getUdemy());
                System.out.println("Website Sales of " + title + " is " + sales.getWebsite());

                List<ComplexJson.Review> reviewsList = course.getReviews();
                for (ComplexJson.Review review : reviewsList) {
                    String channel = review.getChannel();
                    int reviewCount = review.getReviewcount();
                    System.out.println("Reviews from " + channel + " is " + reviewCount);
                }
            }

            if (totalCalculatedAmount != totalAmount) {
                System.err.println("Total Sum Test failed");
            } else {
                System.out.println("Total Sum Test passed");
            }

        } catch (IOException e) {
            System.err.println("Failed to read JSON file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}

package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.ComplexJson;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ComplexJsonPojoTest {

    @Test
    public void testComplexJsonParsing() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Read the JSON file
            File file = new File("src/test/resources/complex.json");
            ComplexJson.ComplexJsonData complexJsonData = mapper.readValue(file, ComplexJson.ComplexJsonData.class);

            // Fetch courses and dashboard data
            List<ComplexJson.Course> courses = complexJsonData.getCourses();
            ComplexJson.Dashboard dashboard = complexJsonData.getDashboard();
            int totalAmount = dashboard.getPurchaseAmount();

            int totalCalculatedAmount = 0;

            // Loop through each course to calculate the total amount and print details
            for (ComplexJson.Course course : courses) {
                String title = course.getTitle();
                int price = course.getPrice();
                int copies = course.getCopies();
                totalCalculatedAmount += price * copies;

                // Print details (optional for debug, not necessary for assertions)
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

            // Use assertions to validate results
            Assert.assertEquals(totalCalculatedAmount, totalAmount, "Total Sum Test failed");

        } catch (IOException e) {
            // Handle exceptions using assertions
            Assert.fail("Failed to read JSON file: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("An unexpected error occurred: " + e.getMessage());
        }
    }
}

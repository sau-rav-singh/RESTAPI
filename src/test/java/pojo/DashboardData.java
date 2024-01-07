package pojo;

import lombok.Data;
import java.util.List;

@Data
public class DashboardData {
    private Dashboard dashboard;
    private List<Course> courses;

    @Data
    public static class Dashboard {
        private int purchaseAmount;
        private String website;
    }

    @Data
    public static class Course {
        private String title;
        private int price;
        private int copies;
    }
}

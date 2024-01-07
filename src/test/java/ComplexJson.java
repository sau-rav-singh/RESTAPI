import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ComplexJson {
    public static void main(String[] args) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/resources/complex.json")));
        JSONObject jsonObject = new JSONObject(jsonContent);
        JSONArray coursesArray = jsonObject.getJSONArray("courses");
        System.out.println("Count of Courses is " + coursesArray.length());

        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject courseObject = coursesArray.getJSONObject(i);
            System.out.println("Course: " + courseObject.getString("title") + ", Price: " + courseObject.getInt("price"));
        }

    }
}

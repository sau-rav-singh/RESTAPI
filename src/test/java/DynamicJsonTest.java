import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class DynamicJsonTest {

    @Test
    public void addBook() throws IOException {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        JSONObject jsonObject=new JSONObject(new String(Files.readAllBytes(Paths.get("src/test/resources/AddBook.json"))));
        jsonObject.put("isbn","bcdfr");
        jsonObject.put("aisle","123");

        String addBookResponse=given().header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .when().post("Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();
        JsonPath js=new JsonPath(addBookResponse);
        String bookId=js.getString("ID");
        System.out.println("bookId is "+bookId);
    }
}

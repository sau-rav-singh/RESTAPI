import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BasicTest {
    public static void main(String[] args) throws IOException {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String postResponse = given().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(new String(Files.readAllBytes(Paths.get("src/test/resources/AddPlace.json")))).when().post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        JsonPath js = new JsonPath(postResponse);
        String place_id = js.get("place_id");
        System.out.println("place_id is " + place_id);

        String putResponse = given().queryParam("place_id", place_id).queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\n" + "\"place_id\":\"" + place_id + "\",\n" + "\"address\":\"70 Summer walk, IND\",\n" + "\"key\":\"qaclick123\"\n" + "}").when().put("maps/api/place/update/json").then().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated")).extract().response().asString();

        System.out.println("putResponse is " + putResponse);

        String getResponse = given().queryParam("key", "qaclick123").queryParam("place_id", place_id).when().get("maps/api/place/get/json").then().statusCode(200).extract().response().asString();

        System.out.println(getResponse);

    }
}

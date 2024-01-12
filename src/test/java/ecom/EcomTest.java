package ecom;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class EcomTest {
    String loginToken, loginUserId, productId;

    @Test
    public void loginTest() {
        RequestSpecification loginRequestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("selena@gomez.com");
        loginRequest.setUserPassword("Iamking@000");
        RequestSpecification reqLogin = given().spec(loginRequestSpec).body(loginRequest);
        LoginResponse loginResponseSpec = reqLogin.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
        loginToken = loginResponseSpec.getToken();
        loginUserId = loginResponseSpec.getUserId();
    }

    @Test(dependsOnMethods = "loginTest")
    public void addProduct() {
        RequestSpecification addProductRequestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", loginToken).build();
        RequestSpecification addProduct = given().spec(addProductRequestSpec).param("productName", "Sexy Gomez").param("productAddedBy", loginUserId).param("productCategory", "productCategory").param("productSubCategory", "Baby").param("productPrice", "1").param("productDescription", "HotAsHell").param("productFor", "Men").multiPart("productImage", new File("src/test/java/ecom/SG.png"));
        String addProductResponse = addProduct.when().post("api/ecom/product/add-product").then().extract().asString();
        JsonPath js = new JsonPath(addProductResponse);
        productId = js.getString("productId");
    }

    @Test(dependsOnMethods = "addProduct")
    public void createOrder() {


    }

    @Data
    public static class LoginRequest {
        private String userEmail;
        private String userPassword;
    }

    @Data
    public static class LoginResponse {
        private String token;
        private String userId;
        private String message;
    }
}

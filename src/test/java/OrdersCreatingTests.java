import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.services.praktikum.scooter.qa.Orders;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

@RunWith(Parameterized.class)
public class OrdersCreatingTests {


    private final Orders orders;

    public OrdersCreatingTests(Orders orders) {
        this.orders = orders;
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[][] getTestData() {
        return new Object[][]{{new Orders("Жан Жак", "Тестировалль", "ул.Скворцова 11", "Комсомольская", "89999999999", 3, "2025-04-14", "Тест коммент", new String[]{"BLACK"})}, {new Orders("Винченцо", "Тестини", "ул.Удальцова 8", "Комсомольская", "89999999998", 5, "2025-04-14", "Тестовый коммент", new String[]{"GREY"})}, {new Orders("Хосе", "Тестильо", "ул.Нижегородская 25", "Комсомольская", "89999999997", 7, "2025-04-25", "", new String[]{"BLACK", "GREY"})}, {new Orders("Джон", "Тестоуни", "ул.Ленина 23", "Спортивная", "89999999996", 3, "2025-04-23", "", new String[]{})},};
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с различными данными")
    public void checkCreateOrder() {
        Response response = given().log().all().header("Content-type", "application/json").body(orders).when().post("/api/v1/orders");
        response.then().log().all().assertThat().and().statusCode(201).body("track", Matchers.notNullValue());
    }
}

package api.tests;

import api.dto.*;
import api.services.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User api tests")
@DisplayName("Test user api")
class ApiTests {
    private static ApiService apiService;
    private final static String URL = "https://reqres.in/";

    @BeforeAll
    static void prepare() {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    @Test
    @Story("Validity check user avatar and email")
    void avatarAndEmail() throws IOException {
        Response<UsersDataList> response = apiService.getUsersList().execute();

        assertEquals(200, response.code());

        assert response.body() != null;
        List<UserData> users = response.body().data;

        users.forEach(x -> assertTrue(x.getAvatar().contains(x.getId().toString())));
        assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
    }



    @Test
    @Story("Testing user creation")
    void createUser() throws IOException {
        CreatedUser createdUser = new CreatedUser("morpheus", "leader");
        Call<CreatedUserResponse> call = apiService.createUser(createdUser);
        Response<CreatedUserResponse> response = call.execute();

        assertEquals(201, response.code());

        CreatedUserResponse updatedUser = response.body();
        assertNotNull(updatedUser.getCreatedAt());
        assertNotNull(updatedUser.getId());
    }

    @Test
    @Story("Testing user data update")
    void updateUser() throws IOException {
        CreatedUser createdUser = new CreatedUser("morpheus", "zion resident");
        Call<CreatedUserResponse> call = apiService.updateUser(createdUser);
        Response<CreatedUserResponse> response = call.execute();

        assertEquals(200, response.code());

        CreatedUserResponse updatedUser = response.body();
        assertNotNull(updatedUser.getUpdatedAt());
    }

    @Test
    @Story("User deletion check")
    void deleteUser() throws IOException {
        Response<UserData> response = apiService.deleteUser().execute();

        assertEquals(204, response.code());
    }

    @Test
    @Story("Testing for missing user data")
    void notFoundUser() throws IOException {
        var call = apiService.getNotFoundUser();
        var response = call.execute();

        assertEquals(404, response.code());
    }

    @Test
    @Story("Data sort check")
    void unknownDataSortedByYear() throws IOException {
        Response<UnknownDataList> response = apiService.getUnknownList().execute();

        assertEquals(200, response.code());

        assert response.body() != null;
        List<UnknownData> unknowns = response.body().data;

        List<Integer> years = unknowns.stream().map(UnknownData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());

        assertEquals(years, sortedYears);
    }

    @Nested
    @DisplayName("Test user login")
    @Tag("Login")
    class LoginTest {
        @Test
        @Story("Success login")
        void successLogin() throws IOException {
            String token = "QpwL5tke4Pnpja7X4";
            LoginData loginData = new LoginData("eve.holt@reqres.in", "cityslicka");
            Call<LoginResponse> call = apiService.loginUser(loginData);
            Response<LoginResponse> response = call.execute();

            assertEquals(200, response.code());

            LoginResponse successReg = response.body();

            assertNotNull(successReg.getToken());
            assertEquals(token, successReg.getToken());
        }

        @Test
        @Story("Failed login")
        void failedLogin() throws IOException {
            LoginData loginData = new LoginData("peter@klaven", "");
            Call<LoginResponse> call = apiService.loginUser(loginData);
            Response<LoginResponse> response = call.execute();

            assertEquals(400, response.code());

            LoginResponse failedLogin = response.body();

            assertNull(failedLogin);
        }
    }

    @Nested
    @DisplayName("Test user registration")
    @Tag("Registration")
    class RegistrationTest {
        @Test
        @Story("Success registration")
        void successRegistration() throws IOException {
            Integer id = 4;
            String token = "QpwL5tke4Pnpja7X4";
            LoginData loginData = new LoginData("eve.holt@reqres.in", "pistol");
            Call<LoginResponse> call = apiService.registerUser(loginData);
            Response<LoginResponse> response = call.execute();

            assertEquals(200, response.code());

            LoginResponse successReg = response.body();

            assertNotNull(successReg.getId());
            assertNotNull(successReg.getToken());
            assertEquals(id, successReg.getId());
            assertEquals(token, successReg.getToken());
        }

        @Test
        @Story("Failed registration")
        void failedRegistration() throws IOException {
            LoginData loginData = new LoginData("sydney@fifse", "");
            Call<LoginResponse> call = apiService.registerUser(loginData);
            Response<LoginResponse> response = call.execute();

            assertEquals(400, response.code());

            LoginResponse failedReg = response.body();

            assertNull(failedReg);
        }
    }

}


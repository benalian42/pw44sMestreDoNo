package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class UserControllerTest {
    private static final String API_USER = "/users";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void postUser_whenUserIsValid_receiveCREATED() {
        User user = createValidUser();

        ResponseEntity<Object> response =
                this.restTemplate.postForEntity(API_USER, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void postUser_whenUserIsValid_userSavedToDatabase() {
        User user = createValidUser();

        this.restTemplate.postForEntity(API_USER, user, Object.class);

        assertThat( userRepository.count() ).isEqualTo(1);

    }

    @Test
    public void postUser_whenUserIsValid_passwordIsHashedInDatabase() {
        User user = createValidUser();
        this.restTemplate.postForEntity(API_USER, user, Object.class);
        List<User> users = userRepository.findAll();
        User userDB = users.getFirst();
        assertThat( user.getPassword() ).isNotEqualTo(  userDB.getPassword() );
    }

    @Test
    public void postUser_whenUserHasNullUsername_receiveBadRequest() {
        User user = createValidUser();
        user.setUsername(null);

        ResponseEntity<Object> response =
                this.restTemplate.postForEntity(API_USER, user, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }



    private User createValidUser() {
        return User.builder()
                .username("test-User")
                .displayName("test-Display")
                .password("P4ssword")
                .build();
    }
}

package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.dto.*;
import br.edu.utfpr.pb.pw44s.server.model.Category;
import br.edu.utfpr.pb.pw44s.server.model.Product;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.repository.CategoryRepository;
import br.edu.utfpr.pb.pw44s.server.repository.ProductRepository;
import br.edu.utfpr.pb.pw44s.server.repository.UserRepository;
import br.edu.utfpr.pb.pw44s.server.security.dto.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class BackendFeaturesTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String authToken;
    private User testUser;
    private Category testCategory;
    private Product testProduct;

    @BeforeEach
    public void setup() {
        // Criar ou garantir usuário de teste
        testUser = userRepository.findUserByUsername("auth.user");
        if (testUser == null) {
            testUser = userRepository.save(User.builder()
                    .username("auth.user")
                    .displayName("Auth User")
                    .password(passwordEncoder.encode("P4ssword"))
                    .build());
        }

        // Criar ou garantir categoria
        testCategory = categoryRepository.findAll().stream().findFirst().orElseGet(() ->
                categoryRepository.save(Category.builder().name("Macramê Test").build())
        );

        // Criar ou garantir produto
        testProduct = productRepository.findAll().stream().findFirst().orElseGet(() ->
                productRepository.save(Product.builder()
                        .name("Painel Teste")
                        .description("Descrição do painel de teste")
                        .price(new BigDecimal("150.00"))
                        .imageUrl("https://exemplo.com/imagem.png")
                        .category(testCategory)
                        .build())
        );

        // Realizar login para obter token JWT
        AuthRequestDTO loginRequest = new AuthRequestDTO();
        loginRequest.setUsername("auth.user");
        loginRequest.setPassword("P4ssword");

        ResponseEntity<AuthenticationResponse> loginResponse = restTemplate.postForEntity(
                "/login", loginRequest, AuthenticationResponse.class);

        if (loginResponse.getStatusCode() == HttpStatus.OK && loginResponse.getBody() != null) {
            this.authToken = loginResponse.getBody().getToken();
        }
    }

    // 1. Lista de categorias
    @Test
    public void test1_getCategoriesList_receiveOk() {
        ResponseEntity<List<CategoryDTO>> response = restTemplate.exchange(
                "/categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CategoryDTO>>() {}
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
    }

    // 2. Lista de produtos com URL de imagem
    @Test
    public void test2_getProductsList_receiveOk() {
        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
                "/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDTO>>() {}
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody().getFirst().getImageUrl()).isNotNull();
    }

    // 3. Produto pelo ID
    @Test
    public void test3_getProductById_receiveOk() {
        ResponseEntity<ProductDTO> response = restTemplate.getForEntity(
                "/products/" + testProduct.getId(),
                ProductDTO.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(testProduct.getId());
        assertThat(response.getBody().getName()).isEqualTo(testProduct.getName());
    }

    // 4. Cadastro de Usuário
    @Test
    public void test4_postUser_receiveCreated() {
        UserDTO newUser = UserDTO.builder()
                .username("new.client")
                .displayName("New Client")
                .password("P4ssword")
                .build();

        ResponseEntity<Object> response = restTemplate.postForEntity("/users", newUser, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(userRepository.findUserByUsername("new.client")).isNotNull();
    }

    // 5. Autenticação (Login)
    @Test
    public void test5_postLogin_receiveToken() {
        AuthRequestDTO loginRequest = new AuthRequestDTO();
        loginRequest.setUsername("auth.user");
        loginRequest.setPassword("P4ssword");

        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
                "/login", loginRequest, AuthenticationResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isNotBlank();
        assertThat(response.getBody().getUser().getUsername()).isEqualTo("auth.user");
    }

    // 6. Cadastro de Endereços (do usuário autenticado)
    @Test
    public void test6_postAddress_whenAuthenticated_receiveCreated() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);

        AddressDTO addressDTO = AddressDTO.builder()
                .street("Rua das Palmeiras")
                .number("100")
                .neighborhood("Centro")
                .city("Pato Branco")
                .state("PR")
                .addressNumber("85501-000")
                .build();

        HttpEntity<AddressDTO> request = new HttpEntity<>(addressDTO, headers);

        ResponseEntity<AddressDTO> response = restTemplate.postForEntity(
                "/addresses", request, AddressDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserId()).isEqualTo(testUser.getId());
        assertThat(response.getBody().getStreet()).isEqualTo("Rua das Palmeiras");
    }

    // 7. Cadastro de Pedidos (do usuário autenticado)
    @Test
    public void test7_postOrder_whenAuthenticated_receiveCreated() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);

        OrderItemDTO item = OrderItemDTO.builder()
                .productId(testProduct.getId())
                .quantity(2)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .items(Collections.singletonList(item))
                .build();

        HttpEntity<OrderDTO> request = new HttpEntity<>(orderDTO, headers);

        ResponseEntity<OrderDTO> response = restTemplate.postForEntity(
                "/orders", request, OrderDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserId()).isEqualTo(testUser.getId());
        assertThat(response.getBody().getItems()).hasSize(1);
        assertThat(response.getBody().getTotal()).isGreaterThan(BigDecimal.ZERO);
    }

    // 8. Lista de Pedidos (do usuário autenticado)
    @Test
    public void test8_getOrders_whenAuthenticated_receiveOk() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<List<OrderDTO>> response = restTemplate.exchange(
                "/orders",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<OrderDTO>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}

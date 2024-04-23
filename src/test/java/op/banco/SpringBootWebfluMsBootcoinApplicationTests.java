package op.banco;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import op.banco.entity.Orden;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SpringBootWebfluMsBootcoinApplicationTests {
	
	@Autowired
	private WebTestClient client;

	@Test
	void contextLoads() {
	}

	@Test
	public void cantiadOrden() {
		client.get().uri("/bootcoin/orden")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk() 
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBodyList(Orden.class)
		.hasSize(2);
	}
	
	@Test
	public void listarOrden() {
		client.get().uri("/bootcoin/orden")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk() 
		.expectHeader().contentType(MediaType.APPLICATION_JSON) //.hasSize(2);
		.expectBodyList(Orden.class).consumeWith(response -> {
			
			List<Orden> cuentaBanco = response.getResponseBody();
			
			cuentaBanco.forEach(p -> {
				System.out.println(p.getCoins());
			});
			
			Assertions.assertThat(cuentaBanco.size() > 0).isTrue();
		});
	}
	
}

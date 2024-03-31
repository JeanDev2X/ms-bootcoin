package op.banco.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import op.banco.entity.BootCoin;
import op.banco.entity.Orden;
import op.banco.entity.Transaccion;
import op.banco.service.BootcoinService;
import op.banco.service.TransaccionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bootcoin")
public class BootcoinController {
	
	private final TransaccionService customerService;
		
	@Autowired
	private BootcoinService bootcoinService;

	public BootcoinController(TransaccionService customerService) {		
		this.customerService = customerService;
	}
	
	@PostMapping(value = "/compra/{destino}")
	public Transaccion saveOperacion(@RequestBody Orden orden,@PathVariable String destino) {		
		return this.customerService.save(orden,destino);
	}
		
	//listar
	
	@GetMapping("/orden")
	public Mono<ResponseEntity<Flux<Orden>>> findAll() {
		return Mono.just(
				ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bootcoinService.findAllOperacion())

		);
	}
	
	@GetMapping("/orden/{id}")
	public Mono<ResponseEntity<Orden>> viewId(@PathVariable String id) {
		return bootcoinService.findByIdOperacion(id)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/coin")
	public Mono<ResponseEntity<Flux<BootCoin>>> findAllCoins() {
		return Mono.just(
				ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bootcoinService.findAllCoin())

		);
	}
	
	@GetMapping("/coin/{dni}")
	public Mono<ResponseEntity<BootCoin>> viewIdCoins(@PathVariable String dni) {
		return bootcoinService.findByIdCoin(dni)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	//metodo retiro: bootcoinService.retiroCoin(Orden);
	@PutMapping("/retiroCoin/{numeroCelular}/{monto}")
	public Mono<BootCoin> retiroCoin(@PathVariable String numeroCelular,@PathVariable Double monto){
		return bootcoinService.retiroCoin(numeroCelular,monto);
	}
	
	@PutMapping("/depositosCoin/{numeroCelular}/{monto}")
	public Mono<BootCoin> depositoCoin(@PathVariable String numeroCelular,@PathVariable Double monto){
		return bootcoinService.depositosCoin(numeroCelular,monto);
	}
	
}

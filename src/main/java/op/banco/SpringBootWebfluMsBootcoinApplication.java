package op.banco;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import op.banco.entity.Usuario;
import op.banco.entity.BootCoin;
import op.banco.entity.Orden;
import op.banco.entity.Transaccion;
import op.banco.service.BootcoinService;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluMsBootcoinApplication implements CommandLineRunner{
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	@Autowired
	private BootcoinService bootcoinService;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluMsBootcoinApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluMsBootcoinApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("Usuario").subscribe();
		
		Usuario usuario1 = new Usuario("1","47305710","963791402");
		Orden orden1 = new Orden("11","10001",usuario1,4,"yanki","venta",3.37,3.67);
		Transaccion transaccion1 = new Transaccion("111","T1001",orden1,orden1.getUsuario().getNumeroCelular(),"963791420",orden1.getTasaVenta()*orden1.getCoins());
		
		Usuario usuario2 = new Usuario("2","47305712","963791420");
		Orden orden2 = new Orden("22","10002",usuario2,10,"yanki","venta",3.37,3.67);
		Transaccion transaccion2 = new Transaccion("222","T1002",orden2,orden2.getUsuario().getNumeroCelular(),"963791402",orden2.getTasaVenta()*orden2.getCoins());
		
		Flux.just(usuario1,usuario2)
		.flatMap(bootcoinService::saveUsuario)
		.doOnNext(u -> {
			log.info("Usuario Creado[" + u.getDni() +"]");
		}).thenMany(
				Flux.just(
						orden1,orden2
				).flatMap(op -> {
					return bootcoinService.saveOrden(op);
				})
		).thenMany(
				Flux.just(
						transaccion1,transaccion2
				).flatMap(ts -> {
					return bootcoinService.saveTransaccion(ts);
				})					
		).thenMany(
				Flux.just(
						new BootCoin("1111",usuario1.getDni(),usuario1.getNumeroCelular(),200,transaccion1.getNumTransaccion()),
						new BootCoin("2222",usuario2.getDni(),usuario2.getNumeroCelular(),100,transaccion2.getNumTransaccion())
				).flatMap(bt -> {
					return bootcoinService.saveCoin(bt);
				})	
		).subscribe();
		
	}

}


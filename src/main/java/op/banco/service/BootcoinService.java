package op.banco.service;

import op.banco.entity.Orden;
import op.banco.entity.Transaccion;
import op.banco.entity.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import op.banco.entity.BootCoin;

public interface BootcoinService {
	
	Mono<Orden> saveOrden(Orden orden);	
	Mono<Usuario> saveUsuario(Usuario usuario);
	Mono<Transaccion> saveTransaccion(Transaccion transaccion);
	Mono<BootCoin> saveCoin(BootCoin bootCoin);
	//
	
	Flux<Orden> findAllOperacion();
	Mono<Orden> findByIdOperacion(String id);
	Flux<BootCoin> findAllCoin();
	Mono<BootCoin> findByIdCoin(String dni);
	//
	Mono<BootCoin> retiroCoin(String numeroCelular,double monto);
	Mono<BootCoin> depositosCoin(String numeroCelular,double monto);
}

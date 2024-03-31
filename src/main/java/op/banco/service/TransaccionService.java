package op.banco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import op.banco.config.Constantes;
import op.banco.config.UtilisCode;
import op.banco.entity.BootCoin;
import op.banco.entity.Orden;
import op.banco.entity.Transaccion;
import reactor.core.publisher.Mono;

@Service
public class TransaccionService {
	
	private final TransaccionEventsService customerEventsService;

	public TransaccionService(TransaccionEventsService customerEventsService) {
		super();
		this.customerEventsService = customerEventsService;
	}
	
	@Autowired
	private BootcoinService bootcoinService;

//	public Customer save(Customer customer) {
//		System.out.println("Received " + customer);
//		this.customerEventsService.publish(customer);
//		return customer;
//		
//	}
	
	public Transaccion save(Orden orden,String destino) {
		System.out.println("Received " + orden);
		
		
		
		Transaccion transaccion = new Transaccion();
		transaccion.setOrigen(orden.getUsuario().getNumeroCelular());
		transaccion.setDestino(destino);
		
//		transaccion.setMontoPago(orden.getCoins()*orden.getTasaCompra());
		
		transaccion.setNumTransaccion(Constantes.NUMERO_TRANSACCION+UtilisCode.numTransaccion());
		transaccion.setOrden(orden);
		
		if(orden.getTipoOperacion().toUpperCase().equalsIgnoreCase("COMPRA")) {
			System.out.println("compra");
			transaccion.setMontoPago(orden.getCoins()*orden.getTasaCompra());
			Mono<BootCoin> depositoCoin = bootcoinService.depositosCoin(orden.getUsuario().getNumeroCelular(),orden.getCoins());
			depositoCoin.subscribe(coins ->{
				System.out.println("coins " + coins);
			},error -> {
				System.out.println("Error al obtener deposito : " + error.getMessage());
			});
			
			Mono<BootCoin> retiroCoin = bootcoinService.retiroCoin(destino,orden.getCoins());
			retiroCoin.subscribe(coins ->{
				System.out.println("coins " + coins);
			},error -> {
				System.out.println("Error al obtener retiro : " + error.getMessage());
			});
			
		}
		else if(orden.getTipoOperacion().toUpperCase().equalsIgnoreCase("VENTA")) {
			System.out.println("venta");
			transaccion.setMontoPago(orden.getCoins()*orden.getTasaVenta());
			Mono<BootCoin> retiroCoin = bootcoinService.retiroCoin(orden.getUsuario().getNumeroCelular(),orden.getCoins());
			retiroCoin.subscribe(coins ->{
				System.out.println("coins " + coins);
			},error -> {
				System.out.println("Error al obtener retiro : " + error.getMessage());
			});
			
			Mono<BootCoin> depositoCoin = bootcoinService.depositosCoin(destino,orden.getCoins());
			depositoCoin.subscribe(coins ->{
				System.out.println("coins " + coins);
			},error -> {
				System.out.println("Error al obtener deposito : " + error.getMessage());
			});
			
		}
		
		System.out.println("Received transaccion" + transaccion);
		this.customerEventsService.publish(transaccion);
		return transaccion;
		
	}
	
	

}

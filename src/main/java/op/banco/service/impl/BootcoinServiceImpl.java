package  op.banco.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import op.banco.dao.BootCoinDao;
import op.banco.dao.OrdenDao;
import op.banco.dao.TransaccionDao;
import op.banco.dao.UsuarioDao;
import op.banco.entity.BootCoin;
import op.banco.entity.Orden;
import op.banco.entity.Transaccion;
import op.banco.entity.Usuario;
import op.banco.service.BootcoinService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BootcoinServiceImpl implements BootcoinService{
	
	private static final Logger log = LoggerFactory.getLogger(BootcoinServiceImpl.class);
	
	@Autowired
	public TransaccionDao transaccionDao;
	
	@Autowired
	public OrdenDao ordenDao;
	
	@Autowired
	public UsuarioDao usuarioDao;
	
	@Autowired
	public BootCoinDao bootCoinDao;

	@Override
	public Mono<Orden> saveOrden(Orden orden) {		
		return ordenDao.save(orden);
	}

	@Override
	public Mono<Usuario> saveUsuario(Usuario usuario) {
		System.out.println("Hola saveUsuario");
		return usuarioDao.save(usuario);
	}

	@Override
	public Mono<Transaccion> saveTransaccion(Transaccion transaccion) {
		return transaccionDao.save(transaccion);
	}
	
	@Override
	public Mono<BootCoin> saveCoin(BootCoin bootCoin) {
		return bootCoinDao.save(bootCoin);
	}
	
	@Override
	public Flux<Orden> findAllOperacion() {
		return ordenDao.findAll();
	}

	@Override
	public Mono<Orden> findByIdOperacion(String id) {
		return ordenDao.findById(id);
	}
	
	@Override
	public Flux<BootCoin> findAllCoin() {
		return bootCoinDao.findAll();
	}

	@Override
	public Mono<BootCoin> findByIdCoin(String dni) {
		return bootCoinDao.findByDni(dni);
	}

	@Override
	public Mono<BootCoin> retiroCoin(String numeroCelular,double monto) {
		log.info("retiroCoin");
		//origen
		return bootCoinDao.findByNumeroCelular(numeroCelular).flatMap(c -> {

			System.out.println("coins"+c.toString());
			
			if (c.getCoins() > monto) {
				c.setCoins(c.getCoins()-monto);				
				return bootCoinDao.save(c);
			}
			
			return Mono.error(new InterruptedException("SALDO INSUFICIENTE"));
		});
		
	}

	@Override
	public Mono<BootCoin> depositosCoin(String numeroCelular,double monto) {
		//destino
		return bootCoinDao.findByNumeroCelular(numeroCelular).flatMap(c -> {

			System.out.println("El monto es : " + monto);
			c.setCoins(c.getCoins()+monto);			
			return bootCoinDao.save(c);
		});
	}
}

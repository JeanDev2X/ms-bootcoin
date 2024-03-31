package op.banco.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import op.banco.entity.BootCoin;
import reactor.core.publisher.Mono;

public interface BootCoinDao extends ReactiveMongoRepository<BootCoin, String>{
	@Query("{ 'dni' : ?0 }")
	Mono<BootCoin> findByDni(String dni);
	
	@Query("{ 'numeroCelular' : ?0 }")
	Mono<BootCoin> findByNumeroCelular(String numeroCelular);
}

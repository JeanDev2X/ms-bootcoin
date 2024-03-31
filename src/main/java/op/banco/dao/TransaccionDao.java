package  op.banco.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import op.banco.entity.Transaccion;

public interface TransaccionDao extends ReactiveMongoRepository<Transaccion, String>{

}

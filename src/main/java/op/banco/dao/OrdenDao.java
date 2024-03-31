package  op.banco.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import op.banco.entity.Orden;

public interface OrdenDao extends ReactiveMongoRepository<Orden, String>{

}

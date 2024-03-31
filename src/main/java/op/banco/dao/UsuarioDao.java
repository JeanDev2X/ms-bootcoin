package  op.banco.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import op.banco.entity.Usuario;

public interface UsuarioDao extends ReactiveMongoRepository<Usuario, String>{

}

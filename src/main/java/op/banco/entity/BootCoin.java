package op.banco.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection ="BootCoin")
public class BootCoin {

	private String id;
	private String dni;
	private String numeroCelular;
	private double coins;
	private String numTransaccion;
	
}

package op.banco.entity;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection ="Transaccion")
public class Transaccion {
	private String id;
	private String numTransaccion;
	private Orden orden;
	@NotEmpty
	private String origen;
	@NotEmpty
	private String destino;
	private double montoPago;//monto a pagar por la cantidad de bitconind
}

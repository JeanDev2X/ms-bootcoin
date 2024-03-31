package op.banco.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import op.banco.entity.Transaccion;


@Data
@EqualsAndHashCode(callSuper = true)
public class TransaccionCreatedEvent extends Event<Transaccion> {

}

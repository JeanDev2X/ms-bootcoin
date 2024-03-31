package op.banco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import op.banco.entity.Transaccion;
import op.banco.events.Event;
import op.banco.events.EventType;
import op.banco.events.TransaccionCreatedEvent;

import java.util.Date;
import java.util.UUID;

@Component
public class TransaccionEventsService {
	
	@Autowired
	private KafkaTemplate<String, Event<?>> producer;
	
//	//topic.customer.name:customers
//	//topic.transaccion.name:transaccions
//	@Value("${topic.customer.name:customers}")
//	private String topicCustomer;
//	
//	public void publish(Customer customer) {
//
//		CustomerCreatedEvent created = new CustomerCreatedEvent();
//		created.setData(customer);
//		created.setId(UUID.randomUUID().toString());
//		created.setType(EventType.CREATED);
//		created.setDate(new Date());
//
//		this.producer.send(topicCustomer, created);
//	}
	
	@Value("${topic.transaccion.name:transaccions}")
	private String topicTransaccion;
	
	public void publish(Transaccion transaccion) {

		TransaccionCreatedEvent created = new TransaccionCreatedEvent();
		created.setData(transaccion);
		created.setId(UUID.randomUUID().toString());
		created.setType(EventType.CREATED);
		created.setDate(new Date());

		this.producer.send(topicTransaccion, created);
	}
	
}

package com.example.transfer_service.kafka;


import com.example.transfer_service.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransferProducer {

    private static final String TOPIC = "transfer-topic";

    private final KafkaTemplate<String, Transfer> kafkaTemplate;




    public TransferProducer(KafkaTemplate<String, Transfer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransfer(Transfer transfer) {
        kafkaTemplate.send(TOPIC, transfer);
    }
}

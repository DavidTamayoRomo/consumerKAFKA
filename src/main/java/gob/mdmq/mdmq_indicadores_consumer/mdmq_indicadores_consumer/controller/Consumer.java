package gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.model.Datos;
import gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.service.DatoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Consumer {

    @Autowired
    public DatoService datoService;

    @KafkaListener(topics = { "1" })
    public void consumeMessage(String message, Acknowledgment acknowledgment) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Datos datos = mapper.readValue(message, Datos.class);
            datoService.save(datos);
            log.info("Transformado", datos);
            log.info("Mensaje recibido: {}", message);
            acknowledgment.acknowledge(); 
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
}

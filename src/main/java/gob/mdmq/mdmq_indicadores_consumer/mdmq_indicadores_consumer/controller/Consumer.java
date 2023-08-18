package gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;

import gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.model.Datos;
import gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.service.DatoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RestController
@RequestMapping("/datos")
public class Consumer {

    private final MongoTemplate mongoTemplate;

    public Consumer(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    public DatoService datoService;

    @KafkaListener(topics = { "temaBroker-2" }, groupId = "Group100")
    public void consumeMessage(String message, Acknowledgment acknowledgment) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Datos datos = mapper.readValue(message, Datos.class);
            // datoService.save(datos);
            // Obtener en de la coleccion el sistema en el que se va a almacenar
            Object SISTEMA = datos.getDatos().get("SISTEMA");
            mongoTemplate.save(datos, SISTEMA.toString());

            //log.info("Transformado", datos.getDatos().get("datos"));
            //log.info("Mensaje recibido: {}", message);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @GetMapping
    public Object listarDatos() throws JsonProcessingException {
        try {
            return datoService.listarDatos();
        } catch (Exception e) {
            return "Error al enviar el mensaje";
        }
    }

    public MongoCollection<Document> getUsuariosCollection() {
        return mongoTemplate.getCollection("STL2");
    }

    @GetMapping("/coleccion")
    public Object listarDatosporColeccion() throws JsonProcessingException {
        try {   
            MongoCollection<Document> usuariosCollection = getUsuariosCollection();
            List<Document> usuarios = new ArrayList<>();
            return usuariosCollection.find().into(usuarios);
        } catch (Exception e) {
            return "Error al enviar el mensaje";
        }
    }

    /*
     * @GetMapping
     * public Slice<Object> listarDatos(@RequestParam(defaultValue = "0") int page)
     * throws JsonProcessingException {
     * 
     * try {
     * int pageSize = 10000;
     * Pageable pageable = PageRequest.of(page, pageSize);
     * 
     * Page<Object> coleccionPage = (Page<Object>)
     * datoService.listarDatosPag(pageable);
     * 
     * // Convertir el Page en un Slice para mantener la información de si hay más
     * // elementos
     * Slice<Object> coleccionSlice = new SliceImpl<>(coleccionPage.getContent(),
     * pageable,
     * coleccionPage.hasNext());
     * 
     * return coleccionSlice;//datoService.listarDatos();
     * } catch (Exception e) {
     * return "Error al enviar el mensaje";
     * }
     * 
     * }
     */
}

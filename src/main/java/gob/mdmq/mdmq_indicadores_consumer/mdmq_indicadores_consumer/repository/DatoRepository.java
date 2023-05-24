package gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.model.Datos;

@Repository
public interface DatoRepository extends MongoRepository<Datos, String> {
    
}

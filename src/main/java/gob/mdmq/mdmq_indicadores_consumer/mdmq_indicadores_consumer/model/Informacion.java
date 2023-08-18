package gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Informacion {
    @Id
    private String idSistema;

    private List<Datos> informacion; 
}

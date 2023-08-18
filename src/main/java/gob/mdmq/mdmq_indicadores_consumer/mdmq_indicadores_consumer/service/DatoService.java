package gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.model.Datos;
import gob.mdmq.mdmq_indicadores_consumer.mdmq_indicadores_consumer.repository.DatoRepository;

@Service
public class DatoService {
    private final DatoRepository datoRepository;

    @Autowired
    public DatoService(DatoRepository datoRepository) {
        this.datoRepository = datoRepository;
    }

    public void save(Datos dato) {
        datoRepository.save(dato);
    }

    public Object listarDatos() {
        return datoRepository.findAll();
    }

    public Object listarDatosPag(Pageable pageable) {
        return datoRepository.findAll(pageable);
    }
}

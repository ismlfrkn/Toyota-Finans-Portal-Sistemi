package com.toyota.market_data_service.service;

import com.toyota.market_data_service.entity.Instrument;

import java.util.List;
import java.util.UUID;

public interface InstrumentService {

    List<Instrument> findAll();

    Instrument findById(UUID id);

    Instrument create(Instrument instrument);

    Instrument update(UUID id, Instrument updated);

    void delete(UUID id);
}

package com.toyota.market_data_service.service.impl;

import com.toyota.market_data_service.entity.Instrument;
import com.toyota.market_data_service.repository.InstrumentRepository;
import com.toyota.market_data_service.service.InstrumentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InstrumentServiceImpl implements InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public InstrumentServiceImpl(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    @Override
    public List<Instrument> findAll() {
        return instrumentRepository.findAll();
    }

    @Override
    public Instrument findById(UUID id) {
        return instrumentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instrument not found: " + id));
    }

    @Override
    public Instrument create(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }

    @Override
    public Instrument update(UUID id, Instrument updated) {
        Instrument existing = findById(id);
        existing.setSymbol(updated.getSymbol());
        existing.setName(updated.getName());
        existing.setType(updated.getType());
        existing.setCurrency(updated.getCurrency());
        return instrumentRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        instrumentRepository.deleteById(id);
    }
}

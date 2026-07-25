package com.toyota.market_data_service.controller;

import com.toyota.market_data_service.entity.Instrument;
import com.toyota.market_data_service.service.InstrumentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/instruments")
public class InstrumentController {

    private final InstrumentService instrumentService;

    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @GetMapping
    public List<Instrument> findAll() {
        return instrumentService.findAll();
    }

    @GetMapping("/{id}")
    public Instrument findById(@PathVariable UUID id) {
        return instrumentService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Instrument create(@RequestBody Instrument instrument) {
        return instrumentService.create(instrument);
    }

    @PutMapping("/{id}")
    public Instrument update(@PathVariable UUID id, @RequestBody Instrument instrument) {
        return instrumentService.update(id, instrument);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        instrumentService.delete(id);
    }
}

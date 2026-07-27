package com.toyota.market_data_service.controller;

import com.toyota.market_data_service.dto.request.InstrumentCreateRequest;
import com.toyota.market_data_service.dto.request.InstrumentUpdateRequest;
import com.toyota.market_data_service.dto.response.InstrumentCreateResponse;
import com.toyota.market_data_service.dto.response.InstrumentResponse;
import com.toyota.market_data_service.dto.response.InstrumentUpdateResponse;
import com.toyota.market_data_service.entity.Instrument;
import com.toyota.market_data_service.mapper.InstrumentMapper;
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
    private final InstrumentMapper instrumentMapper;

    public InstrumentController(InstrumentService instrumentService, InstrumentMapper instrumentMapper) {
        this.instrumentService = instrumentService;
        this.instrumentMapper = instrumentMapper;
    }

    @GetMapping
    public List<InstrumentResponse> findAll() {
        return instrumentService.findAll().stream()
                .map(instrumentMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public InstrumentResponse findById(@PathVariable UUID id) {
        return instrumentMapper.toResponse(instrumentService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstrumentCreateResponse create(@RequestBody InstrumentCreateRequest request) {
        Instrument instrument = instrumentMapper.toEntity(request);
        return instrumentMapper.toCreateResponse(instrumentService.create(instrument));
    }

    @PutMapping("/{id}")
    public InstrumentUpdateResponse update(@PathVariable UUID id, @RequestBody InstrumentUpdateRequest request) {
        Instrument updated = instrumentMapper.toEntity(request);
        return instrumentMapper.toUpdateResponse(instrumentService.update(id, updated));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        instrumentService.delete(id);
    }
}

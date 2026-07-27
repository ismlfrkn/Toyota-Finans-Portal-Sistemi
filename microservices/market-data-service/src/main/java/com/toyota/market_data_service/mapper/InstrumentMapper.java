package com.toyota.market_data_service.mapper;

import com.toyota.market_data_service.dto.request.InstrumentCreateRequest;
import com.toyota.market_data_service.dto.request.InstrumentUpdateRequest;
import com.toyota.market_data_service.dto.response.InstrumentCreateResponse;
import com.toyota.market_data_service.dto.response.InstrumentResponse;
import com.toyota.market_data_service.dto.response.InstrumentUpdateResponse;
import com.toyota.market_data_service.entity.Instrument;
import org.springframework.stereotype.Component;

@Component
public class InstrumentMapper {

    public Instrument toEntity(InstrumentCreateRequest request) {
        return new Instrument(request.symbol(), request.name(), request.type(), request.currency());
    }

    public Instrument toEntity(InstrumentUpdateRequest request) {
        return new Instrument(request.symbol(), request.name(), request.type(), request.currency());
    }

    public InstrumentResponse toResponse(Instrument instrument) {
        return new InstrumentResponse(
                instrument.getId(),
                instrument.getSymbol(),
                instrument.getName(),
                instrument.getType(),
                instrument.getCurrency()
        );
    }

    public InstrumentCreateResponse toCreateResponse(Instrument instrument) {
        return new InstrumentCreateResponse(
                instrument.getId(),
                instrument.getSymbol(),
                instrument.getName(),
                instrument.getType(),
                instrument.getCurrency()
        );
    }

    public InstrumentUpdateResponse toUpdateResponse(Instrument instrument) {
        return new InstrumentUpdateResponse(
                instrument.getId(),
                instrument.getSymbol(),
                instrument.getName(),
                instrument.getType(),
                instrument.getCurrency()
        );
    }
}

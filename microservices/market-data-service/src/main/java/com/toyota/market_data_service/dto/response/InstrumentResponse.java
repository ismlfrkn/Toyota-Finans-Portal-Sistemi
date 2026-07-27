package com.toyota.market_data_service.dto.response;

import com.toyota.market_data_service.entity.InstrumentType;

import java.util.UUID;

public record InstrumentResponse(
        UUID id,
        String symbol,
        String name,
        InstrumentType type,
        String currency
) {
}

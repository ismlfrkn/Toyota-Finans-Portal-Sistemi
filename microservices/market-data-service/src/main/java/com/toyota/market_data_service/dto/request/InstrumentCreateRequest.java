package com.toyota.market_data_service.dto.request;

import com.toyota.market_data_service.entity.InstrumentType;

public record InstrumentCreateRequest(
        String symbol,
        String name,
        InstrumentType type,
        String currency
) {
}

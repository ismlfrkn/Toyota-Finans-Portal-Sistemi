package com.toyota.market_data_service.repository;

import com.toyota.market_data_service.entity.InstrumentPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstrumentPriceRepository extends JpaRepository<InstrumentPrice, UUID> {
}

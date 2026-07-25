package com.toyota.market_data_service.repository;

import com.toyota.market_data_service.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstrumentRepository extends JpaRepository<Instrument, UUID> {
}

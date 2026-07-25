CREATE TABLE instruments (
    id UUID PRIMARY KEY,
    symbol VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL,
    currency VARCHAR(10) NOT NULL
);

CREATE TABLE instrument_prices (
    id UUID PRIMARY KEY,
    instrument_id UUID NOT NULL REFERENCES instruments (id),
    price_date DATE NOT NULL,
    open_price NUMERIC(19, 4) NOT NULL,
    high_price NUMERIC(19, 4) NOT NULL,
    low_price NUMERIC(19, 4) NOT NULL,
    close_price NUMERIC(19, 4) NOT NULL,
    CONSTRAINT uq_instrument_price_date UNIQUE (instrument_id, price_date)
);

CREATE INDEX idx_instrument_prices_instrument_id ON instrument_prices (instrument_id);
CREATE INDEX idx_instrument_prices_price_date ON instrument_prices (price_date);

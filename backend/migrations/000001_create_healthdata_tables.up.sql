CREATE TABLE IF NOT EXISTS heartbeats (
    id bigserial PRIMARY KEY,  
    created_at timestamp(0) with time zone NOT NULL DEFAULT NOW(),
    heartbeat integer NOT NULL
);

CREATE TABLE IF NOT EXISTS body_temperatures (
    id bigserial PRIMARY KEY,  
    created_at timestamp(0) with time zone NOT NULL DEFAULT NOW(),
    temperature integer NOT NULL
);
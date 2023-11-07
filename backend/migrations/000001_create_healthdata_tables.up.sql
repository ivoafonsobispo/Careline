CREATE TABLE IF NOT EXISTS heartbeat (
    id bigserial PRIMARY KEY,  
    created_at timestamp(0) with time zone NOT NULL DEFAULT NOW(),
    heartbeat integer NOT NULL
);

CREATE TABLE IF NOT EXISTS body_temperature (
    id bigserial PRIMARY KEY,  
    created_at timestamp(0) with time zone NOT NULL DEFAULT NOW(),
    temperature integer NOT NULL
);
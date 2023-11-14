CREATE TABLE IF NOT EXISTS patients (
    id bigserial PRIMARY KEY,
    created_at timestamp(0) with time zone NOT NULL DEFAULT NOW(),
    name text NOT NULL,
    email citext UNIQUE NOT NULL,
    password_hash bytea NOT NULL,
    sex bool NOT NULL,
    health_number text UNIQUE NOT NULL,
    activated bool NOT NULL,
    version integer NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS professionals (
    id bigserial PRIMARY KEY,
    created_at timestamp(0) with time zone NOT NULL DEFAULT NOW(),
    name text NOT NULL,
    email citext UNIQUE NOT NULL,
    password_hash bytea NOT NULL,
    sex bool NOT NULL,
    health_professional_number text UNIQUE NOT NULL,
    activated bool NOT NULL,
    version integer NOT NULL DEFAULT 1
);

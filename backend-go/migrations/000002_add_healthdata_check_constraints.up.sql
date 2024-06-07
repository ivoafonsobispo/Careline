ALTER TABLE heartbeats ADD CONSTRAINT heartbeat_runtime_check CHECK (heartbeat BETWEEN 0 AND 300);

ALTER TABLE body_temperatures ADD CONSTRAINT body_temperature_runtime_check CHECK (temperature BETWEEN 0 AND 50);

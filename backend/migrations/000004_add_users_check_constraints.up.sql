ALTER TABLE patients ADD CONSTRAINT health_number_runtime_check CHECK (health_number ~ '^[0-9]{9}$');

ALTER TABLE professionals ADD CONSTRAINT health_professional_number_runtime_check CHECK (health_professional_number ~ '^[0-9]{9}$');

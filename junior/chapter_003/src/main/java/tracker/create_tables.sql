CREATE SCHEMA tracker;
ALTER SCHEMA tracker OWNER TO postgres;

CREATE TABLE tracker.items ( id SERIAL primary key, name text);
ALTER TABLE tracker.items OWNER TO postgres;

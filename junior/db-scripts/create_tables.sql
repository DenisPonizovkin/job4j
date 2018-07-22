\connect job4j;
CREATE SCHEMA uml;
ALTER SCHEMA uml OWNER TO postgres;

---------------------------------------------------------------------
-- categories
---------------------------------------------------------------------
CREATE TABLE uml.categories (
    	id SERIAL primary key,
    	name text
);
ALTER TABLE uml.categories OWNER TO postgres;
---------------------------------------------------------------------
-- roles
---------------------------------------------------------------------
CREATE TABLE uml.roles (
    id SERIAL primary key,
    name text
);
ALTER TABLE uml.roles OWNER TO postgres;
---------------------------------------------------------------------
-- rules
---------------------------------------------------------------------
CREATE TABLE uml.rules (
    	id SERIAL primary key,
    	name text
);
ALTER TABLE uml.rules OWNER TO postgres;
---------------------------------------------------------------------
-- states
---------------------------------------------------------------------
CREATE TABLE uml.states (
    	id SERIAL primary key,
    	name text
);
ALTER TABLE uml.states OWNER TO postgres;
---------------------------------------------------------------------
-- items
---------------------------------------------------------------------
CREATE TABLE uml.items (
    	id SERIAL primary key,
    	name text,
    	user_id integer,
    	category_id integer,
    	state_id integer,
	UNIQUE(user_id)
);
ALTER TABLE uml.items OWNER TO postgres;
---------------------------------------------------------------------
-- attachments
---------------------------------------------------------------------
CREATE TABLE uml.attachments (
	id SERIAL primary key,
	name text,
	item_id integer,
        FOREIGN KEY ("item_id") REFERENCES uml.items(id) ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE uml.attachments OWNER TO postgres;
---------------------------------------------------------------------
-- comments
---------------------------------------------------------------------
CREATE TABLE uml.comments (
    	id SERIAL primary key,
    	name text,
	item_id integer,
        FOREIGN KEY ("item_id") REFERENCES uml.items(id) ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE uml.comments OWNER TO postgres;
---------------------------------------------------------------------
-- roles-rules relation
---------------------------------------------------------------------
CREATE TABLE uml.roles_rules_map (
    	id SERIAL primary key,
    	role_id integer,
    	rule_id integer
);
ALTER TABLE uml.roles_rules_map OWNER TO postgres;
---------------------------------------------------------------------
-- users
---------------------------------------------------------------------
CREATE TABLE uml.users (
    	id SERIAL primary key,
    	name text,
    	role_id integer,
	FOREIGN KEY ("role_id") REFERENCES uml.roles(id) ON UPDATE CASCADE ON DELETE CASCADE
);
---------------------------------------------------------------------
-- CONSTRAINTS
---------------------------------------------------------------------
ALTER TABLE ONLY uml.items
    ADD CONSTRAINT items_states_fkey FOREIGN KEY (state_id) REFERENCES uml.states(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY uml.items
    ADD CONSTRAINT items_users_fkey FOREIGN KEY (user_id) REFERENCES uml.users(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY uml.roles_rules_map
    ADD CONSTRAINT roles_rules_map_role_id_fkey FOREIGN KEY (role_id) REFERENCES uml.roles(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY uml.roles_rules_map
    ADD CONSTRAINT roles_rules_map_rule_id_fkey FOREIGN KEY (rule_id) REFERENCES uml.rules(id) ON UPDATE CASCADE ON DELETE CASCADE;

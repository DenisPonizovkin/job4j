-- Init data. 1. UML системы [#62660]

---------------------------------------------------------
-- categories
---------------------------------------------------------
INSERT INTO uml.categories VALUES (1, 'category1');
INSERT INTO uml.categories VALUES (2, 'category2');
INSERT INTO uml.categories VALUES (3, 'category3');

---------------------------------------------------------
-- categories
---------------------------------------------------------
INSERT INTO uml.roles VALUES (1, 'user');
INSERT INTO uml.roles VALUES (2, 'manager');
INSERT INTO uml.roles VALUES (3, 'admin');

---------------------------------------------------------
-- categories
---------------------------------------------------------
INSERT INTO uml.states VALUES (1, 'state1');
INSERT INTO uml.states VALUES (2, 'state2');
INSERT INTO uml.states VALUES (3, 'state3');

---------------------------------------------------------
-- categories
---------------------------------------------------------
INSERT INTO uml.users VALUES (1, 'user', 1);
INSERT INTO uml.users VALUES (2, 'manager', 2);
INSERT INTO uml.users VALUES (3, 'admin', 3);

---------------------------------------------------------
-- categories
---------------------------------------------------------
INSERT INTO uml.items VALUES (4, 'item2', 2, 2, 2);
INSERT INTO uml.items VALUES (5, 'item3', 3, 3, 3);
INSERT INTO uml.items VALUES (8, 'item 1', 1, 1, 1);

---------------------------------------------------------
-- categories
---------------------------------------------------------
INSERT INTO uml.rules VALUES (1, 'simple');
INSERT INTO uml.rules VALUES (2, 'grant');
INSERT INTO uml.rules VALUES (3, 'full');


---------------------------------------------------------
-- categories
---------------------------------------------------------
INSERT INTO uml.roles_rules_map VALUES (1, 1, 1);
INSERT INTO uml.roles_rules_map VALUES (2, 2, 1);
INSERT INTO uml.roles_rules_map VALUES (3, 2, 2);
INSERT INTO uml.roles_rules_map VALUES (4, 3, 1);
INSERT INTO uml.roles_rules_map VALUES (5, 3, 2);
INSERT INTO uml.roles_rules_map VALUES (6, 3, 3);

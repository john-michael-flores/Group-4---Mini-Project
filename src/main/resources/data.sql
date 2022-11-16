INSERT INTO user (id, user_name, password, role) VALUES (1, 'Dondon', '$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu', 'ADMIN');
INSERT INTO user (id, user_name, password, role) VALUES (2, 'Michael', '$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu', 'USER');

INSERT INTO payroll (id, name, basic_pay, allowances, type) VALUES (1, 'Vonn', 30000, 3066, 'RANK_AND_FILE');
INSERT INTO payroll (id, name, basic_pay, allowances, type) VALUES (2, 'Don', 30000, 3066, 'RANK_AND_FILE');
INSERT INTO payroll (id, name, basic_pay, allowances, type) VALUES (3, 'Michael', 30000, 3066, 'MANAGERIAL');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_CUSTOMER');

INSERT INTO usuarios (username, password, enabled, name, last_name, email, role_id) VALUES ('alvaro_admin', '$2y$10$SLOGO1qrAEyK4w1HrLfWgecVIK7u88NdX5tWtDcJJ2KqLc5OB.UIK', 1, 'Alvaro', 'Sánchez', 'alvaro@gmail.com', 1);
INSERT INTO usuarios (username, password, enabled, name, last_name, email, role_id) VALUES ('pedro123', '$2y$10$iNMF09H35ZYg1NQsCs.jdO3YICvdNkS4UAcrTQ0.zTnO0d1b5xG6C', 1, 'Pedro', 'Martínez', 'pedro@gmail.com', 2);

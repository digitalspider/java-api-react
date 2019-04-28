INSERT INTO myapp.role (id,name) VALUES
(1,'ADMIN'),
(2,'MANAGER');

INSERT INTO myapp.user (id,username,password,email,created_at,updated_at) VALUES
(1,'admin','admin','admin@admin.com',now(),now());

INSERT INTO myapp.userroles (user_id,role_id) VALUES (1,1);
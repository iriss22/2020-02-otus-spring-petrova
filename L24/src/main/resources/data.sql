INSERT INTO user (username, password, enabled) values ('user', '$2y$12$UBilzHo4ymo12Wb7SROUfOT1DE9tg/RFFv4M2QNYWt9LKVo.COgfm', TRUE);
INSERT INTO user (username, password, enabled) values ('admin', '$2y$12$UBilzHo4ymo12Wb7SROUfOT1DE9tg/RFFv4M2QNYWt9LKVo.COgfm', TRUE);
INSERT INTO user (username, password, enabled) values ('user2', '$2y$12$UBilzHo4ymo12Wb7SROUfOT1DE9tg/RFFv4M2QNYWt9LKVo.COgfm', FALSE);

INSERT INTO authority (username, authority) values ('user', 'ROLE_USER');
INSERT INTO authority (username, authority) values ('admin', 'ROLE_ADMIN');
INSERT INTO authority (username, authority) values ('user2', 'ROLE_USER');

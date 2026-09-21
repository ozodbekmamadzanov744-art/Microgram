--liquibase formatted sql
--changeset microgram:002
INSERT INTO users (login, email, password, name, avatar, information)
VALUES ('azamat', 'azamat@example.com', '$2b$12$3s0a1X049vSQ.UPJkR.i1u60ob.6QEewAYO0qF081DhbUPDPiMoW6', 'Азамат', '/images/avatar.png', 'Люблю фотографировать город'),
       ('aida', 'aida@example.com', '$2b$12$3s0a1X049vSQ.UPJkR.i1u60ob.6QEewAYO0qF081DhbUPDPiMoW6', 'Аида', '/images/avatar.png', 'Путешествия и прогулки');
INSERT INTO publications (user_id, image, description, created_at)
VALUES ((SELECT id FROM users WHERE login = 'aida'), '/images/sample.png', 'Прогулка в горах', '2026-09-19 10:00:00');
INSERT INTO subscriptions (subscriber_id, author_id)
VALUES ((SELECT id FROM users WHERE login = 'azamat'), (SELECT id FROM users WHERE login = 'aida'));

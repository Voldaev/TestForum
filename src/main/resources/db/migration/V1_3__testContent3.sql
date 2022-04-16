-- тестовый раздел
INSERT INTO comments (theme_id, user_id, text, published, score) VALUES ((select id from themes where theme = 'TestTheme1'),(select id from users where sign = 'TestSignature'), 'тестовый текст комментария 1',current_timestamp,'5');
INSERT INTO comments (theme_id, user_id, text, published, score) VALUES ((select id from themes where theme = 'TestTheme1'),(select id from users where sign = 'TestSignature'), 'тестовый текст комментария 2',current_timestamp,'6');
INSERT INTO comments (theme_id, user_id, text, published, score) VALUES ((select id from themes where theme = 'TestTheme1'),(select id from users where sign = 'TestSignature'), 'тестовый текст комментария 3',current_timestamp,'1');

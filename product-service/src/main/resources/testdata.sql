-- Category 테이블 데이터
INSERT INTO public.category (name, created_at, updated_at) VALUES
                                                               ('Red Wine', CURRENT_TIMESTAMP, NULL),
                                                               ('White Wine', CURRENT_TIMESTAMP, NULL),
                                                               ('Rosé Wine', CURRENT_TIMESTAMP, NULL),
                                                               ('Sparkling Wine', CURRENT_TIMESTAMP, NULL),
                                                               ('Dessert Wine', CURRENT_TIMESTAMP, NULL);

-- Region 테이블 데이터
INSERT INTO public.region (name, created_at, updated_at) VALUES
                                                             ('Bordeaux', CURRENT_TIMESTAMP, NULL),
                                                             ('Burgundy', CURRENT_TIMESTAMP, NULL),
                                                             ('Tuscany', CURRENT_TIMESTAMP, NULL),
                                                             ('Napa Valley', CURRENT_TIMESTAMP, NULL),
                                                             ('Rioja', CURRENT_TIMESTAMP, NULL);

-- Wine 테이블 데이터
INSERT INTO public.wine (region_id, name, description, alcohol_percentage, registrant_id, created_at, updated_at) VALUES
                                                                                                                                             ((SELECT region_id FROM public.region WHERE name = 'Bordeaux'), 'Chateau Margaux', 'A premium Bordeaux blend with rich flavors.', 13.5, 1, CURRENT_TIMESTAMP, NULL),
                                                                                                                                             ((SELECT region_id FROM public.region WHERE name = 'Burgundy'), 'Domaine de la Romanée-Conti', 'An exquisite Pinot Noir from Burgundy.', 13.0, 1, CURRENT_TIMESTAMP, NULL),
                                                                                                                                             ((SELECT region_id FROM public.region WHERE name = 'Tuscany'), 'Sassicaia', 'A Super Tuscan wine with bold character.', 14.0, 2, CURRENT_TIMESTAMP, NULL),
                                                                                                                                             ((SELECT region_id FROM public.region WHERE name = 'Napa Valley'), 'Opus One', 'A prestigious Cabernet Sauvignon blend.', 14.5, 2, CURRENT_TIMESTAMP, NULL),
                                                                                                                                             ((SELECT region_id FROM public.region WHERE name = 'Rioja'), 'La Rioja Alta', 'A classic Tempranillo from Rioja.', 13.5, 3, CURRENT_TIMESTAMP, NULL);

-- Wine_Category 테이블 데이터
INSERT INTO public.wine_category (wine_id, category_id, created_at, updated_at) VALUES
                                                                                    ((SELECT wine_id FROM public.wine WHERE name = 'Chateau Margaux'), (SELECT category_id FROM public.category WHERE name = 'Red Wine'), CURRENT_TIMESTAMP, NULL),
                                                                                    ((SELECT wine_id FROM public.wine WHERE name = 'Domaine de la Romanée-Conti'), (SELECT category_id FROM public.category WHERE name = 'Red Wine'), CURRENT_TIMESTAMP, NULL),
                                                                                    ((SELECT wine_id FROM public.wine WHERE name = 'Sassicaia'), (SELECT category_id FROM public.category WHERE name = 'Red Wine'), CURRENT_TIMESTAMP, NULL),
                                                                                    ((SELECT wine_id FROM public.wine WHERE name = 'Opus One'), (SELECT category_id FROM public.category WHERE name = 'Red Wine'), CURRENT_TIMESTAMP, NULL),
                                                                                    ((SELECT wine_id FROM public.wine WHERE name = 'La Rioja Alta'), (SELECT category_id FROM public.category WHERE name = 'Red Wine'), CURRENT_TIMESTAMP, NULL);


-- WINE_SALE 테이블 테스트 데이터
INSERT INTO public.wine_sale (wine_sale_id, seller_id, wine_id, registered_quantity, stock_quantity, sold_quantity, sale_status, sale_start_time, sale_end_time, created_at, updated_at, price) VALUES
                                                                                                                                                                                                    (1, 1, (SELECT wine_id FROM public.wine WHERE name = 'Chateau Margaux'), 100, 100, 0, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '7 days', CURRENT_TIMESTAMP, NULL, 150000),
                                                                                                                                                                                                    (2, 2, (SELECT wine_id FROM public.wine WHERE name = 'Domaine de la Romanée-Conti'), 50, 45, 5, 2, CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP + INTERVAL '6 days', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP, 200000),
                                                                                                                                                                                                    (3, 1, (SELECT wine_id FROM public.wine WHERE name = 'Sassicaia'), 75, 75, 0, 0, CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP + INTERVAL '8 days', CURRENT_TIMESTAMP, NULL, 120000),
                                                                                                                                                                                                    (4, 3, (SELECT wine_id FROM public.wine WHERE name = 'Opus One'), 200, 180, 20, 2, CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP + INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP, 180000),
                                                                                                                                                                                                    (5, 2, (SELECT wine_id FROM public.wine WHERE name = 'La Rioja Alta'), 150, 150, 0, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '10 days', CURRENT_TIMESTAMP, NULL, 90000),
                                                                                                                                                                                                    (6, 3, (SELECT wine_id FROM public.wine WHERE name = 'Chateau Margaux'), 80, 60, 20, 2, CURRENT_TIMESTAMP - INTERVAL '3 days', CURRENT_TIMESTAMP + INTERVAL '4 days', CURRENT_TIMESTAMP - INTERVAL '3 days', CURRENT_TIMESTAMP, 155000),
                                                                                                                                                                                                    (7, 1, (SELECT wine_id FROM public.wine WHERE name = 'Domaine de la Romanée-Conti'), 30, 30, 0, 0, CURRENT_TIMESTAMP + INTERVAL '2 days', CURRENT_TIMESTAMP + INTERVAL '9 days', CURRENT_TIMESTAMP, NULL, 210000),
                                                                                                                                                                                                    (8, 2, (SELECT wine_id FROM public.wine WHERE name = 'Sassicaia'), 120, 100, 20, 2, CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP + INTERVAL '6 days', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP, 125000),
                                                                                                                                                                                                    (9, 3, (SELECT wine_id FROM public.wine WHERE name = 'Opus One'), 90, 90, 0, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '7 days', CURRENT_TIMESTAMP, NULL, 185000),
                                                                                                                                                                                                    (10, 1, (SELECT wine_id FROM public.wine WHERE name = 'La Rioja Alta'), 60, 55, 5, 2, CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP + INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP, 95000);

-- Category 테이블 시퀀스 업데이트
SELECT setval('category_category_id_seq', (SELECT MAX(category_id) FROM category));

-- Region 테이블 시퀀스 업데이트
SELECT setval('region_region_id_seq', (SELECT MAX(region_id) FROM region));

-- Wine 테이블 시퀀스 업데이트
SELECT setval('wine_wine_id_seq', (SELECT MAX(wine_id) FROM wine));

-- Wine_Category 테이블 시퀀스 업데이트
SELECT setval('wine_category_wine_category_id_seq', (SELECT MAX(wine_category_id) FROM wine_category));

-- Wine_Sale 테이블 시퀀스 업데이트
SELECT setval('wine_sale_wine_sale_id_seq', (SELECT MAX(wine_sale_id) FROM wine_sale));

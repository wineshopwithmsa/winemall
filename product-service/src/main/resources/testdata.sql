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
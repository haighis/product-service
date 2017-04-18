INSERT INTO public.category (title, description) VALUES ('Mobile', 'For Mobile');
INSERT INTO public.category (title, description) VALUES ('Electronics', 'TV et other consumer electronic');
INSERT INTO public.category (title, description) VALUES ('Toys', '');
INSERT INTO public.category (title, description) VALUES ('Books', '');

INSERT INTO public.product(
    sku, title, price, brand, description, category_id)
VALUES ('aba1', 'Applie iPhone', 1299.00, 'branda', 'This is a description', (SELECT id FROM Category WHERE Title = 'Mobile'));

INSERT INTO public.product(
    sku, title, price, brand, description, category_id)
VALUES ('bba1', 'Samsung 52 LED', 949.00, 'brandb', 'This is a description', (SELECT id FROM Category WHERE Title = 'Electronics'));

INSERT INTO public.product(
    sku, title, price, brand, description, category_id)
VALUES ('cba1', 'Red Wagon', 49.00, 'brandb', 'This is a description', (SELECT id FROM Category WHERE Title = 'Toys'));
    
INSERT INTO public.product(
    sku, title, price, brand, description, category_id)
VALUES ('dba1', 'It by Stephen King', 649.00, 'brandb', 'This is a description', (SELECT id FROM Category WHERE Title = 'Books'));

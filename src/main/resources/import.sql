-- 1. Tabela de Usuários (users)
INSERT INTO users (id, username, display_name, password) VALUES (1, 'joao.silva', 'João Silva', '$2a$10$exemploHashSenha123');
INSERT INTO users (id, username, display_name, password) VALUES (2, 'maria.souza', 'Maria Souza', '$2a$10$exemploHashSenha456');
-- Avança a sequência para evitar conflito de PK com IDs gerados automaticamente nos testes
ALTER TABLE users ALTER COLUMN id RESTART WITH 100;

-- 2. Tabela de Categorias (categories)
INSERT INTO categories (id, name) VALUES (1, 'Painéis');
INSERT INTO categories (id, name) VALUES (2, 'Suportes');
ALTER TABLE categories ALTER COLUMN id RESTART WITH 100;

-- 3. Tabela de Produtos (produtos)
INSERT INTO produtos (id, name, description, price, image_url, category_id) VALUES (1, 'Painel de Macramê Boho Grande', 'Painel decorativo feito à mão com cordão de algodão cru, estilo boho.', 250.00, 'https://exemplo.com/img/painel-boho.png', 1);
INSERT INTO produtos (id, name, description, price, image_url, category_id) VALUES (2, 'Suporte para Planta Suspensa', 'Suporte em macramê para vasos de plantas, ideal para decoração interna.', 45.00, 'https://exemplo.com/img/suporte-planta.png', 2);
INSERT INTO produtos (id, name, description, price, image_url, category_id) VALUES (3, 'Flâmula de Macramê Geométrica', 'Flâmula decorativa de parede com detalhes geométricos e franjas.', 120.00, 'https://exemplo.com/img/flamula.png', 1);
ALTER TABLE produtos ALTER COLUMN id RESTART WITH 100;

-- 4. Tabela de Endereços (addresses)
INSERT INTO addresses (id, user_id, street, number, complement, neighborhood, city, state, address_number) VALUES (1, 1, 'Rua das Flores', '123', 'Apto 42', 'Centro', 'São Paulo', 'SP', '01001-000');
INSERT INTO addresses (id, user_id, street, number, complement, neighborhood, city, state, address_number) VALUES (2, 2, 'Avenida Brasil', '1500', 'Bloco B', 'Jardins', 'Rio de Janeiro', 'RJ', '20040-000');
ALTER TABLE addresses ALTER COLUMN id RESTART WITH 100;

-- 5. Tabela de Pedidos (orders)
INSERT INTO orders (id, data, user_id) VALUES (1, '2026-09-22T14:30:00', 1);
INSERT INTO orders (id, data, user_id) VALUES (2, '2026-09-22T16:00:00', 2);
ALTER TABLE orders ALTER COLUMN id RESTART WITH 100;

-- 6. Tabela de Itens do Pedido (itens_do_pedido) - sem autoincrement, PK composta
INSERT INTO itens_do_pedido (order_id, product_id, price, quantity) VALUES (1, 1, 250.00, 1);
INSERT INTO itens_do_pedido (order_id, product_id, price, quantity) VALUES (1, 2, 45.00, 2);
INSERT INTO itens_do_pedido (order_id, product_id, price, quantity) VALUES (2, 3, 120.00, 1);
-- ==========================================
-- 1. Tabela de Usuários (tb_user)
-- ==========================================
INSERT INTO tb_user (id, username, display_name, password) VALUES
                                                               (1, 'joao.silva', 'João Silva', '$2a$10$exemploHashSenha123'),
                                                               (2, 'maria.souza', 'Maria Souza', '$2a$10$exemploHashSenha456');

-- ==========================================
-- 2. Tabela de Produtos (Macramê)
-- ==========================================
INSERT INTO produtos (id, nome, descricao, preco, url_imagem, categoria_id) VALUES
                                                                                (1, 'Painel de Macramê Boho Grande', 'Painel decorativo feito à mão com cordão de algodão cru, estilo boho.', 250.00, 'https://exemplo.com/img/painel-boho.png', 1),
                                                                                (2, 'Suporte para Planta Suspensa', 'Suporte em macramê para vasos de plantas, ideal para decoração interna.', 45.00, 'https://exemplo.com/img/suporte-planta.png', 2),
                                                                                (3, 'Flâmula de Macramê Geométrica', 'Flâmula decorativa de parede com detalhes geométricos e franjas.', 120.00, 'https://exemplo.com/img/flamula.png', 1);

-- ==========================================
-- 3. Tabela de Endereços (referenciando tb_user)
-- ==========================================
INSERT INTO enderecos (id, usuario_id, logradouro, numero, complemento, bairro, cidade, estado, cep) VALUES
                                                                                                         (1, 1, 'Rua das Flores', '123', 'Apto 42', 'Centro', 'São Paulo', 'SP', '01001-000'),
                                                                                                         (2, 2, 'Avenida Brasil', '1500', 'Bloco B', 'Jardins', 'Rio de Janeiro', 'RJ', '20040-000');

-- ==========================================
-- 4. Tabela de Pedidos (referenciando tb_user)
-- ==========================================
INSERT INTO pedidos (id, data, usuario_id) VALUES
                                               (1, '2026-09-22 14:30:00', 1),
                                               (2, '2026-09-22 16:00:00', 2);

-- ==========================================
-- 5. Tabela de Itens do Pedido (Chave Composta)
-- ==========================================
INSERT INTO itens_do_pedido (pedido_id, produto_id, preco, quantidade) VALUES
                                                                           (1, 1, 250.00, 1), -- 1 Painel Boho no Pedido 1
                                                                           (1, 2, 45.00, 2),  -- 2 Suportes de Planta no Pedido 1
                                                                           (2, 3, 120.00, 1);  -- 1 Flâmula no Pedido 2
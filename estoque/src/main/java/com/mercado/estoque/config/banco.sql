-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 09/02/2026 às 19:45
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `mercado`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `item_pedido`
--

CREATE TABLE `item_pedido` (
  `id` bigint(20) NOT NULL,
  `preco_unitario` double DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `pedido_id` bigint(20) DEFAULT NULL,
  `produto_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `item_pedido`
--

INSERT INTO `item_pedido` (`id`, `preco_unitario`, `quantidade`, `pedido_id`, `produto_id`) VALUES
(1, 25.9, 1, 1, 1),
(2, 25.9, 1, 2, 1),
(3, 25.9, 1, 3, 1),
(4, 25.9, 1, 4, 1),
(5, 25.9, 1, 5, 1),
(6, 25.9, 1, 6, 1),
(7, 8.5, 1, 6, 2),
(8, 4.2, 1, 6, 3);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pedido`
--

CREATE TABLE `pedido` (
  `id` bigint(20) NOT NULL,
  `data_criacao` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `pedido`
--

INSERT INTO `pedido` (`id`, `data_criacao`) VALUES
(1, '2026-02-09 15:10:38.000000'),
(2, '2026-02-09 15:11:00.000000'),
(3, '2026-02-09 15:19:02.000000'),
(4, '2026-02-09 15:19:19.000000'),
(5, '2026-02-09 15:23:50.000000'),
(6, '2026-02-09 15:31:16.000000');

-- --------------------------------------------------------

--
-- Estrutura para tabela `produto`
--

CREATE TABLE `produto` (
  `id` bigint(20) NOT NULL,
  `ativo` bit(1) NOT NULL,
  `codigo_barras` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `preco` double NOT NULL,
  `quantidade_estoque` int(11) DEFAULT NULL,
  `url_imagem` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `produto`
--

INSERT INTO `produto` (`id`, `ativo`, `codigo_barras`, `nome`, `preco`, `quantidade_estoque`, `url_imagem`) VALUES
(1, b'1', '7891001', 'Arroz Integral 5kg', 25.9, 44, '/produtos/7891001.jpg'),
(2, b'1', '7891002', 'Feijão Carioca 1kg', 8.5, 39, '/produtos/7891002.jpg'),
(3, b'1', '7891003', 'Açúcar Refinado 1kg', 4.2, 29, '/produtos/7891003.jpg'),
(4, b'1', '7891004', 'Café Extra Forte 500g', 18.9, 20, '/produtos/7891004.jpg'),
(5, b'1', '7891005', 'Óleo de Soja 900ml', 6.8, 4, '/produtos/7891005.jpg'),
(6, b'1', '7891006', 'Leite Integral 1L', 5.2, 100, '/produtos/7891006.jpg'),
(7, b'1', '7891007', 'Macarrão Espaguete', 3.9, 60, '/produtos/7891007.jpg'),
(8, b'1', '7891008', 'Molho de Tomate Sachê', 2.1, 3, '/produtos/7891008.jpg'),
(9, b'1', '7891009', 'Detergente Neutro', 2.2, 25, '/produtos/7891009.jpg'),
(10, b'1', '7891010', 'Sabão em Pó 1kg', 14.5, 15, '/produtos/7891010.jpg'),
(11, b'1', '7890011', 'Amaciante', 11.187680269817932, 28, '/produtos/7891011.jpg'),
(12, b'1', '7890012', 'cebola', 8.22, 28, '/produtos/7891012.jpg'),
(13, b'1', '7890013', 'Creme de alho', 11.187680269817932, 28, '/produtos/7891013.jpg'),
(14, b'1', '7890014', 'File', 70, 28, '/produtos/7891014.jpg'),
(15, b'1', '7890015', 'Manteiga', 20, 28, '/produtos/7891015.jpg'),
(16, b'1', '7890016', 'Creme dental', 11.187680269817932, 28, '/produtos/7891016.jpg'),
(17, b'1', '7890017', 'Requeijão', 11.187680269817932, 28, '/produtos/7891013.jpg'),
(18, b'1', '7890018', 'Sabonete', 70, 28, '/produtos/7891014.jpg'),
(19, b'1', '7890019', 'Sal', 4, 28, '/produtos/7891015.jpg'),
(20, b'1', '7890020', 'Tomate', 11.187680269817932, 28, '/produtos/7891016.jpg');
-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `login` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuario`
--

INSERT INTO `usuario` (`id`, `login`, `senha`) VALUES
(1, 'ana', '$2a$10$3ueGjE2th3R6487odqN5tuyiv73TP.uxar0l0FIZFObh41aEyWiJK');

-- --------------------------------------------------------

--
-- Estrutura para tabela `venda`
--

CREATE TABLE `venda` (
  `id` bigint(20) NOT NULL,
  `data_venda` datetime(6) NOT NULL,
  `valor_total` double NOT NULL,
  `pedido_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `venda`
--

INSERT INTO `venda` (`id`, `data_venda`, `valor_total`, `pedido_id`) VALUES
(1, '2026-02-09 15:10:38.000000', 25.9, 1),
(2, '2026-02-09 15:11:00.000000', 25.9, 2),
(3, '2026-02-09 15:19:02.000000', 25.9, 3),
(4, '2026-02-09 15:19:19.000000', 25.9, 4),
(5, '2026-02-09 15:23:50.000000', 25.9, 5),
(6, '2026-02-09 15:31:16.000000', 38.6, 6);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `item_pedido`
--
ALTER TABLE `item_pedido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK60ym08cfoysa17wrn1swyiuda` (`pedido_id`),
  ADD KEY `FKtk55mn6d6bvl5h0no5uagi3sf` (`produto_id`);

--
-- Índices de tabela `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `produto`
--
ALTER TABLE `produto`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK4lbdi5d3x5u70wuhqo1nvg98i` (`codigo_barras`);

--
-- Índices de tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKpm3f4m4fqv89oeeeac4tbe2f4` (`login`);

--
-- Índices de tabela `venda`
--
ALTER TABLE `venda`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKs8pqn46pvuooc77v7wx6kgvox` (`pedido_id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `item_pedido`
--
ALTER TABLE `item_pedido`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de tabela `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `produto`
--
ALTER TABLE `produto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `venda`
--
ALTER TABLE `venda`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `item_pedido`
--
ALTER TABLE `item_pedido`
  ADD CONSTRAINT `FK60ym08cfoysa17wrn1swyiuda` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`),
  ADD CONSTRAINT `FKtk55mn6d6bvl5h0no5uagi3sf` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`);

--
-- Restrições para tabelas `venda`
--
ALTER TABLE `venda`
  ADD CONSTRAINT `FKqpyibmhemoyrwumfhbqop8xld` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

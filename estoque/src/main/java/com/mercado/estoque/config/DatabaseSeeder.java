package com.mercado.estoque.config;

import com.mercado.estoque.model.Produto;
import com.mercado.estoque.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(ProdutoRepository repository) {
        return args -> {
            // Verifica se o banco já possui os dados do SQL ou se está vazio
            if (repository.count() == 0) {
                System.out.println("🌱 Banco vazio. Iniciando Seed de 100 produtos compatível com mercado.sql...");

                List<Produto> listaProdutos = new ArrayList<>();

                // 1. Itens principais para teste rápido
                listaProdutos.add(criarProduto("Arroz Integral 5kg", "7891001", 25.90, 50));
                listaProdutos.add(criarProduto("Feijão Carioca 1kg", "7891002", 8.50, 40));
                listaProdutos.add(criarProduto("Açúcar Refinado 1kg", "7891003", 4.20, 30));
                listaProdutos.add(criarProduto("Café Extra Forte 500g", "7891004", 18.90, 20));
                listaProdutos.add(criarProduto("Óleo de Soja 900ml", "7891005", 6.80, 4));
                listaProdutos.add(criarProduto("Leite Integral 1L", "7891006", 5.20, 100));

                // 2. Loop para completar 100 itens (Gera códigos e preços variados)
                for (int i = listaProdutos.size() + 1; i <= 100; i++) {
                    String codigo = "789" + String.format("%04d", i);
                    double precoAleatorio = 5.0 + (Math.random() * 45.0);
                    int estoqueAleatorio = (int) (Math.random() * 50) + 1;
                    
                    listaProdutos.add(criarProduto(
                        "Produto Exemplo " + i, 
                        codigo, 
                        precoAleatorio, 
                        estoqueAleatorio
                    ));
                }

                repository.saveAll(listaProdutos);
                System.out.println("✅ Cadastro concluído! 100 produtos inseridos conforme estrutura SQL.");
            } else {
                System.out.println("ℹ️ O banco já possui produtos. Seed ignorado para evitar duplicidade.");
            }
        };
    }

    private Produto criarProduto(String nome, String cod, double preco, int qtd) {
        Produto p = new Produto();
        p.setNome(nome);
        p.setCodigoBarras(cod); // Corresponde a codigo_barras no SQL
        p.setPreco(preco);
        p.setQuantidadeEstoque(qtd); // Corresponde a quantidade_estoque no SQL
        p.setAtivo(true);
        // O SQL usa url_imagem varchar(255)
        p.setUrlImagem("/produtos/" + cod + ".jpg");
        return p;
    }
}
package com.vendasfinal.sistema.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    // Caminho relativo que aponta para a pasta static do seu projeto no Eclipse
    private final String uploadDir = "src/main/resources/static/img/";

    /**
     * Salva o arquivo no disco e retorna o nome gerado.
     * @param arquivo O arquivo vindo do formulário (MultipartFile)
     * @param subPasta A subpasta dentro de img (ex: "perfil/")
     * @return String Nome único do arquivo salvo
     */
    public String salvarArquivo(MultipartFile arquivo, String subPasta) {
        if (arquivo.isEmpty()) {
            return null;
        }

        try {
            // 1. Define e cria o diretório se não existir
            Path diretorioPath = Paths.get(uploadDir + subPasta).toAbsolutePath().normalize();
            
            if (!Files.exists(diretorioPath)) {
                Files.createDirectories(diretorioPath);
            }

            // 2. Gera um nome único com UUID para evitar nomes duplicados
            String nomeOriginal = arquivo.getOriginalFilename();
            String extensao = "";
            
            if (nomeOriginal != null && nomeOriginal.contains(".")) {
                extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            }
            
            String nomeArquivo = UUID.randomUUID().toString() + extensao;

            // 3. Define o destino final (Caminho + Nome)
            Path destino = diretorioPath.resolve(nomeArquivo);

            // 4. Copia o arquivo (InputStream) para o destino físico
            Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            // Retorna apenas o nome do arquivo para salvar no banco de dados
            return nomeArquivo;

        } catch (IOException e) {
            throw new RuntimeException("Erro fatal: Não foi possível salvar o arquivo no disco. " + e.getMessage());
        }
    }
}
package mercado.emark.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/imagem/produto")
public class ImagemProdutoPublicoController {

    // Caminho absoluto fixo para Windows
    private final Path uploadPath = Paths.get("C:/pasta-faculdade/emark/uploads/imagemProduto");

    @GetMapping("/{nomeImagem:.+}")
    public ResponseEntity<Resource> baixarImagem(@PathVariable String nomeImagem) {
        try {
            // Verifica e cria o diretório se não existir
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path caminhoImagem = uploadPath.resolve(nomeImagem).normalize();
            
            // Verificação adicional de segurança
            if (!caminhoImagem.startsWith(uploadPath)) {
                return ResponseEntity.badRequest().build();
            }

            Resource recurso = new UrlResource(caminhoImagem.toUri());

            if (!recurso.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Detecta o tipo MIME automaticamente
            String contentType = Files.probeContentType(caminhoImagem);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                    .body(recurso);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
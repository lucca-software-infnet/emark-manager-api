package mercado.emark.controller;

import mercado.emark.model.Cliente;
import mercado.emark.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/imagem/cliente")
public class UploadImagemClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    private final String diretorioImagens = "src/main/resources/static/imagemCliente";

    @PostMapping("/{id}")
    public ResponseEntity<String> uploadImagem(@PathVariable Integer id, @RequestParam("imagem") MultipartFile imagem) {
        if (imagem.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhuma imagem enviada");
        }

        if (!imagem.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body("Apenas arquivos de imagem são permitidos");
        }

        clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        try {
            Path uploadPath = Paths.get(new File(diretorioImagens).getAbsolutePath());
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String nomeImagem = id + ".jpg";
            Path caminhoImagem = uploadPath.resolve(nomeImagem);
            imagem.transferTo(caminhoImagem);

            return ResponseEntity.ok("Imagem enviada com sucesso!");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao salvar imagem: " + e.getMessage());
        }
    }
}

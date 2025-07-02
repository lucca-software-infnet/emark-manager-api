package mercado.emark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import mercado.emark.model.ImagemProduto;
import mercado.emark.model.Produto;
import mercado.emark.repository.ImagemProdutoRepository;
import mercado.emark.repository.ProdutoRepository;
import mercado.emark.service.ImagemProdutoService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/imagem/produto")
public class ImagemProdutoController {

    @Autowired
    private ImagemProdutoService imagemProdutoService;

    @PostMapping("/{produtoId}")
    public ResponseEntity<String> uploadImagem(
            @PathVariable Integer produtoId,
            @RequestParam("imagem") MultipartFile imagem) {

        if (imagem.isEmpty() || !imagem.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body("Arquivo inválido");
        }

        try {
            imagemProdutoService.salvarImagem(produtoId, imagem);
            return ResponseEntity.ok("Imagem enviada com sucesso");

        } catch (RuntimeException | IOException e) {
            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/listar/{produtoId}")
    public ResponseEntity<List<Map<String, Object>>> listarImagens(@PathVariable Integer produtoId) {
        List<Map<String, Object>> imagens = imagemProdutoService.listarImagensProduto(produtoId)
                .stream()
                .map(img -> {
                    Map<String, Object> mapa = new HashMap<>();
                    mapa.put("id", img.getId());
                    mapa.put("nomeArquivo", img.getNomeArquivo());
                    return mapa;
                })
                .toList();
        return ResponseEntity.ok(imagens);
    }
    

    @DeleteMapping("/{imagemId}")
    public ResponseEntity<String> deletarImagem(@PathVariable Integer imagemId) {
        try {
            imagemProdutoService.deletarImagem(imagemId);
            return ResponseEntity.ok("Imagem deletada com sucesso");
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
        }
    }
}



package mercado.emark.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/imagem/funcionario")
public class ImagemFuncionarioController {

    private static final Logger logger = LoggerFactory.getLogger(ImagemFuncionarioController.class);

    // Caminho correto da sua pasta de imagens
    private final String diretorioImagens = "C:/pasta-faculdade/emark/src/main/resources/static/imagemFuncionario";

    @RequestMapping(
            value = "/{id}",
            method = {RequestMethod.GET, RequestMethod.HEAD},
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<Resource> obterImagem(@PathVariable Integer id) throws MalformedURLException {
        logger.info("Requisição recebida para imagem do funcionário com id: {}", id);

        String nomeImagem = id + ".jpg";
        Path caminhoImagem = Paths.get(diretorioImagens).resolve(nomeImagem);
        logger.debug("Caminho da imagem: {}", caminhoImagem.toAbsolutePath());

        Resource recurso = new UrlResource(caminhoImagem.toUri());

        if (recurso.exists() && recurso.isReadable()) {
            logger.info("Imagem encontrada e legível. Retornando imagem.");
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(recurso);
        } else {
            logger.warn("Imagem NÃO encontrada ou NÃO legível para o id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}



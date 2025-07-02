package mercado.emark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import mercado.emark.model.ImagemProduto;
import mercado.emark.model.Produto;
import mercado.emark.repository.ImagemProdutoRepository;
import mercado.emark.repository.ProdutoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;


@Service
public class ImagemProdutoService {

    @Value("${app.upload.dir:${user.home}/uploads/imagemProduto}")
    private String uploadDir;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    public String salvarImagem(Integer produtoId, MultipartFile imagem) throws IOException {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getImagens().size() >= 10) {
            throw new RuntimeException("Limite de 10 imagens por produto atingido");
        }

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String nomeImagem = UUID.randomUUID() + "-" + StringUtils.cleanPath(imagem.getOriginalFilename());
        Path caminhoImagem = uploadPath.resolve(nomeImagem);
        Files.copy(imagem.getInputStream(), caminhoImagem, StandardCopyOption.REPLACE_EXISTING);

        ImagemProduto imagemProduto = new ImagemProduto();
        imagemProduto.setNomeArquivo(nomeImagem);
        imagemProduto.setProduto(produto);
        imagemProdutoRepository.save(imagemProduto);

        return nomeImagem;
    }

    public List<ImagemProduto> listarImagensProduto(Integer produtoId) {
        return imagemProdutoRepository.findByProdutoId(produtoId);
    }

    public void deletarImagem(Integer imagemId) throws IOException {
        ImagemProduto imagemProduto = imagemProdutoRepository.findById(imagemId)
                .orElseThrow(() -> new RuntimeException("Imagem não encontrada"));

        Path caminhoImagem = Paths.get(uploadDir).resolve(imagemProduto.getNomeArquivo());
        Files.deleteIfExists(caminhoImagem);

        imagemProdutoRepository.delete(imagemProduto);
    }
}



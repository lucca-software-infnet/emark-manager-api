package mercado.emark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mercado.emark.model.ImagemProduto;

public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Integer> {
    List<ImagemProduto> findByProdutoId(Integer produtoId);
}

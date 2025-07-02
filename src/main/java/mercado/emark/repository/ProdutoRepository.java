package mercado.emark.repository;

import mercado.emark.model.Produto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional<Produto> findByCodigo(Integer codigo);
    boolean existsByCodigo(Integer codigo);
    Optional<Produto> findById(Integer id); 
}

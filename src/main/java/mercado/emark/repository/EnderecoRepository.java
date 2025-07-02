package mercado.emark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mercado.emark.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {}


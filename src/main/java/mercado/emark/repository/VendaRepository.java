package mercado.emark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mercado.emark.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {
}
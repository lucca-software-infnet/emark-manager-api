package mercado.emark.service;

import mercado.emark.dto.ProdutoDTO;
import mercado.emark.model.Produto;
import mercado.emark.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto criarProduto(ProdutoDTO produtoDTO) {
        if (produtoRepository.existsByCodigo(produtoDTO.getCodigo())) {
            throw new IllegalArgumentException("Já existe um produto com este código");
        }

        Produto produto = new Produto();
        produto.setCodigo(produtoDTO.getCodigo());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setValidade(produtoDTO.getValidade());
        produto.setVolume(produtoDTO.getVolume());
        produto.setQuantidade(produtoDTO.getQuantidade());
        produto.setPrecoCusto(produtoDTO.getPrecoCusto());
        produto.setPrecoVenda(produtoDTO.getPrecoVenda());
        produto.setMarca(produtoDTO.getMarca());
        produto.setDepartamento(produtoDTO.getDepartamento());
        produto.setPrateleira(produtoDTO.getPrateleira());
        
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto atualizarProduto(Integer id, ProdutoDTO produtoDTO) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        // Atualiza apenas campos permitidos (não atualiza o código)
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setValidade(produtoDTO.getValidade());
        produto.setVolume(produtoDTO.getVolume());
        produto.setQuantidade(produtoDTO.getQuantidade());
        produto.setPrecoCusto(produtoDTO.getPrecoCusto());
        produto.setPrecoVenda(produtoDTO.getPrecoVenda());
        produto.setMarca(produtoDTO.getMarca());
        produto.setDepartamento(produtoDTO.getDepartamento());
        produto.setPrateleira(produtoDTO.getPrateleira());
        
        return produtoRepository.save(produto);
    }

    @Transactional
    public void deletarProduto(Integer codigo) {
        Produto produto = produtoRepository.findByCodigo(codigo)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        produtoRepository.delete(produto);
    }

    public Optional<Produto> buscarPorCodigo(Integer codigo) {
        return produtoRepository.findByCodigo(codigo);
    }
    

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
    
    public Produto buscarPorId(Integer id) {
        return produtoRepository.findById(id).orElse(null);
    }
    
}

package mercado.emark.controller;

import mercado.emark.dto.ProdutoDTO;
import mercado.emark.model.Produto;
import mercado.emark.service.ProdutoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarProduto(@RequestBody ProdutoDTO produtoDTO) {
        try {
            Produto produto = produtoService.criarProduto(produtoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(produto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    // @PutMapping("/atualizar/{codigo}")
    // public ResponseEntity<?> atualizarPorCodigo(
    //         @PathVariable Integer codigo,
    //         @Valid @RequestBody ProdutoDTO produtoDTO) {

    //     try {
    //         if (!codigo.equals(produtoDTO.getCodigo())) {
    //             return ResponseEntity.badRequest().body("Código na URL não corresponde ao corpo");
    //         }

    //         Produto produto = produtoService.atualizarProduto(codigo, produtoDTO);
    //         return ResponseEntity.ok(produto);

    //     } catch (EntityNotFoundException e) {
    //         return ResponseEntity.notFound().build();
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     } catch (Exception e) {
    //         return ResponseEntity.internalServerError()
    //                 .body("Erro ao atualizar produto: " + e.getMessage());
    //     }
    // }

    @PutMapping("/atualizarViaId/{id}")
    public ResponseEntity<?> atualizarPorId(
            @PathVariable Integer id,
            @Valid @RequestBody ProdutoDTO produtoDTO) {

        try {
            if (!id.equals(produtoDTO.getId())) {
                return ResponseEntity.badRequest().body("ID na URL não corresponde ao corpo da requisição");
            }

            Produto produto = produtoService.atualizarProduto(id, produtoDTO);
            return ResponseEntity.ok(produto);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Integer id) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/buscarporcodigo/{codigo}")
    public ResponseEntity<?> buscarProduto(@PathVariable("codigo") Integer codigo) {
        Optional<Produto> produto = produtoService.buscarPorCodigo(codigo);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }

    @DeleteMapping("/deletar/{codigo}")
    public ResponseEntity<?> deletarProduto(@PathVariable Integer codigo) {
        try {
            produtoService.deletarProduto(codigo);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao deletar produto: " + e.getMessage());
        }
    }
}

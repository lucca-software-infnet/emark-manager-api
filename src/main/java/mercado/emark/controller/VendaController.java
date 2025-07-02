package mercado.emark.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mercado.emark.dto.VendaRequestDTO;
import mercado.emark.model.Venda;
import mercado.emark.service.VendaService;


import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody VendaRequestDTO dto) {
        Venda venda = vendaService.salvarVenda(dto);
        return ResponseEntity.ok(venda);
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = vendaService.listarVendas();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVenda(@PathVariable Integer id) {
        Venda venda = vendaService.buscarPorId(id);
        return ResponseEntity.ok(venda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> atualizarVenda(@PathVariable Integer id, @RequestBody VendaRequestDTO dto) {
        Venda vendaAtualizada = vendaService.atualizarVenda(id, dto);
        return ResponseEntity.ok(vendaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable Integer id) {
        vendaService.deletarVenda(id);
        return ResponseEntity.noContent().build();
    }
}



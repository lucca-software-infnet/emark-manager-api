package mercado.emark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mercado.emark.dto.ClienteDTO;
import mercado.emark.dto.ClienteUpdateDTO;
import mercado.emark.model.Cliente;
import mercado.emark.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.cadastrarCliente(clienteDTO);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Cliente> atualizarCliente(@RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        Cliente cliente = clienteService.atualizarCliente(clienteUpdateDTO);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/buscarporid/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable("id") Integer id) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/buscarporcpf/{cpf}")
    public ResponseEntity<Cliente> buscarClientePorCpf(@PathVariable("cpf") String cpf) {
        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable("id") Integer id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
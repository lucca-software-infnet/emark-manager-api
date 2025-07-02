package mercado.emark.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mercado.emark.dto.FuncionarioDTO;
import mercado.emark.dto.FuncionarioUpdateDTO;
import mercado.emark.model.Funcionario;
import mercado.emark.service.FuncionarioService;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarFuncionario(@Valid @RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            Funcionario funcionarioSalvo = funcionarioService.cadastrarFuncionario(funcionarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarFuncionario(@Valid @RequestBody FuncionarioUpdateDTO funcionarioUpdateDTO) {
        try {
            Funcionario funcionarioAtualizado = funcionarioService.atualizarFuncionario(funcionarioUpdateDTO);
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Funcionario>> listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/buscarporid/{id}")
    public ResponseEntity<?> buscarFuncionarioPorId(@PathVariable("id") Integer id) {
        try {
            Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(id);
            return ResponseEntity.ok(funcionario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/buscarporcpf/{cpf}")
    public ResponseEntity<?> buscarFuncionarioPorCpf(@PathVariable("cpf") String cpf) {
        try {
            Funcionario funcionario = funcionarioService.buscarFuncionarioPorCpf(cpf);
            return ResponseEntity.ok(funcionario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable("id") Integer id) {
        try {
            funcionarioService.deletarFuncionario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
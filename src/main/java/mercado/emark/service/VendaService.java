package mercado.emark.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mercado.emark.dto.ItemVendaDTO;
import mercado.emark.dto.VendaRequestDTO;
import mercado.emark.model.Cliente;
import mercado.emark.model.ItemVenda;
import mercado.emark.model.Produto;
import mercado.emark.model.Venda;
import mercado.emark.repository.ClienteRepository;
import mercado.emark.repository.ProdutoRepository;
import mercado.emark.repository.VendaRepository;


@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(VendaRepository vendaRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Venda salvarVenda(VendaRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    
        Venda venda = new Venda();
        venda.setDataVenda(dto.getDataVenda());
        venda.setNumeroPedido(dto.getNumeroPedido());
        venda.setFormaPagamento(dto.getFormaPagamento());
        venda.setTipoPagamento(dto.getTipoPagamento());
        venda.setBandeira(dto.getBandeira());
        venda.setCliente(cliente);
    
        List<ItemVenda> itens = new ArrayList<>();
        float valorTotal = 0;
    
        for (ItemVendaDTO itemDTO : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoCodigo())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoCodigo()));
    
            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(itemDTO.getPrecoUnitario());
            item.setVenda(venda);
    
            valorTotal += itemDTO.getQuantidade() * itemDTO.getPrecoUnitario();
            itens.add(item);
        }
    
        venda.getItens().clear();
        venda.getItens().addAll(itens);
        venda.setValorTotal(valorTotal);
    
        return vendaRepository.save(venda);
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    public Venda buscarPorId(Integer id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }

    @Transactional
    public Venda atualizarVenda(Integer id, VendaRequestDTO dto) {
        Venda vendaExistente = buscarPorId(id);

        // Atualizar campos
        vendaExistente.setDataVenda(dto.getDataVenda());
        vendaExistente.setNumeroPedido(dto.getNumeroPedido());
        vendaExistente.setFormaPagamento(dto.getFormaPagamento());
        vendaExistente.setTipoPagamento(dto.getTipoPagamento());
        vendaExistente.setBandeira(dto.getBandeira());

        // Atualizar cliente
        vendaExistente.setCliente(clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));

        // Atualizar itens: limpar antigos e adicionar novos
        vendaExistente.getItens().clear();
        dto.getItens().forEach(itemDTO -> {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoCodigo())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoCodigo()));

            var itemVenda = new ItemVenda();
            itemVenda.setVenda(vendaExistente);
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(itemDTO.getQuantidade());
            itemVenda.setPrecoUnitario(itemDTO.getPrecoUnitario());
            vendaExistente.getItens().add(itemVenda);
        });

        // Recalcular valor total
        float total = 0;
        for (var item : vendaExistente.getItens()) {
            total += item.getQuantidade() * item.getPrecoUnitario();
        }
        vendaExistente.setValorTotal(total);

        return vendaRepository.save(vendaExistente);
    }

    public void deletarVenda(Integer id) {
        Venda venda = buscarPorId(id);
        vendaRepository.delete(venda);
    }
}
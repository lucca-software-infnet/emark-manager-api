package mercado.emark.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_venda", nullable = false)
    private Date dataVenda;

    @Column(name = "numero_pedido", nullable = false, unique = true)
    private String numeroPedido;

    @Column(name = "valor_total", nullable = false)
    private Float valorTotal;

    @Column(name = "forma_pagamento", nullable = false)
    private String formaPagamento;

    @Column(name = "tipo_pagamento")
    private String tipoPagamento;

    @Column(name = "bandeira")
    private String bandeira;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenda> itens = new ArrayList<>();

    public Venda(Integer id, Date dataVenda, String numeroPedido, Float valorTotal, 
               String formaPagamento, String tipoPagamento, String bandeira, Cliente cliente) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.numeroPedido = numeroPedido;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.tipoPagamento = tipoPagamento;
        this.bandeira = bandeira;
        this.cliente = cliente;
    }
    
    
    public Venda() {}
}
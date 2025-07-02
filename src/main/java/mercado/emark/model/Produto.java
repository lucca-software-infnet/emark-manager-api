package mercado.emark.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mercado.emark.enums.Departamento;

@Getter
@Setter
@Entity
@Table(name = "produto", uniqueConstraints = {
        @UniqueConstraint(columnNames = "codigo", name = "uk_produto_codigo")
})
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ImagemProduto> imagens = new ArrayList<>();

    @Column(unique = true, nullable = false)
    private Integer codigo;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "validade")
    private Date validade;

    @Column(name = "volume", nullable = false)
    private Integer volume;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade = 0;

    @Column(name = "preco_custo", nullable = false)
    private Float precoCusto;

    @Column(name = "preco_venda", nullable = false)
    private Float precoVenda;

    @Column(name = "marca", length = 100)
    private String marca;

    @Enumerated(EnumType.STRING)
    @Column(name = "departamento", nullable = false, length = 100)
    private Departamento departamento;

    @Column(name = "prateleira")
    private Integer prateleira;

    @Column(name = "data_registro", nullable = false, updatable = false)
    private LocalDateTime dataRegistro;

    public Produto() {
    }

    public Produto(Integer codigo, String descricao, Date validade, Integer volume, Integer quantidade,
            Float precoCusto, Float precoVenda, String marca, Departamento departamento, Integer prateleira) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.validade = validade;
        this.volume = volume;
        this.quantidade = quantidade;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.marca = marca;
        this.departamento = departamento;
        this.prateleira = prateleira;
    }

    @PrePersist
    public void prePersist() {
        this.dataRegistro = LocalDateTime.now();
        if (this.quantidade == null) {
            this.quantidade = 0;
        }
    }
}
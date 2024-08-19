package br.com.pavaneli.motel.entity;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import br.com.pavaneli.motel.dto.ProdutoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private Double preco;
    @Column(nullable = false)
    private String unidade;
    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "produto")
    @JsonManagedReference
    private List<ItemPedido> itensPedidos = new ArrayList<>();

    public Produto(ProdutoDTO produto) {
        BeanUtils.copyProperties(produto, this);
    }
}

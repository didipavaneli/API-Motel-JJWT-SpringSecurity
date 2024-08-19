package br.com.pavaneli.motel.entity;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.pavaneli.motel.dto.ItemPedidoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer quantidade;
    @Column(nullable = false)
    private Double precoTotal;

    @ManyToOne
    @JoinColumn(name = "hospedagem_id")
    @JsonBackReference
    private Hospedagem hospedagem;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    @JsonBackReference
    private Produto produto;

    public ItemPedido(ItemPedidoDTO itemPedidoDto) {
        BeanUtils.copyProperties(itemPedidoDto, this);
    }

    public Double efetuarPedido(Integer qtd) {
        Double soma = qtd * this.precoTotal;
        return soma;
    }
}

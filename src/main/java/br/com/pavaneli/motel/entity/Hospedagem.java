package br.com.pavaneli.motel.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import br.com.pavaneli.motel.dto.HospedagemDTO;
import br.com.pavaneli.motel.enums.StatusHospedagem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Hospedagem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String placa;

    private String descricao;

    @Column(nullable = false)
    private Date checkin;

    private Date checkout;

    private Double valor_hospedagem;

    private Double valor_total;

    private String obs;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusHospedagem status;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    @OneToMany(mappedBy = "hospedagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itensPedidos = new ArrayList<>();

    public Hospedagem(HospedagemDTO hospedagemDto) {
        BeanUtils.copyProperties(hospedagemDto, this);
    }
}

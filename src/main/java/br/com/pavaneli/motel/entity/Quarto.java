package br.com.pavaneli.motel.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.pavaneli.motel.dto.QuartoDTO;
import br.com.pavaneli.motel.enums.StatusQuarto;
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
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer numero;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double valorHora;

    @Column(nullable = false)
    private Double valorPernoite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusQuarto status;

    @ManyToOne
    @JoinColumn(name = "tipoquarto_id")
    private TipoQuarto tipoQuarto;

    @OneToMany(mappedBy = "quarto")
    @JsonBackReference
    private List<Hospedagem> hospedagemAtual = new ArrayList<>();
    

    public Quarto(QuartoDTO quartoDto) {
        BeanUtils.copyProperties(quartoDto, this);
    }
}

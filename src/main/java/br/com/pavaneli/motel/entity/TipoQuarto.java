package br.com.pavaneli.motel.entity;

import org.springframework.beans.BeanUtils;

import br.com.pavaneli.motel.dto.TipoQuartoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TipoQuarto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String descricao;
    
//    @OneToMany(mappedBy = "tipo-quarto")
//    @JsonManagedReference
//    private List<Quarto> quartos = new ArrayList<>();

    public TipoQuarto(TipoQuartoDTO tipoQuarto) {
        BeanUtils.copyProperties(tipoQuarto, this);
    }
}

package br.com.pavaneli.motel.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.pavaneli.motel.entity.Hospedagem;
import br.com.pavaneli.motel.entity.ItemPedido;
import br.com.pavaneli.motel.entity.Quarto;
import br.com.pavaneli.motel.enums.StatusHospedagem;
import br.com.pavaneli.motel.enums.StatusQuarto;
import br.com.pavaneli.motel.repository.HospedagemRepository;
import br.com.pavaneli.motel.repository.ItemPedidoRepository;
import br.com.pavaneli.motel.repository.ProdutoRepository;
import br.com.pavaneli.motel.repository.QuartoRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class HospedagemDTO {
	private String idHospedagem;
	private Long quartoId;	
	private String placaHospedagem;
	private String descricaoHospedagem;	
	private Date checkin;
	private Date checkout;
	private Double valor_hospedagem;
	private Double valor_total;
	private String observacaoHospedagem;
	private String obsHospedagem;
	private String valorHospedagem;
	private String statusHospedagem;
	private String statusQuarto;
	private Quarto quarto;
	private String horainicial;
    private String horafinal;
    private String valorTotalHospedagem;
    private Long quartoHospedagem;
	private List<ItemPedido> itensPedidos = new ArrayList<>();
	
	
	@Autowired
    private HospedagemRepository hospedagemRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private QuartoRepository quartoRepository;
	
	
	public HospedagemDTO(Hospedagem hospedagem) {
		BeanUtils.copyProperties(hospedagem, this);
	}
	
	public Hospedagem toNovaHospedagem(QuartoRepository quartoRepository) {
        Hospedagem hospedagem = new Hospedagem();
        hospedagem.setPlaca(placaHospedagem);
        hospedagem.setDescricao(descricaoHospedagem);
        hospedagem.setObs(observacaoHospedagem);
        hospedagem.setStatus(StatusHospedagem.doValor(statusHospedagem));

        Instant now = Instant.now();
        Date checkinDate = Date.from(now);
        hospedagem.setCheckin(checkinDate);

        if (quartoId != null) {
            Quarto quarto = quartoRepository.findById(quartoId)
                    .orElseThrow(() -> new IllegalArgumentException("Quarto não encontrado"));
            hospedagem.setQuarto(quarto);
            quarto.setStatus(StatusQuarto.doValor(statusQuarto));
        }

        return hospedagem;
    }
	public String toDeleteHospedagem() {
		String id = idHospedagem;
		return id;
	}

	public Hospedagem toFecharHospedagem(QuartoRepository quartoRepository) {
        Hospedagem hospedagem = new Hospedagem();
        if (idHospedagem != null && !idHospedagem.isEmpty()) {
            hospedagem.setId(Long.parseLong(idHospedagem));
        }
        hospedagem.setPlaca(placaHospedagem);
        hospedagem.setDescricao(descricaoHospedagem);
        hospedagem.setValor_hospedagem(Double.parseDouble(valorHospedagem));
        hospedagem.setValor_total(Double.parseDouble(valorTotalHospedagem));
        hospedagem.setObs(obsHospedagem);
        hospedagem.setStatus(StatusHospedagem.doValor(statusHospedagem));
        if (quartoHospedagem != null) {
            Quarto quarto = quartoRepository.findById(quartoHospedagem)
                    .orElseThrow(() -> new IllegalArgumentException("Quarto não encontrado"));
            hospedagem.setQuarto(quarto);
            quarto.setStatus(StatusQuarto.doValor(statusQuarto));
        }

        return hospedagem;
    }
	
	
	@Override
    public String toString() {
        return "RequisicaoNovaHospedagem{" +
            "placaHospedagem='" + placaHospedagem + '\'' +
            ", descricaoHospedagem='" + descricaoHospedagem + '\'' +
            ", observacaoHospedagem='" + observacaoHospedagem + '\'' +
            ", quartoId=" + quartoId +
            ", statusHospedagem='" + statusHospedagem + '\'' +
            ", statusQuarto='" + statusQuarto + '\'' +
            '}';
    }

}


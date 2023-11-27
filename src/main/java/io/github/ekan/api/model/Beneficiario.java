package io.github.ekan.api.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiario {
	
	private Long id;
	private String Nome;
	private String Telefone;
	private LocalDateTime DataDeNascimento;
	private LocalDateTime DataInclusao;
	private LocalDateTime DataAtualizacao;
	private List<Documento> Documentos;
}

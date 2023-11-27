package io.github.ekan.api.model;

import java.time.LocalDateTime;

import io.github.ekan.api.enumeration.TipoDocumentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Documento {
	
	private Long id;
	private TipoDocumentoEnum TipoDocumento;
	private String Descricao;
	private LocalDateTime DataInclusao;
	private LocalDateTime DataAtualizacao;
}

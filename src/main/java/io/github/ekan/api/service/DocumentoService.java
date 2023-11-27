package io.github.ekan.api.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.ekan.api.enumeration.TipoDocumentoEnum;
import io.github.ekan.api.factory.DocumentoFactory;
import io.github.ekan.api.factory.impl.DocumentoFactoryImpl;
import io.github.ekan.api.model.Documento;

@Service
public class DocumentoService {
	
	private DocumentoFactory factory;
	
	private List<Documento> documentos;
	
	public void createFactory() {
		if(factory == null) {
			factory = new DocumentoFactoryImpl();
		}
	}

	public void createDocumentoList() {
		if(documentos == null) {
			documentos = new ArrayList<>();
		}
	}
	
	public boolean isJSONValid(String jsonInString) {
	    try {
	       return new ObjectMapper().readTree(jsonInString) != null;
	    } catch (IOException e) {
	       return false;
	    }
	}
	
	private long parseId(JSONObject documento) {
		return Long.valueOf((int) documento.get("id"));
	}
	
	private LocalDateTime parseDataDeNascimento(JSONObject documento) {
		var dataDeNascimento = (String) documento.get("dataDeNascimento");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
		return ZonedDateTime.parse(dataDeNascimento, formatter.withZone(ZoneId.of("UTC"))).toLocalDateTime();
	}
	
	private LocalDateTime parseDataInclusao(JSONObject documento) {
		var dataInclusao = (String) documento.get("dataInclusao");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
		return ZonedDateTime.parse(dataInclusao, formatter.withZone(ZoneId.of("UTC"))).toLocalDateTime();
	}
	
	private LocalDateTime parseDataAtualizacao(JSONObject documento) {
		var dataAtualizacao = (String) documento.get("dataAtualizacao");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
		return ZonedDateTime.parse(dataAtualizacao, formatter.withZone(ZoneId.of("UTC"))).toLocalDateTime();
	}
	
	private void setDocumentoValues(JSONObject jsonDocumento, Documento documento) {
		
		String orderNumber = (String) jsonDocumento.get("orderNumber");
		String type = (String) jsonDocumento.get("type");
		
		jsonDocumento.setId(orderNumber != null ? orderNumber : documento.getId());
		jsonDocumento.setDescricao(documento.getDescricao());
		jsonDocumento.setDataInclusao(jsonDocumento.get("dataInclusao") != null ? parseDataInclusao(jsonDocumento) : documento.getDataInclusao());
		jsonDocumento.setDataAtualizacao(jsonDocumento.get("dataAtualizacao") != null ? parseDataAtualizacao(jsonDocumento) : documento.getDataAtualizacao());
		documento.setTipoDocumento(type != null ? TipoDocumentoEnum.getEnum(type) : documento.getTipoDocumento());
	}
	
	public Documento create(JSONObject jsonDocumento) {
		
		createFactory();
		
		Documento documento = factory.createDocumento();
		documento.setId(parseId(jsonDocumento));
		setDocumentoValues(jsonDocumento, documento);
		
		return documento;
	}
	
	public Documento update(Documento documento, JSONObject jsonDocumento) {
		
		setDocumentoValues(jsonDocumento, documento);
		return documento;
	}

	public void add(Documento documento) {
		createDocumentoList();
		documentos.add(documento);
	}

	public List<Documento> find() {
		createDocumentoList();
		return documentos;
	}
	
	public Documento findById(long id) {
		return documentos.stream().filter(t -> id == t.getId()).collect(Collectors.toList()).get(0);
	}
	
	public void delete() {
		documentos.clear();
	}
	
	public void clearObjects() {
		documentos = null;
		factory = null;
	}

}

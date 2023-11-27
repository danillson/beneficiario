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

import io.github.ekan.api.factory.BeneficiarioFactory;
import io.github.ekan.api.factory.impl.BeneficiarioFactoryImpl;
import io.github.ekan.api.model.Beneficiario;

@Service
public class BeneficiarioService {
	
	private BeneficiarioFactory factory;
	
	private List<Beneficiario> beneficiarios;
	
	public void createFactory() {
		if(factory == null) {
			factory = new BeneficiarioFactoryImpl();
		}
	}

	public void createBeneficiarioList() {
		if(beneficiarios == null) {
			beneficiarios = new ArrayList<>();
		}
	}
	
	public boolean isJSONValid(String jsonInString) {
	    try {
	       return new ObjectMapper().readTree(jsonInString) != null;
	    } catch (IOException e) {
	       return false;
	    }
	}
	
	private long parseId(JSONObject beneficiario) {
		return Long.valueOf((int) beneficiario.get("id"));
	}
	
	private LocalDateTime parseDataDeNascimento(JSONObject beneficiario) {
		var dataDeNascimento = (String) beneficiario.get("dataDeNascimento");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
		return ZonedDateTime.parse(dataDeNascimento, formatter.withZone(ZoneId.of("UTC"))).toLocalDateTime();
	}
	
	private LocalDateTime parseDataInclusao(JSONObject beneficiario) {
		var dataInclusao = (String) beneficiario.get("dataInclusao");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
		return ZonedDateTime.parse(dataInclusao, formatter.withZone(ZoneId.of("UTC"))).toLocalDateTime();
	}
	
	private LocalDateTime parseDataAtualizacao(JSONObject beneficiario) {
		var dataAtualizacao = (String) beneficiario.get("dataAtualizacao");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
		return ZonedDateTime.parse(dataAtualizacao, formatter.withZone(ZoneId.of("UTC"))).toLocalDateTime();
	}
	
	private void setBeneficiarioValues(JSONObject jsonBeneficiario, Beneficiario beneficiario) {
		
		String orderNumber = (String) jsonBeneficiario.get("orderNumber");
		String type = (String) jsonBeneficiario.get("type");
		
		jsonBeneficiario.setId(orderNumber != null ? orderNumber : beneficiario.getId());
		jsonBeneficiario.setNome(beneficiario.getNome());
		jsonBeneficiario.setTelefone(beneficiario.getTelefone());
		jsonBeneficiario.setDataDeNascimento(jsonBeneficiario.get("dataDeNascimento") != null ? parseDataDeNascimento(jsonBeneficiario) : beneficiario.getDataDeNascimento());
		jsonBeneficiario.setDataInclusao(jsonBeneficiario.get("dataInclusao") != null ? parseDataInclusao(jsonBeneficiario) : beneficiario.getDataInclusao());
		jsonBeneficiario.setDataAtualizacao(jsonBeneficiario.get("dataAtualizacao") != null ? parseDataAtualizacao(jsonBeneficiario) : beneficiario.getDataAtualizacao());
	}
	
	public Beneficiario create(JSONObject jsonBeneficiario) {
		
		createFactory();
		
		Beneficiario beneficiario = factory.createBeneficiario();
		beneficiario.setId(parseId(jsonBeneficiario));
		setBeneficiarioValues(jsonBeneficiario, beneficiario);
		
		return beneficiario;
	}
	
	public Beneficiario update(Beneficiario beneficiario, JSONObject jsonBeneficiario) {
		
		setBeneficiarioValues(jsonBeneficiario, beneficiario);
		return beneficiario;
	}

	public void add(Beneficiario beneficiario) {
		createBeneficiarioList();
		beneficiarios.add(beneficiario);
	}

	public List<Beneficiario> find() {
		createBeneficiarioList();
		return beneficiarios;
	}
	
	public Beneficiario findById(long id) {
		return beneficiarios.stream().filter(t -> id == t.getId()).collect(Collectors.toList()).get(0);
	}
	
	public void delete() {
		beneficiarios.clear();
	}
	
	public void clearObjects() {
		beneficiarios = null;
		factory = null;
	}

}

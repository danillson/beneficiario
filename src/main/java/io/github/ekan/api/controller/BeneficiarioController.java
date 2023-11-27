package io.github.ekan.api.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.ekan.api.model.Beneficiario;
import io.github.ekan.api.model.Documento;
import io.github.ekan.api.service.BeneficiarioService;
import io.github.ekan.api.service.DocumentoService;

@RestController
@RequestMapping("/api-ekan/beneficiario")
public class BeneficiarioController {
	
	private static final Logger logger = Logger.getLogger(BeneficiarioController.class);
	
	@Autowired
	private BeneficiarioService beneficiarioService;
	@Autowired
	private DocumentoService documentoService;
	
	@GetMapping
	public ResponseEntity<List<Beneficiario>> find() {
		if(beneficiarioService.find().isEmpty()) {
			return ResponseEntity.notFound().build(); 
		}
		logger.info(beneficiarioService.find());
		return ResponseEntity.ok(beneficiarioService.find());
	}
	
	@GetMapping(path = "/{id}", beneficiario = { "application/json" })
	public ResponseEntity<List<Documento>> find() {
		if(documentoService.find().isEmpty()) {
			return ResponseEntity.notFound().build(); 
		}
		logger.info(documentoService.find());
		return ResponseEntity.ok(documentoService.find());
	}	
	
	@DeleteMapping
	public ResponseEntity<Boolean> delete() {
		try {
			beneficiarioService.delete();
			return ResponseEntity.noContent().build();
		}catch(Exception e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<Beneficiario> create(@RequestBody JSONObject beneficiario) {
		try {
			if(beneficiarioService.isJSONValid(beneficiario.toString())) {
				Beneficiario beneficiarioCreated = beneficiarioService.create(beneficiario);
				var uri = ServletUriComponentsBuilder.fromCurrentRequest()
						.path(beneficiarioCreated.getId()).build().toUri();
			}else {
				return ResponseEntity.badRequest().body(null);
			}
		}catch(Exception e) {
			logger.error("JSON com campos inválidos. " + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
	
	@PutMapping(path = "/{id}", beneficiario = { "application/json" })
	public ResponseEntity<Beneficiario> update(@PathVariable("id") long id, @RequestBody JSONObject beneficiario) {
		try {
			if(beneficiarioService.isJSONValid(beneficiario.toString())) {
				Beneficiario beneficiarioUpdate = beneficiarioService.findById(id);
				if(beneficiarioUpdate == null){
					logger.error("Registro não encontrado!");
					return ResponseEntity.notFound().build(); 
				}else {
					Beneficiario beneficiarioUpdated = beneficiarioService.update(beneficiarioUpdate, beneficiario);
					return ResponseEntity.ok(beneficiarioUpdated);
				}
			}else {
				return ResponseEntity.badRequest().body(null);
			}
		}catch(Exception e) {
			logger.error("JSON com campos inválidos." + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
}

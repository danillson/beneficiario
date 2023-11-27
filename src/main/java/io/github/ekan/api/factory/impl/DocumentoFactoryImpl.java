package io.github.ekan.api.factory.impl;
import io.github.ekan.api.factory.DocumentoFactory;
import io.github.ekan.api.model.Documento;


public class DocumentoFactoryImpl implements DocumentoFactory {

	@Override
	public Documento createDocumento () {
		
		return new Documento();
	}

}

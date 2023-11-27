package io.github.ekan.api.factory.impl;

import io.github.ekan.api.factory.BeneficiarioFactory;
import io.github.ekan.api.model.Beneficiario;


public class BeneficiarioFactoryImpl implements BeneficiarioFactory {

	@Override
	public Beneficiario createBeneficiario () {
		
		return new Beneficiario();
	}

}

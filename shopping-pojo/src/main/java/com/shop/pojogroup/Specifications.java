package com.shop.pojogroup;

import com.shop.pojo.Specification;
import com.shop.pojo.SpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * 规格组合实体类
 * @author Administrator
 *
 */
public class Specifications implements Serializable{

	private Specification specification;
	
	private List<SpecificationOption> specificationOptionList;

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public List<SpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}

	public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}
	
	
	
	
}

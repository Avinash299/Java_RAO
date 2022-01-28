package com.raleys.api.cao.odt.schedule.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

public class ListValidationWrapper<E> {

	@Valid
    private List<E> list;
 
    public ListValidationWrapper() {
        this.list = new ArrayList<>();
    }
 
    public  ListValidationWrapper(List<E> list) {
        this.list = list;
    }

	public List<E> getList() {
		return this.list;
	}

	public void setList(List<E> list) {
		this.list = list;
	} 
}

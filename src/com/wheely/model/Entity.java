package com.wheely.model;

import com.google.gson.Gson;

/**
 * <b>Сущно�?ть</b><br />
 * Базовый кла�?�? дл�? в�?ех �?ущно�?тей в модели.
 * 
 * @author elf-zwölf
 */
public abstract class Entity {
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
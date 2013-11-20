package com.wheely.model;

import com.google.gson.Gson;

/**
 * <b>Сущность</b><br />
 * Базовый класс для всех сущностей в модели.
 */
public abstract class Entity {
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
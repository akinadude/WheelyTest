package com.wheely.utils;

public class Utils {
	
	/**
	 * Допустимаю длина строки
	 */
	public static final int TITLE_MAX_LENGHT = 11;
	
	/**
	 * Тэг перехода в DetailsActivity
	 */
	public final static String DETAILS_ACTIVITY_TAG = "details_activity_tag";
	
	/**
	 * Заглушка "нет данных"
	 */
	public final static String NO_DATA = "No data";
	
	/**
	 * Интервал авто-апдейта
	 */
    public static final long UPDATE_INTERVAL = 30 * 1000;
        
    /**
     * Таймаут для установки соединения
     */
    public static final int CONNECTION_TIMEOUT = 5 * 1000;
    
    /**
     * Сервер
     */
    public static final String SERVER_URL = "http://crazy-dev.wheely.com";	
}
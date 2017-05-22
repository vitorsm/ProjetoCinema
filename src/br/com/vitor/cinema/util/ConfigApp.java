package br.com.vitor.cinema.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigApp {
	private static ConfigApp configApp;
	private Properties properties;
	
	public static ConfigApp instance() throws ConfigAppException {
		if (configApp == null) {
			configApp = new ConfigApp();
		}
		
		return configApp;
	}
	
	private ConfigApp() throws ConfigAppException {
		
		FileInputStream arquivoIn = null;
		try {
			arquivoIn = new FileInputStream("config.cfg");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ConfigAppException("Não foi possível localizar o arquivo: " + e.getMessage());
		}
		
		properties = new Properties();
		try {
			properties.load(arquivoIn);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConfigAppException("Não foi possível realizar a leitura do arquivo: " + e.getMessage());
		}
	}
	
	public String getPropertie(String propName) {
		return properties.getProperty(propName);
	}
	
	
	public static void main(String[] args) throws ConfigAppException {
		ConfigApp c = ConfigApp.instance();
		System.out.println(c.getPropertie("usuario"));
		System.out.println(c.getPropertie("senha"));
		System.out.println(c.getPropertie("nomeBD"));
		System.out.println(c.getPropertie("senhaBD"));
	}
}

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
	
	public String getPropertie(String propName) throws ConfigAppException {
		String strPropertie = properties.getProperty(propName);
		
		if (strPropertie == null) {
			throw new ConfigAppException("Não foi possível localizar a propriedade " + propName + " no arquivo de configuração");
		}
		
		return strPropertie;
	}
	
}

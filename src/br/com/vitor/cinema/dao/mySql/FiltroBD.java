package br.com.vitor.cinema.dao.mySql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import br.com.vitor.cinema.dao.FiltroBusca;
import br.com.vitor.cinema.entidade.anotacoes.BDColuna;
import br.com.vitor.cinema.entidade.anotacoes.BDPrimaryKey;

public class FiltroBD implements FiltroBusca {
	private Object obj;
	private boolean isPrimaryKey;
	
	protected FiltroBD(Object obj) {
		this.obj= obj;
	}
	
	@Override
	public String getWhere() {
		String where = null;
		isPrimaryKey = false;
		
		Class<?> classe = obj.getClass();
		
		boolean foiFK = false;
		
		for (Method method : classe.getMethods()) {
			String whereAux = where;
//			if (method.isAnnotationPresent(BDPrimaryKey.class)) {
//				isPrimaryKey = true;
//			} else 
			if (method.isAnnotationPresent(BDColuna.class)) {
				boolean atualFK = false;
				if (method.isAnnotationPresent(BDPrimaryKey.class)) {
					atualFK = true;
					if (!isPrimaryKey) {
						where = null;
						isPrimaryKey = true;
					}
				}
				
				BDColuna bdColuna = method.getAnnotation(BDColuna.class);
				try {
					Object obj = method.invoke(this.obj);
					
					if (obj != null) {
						String str = null;
						try {
							str = getPrimaryKey(obj);
						} catch (DadoNaoCorrespondenteException e) {
							try {
								str = dataParaString(obj);
							} catch (DadoNaoCorrespondenteException ex) {
								str = obj.toString();
							}
						}
						
						if (str != null) {
							if (str.equals("")) {
								if (bdColuna.vazioNulo()) {
									str = null;
								}
							} else {
								try {
									str = numeroParaString(obj, bdColuna.vazioNulo());
								} catch (DadoNaoCorrespondenteException ex) {
								}
							}
						}
						
						if (!isPrimaryKey || (isPrimaryKey && atualFK)) {
							if (str == null) {
								if (bdColuna.consideraNulo()) {
									str = "=NULL";
									
									where = iniciaWhere(where) + 
											bdColuna.nomeCampo() + str;
									
									if (isPrimaryKey) {
										foiFK = true;
									}
								}
							} else {
								if (!str.equals("NULL")) {
									if (bdColuna.likeCoringa()) {
										str = " LIKE '%" + str + "%'";
									} else {
										str = "='" + str + "'";
									}
								}
								
								where = iniciaWhere(where) + 
										bdColuna.nomeCampo() + str;
								if (isPrimaryKey) {
									foiFK = true;
								}
							}
						}
					}
					
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
			if (isPrimaryKey && !foiFK) {
				isPrimaryKey = false;
				where = whereAux;
			}
		}
		
		if (where == null) {
			where = " 1";
		}
		
		return where;
	}
	
	private String getPrimaryKey(Object obj) throws DadoNaoCorrespondenteException {
		Class<?> classe = obj.getClass();
		
		String valor = null;
		
		boolean foiUm = false;
		
		for (Method method : classe.getMethods()) {
			if (method.isAnnotationPresent(BDPrimaryKey.class)) {
				foiUm = true;
//				BDPrimaryKey pKey = method.getAnnotation(BDPrimaryKey.class);
				try {
					Object ret = method.invoke(obj);
					if (ret != null) {
						valor = ret.toString();
						try {
							valor = numeroParaString(ret, true);
						} catch (DadoNaoCorrespondenteException e) {
							valor = ret.toString();
						}
					}
					
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (!foiUm) {
			throw new DadoNaoCorrespondenteException("Não se trata de uma foreign key", null);
		}
		
		return valor;
	}
	
	private String numeroParaString(Object obj, boolean zeroNulo) throws DadoNaoCorrespondenteException {
		String ret = null;
		
		if (obj instanceof Integer) {
			Integer i = (Integer) obj;
			
			if (i == 0) {
				if (!zeroNulo) {
					ret = i.intValue() + "";
				}
			} else {
				ret = i.intValue() + "";
			}
		} else if (obj instanceof Double) {
			Double i = (Double) obj;

			if (i == 0) {
				if (!zeroNulo) {
					ret = i.toString();
				}
			} else {
				ret = i.toString();
			}
		} else if (obj instanceof Float) {
			Float i = (Float) obj;

			if (i == 0) {
				if (!zeroNulo) {
					ret = i.toString();
				}
			} else {
				ret = i.toString();
			}
		} else if (obj instanceof Long) {
			Long i = (Long) obj;

			if (i == 0) {
				if (!zeroNulo) {
					ret = i.toString();
				}
			} else {
				ret = i.toString();
			}
		} else if (obj instanceof Byte) {
			Byte i = (Byte) obj;

			if (i == 0) {
				if (!zeroNulo) {
					ret = i.toString();
				}
			} else {
				ret = i.toString();
			}
		} else if (obj instanceof Short) {
			Short i = (Short) obj;

			if (i == 0) {
				if (!zeroNulo) {
					ret = i.toString();
				}
			} else {
				ret = i.toString();
			}
		} else {
			throw new DadoNaoCorrespondenteException("Não se tratad e um número", null);
		}
		
		return ret;
	}
	
	private String dataParaString(Object obj) throws DadoNaoCorrespondenteException {
		if (obj instanceof Date) {
			Date date = (Date) obj;
			java.sql.Date dt = new java.sql.Date(date.getTime());
			java.sql.Timestamp times = new java.sql.Timestamp(date.getTime());
			
			return times.toString();
//			System.out.println(times.toString());
		} else {
			throw new DadoNaoCorrespondenteException("Não se trata de uma data", null);
		}
	}
	
	private String iniciaWhere(String where) {
		if (where == null) {
			return " ";
		} else {
			return where + " AND ";
		}
	}
	
}

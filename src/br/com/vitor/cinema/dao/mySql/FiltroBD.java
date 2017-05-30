package br.com.vitor.cinema.dao.mySql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.vitor.cinema.dao.FiltroBusca;
import br.com.vitor.cinema.entidade.Filme;
import br.com.vitor.cinema.entidade.anotacoes.BDColuna;
import br.com.vitor.cinema.entidade.anotacoes.BDPrimaryKey;

public class FiltroBD implements FiltroBusca {
	private Object obj;
	private boolean isPrimaryKey;
	
	public FiltroBD(Object obj) {
		this.obj= obj;
	}
	
	@Override
	public PreparedStatement getPreparedStatement(Connection conn, String sql) throws FiltroException {
//	public String getWhere() {
		String where = null;
		List<String> camposWhere = new ArrayList<String>();
		List<Boolean> coringa = new ArrayList<Boolean>();
		
		isPrimaryKey = false;
		
		Class<?> classe = obj.getClass();
		
		boolean foiFK = false;
		
		for (Method method : classe.getMethods()) {
			String whereAux = where;
			if (method.isAnnotationPresent(BDColuna.class)) {
				boolean atualFK = false;
				if (method.isAnnotationPresent(BDPrimaryKey.class)) {
					atualFK = true;
					if (!isPrimaryKey) {
						where = null;
						isPrimaryKey = true;
//						camposWhere.removeAll(camposWhere);
//						coringa.removeAll(coringa);
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
//									str = "=NULL";
//									
//									where = iniciaWhere(where) + 
//											bdColuna.nomeCampo() + str;
									
									str = "=NULL";
									
									where = iniciaWhere(where) + 
											bdColuna.nomeCampo() + "=?";
									
									camposWhere.add(null);
									coringa.add(false);
									if (isPrimaryKey) {
										foiFK = true;
									}
								}
							} else {
//								if (!str.equals("NULL")) {
//									if (bdColuna.likeCoringa()) {
//										str = " LIKE '%" + str + "%'";
//									} else {
//										str = "='" + str + "'";
//									}
//								}
//								
//								where = iniciaWhere(where) + 
//										bdColuna.nomeCampo() + str;
								
								if (!str.equals("NULL")) {
									camposWhere.add(str);
									
									if (bdColuna.likeCoringa()) {
										str = " LIKE ?";
										coringa.add(true);
									} else {
										str = "=?";
										coringa.add(false);
									}
									
									where = iniciaWhere(where) + 
											bdColuna.nomeCampo() + str;
									
								} else {
									camposWhere.add(null);
									coringa.add(false);
									where = iniciaWhere(where) + 
											bdColuna.nomeCampo() + str;
								}
								
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
			} else {
				if (isPrimaryKey && foiFK) {
					int tam = camposWhere.size() - 1;
					for (int i = 0; i < tam; i++) {
						camposWhere.remove(0);
						coringa.remove(0);
					}
				}
			}
		}
		
		if (where == null) {
			where = " 1";
		}
		
//		return where;
		
		try {
			PreparedStatement ps = conn.prepareStatement(String.format(sql, where));
			for (int i = 0; i < camposWhere.size(); i++) {
				if (camposWhere.get(i) == null) {
					ps.setString(i + 1, "NULL");
				} else {
					if (coringa.get(i)) {
						ps.setString(i + 1, '%' + camposWhere.get(i) + '%');
					} else {
						ps.setString(i + 1, camposWhere.get(i));
					}
				}
			}
			
			return ps;
		} catch (SQLException e) {
			throw new FiltroException("Falha ao buscar dados no BD - SQL: " + sql + " - WHERE: " + where, e);
		}
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

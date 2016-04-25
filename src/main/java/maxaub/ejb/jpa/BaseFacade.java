package maxaub.ejb.jpa;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class BaseFacade {
	public final String PERSISTENCE_UNIT_NAME = "openjpa";
	
	@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    protected final Logger LOG = Logger.getLogger(this.getClass().getName());

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected <E> List<E> getNativeList(String select, Object... parameters) {
        return getNativeList(select, null, parameters);
    }

    protected <E> List<E> getNativeList(String select, Class<E> clazz,
            Object... parameters) {
        return getNativeList(select, clazz, getEntityManager(), parameters);
    }

    
    @SuppressWarnings("unchecked")
	protected <E> List<E> getNativeList(String select, Class<E> clazz,
            EntityManager entityManager, Object... parameters) {
        Query query = clazz != null ? entityManager.createNativeQuery(select,
                clazz) : entityManager.createNativeQuery(select);

        setPositionalParameters(query, parameters);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	protected List<Map<String, Object>> getNativeMapList(String select,
            Object... parameters) {
        Query query = getEntityManager().createNativeQuery(select, Map.class);

        setPositionalParameters(query, parameters);

        return query.getResultList();
    }

    protected <E> Object getNativeSingleResult(String select,
            Object... parameters) {
        Query query = getEntityManager().createNativeQuery(select);

        setPositionalParameters(query, parameters);

        return query.getSingleResult();
    }

    /**
     * Ejecuta la query nativa y devuelve un único resultado. Si no hay
     * resultado devuelve null sin lanzar excepción
     * 
     * @param select
     *            La query nativa
     * @param parameters
     *            parámetros
     * @return devuelve el objeto si hay resultado, null si no hay resultado
     */
    protected <E> Object getSafeNativeSingleResult(String select,
            Object... parameters) {
        return getGenericSafeSingleResult(
                getEntityManager().createNativeQuery(select), parameters);
    }

    /**
     * Ejecuta la named query y devuelve un único resultado. Si no hay resultado
     * devuelve null sin lanzar excepción
     * 
     * @param select
     *            La named query
     * @param parameters
     *            parámetros
     * @return devuelve el objeto si hay resultado, null si no hay resultado
     */
    protected <E> Object getSafeNamedSingleResult(String select,
            Object... parameters) {
        return getGenericSafeSingleResult(
                getEntityManager().createNamedQuery(select), parameters);
    }

    /**
     * Ejecuta la query JPA y devuelve un único resultado. Si no hay resultado
     * devuelve null sin lanzar excepción
     * 
     * @param select
     *            La query JPA
     * @param parameters
     *            parámetros
     * @return devuelve el objeto si hay resultado, null si no hay resultado
     */
    protected <E> Object getSafeSingleResult(String select,
            Object... parameters) {
        return getGenericSafeSingleResult(getEntityManager()
                .createQuery(select), parameters);
    }

    /**
     * Ejecuta una query (JPA o nativa) y asegura devolver un resultado si lo
     * hay, y si no lo hay devuelve null
     * 
     * @param query
     *            la query (JPA o nativa)
     * @param parameters
     *            parámetros
     * @return
     */
    protected <E> Object getGenericSafeSingleResult(Query query,
            Object... parameters) {
        try {
            setPositionalParameters(query, parameters);

            return query.getSingleResult();

        } catch (NoResultException e) {
            LOG.debug("getGenericSafeSingleResult: " + e);
        }

        return null;
    }

    protected int executeNativeUpdate(String sql, Object... parameters) {
        Query query = getEntityManager().createNativeQuery(sql);

        setPositionalParameters(query, parameters);

        return query.executeUpdate();
    }

    protected void setPositionalParameters(Query query, Object... parameters) {
        if (parameters != null) {
            for (int i = 1; i <= parameters.length; i++) {
                Object entry = parameters[i - 1];
                query.setParameter(i, entry);
            }
        }
    }
    
	public static void setParameterList(PreparedStatement pstm,
			Object... parametersList) throws SQLException {
		if (parametersList != null) {
			int paramIdx = 1;
			for (Object param : parametersList) {
				setPstmParam(pstm, paramIdx++, param);
			}
		}
	}

	private static void setPstmParam(final PreparedStatement pstm, final int paramIdx, final Object value)
			throws SQLException {
		if (value != null) {
			if (value instanceof String) {
				pstm.setString(paramIdx, (String) value);
			} else if (value instanceof Integer) {
				pstm.setInt(paramIdx, (Integer) value);
			} else if (value instanceof Long) {
				pstm.setLong(paramIdx, (Long) value);
			} else if (value instanceof Short) {
				pstm.setShort(paramIdx, (Short) value);
			} else if (value instanceof Double) {
				pstm.setDouble(paramIdx, (Double) value);
			} else if (value instanceof Float) {
				pstm.setFloat(paramIdx, (Float) value);
			} else if (value instanceof BigDecimal) {
				pstm.setBigDecimal(paramIdx, (BigDecimal) value);
			} else if (value instanceof java.sql.Date) {
				pstm.setDate(paramIdx, (java.sql.Date) value);
			} else if (value instanceof java.util.Date) {
				java.sql.Date sqlDate = new java.sql.Date(((java.util.Date) value).getTime());
				pstm.setDate(paramIdx, sqlDate);
			} else if (value instanceof Timestamp) {
				pstm.setTimestamp(paramIdx, (Timestamp) value);
			} else {
				pstm.setString(paramIdx, value.toString());
			}
		} else {
			pstm.setString(paramIdx, null);
		}
	}
	
	// función especial para las consultas sql ejecutadas con variables
    protected boolean comprobarComentariosSQL(String campo) {
        boolean comentario = false;
        
        if (campo.contains("--"))
            comentario = true;
        else if (campo.contains("#"))
            comentario = true;
        else if (campo.contains("/*"))
            comentario = true;
        
        return comentario;
    }
}
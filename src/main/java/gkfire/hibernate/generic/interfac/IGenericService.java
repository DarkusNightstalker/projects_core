/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.hibernate.generic.interfac;

import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;

/**
 *
 * @author Abraham
 * @param <T>
 */
public interface IGenericService<T, ID extends Serializable> {

    /**
     * Guarda un nuevo registro en la base de datos. Sus relaciones deben estar
     * completas, puesto que puede ocurrir un error al subir a la base de datos.
     *
     * @param object nuevo objeto llena con sus relaciones
     * @return retorna el ID del objeto subido.
     */
    public Serializable save(T object);

    /**
     * Realiza un <code>update</code> en la base de datos se debe tener en
     * cuenta que el objeto ya debe existir en la base de datos.
     *
     * @param object el Id debe existir en la base de datos.
     */
    public void update(T object);

    public int updateHQL(String hql) throws Exception;
    public int updateHQL(String hql,Object... parameters) throws Exception;

    /**
     * Realiza un <code>insert</code>, <code>update</code>, <code>delete</code>,
     * en la base de datos, segun sea el caso de sus relaciones.
     *
     * @param object Objeto relacionado.
     */
    public void saveOrUpdate(T object);

    /**
     * Borra en cascada todas las realciones del objeto en la base de datos
     *
     * @param object Tiene que tener lleno sus datos para poder borrar todo en
     * cascada.
     */
    public void delete(T object);

    /**
     * Retorna la lista completa de todos los objetos sin ningun tipo de
     * restricciones.
     *
     * @return Lista total.
     */
    public List list();

    /**
     * Metodo que retorna una lista de objetos, con una consulta HQL, es muy
     * similar a la consulta SQL.
     *
     * @param hql Consulta Hibernate Query Lenguage.
     * @return Lista de Objetos de la clase.
     */
    public List listHQL(String hql);
    /**
     * Metodo que retorna una lista de objetos, con una consulta HQL, es muy
     * similar a la consulta SQL.
     *
     * @param hql Consulta Hibernate Query Lenguage.
     * @param parameters
     * @return Lista de Objetos de la clase.
     */
    public java.util.List listHQL(String hql,Object... parameters);
    /**
     * Metodo que retorna una lista de objetos, con una consulta HQL, es muy
     * similar a la consulta SQL.
     *
     * @param hql Consulta Hibernate Query Lenguage.
     * @param page
     * @param recordsPerPage
     * @param parameters
     * @return Lista de Objetos de la clase.
     */
    public java.util.List listHQLPage(String hql,int page,int recordsPerPage,Object... parameters);
    /**
     * Metodo que retorna una lista de objetos, con una consulta HQL, es muy
     * similar a la consulta SQL.
     *
     * @param hql Consulta Hibernate Query Lenguage.
     * @return Lista de Objetos de la clase.
     */
    public Object getByHQL(String hql);
    /**
     * Metodo que retorna una lista de objetos, con una consulta HQL, es muy
     * similar a la consulta SQL.
     *
     * @param hql Consulta Hibernate Query Lenguage.
     * @param parameters Parametros de la consulta
     * @return Objeto de la clase.
     */
    public Object getByHQL(String hql,Object... parameters);

    /**
     * Metodo que ordena las columnas en forma ascendente o descendente,
     * dependiendo del parametro booleano. Se ingresa un arreglo del nombre de
     * las columnas de las clases que se desea ordenar.
     *
     * <ul>Se puede ordenar por "id" <-- Atributo de la clse.</ul> <ul>Tambien
     * se p uede poner un campo <code>date</code> para ordenarlo</ul>
     *
     * @param nameColumns Arreglo con los nombres de las columnas
     * @param asc <code>true</code> si se ordena en forma acsendente
     * @return Retorna la lista ordenada.
     */
    public List listOrderByColumns(String[] nameColumns, boolean asc);

    /**
     * Metodo que devuelve un objeto completo ingresando como parametro su Id
     *
     * @param id Su identificador de la clase que esta relacionado con la base
     * de datos
     *
     * @return Retorna un Objeto del tipo de la misma clase.
     */
    public T getById(ID id);

    /**
     * Cuenta todas las filas y trae el resultado
     *
     * @return retorna el resultado del conteo.
     */
    public Integer count();

    /**
     * Se debe crear una lista de objetos Criterion de Hibernate: <ul>
     * <code>List(Criterion) listCriterion = new ArrayList();</code> Ejemplos:
     * </ul> <ul> <li> To get records having salary more than 2000 </li>
     * <code>listCriterion.add(Restrictions.gt("salary", 2000));</code>
     *
     * </ul> <ul><li>To get records having salary less than 2000</li>
     * <code>listCriterion.add(Restrictions.lt("salary", 2000));</code> </ul>
     * <ul> <li>To get records having fistName starting with zara</li>
     * <code>listCriterion.add(Restrictions.like("firstName", "zara%"));</code>
     * </ul> <ul> <li>Case sensitive form of the above restriction.</li>
     * <code>listCriterion.add(Restrictions.ilike("firstName", "zara%"));</code>
     * </ul> <ul> <li> To get records having salary in between 1000 and
     * 2000</li>
     * <code>listCriterion.add(Restrictions.between("salary", 1000, 2000));</code>
     * </ul> <ul> <li> To check if the given property is null</li>
     * <code>listCriterion.add(Restrictions.isNull("salary"));</code> </ul> <ul>
     * <li> To check if the given property is not null</li>
     * <code>listCriterion.add(Restrictions.isNotNull("salary"));</code> </ul>
     * <ul> <li> To check if the given property is empty</li>
     * <code>listCriterion.add(Restrictions.isEmpty("salary"));</code> </ul>
     * <ul> <li> To check if the given property is not empty</li>
     * <code>listCriterion.add(Restrictions.isNotEmpty("salary"));</code> </ul>
     *
     * @param listCriterion Lista de restricciones para realizar la consulta.
     * @return Retorna una lista con condiciones dadas.
     */
    public java.util.List addRestrictions(java.util.List<Criterion> listCriterion);

    public java.util.List addRestrictions(java.util.List<Criterion> listCriterion, List<Projection> projections);

    public java.util.List addRestrictions(java.util.List<Criterion> listCriterion, int page, int rows);

    public java.util.List addRestrictions(java.util.List<Criterion> listCriterion, List<Projection> projections, int page, int rows);

    public java.util.List addRestrictionsVariant(List variant);

    public List addRestrictionsVariant(Object... variant);

    public ID nextId(ID id, String idName, boolean withDisabled);

    public ID previousId(ID id, String idName, boolean withDisabled);

    public Number rowNumber(ID id, boolean withDisabled);

    public Number countRestrictions(List<Criterion> listCriterion);
    public Number countRestrictions(CriterionList criterionList,AliasList aliasList);
    
    public String getClassName();
    
    public List addRestrictionsVariant(int rows,int page,Object... variant);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.hibernate.generic;

import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.generic.interfac.IGenericDao;
import gkfire.hibernate.generic.interfac.IGenericService;
import gkfire.model.interfac.EntityActivate;
import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;

/**
 *
 * @author Jhoan Brayam
 * @param <T>
 * @param <ID>
 */
public abstract class GenericService<T, ID extends Serializable> implements IGenericService<T, ID> {

    protected abstract IGenericDao<T, ID> getDao();

    @Override
    public boolean isActive(ID id) {
        if(EntityActivate.class.isAssignableFrom(getDao().getObjectClass())){
            return (boolean) getDao().getByHQL("SELECT e.active FROM "+getClassName()+" e WHERE e.id =?",id);
        }else{
            throw new UnsupportedOperationException("This methos not supportes not EntityActivate Objects");
        }
    }
    @Override
    public Serializable save(T object) {
        return getDao().save(object);
    }

    @Override
    public void update(T object) {
        getDao().update(object);
    }

    @Override
    public void saveOrUpdate(T object) {
        getDao().saveOrUpdate(object);
    }

    @Override
    public void delete(T object) {
        getDao().delete(object);
    }

    @Override
    public List list() {
        return getDao().list();
    }

    @Override
    public List listHQL(String hql) {
        return getDao().listHQL(hql);
    }

    @Override
    public T getById(ID id) {
        return getDao().getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return getDao().listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Number count() {
        return getDao().count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return getDao().addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return getDao().updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return getDao().addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return getDao().countRestrictions(listCriterion);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections) {
        return getDao().addRestrictions(listCriterion, projections);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections, int page, int rows) {
        return getDao().addRestrictions(listCriterion, projections, page, rows);
    }

    @Override
    public List addRestrictionsVariant(List variant) {
        return getDao().addRestrictionsVariant(variant);
    }

    @Override
    public ID nextId(ID id, String idName, boolean withDisabled) {
        return getDao().nextId(id, idName, withDisabled);
    }

    @Override
    public ID previousId(ID id, String idName, boolean withDisabled) {
        return getDao().previousId(id, idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return getDao().addRestrictionsVariant(variant);
    }

    @Override
    public Number rowNumber(ID id, boolean withDisabled) {
        return getDao().rowNumber(id, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return getDao().addRestrictionsVariant(rows, page, variant);
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return getDao().countRestrictions(criterionList, aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return getDao().listHQL(hql, parameters);
    }

    @Override
    public List listHQLPage(String hql, int page, int recordsPerPage, Object... parameters) {
        return getDao().listHQLPage(hql, page, recordsPerPage, parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return getDao().getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return getDao().getByHQL(hql, parameters);
    }

    @Override
    public String getClassName() {
        return getDao().getObjectClass().getSimpleName();
    }

    @Override
    public int updateHQL(String hql, Object... parameters) throws Exception {
        return getDao().updateHQL(hql, parameters);
    }
}

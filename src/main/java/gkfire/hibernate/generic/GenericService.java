/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.hibernate.generic;

import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.SpecialCriterionList;
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

    protected abstract IGenericDao<T, ID> getBasicDao();
    
    //<editor-fold defaultstate="collapsed" desc="Transactionals">
    
    @Override
    public ID save(T object) {
        return getBasicDao().save(object);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void update(T object) {
        getBasicDao().update(object);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(T object) {
        getBasicDao().saveOrUpdate(object);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void delete(T object) {
        getBasicDao().delete(object);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public int updateHQL(String hql, Object... parameters) throws Exception {
        return getBasicDao().updateHQL(hql, parameters);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public int updateHQL(String hql) throws Exception {
        return getBasicDao().updateHQL(hql);
    }
    
    //</editor-fold>

    @Override
    public boolean isActive(ID id) {
        if (EntityActivate.class.isAssignableFrom(getBasicDao().getObjectClass())) {
            return (boolean) getBasicDao().getByHQL("SELECT e.active FROM " + getClassName() + " e WHERE e.id =?", id);
        } else {
            throw new UnsupportedOperationException("This method not supportes not EntityActivate Objects");
        }
    }


    @Override
    public List list() {
        return getBasicDao().list();
    }

    @Override
    public List listHQL(String hql) {
        return getBasicDao().listHQL(hql);
    }

    @Override
    public T getById(ID id) {
        return getBasicDao().getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return getBasicDao().listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Number count() {
        return getBasicDao().count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return getBasicDao().addRestrictions(listCriterion);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return getBasicDao().addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return getBasicDao().countRestrictions(listCriterion);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections) {
        return getBasicDao().addRestrictions(listCriterion, projections);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections, int page, int rows) {
        return getBasicDao().addRestrictions(listCriterion, projections, page, rows);
    }

    @Override
    public List addRestrictionsVariant(List variant) {
        return getBasicDao().addRestrictionsVariant(variant);
    }

    @Override
    public ID nextId(ID id, String idName, boolean withDisabled) {
        return getBasicDao().nextId(id, idName, withDisabled);
    }

    @Override
    public ID previousId(ID id, String idName, boolean withDisabled) {
        return getBasicDao().previousId(id, idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return getBasicDao().addRestrictionsVariant(variant);
    }

    @Override
    public Number rowNumber(ID id, boolean withDisabled) {
        return getBasicDao().rowNumber(id, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return getBasicDao().addRestrictionsVariant(rows, page, variant);
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return getBasicDao().countRestrictions(criterionList, aliasList);
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, SpecialCriterionList specialCriterionList, AliasList aliasList) {
        return getBasicDao().countRestrictions(criterionList, specialCriterionList, aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return getBasicDao().listHQL(hql, parameters);
    }

    @Override
    public List listHQLPage(String hql, int page, int recordsPerPage, Object... parameters) {
        return getBasicDao().listHQLPage(hql, page, recordsPerPage, parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return getBasicDao().getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return getBasicDao().getByHQL(hql, parameters);
    }

    @Override
    public String getClassName() {
        return getBasicDao().getObjectClass().getSimpleName();
    }
}

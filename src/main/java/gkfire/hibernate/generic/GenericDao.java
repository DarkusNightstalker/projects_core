/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.hibernate.generic;

import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.hibernate.SpecialCriterionList;
import gkfire.hibernate.criterion.AssociationCriterion;
import gkfire.hibernate.generic.interfac.IGenericDao;
import gkfire.model.interfac.EntityActivate;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author Abraham
 * @param <T>
 * @param <ID>
 */
@org.springframework.transaction.annotation.Transactional(readOnly = true)
public class GenericDao<T, ID extends Serializable> implements IGenericDao<T, ID> {

    @Autowired
    @Qualifier("sessionFactory")
    protected SessionFactory sessionFactory;
    private final Class<T> oClass;

    public GenericDao() {
        this.oClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public boolean isActive(ID id) {
        if (EntityActivate.class.isAssignableFrom(this.oClass)) {
            return (boolean) getByHQL("SELECT e.active FROM " + this.oClass.getSimpleName() + " e WHERE e.id =?", id);
        } else {
            throw new UnsupportedOperationException("This method not supportes not EntityActivate Objects");
        }
    }

    @Override
    public Class<T> getObjectClass() throws HibernateException {
        return this.oClass;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Transactional">
    

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public ID save(T objeto) throws HibernateException {
        return (ID) getSessionFactory().getCurrentSession().save(objeto);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void update(T objeto) throws HibernateException {
        getSessionFactory().getCurrentSession().update(objeto);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public int updateHQL(String hql) throws Exception {
        return getSessionFactory().getCurrentSession()
                .createQuery(hql)
                .executeUpdate();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public int updateHQL(String hql, Object... parameters) {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        return query.executeUpdate();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(T object) throws HibernateException {
        getSessionFactory().getCurrentSession().saveOrUpdate(object);
    }

    public void commit() {

    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void delete(T objeto) throws HibernateException {
        if (objeto instanceof EntityActivate) {
            ((EntityActivate) objeto).setActive(Boolean.FALSE);
            getSessionFactory().getCurrentSession().update(objeto);
        } else {
            getSessionFactory().getCurrentSession().delete(objeto);
        }
    }
//</editor-fold>

    @Override
    public List list() throws HibernateException {
        List lista = getSessionFactory().getCurrentSession()
                .createCriteria(oClass).list();
        return lista;
    }

    @Override
    public List listHQL(String hql) throws HibernateException {
        List lista = getSessionFactory().getCurrentSession()
                .createQuery(hql).list();
        return lista;
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {

        Criteria criteria = getSessionFactory()
                .getCurrentSession().createCriteria(oClass);

        if (asc) {
            for (int i = 0; i < nameColumns.length; i++) {
                criteria = criteria.addOrder(Order.asc(nameColumns[i]));
            }
        } else {
            for (int i = 0; i < nameColumns.length; i++) {
                criteria = criteria.addOrder(Order.desc(nameColumns[i]));
            }
        }
        return criteria.list();
    }

    @Override
    public T getById(ID id) throws HibernateException {
        return (T) getSessionFactory()
                .getCurrentSession().get(oClass, id);
    }

    @Override
    public Number count() {
        return (Number) getSessionFactory().getCurrentSession()
                .createCriteria(oClass).setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {

        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);

        for (Criterion item : listCriterion) {
            if (item instanceof AssociationCriterion) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        System.out.println(criteria);
        return criteria.list();
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {

        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);

        for (Criterion item : listCriterion) {
            if (item instanceof AssociationCriterion) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        Number rowCount = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
        return rowCount;
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {

        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);

        for (Criterion item : listCriterion) {
            if (item instanceof AssociationCriterion) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        criteria.setMaxResults(rows);
        criteria.setFirstResult((page - 1) * rows);
        return criteria.list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections) {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);

        ProjectionList projectionList = Projections.projectionList();
        for (Projection projection : projections) {
            projectionList.add(projection);
        }

        criteria.setProjection(projectionList);
        for (Criterion item : listCriterion) {
            if (item instanceof AssociationCriterion) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        return criteria.list();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections, int page, int rows) {

        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);

        ProjectionList projectionList = Projections.projectionList();
        for (Projection projection : projections) {
            projectionList.add(projection);
        }

        criteria.setProjection(projectionList);
        for (Criterion item : listCriterion) {
            if (item instanceof AssociationCriterion) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        criteria.setMaxResults(rows);
        criteria.setFirstResult((page - 1) * rows);
        return criteria.list();
    }

    @Override
    public List addRestrictionsOrder(List<Criterion> listCriterion, List<Order> orders) {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);

        for (Criterion item : listCriterion) {
            if (item instanceof AssociationCriterion) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        for (Order order : orders) {
            criteria.addOrder(order);
        }
        return criteria.list();
    }

    @Override
    public List addRestrictionsOrder(List<Criterion> listCriterion, List<Projection> projections, List<Order> orders) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List addRestrictionsOrder(List<Criterion> listCriterion, List<Order> orders, int page, int rows) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List addRestrictionsOrder(List<Criterion> listCriterion, List<Projection> projections, List<Order> orders, int page, int rows) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List addRestrictionsVariant(List variant) {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);
        for (Object c : variant) {
            if (c instanceof ProjectionList) {
                criteria.setProjection((ProjectionList) c);
            } else if (c instanceof AliasList) {
                AliasList a = (AliasList) c;
                Set<String> properties = a.keySet();
                for (String property : properties) {
                    AliasList.AliasItem item = a.get(property);
                    criteria.createAlias(property, item.getAlias(), item.getJoinType());
                }
            } else if (c instanceof CriterionList) {
                for (Criterion item : (CriterionList) c) {
                    criteria.add(item);
                }
            } else if (c instanceof OrderList) {
                for (Order item : (OrderList) c) {
                    criteria.addOrder(item);
                }
            } else {
                throw new IllegalArgumentException("Illegal argument");
            }
        }
        return criteria.list();
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);
        for (Object c : variant) {
            if (c instanceof ProjectionList) {
                criteria.setProjection((ProjectionList) c);
            } else if (c instanceof AliasList) {
                AliasList a = (AliasList) c;
                Set<String> properties = a.keySet();
                for (String property : properties) {
                    AliasList.AliasItem item = a.get(property);
                    criteria.createAlias(property, item.getAlias(), item.getJoinType());
                }
            } else if (c instanceof CriterionList) {
                for (Criterion item : (CriterionList) c) {
                    criteria.add(item);
                }
            } else if (c instanceof OrderList) {
                for (Order item : (OrderList) c) {
                    criteria.addOrder(item);
                }
            } else if (c instanceof SpecialCriterionList) {
                ((SpecialCriterionList) c).make(criteria);
            } else {
                throw new IllegalArgumentException("Illegal argument");
            }
        }
        return criteria.list();
    }

    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);
        for (Object c : variant) {
            if (c instanceof ProjectionList) {
                criteria.setProjection((ProjectionList) c);
            } else if (c instanceof AliasList) {
                AliasList a = (AliasList) c;
                Set<String> properties = a.keySet();
                for (String property : properties) {
                    AliasList.AliasItem item = a.get(property);
                    criteria.createAlias(property, item.getAlias(), item.getJoinType());
                }
            } else if (c instanceof CriterionList) {
                for (Criterion item : (CriterionList) c) {
                    criteria.add(item);
                }
            } else if (c instanceof OrderList) {
                for (Order item : (OrderList) c) {
                    criteria.addOrder(item);
                }
            } else if (c instanceof SpecialCriterionList) {
                ((SpecialCriterionList) c).make(criteria);
            } else {
                throw new IllegalArgumentException("Illegal argument");
            }
        }
        criteria.setMaxResults(rows);
        criteria.setFirstResult((page - 1) * rows);
        return criteria.list();
    }

    @Override
    public ID nextId(ID id, String idName, boolean withDisabled) {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);
        criteria.setProjection(Projections.id());
        criteria.add(Restrictions.gt(idName, id));
        if (!withDisabled) {
            criteria.add(Restrictions.eq("active", true));
        }
        criteria.addOrder(Order.asc(idName));
        criteria.setMaxResults(1);
        return (ID) criteria.uniqueResult();
    }

    @Override
    public ID previousId(ID id, String idName, boolean withDisabled) {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);
        criteria.setProjection(Projections.id());
        criteria.add(Restrictions.lt(idName, id));
        if (!withDisabled) {
            criteria.add(Restrictions.eq("active", true));
        }
        criteria.addOrder(Order.desc(idName));
        criteria.setMaxResults(1);
        return (ID) criteria.uniqueResult();
    }

    @Override
    public Number rowNumber(ID id, boolean withDisabled) {
        String tableName = "";
        System.out.println(getSessionFactory().getClassMetadata(oClass).getClass());
        tableName = ((AbstractEntityPersister) getSessionFactory().getClassMetadata(oClass)).getTableName();
        SQLQuery sq = getSessionFactory().getCurrentSession().createSQLQuery("SELECT sq.rnum FROM (SELECT id,(row_number() OVER())  as rnum FROM " + tableName + " " + (!withDisabled ? "WHERE active=true" : "") + ") sq WHERE sq.id = :id");
        sq.setParameter("id", id);
        return (Number) sq.uniqueResult();
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);

        for (Criterion item : criterionList) {
            criteria.add(item);
        }
        if (aliasList != null) {
            Set<String> properties = aliasList.keySet();
            for (String property : properties) {
                AliasList.AliasItem item = aliasList.get(property);
                criteria.createAlias(property, item.getAlias(), item.getJoinType());
            }
        }
        Number rowCount = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
        return rowCount;
    }

    public Number countRestrictions(CriterionList criterionList, SpecialCriterionList specialCriterionList, AliasList aliasList) {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);

        for (Criterion item : criterionList) {
            criteria.add(item);
        }
        if (aliasList != null) {
            Set<String> properties = aliasList.keySet();
            for (String property : properties) {
                AliasList.AliasItem item = aliasList.get(property);
                criteria.createAlias(property, item.getAlias(), item.getJoinType());
            }
        }
        if (specialCriterionList != null) {
            specialCriterionList.make(criteria);
        }
        Number rowCount = (Number) criteria.setProjection(Projections.countDistinct(getSessionFactory().getClassMetadata(oClass).getIdentifierPropertyName())).uniqueResult();
        return rowCount;
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        return query.list();
    }

    @Override
    public Object getByHQL(String hql, String[] name, Object... parameters) {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] instanceof List) {
                query.setParameterList(name[i], (List) parameters[i]);
            } else {
                query.setParameter(name[i], parameters[i]);
            }
        }
        return query.uniqueResult();
    }

    @Override
    public List listHQL(String hql, String[] name, Object... parameters) {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] instanceof List) {
                query.setParameterList(name[i], (List) parameters[i]);
            } else {
                query.setParameter(name[i], parameters[i]);
            }
        }
        return query.list();
    }

    @Override
    public Object getByHQL(String hql) {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        return query.uniqueResult();
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {

        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        return query.uniqueResult();
    }

    @Override
    public List listHQLPage(String hql, int page, int recordsPerPage, Object... parameters) {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        return query.setFirstResult((page - 1) * recordsPerPage)
                .setMaxResults(recordsPerPage).list();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.hibernate.criterion;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;

/**
 *
 * @author Darkus
 */
public class AssociationCriterion implements Criterion {

    public static AssociationCriterion create(Criterion criterion, String property,String alias) {
        return new AssociationCriterion(criterion, property,alias);
    }

    private boolean use;
    private final Criterion criterion;
    private final String property;
    private final String alias;
    private AssociationCriterion(Criterion criterion, String property,String alias) {
        this.criterion = criterion;
        this.property = property;
        this.alias = alias;
        use=false;
    }

    public Criterion getCriterion() {
        return criterion;
    }

    public String getAlias() {
        return alias;
    }

    public String getProperty() {
        return property;
    }
    @Override
    public String toSqlString(Criteria crtr, CriteriaQuery cq) throws HibernateException {
        return criterion.toSqlString(crtr, cq);
    }

    @Override
    public TypedValue[] getTypedValues(Criteria crtr, CriteriaQuery cq) throws HibernateException {
        return criterion.getTypedValues(crtr, cq);
    }
}

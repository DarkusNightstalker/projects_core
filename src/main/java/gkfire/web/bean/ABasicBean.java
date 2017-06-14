/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.bean;

import gkfire.hibernate.OrderFactory;
import gkfire.hibernate.OrderList;
import gkfire.hibernate.generic.GenericService;
import gkfire.web.util.AbstractImport;
import gkfire.web.util.BeanUtil;
import gkfire.web.util.Pagination;
import org.hibernate.criterion.Order;

/**
 *
 * @author Jhoan Brayam
 */
public abstract class ABasicBean<T extends Object> implements java.io.Serializable, ILoadable {

    protected AbstractImport import_;
    protected T id;

    protected Pagination<Object[]> pagination;
    protected OrderFactory orderFactory;

    public ABasicBean() {
        initImport();
    }

    
    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        refresh();
    }

    public void refresh() {
        clearFields();
        search();
    }

    protected abstract void clearFields();
    protected abstract void initImport();
    public abstract void search();
    public abstract AbstractSessionBean getSessionBean();

    /**
     * @return the import_
     */
    public AbstractImport getImport_() {
        return import_;
    }

    /**
     * @param import_ the import_ to set
     */
    public void setImport_(AbstractImport import_) {
        this.import_ = import_;
    }

    /**
     * @return the id
     */
    public T getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(T id) {
        this.id = id;
    }

    /**
     * @return the pagination
     */
    public Pagination<Object[]> getPagination() {
        return pagination;
    }

    /**
     * @param pagination the pagination to set
     */
    public void setPagination(Pagination<Object[]> pagination) {
        this.pagination = pagination;
    }

    /**
     * @return the orderFactory
     */
    public OrderFactory getOrderFactory() {
        return orderFactory;
    }

    /**
     * @param orderFactory the orderFactory to set
     */
    public void setOrderFactory(OrderFactory orderFactory) {
        this.orderFactory = orderFactory;
    }
}

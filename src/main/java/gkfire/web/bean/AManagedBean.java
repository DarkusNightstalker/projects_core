/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.bean;

import gkfire.hibernate.generic.interfac.IGenericService;
import gkfire.web.util.BeanUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Benji
 */
public abstract class AManagedBean<T, S extends IGenericService> implements java.io.Serializable {

    protected T selected;
    protected boolean createAgain;
    protected boolean keepManaged = false;
    protected boolean saved = false;
    
    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
        if (selected != null) {
            fillFields();
            saved = false;
        } else {
            clearFields();
        }
    }

    public Number getIdSelected() {
        return null;
    }

    public void setIdSelected(Number id) {
        setSelected((T) getMainService().getById(id));
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }    

    public void create() {
        Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
       
        try {
            setSelected((T) persistentClass.getConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(AManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(java.io.Serializable id) {
        T object = (T) getMainService().getById(id);
        getMainService().delete(object);
    }
    public void recovery(java.io.Serializable id) {
        T object = (T) getMainService().getById(id);
        getMainService().update(object);
    }

    public boolean save() {
        fillSelected();
        getMainService().saveOrUpdate(selected);
        return true;
    }

    public void doSave(String page, ILoadable loadable) {
        if (save()) {
            if (!createAgain) {
                getNavigationBean().setContent(page);
                loadable.onLoad(true);
                getSessionBean().setLoadable(loadable);
                BeanUtil.exceuteJS("Core.trays();");
            } else {
                create();
            }
        }
    }

    public void refresh() {
        fillFields();
    }

    public abstract INavigationBean getNavigationBean();

    public abstract AbstractSessionBean getSessionBean();

    public abstract S getMainService();

    public abstract void setMainService(S mainService);

    protected abstract void fillFields();

    protected abstract void clearFields();

    protected abstract void fillSelected();

    /**
     * @return the createAgain
     */
    public boolean isCreateAgain() {
        return createAgain;
    }

    /**
     * @param createAgain the createAgain to set
     */
    public void setCreateAgain(boolean createAgain) {
        this.createAgain = createAgain;
    }

    /**
     * @return the keepManaged
     */
    public boolean isKeepManaged() {
        return keepManaged;
    }

    /**
     * @param keepManaged the keepManaged to set
     */
    public void setKeepManaged(boolean keepManaged) {
        this.keepManaged = keepManaged;
    }

}

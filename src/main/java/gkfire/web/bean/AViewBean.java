/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.bean;

import gkfire.auditory.AuditoryEntity;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.generic.interfac.IGenericService;
import gkfire.web.util.BeanUtil;
import java.util.Arrays;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author Jhoan Brayam
 * @param <T>
 * @param <ID>
 * @param <S>
 */
public abstract class AViewBean<T extends Object, ID extends Number, S extends IGenericService> implements java.io.Serializable, ILoadable {

    protected String permissionDisabled;
    protected Long rowNumber;
    protected Long rowCount;
    protected boolean existNext;
    protected boolean existPrevious;
    protected ID currentId;
    protected String userCreated;
    protected String userEdited;
    protected Boolean direction;
    protected T selected;

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        direction = null;
        update(getSessionBean().authorize(permissionDisabled));
    }

    public void begin(ID id) {
        currentId = id;
        direction = null;
        update(getSessionBean().authorize(permissionDisabled));
    }

    protected void update(boolean widthDisabled) {

        rowNumber = getMainService().rowNumber(currentId, widthDisabled).longValue();
        if (!widthDisabled) {
            rowCount = getMainService().countRestrictions(Arrays.asList((Criterion) Restrictions.eq("active", true))).longValue();
        } else {
            rowCount = getMainService().count().longValue();
        }
        existNext = getMainService().nextId(currentId, "id", widthDisabled) != null;
        existPrevious = getMainService().previousId(currentId, "id", widthDisabled) != null;
        selected = (T) getMainService().getById(currentId);
        
        if (selected instanceof AuditoryEntity) {            
            Object[] users = (Object[]) getMainService().getByHQL("SELECT e.createUser.username,e.editUser.username FROM "+getMainService().getClassName()+" e WHERE e.id = ?",currentId);
            userCreated = (String) users[0];
            userEdited = (String) users[1];
        }
    }

    public void next() {
        boolean widthDisable = getSessionBean().authorize(permissionDisabled);
        currentId = (ID) getMainService().nextId(currentId, "id", widthDisable);
        direction = true;
        update(widthDisable);
    }

    public void previous() {
        boolean widthDisable = getSessionBean().authorize(permissionDisabled);
        currentId = (ID) getMainService().previousId(currentId, "id", widthDisable);
        direction = false;
        update(widthDisable);
    }

    public abstract S getMainService();

    public abstract void setMainService(S mainService);

    public abstract AbstractSessionBean getSessionBean();

    public abstract void setSessionBean(AbstractSessionBean sessionBean);

    /**
     * @return the rowNumber
     */
    public Long getRowNumber() {
        return rowNumber;
    }

    /**
     * @param rowNumber the rowNumber to set
     */
    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * @return the rowCount
     */
    public Long getRowCount() {
        return rowCount;
    }

    /**
     * @param rowCount the rowCount to set
     */
    public void setRowCount(Long rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * @return the existNext
     */
    public boolean isExistNext() {
        return existNext;
    }

    /**
     * @param existNext the existNext to set
     */
    public void setExistNext(boolean existNext) {
        this.existNext = existNext;
    }

    /**
     * @return the existPrevious
     */
    public boolean isExistPrevious() {
        return existPrevious;
    }

    /**
     * @param existPrevious the existPrevious to set
     */
    public void setExistPrevious(boolean existPrevious) {
        this.existPrevious = existPrevious;
    }

    /**
     * @return the currentId
     */
    public ID getCurrentId() {
        return currentId;
    }

    /**
     * @param currentId the currentId to set
     */
    public void setCurrentId(ID currentId) {
        this.currentId = currentId;
    }

    /**
     * @return the userCreated
     */
    public String getUserCreated() {
        return userCreated;
    }

    /**
     * @param userCreated the userCreated to set
     */
    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    /**
     * @return the userEdited
     */
    public String getUserEdited() {
        return userEdited;
    }

    /**
     * @param userEdited the userEdited to set
     */
    public void setUserEdited(String userEdited) {
        this.userEdited = userEdited;
    }

    /**
     * @return the direction
     */
    public Boolean getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Boolean direction) {
        this.direction = direction;
    }

    /**
     * @return the selected
     */
    public T getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(T selected) {
        this.selected = selected;
    }
}

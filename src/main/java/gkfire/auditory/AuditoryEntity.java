/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.auditory;


/**
 *
 * @author DARKUS
 * @param <T>
 */
public interface AuditoryEntity<T extends Object,USER extends Object> extends java.io.Serializable {

    /**
     *
     * @return
     */
    public T getId();

    /**
     *
     * @param id
     */
    public void setId(T id);
    
    /**
     *
     * @return
     */
    public USER getCreateUser();

    /**
     *
     * @param createUser
     */
    public void setCreateUser(USER createUser);

    /**
     *
     * @return
     */
    public USER getEditUser();

    /**
     *
     * @param editUser
     */
    public void setEditUser(USER editUser);
    
    /**
     *
     * @return
     */
    public java.util.Date getCreateDate();

    /**
     *
     * @param createDate
     */
    public void setCreateDate(java.util.Date createDate);

    /**
     *
     * @return
     */
    public java.util.Date getEditDate();

    /**
     *
     * @param editDate
     */
    public void setEditDate(java.util.Date editDate);
    
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.auditory;

import java.util.Calendar;

/**
 *
 * @author DARKUS
 */
public class Auditory {

    /**
     *
     * @param entity
     * @param user
     */
    public static void make(AuditoryEntity entity,Object user){
        if(entity.getId() == null){
            entity.setCreateUser(user);
            entity.setCreateDate(Calendar.getInstance().getTime());
            entity.setEditDate(null);
            entity.setEditUser(null);
        }else{
            entity.setEditUser(user);
            entity.setEditDate(Calendar.getInstance().getTime());
        }
    }
}

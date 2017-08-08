/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.model.interfac;

import javax.persistence.Column;

/**
 *
 * @author Darkus Nightmare
 */
public abstract class ModelSynchronizable implements java.io.Serializable {
    
    @Column(name = "server_exist", nullable = false)
    private Boolean serverExist = Boolean.TRUE;

    /**
     * @return the serverExist
     */
    public Boolean getServerExist() {
        return serverExist;
    }

    /**
     * @param serverExist the serverExist to set
     */
    public void setServerExist(Boolean serverExist) {
        this.serverExist = serverExist;
    }
    
    
}

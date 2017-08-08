/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.hibernate.postgresql;

import java.io.Serializable;

/**
 *
 * @author Darkus Nightmare
 */
public class PgInet implements Serializable {

    private static final long serialVersionUID = 1L;

    private String address;

    public PgInet(String address) {
        this.address = address;
    }

    public PgInet() {
        this.address = null;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address; 
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.hibernate;

import org.hibernate.HibernateException;

/**
 *
 * @author Darkus Nightmare
 */
public class CustomHibernateException extends HibernateException {
    public CustomHibernateException(String message) {
        super(message);
    }    
}

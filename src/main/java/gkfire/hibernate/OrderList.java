/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gkfire.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.criterion.Order;

/**
 *
 * @author DannyPC
 */
public class OrderList extends ArrayList<Order> {

    public OrderList() {
    }

    public OrderList(Collection<? extends Order> c) {
        super(c);
    }
    
    
    
}

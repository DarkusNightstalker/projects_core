/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.hibernate.criterion.Order;

/**
 *
 * @author Jhoan Brayam
 */
public class OrderFactory implements java.io.Serializable {

    private final OrderList orderList;
    private OrderList defaultOrder;
    private final Map<String, Boolean> properties;
    private List<String> keys;
//    private List<OrderState> properties;

    public OrderFactory(OrderList initialOrderList) {
        defaultOrder = new OrderList();
        orderList = initialOrderList;
        properties = new HashMap();
        keys = new ArrayList();
//        properties = new ArrayList<>();
    }

    public Boolean showState(String propertyName) {
        return properties.get(propertyName);
//        for (OrderState item : properties) {
//            if (item.getProp().equals(propertyName)) {
//                return item.getType();
//            }
//
//        }
//        return null;
    }

    public int orderNumber(String propertyName) {
        return keys.indexOf(propertyName) + 1;
    }

    public void changeFromRequest() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String propertyName = params.get("property_name");
        this.change(propertyName);
    }

    public void change(String propertyName) {
        Boolean value = properties.get(propertyName);
        if (value == null) {
            properties.put(propertyName, Boolean.TRUE);
            keys.add(propertyName);
        } else if (value) {
            properties.put(propertyName, Boolean.FALSE);
        } else {
            properties.remove(propertyName);
            keys.remove(propertyName);
        }
//        for (OrderState item : properties) {
//            if (item.getProp().equals(propertyName)) {
//                if (item.getType()) {
//                    item.setType(Boolean.FALSE);
//                } else {
//                    properties.remove(item);
//                }
//                return;
//            }
//
//        }
//        properties.add(new OrderState(propertyName));
    }

    public void remove(String propertyName) {
        properties.remove(propertyName);
        keys.remove(propertyName);
//        for (OrderState item : properties) {
//            if (item.getProp().equals(propertyName)) {
//                properties.remove(item);
//                return;
//            }
//
//        }
//        properties.add(new OrderState(propertyName));
    }

    public void removeAll() {
        properties.clear();
        keys.clear();
//        for (int i = 0; i < properties.size(); i++) {
//            properties.remove(0);
//        }
    }

    public OrderList make() {
        if (keys.isEmpty()) {
            return defaultOrder;
        }
        OrderList orderList = new OrderList(this.orderList);
        for (String property : keys) {
            orderList.add(properties.get(property) ? Order.asc(property) : Order.desc(property));
        }
//        if (properties.isEmpty()) {
//            return defaultOrder;
//        }
//        OrderList orderList = new OrderList(this.orderList);
//        for (OrderState item : properties) {
//            orderList.add(item.getType() ? Order.asc(item.getProp()) : Order.desc(item.getProp()));
//        }
        return orderList;
    }

    /**
     * @return the defaultOrder
     */
    public OrderList getDefaultOrder() {
        return defaultOrder;
    }

    /**
     * @param defaultOrder the defaultOrder to set
     */
    public void setDefaultOrder(OrderList defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

    public void setDefaultOrder(Order... defaultOrder) {
        this.defaultOrder = new OrderList(Arrays.asList(defaultOrder));
    }

//    private class OrderState {
//
//        private String prop;
//        private Boolean type;
//
//        public OrderState(String prop, Boolean type) {
//            this.prop = prop;
//            this.type = type;
//        }
//
//        public OrderState(String prop) {
//            this(prop, true);
//        }
//
//        /**
//         * @return the prop
//         */
//        public String getProp() {
//            return prop;
//        }
//
//        /**
//         * @param prop the prop to set
//         */
//        public void setProp(String prop) {
//            this.prop = prop;
//        }
//
//        /**
//         * @return the type
//         */
//        public Boolean getType() {
//            return type;
//        }
//
//        /**
//         * @param type the type to set
//         */
//        public void setType(Boolean type) {
//            this.type = type;
//        }
//    }
}

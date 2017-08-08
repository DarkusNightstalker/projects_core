/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.sql.JoinType;

/**
 *
 * @author Darkus Nightmare
 */
public class SpecialCriterionList implements java.io.Serializable{
    
    private Map<String,List<Criterion>> criterions;
    private Map<String,Object[]> mapAlias;

    public SpecialCriterionList() {
        criterions = new HashMap();
        mapAlias = new HashMap();
    }
    
    
    public void add(String property, String alias) {
        if(!mapAlias.containsKey(property)){            
            mapAlias.put(property,new Object[]{alias,JoinType.INNER_JOIN});
            criterions.put(property, new ArrayList());
        }
    }
    public void add(String property, String alias,JoinType joinType) {
        if(!mapAlias.containsKey(property)){            
            mapAlias.put(property, new Object[]{alias,joinType});
            criterions.put(property, new ArrayList());
        }
    }
    public void add(String property,Criterion c){
        List<Criterion> list = criterions.get(property);
        if(list == null) {
            list = new ArrayList();
            mapAlias.put(property, new Object[]{property,JoinType.INNER_JOIN});
        }
        list.add(c);
        criterions.put(property, list);
    }
    
    public void add(String property,String alias,Criterion c){
        List<Criterion> list = criterions.get(property);
        if(list == null) {
            list = new ArrayList();
            mapAlias.put(property, new Object[]{alias,JoinType.INNER_JOIN});
        }
        list.add(c);
        criterions.put(property, list);
    }
    
    public void add(String property,String alias,JoinType joinType,Criterion c){
        List<Criterion> list = criterions.get(property);
        if(list == null) {
            list = new ArrayList();
            mapAlias.put(property, new Object[]{alias,joinType});
        }
        list.add(c);
        criterions.put(property, list);
    }
    public void make(Criteria criteris){
        criterions.keySet().forEach(key ->{
            Criteria associatedPath = criteris.createCriteria(key,(String)mapAlias.get(key)[0],(JoinType)mapAlias.get(key)[1]);
            criterions.get(key).forEach( criterion ->{
                associatedPath.add(criterion);
            });
        });
    }

    
}

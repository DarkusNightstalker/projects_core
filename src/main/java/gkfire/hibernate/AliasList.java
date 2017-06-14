/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gkfire.hibernate;

import java.util.HashMap;
import org.hibernate.sql.JoinType;

/**
 *
 * @author DannyPC
 */
public class AliasList extends HashMap<String,AliasList.AliasItem> {

    public void add(String property, String alias) {
        super.put(property, new AliasItem(alias, JoinType.INNER_JOIN)); //To change body of generated methods, choose Tools | Templates.
    }
    public void add(String property, String alias,JoinType joinType) {
        super.put(property, new AliasItem(alias, joinType)); //To change body of generated methods, choose Tools | Templates.
    }
    
    public class AliasItem implements java.io.Serializable{
        private String alias;
        private JoinType joinType;

        private AliasItem(String alias, JoinType joinType) {
            this.alias = alias;
            this.joinType = joinType;
        }
        
        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public JoinType getJoinType() {
            return joinType;
        }

        public void setJoinType(JoinType joinType) {
            this.joinType = joinType;
        }
    
        
        
    }
    
    
}

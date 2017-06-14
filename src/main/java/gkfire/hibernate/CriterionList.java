/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gkfire.hibernate;

import java.util.ArrayList;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author DannyPC
 */
public class CriterionList extends ArrayList<Criterion>{

    public CriterionList _add(Criterion e) {
        
         super.add(e); //To change body of generated methods, choose Tools | Templates.
         return this;
    }
    
}

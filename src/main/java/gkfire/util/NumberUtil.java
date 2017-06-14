/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author Jhoan Brayam
 */
public class NumberUtil {
    
    public final static MathContext CURRENCY_CONTEXT = new MathContext(2, RoundingMode.HALF_UP);
    
    public double round(double value, int decimal){
        return new BigDecimal(value,new MathContext(decimal, RoundingMode.HALF_UP)).doubleValue();
    }
}

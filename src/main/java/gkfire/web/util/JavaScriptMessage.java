/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.util;

/**
 *
 * @author CORE i7
 */
public abstract class JavaScriptMessage implements java.io.Serializable{

    public abstract String toJavaScript();

    public void execute() {
        System.out.println(toJavaScript());
        BeanUtil.exceuteJS(toJavaScript());
    }
}

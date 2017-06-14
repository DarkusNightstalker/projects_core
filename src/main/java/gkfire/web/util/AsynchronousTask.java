/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.util;

import javax.ejb.Asynchronous;

/**
 *
 * @author Jhoan Brayam
 */
public abstract class AsynchronousTask implements java.io.Serializable {
    
    private final String openScriptOnLoad;

    public AsynchronousTask(String openScriptOnLoad) {
        this.openScriptOnLoad = openScriptOnLoad;
    }    
    
    public void openOnLoad(){
        if(!isFinished()){
            BeanUtil.exceuteJS(openScriptOnLoad);
        }
    }
    
    protected abstract boolean isFinished();
    
    @Asynchronous
    public abstract void begin();
    
}

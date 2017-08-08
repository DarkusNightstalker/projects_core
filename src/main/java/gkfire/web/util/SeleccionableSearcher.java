/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.util;

/**
 *
 * @author Darkus Nightmare
 */
public abstract class SeleccionableSearcher<T extends java.io.Serializable> extends SimpleSearcher{
   
    protected T selected;

    /**
     * @return the selected
     */
    public T getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(T selected) {
        this.selected = selected;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.bean;

/**
 *
 * @author Jhoan Brayam
 */
public interface INavigationBean extends java.io.Serializable {

    public String getIcon();

    /**
     * @param content the content to set
     */
    public void setIcon(String icon);
    
    public String getContent();

    /**
     * @param content the content to set
     */
    public void setContent(String content);

    /**
     * @return the javascriptMenu
     */
    public String getJavascriptMenu();

    /**
     * @param javascriptMenu the javascriptMenu to set
     */
    public void setJavascriptMenu(String javascriptMenu);
}

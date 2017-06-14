/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.util;

/**
 *
 * @author CORE i7
 */
public class ErrorMessage implements java.io.Serializable{
    private String code;
    private String content;
    private Object[] options;
    
    public ErrorMessage(String code, String content) {
        this.code = code;
        this.content = content;
    }

    public ErrorMessage(String code, String content, Object... options) {
        this.code = code;
        this.content = content;
        this.options = options;
    }
    

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the options
     */
    public Object[] getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(Object[] options) {
        this.options = options;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.bean;

import gkfire.web.util.AbstractImport;
import gkfire.web.util.AsynchronousTask;
import gkfire.web.util.BeanUtil;
import gkfire.web.util.JavaScriptMessage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jhoan Brayam
 */
public abstract class AbstractSessionBean<USER extends Object> implements java.io.Serializable {

    protected ILoadable loadable;
    protected List<JavaScriptMessage> messages;
    protected List<String> permissions;
    protected AbstractImport abstractImport;
    protected AsynchronousTask asynchronousTask;
    protected USER currentUser;

    public AbstractSessionBean() {
        messages = new ArrayList<>();
    }

    public abstract void printErrors(PrintWriter writer);

    public abstract String addError(Throwable e);

    public abstract String printErrorStack();
    
    public void onLoad() {
        try {
            loadable.onLoad(false);
        } catch (NullPointerException e) {
        }
        if (BeanUtil.isAjaxRequest()) {
            return;
        }
        if (asynchronousTask != null) {
            asynchronousTask.openOnLoad();
        }
        loadPermissions();
    }

    public boolean authorize(String codesJoin) {
        String[] codes = codesJoin.split(",");
        for (String item : permissions) {
            for (String code : codes) {
                if (item.equals(code)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String messagesToJS() {
        String js = "";
        while (!messages.isEmpty()) {
            js += getMessages().remove(0).toJavaScript();
        }
        return js;
    }

    protected abstract void loadPermissions();

    /**
     * @return the messages
     */
    public List<JavaScriptMessage> getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(List<JavaScriptMessage> messages) {
        this.messages = messages;
    }

    /**
     * @return the loadable
     */
    public ILoadable getLoadable() {
        return loadable;
    }

    /**
     * @param loadable the loadable to set
     */
    public void setLoadable(ILoadable loadable) {
        this.loadable = loadable;
    }

    /**
     * @return the permissions
     */
    public List<String> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the abstractImport
     */
    public AbstractImport getAbstractImport() {
        return abstractImport;
    }

    /**
     * @param abstractImport the abstractImport to set
     */
    public void setAbstractImport(AbstractImport abstractImport) {
        this.asynchronousTask = abstractImport;
        this.abstractImport = abstractImport;
    }

    /**
     * @return the currentUser
     */
    public USER getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(USER currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return the asynchronousTask
     */
    public AsynchronousTask getAsynchronousTask() {
        return asynchronousTask;
    }

    /**
     * @param asynchronousTask the asynchronousTask to set
     */
    public void setAsynchronousTask(AsynchronousTask asynchronousTask) {
        this.asynchronousTask = asynchronousTask;
    }

}

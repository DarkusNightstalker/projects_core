/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.util;

import gkfire.report.util.ReportContentType;
import gkfire.util.ImportUtils;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.Part;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Jhoan Brayam
 */
public abstract class AbstractImport<T> extends AsynchronousTask {

    protected List<ExecutorService> executors;
    protected List<Object[]> importObjects;
    private Integer currentPlots;
    private Integer totalPlots;
    protected Part file;
    protected Object fileObject;
    protected Integer percentLoad;
    protected Integer totalRecords;
    protected ImportUtils.State state;
    protected boolean seeDetail;

    protected Map<Integer, String> log;
    protected Map<Integer, String> logError;

    public AbstractImport(String openScriptOnLoad) {
        super(openScriptOnLoad);
    }
    public AbstractImport() {
        super("");
    }

    @Override
    protected boolean isFinished() {
        return percentLoad == 100 || state == ImportUtils.State.ERROR;
    }
    

    protected synchronized void addError(Integer row, String message) {
        logError.put(row, message);
    }

    protected synchronized void addSaved(Integer row, String message) {
        log.put(row, message);
    }

    public void refresh() {
        file = null;
        totalRecords = -1;
        percentLoad = 0;
        currentPlots = 0;
        totalPlots = Integer.MAX_VALUE;
        log = new HashMap() {
            @Override
            public Object put(Object key, Object value) {
                Object o = super.put(key, value); //To change body of generated methods, choose Tools | Templates.
                percentLoad = ((this.keySet().size() + 1 + logError.keySet().size()) * 100) / totalRecords;
                return o;
            }

        };
        logError = new HashMap() {
            @Override
            public Object put(Object key, Object value) {
                Object o = super.put(key, value); //To change body of generated methods, choose Tools | Templates.
                percentLoad = ((this.keySet().size() + 1 + log.keySet().size()) * 100) / totalRecords;
                return o;
            }

        };
        importObjects = new ArrayList();
        state = ImportUtils.State.LOAD;
        seeDetail = false;
    }

    public void stop() {
        try {
            for (ExecutorService executor : executors) {
                Object o = executor.shutdownNow();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void begin() {
        try {
            fileObject = ImportUtils.readFile(this.getFile());
        } catch (Exception e) {
            state = ImportUtils.State.ERROR;
            return;
        }
        executors = new ArrayList();
        state = ImportUtils.State.READING;
        currentPlots = 0;
        totalRecords = ImportUtils.countRows(fileObject);
        totalPlots = 50;
//        int trama = totalRecords / 50;
//
//        if (totalRecords % 50 != 0) {
//            trama++;
//        }
//        final int tTrama = trama;
        for (int i = 0; i < 1; i++) {
            final int j = i;
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        beginThread(1, totalRecords);
                    } catch (Exception e) {
                        addError(j, "HILO : " + e.getMessage());
                        e.printStackTrace();
                    }
                    setCurrentPlots((Integer) (getCurrentPlots() + 1));
                    Thread.currentThread().interrupt();
                }
            };
            executor.submit(task);            
            executors.add(executor);
        }
    }

    public StreamedContent downloadLog(boolean isError) throws Exception {
        Path file = Files.createTempFile("", "txt");
        Writer output = new BufferedWriter(new FileWriter(file.toFile()));
        Map<Integer, String> logDisplay = isError ? logError : log;
        if (isError) {
            List<Integer> keys = new ArrayList(logDisplay.keySet());
            Collections.sort(keys);

            for (Integer key : keys) {
                String sRow = "Fila " + (key + 1) + " : " + logDisplay.get(key);
                sRow += "\r\n";
                output.write(sRow);
                output.flush();
            }
        }
        output.close();
        return new DefaultStreamedContent(new FileInputStream(file.toFile()), ReportContentType.TXT.getMimeType(), "Log - " + (!isError ? "Exito" : "Error") + " - " + System.currentTimeMillis() + "." + ReportContentType.TXT.name().toLowerCase());
    }

    public abstract void beginThread(int rowBegin, int trama);

    /**
     * @return the file
     */
    public Part getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(Part file) {
        this.file = file;
    }

    /**
     * @return the percentLoad
     */
    public Integer getPercentLoad() {
        return percentLoad;
    }

    /**
     * @param percentLoad the percentLoad to set
     */
    public void setPercentLoad(Integer percentLoad) {
        this.percentLoad = percentLoad;
    }

    /**
     * @return the state
     */
    public ImportUtils.State getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(ImportUtils.State state) {
        this.state = state;
    }

    /**
     * @return the totalRecords
     */
    public Integer getTotalRecords() {
        return totalRecords;
    }

    /**
     * @param totalRecords the totalRecords to set
     */
    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * @return the importObjects
     */
    public List<Object[]> getImportObjects() {
        return importObjects;
    }

    /**
     * @param importObjects the importObjects to set
     */
    public void setImportObjects(List<Object[]> importObjects) {
        this.importObjects = importObjects;
    }

    /**
     * @return the seeDetail
     */
    public boolean isSeeDetail() {
        return seeDetail;
    }

    /**
     * @param seeDetail the seeDetail to set
     */
    public void setSeeDetail(boolean seeDetail) {
        this.seeDetail = seeDetail;
    }

    /**
     * @return the fileObject
     */
    public Object getFileObject() {
        return fileObject;
    }

    /**
     * @param fileObject the fileObject to set
     */
    public void setFileObject(Object fileObject) {
        this.fileObject = fileObject;
    }

    /**
     * @return the log
     */
    public Map<Integer, String> getLog() {
        return log;
    }

    /**
     * @param log the log to set
     */
    public void setLog(Map<Integer, String> log) {
        this.log = log;
    }

    /**
     * @return the logError
     */
    public Map<Integer, String> getLogError() {
        return logError;
    }

    /**
     * @param logError the logError to set
     */
    public void setLogError(Map<Integer, String> logError) {
        this.logError = logError;
    }

    /**
     * @return the currentPlots
     */
    public Integer getCurrentPlots() {
        return currentPlots;
    }

    /**
     * @param currentPlots the currentPlots to set
     */
    public void setCurrentPlots(Integer currentPlots) {
        this.currentPlots = currentPlots;
    }

    /**
     * @return the totalPlots
     */
    public Integer getTotalPlots() {
        return totalPlots;
    }

    /**
     * @param totalPlots the totalPlots to set
     */
    public void setTotalPlots(Integer totalPlots) {
        this.totalPlots = totalPlots;
    }

}

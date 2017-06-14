/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.util;

import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.generic.interfac.IGenericService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jhoan Brayam
 * @param <S>
 */
public class Pagination<T> implements Serializable {

    private static List<Object[]> rowsData;

    static {
        rowsData = new ArrayList();
        rowsData.add(new Object[]{10, "10"});
        rowsData.add(new Object[]{15, "15"});
        rowsData.add(new Object[]{20, "20"});
        rowsData.add(new Object[]{30, "30"});
        rowsData.add(new Object[]{50, "50"});
        rowsData.add(new Object[]{100, "100"});
        rowsData.add(new Object[]{-1, "Todos"});
    }

    private Integer page;
    private Integer rows;
    private Integer lastPage;
    private String messageTemplate;
    private Object[] tempVariant;
    private Long totalRecords;
    private List<T> data;

    private IGenericService service;

    public Pagination(IGenericService service) {
        this("Mostrando <b>{START}</b> a <b>{END}</b> de <span class='text-primary'>{TR}</span> registros", service);
    }

    public Pagination(String messageTemplate, IGenericService service) {
        this.messageTemplate = messageTemplate;
        this.service = service;
        rows = (Integer) rowsData.get(0)[0];
    }

    public String message() {
        try {
            String message = messageTemplate
                    .replace("{TR}", totalRecords.toString())
                    .replace("{START}", getRecordStart().toString())
                    .replace("{END}", getRecordEnd().toString());
            return message;
        } catch (Exception e) {
            return "...";
        }
    }

    public List getRowsData() {
        return rowsData;
    }

    public Integer getRecordStart() {
        try {
            int currentRows = rows < 0 ? totalRecords.intValue() : rows;
            return ((page - 1) * currentRows) + 1;
        } catch (Exception e) {
            return null;
        }
    }

    public void first() {
        search(1, tempVariant);
    }

    public void last() {
        search(lastPage, tempVariant);
    }

    public void next() {
        search(page + 1, tempVariant);
    }

    public void previous() {
        search(page - 1, tempVariant);
    }

    public void changeRows() {
        search(1, tempVariant);
    }

    public Integer getRecordEnd() {
        try {
            int currentRows = rows < 0 ? totalRecords.intValue() : rows;
            int v = (page * currentRows);

            return v > totalRecords ? totalRecords.intValue() : v;
        } catch (Exception e) {
            return null;
        }
    }

    public void search(int page, Object... variant) {
        if(variant == null){
            setData(Collections.EMPTY_LIST);
            return;
        }
        tempVariant = variant;
        totalRecords = null;
        CriterionList criterionList = null;
        AliasList aliasList = null;
        for (Object o : variant) {
            if (o instanceof CriterionList) {
                criterionList = (CriterionList) o;
            } else if (o instanceof AliasList) {
                aliasList = (AliasList) o;
            } else {
                continue;
            }
        }
        if (criterionList != null) {
            try {
                totalRecords = service.countRestrictions(criterionList, aliasList).longValue();
            } catch (Exception e) {
                totalRecords = 0L;
                e.printStackTrace();
            }
        } else {
            totalRecords = service.count().longValue();
        }

        if (totalRecords != 0) {
            int currentRows = rows < 0 ? totalRecords.intValue() : rows;
            this.lastPage = totalRecords.intValue() / currentRows;
            if (totalRecords.intValue() % currentRows != 0) {
                this.lastPage++;
            }
            if (page > lastPage) {
                page = lastPage;
            }
            this.page = page;
            data = service.addRestrictionsVariant(currentRows, page, variant);
        } else {
            this.page = null;
            this.lastPage = null;
            data = Collections.EMPTY_LIST;
        }
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the rows
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    /**
     * @param messageTemplate the messageTemplate to set
     */
    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    /**
     * @return the totalRecords
     */
    public Long getTotalRecords() {
        return totalRecords;
    }

    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        if(data.isEmpty()){
            this.totalRecords = 0L;
            this.page = null;
            this.lastPage = null;
            this.tempVariant=null;
        }
    }

    /**
     * @param service the service to set
     */
    public void setService(IGenericService service) {
        this.service = service;
    }

    /**
     * @return the lastPage
     */
    public Integer getLastPage() {
        return lastPage;
    }

}

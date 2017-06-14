/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.report.util;

import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author Jhoan Brayam
 */
public enum ReportContentType {

    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", new JRDocxExporter()),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new JRXlsxExporter()),
    XLS("application/vnd.ms-excel", new JRXlsExporter()),
    PDF("application/pdf", new JRPdfExporter()),
    TXT("text/plain", new JRTextExporter()),
    SYBASE("",null),
    HTML("text/html",new JRHtmlExporter());

    private final String mimeType;
    private final Object exporter;

    private ReportContentType(String mimeType, Object exporter) {
        this.mimeType = mimeType;
        this.exporter = exporter;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Object getExporter() {
        return exporter;
    }
    

}

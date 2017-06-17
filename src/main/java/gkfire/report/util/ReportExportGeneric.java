package gkfire.report.util;

import gkfire.web.bean.AbstractSessionBean;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.function.Function;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

public class ReportExportGeneric implements Serializable {

    @FunctionalInterface
    protected interface FunctionOutputStream {
        OutputStream apply() throws Exception;
    }
    @FunctionalInterface
    protected interface FunctionConnection {
        Connection apply() throws Exception;
    }
    protected String pathJasper;
    protected String fileName;
    protected AbstractSessionBean sessionBean;
    protected Map<String, Object> params;
    protected ReportContentType contentType;
    protected String reportsDirPath;
    protected FunctionOutputStream functionOutputstream;
    protected Function<String, InputStream> functionInputstream;
    protected FunctionConnection functionConnection;

    public ReportExportGeneric(String pathJasper, String fileName, AbstractSessionBean sessionBean, Map<String, Object> defaultParams) {
        this.pathJasper = pathJasper;
        this.sessionBean = sessionBean;
        this.fileName = fileName;
        this.params = defaultParams;
    }

    public void toGenericXlsx() throws Exception {
        this.contentType = ReportContentType.XLS;
        this.params.put("IS_IGNORE_PAGINATION", true);
        execute(new JRXlsExporter());
    }

    public void toGenericPdf() throws Exception {
        this.contentType = ReportContentType.PDF;
        this.params.put("IS_IGNORE_PAGINATION", false);
        execute((JRExporter) this.contentType.getExporter());
    }

    public void toGenericDocx() throws Exception {
        this.contentType = ReportContentType.DOCX;
        this.params.put("IS_IGNORE_PAGINATION", false);
        execute((JRExporter) this.contentType.getExporter());
    }

    public void toGenericTxt() throws Exception {
        this.contentType = ReportContentType.TXT;
        this.params.put("IS_IGNORE_PAGINATION", true);
        execute((JRExporter) this.contentType.getExporter());
    }

    public void toGenericHtml() throws Exception {
        this.contentType = ReportContentType.HTML;
        this.params.put("IS_IGNORE_PAGINATION", true);
        execute((JRExporter) this.contentType.getExporter());
    }

    protected void execute(JRExporter exporter) throws Exception {
        InputStream is = functionInputstream.apply(pathJasper);
        File reportsDir = new File(reportsDirPath);
        this.params.put("REPORT_FILE_RESOLVER", new SimpleFileResolver(reportsDir));

        JasperPrint jasperPrint = JasperFillManager.fillReport(is, this.params, functionConnection.apply());
        try (OutputStream os = functionOutputstream.apply()) {
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
            exporter.exportReport();
            FacesContext.getCurrentInstance().responseComplete();
            os.flush();
            os.close();
        }
        System.gc();
    }

    public ReportContentType getContentType() {
        return this.contentType;
    }

    public void setContentType(ReportContentType contentType) {
        this.contentType = contentType;
    }

    public String getPathJasper() {
        return this.pathJasper;
    }

    public void setPathJasper(String pathJasper) {
        this.pathJasper = pathJasper;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public AbstractSessionBean getSessionBean() {
        return this.sessionBean;
    }

    public void setSessionBean(AbstractSessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}

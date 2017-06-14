/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.report.util;

import gkfire.web.util.BeanUtil;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;

/**
 *
 * @author Danny
 */
@ManagedBean
@SessionScoped
public class IReportManager implements Serializable {

    private JasperPrint jasperPrint;

    public IReportManager(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    /**
     * Para la asignación de JaserPrint,
     *
     * @param filePathWeb ruta de la carpeta que contiene todas las vistas
     * (paginas web) EJEMPLO: <code>"/rpJSF.jasper"</code>
     * @param param parametros para el envío hacia el reporte
     * @param collection EJEMPLO:
     * <code>new JRBeanCollectionDataSource(this.getLstPersonas())</code>
     * @throws JRException
     */
    public void setJasperPrint(String filePathWeb, Map<String, Object> param, JRBeanCollectionDataSource collection) throws JRException {
        File jasper = new File(BeanUtil.getRealPath(filePathWeb));
        jasperPrint = JasperFillManager.fillReport(jasper.getPath(), param, collection);
    }

    /**
     *
     * @param filePathWeb ruta de la carpeta que contiene todas las vistas
     * (paginas web) EJEMPLO: <code>"/rpJSF.jasper"</code>
     * @param param parametros para el envío hacia el reporte
     * @param connection JDBC Connexion.
     * @throws JRException
     */
    public void setJasperPrint(String filePathWeb, Map<String, Object> param, Connection connection) throws JRException {
        File jasper = new File(BeanUtil.getRealPath(filePathWeb));
        jasperPrint = JasperFillManager.fillReport(jasper.getPath(), param, connection);
    }

    public void exportarPDF(String outputName) throws JRException, IOException {
//        HttpServletResponse response = Faces.getResponse();
//        if (!outputName.endsWith(".pdf")) {
//            outputName = outputName + ".pdf";
//        }
//        response.addHeader("Content-disposition", "attachment; filename=" + outputName);
//        ServletOutputStream stream = response.getOutputStream();
//
//        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
//        stream.flush();
//        stream.close();
//
//        Faces.getCurrentInstance().responseComplete();

        if (!outputName.endsWith(".pdf")) {
            outputName = outputName + ".pdf";
        }
        String path = FacesContext.getCurrentInstance().getExternalContext()
                .getRealPath("/");
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, (path + "view\\pages\\report\\file\\" + outputName));
        exporter.exportReport();
        File ficheroDoc = new File((path + "view\\pages\\report\\file\\" + outputName));
        FacesContext ctx = FacesContext.getCurrentInstance();
        FileInputStream fis = new FileInputStream(ficheroDoc);
        byte[] bytes = new byte[1000];
        int read = 0;

        if (!ctx.getResponseComplete()) {
            String fileName = outputName;
            String contentType = "application/pdf";
            HttpServletResponse response
                    = (HttpServletResponse) ctx.getExternalContext().getResponse();

            response.setContentType(contentType);

            response.setHeader("Content-Disposition",
                    "attachment;filename=\"" + fileName + "\"");

            ServletOutputStream out = response.getOutputStream();

            while ((read = fis.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            out.flush();
            out.close();
            System.out.println("\nDescargado\n");
            ctx.responseComplete();
            out = null;
            System.gc();
            clearFile(path + "view\\pages\\report\\file\\");

        }

    }

    private void clearFile(String strFolderSource) {
        String sourcePath = strFolderSource;
        File prueba = new File(sourcePath);
        File[] ficheros = prueba.listFiles();
        File f = null;
        if (prueba.exists()) {
            for (int x = 0; x < ficheros.length; x++) {
                f = new File(ficheros[x].toString());
                System.out.println("Archivo:" + ficheros[x].getName());
                f.delete();
            }
        } else {
            System.out.println("No existe el directorio");
        }
    }

    public void verPDF(ActionEvent actionEvent) throws Exception {
//        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/rpJSF.jasper"));
//
//        byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), null, new JRBeanCollectionDataSource(this.getLstPersonas()));
//        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//        response.setContentType("application/pdf");
//        response.setContentLength(bytes.length);
//        ServletOutputStream outStream = response.getOutputStream();
//        outStream.write(bytes, 0, bytes.length);
//        outStream.flush();
//        outStream.close();
//
//        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarExcel(String outputName) throws JRException, IOException {
        HttpServletResponse response = BeanUtil.getResponse();
        if (!outputName.endsWith(".xls")) {
            outputName = outputName + ".xls";
        }
        response.addHeader("Content-disposition", "attachment; filename=" + outputName);
        ServletOutputStream outStream = response.getOutputStream();

        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
        exporter.exportReport();

        outStream.flush();
        outStream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarDOC(String outputName) throws JRException, IOException {
        HttpServletResponse response = BeanUtil.getResponse();
        if (!outputName.endsWith(".doc")) {
            outputName = outputName + ".doc";
        }
        response.addHeader("Content-disposition", "attachment; filename=" + outputName);
        ServletOutputStream outStream = response.getOutputStream();

        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
        exporter.exportReport();

        outStream.flush();
        outStream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarPPT(String outputName) throws JRException, IOException {
        HttpServletResponse response = BeanUtil.getResponse();
        if (!outputName.endsWith(".ppt")) {
            outputName = outputName + ".ppt";
        }
        response.addHeader("Content-disposition", "attachment; filename=" + outputName);
        ServletOutputStream outStream = response.getOutputStream();

        JRPptxExporter exporter = new JRPptxExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
        exporter.exportReport();

        outStream.flush();
        outStream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
}

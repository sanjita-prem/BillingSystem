package com.billing.pdf;

import java.io.File;
import java.util.Map;

import com.billing.AppContext;
import com.billing.util.DesktopApi;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class PDFGenerator {
	public static String generateBillPDF(ActionEvent event, Map<String, Object> param) throws JRException {
		
		String outPath = AppContext.getValue(AppContext.BILL_LOCATION);
		
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setTitle("Choose bill location");
		DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(outPath));
        File selectedFile = directoryChooser.showDialog(stage);
        AppContext.setContext(AppContext.BILL_LOCATION, selectedFile.getAbsolutePath());

		String path = AppContext.APP_SYSTEM_PATH+"bill1.jrxml";
		JasperReport jasperReport = JasperCompileManager.compileReport(path);
		JRDataSource dataSource = new JREmptyDataSource();
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, dataSource);
		
		// Make sure the output directory exists.
		String fileName = (String)param.get("billReceiptId");
		
        outPath = AppContext.getValue(AppContext.BILL_LOCATION);
		fileName = fileName.replaceAll("/", "_");
		File outDir = new File(outPath);
		outDir.mkdirs();
		outPath = outPath+"/"+fileName+".pdf";
		// Export to PDF.
		JasperExportManager.exportReportToPdfFile(jasperPrint, outPath);
		
		return outPath;
	}
	
	public static void OpenPdfFile(String filePath){
		DesktopApi.open(new File(filePath));
	}
}

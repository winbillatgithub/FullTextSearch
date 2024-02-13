package com.as.filesearch.controller.portal.searchfile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * @author 作者:Randy,Sun
 * @date 创建时间:2016年7月26日 上午10:43:33 生成文书的pdf格式
 */
public class GenerateJudgeDPDF {

	public static String getGUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public static void exportSalesReport(HttpServletRequest request, HttpServletResponse response,String fileContent,String fileName) {
		String storePDFName = getGUID(); // 随机生成pdf的文件名称
		String pdfFullFilePath = request.getSession().getServletContext().getRealPath("/pdfHome/");
		String pdfFullFileName = pdfFullFilePath + "\\" + storePDFName + ".pdf";
		
		String fontsFullFilePath = request.getSession().getServletContext().getRealPath("/fonts/simsun.ttc,1");
		// 生成文档存入到本地
		Rectangle rectPageSize = new Rectangle(PageSize.A4);// A4纸张
		Document document = new Document(rectPageSize, 40, 40, 40, 40);
		try {
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFullFileName));
			document.open();
			// 字体
			BaseFont baseFont = BaseFont.createFont(fontsFullFilePath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            if(null !=fileContent && !"".equals(fileContent)){
            	fileContent = URLDecoder.decode( fileContent,"UTF-8");
            }
            if(null == fileName || "".equals(fileName)){
            	fileName = storePDFName;
            }
			 
			StringBuffer html = new StringBuffer();
			// DOCTYPE 必需写否则类似于 这样的字符解析会出现错误
			html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			html.append("<html>")
			    .append("<head>")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
				.append("</head>")
				.append("<body>");
		    html.append(fileContent);
			html.append("</body></html>");  
			InputStream htmlInputStream = new ByteArrayInputStream(html.toString().getBytes());
			XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document,htmlInputStream, Charset.forName("UTF-8"));
			document.close();

			// 读取文档并且web输出
			InputStream inputStream = new BufferedInputStream(new FileInputStream(pdfFullFileName));
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			inputStream.close();
			response.reset();
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			response.setContentType("application/pdf");
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();

			// 删除临时文件
			File tempFile = new File(pdfFullFileName);
			if (tempFile.exists() && tempFile.isFile()) {
				tempFile.delete();
			}

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

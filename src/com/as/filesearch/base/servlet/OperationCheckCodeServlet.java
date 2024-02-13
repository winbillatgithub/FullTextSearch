package com.as.filesearch.base.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.as.filesearch.base.entity.Constant;

public class OperationCheckCodeServlet  extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//图片宽高
		int width = 180, height = 35;

		//随机运算结果
		String baseStr = generateCheckCode(request);

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		OutputStream os = response.getOutputStream();
		Graphics g = image.getGraphics();

		Random random = new Random();

		g.setColor(getRandColor(random, 200, 250));
		g.fillRect(0, 0, width, height);

		String[] fontTypes = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };
		int fontTypesLength = fontTypes.length;

		g.setColor(getRandColor(random, 160, 200));
		g.setFont(new Font("Times New Roman", Font.PLAIN, 20 + random.nextInt(6)));
		
		for (int i = 0; i < 255; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		
		String [] baseChar = baseStr.split(" ");
		for (int i = 0; i < baseChar.length; i++) {
			g.setColor(getRandColor(random, 30, 150));
			g.setFont(new Font(fontTypes[random.nextInt(fontTypesLength)], Font.BOLD, 30 + random.nextInt(6)));
			g.drawString(baseChar[i], 34 * i + 10, 30);
		}
		
		g.dispose();

		ImageIO.write(image, "JPEG", os);
		os.flush();
		os.close();
	}
	
	/**
	 * 生成随机颜色
	 * @param random
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(Random random, int fc, int bc){
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 获取运算结果
	 * @param request
	 * @return
	 */
	private String generateCheckCode(HttpServletRequest request) {
		Random random = new Random();
		int intTemp;
		int intFirst = random.nextInt(10);
		int intSec = random.nextInt(10);
		String checkCode = "";
		int result = 0;
		switch (random.nextInt(6)) {
			case 0:
				if (intFirst < intSec) {
					intTemp = intFirst;
					intFirst = intSec;
					intSec = intTemp;
				}
				checkCode = intFirst + " - " + intSec + " = ?";
				result = intFirst-intSec;
				break;
			case 1:
				if (intFirst < intSec) {
					intTemp = intFirst;
					intFirst = intSec;
					intSec = intTemp;
				}
				checkCode = intFirst + " - ? = "+(intFirst-intSec);
				result = intSec;
				break;
			case 2:
				if (intFirst < intSec) {
					intTemp = intFirst;
					intFirst = intSec;
					intSec = intTemp;
				}
				checkCode = "? - "+intSec+" = "+(intFirst-intSec);
				result = intFirst;
				break;
			case 3:
				checkCode = intFirst + " + " + intSec + " = ?";
				result = intFirst + intSec;
				break;
			case 4:
				checkCode = intFirst + " + ? ="+(intFirst+intSec);
				result = intSec;
				break;
			case 5:
				checkCode = "? + " + intSec + " ="+(intFirst+intSec);
				result = intFirst;
				break;
		}
		request.getSession().setAttribute(Constant._OPERATION_CHECK_CODE, result);
		return checkCode;
	}

}

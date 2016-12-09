package com.yqboots.web.thymeleaf.support;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.file.Path;
import java.util.Map;

import com.lowagie.text.pdf.BaseFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Component
public class Html2PdfGenerator {
	@Autowired
	private TemplateEngine templateEngine;

	public void generate(String template, Map<String, Object> variables, Path output) throws Exception {
		try (StringWriter writer = new StringWriter();
		     BufferedWriter bw = new BufferedWriter(writer)) {
			// get context
			Context ctx = new Context();
			ctx.setVariables(variables);
			templateEngine.process(template, ctx, bw);

			// output html
			bw.flush();
			writer.flush();

			toPDF(writer.toString(), output);
		}
	}

	private void toPDF(String inputHtml, Path output) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(inputHtml.getBytes("UTF-8")));
		ITextRenderer renderer = new ITextRenderer();

		ITextFontResolver fontResolver = renderer.getFontResolver();
		fontResolver.addFont("C:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		fontResolver.addFont("C:/Windows/Fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		renderer.setDocument(doc, null);
		renderer.layout();
		renderer.createPDF(new FileOutputStream(output.toString()));
	}
	
	private void toPDF(String inputHtmls[], Path output) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		ITextRenderer renderer = new ITextRenderer();

		ITextFontResolver fontResolver = renderer.getFontResolver();
		fontResolver.addFont("C:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		fontResolver.addFont("C:/Windows/Fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		
		renderer.createPDF(new FileOutputStream(output.toString()));
		for(int i=1; i< inputHtmls.length; i++) {
			Document doc = builder.parse(new ByteArrayInputStream(inputHtml[i].getBytes("UTF-8")));
			renderer.setDocument(doc, null);
			renderer.layout();
			renderer.writeNextDocument();
		}
		renderer.finishPDF();
	}
}

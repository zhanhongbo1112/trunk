/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.web.thymeleaf.support;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.pdf.BaseFont;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Utility method for generating PDF based on thymeleaf templates.
 * <br/>
 * TODO: chinese fonts can not be displayed.
 *
 * @author Eric H B Zhan
 * @since 1.2.0
 */
public class Html2PdfGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(Html2PdfGenerator.class);

    private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");

    private final TemplateEngine templateEngine;

    private final String[] fonts;

    public Html2PdfGenerator(final TemplateEngine templateEngine, final String[] fonts) {
        this.templateEngine = templateEngine;
        this.fonts = fonts;
    }

    /**
     * Generates PDF file.
     *
     * @param template  the Thymeleaf template
     * @param variables the variables passed to the template
     * @param output    the output path
     * @throws IOException if error happened
     */
    public void generate(String template, Map<String, Object> variables, Path output) throws IOException {
        generate(new String[]{template}, variables, output);
    }

    /**
     * Generates PDF files of multiple pages, using more than one template.
     *
     * @param templates the Thymeleaf templates
     * @param variables the variables passed to the template
     * @param output    the output path
     * @throws IOException if error happened
     */
    public void generate(final String[] templates, final Map<String, Object> variables, final Path output) throws IOException {
        Assert.notEmpty(templates);

        // get context
        final Context context = new Context();
        context.setVariables(variables);

        final List<String> pages = new ArrayList<>();
        try {
            for (final String template : templates) {
                try (
                        final StringWriter writer = new StringWriter();
                        final BufferedWriter bw = new BufferedWriter(writer)
                ) {
                    templateEngine.process(template, context, bw);

                    // output html
                    bw.flush();
                    writer.flush();

                    pages.add(writer.toString());
                }
            }

            toPDF(pages.toArray(new String[pages.size()]), output);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IOException(e);
        }
    }

    /**
     * Convert the parsed Thymeleaf templates to PDF.
     *
     * @param inputHTMLs parsed html templates
     * @param output     the output path
     * @throws Exception
     */
    private void toPDF(final String[] inputHTMLs, final Path output) throws Exception {
        if (ArrayUtils.isEmpty(inputHTMLs)) {
            return;
        }

        final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        final ITextRenderer renderer = new ITextRenderer();
        addFonts(renderer, fonts);

        Document doc = builder.parse(new ByteArrayInputStream(inputHTMLs[0].getBytes(DEFAULT_ENCODING)));
        renderer.setDocument(doc, null);
        renderer.layout();
        renderer.createPDF(new FileOutputStream(output.toString()), false);
        for (int i = 1; i < inputHTMLs.length; i++) {
            doc = builder.parse(new ByteArrayInputStream(inputHTMLs[i].getBytes(DEFAULT_ENCODING)));
            renderer.setDocument(doc, null);
            renderer.layout();
            renderer.writeNextDocument();
        }
        renderer.finishPDF();
    }

    /**
     * Add fonts to {@link ITextRenderer}, for chinese support.
     *
     * @param renderer renderer
     * @param fonts    fonts
     * @throws Exception
     */
    private void addFonts(final ITextRenderer renderer, final String[] fonts) throws Exception {
        if (ArrayUtils.isEmpty(fonts)) {
            return;
        }

        Assert.notNull(renderer);

        final ITextFontResolver fontResolver = renderer.getFontResolver();
        for (final String font : fonts) {
            final String fontPath = ResourceUtils.getFile(font).getAbsolutePath();
            fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        }
    }
}

package com.resumebuilder;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class ResumeTemplate {
    public enum TemplateStyle {
        PROFESSIONAL,
        CREATIVE,
        MODERN,
        MINIMALIST
    }

    public enum PageSize {
        A4(com.itextpdf.text.PageSize.A4),
        LETTER(com.itextpdf.text.PageSize.LETTER),
        LEGAL(com.itextpdf.text.PageSize.LEGAL);

        private final com.itextpdf.text.Rectangle size;

        PageSize(com.itextpdf.text.Rectangle size) {
            this.size = size;
        }

        public com.itextpdf.text.Rectangle getSize() { return size; }
    }

    private static final Map<TemplateStyle, Font> TITLE_FONTS = new HashMap<>();
    private static final Map<TemplateStyle, Font> HEADING_FONTS = new HashMap<>();
    private static final Map<TemplateStyle, Font> BODY_FONTS = new HashMap<>();
    private static final Map<TemplateStyle, BaseColor> ACCENT_COLORS = new HashMap<>();

    static {
        // Initialize fonts and colors for each template style
        initializeProfessionalStyle();
        initializeCreativeStyle();
        initializeModernStyle();
        initializeMinimalistStyle();
    }

    private static void initializeProfessionalStyle() {
        TITLE_FONTS.put(TemplateStyle.PROFESSIONAL, new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD));
        HEADING_FONTS.put(TemplateStyle.PROFESSIONAL, new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
        BODY_FONTS.put(TemplateStyle.PROFESSIONAL, new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL));
        ACCENT_COLORS.put(TemplateStyle.PROFESSIONAL, new BaseColor(0, 51, 102)); // Navy blue
    }

    private static void initializeCreativeStyle() {
        TITLE_FONTS.put(TemplateStyle.CREATIVE, new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD));
        HEADING_FONTS.put(TemplateStyle.CREATIVE, new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
        BODY_FONTS.put(TemplateStyle.CREATIVE, new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL));
        ACCENT_COLORS.put(TemplateStyle.CREATIVE, new BaseColor(102, 0, 102)); // Purple
    }

    private static void initializeModernStyle() {
        TITLE_FONTS.put(TemplateStyle.MODERN, new Font(Font.FontFamily.HELVETICA, 26, Font.BOLD));
        HEADING_FONTS.put(TemplateStyle.MODERN, new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD));
        BODY_FONTS.put(TemplateStyle.MODERN, new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL));
        ACCENT_COLORS.put(TemplateStyle.MODERN, new BaseColor(0, 102, 51)); // Forest green
    }

    private static void initializeMinimalistStyle() {
        TITLE_FONTS.put(TemplateStyle.MINIMALIST, new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD));
        HEADING_FONTS.put(TemplateStyle.MINIMALIST, new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD));
        BODY_FONTS.put(TemplateStyle.MINIMALIST, new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL));
        ACCENT_COLORS.put(TemplateStyle.MINIMALIST, new BaseColor(51, 51, 51)); // Dark gray
    }

    public static void exportToPDF(String title, String content, File outputFile, 
                                 TemplateStyle style, File profileImage,
                                 PageSize pageSize, boolean includePageNumbers) throws Exception {
        ResumeStyles.StyleConfig styleConfig = ResumeStyles.getStyleConfig(style);
        
        Document document = new Document(pageSize.getSize());
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        
        // Add page numbers if requested
        if (includePageNumbers) {
            writer.setPageEvent(new PageNumbers());
        }
        
        document.open();

        // Set margins
        document.setMargins(
            styleConfig.getMarginLeft(),
            styleConfig.getMarginRight(),
            styleConfig.getMarginTop(),
            styleConfig.getMarginBottom()
        );

        // Add profile image if provided
        if (profileImage != null && profileImage.exists()) {
            Image image = Image.getInstance(profileImage.getAbsolutePath());
            image.scaleToFit(100, 100);
            image.setAlignment(Element.ALIGN_RIGHT);
            document.add(image);
        }

        // Add title
        Paragraph titleParagraph = new Paragraph(title, styleConfig.getTitleFont());
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.setSpacingAfter(20);
        document.add(titleParagraph);

        // Add content with sections
        String[] sections = content.split("\\n\\n");
        for (String section : sections) {
            if (section.trim().isEmpty()) continue;

            String[] lines = section.split("\\n");
            if (lines.length > 0) {
                // First line is the section heading
                Paragraph heading = new Paragraph(lines[0], styleConfig.getHeadingFont());
                heading.setSpacingBefore(15);
                heading.setSpacingAfter(10);
                
                // Add a line under the heading
                LineSeparator ls = new LineSeparator();
                java.awt.Color awtColor = styleConfig.getColorScheme().getPrimary();
                ls.setLineColor(new BaseColor(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue()));
                ls.setLineWidth(1);
                heading.add(new Chunk(ls));
                
                document.add(heading);

                // Rest of the lines are the section content
                for (int i = 1; i < lines.length; i++) {
                    Paragraph contentLine = new Paragraph(lines[i], styleConfig.getBodyFont());
                    contentLine.setSpacingAfter(5);
                    contentLine.setLeading(styleConfig.getLineSpacing() * contentLine.getFont().getSize());
                    document.add(contentLine);
                }
            }
        }

        document.close();
    }

    private static class PageNumbers extends PdfPageEventHelper {
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            cb.saveState();
            
            try {
                // Add page number
                cb.beginText();
                cb.setFontAndSize(BaseFont.createFont(), 10);
                cb.showTextAligned(Element.ALIGN_CENTER, 
                    String.format("Page %d", writer.getPageNumber()),
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
                cb.endText();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            cb.restoreState();
        }
    }

    public static String getTemplateContent(TemplateStyle style) {
        return ResumeStyles.getTemplateContent(style);
    }
} 
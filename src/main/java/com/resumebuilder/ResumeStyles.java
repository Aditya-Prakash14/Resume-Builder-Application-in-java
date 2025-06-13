package com.resumebuilder;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ResumeStyles {
    public enum ColorScheme {
        BLUE(new Color(0, 51, 102), new Color(0, 102, 204), new Color(204, 229, 255)),
        GREEN(new Color(0, 102, 51), new Color(0, 153, 76), new Color(204, 255, 229)),
        PURPLE(new Color(102, 0, 102), new Color(153, 0, 153), new Color(255, 204, 255)),
        RED(new Color(153, 0, 0), new Color(204, 0, 0), new Color(255, 204, 204)),
        GRAY(new Color(51, 51, 51), new Color(102, 102, 102), new Color(229, 229, 229));

        private final Color primary;
        private final Color secondary;
        private final Color accent;

        ColorScheme(Color primary, Color secondary, Color accent) {
            this.primary = primary;
            this.secondary = secondary;
            this.accent = accent;
        }

        public Color getPrimary() { return primary; }
        public Color getSecondary() { return secondary; }
        public Color getAccent() { return accent; }
    }

    public enum FontFamily {
        HELVETICA(Font.FontFamily.HELVETICA),
        TIMES_ROMAN(Font.FontFamily.TIMES_ROMAN),
        COURIER(Font.FontFamily.COURIER);

        private final Font.FontFamily family;

        FontFamily(Font.FontFamily family) {
            this.family = family;
        }

        public Font.FontFamily getFamily() { return family; }
    }

    public static class StyleConfig {
        private final Font titleFont;
        private final Font headingFont;
        private final Font bodyFont;
        private final ColorScheme colorScheme;
        private final float lineSpacing;
        private final float marginLeft;
        private final float marginRight;
        private final float marginTop;
        private final float marginBottom;

        public StyleConfig(Font titleFont, Font headingFont, Font bodyFont, 
                         ColorScheme colorScheme, float lineSpacing,
                         float marginLeft, float marginRight, 
                         float marginTop, float marginBottom) {
            this.titleFont = titleFont;
            this.headingFont = headingFont;
            this.bodyFont = bodyFont;
            this.colorScheme = colorScheme;
            this.lineSpacing = lineSpacing;
            this.marginLeft = marginLeft;
            this.marginRight = marginRight;
            this.marginTop = marginTop;
            this.marginBottom = marginBottom;
        }

        public Font getTitleFont() { return titleFont; }
        public Font getHeadingFont() { return headingFont; }
        public Font getBodyFont() { return bodyFont; }
        public ColorScheme getColorScheme() { return colorScheme; }
        public float getLineSpacing() { return lineSpacing; }
        public float getMarginLeft() { return marginLeft; }
        public float getMarginRight() { return marginRight; }
        public float getMarginTop() { return marginTop; }
        public float getMarginBottom() { return marginBottom; }
    }

    private static final Map<ResumeTemplate.TemplateStyle, StyleConfig> STYLE_CONFIGS = new HashMap<>();

    static {
        initializeStyleConfigs();
    }

    private static void initializeStyleConfigs() {
        // Professional Style
        STYLE_CONFIGS.put(ResumeTemplate.TemplateStyle.PROFESSIONAL,
            new StyleConfig(
                new Font(FontFamily.HELVETICA.getFamily(), 24, Font.BOLD),
                new Font(FontFamily.HELVETICA.getFamily(), 14, Font.BOLD),
                new Font(FontFamily.HELVETICA.getFamily(), 12, Font.NORMAL),
                ColorScheme.BLUE,
                1.5f,
                50f, 50f, 50f, 50f
            )
        );

        // Creative Style
        STYLE_CONFIGS.put(ResumeTemplate.TemplateStyle.CREATIVE,
            new StyleConfig(
                new Font(FontFamily.HELVETICA.getFamily(), 28, Font.BOLD),
                new Font(FontFamily.HELVETICA.getFamily(), 16, Font.BOLD),
                new Font(FontFamily.HELVETICA.getFamily(), 12, Font.NORMAL),
                ColorScheme.PURPLE,
                1.8f,
                40f, 40f, 40f, 40f
            )
        );

        // Modern Style
        STYLE_CONFIGS.put(ResumeTemplate.TemplateStyle.MODERN,
            new StyleConfig(
                new Font(FontFamily.HELVETICA.getFamily(), 26, Font.BOLD),
                new Font(FontFamily.HELVETICA.getFamily(), 15, Font.BOLD),
                new Font(FontFamily.HELVETICA.getFamily(), 11, Font.NORMAL),
                ColorScheme.GREEN,
                1.6f,
                45f, 45f, 45f, 45f
            )
        );

        // Minimalist Style
        STYLE_CONFIGS.put(ResumeTemplate.TemplateStyle.MINIMALIST,
            new StyleConfig(
                new Font(FontFamily.HELVETICA.getFamily(), 22, Font.BOLD),
                new Font(FontFamily.HELVETICA.getFamily(), 13, Font.BOLD),
                new Font(FontFamily.HELVETICA.getFamily(), 11, Font.NORMAL),
                ColorScheme.GRAY,
                1.4f,
                55f, 55f, 55f, 55f
            )
        );
    }

    public static StyleConfig getStyleConfig(ResumeTemplate.TemplateStyle style) {
        return STYLE_CONFIGS.get(style);
    }

    public static String getTemplateContent(ResumeTemplate.TemplateStyle style) {
        switch (style) {
            case PROFESSIONAL:
                return "PROFESSIONAL SUMMARY\n\n" +
                       "EDUCATION\n\n" +
                       "WORK EXPERIENCE\n\n" +
                       "SKILLS\n\n" +
                       "CERTIFICATIONS\n\n" +
                       "CONTACT INFORMATION";
            
            case CREATIVE:
                return "ABOUT ME\n\n" +
                       "EDUCATION & TRAINING\n\n" +
                       "PROFESSIONAL EXPERIENCE\n\n" +
                       "CORE COMPETENCIES\n\n" +
                       "ACHIEVEMENTS\n\n" +
                       "CONTACT DETAILS";
            
            case MODERN:
                return "PROFILE\n\n" +
                       "EDUCATION\n\n" +
                       "EXPERIENCE\n\n" +
                       "TECHNICAL SKILLS\n\n" +
                       "PROJECTS\n\n" +
                       "CONTACT";
            
            case MINIMALIST:
                return "SUMMARY\n\n" +
                       "EDUCATION\n\n" +
                       "EXPERIENCE\n\n" +
                       "SKILLS\n\n" +
                       "CONTACT";
            
            default:
                return "";
        }
    }
} 
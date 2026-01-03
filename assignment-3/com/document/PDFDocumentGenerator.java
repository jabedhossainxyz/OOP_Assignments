package com.document;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PDFDocumentGenerator {
    public static void generatePDF(Document document, String outputPath) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("Title: " + safe(document.getTitle()));
        lines.add("Author: " + safe(document.getAuthor()));
        lines.add("Student ID: " + safe(document.getStudentId()));
        lines.add("");

        for (DocumentElement element : document.getElements()) {
            String prefix = element.getType();
            String content = safe(element.getContent());
            lines.add(prefix + ": " + content);
        }

        byte[] contentStream = buildContentStream(lines);

        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        List<Integer> xrefOffsets = new ArrayList<>();

        writeAscii(pdf, "%PDF-1.4\n");
        writeAscii(pdf, "%\u00E2\u00E3\u00CF\u00D3\n");

        xrefOffsets.add(0);

        // 1 0 obj: Catalog
        xrefOffsets.add(pdf.size());
        writeAscii(pdf,
                "1 0 obj\n" +
                "<< /Type /Catalog /Pages 2 0 R >>\n" +
                "endobj\n");

        // 2 0 obj: Pages
        xrefOffsets.add(pdf.size());
        writeAscii(pdf,
                "2 0 obj\n" +
                "<< /Type /Pages /Kids [3 0 R] /Count 1 >>\n" +
                "endobj\n");

        // 3 0 obj: Page
        xrefOffsets.add(pdf.size());
        writeAscii(pdf,
                "3 0 obj\n" +
                "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] " +
                "/Resources << /Font << /F1 4 0 R >> >> " +
                "/Contents 5 0 R >>\n" +
                "endobj\n");

        // 4 0 obj: Font
        xrefOffsets.add(pdf.size());
        writeAscii(pdf,
                "4 0 obj\n" +
                "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\n" +
                "endobj\n");

        // 5 0 obj: Contents stream
        xrefOffsets.add(pdf.size());
        writeAscii(pdf, "5 0 obj\n");
        writeAscii(pdf, "<< /Length " + contentStream.length + " >>\n");
        writeAscii(pdf, "stream\n");
        pdf.write(contentStream);
        writeAscii(pdf, "\nendstream\nendobj\n");

        int xrefStart = pdf.size();
        writeAscii(pdf, "xref\n");
        writeAscii(pdf, "0 " + xrefOffsets.size() + "\n");
        writeAscii(pdf, String.format("%010d 65535 f \n", 0));

        for (int i = 1; i < xrefOffsets.size(); i++) {
            writeAscii(pdf, String.format("%010d 00000 n \n", xrefOffsets.get(i)));
        }

        writeAscii(pdf,
                "trailer\n" +
                "<< /Size " + xrefOffsets.size() + " /Root 1 0 R >>\n" +
                "startxref\n" +
                xrefStart + "\n" +
                "%%EOF\n");

        try (FileOutputStream out = new FileOutputStream(outputPath)) {
            pdf.writeTo(out);
        }
    }

    private static void writeAscii(ByteArrayOutputStream out, String s) throws IOException {
        out.write(s.getBytes(StandardCharsets.ISO_8859_1));
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

    private static String escapePdfString(String s) {
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' || c == '(' || c == ')') {
                sb.append('\\');
            }
            if (c == '\r') {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static byte[] buildContentStream(List<String> lines) {
        // Simple page text: Helvetica 12pt, start at (50, 800), line spacing 14.
        StringBuilder sb = new StringBuilder();
        sb.append("BT\n");
        sb.append("/F1 12 Tf\n");
        sb.append("14 TL\n");
        sb.append("50 800 Td\n");

        int maxLines = 55;
        int count = 0;
        for (String raw : lines) {
            if (count >= maxLines) break;
            for (String part : wrapLine(raw, 95)) {
                if (count >= maxLines) break;
                sb.append('(').append(escapePdfString(part)).append(") Tj\n");
                sb.append("T*\n");
                count++;
            }
        }
        sb.append("ET");
        return sb.toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    private static List<String> wrapLine(String line, int maxLen) {
        List<String> parts = new ArrayList<>();
        if (line == null) {
            parts.add("");
            return parts;
        }
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            parts.add("");
            return parts;
        }
        String[] words = trimmed.split("\\s+");
        StringBuilder current = new StringBuilder();
        for (String w : words) {
            if (current.length() == 0) {
                current.append(w);
            } else if (current.length() + 1 + w.length() <= maxLen) {
                current.append(' ').append(w);
            } else {
                parts.add(current.toString());
                current.setLength(0);
                current.append(w);
            }
        }
        if (current.length() > 0) {
            parts.add(current.toString());
        }
        return parts;
    }
}

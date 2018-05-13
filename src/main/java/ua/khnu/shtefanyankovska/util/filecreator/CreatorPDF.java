package ua.khnu.shtefanyankovska.util.filecreator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ua.khnu.shtefanyankovska.entity.Result;
import ua.khnu.shtefanyankovska.util.MyException;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CreatorPDF implements Creator {

    @Override
    public File create(List<Result> results, File f) throws MyException {
        try {
            Document document = new Document(PageSize.A4, 5, 5, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(f));

            Font norm = FontFactory.getFont("C:\\Windows\\Fonts\\Arial.ttf", "ISO-8859-5", 11, Font.NORMAL);
            Font bold = FontFactory.getFont("C:\\Windows\\Fonts\\Arial.ttf", "ISO-8859-5", 11, Font.BOLD);
            document.open();

            PdfPTable table = new PdfPTable(3);
            table.setSpacingBefore(25);
            table.setSpacingAfter(25);

            PdfPCell title = new PdfPCell(new Phrase("Название теста", bold));
            table.addCell(title);

            PdfPCell result = new PdfPCell(new Phrase("Результат", bold));
            table.addCell(result);

            PdfPCell date = new PdfPCell(new Phrase("Дата", bold));
            table.addCell(date);

            for (Result r : results) {
                table.addCell(new Paragraph(r.getTestTitle(), norm));
                table.addCell(new Paragraph(String.valueOf(r.getScore()), norm));
                table.addCell(new Paragraph(r.getDate(), norm));
            }

            document.add(table);
            document.close();
            writer.close();
        } catch (Exception e) {
            throw new MyException("Error while writing pdf file.", e);
        }

        return f;
    }
}

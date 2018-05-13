package ua.khnu.shtefanyankovska.util.filecreator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ua.khnu.shtefanyankovska.entity.Result;
import ua.khnu.shtefanyankovska.util.MyException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CreatorXLSX implements Creator {

    @Override
    public File create(List<Result> results, File f) throws MyException {
        Workbook wbook = new HSSFWorkbook();
        Sheet sheet = wbook.createSheet("Results");

        Row row = sheet.createRow(0);
        Cell name = row.createCell(0);
        name.setCellType(CellStyle.ALIGN_LEFT);
        name.setCellValue("НАЗВАНИЕ ТЕСТА");
        Cell score = row.createCell(1);
        score.setCellType(CellStyle.ALIGN_LEFT);
        score.setCellValue("БАЛЛЫ");
        Cell date = row.createCell(2);
        date.setCellType(CellStyle.ALIGN_LEFT);
        date.setCellValue("ДАТА");

        row = sheet.createRow(1);
        name = row.createCell(0);
        name.setCellValue("");
        score = row.createCell(1);
        score.setCellValue("");
        date = row.createCell(2);
        date.setCellValue("");

        for (int i = 0; i < results.size(); i++) {
            row = sheet.createRow(i + 2);
            name = row.createCell(0);
            name.setCellType(CellStyle.ALIGN_LEFT);
            name.setCellValue(results.get(i).getTestTitle());
            score = row.createCell(1);
            score.setCellType(CellStyle.ALIGN_LEFT);
            score.setCellValue(results.get(i).getScore());
            date = row.createCell(2);
            date.setCellValue(results.get(i).getDate());
            date.setCellType(CellStyle.ALIGN_LEFT);
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
        }

        try {
            wbook.write(new FileOutputStream(f));
        } catch (IOException e) {
            throw new MyException("Error while writing XLS file", e);
        }

        return f;
    }

}

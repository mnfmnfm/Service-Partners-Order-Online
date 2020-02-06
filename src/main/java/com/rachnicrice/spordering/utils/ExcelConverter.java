package com.rachnicrice.spordering.utils;

import com.rachnicrice.spordering.models.LineItem;
import com.rachnicrice.spordering.models.Order;
import com.rachnicrice.spordering.models.OrderRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ByteArrayResource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//Resource: https://www.codejava.net/coding/how-to-write-excel-files-in-java-using-apache-poi
//Resource: https://stackoverflow.com/questions/52078128/spring-boot-controller-export-an-excel
public class ExcelConverter {


    public static ByteArrayResource export(String filename, Long id, OrderRepository repo) throws IOException {
        byte[] bytes = new byte[1024];
        Order order = repo.getOne(id);
        order.setSubmitted(true);
        repo.save(order);


        try (Workbook workbook = (Workbook) generateExcel(id, repo)) {
            FileOutputStream fos = write(workbook, filename);
            fos.write(bytes);
            fos.flush();
            fos.close();
        }

        return new ByteArrayResource(bytes);
    }

    //Change from AutoCloseable to Workbook if this does not work
    //Had to change to AutoCloseable due to version changes from Apache 3.9 to 4.0
    public static AutoCloseable generateExcel(Long id, OrderRepository repo) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Order");

        //create columns and rows
        List<LineItem> lineItems = repo.getOne(id).getItemsInThisOrder();

        int rowCount = 0;

        for (LineItem item : lineItems) {
            Row row = sheet.createRow(++rowCount);
            Cell itemCode = row.createCell(1);
            Cell quantity = row.createCell(2);

            itemCode.setCellValue(item.getProduct().getItemCode());
            quantity.setCellValue(item.getQuantity());

        }

        return (AutoCloseable) workbook;
    }

    private static FileOutputStream write(final Workbook workbook, final String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.close();
        return fos;
    }

}

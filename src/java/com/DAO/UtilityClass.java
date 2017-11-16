package com.DAO;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import java.io.FileInputStream;

public class UtilityClass {

    private static final short c1 = 0;
    private static final short c2 = 1;
    private static final short c3 = 2;
    private static final short c4 = 3;
    private static final short c5 = 4;

    private static String materialNumber;
    private static String vendorBatchNumber;
    private static String craftGroup;
    private static String storageLocation;

    public static void main(String... args) {
        DaoClass daoClass = new DaoClass();
        String vendorListFilePath = "C:\\Users\\pranesh\\AppData\\Roaming\\Skype\\My Skype Received Files\\SB-VENDOR-CRFGRP.xls";
        //String vendorListFilePath = "C:\\Users\\pranesh\\Desktop\\VendorCraft.xls";
        try {
            if (!(vendorListFilePath.equals(null) || vendorListFilePath.equals(""))) {
                FileInputStream fileInputStream = new FileInputStream(vendorListFilePath);
                HSSFWorkbook fWorkbook = new HSSFWorkbook(fileInputStream);

                HSSFSheet fSheet = fWorkbook.getSheetAt(0);
                int rows = fSheet.getPhysicalNumberOfRows();
                System.out.println("Rows :  " + rows);
                for (int noRows = 1; noRows < rows; noRows++) {
                    HSSFRow fRow = fSheet.getRow(noRows);
                    HSSFCell hSSFCell0 = fRow.getCell(c1);
                    hSSFCell0.setCellType(HSSFCell.CELL_TYPE_STRING);
                    materialNumber = hSSFCell0.getStringCellValue();
                    //System.out.println("materialNumber : " + materialNumber);

                    HSSFCell hSSFCell1 = fRow.getCell(c2);
                    hSSFCell1.setCellType(HSSFCell.CELL_TYPE_STRING);
                    vendorBatchNumber = hSSFCell1.getStringCellValue();
                    //System.out.println("vendorBatchNumber : " + vendorBatchNumber);

                    HSSFCell hSSFCell2 = fRow.getCell(c3);
                    hSSFCell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                    craftGroup = hSSFCell2.getStringCellValue();
                    //System.out.println("craftGroup : " + craftGroup);

                    HSSFCell hSSFCell3 = fRow.getCell(c4);
                    hSSFCell3.setCellType(HSSFCell.CELL_TYPE_STRING);
                    storageLocation = hSSFCell3.getStringCellValue();
                    //System.out.println("storageLocation : " + storageLocation);

                    String updateVendorCrft = "insert into pos.material_vendor_craft (material_no,vendor_id,craftgroup,storage_location) values('" + materialNumber + "','" + vendorBatchNumber + "','" + craftGroup + "','" + storageLocation + "')";
                    daoClass.Fun_Updat(updateVendorCrft);

                }
            }
        } catch (Exception ex) {
            System.err.println("Excetion in Inserting Vendor Details :  " + ex);
        }
    }

}

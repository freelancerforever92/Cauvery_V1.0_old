/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

/**
 *
 * @author pranesh
 */
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import java.io.FileInputStream;
import java.io.IOException;

public class NewMaterialTableUpload {

    private static final short c1 = 0;
    private static final short c2 = 1;
    private static final short c3 = 2;
    private static final short c4 = 3;

    private static String materialNumber;
    private static String vendorNumber;
    private static String craftGroup;
    private static String storageLoaction;

    public static void main(String... args) {
        DaoClass daoClass = new DaoClass();
        String materialFilePath = "C:\\Users\\pranesh\\Desktop\\MaterialVendorCraftGroup.xls";
        //String vendorListFilePath = "C:\\Users\\pranesh\\Desktop\\VendorCraft.xls";
        try {
            if (!(materialFilePath.equals(""))) {
                FileInputStream fileInputStream = new FileInputStream(materialFilePath);
                HSSFWorkbook fWorkbook = new HSSFWorkbook(fileInputStream);
                HSSFSheet fSheet = fWorkbook.getSheetAt(0);
                int rows = fSheet.getPhysicalNumberOfRows();
                //int rows = 10;
                for (int noRows = 1; noRows < rows; noRows++) {

                    HSSFRow fRow = fSheet.getRow(noRows);
                    HSSFCell hSSFCell0 = fRow.getCell(c1);
                    hSSFCell0.setCellType(HSSFCell.CELL_TYPE_STRING);
                    materialNumber = hSSFCell0.getStringCellValue();
                    //System.out.println("materialNumber : " + materialNumber);

                    HSSFCell hSSFCell1 = fRow.getCell(c2);
                    hSSFCell1.setCellType(HSSFCell.CELL_TYPE_STRING);
                    vendorNumber = hSSFCell1.getStringCellValue();
                    //System.out.println("vendorNumber : " + vendorNumber);

                    HSSFCell hSSFCell2 = fRow.getCell(c3);
                    hSSFCell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                    craftGroup = hSSFCell2.getStringCellValue();
                    //System.out.println("craftGroup : " + craftGroup);

                    HSSFCell hSSFCell3 = fRow.getCell(c4);
                    hSSFCell3.setCellType(HSSFCell.CELL_TYPE_STRING);
                    storageLoaction = hSSFCell3.getStringCellValue();
                    //System.out.println("storageLoaction : " + storageLoaction);

                    String isMaterialExits = "SELECT count(*) FROM pos.material_master_taxgroup where material_no='" + materialNumber + "'";
                    if (daoClass.Fun_Int(isMaterialExits) >= 1) {
                        String isVendorExits = "SELECT count(*) FROM pos.vendor_master where vendor_id='" + vendorNumber + "'";
                        if (daoClass.Fun_Int(isVendorExits) >= 1) {
                            String checkPoint = "select count(*) FROM pos.material_vendor_craft where material_no='" + materialNumber + "' and vendor_id='" + vendorNumber + "' and craftgroup='" + craftGroup + "' and storage_location='" + storageLoaction + "'";
                            if (daoClass.Fun_Int(checkPoint) <= 0) {
                                String qry = "INSERT INTO `pos`.`material_vendor_craft` (material_no,vendor_id,craftgroup,storage_location) VALUES ('" + materialNumber.trim() + "', '" + vendorNumber.trim() + "', '" + craftGroup.trim() + "','" + storageLoaction.trim() + "')";
                                int successfull = daoClass.Fun_Updat(qry);
                                if (successfull > 0) {
                                    System.out.println("SUCCESS :: " + materialNumber);
                                } else if (successfull <= 0) {
                                    System.out.println("FAILURE :: " + materialNumber);
                                }
                            }
                        }
                    }

                }
            }
        } catch (IOException ex) {
            System.out.println("" + ex.getMessage());
        }
    }
}

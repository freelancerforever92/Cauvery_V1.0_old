package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.superpojo.ResultMeterialUploading;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;

public class MeterialUploading extends ActionSupport implements
        ServletRequestAware {

    private static short c1 = 0;
    private static short c2 = 1;
    private static short c3 = 2;
    private static short c4 = 3;
    private static short c5 = 4;
    private static short c6 = 5;
    private static short c7 = 6;
    private static short c8 = 7;
    private static short c9 = 8;
    private static String mNo;
    private static String oldCode;
    private static String itemDescription;
    private static String craft;
    private static String plant;
    private static String storageLocation;
    private static String distributionChannel;
    private static Float price = 0.0f;
    private static Float standardPrice = 0.0f;
    private static String vendorId;
    Connection connection = null;

    int existMatCount = 0;
    String returnString;
    int fileTypeVal = 0;

    private File userImage;
    private String userImageContentType;
    private String userImageFileName;
    private String uploadingFileNamepath;
    List<ResultMeterialUploading> meterialUploadingAL;
    List<ResultMeterialUploading> meterialVenUploadingAL;

    private HttpServletRequest servletRequest;

    public MeterialUploading() {
        this.meterialUploadingAL = new ArrayList<ResultMeterialUploading>();
        meterialVenUploadingAL = new ArrayList<ResultMeterialUploading>();
    }

    public String Uploading() throws IOException {
        DaoClass daoClass = new DaoClass();
        int rows = 0;

        String filePath = servletRequest.getSession().getServletContext().getRealPath("/").concat("Materials File");
        String uploadfileName = this.userImageFileName;
        String uploadfiletype = this.userImageContentType;

        File fileToCreate = new File(filePath, uploadfileName);
        FileUtils.copyFile(this.userImage, fileToCreate);

        uploadingFileNamepath = filePath + "/" + uploadfileName;

        if (uploadfiletype.equalsIgnoreCase("application/vnd.ms-excel")) {
            fileTypeVal = 1;
            try {
                connection = daoClass.Fun_DbCon();
                connection.setAutoCommit(false);
                if (!(uploadingFileNamepath.equals(""))) {
                    FileInputStream fs = new FileInputStream(uploadingFileNamepath);
                    HSSFWorkbook wb = new HSSFWorkbook(fs);

                    for (int noSheets = 0; noSheets < wb.getNumberOfSheets(); noSheets++) {
                        HSSFSheet sheet = wb.getSheetAt(noSheets);
                        rows = sheet.getPhysicalNumberOfRows();
                        if (rows != 0) {
                            for (int noRows = 1; noRows < rows; noRows++) {
                                HSSFRow fRow = sheet.getRow(noRows);
                                HSSFCell hSSFCell0 = fRow.getCell(c1);
                                hSSFCell0.setCellType(hSSFCell0.CELL_TYPE_STRING);
                                mNo = hSSFCell0.getStringCellValue();

                                HSSFCell hSSFCell2 = fRow.getCell(c2);
                                hSSFCell2.setCellType(hSSFCell2.CELL_TYPE_STRING);
                                itemDescription = hSSFCell2.getStringCellValue();
                                itemDescription = itemDescription.replaceAll("[,;+-]", "");
                                itemDescription = itemDescription.replace('"', ' ');
                                itemDescription = itemDescription.replace("'", "");

                                HSSFCell hSSFCell3 = fRow.getCell(c3);
                                hSSFCell3.setCellType(hSSFCell3.CELL_TYPE_STRING);
                                craft = hSSFCell3.getStringCellValue();

                                HSSFCell hSSFCell4 = fRow.getCell(c4);
                                hSSFCell4.setCellType(hSSFCell4.CELL_TYPE_STRING);
                                plant = hSSFCell4.getStringCellValue();

                                HSSFCell hSSFCell5 = fRow.getCell(c5);
                                hSSFCell5.setCellType(hSSFCell5.CELL_TYPE_STRING);
                                storageLocation = hSSFCell5.getStringCellValue();

                                HSSFCell hSSFCell6 = fRow.getCell(c6);
                                hSSFCell6.setCellType(hSSFCell6.CELL_TYPE_STRING);
                                distributionChannel = hSSFCell6.getStringCellValue();

                                HSSFCell hSSFCell7 = fRow.getCell(c7);
                                hSSFCell7.setCellType(hSSFCell7.CELL_TYPE_STRING);
                                price = Float.valueOf(hSSFCell7.getStringCellValue());

                                HSSFCell hSSFCell8 = fRow.getCell(c8);
                                hSSFCell8.setCellType(hSSFCell8.CELL_TYPE_STRING);
                                standardPrice = Float.valueOf(hSSFCell8.getStringCellValue());

                                HSSFCell hSSFCell9 = fRow.getCell(c9);
                                hSSFCell9.setCellType(hSSFCell9.CELL_TYPE_STRING);
                                vendorId = hSSFCell9.getStringCellValue();
                                int count = 0;
                                String metCountQry = "select count(*) from material_master_taxgroup where material_no='" + mNo + "'";
                                count = daoClass.Fun_Int(metCountQry);
                                if (!vendorId.equals("")) {
                                    if (count <= 0) {
                                        String metUploadQry = "INSERT INTO `pos`.`material_master_taxgroup` "
                                                + "(`material_no`, `material_desc`, `craft_group`, `plant`, "
                                                + "`storageLocation`, `distributionChannel`, `price`, `standardPrice`,`createdDateTime`,`lastUpdatedDateTime`)"
                                                + " VALUES ('" + mNo.trim() + "', '" + itemDescription.trim() + "', "
                                                + "'" + craft.trim() + "', '" + plant.trim() + "', '" + storageLocation.trim() + "', "
                                                + "'" + distributionChannel.trim() + "', '" + price + "', "
                                                + "'" + standardPrice + "',now(),'0001-01-01 01:01:01')";//                                
                                        int metUploadVal = daoClass.Fun_Updat(metUploadQry);//                                 
                                        if (metUploadVal == 1) {
                                            meterialUploadingAL.add(new ResultMeterialUploading(mNo, itemDescription.trim(), "Success"));
                                        } else {
                                            meterialUploadingAL.add(new ResultMeterialUploading(mNo, itemDescription.trim(), "Fail"));
                                        }
                                    } else {
                                        existMatCount++;
                                        meterialUploadingAL.add(new ResultMeterialUploading(mNo, itemDescription.trim(), "Duplicate"));
                                    }
                                    int metVenCountVal = 0;
                                    String metVenCountQry = "select count(*) from material_vendor_craft where material_no='" + mNo.trim() + "' AND vendor_id='" + vendorId.trim() + "' AND craftgroup='" + craft.trim() + "' AND storage_location='" + storageLocation.trim() + "'";
                                    metVenCountVal = daoClass.Fun_Int(metVenCountQry);
                                    if (metVenCountVal <= 0) {
                                        String metVenUploadQry = "INSERT INTO `pos`.`material_vendor_craft` "
                                                + "(`material_no`, `vendor_id`, `craftgroup`,`storage_location`) "
                                                + "VALUES ('" + mNo.trim() + "', '" + vendorId.trim() + "', '" + craft.trim() + "','" + storageLocation.trim() + "')";
                                        int metVenUploadVal = daoClass.Fun_Updat(metVenUploadQry);
                                        meterialVenUploadingAL.add(new ResultMeterialUploading(mNo, vendorId, craft, "SUCCESS"));
                                    } else {
                                        try {
                                            meterialVenUploadingAL.add(new ResultMeterialUploading(mNo, vendorId, craft, "FAIL"));
                                        } catch (Exception e) {
                                            System.out.println("Error  : " + e.getMessage());
                                        }
                                    }
                                } else {
                                    meterialVenUploadingAL.add(new ResultMeterialUploading(mNo, vendorId, craft, "FAIL"));
                                }
                            }
                        }
                    }
                    connection.commit();
                } else {
                    System.out.println("Not exist");
                }
            } catch (Exception ex) {

                try {
//                meterialUploadingAL.add(new ResultMeterialUploading(mNo, itemDescription.trim(), "Fail"));
                    System.out.println("Exception in Reading Excel File :  " + ex);

//                connection.rollback();
                } catch (Exception rollbackEx) {
                    System.out.println("" + rollbackEx.getMessage());
                }

            }
        } else {
            fileTypeVal = 0;
        }

        returnString = "success";

        return returnString;

    }

    public List<ResultMeterialUploading> getMeterialUploadingAL() {
        return meterialUploadingAL;
    }

    public void setMeterialUploadingAL(List<ResultMeterialUploading> meterialUploadingAL) {
        this.meterialUploadingAL = meterialUploadingAL;
    }

    public File getUserImage() {
        return userImage;
    }

    public void setUserImage(File userImage) {
        this.userImage = userImage;
    }

    public String getUserImageContentType() {
        return userImageContentType;
    }

    public void setUserImageContentType(String userImageContentType) {
        this.userImageContentType = userImageContentType;
    }

    public String getUserImageFileName() {
        return userImageFileName;
    }

    public void setUserImageFileName(String userImageFileName) {
        this.userImageFileName = userImageFileName;
    }

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;

    }

    public int getFileTypeVal() {
        return fileTypeVal;
    }

    public void setFileTypeVal(int fileTypeVal) {
        this.fileTypeVal = fileTypeVal;
    }

    public List<ResultMeterialUploading> getMeterialVenUploadingAL() {
        return meterialVenUploadingAL;
    }

    public void setMeterialVenUploadingAL(List<ResultMeterialUploading> meterialVenUploadingAL) {
        this.meterialVenUploadingAL = meterialVenUploadingAL;
    }

}

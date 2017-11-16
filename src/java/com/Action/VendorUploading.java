package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.superpojo.ResultVendorUploading;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;

public class VendorUploading extends ActionSupport implements
        ServletRequestAware {

    private static final short c1 = 0;
    private static final short c2 = 1;
    private static final short c3 = 2;
    private static final short c4 = 3;
    private static final short c5 = 4;
    private static final short c6 = 5;
    private static final short c7 = 6;
    private static final short c8 = 7;
    private static final short c9 = 8;
    private static final short c10 = 9;
    private static final short c11 = 10;
    private static final short c12 = 11;
    private static final short c13 = 12;
    private static final short c14 = 13;
    private static final short c15 = 14;
    private static final short c16 = 15;
    private static final short c17 = 16;
    private static final short c18 = 17;
    private static final short c19 = 18;
    private static final short c20 = 19;

    private static String vendorCode;
    private static String sapVenCode;
    private static String title;
    private static String vendorName1;
    private static String vendorName2;
    private static String vendorName3;
    private static String vendorName4;
    private static String searchterm;
    private static String address1;
    private static String address2;
    private static String address3;
    private static String address4;
    private static String city;
    private static String pincode;
    private static String district;
    private static String state;
    private static String teleNo;
    private static String mobileNo;
    private static String faxNo;
    private static String emailId;

    String returnString;
    int count = 0;
    int existVendorCount = 0;
    int fileTypeVal = 0;
    List<ResultVendorUploading> vendorUploadingAL = new ArrayList<ResultVendorUploading>();

    private File userImage;
    private String userImageContentType;
    private String userImageFileName;
    private String vendoruploadingFileNamepath;
    private HttpServletRequest servletRequest;

    public String uploading_v() throws IOException {

        DaoClass daoClass = new DaoClass();

        String filePath = servletRequest.getSession().getServletContext().getRealPath("/").concat("Vendor File");
        String uploadfileName = this.userImageFileName;
        String uploadfiletype = this.userImageContentType;
        File fileToCreate = new File(filePath, uploadfileName);
        FileUtils.copyFile(this.userImage, fileToCreate);
        vendoruploadingFileNamepath = filePath + "/" + uploadfileName;

        if (uploadfiletype.equalsIgnoreCase("application/vnd.ms-excel")) {
            fileTypeVal = 1;
            try {
                if (!(vendoruploadingFileNamepath.equals(null) || vendoruploadingFileNamepath.equals(""))) {
                    FileInputStream fileInputStream = new FileInputStream(vendoruploadingFileNamepath);
                    HSSFWorkbook fWorkbook = new HSSFWorkbook(fileInputStream);

                    HSSFSheet fSheet = fWorkbook.getSheetAt(0);
                    int rows = fSheet.getPhysicalNumberOfRows();
                    System.out.println("Rows :  " + rows);
                    for (int noRows = 1; noRows < rows; noRows++) {
                        HSSFRow fRow = fSheet.getRow(noRows);
                        HSSFCell hSSFCell0 = fRow.getCell(c1);
                        hSSFCell0.setCellType(HSSFCell.CELL_TYPE_STRING);
                        vendorCode = hSSFCell0.getStringCellValue();
//                        System.out.println("vendorCode  : " + vendorCode);

                        HSSFCell hSSFCell1 = fRow.getCell(c2);
                        hSSFCell1.setCellType(HSSFCell.CELL_TYPE_STRING);
                        sapVenCode = hSSFCell1.getStringCellValue();
                        //System.out.println("sapVenCode : " + sapVenCode);

                        HSSFCell hSSFCell2 = fRow.getCell(c3);
                        hSSFCell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                        title = hSSFCell2.getStringCellValue();
                        //System.out.println("title : " + title);

                        HSSFCell hSSFCell3 = fRow.getCell(c4);
                        hSSFCell3.setCellType(HSSFCell.CELL_TYPE_STRING);
                        vendorName1 = hSSFCell3.getStringCellValue();
                        //System.out.println("vendorName1 : " + vendorName1);

                        HSSFCell hSSFCell4 = fRow.getCell(c5);
                        hSSFCell4.setCellType(HSSFCell.CELL_TYPE_STRING);
                        vendorName2 = hSSFCell4.getStringCellValue();
                        //System.out.println("vendorName2 : " + vendorName2);

                        HSSFCell hSSFCell5 = fRow.getCell(c6);
                        hSSFCell5.setCellType(HSSFCell.CELL_TYPE_STRING);
                        vendorName3 = hSSFCell5.getStringCellValue();
                        //System.out.println("vendorName3 : " + vendorName3);

                        HSSFCell hSSFCell6 = fRow.getCell(c7);
                        hSSFCell6.setCellType(HSSFCell.CELL_TYPE_STRING);
                        vendorName4 = hSSFCell6.getStringCellValue();
                        //System.out.println("vendorName4 : " + vendorName4);

                        HSSFCell hSSFCell7 = fRow.getCell(c8);
                        hSSFCell7.setCellType(HSSFCell.CELL_TYPE_STRING);
                        searchterm = hSSFCell7.getStringCellValue();
                        //System.out.println("searchterm : " + searchterm);

                        HSSFCell hSSFCell8 = fRow.getCell(c9);
                        hSSFCell8.setCellType(HSSFCell.CELL_TYPE_STRING);
                        address1 = hSSFCell8.getStringCellValue();
                        //System.out.println("address1 : " + address1);

                        HSSFCell hSSFCell9 = fRow.getCell(c10);
                        hSSFCell9.setCellType(HSSFCell.CELL_TYPE_STRING);
                        address2 = hSSFCell9.getStringCellValue();
                        //System.out.println("address2 : " + address2);

                        HSSFCell hSSFCell10 = fRow.getCell(c11);
                        hSSFCell10.setCellType(HSSFCell.CELL_TYPE_STRING);
                        address3 = hSSFCell10.getStringCellValue();
                        //System.out.println("address3 : " + address3);

                        HSSFCell hSSFCell11 = fRow.getCell(c12);
                        hSSFCell11.setCellType(HSSFCell.CELL_TYPE_STRING);
                        address4 = hSSFCell11.getStringCellValue();
                        //System.out.println("address4 : " + address4);

                        HSSFCell hSSFCell12 = fRow.getCell(c13);
                        hSSFCell12.setCellType(HSSFCell.CELL_TYPE_STRING);
                        city = hSSFCell12.getStringCellValue();
                        //System.out.println("city : " + city);

                        HSSFCell hSSFCell13 = fRow.getCell(c14);
                        hSSFCell13.setCellType(HSSFCell.CELL_TYPE_STRING);
                        pincode = hSSFCell13.getStringCellValue();
                        //System.out.println("pincode : " + pincode);

                        HSSFCell hSSFCell14 = fRow.getCell(c15);
                        hSSFCell14.setCellType(HSSFCell.CELL_TYPE_STRING);
                        district = hSSFCell14.getStringCellValue();
                        //System.out.println("district : " + district);

                        HSSFCell hSSFCell15 = fRow.getCell(c16);
                        hSSFCell15.setCellType(HSSFCell.CELL_TYPE_STRING);
                        state = hSSFCell15.getStringCellValue();
                        //System.out.println("state : " + state);

                        HSSFCell hSSFCell16 = fRow.getCell(c17);
                        hSSFCell16.setCellType(HSSFCell.CELL_TYPE_STRING);
                        teleNo = hSSFCell16.getStringCellValue();
                        //System.out.println("teleNo : " + teleNo);

                        HSSFCell hSSFCell17 = fRow.getCell(c18);
                        hSSFCell17.setCellType(HSSFCell.CELL_TYPE_STRING);
                        mobileNo = hSSFCell17.getStringCellValue();
                        //System.out.println("mobileNo : " + mobileNo);

                        HSSFCell hSSFCell18 = fRow.getCell(c19);
                        hSSFCell18.setCellType(HSSFCell.CELL_TYPE_STRING);
                        faxNo = hSSFCell18.getStringCellValue();
                        //System.out.println("faxNo : " + faxNo);

                        HSSFCell hSSFCell19 = fRow.getCell(c20);
                        hSSFCell19.setCellType(HSSFCell.CELL_TYPE_STRING);
                        emailId = hSSFCell19.getStringCellValue();
//                        System.out.println("emailId : " + emailId);

                        String getCountQry = "select count(*) from pos.vendor_master where vendor_id='" + vendorCode + "'";
                        count = daoClass.Fun_Int(getCountQry);

                        if (count <= 0) {

                            vendorUploadingAL.add(new ResultVendorUploading(vendorCode, vendorName1.trim(), "Success"));
                            String qry = "insert into vendor_master"
                                    + "(vendor_id,account_group,title,vendor_name,vendor_name2,vendor_name3,"
                                    + "vendor_name4,search_term,address1,address2,address3,address4,"
                                    + "city,pincode,district,state,tel_no,mobile_no,fax_no,email_id,created_date_time,updated_date_time)"
                                    + "values('" + vendorCode.trim() + "','" + sapVenCode.trim() + "','"
                                    + title.trim() + "','" + vendorName1.trim() + "','" + vendorName2.trim()
                                    + "','" + vendorName3.trim() + "','" + vendorName4.trim() + "','"
                                    + searchterm.trim() + "','" + address1.trim() + "','" + address2.trim() + "','"
                                    + address3.trim() + "','" + address4.trim() + "','" + city.trim() + "','"
                                    + pincode.trim() + "','" + district.trim() + "','" + state.trim() + "','"
                                    + teleNo.trim() + "','" + mobileNo.trim() + "','" + faxNo.trim() + "','"
                                    + emailId.trim() + "',now(),'0001-01-01 01:01:01')";
                            daoClass.updatingRecord(qry);
                        } else {
//                            System.out.println("Fail");
                            existVendorCount++;
                            vendorUploadingAL.add(new ResultVendorUploading(vendorCode, vendorName1.trim(), "Duplicate"));
                        }
                        System.out.println("List" + vendorUploadingAL);

                    }
                }
            } catch (Exception ex) {
//            vendorUploadingAL.add(new ResultVendorUploading(vendorCode, vendorName1.trim(), "Fail"));
                System.err.println("Excetion in Inserting Vendor Details :  " + ex);
            }

        } else {
            fileTypeVal = 0;
        }

        returnString = "success";
        return returnString;
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

    public List<ResultVendorUploading> getVendorUploadingAL() {
        return vendorUploadingAL;
    }

    public void setVendorUploadingAL(List<ResultVendorUploading> vendorUploadingAL) {
        this.vendorUploadingAL = vendorUploadingAL;
    }

    public int getFileTypeVal() {
        return fileTypeVal;
    }

    public void setFileTypeVal(int fileTypeVal) {
        this.fileTypeVal = fileTypeVal;
    }

}

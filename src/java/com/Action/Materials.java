/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;

import com.superpojo.MaterialDetailPojo;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JAVA
 */
public class Materials extends ActionSupport {

    String returnString;
    List matList = new ArrayList();
    DaoClass daoClass = new DaoClass();
    ResultSet rs = null;
    ResultSet crafrtGrprs = null;
    private String matId;
    private String matDesc;
    private String craftGroup;
    private String plantNo;
    private String storageLoc;
    private int distChanelNo;
    private float price;
     private float standardPrice;
    int isMatExt = 0;
    List<String> craftGroupList = new ArrayList<String>();
    MaterialDetailPojo matdetailPojo = new MaterialDetailPojo();

    public String materialsList() {
        try {

            String matDetailsQuery = "SELECT distributionChannel,material_no,material_desc,craft_group,plant,storageLocation,price,standardPrice,DATE_FORMAT(material_master_taxgroup.createdDateTime,'%d-%m-%Y %h:%m:%s')as createdDateTime,DATE_FORMAT(material_master_taxgroup.lastUpdatedDateTime,'%d-%m-%Y %h:%m:%s')as lastUpdatedDateTime FROM pos.material_master_taxgroup";
            rs = daoClass.Fun_Resultset(matDetailsQuery);
            while (rs.next()) {
                matdetailPojo = new MaterialDetailPojo(rs.getInt("distributionChannel"), rs.getString("material_no"), rs.getString("material_desc"), rs.getString("craft_group"), rs.getString("plant"), rs.getString("storageLocation"), rs.getFloat("price"),rs.getFloat("standardPrice"), rs.getString("createdDateTime"), rs.getString("lastUpdatedDateTime"));
                matList.add(matdetailPojo);
            }
        } catch (Exception ex) {
            System.out.println("Exception  : " + ex);
        } finally {
            daoClass.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String MaterialInfo() {
        try {
            String craftGroupQry = "select distinct craft_group from pos.craft_tax_price";
            String qry = "select material_desc,craft_group,plant,storageLocation,distributionChannel,price,standardPrice from material_master_taxgroup where material_no='" + matId.trim() + "'";
            rs = daoClass.Fun_Resultset(qry);
            if (rs.next()) {
                isMatExt = 2;
                matDesc = rs.getString("material_desc");
                craftGroup = rs.getString("craft_group");
                plantNo = rs.getString("plant");
                storageLoc = rs.getString("storageLocation");
                distChanelNo = rs.getInt("distributionChannel");
                price = rs.getFloat("price");
                standardPrice=rs.getFloat("standardPrice");
                crafrtGrprs = daoClass.Fun_Resultset(craftGroupQry);
                while (crafrtGrprs.next()) {
                    craftGroupList.add(crafrtGrprs.getString("craft_group"));
                }
            } else {
                isMatExt = 1;
//                System.out.println("Material Id  is Not there");
            }
        } catch (Exception e) {
            System.out.println("Exception in " + e.getMessage());
        } finally {
            daoClass.closeResultSet(rs);
            daoClass.closeResultSet(crafrtGrprs);
        }
        return SUCCESS;
    }

    public String updateMaterial() {   
        System.out.println("hdegfc");
        String updateMaterialQry = "update pos.material_master_taxgroup set material_desc='" + matDesc + "', craft_group='" + craftGroup + "', plant='" + plantNo + "', storageLocation='" + storageLoc + "', distributionChannel='" + distChanelNo + "', price='" + price + "', standardPrice='" + standardPrice + "', lastUpdatedDateTime=now() where material_no='" + matId.trim() + "'";
        daoClass.updatingRecord(updateMaterialQry);
        return SUCCESS;
    }

    public String getReturnString() {
        return returnString;
    }

    public void setReturnString(String returnString) {
        this.returnString = returnString;
    }

    public List getMatList() {
        return matList;
    }

    public void setMatList(List matList) {
        this.matList = matList;
    }

    public String getMatId() {
        return matId;
    }

    public void setMatId(String matId) {
        this.matId = matId;
    }

    public String getMatDesc() {
        return matDesc;
    }

    public void setMatDesc(String matDesc) {
        this.matDesc = matDesc;
    }

    public String getCraftGroup() {
        return craftGroup;
    }

    public void setCraftGroup(String craftGroup) {
        this.craftGroup = craftGroup;
    }

    public String getPlantNo() {
        return plantNo;
    }

    public void setPlantNo(String plantNo) {
        this.plantNo = plantNo;
    }

    public String getStorageLoc() {
        return storageLoc;
    }

    public void setStorageLoc(String storageLoc) {
        this.storageLoc = storageLoc;
    }

    public int getDistChanelNo() {
        return distChanelNo;
    }

    public void setDistChanelNo(int distChanelNo) {
        this.distChanelNo = distChanelNo;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(float standardPrice) {
        this.standardPrice = standardPrice;
    }

    public int getIsMatExt() {
        return isMatExt;
    }

    public void setIsMatExt(int isMatExt) {
        this.isMatExt = isMatExt;
    }

    public List<String> getCraftGroupList() {
        return craftGroupList;
    }

    public void setCraftGroupList(List<String> craftGroupList) {
        this.craftGroupList = craftGroupList;
    }

   
    
    

}

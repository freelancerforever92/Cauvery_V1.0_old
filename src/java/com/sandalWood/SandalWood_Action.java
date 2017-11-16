package com.sandalWood;

import java.sql.*;
import com.DAO.*;
import com.opensymphony.xwork2.ActionSupport;

/*@author Pranesh*/
public class SandalWood_Action extends ActionSupport {

    ResultSet rs;
    DaoClass daoClass = new DaoClass();
    private String materialId;
    private String vendorId;
    private String materialCraftName;
    private int vendorCountValue;
    private String SwMaterialId;
    private String swMaterialPrice;
    private String swMaterialDescription;
    private Float swMaterialTaxValue = 0.f;

    public SandalWood_Action() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String validateSwMaterial() {
        try {
            String isSwMaterialExits_Querry = "select count(*)from material_master_taxgroup where material_no='" + materialId.trim() + "'";
            int countValue = daoClass.Fun_Int(isSwMaterialExits_Querry);
            if (countValue >= 1) {
                String swMaterialGroup_Query = "select craft_group from material_master_taxgroup where material_no='" + materialId.trim() + "'";
                materialCraftName = daoClass.Fun_Str(swMaterialGroup_Query);
            } else if (countValue <= 0) {
            }
        } catch (Exception ex) {
            System.out.println("Exception In Validating SandalWood Material :  " + ex);
        } finally {
            daoClass.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String validateSwVendor() {
        try {
            String isVendorExitsQry = "select count(*)from vendor_master where vendor_id='" + vendorId.trim() + "'";
            vendorCountValue = daoClass.Fun_Int(isVendorExitsQry);
        } catch (Exception ex) {
            System.out.println("Exception In Validating SandalWood Vendor :  " + ex);
        } finally {
            daoClass.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String sandalWoodMaterialDetails() {
        try {
            String fetchSwMaterialDetails_Query = "SELECT materialTaxgroup.price,materialTaxgroup.material_desc,craftTaxprice.output_tax FROM pos.material_master_taxgroup materialTaxgroup INNER JOIN craft_tax_price craftTaxprice ON(materialTaxgroup.craft_group =craftTaxprice.craft_group)WHERE(materialTaxgroup.material_no = '" + SwMaterialId.trim() + "')";
            rs = daoClass.Fun_Resultset(fetchSwMaterialDetails_Query);
            while (rs.next()) {
                swMaterialPrice = rs.getString("price");
                swMaterialDescription = rs.getString("material_desc");
                swMaterialTaxValue = rs.getFloat("output_tax");
            }
        } catch (Exception ex) {
            System.out.println("Exception In Fetching SandalWood Details :  " + ex);
        } finally {
            daoClass.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String getMaterialCraftName() {
        return materialCraftName;
    }

    public void setMaterialCraftName(String materialCraftName) {
        this.materialCraftName = materialCraftName;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public int getVendorCountValue() {
        return vendorCountValue;
    }

    public void setVendorCountValue(int vendorCountValue) {
        this.vendorCountValue = vendorCountValue;
    }

    public String getSwMaterialId() {
        return SwMaterialId;
    }

    public void setSwMaterialId(String SwMaterialId) {
        this.SwMaterialId = SwMaterialId;
    }

    public String getSwMaterialPrice() {
        return swMaterialPrice;
    }

    public void setSwMaterialPrice(String swMaterialPrice) {
        this.swMaterialPrice = swMaterialPrice;
    }

    public String getSwMaterialDescription() {
        return swMaterialDescription;
    }

    public void setSwMaterialDescription(String swMaterialDescription) {
        this.swMaterialDescription = swMaterialDescription;
    }

    public Float getSwMaterialTaxValue() {
        return swMaterialTaxValue;
    }

    public void setSwMaterialTaxValue(Float swMaterialTaxValue) {
        this.swMaterialTaxValue = swMaterialTaxValue;
    }
}

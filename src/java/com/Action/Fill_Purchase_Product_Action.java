package com.Action;

import java.sql.*;
import com.DAO.*;
import com.opensymphony.xwork2.ActionSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;

/*@author Administrator*/
public class Fill_Purchase_Product_Action extends ActionSupport {

    ResultSet rs;
    private String Txtvendor = "";
    private String Txtmaterial = "";
    private Float Txtquantity = 0.f;
    private Float materialPrice;
    private String materialCraftGroup = "";
    private String materialDescription = "";
    Float materailTaxValue = 0.f;
    private Float materialCraftGST = 0.f; // FOR GST VALUE
    private Float vendorStock = 0.f;
    private Float materialStock = 0.f;
    //private boolean sapStockStatus = false;/*BLOCKED BY PRANESH 21.04.15*/
    private int sapStockStatus = 0;
    //private boolean liveStockStaus = false;/*BLOCKED BY PRANESH 21.04.15*/
    private int liveStockStaus = 0;
    private int stockCondition = 0;
    DaoClass cado = new DaoClass();
    Map<String, String> params = new HashMap<String, String>();

    public Fill_Purchase_Product_Action() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getSAPLiveStock() {
        try {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String strURL = "http://164.100.133.74:3200/CauveryWebServer/index.jsp?mId=" + Txtmaterial.trim() + "&vId=" + Txtvendor.trim() + "&pId=" + cado.getPlantId().trim() + "";
            URL url = new URL(strURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.getContent();
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String textOnly = Jsoup.parse(sb.toString()).text();
            String sapResult[] = textOnly.split(",");
            /*
             //if (!(sapResult[0].equals("") || sapResult[0].equals(null) && (sapResult[1].equals("") || sapResult[1].equals(null)))) {
             materialStock = Float.valueOf(sapResult[0]);
             vendorStock = Float.valueOf(sapResult[1]);
             sapStockStaus = true;
             //} else {
             //sapStockStaus = false;
             //}
             */
            if (DaoClass.getStockCheckValue().equals("1")) {
                /*STOCK CHECKING IS ENABLED.*/
                if (!((sapResult[0].equals("") || sapResult[0].equals("0") || sapResult[0].equals("0.0") || sapResult[0] == null) && (sapResult[1].equals("") || sapResult[1].equals("0") || sapResult[1].equals("0.0") || sapResult[1] == null))) {
                    materialStock = Float.valueOf(sapResult[0]);
                    vendorStock = Float.valueOf(sapResult[1]);
                    if (Txtquantity <= vendorStock) {
                        sapStockStatus = 1;
                        /*ENTERED QTY IS LESS THAN OR EQUAL TO AVAIABLE QTY ON SAP.*/
                    } else if (Txtquantity > vendorStock) {
                        sapStockStatus = -1;
                        /*ENTERED QTY IS GREATER THE AVAIABLE QTY ON SAP.*/
                    }
                } else {
                    sapStockStatus = -2;
                    /*OBTAINED QTY IS MAYBE 0 OR NULL.*/
                }
                /*STOCK CHECKING IS ENABLED.*/
            } else if (DaoClass.getStockCheckValue().equals("0")) {
                /*STOCK CHECKING IS BLOCKED.*/
                materialStock = Float.valueOf(sapResult[0]);
                vendorStock = Float.valueOf(sapResult[1]);
                sapStockStatus = 1;
                /*ALLOWING SALES PROCEESS.*/
                /*STOCK CHECKING IS BLOCKED.*/
            }
        } catch (Exception ex) {
            materialStock = 0.0f;
            vendorStock = 0.0f;
            sapStockStatus = 1;
            /*NOT ABLE TO FETCH STOCK FROM SAP (INTERNET ISSUE).*/
            System.out.println("WebServerUrl Exception :  " + ex.getMessage());
        }
        return sapStockStatus;
    }

    public String Fun_PurchaseDetials() {
        try {
            stockCondition = Integer.parseInt(DaoClass.getStockCheckValue());
//            System.out.println("SAP STOCK :  " + stockCondition);
            // if (getSAPLiveStock() > 0) {
            if (stockCondition >= 0) {
                String getMaterialDetailsQuery = "SELECT materialTaxgroup.price,materialTaxgroup.material_desc,materialTaxgroup.craft_group,craftTaxprice.output_tax,craftTaxprice.output_gst_tax FROM pos.material_master_taxgroup materialTaxgroup INNER JOIN craft_tax_price craftTaxprice ON(materialTaxgroup.craft_group =craftTaxprice.craft_group)WHERE(materialTaxgroup.material_no = '" + Txtmaterial.trim() + "')"; // craftTaxprice.output_gst_tax - FOR GST VALUE
                rs = cado.Fun_Resultset(getMaterialDetailsQuery);
                while (rs.next()) {
                    materialPrice = rs.getFloat("price");
                    materailTaxValue = rs.getFloat("output_tax");
                    materialCraftGST = rs.getFloat("output_gst_tax");
                    materialCraftGroup = rs.getString("craft_group");
                    materialDescription = rs.getString("material_desc");
                }
                //liveStockStaus = true;
                liveStockStaus = 1;
                //} else if (getSAPLiveStock() == -1) {
            } else if (stockCondition == -1) {
                //liveStockStaus = false;
                liveStockStaus = -1;
                //}else if (getSAPLiveStock() == -2) {
            } else if (stockCondition == -2) {
                //liveStockStaus = false;
                liveStockStaus = -2;
            }
            /*
             else {
             liveStockStaus = false;
             }
             */

        } catch (Exception ex) {
            System.out.println("Exception In Fetching Material Details :  " + ex);
        } finally {
            cado.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public int getStockCondition() {
        return stockCondition;
    }

    public void setStockCondition(int stockCondition) {
        this.stockCondition = stockCondition;
    }

    public Float getTxtquantity() {
        return Txtquantity;
    }

    public void setTxtquantity(Float Txtquantity) {
        this.Txtquantity = Txtquantity;
    }

    public Float getMaterialStock() {
        return materialStock;
    }

    public void setMaterialStock(Float materialStock) {
        this.materialStock = materialStock;
    }

    public int getLiveStockStaus() {
        return liveStockStaus;
    }

    public void setLiveStockStaus(int liveStockStaus) {
        this.liveStockStaus = liveStockStaus;
    }

    public String getTxtvendor() {
        return Txtvendor;
    }

    public void setTxtvendor(String Txtvendor) {
        this.Txtvendor = Txtvendor;
    }

    public Float getVendorStock() {
        return vendorStock;
    }

    public void setVendorStock(Float vendorStock) {
        this.vendorStock = vendorStock;
    }

    public String getMaterialCraftGroup() {
        return materialCraftGroup;
    }

    public void setMaterialCraftGroup(String materialCraftGroup) {
        this.materialCraftGroup = materialCraftGroup;
    }

    public String getTxtmaterial() {
        return Txtmaterial;
    }

    public void setTxtmaterial(String Txtmaterial) {
        this.Txtmaterial = Txtmaterial;
    }

    public Float getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(Float materialPrice) {
        this.materialPrice = materialPrice;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public Float getMaterailTaxValue() {
        return materailTaxValue;
    }

    public void setMaterailTaxValue(Float materailTaxValue) {
        this.materailTaxValue = materailTaxValue;
    }

    public Float getMaterialCraftGST() {
        return materialCraftGST;
    }

    public void setMaterialCraftGST(Float materialCraftGST) {
        this.materialCraftGST = materialCraftGST;
    }

}

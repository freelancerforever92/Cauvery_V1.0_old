<%-- 
    Document   : sandalWood
    Created on : Jun 18, 2014, 2:38:54 PM
    Author     : Administrator
--%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
        <link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.css'/>" type="text/css" />
        <link rel="stylesheet" href="<c:url value='/css/style.css'/>" type="text/css" />
        <link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.min.css'/>" type="text/css" />
        <script type="text/javascript" src="<c:url value='/js/jquery-sandwood.js'/>"></script>
        <style type="text/css">
            .black_overlay{
                display: none;
                position: fixed;
                top: 0%;
                left: 0%;
                width: 100%;
                height: 100%;
                background-color:black;
                z-index:1001;
                -moz-opacity: 0.8;
                opacity:.80;
                filter: alpha(opacity=80);
            }
            .white_content {
                display: none;
                position: fixed;
                top: 30%;
                left: 40%;
                width: 16%;
                height: 35%;
                padding: 16px;
                border: 2px solid #00FF00;
                background-color: white;
                z-index:1002;
                overflow: auto;
            }
        </style>
        <style type="text/css">

            .white_contentcustomerInfo {
                display: none;
                position: fixed;
                top: 30%;
                left: 35%;
                width: 28%;
                height: 36%;
                padding: 16px;
                border: 2px solid #ffff00; 
                background-color: #F5F5F5;
                z-index:1002;
                overflow: auto;
            }
        </style>
    </head>
    <form id="SandalWoodForm" name="SandalWoodForm" method="post" class="form-horizontal row-fluid">
        <fieldset>
            <legend>
                SandalWood-Billets
                <input type="button" id="sandalWood-CancelButton" name="sandalWood-CancelButton" class="btn btn-danger" value="Cancelling" onclick=";" style="margin-left: 8px;float: right;"/>
                <input type="button" id="sandalWood-CustomerInfoButton" name="sandalWood-CustomerInfoButton" class="btn btn-success" value="Customer Info" style="float: right;" onclick=""/>
            </legend>

            <div class="control-group" style="margin-left: -130px;margin-top: 10px;">
                <div class="controls">
                    <label class="control-label">Material 
                        <input type="text" class="input-medium" name="SwMaterialId" id="SwMaterialId" value="8907043000048" title="Please Enter Material ID" onblur="validateSwMaterial();" >
                    </label> <label class="control-label"  style="margin-left: 5px">Vendor
                        <input type="text" class="input-medium" name="sWVendorlId" id="sWVendorlId" value="100031" title="Please Enter Vendor" onblur="validateVendor();" >
                    </label> 
                    <label class="control-label"  style="margin-left: 5px">Kg
                        <input type="text" class="input-medium" name="sWQuantityId" id="sWQuantityId" value="0.025" title="Please Enter Quantity" onkeyup="return checkNumber(this)">
                    </label> 
                    <input type="button" style="margin-left: 10px; margin-top: 5px" class="btn btn-danger" title="Click On Button" id="clearBtn" name="clearBtn" value="Clear" onclick="functionClearAll();" />
                </div>
            </div>
            <div id="sandalWoodTableDiv" style="width: 70%; float: left; height: 300px; overflow: scroll; vertical-align: top;">
                <table id="sandalWoodProdTable" class="table" name="sandalWoodProdTable" border="2">
                    <thead>
                        <tr>
                            <th style="text-align: center;">S.No</th>
                            <th style="text-align: center;">Material</th>
                            <th style="text-align: center;width: 15%">Description</th>
                            <th style="text-align: center;">Vendor</th>
                            <th style="text-align: center;">Quantity</th>
                            <th style="text-align: center;">Rate</th>
                            <th style="text-align: center;">Vat%</th>
                            <th style="text-align: center;width: 5%">Vat-Value(Rs)</th>
                            <th style="text-align: center;display: none;">Discount Value</th>
                            <th style="text-align: center;">Net Value</th>

                            <th style="text-align: center;width: 10px;">Action</th>
                        </tr>
                    </thead>
                    <tbody id="sandalWood-pruchase_tbody"></tbody>
                    <tfoot></tfoot>
                </table>
            </div>
            <!--Booked Paticulars Table Opened -->
            <div style="width: 30%; height: 200px; float: right; margin-right: -15px; overflow: auto;">
                <table id="sandalWood-summry-table" border="2px" style=" width: 395px;">
                    <thead>
                        <tr>
                            <td colspan="8" align="center"
                                style="border-bottom: 1px; border-bottom-color: green; border-bottom-style: solid;">
                                <h4>Stock Information</h4>
                            </td>
                        </tr>
                        <tr>
                            <th style="text-align: center; display: none;">S.No</th>
                            <th style="text-align: center;width: 35%;">Material</th>
                            <th style="text-align: center;">Quantity</th>
                            <th style="text-align: center;">Stock</th>
                        </tr>
                    </thead>
                    <tbody id="sandalWood-summary-table-body"></tbody>
                </table>
            </div>
            <!--Booked Paticulars Table Closed -->


            <!--Discount,Packing Charge Options  Opened -->
            <div style="width: 30%; height: 200px; float: right; margin-right: -15px">
                <div class="control-group">
                    <label class="control-label">Discount (%)</label>
                    <div class="controls">
                        <input type="text" class="input-medium" id="sWDiscountPercentage" name="sWDiscountPercentage" onkeyup="swDisCountCalculation();"<%--onkeyup="resetBillAmtWithDiscoutValue();"--%>/>
                    </div>
                    <!--LIGHT-BOX FORM FOR DISCOUNT inserted -->
                    <div id="fade" class="black_overlay"></div>
                    <div id="light" class="white_content">
                        <form id="sandalWoodApprovalForm" name="sandalWoodApprovalForm" method="post">
                            <h2 class="form-signin-heading" style="text-align: center">Admin Approval Text</h2><hr/>
                            <textarea id="sandalWoodApprovalText" class="sandalWoodApprovalText" placeholder="Enter Approval Text" rows="4">
                                
                            </textarea>
                            <input type="button" id="sandalWoodSubmit" name="sandalWoodSubmit" class="btn btn-success" value="Submit" onclick="hideApprovalDialogue();" style="margin-left: 29px; margin-top: 12px;"/>
                            <a href = "javascript:void(0)" onclick = "document.getElementById('light').style.display = 'none';
                                    document.getElementById('fade').style.display = 'none';
                                    clearDiscountTextValue();"/>
                            <input type="button" id="sandalWoodCancel" name="sandalWoodCancel" class="btn btn-danger" value="Cancel" onclick="swCancelDiscount();" style="margin-left: 10px; margin-top: 12px;"/>
                        </form>

                        <!--LIGHT-BOX FORM FOR DISCOUNT inserted -->

                    </div>

                    <div id="fadeCustomerInfo" class="black_overlay"></div> 
                    <div id="sandalWoodCustInfodiv" class="white_contentcustomerInfo">
                        <center>
                            <form id="sandalWoodCustInfoForm" name="sandalWoodCustInfoForm" method="post">
                                <h2 class="form-signin-heading" style="text-align: center">Customer Info</h2><hr/>
                                <input type="text" id="swCustFname" name="swCustFname" placeholder="FirstName" />
                                <input type="text" id="swCustLname" name="swCustLname" style="margin-top: 8px;" placeholder="LastName" />
                                <input type="text" id="swCustCNo" name="swCustCNo" style="margin-top: 8px;" placeholder="Contact Number" /><br>
                                <input type="button" id="swCustInfoSave" name="swCustInfoSave" class="btn btn-success" value="Save" style="margin-left: 5px;margin-top: 20px;" onclick="resetcustomerInfoSaveText();"/>
                                <input type="button" id="swCustInfoCancel" name="swCustInfoCancel" class="btn btn-danger" value="Cancel" style="margin-top: 20px;" onclick="document.getElementById('sandalWoodCustInfodiv').style.display = 'none';
                                        document.getElementById('fadeCustomerInfo').style.display = 'none';"/>
                            </form>
                        </center>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" >Packing Charges
                        (Rs.)</label>
                    <div class="controls">
                        <input type="text" class="input-medium" name="swPackingCharge" id="swPackingCharge" onkeyup="billAmtWithPackingCharge();" onkeydown="number(event)" />
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" >Net Total (Rs.)</label>
                    <div class="controls">
                        <input type="text" class="input-medium" id="swGrandTotal" name="swGrandTotal" readonly="true" />
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" >Bill Amount
                        (Rs.)</label>
                    <div class="controls">
                        <input type="text" class="input-medium" name="swBillAmt"
                               readonly="true" id="swBillAmt" />
                    </div>
                </div>

            </div>
            <!--Discount,Packing Charge Options  Closed -->

            <!--Discount Approval Text Closed -->
            <div style="float: left;margin-top: 2%;">
                <label class="control-label" ><b>Approval</b> </label>
                <input type="text" id="swTxtApproval" class="swDiscountApproval"readonly="true" name="swTxtApproval" style="margin-left: 6px;width: 210px;" />
            </div>
            <!--Discount Approval Text Closed -->


            <!--Save/Print Buttons Opened -->
            <div style="float: right;margin-top: 20px;margin-right: 30px;margin-bottom: 15px;">
                <input type="button" id="swBtnSavePrint" class="btn btn-success" name="swBtnSavePrint" value="Save / Print" onclick="insertSwTableProducts();" />
                <input type="button" class="btn btn-primary" onclick="document.getElementById('lightReprint').style.display = 'block';
                        document.getElementById('fade').style.display = 'block';"  value="Re-Print" />
                <input type="reset" id="swBtnCancel" class="btn btn-danger" name="swBtnCancel" value="Cancel" onclick="functionClearAll();"/>           
            </div>
            <!--Save/Print Buttons Closed -->
        </fieldset>
    </form>
</html>

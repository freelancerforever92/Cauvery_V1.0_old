<%-- 
    Document   : SalesFrom
    Created on : Feb 26, 2014, 9:33:41 PM
    Author     : Administrator
--%>

<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<script src="<c:url value='/js/sales.home.js'/>"></script>
<script src="<c:url value='/js/salesorder-cancelling.js'/>"></script>
<link rel="stylesheet" href="css/font-awesome.min.css">

<head>
    <style type="text/css">
        body{
            height: 700px;
            width: 1470px;
        }
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
            width: 17%;
            height: 29%;
            padding: 16px;
            /*border: 2px solid #00FF00;*/
            background-color: white;
            z-index:1002;
            overflow: auto;
        }
        .white_contentcustomerInfo {
            display: none;
            position: fixed;
            top: 30%;
            left: 35%;
            width: 28%;
            height: 37%;
            padding: 16px;
            border: 2px solid #ffff00; 
            background-color: #F5F5F5;
            z-index:1002;
            overflow: auto;
        }
        #txtApproval{
            outline: 0;
        }
    </style>
    <script type="text/javascript">
        
             $(document).ready(function()
            {
                 $("#Txtmaterial").attr('disabled','disabled');
                 $("#Txtvendor").attr('disabled','disabled');
                 $("#Txtquantity").attr('disabled','disabled');
                 
                 
            });
            
        function blurfunction(x) {
            x.style.background = "while";
        }
        function inputBoxColorChange(x) {
            x.style.background = "lightblue";
        }
        function buttonOnFocus(x) {
            x.style.border = "4px solid #85335C";
        }
        function buttonFocusLost(x) {
            x.style.border = "";
            x.style.class = "btn btn-danger";
        }
    </script>
    <sj:head jquerytheme="start"/>
</head>
<body>
    <form id="Salesform" name="Salesform" method="post" class="form-horizontal row-fluid">
        <fieldset>
            <legend>
                Counter Bill
                <input type="button" id="sales-ReportButton" name="sales-ReportButton" class="btn btn-primary" value="Reports" onfocus="buttonOnFocus(this);" onblur="buttonFocusLost(this);" onclick="loadCounterReportLayout();" style="margin-left: 8px;float: right;"/>
                <input type="button" id="sales-CancelButton" name="sales-CancelButton" class="btn btn-danger" value="Viewing / Cancelling" onfocus="buttonOnFocus(this);" onblur="buttonFocusLost(this);" onclick="loadSalesOrderCancelForm();" style="margin-left: 8px;float: right;"/>
                <%--<input type="button" id="sales-CustomerInfoButton" name="sales-CustomerInfoButton" class="btn btn-success" value="Customer Info" style="float: right;" onfocus="buttonOnFocus(this);" onblur="buttonFocusLost(this);" onclick="showCustomerInfoDialog();"/>--%>
                <input type="button" id="sales-CustomerInfoButton" name="sales-CustomerInfoButton" class="btn btn-success" value="Customer Info" style="float: right;" data-toggle="modal" data-target=".bs-modal-sm" onfocus="buttonOnFocus(this);" onblur="buttonFocusLost(this);" onclick="showCustomerInfoDialog();"/>
            </legend>
            <input type="hidden" id="hdnProdPrice" name="hdnProdPrice" style="display: none;"/>
            <input type="hidden" id="hdnTotalPrice" name="hdnTotalPrice" style="display: none;" />
            <input type="hidden" id="hidden-craft-gst-percentage" name="hiddenGST" style="display: none;"/>
            <input type="hidden" id="hiddenNavigatedCounterName" name="hiddenNavigatedCounterName" style="display: none;" />
            <input type="hidden" class="HidTextField" id="tableCountValue" name="tableCountValue" style="display: none;" />
            <input type="hidden" id="hdnDisCountValue" name="hdnDisCountValue" readonly="true" style="display: none;"/>
            <input type="hidden" class="HidTextField" id="TxtPurchaseProd" name="TxtPurchaseProd" style="display: none;"/>

            <div class="control-group" style="margin-left: -130px;margin-top: 10px;">
                <div class="controls">
                    <label class="control-label">Material 
                        <input type="text" class="input-medium" name="Txtmaterial" id="Txtmaterial" title="Please Enter Material ID"  onblur="validate_material();">
                    </label> 
                    <label class="control-label"  style="margin-left: 5px">Vendor
                        <input type="text" class="input-medium" name="Txtvendor" id="Txtvendor" title="Please Enter Vendor"  onblur="validate_vendor();">
                    </label> 
                    <label class="control-label"  style="margin-left: 5px">Quantity
                        <input type="text" class="input-medium" name="Txtquantity" id="Txtquantity" title="Please Enter Quantity"  onkeyup="return checkNumber(this)">
                    </label> 
                    <input type="button" style="margin-left: 10px; margin-top: 5px" class="btn btn-danger" title="Clear" id="clearBtn" name="clearBtn" value="Clear" onfocus="buttonOnFocus(this);" onblur="buttonFocusLost(this);" onclick="functionClearAll();" />                
                    <div id="control-backDated" style="float: right;margin-left: 10px;" >
                        <input type="checkbox" id="enableDisableBackDated" name="enableDisableBackDated" title="Checkit for backdated" style="margin-right: 5px;"/><b style="color: red;">Back Dated : </b>
                        <sj:datepicker id="salesBackDate" name="salesBackDate" placeholder="Select Date" changeMonth="true" changeYear="true" showOn="focus" displayFormat="yy-mm-dd" maxDate="true" cssClass="input-medium"/>
                        <input type="text" id="enableManualBil" name="enableManualBil" placeholder="Manual Bill No" class="input-medium" disabled>
                    </div>
                </div>
            </div>
            <div>
                <div id="salesTable" style="width: 75%; float: left; height: 300px; overflow: scroll; vertical-align: top;">
                    <table id="purchase_prodTable" class="table" name="purchase_prodTable" border="2">
                        <thead>
                            <tr>
                                <th style="text-align: center;">S.No</th>
                                <th style="text-align: center;">Material</th>
                                <th style="text-align: center;width: 15%">Description</th>
                                <th style="text-align: center;">Vendor</th>
                                <th style="text-align: center;">Vendor-Stock</th>
                                <th style="text-align: center;">Quantity</th>
                                <th style="text-align: center;">Rate</th>
                                
                                <th style="text-align: center;display: none;" class="vatTD">Vat%</th>
                                <th style="text-align: center;display: none;" class="vatTD">Vat-Value(Rs)</th>
                                
                                <%--
                                    <th style="text-align: center;display: none;">Discount Value</th>
                                --%>
                                <th style="text-align: center;">Net Value</th>
                                <th style="text-align: center;width: 10px;">Action</th>
                            </tr>
                        </thead>
                        <tbody id="pruchase_tbody"></tbody>
                        <tfoot></tfoot>
                    </table>
                </div>

                <div style="width: 350px; height: 200px; float: right; margin-right: -25px; overflow: auto;">
                    <table id="summry-table" border="2px" style=" width: 355px;">
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
                                <th style="text-align: center; display: none;">Description</th>
                                <th style="text-align: center;">Quantity</th>
                                <th style="text-align: center; display: none;">Rate</th>
                                <th style="text-align: center; display: none;">Net Value</th>
                                <th style="text-align: center;">Stock</th>
                            </tr>
                        </thead>
                        <tbody id="summary-table-body"></tbody>
                    </table>
                </div>

                <div style="width: 300px; height: 200px; float: right; margin-right: -15px">
                    <div class="control-group">
                        <label class="control-label">Discount (%)</label>
                        <div class="controls">
                            <input type="text" class="input-medium" id="TxtDiscount" name="TxtDiscount" onkeyup="discountCalculation();"<%--onkeyup="resetBillAmtWithDiscoutValue();"--%>/>
                        </div>
                        <div id="fade" class="black_overlay"></div>
                        <div id="light" class="white_content">
                            <form id="discuntAuthentication" name="discuntAuthentication" method="post">
                                <h2 class="form-signin-heading" style="text-align: center">Admin Approval</h2><hr/>
                                <textarea id="discountApprovalText" name="discountApprovalText" placeholder="Enter Approval Text" rows="4">
                                
                                </textarea>
                                <input type="button" id="disSubmit" name="disSubmit" class="btn btn-success" value="Submit" onclick="autheDiscount();" style="margin-left: 35px; margin-top: 12px;"/>
                                <a href = "javascript:void(0)" onclick = "document.getElementById('light').style.display = 'none';
                                        document.getElementById('fade').style.display = 'none';
                                        clearDiscountTextValue();"/>
                                <input type="button" id="disCancel" name="disCancel" class="btn btn-danger" value="Cancel" onclick="autheDiscountCancel();" style="margin-left: 10px; margin-top: 12px;"/>
                            </form>
                        </div>
                        <%--
                        <div id="fadeCustomerInfo" class="black_overlay"></div> 
                        <div id="customerInfoLight" class="white_contentcustomerInfo">
                            <center>
                                <form id="customerInfoForm" name="customerInfoForm" method="post">
                                    <h2 class="form-signin-heading" style="text-align: center">Customer Info</h2><hr/>
                                    <input type="text" id="custFname" name="custFname" placeholder="FirstName" />
                                    <input type="text" id="custLname" name="custLname" style="margin-top: 8px;" placeholder="LastName" />
                                    <input type="text" id="custCNo" name="custCNo" style="margin-top: 8px;" placeholder="Contact Number" /><br>
                                    <input type="button" id="custInfoSave" name="custInfoSave" class="btn btn-success" value="Save" style="margin-left: 5px;margin-top: 20px;" onclick="resetcustomerInfoSaveText();"/>
                                    <input type="button" id="custInfoCancel" name="custInfoCancel" class="btn btn-danger" value="Cancel" style="margin-top: 20px;" onclick="document.getElementById('customerInfoLight').style.display = 'none';
                                            document.getElementById('fadeCustomerInfo').style.display = 'none';"/>
                                </form>
                            </center>
                        </div>
                        --%>
                        <div class="modal fade bs-modal-sm" tabindex="-1" role="dialog" aria-labelledby="coustomeModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-sm">
                                <div class="modal-content">
                                    <center>
                                        <form id="customerInfoForm" name="customerInfoForm" method="post">
                                            <h2 class="form-signin-heading" style="text-align: center">Customer Info
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></h2><hr/>
                                            <input type="text" id="custFname" class="input-large" name="custFname" placeholder="FirstName" /><br>
                                            <input type="text" id="custLname" class="input-large" name="custLname" style="margin-top: 8px;" placeholder="LastName" /><br>
                                            <input type="text" id="custCNo" class="input-large" name="custCNo" style="margin-top: 8px;" placeholder="Contact Number" /><br>
                                            <input type="button" id="custInfoSave" name="custInfoSave" class="btn btn-success" value="Save" data-dismiss="modal"  style="margin-left: 5px;margin-top: 20px;" onclick="resetcustomerInfoSaveText();"/>
                                            <input type="button" id="custInfoCancel" name="custInfoCancel" class="btn btn-danger" data-dismiss="modal" style="margin-top: 20px;" value="Cancel"/>
                                            <%--
                                            <input type="button" id="custInfoCancel" name="custInfoCancel" class="btn btn-danger" value="Cancel" style="margin-top: 20px;" onclick="document.getElementById('customerInfoLight').style.display = 'none';
                                                    document.getElementById('fadeCustomerInfo').style.display = 'none';"/>
                                            --%>
                                        </form>
                                    </center>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">Packing Charges
                            (Rs.)</label>
                        <div class="controls">
                            <input type="text" class="input-medium" name="PckCharges"id="PckCharges" onkeyup="resetBillAmtWithPackageValue();"onkeydown="number(event)" />
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">Net Total (Rs.)</label>
                        <div class="controls">
                            <input type="text" class="input-medium" name="GrndTotal"
                                   readonly="true" id="GrndTotal" />
                        </div>
                    </div>
                   
                    <div class="control-group">
                        <label class="control-label">SGST (Rs.)</label>
                        <div class="controls">
                            <input type="text" class="input-medium" id="sgst-value" name="cgst" readonly="true"  />
                        </div>
                    </div>
                    
                    <div class="control-group">
                        <label class="control-label">CGST (Rs.)</label>
                        <div class="controls">
                            <input type="text" class="input-medium" id="cgst-value" name="gst" readonly="true"  />
                        </div>
                    </div>
                                        

                    <div class="control-group">
                        <label class="control-label">Bill Amount
                            (Rs.)</label>
                        <div class="controls">
                            <input type="text" class="input-medium" name="BillTotal"readonly="true" id="BillTotal" />
                        </div>
                    </div>
                </div>

                <div style="float: left;margin-top: 2%;">
                    <label class="control-label disApproval"><b>Approval</b> </label>
                    <input type="text" id="txtApproval" class="disApproval"readonly="true" name="txtApproval" style="margin-left: 6px;width: 210px;" />
                </div>

                <div style="float: right;margin-top: 20px;margin-right: 30px;margin-bottom: 15px;">
                    <input type="button" id="btnSavePrint" class="btn btn-success" name="btnSavePrint" value="Save / Print" onfocus="buttonOnFocus(this);" onblur="buttonFocusLost(this);" onclick="checkIsBackDated();"  /><%--insBookedProducts--%>
                    <input type="button" class="btn btn-primary" value="Re-Print" data-toggle="modal" data-target=".bs-reprint-modal-sm"  onfocus="buttonOnFocus(this);" onblur="buttonFocusLost(this);"/>
                    <%--
                    <input type="button" class="btn btn-primary" onclick="document.getElementById('lightReprint').style.display = 'block';
                            document.getElementById('fade').style.display = 'block';"  value="Re-Print" onfocus="buttonOnFocus(this);" onblur="buttonFocusLost(this);" />
                    --%>
                    <input type="reset" id="btnCancel" class="btn btn-danger" name="btnCancel" value="Cancel" onfocus="buttonOnFocus(this);" onblur="buttonFocusLost(this);" onclick="functionClearAll();"/>           
                </div>

            </div>
        </fieldset>

        <%--
        <div id="lightReprint" class="white_content" style="height: 15%;">
        <form id="frmReprint" name="frmReprint" method="post">
        <input type="hidden" id="hidReBillNo" name="hidReBillNo"/>
        <input type="text" id="billNumber" name="billNumber" size="10" style="width: 185px;" placeholder="Enter Sales OrderNumber"/>
        <input type="button" id="rePrint" class="btn btn-primary" name="rePrint" value="Print" onclick="Fun_Reprint();" style="margin-left: 50px;margin-top: 15px;" />
        <input type="button" id="rePrintCancel" class="btn btn-primary" name="rePrintCancel" value="Cancel" style="margin-top: 15px;" onclick="document.getElementById('lightReprint').style.display = 'none';
        document.getElementById('fade').style.display = 'none';"  />
        </form>
        </div>

<div id="fade" class="black_overlay"></div> 
        --%>
        <center>
            <div class="modal fade bs-reprint-modal-sm" tabindex="-1" role="dialog" aria-labelledby="coustomeModalLabel" aria-hidden="true" style="width: 250px;margin-left: -7em;">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <center>
                            <form id="frmReprint" name="frmReprint" method="post">
                                <h2 class="form-signin-heading" style="text-align: center">Reprint 
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </h2>
                                <hr/>
                                <input type="hidden" id="hidReBillNo" name="hidReBillNo"/>
                                <input type="text" id="billNumber" name="billNumber" size="10" style="width: 185px;" placeholder="Enter Sales OrderNumber"/><br>
                                <input type="button" id="rePrint" class="btn btn-primary" name="rePrint" value="Print" onclick="Fun_Reprint();" style="margin-left: 20px;margin-top: 15px;" />
                                <input type="button" id="rePrintCancel" class="btn btn-danger" name="rePrintCancel" value="Cancel" style="margin-top: 15px;" data-dismiss="modal"/>
                            </form>
                        </center>
                    </div>
                </div>
            </div>
        </center>
    </form>
</body>



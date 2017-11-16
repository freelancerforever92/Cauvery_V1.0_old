<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<script type="text/javascript">
    $(document).ready(function()
    {
        cashbillOrderNo();
        cashbill_LoadPaymentType();
        cashbill_LoadCouponTypes();
        $(".paymentType_td").hide();
        $(".couponReduction_td").hide();
        $(".couponReductiontable_td").hide();
        $(".otherCurrency").hide();
        $("#txtTotalAmt").val("0.0");
        $("#txtBalanceAmt").val("0.0");
//        $("#txtCounterBillNo").focus();
        $("#cashBackDate").attr("disabled", true);
        $("#txtCashPayment").attr("disabled", "disabled");
        $("#txtCardPayment").attr("disabled", "disabled");
        $("#txtForeginCurrency").attr("disabled", "disabled");
        $("#otherCurrencyValue").attr("disabled", "disabled");
        $("#calculatedINRValue").attr("disabled", "disabled");
        $("#cashBillSavePrint_btn").attr("disabled", true);
        $("#txtCardType").val('Select');
       var cYear=$("#cYear").val();
        setcYearVal(cYear);
       // document.getElementById('cashierNameTable').style.display = 'none';
    });

    /*$.getJSON('SessionName.action', function(data) {
     if (data.logusrtype !== "all") {
     document.getElementById('backDated-adminOption').style.display = 'none';
     } else if (data.logusrtype === "all") {
     document.getElementById('backDated-adminOption').style.display = 'block';
     }
     });
     */

    $('#txtCounterBillNo').keypress("keypress", function(e) {
        if (e.keyCode === 13) {
            fetchCounterBill_And_Amt();           
        }
      
    });

    
    
    $('#enableDisableBackDateCash').click(function() {
        if ($(this).is(":checked")) {
            $("#cashBackDate").attr("disabled", false);
        } else {
            $('#cashBackDate').val('');
            $("#cashBackDate").attr("disabled", true);
        }
    });
    /*
     //LOADING ACTIVE CASHIER NAMES FOR BACK DATED POSTING (TEMP-BLOCKED)15.04.2015
     $('#enableDisableBackDateCash').click(function() {
     if ($(this).is(":checked")) {
     $("#cashBackDate").attr("disabled", false);
     $.getJSON('getCashierNames.action', function(data) {
     $("#cashier-detail-table").empty();
     document.getElementById('cashierNameTable').style.display = 'block';
     $.each(data.cashierNames, function(i, item) {
     var dynamicCashierDetailTr = '<tr id="cashierDetail-tr-id" class="cashierDetail-tr-class"> <td> <center> <input type="radio" id="cashierPk" name="cashierPk" value=' + item.cashierEmpId + '> </center></td> <td id=' + item.cashierEmpId + ' name=' + item.cashierEmpId + '>' + item.cashierName + ' </td></tr>';
     $("#cashier-detail-table").append(dynamicCashierDetailTr);
     });
     });
     }
     else {
     $('#cashBackDate').val('');
     $("#cashier-detail-table").empty();
     $("#cashBackDate").attr("disabled", true);
     document.getElementById('cashierNameTable').style.display = 'none';
     }
     });
     */
    function daysInMonth() {
        var inputDate = $("#txtExpiryDate").val().trim();
        var expDate = inputDate.split('/');
        alert(new Date(expDate[1], expDate[0], 0).getDate());
    }

    function cashButtonOnFocus(x) {
        x.style.border = "4px solid #85335C";
    }
    function cashButtonFocusLost(x) {
        x.style.border = "";
        x.style.class = "btn btn-danger";
    }
    function  showcurrency() {
        $("#conversionDiv").show();
    }


</script>

<style>
    .currency{position: fixed; right: 0; top: 380px;}
    .conversion{display: none;}
    .currencyIcon{background-color: #009900; padding: 10px;}
    .positionCurr{margin-left: -204px!important; bottom: -61px!important;border: black 1px solid;}
</style>

<%

 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd ");
        Date dt = new Date();
        int currentYear = 0;
        String s = sdf.format(dt).split("/")[1];
        if (s.equals("03") || s.equals("02") || s.equals("01")) {

            int year = Integer.parseInt(sdf.format(dt).split("/")[0]);
            currentYear = year - 1;

        } else {
            int year = Integer.parseInt(sdf.format(dt).split("/")[0]);

            currentYear = year;
        }
%>

<sj:head jquerytheme="start"/>

<form id="cashBillingForm" name="cashBillingForm" method="post">

    <legend>Cash Counter
        
        <%--<input type="button" id="cashBillReport" name="cashBillReport" class="btn btn-large" value="UnCollected Bills : 1" onfocus="cashButtonOnFocus(this);" onblur="cashButtonFocusLost(this);" onclick="loadCashBillReportHome();" style="margin-left: 8px;float: right;"/>--%>
        <input type="button" id="cashToCard" class="btn btn-info" value="Change Cash / Card" onclick="loadChangePayType();" style="margin-left: 8px;float: right;"/>
        <input type="button" id="cashBillReport" name="cashBillReport" class="btn btn-success" value="Reports" onfocus="cashButtonOnFocus(this);" onblur="cashButtonFocusLost(this);" onclick="loadCashBillReportHome();" style="margin-left: 8px;float: right;"/>
        <input type="button" id="cashbilling_clearbutton" name="cashbilling_clearbutton" class="btn btn-danger" onfocus="cashButtonOnFocus(this);" onblur="cashButtonFocusLost(this);" onclick="clearAllFieldValues();" value="Clear" style="position: relative;float: right;"/>
        <input type="button" id="scannedCounterBillCount" name="scannedCounterBillCount" class="btn btn-primary" value="0" style="margin-right: 8px;float: right;font-weight: bold;height:40px;width: 55px; margin-top: -12px; "/>
    </legend>

    <div class="control-group">
        <div class="row">
            <div class="span9">              
                <table style="width: 100%;">
                    <tr>
                        <td width="12%"></td>
                        <td width="10%"></td>
                        <td width="75%"></td>
                    </tr>
                    <tr>
                        <td>
                            <label class="control-label">Counter Bill</label>
                        <input  type="text" id="cYear" style="display: none;" value="<%=currentYear%>"/>
                        </td>
                        <td>
                            <input type="text" class="input-medium" id="txtCounterBillNo" name="txtCounterBillNo" title="CounterBill ID" value="<%=currentYear%>"/>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><label class="control-label">Payment Mode</label></td>
                        <td><select class="input-block-level" id="cashBillPaymentType" name="cashBillPaymentType" onchange="show_CashTypes();" title="Select Payment Type">
                                <option value="Select">Select</option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>     
                        <td class="paymentType_td">
                            <label style="padding-right: 5px;" class="control-label" for="txtCardType">Card Type</label>
                        </td>
                        <td class="paymentType_td">
                            <!--<input type="text" id="txtCardType" name="txtCardType" title="Card Type" placeholder="Card Type" style="margin-left: 20px;margin-top: 15px;width: 100px;"/>-->
                            <select class="btn dropdown-toggle input-block-level" id="txtCardType" name="txtCardType" >
                                <option value="Select">Select</option>
                                <option value="VISA">Visa</option>
                                <option value="MC">MasterCard</option>
                            </select>
                        </td>
                        <td>
                            <table style="width: 100%;">
                                <tr>
                                    <td width="15%"></td>
                                    <td width="20%"></td>
                                    <td width="15%"></td>
                                    <td width="15%"></td>
                                    <td width="20%"></td>
                                    <td width="15%"></td>
                                </tr>
                                <tr> 
                                    <td class="paymentType_td">
                                        <label class="pull-right" style="padding-right: 5px;">Card Number</label>
                                    </td>
                                    <td class="paymentType_td">
                                        <input class="input-block-level" type="text" id="txtCardNumber" name="txtCardNumber" title="Card Number" placeholder="Card Number"/>
                                    </td>

                                    <td class="paymentType_td">
                                        <label class="pull-right" style="padding-right: 5px;">Expiry Date</label>
                                    </td>
                                    <td class="paymentType_td">
                                        <input class="input-block-level" type="text" id="txtExpiryDate" name="txtExpiryDate" title="Card ExpriyDate" placeholder="MM / YYYY"/>
                                    </td>

                                    <td class="paymentType_td">
                                        <label class="pull-right" style="padding-right: 5px;">Card Holder Name</label>
                                    </td>
                                    <td class="paymentType_td">
                                        <input class="input-block-level" type="text" id="txtCardHolderName" name="txtCardHolderName" title="Card Holder Name" placeholder="Card Holder Name"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label class="control-label" for="cashbill_CouponRedemption">Coupon Redn</label>
                        </td>
                        <td>
                            <select  class="btn dropdown-toggle input-block-level" id="cashbill_CouponRedemption" name="cashbill_CouponRedemption" title="Select Redemption Type"  onchange="show_CouponRedu();">
                                <option value="select">Select</option>
                                <option value="couponredu">Coupon Reduction</option>
                            </select>
                        </td>
                    </tr>

                    <tr><td colspan="3">&nbsp;</td></tr>
                    <tr>
                        <%--Coupon Reduction Process--%>
                        <td class="couponReduction_td">
                            <label class="control-label" for="cashbillCouponType">Coupon Type</label>
                        </td>
                        <td class="couponReduction_td">
                            <select class="input-block-level" id="cashbillCouponType" name="cashbillCouponType" onchange="show_CouponRedu();">
                                <option value="select">Select</option>
                            </select>
                        </td>
                        <td>
                            <table style="width: 100%;">
                                <tr>
                                    <td width="15%"></td>
                                    <td width="25%"></td>
                                    <td width="10%"></td>
                                    <td width="50%"></td>
                                </tr>         
                                <tr>
                                    <td class="couponReduction_td">
                                        <label class="pull-right" style="padding-right: 5px;" for="couponNoFrom">
                                            Coupon No
                                        </label>
                                    </td>
                                    <td class="couponReduction_td">
                                        <input type="text" id="couponNos" name="couponNos" class="input-block-level"/>
                                    </td>

                                    <td class="couponReduction_td">
                                        <input style="margin-bottom: 10px;margin-left: 10px;" type="button" id="couponNoGen_btn" name="couponNoGen_btn" onclick="couponRedemption();" class="btn btn-success" value="Enter"/>
                                    </td>
                                    <td></td>

                                </tr>
                            </table>
                        </td>
                        <%--Coupon Reduction Process--%>
                    </tr>
                </table>
            </div>

            <div class="span3">
<!--                <div class="clearfix pull-right" id="backDated-adminOption">
                    <input type="checkbox" id="enableDisableBackDateCash" name="enableDisableBackDateCash" title="Checkit for backdated" style="margin-right: 5px;"/><b style="color: red;" for="enableDisableBackDateCash">Back Dated : </b>
                    <sj:datepicker id="cashBackDate" name="cashBackDate" changeMonth="true" changeYear="true" showOn="focus" displayFormat="yy-mm-dd" maxDate="true" cssClass="input-medium"/>
                    <div id="cashierNameTable" style="overflow: auto;height: 150px;">
                        <table class="table table-bordered">
                            <thead>
                            <th><center>Select</center></th>
                            <th><center>Cashier Name</center></th>
                            </thead>
                            <tbody id="cashier-detail-table"></tbody>
                        </table>
                    </div>
                </div>-->
            </div>
        </div>
    </div>

    <div style="float: left;height: 180px;width: 369px;overflow: auto;vertical-align: top;">
        <table class="table table-bordered" border="1" style="width: 350px;margin-top: 10px;">
            <thead>
                <tr>
                    <th style="text-align: center">Counter Bill</th>
                    <th style="text-align: center">Total Amount</th>
                    <th style="text-align: center">Date Time</th>
                    <th></th>
                </tr>
            </thead>
            <tbody id="loadScannedBillnoAmt"></tbody>
        </table>
    </div>

    <div style="position: relative;margin-left: 400px;margin-top: 5px;">
        <div class="couponReduction_td" style="float: right;height: 110px;width: 420px;overflow: auto;">
            <table class="table table-bordered" id="couponRedemTable" border="1">
                <thead>
                    <tr>
                        <th style="text-align: center">Coupon Type</th>
                        <th style="text-align: center">Coupon No</th>
                        <th style="text-align: center">Amount</th>
                        <th style="text-align: center">Action</th>
                    </tr>
                </thead>
                <tbody id="load_CouponNumbers"></tbody>
            </table>
        </div>

        <label style="margin-left: 150px;"><b>Payments</b></label>
        <table>
            <tr>
                <td>
                    <label class="control-label">Cash</label>
                </td>
                <td>
                    <input type="text" id="txtCashPayment" name="txtCashPayment" class="input-medium" style="margin-left: 15px;"/>
                </td>

            </tr>

            <tr>
                <td>
                    <label class="control-label">Card</label>
                </td>
                <td>
                    <input type="text" id="txtCardPayment" name="txtCardPayment"  class="input-medium" style="margin-left: 15px;"/>
                </td>
            </tr>
            <%--
            <tr>
                <td>
                    <label class="control-label">Traveller's Cheque</label>
                </td>
                <td>
                    <input type="text" id="txtTravellersCheque" name="txtTravellersCheque" onkeyup="" class="input-medium" style="margin-left: 50px;"/>
                </td>
            <%--
            <td>
                <input type="button" id="" name="" class="btn btn-primary" value="Return Sales"style="margin-left: 40px;width: 145px;" onclick="loadSalesReturnForm();"/>
            </td>
           
        </tr>
            --%>
            <tr>
                <td>
                    <label class="control-label">Currency Type</label>
                </td>
                <td>
                    <select id="currencyType" name="currencyType" class="otherCurrency" style="width: 155px;height: 27px; margin-left: 15px;" onclick="selectOptionCurrencyType();">
                        <option value="select">Select</option>
                        <option value="usd">USD</option>
                        <!--<option value="euro">Euro</option>-->
                    </select>
                    <input type="text" id="txtForeginCurrency" name="txtForeginCurrency" style="margin-left: 15px;" placeholder="Foregin Currency Value" class="input-medium" style="margin-left: 15px;" onkeyup="calculateInr();"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input type="text" id="otherCurrencyValue" name="otherCurrencyValue"  placeholder="Exchange Rate" class="input-medium otherCurrency" style="margin-left: 15px;" onkeyup="calculatedInrValue();"/>
                    <input type="text" id="calculatedINRValue" name="calculatedINRValue"  readonly="true" class="input-medium otherCurrency" style="margin-left: 15px;"/>
                </td>
            </tr>
        </table>

        <div style="margin-top: 10px;">
            <input type="button" id="cashBillSavePrint_btn" name="cashBillSavePrint_btn" class="btn btn-success" style="margin-left: -400px; margin-top: 35px;" value="Save" onfocus="cashButtonOnFocus(this);" onblur="cashButtonFocusLost(this);" onclick="checkIsBackDatedCash();"/><%-- saveCashBillOper--%>
            <%--
            <input type="button" id="casBillReprintBtn" name="casBillReprintBtn" class="btn btn-primary" style="margin-top: 35px;" onfocus="cashButtonOnFocus(this);" onblur="cashButtonFocusLost(this);" onclick="document.getElementById('cashBillLight').style.display = 'block';
                    document.getElementById('cashBillFade').style.display = 'block'" value="Re-Print"/>
            --%>
            <%--
            <input type="button" id="casBillReprintBtn" name="casBillReprintBtn"class="btn btn-primary" value="Re-Print" data-toggle="modal" data-target=".bs-modal-sm" style="margin-top: 35px;"/>
            --%>
            <label class="control-label" style="float: right;margin-left: 10px;">Balance
                <input type="text" id="txtBalanceAmt" name="txtBalanceAmt" class="input-medium" readonly="true" style="margin-left: 13px;width: 90px;"/>
            </label>

            <label class="control-label" style="float: right;margin-left: 10px;">Total Amount
                <input type="text" id="txtTotalAmt" name="txtTotalAmt" class="input-medium" readonly="true" style="margin-left: 13px;width: 200px;height: 37px;font-size: 35px;"/>
            </label>

            <label class="control-label" style="float: right;margin-left: 10px;">Coupon Amount
                <input type="text" id="couponTotalAmount" name="couponTotalAmount" class="input-medium" readonly="true" style="margin-left: 13px;width: 90px;"/>
            </label>

            <label class="control-label hideReceiveAmount" style="float: right;">Received Amount
                <input type="text" id="txtReceivedAmt" name="txtReceivedAmt" class="input-medium hideReceiveAmount" onkeyup="calculate_BalanceAmt();" style="margin-left: 13px;width: 90px;"/>
            </label>
        </div>

    </div>
    <%--
<div id="cashBillFade" class="black_overlay"></div>
<div id="cashBillLight" class="white_content" style="height: 12%;margin-top: 5px;">
<form id="cashBillReprintForm" name="cashBillReprintForm" method="post">
    <input type="hidden" id="cashBillhdnReBillNo" name="cashBillhdnReBillNo"/>
    <center><input type="text" id="cashBillNumber" name="cashBillNumber" size="10" style="width: 185px;" placeholder="Enter CashBill OrderNumber" style="margin-left: 50px;"/><br>
        <input type="button" id="cashBillRePrintBtn" name="cashBillRePrintBtn" class="btn btn-primary" value="Print" onfocus="cashButtonOnFocus(this);" onblur="cashButtonFocusLost(this);" onclick="cashBillReprint();" style="margin-left: 10px;margin-top:15px; "/>
        <input type="button" id="cashBillRePrintCancelBtn" name="cashBillRePrintCancelBtn" class="btn btn-danger" value="Cancel" style="margin-top:15px;margin-left: 10px;" onfocus="cashButtonOnFocus(this);" onblur="cashButtonFocusLost(this);" onclick="document.getElementById('cashBillLight').style.display = 'none';
                document.getElementById('cashBillFade').style.display = 'none';" />
    </center>
</form>
</div>
    --%>
    <div class="modal fade bs-modal-sm"  tabindex="-1" role="dialog" aria-labelledby="coustomeModalLabel" aria-hidden="true" style="width: 280px;margin-left: -7em;">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <form id="cashBillReprintForm" name="cashBillReprintForm" method="post">
                    <h2 class="form-signin-heading" style="text-align: center">Reprint 
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </h2>
                    <hr/>
                    <input type="hidden" id="cashBillhdnReBillNo" name="cashBillhdnReBillNo"/>
                    <center>
                        <input type="text" id="cashBillNumber" name="cashBillNumber" size="10" style="width: 185px;" placeholder="Enter CashBill OrderNumber" style="margin-left: 50px;"/><br>
                        <input type="button" id="cashBillRePrintBtn" name="cashBillRePrintBtn" class="btn btn-primary" value="Print" onfocus="cashButtonOnFocus(this);" onblur="cashButtonFocusLost(this);" onclick="cashBillReprint()
                                        ;" data-dismiss="modal" style="margin-left: 10px;margin-top:5px; "/>
                        <input type="button" id="cashBillRePrintCancelBtn" name="cashBillRePrintCancelBtn" class="btn btn-danger" value="Cancel" style="margin-top:10px;margin-left: 3px;" data-dismiss="modal"/>
                        <%--
                        <input type="button" id="cashBillRePrintCancelBtn" name="cashBillRePrintCancelBtn" class="btn btn-danger" value="Cancel" style="margin-top:15px;margin-left: 10px;" onfocus="cashButtonOnFocus(this);" onblur="cashButtonFocusLost(this);" onclick="document.getElementById('cashBillLight').style.display = 'none';
                                document.getElementById('cashBillFade').style.display = 'none';" />
                        --%>
                    </center>
                </form>
            </div>
        </div>
    </div>

    <div class="clearfix currency">
        <div class="btn-group dropup">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                <span class="sr-only"><img src="./images/rupee.png"></span>
            </button>
            <ui class="dropdown-menu positionCurr" role="menu">
                <iframe frameborder="0" scrolling="no" height="150" width="250" allowtransparency="true" marginwidth="0" marginheight="0" src="https://www.google.com/finance/converter"></iframe>
            </ui>
        </div>
 </div>
</form>
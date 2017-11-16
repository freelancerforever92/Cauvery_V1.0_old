var tabClk = 0;
var cRedemTr = 1;
var rowIndex = 1;
var cuntrName;
var vndrName;
var noOfScannedBills = 0;
var totCouponAmount = 0;

/*Mastan   */

var cYear = '';
function setcYearVal(currentYear) {
    cYear = currentYear;
}

$(document).ready(function ()
{
    var now = new Date();
    var curr_datetime = "";
    curr_datetime = now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();
    $('#txtlogo_datetime').text(curr_datetime);
    load_CashBillSalesForm();
    //cashbillOrderNo();
    //cashbill_LoadPaymentType();
});

function userLogout()
{
    $.getJSON('SessionClear.action?curntLoginId=' + $("#txtlog_empid").text(), function (data)
    {
        if (data.forwardPage === "homepage")
        {
            document.location.href = 'index-login.jsp';
            alert("Logout Successfully..");
        }
    });
}

/*GENERATING-CASHBILL-ORDERNUMBER*/
function cashbillOrderNo()
{
//    $.getJSON('cashbillBillNo.action', function(data)
//    {
//        $("#txtCashBillOrderno").text("");
//        $("#txtCashBillOrderno").text(data.cashBillSrlNo);
//    });
}
/*GENERATING-CASHBILL-ORDERNUMBER*/

function load_CashBillSalesForm()
{
    $("#cashBill_ContentDiv").empty();
    //loadimage();
    //$("#cashBill_ContentDiv").load('cashbill_user_jsp/cash-Billing.jsp');
    $("#cashBill_ContentDiv").load('cashbill/cash-Billing.jsp');
}

/*CASH BILL REPORT FUNCTIONS*/
function loadCashBillReportHome()
{
    $("#cashBill_ContentDiv").empty();
    //loadimage();
//    $("#cashBill_ContentDiv").height(($(window).height() - ($(window).height() * 0.25)));
    $("#cashBill_ContentDiv").load('cashbill/cashbill-summary-report-home.jsp');
}

function loadCashBillReport() {
    $("#ReportContentArea").empty();
    $("#ReportContentArea").load('cashbill/cashbill-summary-report.jsp');
    $.getJSON('getCashSummaryTotal.action', function (data)
    {
        $("#cashTotalSumValue").text("");
        $("#cashTotalSumValue").text(Math.round(data.cashBillTotalSum).toFixed(2));

        $("#totalCounterBillValue").text("");
        $("#totalCounterBillValue").text(data.totalCounterBillCountValue);
        $("#hiddenLoginSessionId").val("");
        $("#hiddenLoginSessionId").val(data.loginSessionId);
    });
}

function filterCashBillRecord() {
    var choosedPaymentType = $("#reportPaymentType").find("option:selected").val().trim();
    if (choosedPaymentType !== "Select") {
        if (($("#cbFromDate").val() !== "") && ($("#cbToDate").val() === "")) {
            $("#ReportContentArea").load('cashBillRecordFromTo.action?&cashBillReportButton=' + $("#cashBillReportSearchButton").val().trim(), '?&cbFromDate=' + $("#cbFromDate").val().trim() + '&cbToDate=' + '0000-01-01' + '&reportPaymentType=' + $("#reportPaymentType").find("option:selected").val().trim());
            $.getJSON("getCashSummaryTotal.action?filterCashBillReportButton=" + $("#cashBillReportSearchButton").val().trim(), '?&filterCbFromDate=' + $("#cbFromDate").val().trim() + '&filterCbToDate=' + '0000-01-01' + '&reportPaymentType=' + $("#reportPaymentType").find("option:selected").val().trim(), function (data)
            {
                $("#cashTotalSumValue").text("");
                $("#cashTotalSumValue").text(Math.round(data.cashBillTotalSum).toFixed(2));

                $("#totalCounterBillValue").text("");
                $("#totalCounterBillValue").text(data.totalCounterBillCountValue);
            });
        } else if (($("#cbFromDate").val() === "") && ($("#cbToDate").val() !== "")) {
            $("#ReportContentArea").load('cashBillRecordFromTo.action?&cashBillReportButton=' + $("#cashBillReportSearchButton").val().trim(), '?&cbFromDate=' + '0000-01-01' + '&cbToDate=' + $("#cbToDate").val().trim() + '&reportPaymentType=' + $("#reportPaymentType").find("option:selected").val().trim());
            $.getJSON("getCashSummaryTotal.action?filterCashBillReportButton=" + $("#cashBillReportSearchButton").val().trim(), '?&filterCbFromDate=' + '0000-01-01' + '&filterCbToDate=' + $("#cbToDate").val().trim() + '&reportPaymentType=' + $("#reportPaymentType").find("option:selected").val().trim(), function (data)
            {
                $("#cashTotalSumValue").text("");
                $("#cashTotalSumValue").text(Math.round(data.cashBillTotalSum).toFixed(2));

                $("#totalCounterBillValue").text("");
                $("#totalCounterBillValue").text(data.totalCounterBillCountValue);
            });
        } else if (($("#cbFromDate").val() !== "") && ($("#cbToDate").val() !== "")) {
            $("#ReportContentArea").load('cashBillRecordFromTo.action?&cashBillReportButton=' + $("#cashBillReportSearchButton").val().trim(), '?&cbFromDate=' + $("#cbFromDate").val().trim() + '&cbToDate=' + $("#cbToDate").val().trim() + '&reportPaymentType=' + $("#reportPaymentType").find("option:selected").val().trim());
            $.getJSON("getCashSummaryTotal.action?filterCashBillReportButton=" + $("#cashBillReportSearchButton").val().trim(), '?&filterCbFromDate=' + $("#cbFromDate").val().trim() + '&filterCbToDate=' + $("#cbToDate").val().trim() + '&reportPaymentType=' + $("#reportPaymentType").find("option:selected").val().trim(), function (data)
            {
                $("#cashTotalSumValue").text("");
                $("#cashTotalSumValue").text(Math.round(data.cashBillTotalSum).toFixed(2));

                $("#totalCounterBillValue").text("");
                $("#totalCounterBillValue").text(data.totalCounterBillCountValue);
            });
        } else if (($("#cbFromDate").val() === "") && ($("#cbToDate").val() === "")) {
            alert("Oops!!!,select atleast one date range");
            $("#cbFromDate").focus();
//        $("#ReportContentArea").load('cashBillRecordFromTo.action?&cashBillReportButton=' + $("#cashBillReportSearchButton").val(), '?&cbFromDate=' + '0000-01-01' + '&cbToDate=' + '0000-01-01' + '&reportPaymentType=' + $("#reportPaymentType").find("option:selected").val().trim());
//        $.getJSON("getCashSummaryTotal.action?filterCashBillReportButton=" + $("#cashBillReportSearchButton").val(), '?&filterCbFromDate=' + $("#cbFromDate").val().trim(), function(data)
//        {
//            $("#cashTotalSumValue").text("");
//            $("#cashTotalSumValue").text(Math.round(data.cashBillTotalSum).toFixed(2));
//
//            $("#totalCounterBillValue").text("");
//            $("#totalCounterBillValue").text(data.totalCounterBillCountValue);
//        });
        }
    } else {
        alert("Oops!!!,select payment type");
        $("#reportPaymentType").focus();
    }


    /*
     if ($("#cbFromDate").val() !== "") {
     //$("#ReportContentArea").load('cashBillRecordFromTo.action?&cashBillReportButton=' + $("#cashBillReportSearchButton").val(), '?&cbFromDate=' + $("#cbFromDate").val().trim() + '&cbToDate=' + $("#cbToDate").val().trim());
     $("#ReportContentArea").load('cashBillRecordFromTo.action?&cashBillReportButton=' + $("#cashBillReportSearchButton").val(), '?&cbFromDate=' + $("#cbFromDate").val().trim() + '&cbToDate=' + $("#cbToDate").val().trim() + '&reportPaymentType=' + $("#reportPaymentType").find("option:selected").val().trim());
     //?filterButtonType=" + $("#counterReportSearchButton").val().trim(), '?&filterFromDate=' + $("#counterFromDate").val().trim()
     $.getJSON("getCashSummaryTotal.action?filterCashBillReportButton=" + $("#cashBillReportSearchButton").val(), '?&filterCbFromDate=' + $("#cbFromDate").val().trim(), function(data)
     {
     $("#cashTotalSumValue").text("");
     $("#cashTotalSumValue").text(Math.round(data.cashBillTotalSum).toFixed(2));
     
     $("#totalCounterBillValue").text("");
     $("#totalCounterBillValue").text(data.totalCounterBillCountValue);
     });
     }
     else {
     alert("Please select from date");
     }
     */
}

function exportDailyReport()
{
    var paymentType = $("#reportPaymentType").find("option:selected").val().trim();
    if (paymentType !== "Select") {
        $.getJSON('dailyCashReport.action', $("#CasherwiseAccordionForm").serialize(), function (data) {
            if (data.rtn == 1) {
                window.open("Downloads/" + data.xlFileName);
                //alert("Data exported successfully");
                $("#cbFromDate").val("");
                $("#cbToDate").val("");
                $("#reportPaymentType").val("Select");
            } else {
                alert("No records to display");
            }
        });
    } else {
        alert("Oops!!!,Please select payment type");
    }
}

function enableDisablePaymentType() {
    var selectedReportType = $("#reportType").find("option:selected").val().trim();
    if (selectedReportType !== "Select") {
        switch (selectedReportType) {
            case'CWSR':
                $("#reportPaymentType").attr("disabled", false);
                $("#reportPaymentType").val("Select");
                $("#displaySalesIntimation").hide();
                $("#vendorsIdNumber").hide();
                $("#craftGroups").hide();
                $("#discountPer").val("");
                $("#discPer").hide();
                /*TIME_BASED_REPORT */
                /*$("#normalfromdate").show();
                 $("#normaltodate").show();
                 $("#tbrfromdate").hide();
                 $("#tbrtodate").hide();
                 */
                break;
            case 'DCSR' :
                $("#reportPaymentType").attr("disabled", false);
                $("#reportPaymentType").val("Select");
                //$("#vendorsIdNumber").show();/*UPDATED - PRANESH - 14-12-2015 */
                $("#vendorsIdNumber").hide();
                $("#craftGroups").hide();
                $("#discountPer").val("");
                $("#discPer").hide();
                $("#displaySalesIntimation").hide();
                /*TIME_BASED_REPORT */
                /*
                 $("#normalfromdate").show();
                 $("#normaltodate").show();
                 $("#tbrfromdate").hide();
                 $("#tbrtodate").hide();
                 */
                break;
            case 'DCSRA' :
                $("#reportPaymentType").attr("disabled", false);
                $("#reportPaymentType").val("Select");
                $("#displaySalesIntimation").hide();
                $("#vendorsIdNumber").hide();
                $("#craftGroups").show();
                $("#discountPer").val("");
                $("#discPer").hide();
                /*TIME_BASED_REPORT */
                /*
                 $("#normalfromdate").show();
                 $("#normaltodate").show();
                 $("#tbrfromdate").hide();
                 $("#tbrtodate").hide();
                 */
                break;
            case 'DCSRAE' :
                $("#reportPaymentType").attr("disabled", false);
                $("#reportPaymentType").val("Select");
                $("#displaySalesIntimation").hide();
                $("#vendorsIdNumber").hide();
                $("#craftGroups").hide();
                $("#discountPer").val("");
                $("#discPer").hide();
                /*TIME_BASED_REPORT */
                /*
                 $("#normalfromdate").show();
                 $("#normaltodate").show();
                 $("#tbrfromdate").hide();
                 $("#tbrtodate").hide();
                 */
                break;
            case 'UCB' :
                $("#reportPaymentType").attr("disabled", true);
                $("#reportPaymentType").val("Select");
                $("#displaySalesIntimation").hide();
                $("#vendorsIdNumber").hide();
                $("#craftGroups").hide();
                $("#discountPer").val("");
                $("#discPer").hide();
                /*TIME_BASED_REPORT */
                /*
                 $("#normalfromdate").show();
                 $("#normaltodate").show();
                 $("#tbrfromdate").hide();
                 $("#tbrtodate").hide();
                 */
                break;
            case 'SIA' :
                //$("#crfgrp").attr("disabled", false);
                $("#reportPaymentType").attr("disabled", false);
                $("#reportPaymentType").val("Select");
                $("#displaySalesIntimation").show();
                $("#reportPaymentType").show();
                $("#vendorsIdNumber").show();
                $("#craftGroups").show();
                $("#discountPer").val("");
                $("#discPer").hide();
                /*TIME_BASED_REPORT */
                /*
                 $("#normalfromdate").show();
                 $("#normaltodate").show();
                 $("#tbrfromdate").hide();
                 $("#tbrtodate").hide();
                 */

                break;
            case 'SID' :
                //$("#crfgrp").attr("disabled", false);
                $("#reportPaymentType").attr("disabled", false);
                $("#reportPaymentType").val("Select");
                $("#displaySalesIntimation").show();
                $("#reportPaymentType").show();
                $("#vendorsIdNumber").show();
                $("#craftGroups").show();
                $("#discountPer").val("");
                $("#discPer").hide();
                /*TIME_BASED_REPORT */
                /*
                 $("#normalfromdate").show();
                 $("#normaltodate").show();
                 $("#tbrfromdate").hide();
                 $("#tbrtodate").hide();
                 */

                break;
            case 'SIS' :
                //$("#crfgrp").attr("disabled", false);
                $("#reportPaymentType").attr("disabled", false);
                $("#reportPaymentType").val("Select");
                $("#displaySalesIntimation").show();
                $("#reportPaymentType").show();
                $("#vendorsIdNumber").show();
                $("#craftGroups").show();
                $("#discountPer").val("");
                $("#discPer").hide();
                /*TIME_BASED_REPORT */
                /*
                 $("#normalfromdate").show();
                 $("#normaltodate").show();
                 $("#tbrfromdate").hide();
                 $("#tbrtodate").hide();
                 */

                break;
            case 'DCW':
                $("#reportPaymentType").attr("disabled", false);
                $("#reportPaymentType").val("Select");
                $("#displaySalesIntimation").hide();
                $("#reportPaymentType").show();
                $("#vendorsIdNumber").hide();
                $("#craftGroups").show();
                $("#discPer").hide();
                $("#discountPer").val("");
                /*TIME_BASED_REPORT */
                /*
                 $("#normalfromdate").show();
                 $("#normaltodate").show();
                 $("#tbrfromdate").hide();
                 $("#tbrtodate").hide();
                 */

                break;
            case 'DPR' :
                $("#reportPaymentType").attr("disabled", false);
                $("#reportPaymentType").val("Select");
                $("#displaySalesIntimation").show();
                $("#reportPaymentType").show();
                $("#vendorsIdNumber").show();
                $("#craftGroups").show();
                $("#discPer").show();
                $("#discountPer").val("10");
                /*TIME_BASED_REPORT */
                /*
                 $("#normalfromdate").show();
                 $("#normaltodate").show();
                 $("#tbrfromdate").hide();
                 $("#tbrtodate").hide();
                 */

                break;
            case 'TBR' :
                $("#normalfromdate").hide();
                $("#normaltodate").hide();
                $("#tbrfromdate").show();
                $("#tbrtodate").show();
                $("#reportPaymentType").attr("disabled", false);
                $("#displaySalesIntimation").hide();
                $("#vendorsIdNumber").hide();
                $("#craftGroups").hide();
                $("#discountPer").val("");
                $("#discPer").hide();
                /*TIME_BASED_REPORT */
                /*
                 $("#normalfromdate").hide();
                 $("#normaltodate").hide();
                 $("#tbrfromdate").show();
                 $("#tbrtodate").show();
                 */

                break;
                
                  case 'ADSR' :
                $("#reportPaymentType").attr("disabled", false);
                $("#reportPaymentType").val("Select");
                $("#displaySalesIntimation").hide();
                $("#vendorsIdNumber").hide();
                $("#craftGroups").hide();
                $("#discountPer").val("");
                $("#discPer").hide();
                /*TIME_BASED_REPORT */
                /*
                 $("#normalfromdate").show();
                 $("#normaltodate").show();
                 $("#tbrfromdate").hide();
                 $("#tbrtodate").hide();
                 */
                break;
                
        }
    } else {
        $("#displaySalesIntimation").hide();
        $("#vendorsIdNumber").hide();
        $("#craftGroups").hide();
        clearOtherOptions();
    }
}
function clearOtherOptions() {
    $("#vendorNumbers").empty();
    $("#selSalesIntimationType").val("0");
    $("#reportPaymentType").val("Select");
    $("#displayVendorList").text("Select");
    $("#displayCraftGroup").text("Select");
    $(".craftEnableDisable").attr('checked', false);
    document.getElementById('rptCrfGrpLst').style.display = "none";
    document.getElementById('vendorNumbers').style.display = "none";
}

function enableDisableCraftAll() {
    if ($("#selectAllCraft").is(":checked")) {
        $(".craftEnableDisable").attr('checked', false);
        $(".craftEnableDisable").attr("disabled", true);
    } else {
        $(".craftEnableDisable").attr("disabled", false);
    }
}

function loadingRptCraftGroup() {
    $.getJSON('getCraftGroups.action', function (data)
    {
        var craftIndex = 1;
        $("#rptCrfGrpLst").empty();
        $("#rptCrfGrpLst").append($("<label><input type='checkbox' class='counterNames' id='selectAllCraft' onchange='getSelectedCounterValue();' onclick='enableDisableCraftAll();'  value='All' />All</label>"));
        $.each(data.craftGrpName, function (i, item) {
            $("#rptCrfGrpLst").append($("<label><input type='checkbox' class='counterNames craftEnableDisable' onchange='getSelectedCounterValue();' value=" + item.craftGroupId + " id='craftName" + craftIndex + "'>" + '  ' + item.craftGroupDescription + "</label>"));
            craftIndex++;
        });
    });
}

function getSelectedCounterValue() {
    cuntrName = "";
    if ($("input[type='checkbox']:checked.counterNames").text() === 'All') {
        cuntrName = 'All';
    } else {
        cuntrName = $("input[type='checkbox']:checked.counterNames").map(function () {
            return this.value;
        }).get().join("'" + "," + "'");
    }
    $("#displayCraftGroup").text(cuntrName);
}

function enableDisableVendorAll() {
    if ($("#selectAll").is(":checked")) {
        $(".disableVendor").attr('checked', false);
        $(".disableVendor").attr("disabled", true);
    } else {
        $(".disableVendor").attr("disabled", false);
    }
}

function showHideVendorNos() {
    var toDate = $("#cbToDate").val().trim();
    var fromDate = $("#cbFromDate").val().trim();
    var selReportType = $("#reportType").find("option:selected").val().trim();
    var selVendorType = $("#selSalesIntimationType").find("option:selected").val().trim();
    if (selReportType !== "Select") {
        if (selReportType == "DCSR" || selReportType == "DCSRA" || selReportType == "DCSRAE" || selReportType == "SIA" || selReportType == "SID" || selReportType == "SIS" || selReportType == "DCW" || selReportType == "DPR" || selReportType == "ADSR") {
            if (selVendorType !== 0) {
                $.getJSON("getVendorNos.action?selectedVendorType=" + selVendorType, '?&dcsrToDate=' + toDate + '&dcsrFromDate=' + fromDate, function (data)
                {
                    var vndrNoIndex = 1;
                    $("#vendorNumbers").empty();
                    $("#vendorNumbers").append($("<label><input type='checkbox' class='vendorId' id='selectAll' onchange='getSelectedVendors();' onclick='enableDisableVendorAll();'  value='All' />All</label>"));
                    $.each(data.vendorNumbers, function (i, item)
                    {
                        $("#vendorNumbers").append($("<label><input type='checkbox' class='vendorId disableVendor' onchange='getSelectedVendors();' value=" + item.vendorIds + " id='vndrNo" + vndrNoIndex + "'>" + '  ' + item.vendorIdName + "</label>"));
                        vndrNoIndex++;
                    });
                });
            }
        }
    }
}
function getSelectedVendors() {
    vndrName = "";
    if ($("input[type='checkbox']:checked.vendorId").text() === 'All') {
        vndrName = 'All';
    } else {
        vndrName = $("input[type='checkbox']:checked.vendorId").map(function () {
            return this.value;
        }).get().join("'" + "," + "'");
    }
    $("#displayVendorList").text(vndrName);
}
function viewCBDR() {
    var choosedToDate = $("#cbToDate").val().trim();
    var choosedFromDate = $("#cbFromDate").val().trim();
    var discountPersentage = $("#discountPer").val().trim();
    var loginSessionId = $("#hiddenLoginSessionId").val().trim();
    var choosedReportType = $("#reportType").find("option:selected").val().trim();
    var choosedPaymentType = $("#reportPaymentType").find("option:selected").val().trim();
    var choosedIntimationType = $("#selSalesIntimationType").find("option:selected").val().trim();
    var choosedIntimationTypeText = $("#selSalesIntimationType").find("option:selected").text().trim();
    var intimationText = choosedIntimationTypeText.split('-');
    intimationText = intimationText[0];

    if (choosedFromDate !== "") {
        if (choosedToDate !== "") {
            //if (choosedPaymentType !== "Select"){
            if (choosedReportType !== "Select") {
                if (choosedReportType === "CWSR") {
                    if (choosedPaymentType !== "Select") {
                        if (loginSessionId !== "" || loginSessionId !== null) {
                            $("#userProgress").show();
                            $("#ReportContentArea").empty();//removing the page which is already placed in tat div.
                            //$("#ReportContentArea").load('cashbill/cashbill-summary-daily-report-view.jsp);//loading cashbill-summary-daily-report-view.jsp page into the div
                            $("#ReportContentArea").load('cashbill/cashbill-summary-daily-report-view.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim());//loading cashbill-summary-daily-report-view.jsp page into the div
                            $.getJSON("cbdrTotal.action?cbFromDate=" + choosedFromDate, '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim(), function (data)
                            {
                                $("#cashTotalSumValue").text("");
                                $("#cashTotalSumValue").text(data.cwdsTotAmount.toFixed(2));
                                $("#totalCounterBillValue").text("");
                                $("#totalCounterBillValue").text(data.cwdsTotBillCount);
                            });
                        }
                    } else {
                        alert("Oops,Please select payment type");
                        $("#reportPaymentType").focus();
                    }
                } else if (choosedReportType === "DCSR") {
                    if (choosedPaymentType !== "Select") {
                        if (loginSessionId !== "" || loginSessionId !== null) {
                            vndrName = "All";/*UPDATED - PRANESH 14-12-2015 ( INORDER TO BLOCK SELECTING INDIVIDAUL VENDOR,ITS MARKED AS all BY DEFAULT */
                            /*$("#userProgress").show();
                             $("#ReportContentArea").empty();//removing the page which is already placed in tat div.
                             $("#ReportContentArea").load('cashbill/cashbill-summary-daily-report-dcsr.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim() + '&vendorIds=' + vndrName.trim() + '&vendorType=' + choosedIntimationType.trim());//loading cashbill-summary-daily-report-view.jsp page into the div
                             */
                            /*BLOCKED DUE TO PERFORMANCE DELAY.REVERTING TO OLD PROCESS */
                            $("#userProgress").show();
                            $("#ReportContentArea").empty();//removing the page which is already placed in tat div.
                            $("#ReportContentArea").load('cashbill/cashbill-summary-daily-report-dcsr.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim());//loading cashbill-summary-daily-report-view.jsp page into the div
                        }
                    } else {
                        alert("Oops,Please select payment type");
                        $("#reportPaymentType").focus();
                    }
                } else if (choosedReportType === "DCSRA") {
                    if (choosedPaymentType !== "Select") {
                        if (loginSessionId !== "" || loginSessionId !== null) {
                            var SplitedFromDate = choosedFromDate.split('-');
                            var SplitedToDate = choosedToDate.split('-');
                            if (SplitedFromDate[0] == SplitedToDate[0]) {
                                if (SplitedFromDate[1] == SplitedToDate[1]) {
                                    $("#userProgress").show();
                                    $("#ReportContentArea").empty();//removing the page which is already placed in tat div.
                                    $("#ReportContentArea").load('cashbill/cashbill-summary-daily-report-dcsr-abstract.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim()+'&craftGroups='+cuntrName.trim());//loading cashbill-summary-daily-report-view.jsp page into the div

                                } else {
                                    alert("Select Correct Month..!");
                                    $("#reportPaymentType").focus();
                                }
                            } else {
                                alert("Select Correct Year...!");
                                $("#reportPaymentType").focus();
                            }
                        }
                    } else {
                        alert("Oops,Please select payment type");
                        $("#reportPaymentType").focus();
                    }
                } else if (choosedReportType === "DCSRAE") {
                    if (choosedPaymentType !== "Select") {
                        if (loginSessionId !== "" || loginSessionId !== null) {
                            var SplitedFromDate = choosedFromDate.split('-');
                            var SplitedToDate = choosedToDate.split('-');
                            if (SplitedFromDate[0] == SplitedToDate[0]) {
                                if (SplitedFromDate[1] == SplitedToDate[1]) {
                                    $("#userProgress").show();
                                    $("#ReportContentArea").empty();//removing the page which is already placed in tat div.
                                    $("#ReportContentArea").load('cashbill/cashbill-summary-daily-report-dcsr-abstract-emp.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim());//loading cashbill-summary-daily-report-view.jsp page into the div
                                } else {
                                    alert("Select Correct Month..!");
                                    $("#reportPaymentType").focus();
                                }
                            } else {
                                alert("Select Correct Year...!");
                                $("#reportPaymentType").focus();
                            }
                        }
                    } else {
                        alert("Oops,Please select payment type");
                        $("#reportPaymentType").focus();
                    }
                } else if (choosedReportType === "UCB") {
                    if (loginSessionId !== "" || loginSessionId !== null) {
                        $("#userProgress").show();
                        $("#ReportContentArea").empty();//removing the page which is already placed in tat div.
                        $("#ReportContentArea").load('cashbill/cashbill-unprocessed-invoices.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim());//loading cashbill-unprocessed-invoices.jsp page into the div
                    }
                } else if (choosedReportType === "SIA") {
                    if (loginSessionId !== "" || loginSessionId !== null) {
                        if ($("#selSalesIntimationType").val() !== "Select") {
                            if (choosedPaymentType !== "Select") {
                                $("#userProgress").show();
                                $("#ReportContentArea").empty();
                                $("#ReportContentArea").load('cashbill/salesitimation-abstract-report.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim() + '&typeValue=' + choosedIntimationType.trim() + "&intimationName=" + intimationText.trim() + '&craftGroups=' + cuntrName.trim() + '&vendorIds=' + vndrName.trim() + '&vendorType=' + choosedIntimationType.trim());
                            } else {
                                alert("Oops,Please select payment type");
                                $("#reportPaymentType").focus();
                            }
                        } else {
                            alert("Oops,Select intimation type..!");
                            $("#selSalesIntimationType").focus();
                        }
                    }
                } else if (choosedReportType === "SID") {
                    if (loginSessionId !== "" || loginSessionId !== null) {
                        if ($("#selSalesIntimationType").val() !== "Select") {
                            if (choosedPaymentType !== "Select") {
                                $("#userProgress").show();
                                $("#ReportContentArea").empty();
                                $("#ReportContentArea").load('cashbill/salesitimation-detailed-report.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim() + '&typeValue=' + choosedIntimationType.trim() + "&intimationName=" + intimationText.trim() + '&craftGroups=' + cuntrName.trim() + '&vendorIds=' + vndrName.trim() + '&vendorType=' + choosedIntimationType.trim());
                            } else {
                                alert("Oops,Please select payment type");
                                $("#reportPaymentType").focus();
                            }
                        } else {
                            alert("Oops,Select required intimation type..!");
                            $("#selSalesIntimationType").focus();
                        }
                    }
                } else if (choosedReportType === "SIS") {
                    if (loginSessionId !== "" || loginSessionId !== null) {
                        if ($("#selSalesIntimationType").val() !== "Select") {
                            if (choosedPaymentType !== "Select") {
                                $("#userProgress").show();
                                $("#ReportContentArea").empty();
                                $("#ReportContentArea").load('cashbill/saleitimation-summary-report.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim() + '&typeValue=' + choosedIntimationType.trim() + "&intimationName=" + intimationText.trim() + '&craftGroups=' + cuntrName.trim() + '&vendorIds=' + vndrName.trim() + '&vendorType=' + choosedIntimationType.trim());
                            } else {
                                alert("Oops,Please select payment type");
                                $("#reportPaymentType").focus();
                            }
                        } else {
                            alert("Oops,Select required intimation type..!");
                            $("#selSalesIntimationType").focus();
                        }
                    }
                } else if (choosedReportType === "DCW") {
                    if (loginSessionId !== "" || loginSessionId !== null) {
                        if ($("#selSalesIntimationType").val() !== "Select") {
                            if (choosedPaymentType !== "Select") {
                                $("#userProgress").show();
                                $("#ReportContentArea").empty();
                                //$("#ReportContentArea").load('cashbill/dcsr-counter-wise-report.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim() + '&typeValue=' + choosedIntimationType.trim() + "&intimationName=" + intimationText.trim() + '&craftGroups=' + cuntrName.trim() + '&vendorIds=' + vndrName.trim() + '&vendorType=' + choosedIntimationType.trim());
                                $("#ReportContentArea").load('cashbill/dcsr-counter-wise-report.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim()   + '&craftGroups=' + cuntrName.trim() );
                            } else {
                                alert("Oops,Please select payment type");
                                $("#reportPaymentType").focus();
                            }
                        } else {
                            alert("Oops,Select required intimation type..!");
                            $("#selSalesIntimationType").focus();
                        }
                    }
                } else if (choosedReportType === "DPR") {
                    if (loginSessionId !== "" || loginSessionId !== null) {
                        if (discountPersentage == "") {
                            discountPersentage = "0";
                        } else if (discountPersentage > 0) {
                            $("#userProgress").show();
                            $("#ReportContentArea").empty();
                            $("#ReportContentArea").load('cashbill/discount-persentage-report.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim() + '&typeValue=' + choosedIntimationType.trim() + "&intimationName=" + intimationText.trim() + '&craftGroups=' + cuntrName.trim() + '&vendorIds=' + vndrName.trim() + '&vendorType=' + choosedIntimationType.trim() + '&discountPersentage=' + discountPersentage.trim());
                        } else {
                            alert("Plz Enter Correct Value..!");
                            $("#discountPer").focus();
                        }
                    }
                }
                /*TIME_BASED_REPORT */
                /*else if (choosedReportType === "TBR") {
                 choosedFromDate = $("#cbFromDateTimeMin").val().trim();
                 choosedToDate = $("#cbToDateTimeMin").val().trim();
                 alert(choosedToDate + "===>" + choosedFromDate);
                 if (loginSessionId !== "" || loginSessionId !== null) {
                 if (choosedPaymentType !== "Select") {
                 $("#userProgress").show();
                 $("#ReportContentArea").empty();
                 $("#ReportContentArea").load('cashbill/cashbill-time-based-report.jsp?reportPaymentType=' + choosedPaymentType.trim(), '&cbToDate=' + choosedToDate.trim() + '&cbFromDate=' + choosedFromDate.trim() + '&loggedSessionId=' + loginSessionId.trim());
                 } else {
                 alert("Oops,Please select payment type");
                 $("#reportPaymentType").focus();
                 }
                 }
                 }*/
                else if (choosedReportType === "ADSR") {
                    if (choosedPaymentType !== "Select") {
                        if (loginSessionId !== "" || loginSessionId !== null) {
                            var SplitedFromDate = choosedFromDate.split('-');
                            var SplitedToDate = choosedToDate.split('-');
                            if (SplitedFromDate[0] == SplitedToDate[0]) {
                                if (SplitedFromDate[1] == SplitedToDate[1]) {
                                    $("#userProgress").show();
                                    $("#ReportContentArea").empty();//removing the page which is already placed in tat div.
                                    $("#ReportContentArea").load('cashbill/cashbill-daily-sales-abstract.jsp?cbFromDate=' + choosedFromDate.trim(), '?&cbToDate=' + choosedToDate.trim() + '&reportPaymentType=' + choosedPaymentType.trim() + '&loggedSessionId=' + loginSessionId.trim());//loading cashbill-summary-daily-report-view.jsp page into the div

                                } else {
                                    alert("Select Correct Month..!");
                                    $("#reportPaymentType").focus();
                                }
                            } else {
                                alert("Select Correct Year...!");
                                $("#reportPaymentType").focus();
                            }
                        }
                    } else {
                        alert("Oops,Please select payment type");
                        $("#reportPaymentType").focus();
                    }
                }
                
                
                
            } else {
                alert("Oops,Please Report type");
                $("#reportType").focus();
            }
            /*} 
             else {
             alert("Oops,Please select payment type");
             $("#reportPaymentType").focus();
             }*/
        } else {
            alert("Oops,Please select to date");
            $("#cbToDate").focus();
        }
    } else {
        alert("Oops,Please select from date");
        $("#cbFromDate").focus();
    }
}

function backCWSR() {
    $("#ReportContentArea").load('cashbill/cashbill-summary-report.jsp');
}

/*CASH BILL REPORT FUNCTIONS*/
function loadSalesReturnForm()
{
    $("#cashBill_ContentDiv").empty();
    $("#cashBill_ContentDiv").load('cashbill/cashbill-return.jsp');
}

function loadCreateCouponPage()
{
    $("#cashBill_ContentDiv").empty();
    $("#cashBill_ContentDiv").load('coupon_creation/coupon-creation.jsp');
}

function loadCouponSalesPage()
{
    $("#cashBill_ContentDiv").empty();
    $("#cashBill_ContentDiv").load('coupon_creation/coupon-sales.jsp');
    couponSalesOrdeNo();
}

function loadReportTypes() {
    $.getJSON('loadingReportTypes.action', function (data)
    {
        $("#reportType").empty();
        $.each(data.reportTypesTo, function (i, item)
        {
            $("#reportType").append($("<option></option>").attr("value", item.aliasReportName).text(item.reportName));
        });
    });
}

function loadSalesIntimationTypes() {
    $.getJSON('loadSalesIntimationTypes.action', function (data)
    {
        $("#selSalesIntimationType").empty();
        $.each(data.intimationTypesTo, function (i, item)
        {
            $("#selSalesIntimationType").append($("<option></option>").attr("value", item.reportAliasName).text(item.reportName));
        });
    });
}

function loadChangePayType() {
    $("#cashBill_ContentDiv").load('cashbill/cashbill-change-pay-type.jsp');
}

function cashbill_LoadPaymentType()
{
    $.getJSON('paymentType.action', function (data)
    {
        $("#cashBillPaymentType").empty();
        $.each(data.ptts, function (i, item)
        {
            $("#cashBillPaymentType").append($("<option></option>").attr("value", item.selectedPaymentTypeValue).text(item.paytypeText));
        });
    });
}

function cashbill_LoadCouponTypes()
{
    $.getJSON('couponTypesList.action', function (data)
    {
        $("#cashbillCouponType").empty();
        $.each(data.couponTypeList, function (i, item)
        {
            $("#cashbillCouponType").append($("<option></option>").attr("value", item.couponId).text(item.couponType));
        });
    });
}

function show_CashTypes()
{
    var totalCashBillAmount = $("#txtTotalAmt").val().trim();
    var receivedCashAmount = $("#txtReceivedAmt").val().trim();
    var selectedPayType = $("#cashBillPaymentType").find("option:selected").text();
    if (selectedPayType !== "Select")
    {
        clearCreditCardValues();
        switch (selectedPayType)
        {
            case 'CASH' :
                $(".otherCurrency").hide();
                $(".paymentType_td").hide();
                $(".hideReceiveAmount").show();
                $("#otherCurrencyValue").val("");
                $("#calculatedINRValue").val("")
//                $("#txtBalanceAmt").val("");
//                $("#txtBalanceAmt").val("0.0");
                clearPaymentValues();
                if (totalCashBillAmount !== "") {
                    $("#txtCashPayment").val("");
                    $("#txtCashPayment").val(Math.round(totalCashBillAmount).toFixed(2));
                } else {
                    alert("Oops!!!,InvoiceNumber is not processed..");
                }

                if (receivedCashAmount !== "" && receivedCashAmount > 0) {
                    if (totalCashBillAmount !== "" && totalCashBillAmount > 0) {
                        if (receivedCashAmount > totalCashBillAmount) {
                            $("#txtBalanceAmt").val("");
                            $("#txtBalanceAmt").val(Math.round(receivedCashAmount - totalCashBillAmount).toFixed(2));
                        } else {
                            alert("Oops!!!,Received amount is not enoungh for processing...");
                            $("#txtReceivedAmt").focus();
                            $("#txtReceivedAmt").select();
                        }
                    }
                } else {
                    $("#txtBalanceAmt").val("");
                    $("#txtBalanceAmt").val("0.0");
                }

                //$("#txtCashPayment").attr("disabled", false);
                $("#currencyType").val("select");
                $("#txtCashPayment").attr("disabled", "disabled");
                $("#txtCardPayment").attr("disabled", "disabled");
                $("#txtForeginCurrency").attr("disabled", "disabled");
                break;
            case 'CREDIT CARD' :
                $(".otherCurrency").hide();
                $(".paymentType_td").show();
                $(".hideReceiveAmount").show();
                $("#otherCurrencyValue").val("");
                $("#calculatedINRValue").val("")
                clearPaymentValues();

                if (totalCashBillAmount !== "") {
                    $("#txtCashPayment").val("");
                    $("#txtCardPayment").val(Math.round(totalCashBillAmount).toFixed(2));
                } else {
                    alert("Oops!,InvoiceNumber is not processed..");
                }

                if (receivedCashAmount !== "" && receivedCashAmount > 0) {
                    if (totalCashBillAmount !== "" && totalCashBillAmount > 0) {
                        if (receivedCashAmount > totalCashBillAmount) {
                            $("#txtBalanceAmt").val("");
                            $("#txtBalanceAmt").val(Math.round(receivedCashAmount - totalCashBillAmount).toFixed(2));
                        } else {
                            alert("Oops!!!,Received amount is not enoungh for processing...");
                            $("#txtReceivedAmt").focus();
                            $("#txtReceivedAmt").select();
                        }
                    }
                } else {
                    $("#txtBalanceAmt").val("");
                    $("#txtBalanceAmt").val("0.0");
                }

                $("#currencyType").val("select");
                $("#txtCashPayment").attr("disabled", "disabled");
                //$("#txtCardPayment").attr("disabled", false);
                $("#txtForeginCurrency").attr("disabled", "disabled");
                break;
            case 'FOREIGN CURRENCY' :
                $(".otherCurrency").show();
                $(".paymentType_td").hide();
                $("#txtReceivedAmt").val("");
                $(".hideReceiveAmount").hide();
                $("#otherCurrencyValue").val("");
                $("#calculatedINRValue").val("")
                clearPaymentValues();
                $("#txtCashPayment").attr("disabled", "disabled");
                $("#txtCardPayment").attr("disabled", "disabled");
                $("#txtBalanceAmt").val("0.0");
                //$("#otherCurrencyValue").attr("disabled", false);
                break;
            case 'Travellers Cheque' :
                $(".otherCurrency").hide();
                $(".paymentType_td").hide();
                $(".hideReceiveAmount").show();
                $("#otherCurrencyValue").val("");
                $("#calculatedINRValue").val("")
                break;
            case 'Gain From Coupon' :
                $(".otherCurrency").hide();
                $(".paymentType_td").hide();
                $(".hideReceiveAmount").show();
                $("#otherCurrencyValue").val("");
                $("#calculatedINRValue").val("")
                break;
            default:
                $(".otherCurrency").hide();
                $(".paymentType_td").hide();
                $(".hideReceiveAmount").show();
                $("#otherCurrencyValue").val("");
                $("#calculatedINRValue").val("")
                break;
        }
    } else
    {
        clearCreditCardValues();
        $(".otherCurrency").hide();
        $(".paymentType_td").hide();
        clearPaymentValues();
        $("#txtCashPayment").attr("disabled", "disabled");
        $("#txtCardPayment").attr("disabled", "disabled");
//        $("#otherCurrencyValue").attr("disabled", "disabled");
//        $("#txtForeginCurrency").attr("disabled", "disabled");
    }
}

function selectOptionCurrencyType()
{
    var selectedCurrency = $("#currencyType").find("option:selected").text();
    if (selectedCurrency !== "Select")
    {
        $("#txtForeginCurrency").val("");
        $("#otherCurrencyValue").val("");
        $("#calculatedINRValue").val("0.0");
        $("#txtForeginCurrency").attr("disabled", false);
        $("#otherCurrencyValue").attr("disabled", false);
    } else
    {
        $("#txtForeginCurrency").attr("disabled", "disabled");
        $("#calculatedINRValue").attr("disabled", "disabled");
    }
}

function calculateInr()
{
    var cashBillTotalAmount = $("#txtTotalAmt").val().trim();
    var foreginCurrencyValu = $("#txtForeginCurrency").val();
    var otherCurremcyValue = $("#otherCurrencyValue").val();
    if (foreginCurrencyValu !== "")
    {
        if (otherCurremcyValue !== "")
        {
            if (otherCurremcyValue > 0)
            {
                var summedInrValue = Math.round(parseFloat(foreginCurrencyValu) * parseFloat(otherCurremcyValue));
                //$("#calculatedINRValue").val(summedInrValue.toFixed(2));
                if (summedInrValue > 0) {
                    $("#calculatedINRValue").val(summedInrValue.toFixed(2));
                    if (summedInrValue > cashBillTotalAmount) {
                        var balTotalForeign = Math.round(parseInt(summedInrValue) - parseInt(cashBillTotalAmount)).toFixed(2);
                        if (balTotalForeign > 0) {
                            $("#txtBalanceAmt").val("");
                            $("#txtBalanceAmt").val(balTotalForeign);
                        } else {
                            $("#txtBalanceAmt").val("");
                            $("#txtBalanceAmt").val("0.0");
                        }
                    } else {
                        $("#txtBalanceAmt").val("");
                        $("#txtBalanceAmt").val("0.0");
                    }
                } else {
                    $("#calculatedINRValue").val("0.0");
                }
                document.getElementById('txtForeginCurrency').style.borderColor = "";
            }
        } else
        {
            $("#calculatedINRValue").val("");
            $("#calculatedINRValue").val("0.0");
        }
    } else
    {
        $("#calculatedINRValue").val("");
        $("#calculatedINRValue").val("0.0");
    }
}

function calculatedInrValue()
{
    var cashBillTotalAmount = $("#txtTotalAmt").val().trim();
    var foreginValue = $("#txtForeginCurrency").val();
    var otherCurrencyValue = $("#otherCurrencyValue").val();
    if (otherCurrencyValue !== "") {
        if (otherCurrencyValue > 0) {
            if (foreginValue !== "") {
                if (foreginValue > 0) {
                    $("#calculatedINRValue").val("");
                    var totalInrValue = Math.round(parseFloat(foreginValue) * parseFloat(otherCurrencyValue));
                    if (totalInrValue > 0) {
                        $("#calculatedINRValue").val(totalInrValue.toFixed(2));
                        if (totalInrValue > cashBillTotalAmount) {
                            var balTotalForeign = Math.round(parseInt(totalInrValue) - parseInt(cashBillTotalAmount)).toFixed(2);
                            if (balTotalForeign > 0) {
                                $("#txtBalanceAmt").val("");
                                $("#txtBalanceAmt").val(balTotalForeign);
                            } else {
                                $("#txtBalanceAmt").val("");
                                $("#txtBalanceAmt").val("0.0");
                            }
                        } else {
                            $("#txtBalanceAmt").val("");
                            $("#txtBalanceAmt").val("0.0");
                        }
                    } else {
                        $("#calculatedINRValue").val("0.0");
                    }
                    document.getElementById('txtForeginCurrency').style.borderColor = "";
                } else {
                    $("#txtForeginCurrency").val("");
                    $("#txtForeginCurrency").focus();
                    alert("Oops!Value Can't Be LessThan Zero")
                    document.getElementById('txtForeginCurrency').style.borderColor = "red";
                }
            }
        } else {
            $("#otherCurrencyValue").val("");
            $("#otherCurrencyValue").focus();
            alert("Oops!Value Can't Be LessThan Zero")
            document.getElementById('otherCurrencyValue').style.borderColor = "red";
        }
    } else {
        $("#calculatedINRValue").val("0.0");
        $("#txtBalanceAmt").val("");
        $("#txtBalanceAmt").val("0.00");
    }
}


function clearPaymentValues()
{
    $("#txtCashPayment").val("");
    $("#txtCardPayment").val("");
    $("#txtForeginCurrency").val("");
}

function clearCreditCardValues()
{
    $("#txtCardType").val("Select");
    $("#txtCardNumber").val("");
    $("#txtExpiryDate").val("");
    $("#txtCardHolderName").val("");
}

function show_CouponRedu()
{
    var couponTableRowValue = $('#load_CouponNumbers tr').length;
    var selectedPaymentValue = $("#cashBillPaymentType").find("option:selected").text().trim();
    var selectedCouponType = $("#cashbill_CouponRedemption").find("option:selected").text().trim();

    if (selectedCouponType !== "Select") {
        $("#couponNoTo").val("");
        $("#couponNoFrom").val("");
        switch (selectedCouponType) {
            case 'Coupon Reduction':
                $(".couponReduction_td").show();
                $(".couponReductiontable_td").show();
                break;
            default :
                $(".couponReduction_td").hide();
                $(".couponReductiontable_td").hide();
                break;
        }
    } else {
        $(".couponReduction_td").hide();
        $(".couponReductiontable_td").hide();
        $("#cashbill_CouponType").val("Select");
        $("#txtTotalAmt").val(Math.round(parseInt($("#txtTotalAmt").val().trim()) + parseInt(totCouponAmount)).toFixed(2));
        var receivedCashAmount = parseInt($("#txtReceivedAmt").val().trim());

        if (receivedCashAmount !== "" && receivedCashAmount > 0) {
            var totalAmount = parseInt($("#txtTotalAmt").val().trim());
            if (receivedCashAmount > totalAmount) {
                $("#txtBalanceAmt").val("");
                $("#txtBalanceAmt").val(Math.round(parseInt(receivedCashAmount) - parseInt(totalAmount)).toFixed(2));
            } else {
                alert("Oops !!!,Received amount is not equal to bill amount");
                $("#txtBalanceAmt").val("");
                $("#txtBalanceAmt").val("0.0");

                $("#txtReceivedAmt").focus();
                $("#txtReceivedAmt").select();
                document.getElementById('txtReceivedAmt').style.borderColor = "red";
            }
        } else {
            $("#txtBalanceAmt").val("");
            $("#txtBalanceAmt").val("0.0");
        }

        if (couponTableRowValue >= 1)
        {
            for (var j = 1; j <= couponTableRowValue; j++)
            {
                $("#couponTypeName" + j).val("");
                $("#couponTypeValue" + j).val("");
            }
        }
        $("#couponTotalAmount").val("0.0");
        $("#load_CouponNumbers tr").remove();


        if (selectedPaymentValue !== "Select") {
            var cashBillTotalAmount = Math.round($("#txtTotalAmt").val().trim()).toFixed(2);
            switch (selectedPaymentValue)
            {
                case 'CASH' :
                    $("#txtCashPayment").val("");
                    $("#txtCashPayment").val(cashBillTotalAmount);
                    $("#txtCardPayment").val("");
                    $("#txtCardPayment").val("0.0");
                    break;
                case 'CREDIT CARD':
                    $("#txtCashPayment").val("");
                    $("#txtCardPayment").val("");
                    $("#txtCardPayment").val(cashBillTotalAmount);
                    break;
                case 'FOREIGN CURRENCY':
                    $("#txtCashPayment").val("");
                    $("#txtCardPayment").val("");
                    break;
            }
        }
    }
}

function fetchCounterBill_And_Amt()
{   
    cYear = $("#cYear").val();
    var inputCounterBillNo = $("#txtCounterBillNo").val().trim();
    if (inputCounterBillNo.length > 0)
    {
        if (inputCounterBillNo !== "" || inputCounterBillNo !== null)
        {
            $("#cashBillSavePrint_btn").attr("disabled", false);
            $.getJSON('isCounterBillProcessed.action?counterBillNo=' + $("#txtCounterBillNo").val().trim(), function (data)
            {
                if (data.isCounterBillProcessedStatus !== true)
                {
                    $.getJSON('getCounterBillNoAmt.action?txtcounterBillNo=' + $("#txtCounterBillNo").val().trim(), function (data)
                    {
                        if (data.counterBillCancelStatus !== 2)
                        {
                            if (data.counterBillnoCountValue >= 1)
                            {
                                loadCounterBill_Row(inputCounterBillNo, Math.round(data.dbNetAmt).toFixed(2),data.dbDateTime);
                                $("#txtCounterBillNo").val(cYear);
                                document.getElementById('txtCounterBillNo').style.borderColor = "";
                                noOfScannedBills = parseInt(noOfScannedBills) + 1;
                                $("#scannedCounterBillCount").val("");
                                $("#scannedCounterBillCount").val(noOfScannedBills).trim();
                            } else if (data.counterBillnoCountValue <= 0)
                            {
                                alert("Invalid-InvoiceNumber");
                                $("#txtCounterBillNo").val(cYear);
                                $("#txtCounterBillNo").focus();
                                document.getElementById('txtCounterBillNo').style.borderColor = "red";
                            }
                        } else if (data.counterBillCancelStatus == 2)
                        {
                            alert("Sorry,InvoiceNumber  " + inputCounterBillNo + " its already been cancelled..");
                            $("#txtCounterBillNo").val(cYear);
                        }
                    });
                } else if (data.isCounterBillProcessedStatus == true)
                {
                    alert("Opps!! InvoiceNumber  " + inputCounterBillNo + " its already been processed..");
                    $("#txtCounterBillNo").val(cYear);
                }
            });
        }
    }
}

function loadCounterBill_Row(counterBillNo, rcvDbNetAmt,dbDateTime)
{
 var rowCount = $('#loadScannedBillnoAmt tr').length;
    var receivedAmount = $("#txtReceivedAmt").val().trim();
    var redemCouponTrCount = $("#load_CouponNumbers tr").length;
    var bolSameOrderNo = 0;
    if (counterBillNo !== "")
    {
        if (rowCount === 0)
        {
           $("#loadScannedBillnoAmt").prepend('<tr id="tr' + rowIndex + '" class="trWrapper"><td style=""><input style="height:35px; font-size:13px;" type="text" id="txtCounterBillNo' + rowIndex + '" name="txtCounterBillNo' + rowIndex + '" class="input-block-level txtFieldcounterBillNo" value="' + counterBillNo + '" readonly="true"  /></td><td style="width: 30px; text-align: center"><input type="text" value="' + rcvDbNetAmt + '" id="txtNetAmt' + rowIndex + '" name="txtNetAmt' + rowIndex + '" class="txtFieldNetAmt" readonly="true" style="width:80px;height:26px;font-size:13px;"/></td><td style="width: 15px; text-align: center"><input type="text" value="' + dbDateTime + '" id="txtCounterDateNew' + rowIndex + '" name="txtCounterDateNew' + rowIndex + '" class="txtCounterDateNew" readonly="true" style="width:65px;height:26px;font-size:13px;"/></td><td style="width: 25px; text-align: center"><img src="./images/minus-circle.gif" id="removeTr' + rowIndex + '" name="removeTr' + rowIndex + '" class="fieldRemove" style="width:100%;" onclick="rmvSelectedItem(this,' + rowIndex + ');" /> </td></tr>');
            reordering_tr_values();
            calculateTotalAmount();
            rowIndex++;

        } else if (rowCount >= 1)
        {
            for (var cLop = 1; cLop <= rowCount; cLop++)
            {
                //if (!(($("#txtCounterBillNo" + cLop).val()) === (counterBillNo)))
                if ($("#txtCounterBillNo" + cLop).val() !== (counterBillNo))
                {
                    bolSameOrderNo = 0;
                } else
                {
                    bolSameOrderNo = 1;
                    break;
                }
            }
            if (bolSameOrderNo === 1)
            {
                alert("Oops!!!, InvoiceNumber is already in list..");
                $("#txtCounterBillNo").val(cYear);
                $("#txtCounterBillNo").focus();
                exit(0);
            } else if (bolSameOrderNo === 0)
            {
             //   $("#loadScannedBillnoAmt").prepend('<tr id="tr' + rowIndex + '" class="trWrapper"><td style=""><input type="text" id="txtCounterBillNo' + rowIndex + '" name="txtCounterBillNo' + rowIndex + '" class="input-block-level txtFieldcounterBillNo" value="' + counterBillNo + '" readonly="true"/></td><td style="width: 15px; text-align: center"><input type="text" value="' + rcvDbNetAmt + '" id="txtNetAmt' + rowIndex + '" name="txtNetAmt' + rowIndex + '" class="txtFieldNetAmt" readonly="true" style="width:80px;"/></td><td style="width: 25px; text-align: center"><img src="./images/minus-circle.gif"  id="removeTr' + rowIndex + '" name="removeTr' + rowIndex + '" class="fieldRemove" onclick="rmvSelectedItem(this,' + rowIndex + ');" /> </td></tr>');
                $("#loadScannedBillnoAmt").prepend('<tr id="tr' + rowIndex + '" class="trWrapper"><td style=""><input style="height:35px; font-size:13px;" type="text" id="txtCounterBillNo' + rowIndex + '" name="txtCounterBillNo' + rowIndex + '" class="input-block-level txtFieldcounterBillNo" value="' + counterBillNo + '" readonly="true"  /></td><td style="width: 35px; text-align: center"><input type="text" value="' + rcvDbNetAmt + '" id="txtNetAmt' + rowIndex + '" name="txtNetAmt' + rowIndex + '" class="txtFieldNetAmt" readonly="true" style="width:80px;height:26px;font-size:13px;"/></td><td style="width: 15px; text-align: center"><input type="text" value="' + dbDateTime + '" id="txtCounterDateNew' + rowIndex + '" name="txtCounterDateNew' + rowIndex + '" class="txtCounterDateNew" readonly="true" style="width:65px;height:26px;font-size:13px;"/></td><td style="width: 25px; text-align: center"><img src="./images/minus-circle.gif" id="removeTr' + rowIndex + '" name="removeTr' + rowIndex + '" class="fieldRemove" style="width:100%;" onclick="rmvSelectedItem(this,' + rowIndex + ');" /> </td></tr>');
                reordering_tr_values();
                calculateTotalAmount();
                rowIndex++;
            }

        }
        if (redemCouponTrCount >= 1) {
            var rTableTotal = 0;

            var selectedPaymentValue = $("#cashBillPaymentType").find("option:selected").text().trim();

            for (var redemIval = 1; redemIval <= redemCouponTrCount; redemIval++) {
                rTableTotal = parseInt(rTableTotal) + parseInt($("#couponAmount" + redemIval).val().trim());
            }

            var totAmountRedTable = parseInt($("#txtTotalAmt").val().trim()) - parseInt(rTableTotal);
            totAmountRedTable = Math.round(totAmountRedTable).toFixed(2);

            $("#txtTotalAmt").val("");
            $("#txtTotalAmt").val(totAmountRedTable);


            if (selectedPaymentValue !== "Select") {
                switch (selectedPaymentValue)
                {
                    case 'CASH' :
                        $("#txtCashPayment").val("");
                        $("#txtCashPayment").val(totAmountRedTable);
                        $("#txtCardPayment").val("");
                        $("#txtCardPayment").val("0.0");
                        break;
                    case 'CREDIT CARD':
                        $("#txtCashPayment").val("");
                        $("#txtCardPayment").val("");
                        $("#txtCardPayment").val(totAmountRedTable);
                        break;
                    case 'FOREIGN CURRENCY':
                        $("#txtCashPayment").val("0.0");
                        $("#txtCardPayment").val("0.0");
                        var selectedCurrencyType = $("#currencyType").find("option:selected").text().trim();
                        if (selectedCurrencyType !== "Select")
                        {
                            switch (selectedCurrencyType)
                            {
                                case'USD':
                                    var foreginCurrencyValue = $("#txtForeginCurrency").val().trim();
                                    var otherCurrencyValue = $("#otherCurrencyValue").val().trim();
                                    if (foreginCurrencyValue !== "" && foreginCurrencyValue > 0 && otherCurrencyValue !== "" && otherCurrencyValue > 0)
                                    {
                                        $("#calculatedINRValue").val("");
                                        $("#calculatedINRValue").val(Math.round(parseInt(foreginCurrencyValue) * parseInt(otherCurrencyValue)).toFixed(2));
                                        var inrValue = $("#calculatedINRValue").val().trim();
                                        var totalCashBillValue = $("#txtTotalAmt").val().trim();
                                        if (parseInt(inrValue) > parseInt(totalCashBillValue))
                                        {
                                            $("#txtBalanceAmt").val("");
                                            $("#txtBalanceAmt").val(Math.round(parseInt($("#calculatedINRValue").val()) - parseInt($("#txtTotalAmt").val())).toFixed(2));
                                        }
//                                        else if (parseInt($("#calculatedINRValue").val().trim()) < parseInt($("#txtTotalAmt").val().trim()))
//                                        {
//                                            alert("Oops !!!,Calculated INR values is not equal to bill amount");
//                                            $("#calculatedINRValue").focus();
//                                            $("#calculatedINRValue").select();
//                                        }
                                    } else
                                    {
                                        $("#calculatedINRValue").val("");
                                    }
                                    break;
                                case 'Euro':
                                    break;
                            }
                        }
                        break;
                }
            } else {
                $("#txtCashPayment").val("");
                $("#txtCardPayment").val("");
                $("calculatedINRValue").val("");
                $("#txtForeginCurrency").val("");
                $("#otherCurrencyValue").val("");
            }

        }
        var totalCashAmount = $("#txtTotalAmt").val().trim();
        if (receivedAmount !== "") {
            if (receivedAmount > 0) {
                if (receivedAmount > totalCashAmount) {
                    $("#txtBalanceAmt").val("");
                    $("#txtBalanceAmt").val(Math.round(parseInt(receivedAmount) - parseInt(totalCashAmount)).toFixed(2));
                    document.getElementById('txtReceivedAmt').style.borderColor = "";
                } else if (receivedAmount < totalCashAmount) {
                    $("#txtBalanceAmt").val("");
                    $("#txtBalanceAmt").val("0.0");
                    alert("Oops,Received amount is not equal");
                    $("#txtReceivedAmt").focus();
                    $("#txtReceivedAmt").select();
                    document.getElementById('txtReceivedAmt').style.borderColor = "red";
                }
            } else if (receivedAmount <= 0) {
                $("#txtBalanceAmt").val("");
                $("#txtBalanceAmt").val("0.0");
            }
        }


    }


}

function calculateTotalAmount()
{
    var summedValue = 0;
    var billAmt_rowCount;
    var billAmt_rowCount = $('#loadScannedBillnoAmt tr').length;
    var billAmt_rowCount = billAmt_rowCount - 1;
    var paymentType = $("#cashBillPaymentType").find("option:selected").text();
    if (billAmt_rowCount === 0)
    {
        $("#txtTotalAmt").val(Math.round($("#txtNetAmt1").val()).toFixed(2));
    } else if (billAmt_rowCount >= 1)
    {
        var loopValue = 1;
        while (loopValue <= billAmt_rowCount + 1)
        {
            summedValue = summedValue + parseInt($("#txtNetAmt" + loopValue).val());
            loopValue++;
        }
        summedValue = Math.round(summedValue).toFixed(2);
        $("#txtTotalAmt").val("");
        $("#txtTotalAmt").val(summedValue);

        if (paymentType !== "Select")
        {
            clearCreditCardValues();
            switch (paymentType)
            {
                case 'CASH' :
                    $("#txtCashPayment").val("");
                    $("#txtCashPayment").val(summedValue);
                    $("#txtCardPayment").val("");
                    $("#txtCardPayment").val("0.0");
                    break;
                case 'CREDIT CARD':
                    $("#txtCashPayment").val("");
                    $("#txtCardPayment").val("");
                    $("#txtCardPayment").val(summedValue);
                    break;
                case 'FOREIGN CURRENCY':
                    $("#txtCashPayment").val("");
                    $("#txtCardPayment").val("");
                    $("#calculatedINRValue").val("");
                    $("#calculatedINRValue").val(summedValue);
                    break;
            }
        } else
        {
            $("#txtCashPayment").val("");
            $("#txtCardPayment").val("");
            $("calculatedINRValue").val("");
            $("#txtForeginCurrency").val("");
            $("#otherCurrencyValue").val("");
        }
    }
}

function rmvSelectedItem(rcvKeyEle, rcvTrEle)
{
    var totBillingAmount = $("#txtTotalAmt").val().trim();
    var receivedAmount = $("#txtReceivedAmt").val().trim();
    var tblNetAmtVal = $("#txtNetAmt" + rcvTrEle).val().trim();
    var countCounterBillTr = $('#loadScannedBillnoAmt tr').length;
    var selectedCurrencyType = $("#currencyType").find("option:selected").text().trim();
    var selectedPaymentValue = $("#cashBillPaymentType").find("option:selected").text().trim();
    if (tblNetAmtVal !== "" && tblNetAmtVal > 0)
    {
        if (confirm("Remove This InvoiceNumber?"))//COMFIRMING WIHT THE USER FOR REMOVAL OF THE ROW
        {
            var calculatedCouponRedemTotalValue = parseInt(totBillingAmount) - parseInt(tblNetAmtVal);
            calculatedCouponRedemTotalValue = Math.round(calculatedCouponRedemTotalValue).toFixed(2);

            if (!(calculatedCouponRedemTotalValue < 0)) {
                $("#txtTotalAmt").val("");
                $("#txtTotalAmt").val(calculatedCouponRedemTotalValue);

//                $("#txtCashPayment").val("");
//                $("#txtCashPayment").val(calculatedCouponRedemTotalValue);

                if (selectedPaymentValue !== "Select") {
                    switch (selectedPaymentValue)
                    {
                        case 'CASH' :
                            $("#txtCashPayment").val("");
                            $("#txtCashPayment").val(calculatedCouponRedemTotalValue);
                            $("#txtCardPayment").val("");
                            $("#txtCardPayment").val("0.0");
                            break;
                        case 'CREDIT CARD':
                            $("#txtCashPayment").val("");
                            $("#txtCardPayment").val("");
                            $("#txtCardPayment").val(calculatedCouponRedemTotalValue);
                            break;
                        case 'FOREIGN CURRENCY':
                            $("#txtCashPayment").val("");
                            $("#txtCardPayment").val("");
                            if (selectedCurrencyType !== "Select") {
                                switch (selectedCurrencyType) {
                                    case 'USD':
                                        var foreginCurrencyValue = $("#txtForeginCurrency").val().trim();
                                        var otherCurrencyValue = $("#otherCurrencyValue").val().trim();
                                        var totalINRValue = $("#calculatedINRValue").val().trim();
                                        if (foreginCurrencyValue !== "" && foreginCurrencyValue > 0)
                                        {
                                            if (otherCurrencyValue !== "" && otherCurrencyValue > 0)
                                            {
                                                if (totalINRValue !== "" && totalINRValue > 0)
                                                {
                                                    $("#txtBalanceAmt").val("");
                                                    $("#txtBalanceAmt").val(Math.round(parseInt(totalINRValue) - parseInt($("#txtTotalAmt").val())).toFixed(2));
                                                }
                                            }
                                        }
                                        break;
                                    case'Euro':
                                        break;
                                }
                            }
                            break;
                    }
                } else {
                    $("#txtCashPayment").val("");
                    $("#txtCardPayment").val("");
                    $("calculatedINRValue").val("");
                    $("#txtForeginCurrency").val("");
                    $("#otherCurrencyValue").val("");
                }
                $(rcvKeyEle).closest('tr[id]').remove();//REMOVING THE SPECIFIC ROW FROM THE TABLE
                reordering_tr_values();
                if (noOfScannedBills > 0) {
                    noOfScannedBills = parseInt(noOfScannedBills) - 1;
                    $("#scannedCounterBillCount").val("");
                    $("#scannedCounterBillCount").val(noOfScannedBills).trim();
                }

            } else {
                alert("Oops !!! ,Remove a coupon");
                $("#txtTotalAmt").focus();
            }

            totBillingAmount = parseInt($("#txtTotalAmt").val().trim());
            if (receivedAmount !== "" && receivedAmount > 0) {
                if (receivedAmount > totBillingAmount) {
                    var balaceAmount = Math.round(parseInt(receivedAmount) - parseInt(totBillingAmount)).toFixed(2);
                    $("#txtBalanceAmt").val("");
                    $("#txtBalanceAmt").val(balaceAmount);
                } else {
                    $("#txtBalanceAmt").val("");
                    $("#txtBalanceAmt").val("0.0");
                }
            }

            if (countCounterBillTr === 1) {
                clearAllFieldValues();
                totCouponAmount = 0;
                $("#cashbillCouponType").val(1);
                $("#cashBillPaymentType").val('Select');
                $("#cashbill_CouponRedemption").val('select');
            }
            //reorderTotalAmt_OnRemoval(tblNetAmtVal, selectedPaymentType);
        }
    }
}

function reorderTotalAmt_OnRemoval(dynamicNetAmount, receivedPaymentType)
{
    var TablerowCount = $('#loadScannedBillnoAmt tr').length;
    //var TablerowCount = TablerowCount - 1;
    var currentTotalAmt = $("#txtTotalAmt").val();
    var reducedValue = parseInt(currentTotalAmt) - parseInt(dynamicNetAmount);
    $("#txtTotalAmt").val("");
    $("#txtCashPayment").val("");

    if (TablerowCount >= 1)
    {
        if (reducedValue > 0)
        {
            $("#txtTotalAmt").val(reducedValue);
            if (receivedPaymentType !== "Select")
            {
                clearCreditCardValues();
                switch (receivedPaymentType)
                {
                    case 'CASH' :
                        $("#txtCashPayment").val("");
                        $("#txtCashPayment").val(reducedValue);
                        break;
                    case 'CREDIT CARD':
                        $("#txtCashPayment").val("");
                        $("#txtCardPayment").val("");
                        $("#txtCardPayment").val(reducedValue);
                        break;
                    case 'FOREIGN CURRENCY':
                        $("#txtCashPayment").val("");
                        $("#txtCardPayment").val("");
                        $("calculatedINRValue").val("");
                        $("calculatedINRValue").val(reducedValue);
                        break;
                }
            } else
            {
                $("#txtCashPayment").val("0.0");
                $("#txtCardPayment").val("0.0");
                $("calculatedINRValue").val("0.0");
            }
        } else if (reducedValue === 0)
        {
            clearCreditCardValues();
            $(".paymentType_td").hide();
            $("#cashBillSavePrint_btn").attr("disabled", false);
            if (receivedPaymentType !== "Select")
            {
                clearCreditCardValues();
                switch (receivedPaymentType)
                {
                    case 'CASH' :
                        $("#txtCashPayment").val("");
                        $("#txtTotalAmt").val("0.0");
                        $("#txtCashPayment").val("0.0");
                        break;
                    case 'CREDIT CARD':
                        $("#txtCashPayment").val("");
                        $("#txtCardPayment").val("");
                        $("#txtTotalAmt").val("0.0");
                        $("#txtCardPayment").val("0.0");
                        break;
                    case 'FOREIGN CURRENCY':
                        $("#txtCashPayment").val("");
                        $("#txtCardPayment").val("");
                        $("calculatedINRValue").val("");
                        $("#txtTotalAmt").val("0.0");
                        $("calculatedINRValue").val("0.0");
                        break;
                }
            }
        }
    } else if (TablerowCount === 0)
    {
        $("#cashBillSavePrint_btn").attr("disabled", true);
    }
}

function calculate_BalanceAmt()
{
    var receiveAmt, balanceAmt, totalAmt;
    totalAmt = parseInt($("#txtTotalAmt").val());
    receiveAmt = parseInt($("#txtReceivedAmt").val());
    if (receiveAmt !== "" && receiveAmt > 0)
    {
        if (receiveAmt > totalAmt)
        {
            balanceAmt = parseInt(receiveAmt) - parseInt(totalAmt);
            balanceAmt = Math.round(balanceAmt).toFixed(2);
            if (balanceAmt <= 0)
            {
                $("#txtBalanceAmt").val("");
                $("#txtBalanceAmt").val("0.0");
            } else
            {
                $("#txtBalanceAmt").val("");
                $("#txtBalanceAmt").val(balanceAmt);
            }
            document.getElementById('txtReceivedAmt').style.borderColor = "";
        } else if (receiveAmt < totalAmt)
        {
            $("#txtBalanceAmt").val("");
            $("#txtBalanceAmt").val("0.0");
        }
    } else
    {
        $("#txtBalanceAmt").val("");
        $("#txtBalanceAmt").val("0.0");
    }
}

function checkIsBackDatedCash() {
    if ($("#enableDisableBackDateCash").is(':checked')) {
        if ($("#cashBackDate").val().trim() !== "") {
            saveCashBillOper($("#cashBackDate").val().trim(), " ");
            /*var selectedCashierPK = $("input[name=cashierPk]:checked").val();
             if (selectedCashierPK !== undefined && selectedCashierPK !== "") {
             saveCashBillOper($("#cashBackDate").val().trim(), $("input[name=cashierPk]:checked").val());
             } else {
             alert("Oops !! Select Cashier...");
             }*/
        } else if ($("#cashBackDate").val().trim() === "") {
            alert("Oops !! Select BackDated Date..");
            $("#cashBackDate").focus();
        }
    } else {
        saveCashBillOper("0000-01-01", " ");
    }
}


function saveCashBillOper(cashBackDatedDateTo, cashierPk)
{
    var cashPayment_val = "";
    var cardPayment_val = "";
    var currentTotalValue = "";
    var foreginCurrency_val = "";
    var travellersCheque_val = "";

    cashPayment_val = $("#txtCashPayment").val();
    cardPayment_val = $("#txtCardPayment").val();
    travellersCheque_val = $("#txtTravellersCheque").val();
    //foreginCurrency_val = $("#txtForeginCurrency").val();
    //calculatedINRValue
    foreginCurrency_val = $("#calculatedINRValue").val();
    currentTotalValue = $("#txtTotalAmt").val();
    var receivedAmount = $("#txtReceivedAmt").val();
    var finalTotalValue = 0;
    if ((cashPayment_val !== "") && (cashPayment_val > 0))
    {
        finalTotalValue = parseInt(finalTotalValue) + parseInt(cashPayment_val);
    }

    if ((cardPayment_val !== "") && (cardPayment_val > 0))
    {
        finalTotalValue = parseInt(finalTotalValue) + parseInt(cardPayment_val);
    }

    if ((travellersCheque_val !== "") && (travellersCheque_val > 0))
    {
        finalTotalValue = parseInt(finalTotalValue) + parseInt(travellersCheque_val);
    }

    if ((foreginCurrency_val !== "") && (foreginCurrency_val > 0))
    {
        finalTotalValue = parseInt(finalTotalValue) + parseInt(foreginCurrency_val);
    }
    if (finalTotalValue == currentTotalValue)
    {
        if (receivedAmount !== "")
        {
            if (receivedAmount >= finalTotalValue)
            {
                packagingCashBillScreenValues(cashBackDatedDateTo, cashierPk);
            } else if (receivedAmount < finalTotalValue)
            {
                alert("Oops!,Received amount is not equal");
                $("#txtReceivedAmt").focus();
                document.getElementById('txtReceivedAmt').style.borderColor = "red";
            }
        } else if (receivedAmount == "")
        {
            packagingCashBillScreenValues(cashBackDatedDateTo, cashierPk);
        }
    } else if (finalTotalValue > currentTotalValue)
    {
        if (receivedAmount !== "")
        {
            if (receivedAmount >= finalTotalValue)
            {
                var balanceAmount = parseInt(finalTotalValue) - parseInt(currentTotalValue);
                balanceAmount = Math.round(balanceAmount);
                $("#txtBalanceAmt").val("");
                $("#txtBalanceAmt").val(balanceAmount.toFixed(2));
                //packagingCashBillScreenValues();
            } else if (receivedAmount < finalTotalValue)
            {
                alert("Oops!,Received amount is not equal");
                $("#txtReceivedAmt").focus();
                document.getElementById('txtReceivedAmt').style.borderColor = "red";
            }
        } else if (receivedAmount == "")
        {
            var balanceAmount = parseInt(finalTotalValue) - parseInt(currentTotalValue);
            balanceAmount = Math.round(balanceAmount);
            $("#txtBalanceAmt").val("");
            $("#txtBalanceAmt").val(balanceAmount.toFixed(2));
            //packagingCashBillScreenValues();
        }
    } else
    {
        alert("Payment Value Is Not-Equal");
        $("#txtCashPayment").focus();
    }
}


function packagingCashBillScreenValues(cashBackDatedDateToPara, cashierPkPara)
{
    var aryCounterBillNo = [];
    var aryCounterBillNetAmt = [];
   
    var aryCouponType = [];
    var aryCouponNumber = [];
    var aryCouponAmount = [];

    var tblRowCount = $('#loadScannedBillnoAmt tr').length;
    var CouponNoTrs = $("#load_CouponNumbers tr").length;

    var cashBillNo = $('#txtCashBillOrderno').text();
    //$("#cashBillPaymentType").find("option:selected").val();
    //var cardType = $("#txtCardType").text();
    var cardType = $("#txtCardType").find("option:selected").val();
    if (cardType !== "Select")
    {
        cardType = $("#txtCardType").val();
    } else if (cardType === "Select" || cardType === "")
    {
        cardType = "NoData";
    }

    var cardNumber = $("#txtCardNumber").val();
    if (cardNumber != "")
    {
        cardNumber = $("#txtCardNumber").val();
    } else
    {
        cardNumber = "NoData";
    }
    var cardExpDate = $("#txtExpiryDate").val();
    if (cardExpDate != "")
    {
        //cardExpDate = $("#txtExpiryDate").val();
        var inputDate = $("#txtExpiryDate").val().trim();
        var expDate = inputDate.split('/');
        var lastDayInMonth = new Date(expDate[1], expDate[0], 0).getDate();//month,year
        cardExpDate = lastDayInMonth + "." + expDate[0] + "." + expDate[1];
    } else
    {
        cardExpDate = "NoData";
    }
    var cardHolderName = $("#txtCardHolderName").val();

    if (cardHolderName != "")
    {
        cardHolderName = $("#txtCardHolderName").val();
    } else
    {
        cardHolderName = "NoData";
    }

    var couponNoFrom = $("#couponNoFrom").val();
    var couponNoTo = $("#couponNoTo").val();

    var cashPayment = $("#txtCashPayment").val();
    var cardPayment = $("#txtCardPayment").val();
    var travellersCheque = $("#txtTravellersCheque").val();
    var foreginCurrency = $("#txtForeginCurrency").val();

    var balanceAmt = $("#txtBalanceAmt").val();
    var totalAmt = $("#txtTotalAmt").val();
    var receivedAmt = $("#txtReceivedAmt").val();


    var couponTotalAmount = $("#couponTotalAmount").val();
    var setCouponTotalAmount = "";

    var packageCashBillData = "";
    var packageCouponRedemtionData = "";
    var paymentMode = "";
    var paymentTypeSelected = $("#cashBillPaymentType").find("option:selected").val();

    if (paymentTypeSelected !== "Select")
    {
        paymentMode = paymentTypeSelected;
    } else
    {
        paymentMode = "NoData";
    }

    var currencyTypeSelected = $("#currencyType").find("option:selected").text();
    if (currencyTypeSelected != "Select")
    {
        currencyTypeSelected = $("#currencyType").find("option:selected").text();
    } else
    {
        currencyTypeSelected = "NoData";
    }

    var xChangeRate = $("#otherCurrencyValue").val();
    if (xChangeRate != "")
    {
        if (xChangeRate > 0)
        {
            xChangeRate = $("#otherCurrencyValue").val();
        } else
        {
            xChangeRate = "0.0";
        }
    } else
    {
        xChangeRate = "0.0";
    }

    for (var i = 1; i <= tblRowCount; i++)
    {
        aryCounterBillNo.push($("#txtCounterBillNo" + i).val().trim());
        aryCounterBillNetAmt.push($("#txtNetAmt" + i).val().trim());
  }
    for (var j = 0; j < tblRowCount; j++)
    {
        if (packageCashBillData == "")
        {
            //packageCashBillData = cashBillNo.trim() + "-" + paymentMode.trim() + "-" + cardType.trim() + "-" + cardNumber.trim() + "-" + cardExpDate.trim() + "-" + cardHolderName.trim() + "-" + currencyTypeSelected.trim() + "-" + xChangeRate.trim() + "-" + totalAmt.trim() + "-" + balanceAmt.trim() + "-" + aryCounterBillNo[j].trim() + "-" + aryCounterBillNetAmt[j].trim();
            packageCashBillData = paymentMode.trim() + "-" + cardType.trim() + "-" + cardNumber.trim() + "-" + cardExpDate.trim() + "-" + cardHolderName.trim() + "-" + currencyTypeSelected.trim() + "-" + xChangeRate.trim() + "-" + totalAmt.trim() + "-" + balanceAmt.trim() + "-" + aryCounterBillNo[j].trim() + "-" + aryCounterBillNetAmt[j].trim();
            //alert("Single :  " + packageCashBillData);
        } else
        {
            //packageCashBillData = packageCashBillData + "," + aryCounterBillNo[j] + "-" + aryCounterBillNetAmt[j];
            packageCashBillData = packageCashBillData + "," + aryCounterBillNo[j].trim() + "-" + aryCounterBillNetAmt[j].trim();
            //alert("Multiple :  " + packageCashBillData);
        }
    }


    if (couponTotalAmount != "")
    {
        if (couponTotalAmount > 0)
        {
            setCouponTotalAmount = $("#couponTotalAmount").val().trim();
        } else {
            setCouponTotalAmount = "0.0";
        }
    }

    if (CouponNoTrs > 1) {
        for (var couponTablePushLop = 1; couponTablePushLop <= CouponNoTrs; couponTablePushLop++) {
            aryCouponType.push($("#couponType" + couponTablePushLop).val().trim());
            aryCouponNumber.push($("#couponNumber" + couponTablePushLop).val().trim());
            aryCouponAmount.push($("#couponAmount" + couponTablePushLop).val().trim());
        }
    } else if (CouponNoTrs === 1) {
        aryCouponType.push($("#couponType1").val().trim());
        aryCouponNumber.push($("#couponNumber1").val().trim());
        aryCouponAmount.push($("#couponAmount1").val().trim());
    }
    for (var couponTableGetLop = 0; couponTableGetLop < CouponNoTrs; couponTableGetLop++) {
        if (packageCouponRedemtionData === "") {
            packageCouponRedemtionData = setCouponTotalAmount + "-" + aryCouponType[couponTableGetLop].trim() + "-" + aryCouponNumber[couponTableGetLop].trim() + "-" + aryCouponAmount[couponTableGetLop].trim();
            //alert("packageCouponRedemtionData SINGLE   :   " + packageCouponRedemtionData);
        } else {
            packageCouponRedemtionData = packageCouponRedemtionData + "," + aryCouponType[couponTableGetLop].trim() + "-" + aryCouponNumber[couponTableGetLop].trim() + "-" + aryCouponAmount[couponTableGetLop].trim();
            //alert("packageCouponRedemtionData DOUBLE   :   " + packageCouponRedemtionData);
        }
    }
    pushToDataBase(packageCashBillData, packageCouponRedemtionData, cashBackDatedDateToPara, cashierPkPara);
}
/* 
 function pushToDataBase(packageCashBillData, packageCouponRedemtionData, receiveCashBackDatedDateToValue, receiveCashierPkParaValue) 
 TEMP-BLOCKED-15.04.2015                                                                                                                                    
 */
function pushToDataBase(packageCashBillData, packageCouponRedemtionData, receiveCashBackDatedDateToValue)
{
    if (packageCashBillData !== "")
    {
        /*
         $.getJSON('pushCashBillValue.action?receivePackagedValue=' + packageCashBillData, "&pushingCouponRedemptionDeatail=" + packageCouponRedemtionData + "&pushingBackDatedValue=" + receiveCashBackDatedDateToValue + "&pushingCashierPkID=" + receiveCashierPkParaValue, function(data)
         TEMP-BLOCKED-15.04.2015
         */
        $.getJSON('pushCashBillValue.action?receivePackagedValue=' + packageCashBillData, "&pushingCouponRedemptionDeatail=" + packageCouponRedemtionData + "&pushingBackDatedValue=" + receiveCashBackDatedDateToValue, function (data)
        {
            if (data.printFlagValue == 1)
            {
                if (data.cashBillNo !== "")
                {
                    /*
                     OPENS A POPUP WINDOW WITH PRINT-REVIEW FOR BILLS THAT IS BEEN PROCESSED.
                     BLOCKED 05-02-2015-PRANESH
                     xCashBillWinOpen("print_jsp/cashbill-print.jsp?cashBillNo=" + data.cashBillNo);
                     */
                    alert("CashBill reference " + data.cashBillNo + "  processed successfully..");
                    clearAllFieldValues();
                    if ($("#enableDisableBackDateCash").is(':checked')) {
                        $('#cashBackDate').val('');
                        $("#cashier-detail-table").empty();
                        $("#cashBackDate").attr("disabled", true);
                        $("#enableDisableBackDateCash").attr('checked', false);
                    }
                    $("#txtCounterBillNo").focus();
                    cashbill_LoadPaymentType();
                    noOfScannedBills = parseInt(0);
                    $("#scannedCounterBillCount").val(noOfScannedBills).trim();
                    //cashbillOrderNo();-CALLING THIS FUNCTION TO SHOW NEXT CASHBILLNO(LOC :jquery-coupon-sales.js)
                } else {
                    alert("Please,do re-print");
                }
            }
        });
    }
}
var xcashBillChildWindow = null;
function xCashBillWinOpen(sUrl)
{
    var features = "left=5,top=7,width=1245,height=610,location=false,directories=false,toolbar=false,menubars=false,scrollbars=false,resize=false";
    if (xcashBillChildWindow && !xcashBillChildWindow.closed) {
        xcashBillChildWindow.location.href = sUrl;
    } else {
        xcashBillChildWindow = window.open(sUrl, "CashBill-PrintOut.jsp", features);
    }
    xcashBillChildWindow.focus();
    return false;
}
function cashBillReprint()
{
    if (!($("#cashBillNumber").val().trim() == ""))
    {
        $.getJSON('validDateCashBillNo?reprintCashBillNo=' + $("#cashBillNumber").val().trim(), function (data)
        {
            if (data.isCashBillStatus == true)
            {
                xCashBillWinOpen("print_jsp/cashbill-print.jsp?cashBillNo=" + $("#cashBillNumber").val().trim());
                document.getElementById('cashBillLight').style.display = 'none';
                document.getElementById('cashBillFade').style.display = 'none';
                $("#cashBillNumber").val("");
            } else if (data.isCashBillStatus == false)
            {
                alert("Oops,CashBillNo Doesn't Exits...!!");
                $("#cashBillNumber").val("");
                $("#cashBillNumber").focus();
                document.getElementById('cashBillNumber').style.borderColor = "red";
            }
        });
    } else
    {
        $("#cashBillNumber").focus();
        document.getElementById('cashBillNumber').style.borderColor = "red";
        alert("Oops,CashBillNo Is Missing..!!");
    }
}

function clearAllFieldValues()
{
    var tableLeftRowCountValue = "";
    var tableRightRowCountValue = "";
    tableLeftRowCountValue = $('#load_CouponNumbers tr').length;
    tableRightRowCountValue = $('#loadScannedBillnoAmt tr').length;
    $("#cashBillSavePrint_btn").attr("disabled", true);
    $("#cashBillPaymentType").val('Select');   
    $("#txtCounterBillNo").val(cYear);

    $("#txtCardType").val("Select");
    $("#txtCardNumber").val("");
    $("#txtExpiryDate").val("");
    $("#txtCardHolderName").val("");

    $("#couponNoTo").val("");
    $("#couponNoFrom").val("");

    $("#txtCashPayment").val("");
    $("#txtCardPayment").val("");
    $("#txtForeginCurrency").val("");
    $("#txtTravellersCheque").val("");
    $("#otherCurrencyValue").val("");
    $("#calculatedINRValue").val("");

    $(".otherCurrency").hide();
    $("#couponTotalAmount").val("");
    $("#txtTotalAmt").val("");
    $("#txtBalanceAmt").val("");
    $("#txtReceivedAmt").val("");

    $("#txtCashPayment").attr("disabled", "disabled");
    $("#txtCardPayment").attr("disabled", "disabled");
    $("#txtForeginCurrency").attr("disabled", "disabled");

    if (tableRightRowCountValue >= 1) {
        for (var i = 1; i <= tableRightRowCountValue; i++) {
            $("#txtNetAmt" + i).val("");
            $("#txtCounterBillNo" + i).val("");
        }
    }
    $("#loadScannedBillnoAmt tr").remove();

    if (tableLeftRowCountValue >= 1) {
        for (var j = 1; j <= tableLeftRowCountValue; j++) {
            $("#couponTypeName" + j).val("");
            $("#couponTypeValue" + j).val("");
        }
    }

    if ($("#enableDisableBackDateCash").is(':checked')) {
        $('#cashBackDate').val('');
        $("#cashBackDate").attr("disabled", true);
        $("#enableDisableBackDateCash").attr('checked', false);
    }


    $("#load_CouponNumbers tr").remove();
    $("#cashbill_CouponType").val("Select");
    $("#cashBill_PaymentType").val("Select");
    $("#cashbill_CouponRedemption").val(1);
    $(".paymentType_td").hide();
    $(".couponReduction_td").hide();
    $(".couponReductiontable_td").hide();
    rowIndex = 1;
    noOfScannedBills = parseInt(0);
    $("#scannedCounterBillCount").val(noOfScannedBills).trim();
    $("#txtTotalAmt").val("0.0");
    $("#txtBalanceAmt").val("0.0");



//    $("#userRole").val("select");
    $("#txtTotalAmt").val("0.0");
    $("#txtBalanceAmt").val("0.0");
}


function saveCashBill()
{
    var txtCounterBillNoValue, txtCardTypeValue, txtCardNumberValue, txtExpiryDateValue, txtCardHolderNameValue, cashbill_CouponTypeValue, sel_CashPaymentType, sel_CouponRedemption, couponNoFromValue;
    var couponNoToValue, txtCashPaymentValue, txtCardPaymentValue, txtTravellersChequeValue, txtForeginCurrencyValue, txtBalanceAmtValue, txtTotalAmtValue, txtReceivedAmtValue;
    txtCounterBillNoValue = $("#txtCounterBillNo").val();
    txtCardTypeValue = $("#txtCardType").val();
    txtCardNumberValue = $("#txtCardNumber").val();
    txtExpiryDateValue = $("#txtExpiryDate").val();
    txtCardHolderNameValue = $("#txtCardHolderName").val();
    couponNoFromValue = $("#couponNoFrom").val();
    couponNoToValue = $("#couponNoTo").val();
    txtCashPaymentValue = $("#txtCashPayment").val();
    txtCardPaymentValue = $("#txtCardPayment").val();
    txtTravellersChequeValue = $("#txtTravellersCheque").val();
    txtForeginCurrencyValue = $("#txtForeginCurrency").val();
    txtBalanceAmtValue = $("#txtBalanceAmt").val();
    txtTotalAmtValue = $("#txtTotalAmt").val();
    txtReceivedAmtValue = $("#txtReceivedAmt").val();
    sel_CashPaymentType = $("#cashBill_PaymentType").find("option:selected").text();
    cashbill_CouponTypeValue = $("#cashbill_CouponType").find("option:selected").text();
    sel_CouponRedemption = $("#cashbill_CouponRedemption").find("option:selected").text();

    var packedPaymentTypeValues = "", packedCouponRedemptionValues = "";


    if (sel_CashPaymentType !== "Select")
    {
        if (sel_CashPaymentType == "Credit Card")
        {
            if (txtCardTypeValue !== "" && txtCardNumberValue !== "" && txtExpiryDateValue !== "" && txtCardHolderNameValue !== "")
            {
                packedPaymentTypeValues;
            }
        }
    } else
    {
        $("#txtCardType").val("Select");
        $("#txtCardNumber").val("");
        $("#txtExpiryDate").val("");
        $("#txtCardHolderName").val("");
        packedPaymentTypeValues = "";
    }



    if (sel_CouponRedemption !== "Select")
    {
        if (sel_CouponRedemption == "Coupon Reduction")
        {
            if (cashbill_CouponTypeValue !== "Select")
            {
                if (couponNoFromValue !== "")
                {
                    if (couponNoToValue !== "")
                    {
                        packedCouponRedemptionValues;
                    }
                }
            } else
            {
                alert("Select Coupon Type");
                $("#cashbill_CouponType").focus();
            }
        } else
        {
            packedCouponRedemptionValues = "";
        }
    } else
    {
        $("#couponNoFrom").val("");
        $("#couponNoTo").val("");
    }
}


function reordering_tr_values()
{
    //RE-ORDERING COUNTER TABLE TR
    var trValue = $('.trWrapper');
    var countTr = 1;
    $.each(trValue, function ()
    {
        $(this).attr('id', 'tr' + countTr);
        countTr++;
    });

    //RE-ORDERING COUNTERBILL TEXT IN TABLE
    var counterBillnofield = $('.txtFieldcounterBillNo');
    var countCounerBillValue = 1;
    $.each(counterBillnofield, function () {
        $(this).attr('id', 'txtCounterBillNo' + countCounerBillValue);
        $(this).attr('name', 'txtCounterBillNo' + countCounerBillValue);
        countCounerBillValue++;
    });

    //RE-ORDERING NETMAMOUNT TEXT IN TABLE
    var netAmtfield = $('.txtFieldNetAmt');
    var countNetAmt = 1;
    $.each(netAmtfield, function () {
        $(this).attr('id', 'txtNetAmt' + countNetAmt);
        $(this).attr('name', 'txtNetAmt' + countNetAmt);
        countNetAmt++;
    });

    //RE-ORDERING REMOVE BUTTON TEXT IN TABLE
    var removefield = $('.fieldRemove');
    var countRemoveItms = 1;
    $.each(removefield, function () {
        $(this).attr('id', 'removeTr' + countRemoveItms);
        $(this).attr('name', 'removeTr' + countRemoveItms);
        $(this).attr('onclick', "rmvSelectedItem(this," + countRemoveItms + ")");
        countRemoveItms++;
    });
}

function reOrderingCouponRedemTable()
{
    var removeCouponTr = $('.couponRedemTrWrapper');
    var countCouponTr = 1;
    $.each(removeCouponTr, function ()
    {
        $(this).attr('id', 'tr' + countCouponTr);
        countCouponTr++;
    });

    var couponTypeName = $('.txtFieldCouponType');
    var countCouponTypeName = 1;
    $.each(couponTypeName, function () {
        $(this).attr('id', 'couponType' + countCouponTypeName);
        $(this).attr('name', 'couponType' + countCouponTypeName);
        countCouponTypeName++;
    });

    var couponNumber = $('.txtFieldCouponNumber');
    var countCouponNumber = 1;
    $.each(couponNumber, function () {
        $(this).attr('id', 'couponNumber' + countCouponNumber);
        $(this).attr('name', 'couponNumber' + countCouponNumber);
        countCouponNumber++;
    });

    var couponAmountValue = $('.txtFieldCouponAmount');
    var countAmountValue = 1;
    $.each(couponAmountValue, function () {
        $(this).attr('id', 'couponAmount' + countAmountValue);
        $(this).attr('name', 'couponAmount' + countAmountValue);
        countAmountValue++;
    });

    var couponRemoveField = $('.couponRemove');
    var countRemoveField = 1;
    $.each(couponRemoveField, function () {
        $(this).attr('id', 'couponRemoveTr' + countRemoveField);
        $(this).attr('name', 'couponRemoveTr' + countRemoveField);
        $(this).attr('onclick', "removeCouponRedemTr(this," + countRemoveField + ")");
        countRemoveField++;
    });
}

function removeCouponRedemTr(inpKeyValue, inpTValue)
{
    var receiveAmountCr = $("#txtReceivedAmt").val().trim();
    var totCouponNoEle = $("#load_CouponNumbers tr").length;
    var otherCurrencyValue = $("#otherCurrencyValue").val().trim();
    var foreginCurrencyValue = $("#txtForeginCurrency").val().trim();
    var calculatedCurrencyValue = $("#calculatedINRValue").val().trim();
    var selectedPayType = $("#cashBillPaymentType").find("option:selected").text();
    var selectedCurrencyType = $("#currencyType").find("option:selected").text().trim();
    if (confirm("Remove This Item?")) {

        if (!($("#couponAmount" + inpTValue).val() <= 0)) {

            $("#couponTotalAmount").val(Math.round(parseInt($("#couponTotalAmount").val().trim()) - parseInt($("#couponAmount" + inpTValue).val().trim())).toFixed(2));

            $("#txtTotalAmt").val(Math.round(parseInt($("#txtTotalAmt").val().trim()) + parseInt($("#couponAmount" + inpTValue).val().trim())).toFixed(2));

            if (parseInt(receiveAmountCr) > 0 && parseInt(receiveAmountCr) !== "") {
                if ($("#txtTotalAmt").val().trim() !== "" && $("#txtTotalAmt").val().trim() > 0) {
                    if (parseInt(receiveAmountCr) > parseInt($("#txtTotalAmt").val().trim())) {
                        $("#txtBalanceAmt").val("");
                        $("#txtBalanceAmt").val(Math.round(parseInt(receiveAmountCr) - parseInt($("#txtTotalAmt").val().trim())).toFixed(2));
                    } else {
                        alert("Oops !!!,Received amount is not enough-Unable to process");
                        $("#txtReceivedAmt").focus();
                        $("#txtReceivedAmt").select();
                        $("#txtBalanceAmt").val("0.0");
                    }
                }
            }

            var cashBillAmount = $("#txtTotalAmt").val().trim();
            if (selectedPayType !== "Select") {
                if (selectedCurrencyType !== "Select") {
                    if (foreginCurrencyValue !== "" && foreginCurrencyValue > 0) {
                        if (otherCurrencyValue !== "" && otherCurrencyValue > 0) {
                            if (calculatedCurrencyValue !== "" && calculatedCurrencyValue > 0) {
                                if (parseInt(calculatedCurrencyValue) > parseInt(cashBillAmount)) {
                                    $("#txtBalanceAmt").val("");
                                    $("#txtBalanceAmt").val(Math.round(parseInt(calculatedCurrencyValue) - parseInt(cashBillAmount)).toFixed(2)).to;
                                } else {
                                    alert("Oops !!!,Calculated INR is not enough-Unable to process");
                                    $("#calculatedINRValue").focus();
                                    $("#calculatedINRValue").select();
                                }
                            }
                        }
                    }
                }
            }

            $(inpKeyValue).closest('tr[id]').remove();//REMOVING THE SPECIFIC ROW FROM THE TABLE
            reOrderingCouponRedemTable();
            totCouponAmount = totCouponAmount - $("#couponAmount" + inpTValue).val();
            if (totCouponNoEle === 1)
            {
                $("#couponTotalAmount").val("");
                $("#couponTotalAmount").val("0.0")
                $(".couponReduction_td").hide();
                $("#cashbillCouponType").val(1);
                $("#cashbill_CouponRedemption").val('select');
                totCouponAmount = $("#couponTotalAmount").val();
                if (selectedPayType !== "Select") {
                    var totalCashBillAmount = Math.round($("#txtTotalAmt").val().trim()).toFixed(2);
                    switch (selectedPayType)
                    {
                        case 'CASH' :
                            $("#txtCashPayment").val("");
                            $("#txtCashPayment").val(totalCashBillAmount);
                            break;
                        case 'CREDIT CARD':
                            $("#txtCardPayment").val("");
                            $("#txtCardPayment").val(totalCashBillAmount);
                            break;
                        case 'FOREIGN CURRENCY':
                            if (foreginCurrencyValue !== "" && foreginCurrencyValue > 0) {
                                if (otherCurrencyValue !== "" && otherCurrencyValue > 0) {
                                    if (calculatedCurrencyValue !== "" && calculatedCurrencyValue > 0) {
                                        if (parseInt(calculatedCurrencyValue) > parseInt(cashBillAmount)) {
                                            $("#txtBalanceAmt").val("");
                                            $("#txtBalanceAmt").val(Math.round(parseInt(calculatedCurrencyValue) - parseInt(cashBillAmount)).toFixed(2)).to;
                                        } else {
                                            alert("Oops !!!,Calculated INR is not enough-Unable to process");
                                            $("#calculatedINRValue").focus();
                                            $("#calculatedINRValue").select();
                                        }
                                    }
                                } else {
                                    alert("Oops !!!,Other Currency Value is Missing");
                                    $("#otherCurrencyValue").focus();
                                    $("#otherCurrencyValue").select();
                                }
                            } else {
                                alert("Oops !!!,Foregin Currency Value is Missing");
                                $("#txtForeginCurrency").focus();
                                $("#txtForeginCurrency").select();
                            }
                            break;
                        default:
                            $("#txtCashPayment").val("");
                            $("#txtCashPayment").val("0.0");

                            $("#txtCardPayment").val("");
                            $("#txtCardPayment").val("0.0");
                            break;
                    }
                }
            } else if (totCouponNoEle > 1) {
                if (selectedPayType !== "Select") {
                    var totalCashBillAmount = Math.round($("#txtTotalAmt").val().trim()).toFixed(2);
                    switch (selectedPayType)
                    {
                        case 'CASH' :
                            $("#txtCashPayment").val("");
                            $("#txtCashPayment").val(totalCashBillAmount);
                            break;
                        case 'CREDIT CARD':
                            $("#txtCardPayment").val("");
                            $("#txtCardPayment").val(totalCashBillAmount);
                            break;
                        case 'FOREIGN CURRENCY':
                            if (foreginCurrencyValue !== "" && foreginCurrencyValue > 0) {
                                if (otherCurrencyValue !== "" && otherCurrencyValue > 0) {
                                    if (calculatedCurrencyValue !== "" && calculatedCurrencyValue > 0) {
                                        if (parseInt(calculatedCurrencyValue) > parseInt(cashBillAmount)) {
                                            $("#txtBalanceAmt").val("");
                                            $("#txtBalanceAmt").val(Math.round(parseInt(calculatedCurrencyValue) - parseInt(cashBillAmount)).toFixed(2)).to;
                                        } else {
                                            alert("Oops !!!,Calculated INR is not enough-Unable to process");
                                            $("#calculatedINRValue").focus();
                                            $("#calculatedINRValue").select();
                                        }
                                    }
                                } else {
                                    alert("Oops !!!,Other Currency Value is Missing");
                                    $("#otherCurrencyValue").focus();
                                    $("#otherCurrencyValue").select();
                                }
                            } else {
                                alert("Oops !!!,Foregin Currency Value is Missing");
                                $("#txtForeginCurrency").focus();
                                $("#txtForeginCurrency").select();
                            }

                            break;
                        default:
                            $("#txtCashPayment").val("");
                            $("#txtCashPayment").val("0.0");

                            $("#txtCardPayment").val("");
                            $("#txtCardPayment").val("0.0");
                            break;
                    }
                }
            }
        }

    }
}
function couponRedemption()
{

    var couponNumber = $("#couponNos").val().trim();
    var totBillAmount = $("#txtTotalAmt").val().trim();
    var inrValue = $("#calculatedINRValue").val().trim();
    var amountReceived = $("#txtReceivedAmt").val().trim();

    var choosedCurrencyType = $("#currencyType").find("option:selected").text().trim();
    var choosedPayType = $("#cashBillPaymentType").find("option:selected").text().trim();
    var selectedCouponType = $("#cashbillCouponType").find("option:selected").text().trim();
    totCouponAmount = 0;
    $("#couponNos").val("");
    if (!(choosedPayType == "Select")) {

        if (couponNumber > 0) {

            if (selectedCouponType !== "Select") {

                $.getJSON('couponRedemptionAction.action?couponNumber=' + couponNumber, "&couponType=" + selectedCouponType, function (data)
                {
                    if (data.couponStatus == true) {
                        if (totBillAmount > 0)
                        {
                            var couponRowCount = $('#couponRedemTable tr').length;
                            totBillAmount = parseInt(totBillAmount) - parseInt(data.responseCouponAmount);
                            totBillAmount = Math.round(totBillAmount).toFixed(2);//TOTALAMOUNT AFTER REDUCING COUPON AMOUNT
                            if (totBillAmount > 0) {
                                $("#load_CouponNumbers").append('<tr id="tr' + cRedemTr + '" class="couponRedemTrWrapper">  <td style="width: 15px; text-align: center"><input type="text" id="couponType' + cRedemTr + '" name="couponType' + cRedemTr + '" class="txtFieldCouponType" value="' + selectedCouponType + '" readonly="true"  style="width:80px;"/></td>  <td style="width: 15px; text-align: center"><input type="text" value="' + data.responseCouponNumber + '" id="couponNumber' + cRedemTr + '" name="couponNumber' + cRedemTr + '" class="txtFieldCouponNumber" readonly="true" style="width:80px;"/></td>  <td style="width: 15px; text-align: center"><input type="text" value="' + data.responseCouponAmount + '" id="couponAmount' + cRedemTr + '" name="couponAmount' + cRedemTr + '" class="txtFieldCouponAmount" readonly="true" style="width:80px;"/></td>  <td style="width: 25px; text-align: center"><img src="./images/minus-circle.gif" width: 20px;" id="couponRemoveTr' + cRedemTr + '" name="couponRemoveTr' + cRedemTr + '" class="couponRemove" onclick="removeCouponRedemTr(this,' + cRedemTr + ');" /> </td></tr>');
                                cRedemTr++;
                                $("#couponNos").val("");

                                reOrderingCouponRedemTable();

                                for (var couponLoop = 1; couponLoop <= couponRowCount; couponLoop++) {
                                    totCouponAmount = parseInt(totCouponAmount) + parseInt($("#couponAmount" + couponLoop).val());
                                }
                                totCouponAmount = Math.round(totCouponAmount).toFixed(2);

                                $("#couponTotalAmount").val("");
                                $("#couponTotalAmount").val(totCouponAmount);

                                $("#txtTotalAmt").val("");
                                $("#txtTotalAmt").val(totBillAmount);

                                if (choosedPayType !== "Select")
                                {
                                    switch (choosedPayType)
                                    {
                                        case 'CASH' :
                                            $("#txtCashPayment").val("");
                                            $("#txtCashPayment").val(totBillAmount);
                                            if (parseInt(amountReceived) !== "" && parseInt(amountReceived) > 0) {
                                                if (parseInt(amountReceived) > parseInt(totBillAmount)) {
                                                    $("#txtBalanceAmt").val("");
                                                    $("#txtBalanceAmt").val(Math.round(parseInt(amountReceived) - parseInt(totBillAmount)).toFixed(2));
                                                } else {
                                                    $("#txtBalanceAmt").val("");
                                                    $("#txtBalanceAmt").val("0.0");
                                                    alert("Oops!!!,Received amount is not enough");
                                                    $("#txtReceivedAmt").focus();
                                                    $("#txtReceivedAmt").select();
                                                }
                                            }
                                            break;
                                        case 'CREDIT CARD':
                                            $("#txtCardPayment").val("");
                                            $("#txtCardPayment").val(totBillAmount);

                                            if (parseInt(amountReceived) !== "" && parseInt(amountReceived) > 0) {
                                                if (parseInt(amountReceived) > parseInt(totBillAmount)) {
                                                    $("#txtBalanceAmt").val("");
                                                    $("#txtBalanceAmt").val(Math.round(parseInt(amountReceived) - parseInt(totBillAmount)).toFixed(2));
                                                } else {
                                                    $("#txtBalanceAmt").val("");
                                                    $("#txtBalanceAmt").val("0.0");
                                                    alert("Oops!!!,Received amount is not enough");
                                                    $("#txtReceivedAmt").focus();
                                                    $("#txtReceivedAmt").select();
                                                }
                                            }
                                            break;
                                        case 'FOREIGN CURRENCY':
                                            if (choosedCurrencyType !== "Select")
                                            {
                                                if (inrValue !== "")
                                                {
                                                    if (inrValue > 0)
                                                    {
                                                        if (parseInt(totBillAmount) !== "" && parseInt(totBillAmount > 0))
                                                        {
                                                            if (parseInt(inrValue) > parseInt(totBillAmount))
                                                            {
                                                                $("#txtBalanceAmt").val("");
                                                                $("#txtBalanceAmt").val(Math.round(parseInt(inrValue) - parseInt(totBillAmount)).toFixed(2));
                                                            } else
                                                            {
                                                                $("#txtBalanceAmt").val("");
                                                                $("#txtBalanceAmt").val("0.0");
                                                                alert("Oops!!!,Received amount is not enough");
                                                                $("#txtReceivedAmt").focus();
                                                                $("#txtReceivedAmt").select();
                                                            }
                                                        } else
                                                        {
                                                            $("#txtBalanceAmt").val("");
                                                            $("#txtBalanceAmt").val("0.0");
                                                        }
                                                    } else
                                                    {
                                                        $("#txtBalanceAmt").val("");
                                                        $("#txtBalanceAmt").val("0.0");
                                                    }
                                                } else
                                                {
                                                    $("#txtBalanceAmt").val("");
                                                    $("#txtBalanceAmt").val("0.0");
                                                }
                                            }
                                            $("#txtTotalAmt").val("");
                                            $("#txtTotalAmt").val(totBillAmount);
                                            break;
                                    }
                                }

                            } else if (totBillAmount < 0) {
                                alert("Oops!!!,Cannot process this coupon-billamount is not sufficient");
                            }
                        } else {
                            alert("Oops,Bill amount is sufficient");
                        }
                    } else if (data.couponStatus == false) {
                        alert("Invalid Coupon");
                        $("#couponNos").val("");
                    }
                });
            }
        }
    } else {
        alert("Please payment type");
        $("#cashBillPaymentType").focus();
    }
}

//function couponRedemption()
//{
//
//    var couponNumber = $("#couponNos").val().trim();
//    var totBillAmount = $("#txtTotalAmt").val().trim();
//    var inrValue = $("#calculatedINRValue").val().trim();
//    var amountReceived = $("#txtReceivedAmt").val().trim();
//
//    var choosedCurrencyType = $("#currencyType").find("option:selected").text().trim();
//    var choosedPayType = $("#cashBillPaymentType").find("option:selected").text().trim();
//    var selectedCouponType = $("#cashbillCouponType").find("option:selected").text().trim();
//    totCouponAmount = 0;
//    $("#couponNos").val("");
//    if (!(choosedPayType == "Select")) {
//
//        if (couponNumber > 0) {
//
//            if (selectedCouponType !== "Select") {
//
//                $.getJSON('couponRedemptionAction.action?couponNumber=' + couponNumber, "&couponType=" + selectedCouponType, function(data)
//                {
//                    if (data.couponStatus == true) {
//
//                        $("#load_CouponNumbers").append('<tr id="tr' + cRedemTr + '" class="couponRedemTrWrapper">  <td style="width: 15px; text-align: center"><input type="text" id="couponType' + cRedemTr + '" name="couponType' + cRedemTr + '" class="txtFieldCouponType" value="' + selectedCouponType + '" readonly="true"  style="width:80px;"/></td>  <td style="width: 15px; text-align: center"><input type="text" value="' + data.responseCouponNumber + '" id="couponNumber' + cRedemTr + '" name="couponNumber' + cRedemTr + '" class="txtFieldCouponNumber" readonly="true" style="width:80px;"/></td>  <td style="width: 15px; text-align: center"><input type="text" value="' + data.responseCouponAmount + '" id="couponAmount' + cRedemTr + '" name="couponAmount' + cRedemTr + '" class="txtFieldCouponAmount" readonly="true" style="width:80px;"/></td>  <td style="width: 25px; text-align: center"><img src="./images/minus-circle.gif" width: 20px;" id="couponRemoveTr' + cRedemTr + '" name="couponRemoveTr' + cRedemTr + '" class="couponRemove" onclick="removeCouponRedemTr(this,' + cRedemTr + ');" /> </td></tr>');
//                        $("#couponNos").val("");
//                        reOrderingCouponRedemTable();
//
//                        if (totBillAmount > 0) {
//                            var couponRowCount = $('#couponRedemTable tr').length;
//
//                            totBillAmount = parseInt(totBillAmount) - parseInt(data.responseCouponAmount);
//                            totBillAmount = Math.round(totBillAmount).toFixed(2);
//
//                            for (var couponLoop = 1; couponLoop < couponRowCount; couponLoop++) {
//                                totCouponAmount = parseInt(totCouponAmount) + parseInt($("#couponAmount" + couponLoop).val());
//                            }
//                            totCouponAmount = Math.round(totCouponAmount).toFixed(2);
//
//                            $("#couponTotalAmount").val("");
//                            $("#couponTotalAmount").val(totCouponAmount);
//
//                            $("#txtTotalAmt").val("");
//                            $("#txtTotalAmt").val(totBillAmount);
//
//                            if (choosedPayType !== "Select")
//                            {
//                                switch (choosedPayType)
//                                {
//                                    case 'CASH' :
//                                        $("#txtCashPayment").val("");
//                                        $("#txtCashPayment").val(totBillAmount);
//                                        if (parseInt(amountReceived) !== "" && parseInt(amountReceived) > 0) {
//                                            if (parseInt(amountReceived) > parseInt(totBillAmount)) {
//                                                $("#txtBalanceAmt").val("");
//                                                $("#txtBalanceAmt").val(Math.round(parseInt(amountReceived) - parseInt(totBillAmount)).toFixed(2));
//                                            } else {
//                                                $("#txtBalanceAmt").val("");
//                                                $("#txtBalanceAmt").val("0.0");
//                                                alert("Oops!!!,Received amount is not enough");
//                                                $("#txtReceivedAmt").focus();
//                                                $("#txtReceivedAmt").select();
//                                            }
//                                        }
//                                        break;
//                                    case 'CREDIT CARD':
//                                        $("#txtCardPayment").val("");
//                                        $("#txtCardPayment").val(totBillAmount);
//
//                                        if (parseInt(amountReceived) !== "" && parseInt(amountReceived) > 0) {
//                                            if (parseInt(amountReceived) > parseInt(totBillAmount)) {
//                                                $("#txtBalanceAmt").val("");
//                                                $("#txtBalanceAmt").val(Math.round(parseInt(amountReceived) - parseInt(totBillAmount)).toFixed(2));
//                                            } else {
//                                                $("#txtBalanceAmt").val("");
//                                                $("#txtBalanceAmt").val("0.0");
//                                                alert("Oops!!!,Received amount is not enough");
//                                                $("#txtReceivedAmt").focus();
//                                                $("#txtReceivedAmt").select();
//                                            }
//                                        }
//                                        break;
//                                    case 'FOREIGN CURRENCY':
//                                        if (choosedCurrencyType !== "Select")
//                                        {
//                                            if (inrValue !== "")
//                                            {
//                                                if (inrValue > 0)
//                                                {
//                                                    if (parseInt(totBillAmount) !== "" && parseInt(totBillAmount > 0))
//                                                    {
//                                                        if (parseInt(inrValue) > parseInt(totBillAmount))
//                                                        {
//                                                            $("#txtBalanceAmt").val("");
//                                                            $("#txtBalanceAmt").val(Math.round(parseInt(inrValue) - parseInt(totBillAmount)).toFixed(2));
//                                                        }
//                                                        else
//                                                        {
//                                                            $("#txtBalanceAmt").val("");
//                                                            $("#txtBalanceAmt").val("0.0");
//                                                            alert("Oops!!!,Received amount is not enough");
//                                                            $("#txtReceivedAmt").focus();
//                                                            $("#txtReceivedAmt").select();
//                                                        }
//                                                    }
//                                                    else
//                                                    {
//                                                        $("#txtBalanceAmt").val("");
//                                                        $("#txtBalanceAmt").val("0.0");
//                                                    }
//                                                }
//                                                else
//                                                {
//                                                    $("#txtBalanceAmt").val("");
//                                                    $("#txtBalanceAmt").val("0.0");
//                                                }
//                                            }
//                                            else
//                                            {
//                                                $("#txtBalanceAmt").val("");
//                                                $("#txtBalanceAmt").val("0.0");
//                                            }
//                                        }
//                                        $("#txtTotalAmt").val("");
//                                        $("#txtTotalAmt").val(totBillAmount);
//                                        break;
//                                }
//                            }
//                        }
//                        else {
//                            alert("Oops,Bill amount is sufficient");
//                        }
//                        cRedemTr++;
//                    }
//                    else if (data.couponStatus == false) {
//                        alert("Invalid Coupon");
//                        $("#couponNos").val("");
//                    }
//                });
//            }
//        }
//    }
//    else {
//        alert("Please payment type");
//        $("#cashBillPaymentType").focus();
//    }
//}


//TEMPORARILY BLOCKED 18.09.2014
//function couponNoGeneration()
//{
//    var txtFrom = "", txtTo = "", selected_couponType = "";
//    txtFrom = $("#couponNoFrom").val();
//    //alert("From :   " + txtFrom);
//
//    txtTo = $("#couponNoTo").val();
//    //alert("to   :   " + txtTo);
//
//    selected_couponType = $("#cashbillCouponType").find("option:selected").text();
//    //alert("CouponType :  " + selected_couponType);
//    if (selected_couponType !== "Select")
//    {
//        //alert("1");
//        if (txtFrom !== "")
//        {
//            //alert("2");
//            if (txtTo !== "")
//            {
//                //alert("3");
//                //alert("From Inside :   " + txtFrom);
//                //alert("to   Inside :   " + txtTo);
//                for (var lopFrom = txtFrom; lopFrom <= txtTo; lopFrom++)
//                {
//                    //alert(lopFrom);
//                    $("#load_CouponNumbers").append('<tr id="tr' + lopFrom + '" class="couponTypeTrWrapper"><td style="width: 15px; text-align: center"><input type="text" id="couponTypeName' + lopFrom + '" name="couponTypeName' + lopFrom + '" class="txtFieldcouponTypeName" value="' + selected_couponType + '" readonly="true"  style="width:80px;"/></td><td style="width: 15px; text-align: center"><input type="text" value="' + lopFrom + '" id="couponTypeValue' + lopFrom + '" name="couponTypeValue' + lopFrom + '" class="txtFieldcouponTypeValue" readonly="true" style="width:80px;"/></td></tr>');
//                }
//            }
//            else
//            {
//                alert("Enter Coupon To Number Is Missing...!");
//                $("#couponNoTo").focus();
//                document.getElementById('couponNoTo').style.borderColor = "red";
//            }
//        }
//        else
//        {
//            alert("Enter Coupon From Number Is Missing...!");
//            $("#couponNoFrom").focus();
//            document.getElementById('couponNoFrom').style.borderColor = "red";
//        }
//    }
//    else
//    {
//        alert("Please Select CouponType");
//        $("#cashbill_CouponType").focus();
//    }
//}

function loadingProgressCircle()
{
    $("#progressImage").empty();
    $('#progressImage').html('<div class="loadimage"></div><div id="waiting"><center><img src="images/ProgressBar.gif"></center></div>');
    $("#progressImage").show();
}

function loadimage() {
    $("#cashBill_ContentDiv").empty();
    $('#cashBill_ContentDiv').html('<div class="loadimgnr"></div><div id="waiter"><center><img src="images/ProgressBar.gif"></center></div>');
    $("#cashBill_ContentDiv").show();
}


function printDiv(divName)
{
    var myWindow = window.open('', '', 'width=1000,height=700');
    var printContents = document.getElementById(divName).innerHTML;
    myWindow.document.write('<style>table{page-break-inside :auto; max-width:auto;border: 1px solid black;}table tr td{page-break-inside:avoid;page-break-after:auto;border: 1px solid black;} .exportlinks{display: none;} input {visibility: hidden;}</style>');
    myWindow.document.write(printContents);
    myWindow.document.close();
    myWindow.focus();
    myWindow.print();
    myWindow.close();

}


function changeCashToCard() {
    $("#displayInfotTable").hide();
    if ($("#invoiceNo").val() !== "") {
        $.getJSON('changeCashToCardAction.action?invoiceNo=' + $("#invoiceNo").val().trim(), function (data)
        {
            if (data.synVal === 0) {
                if (data.userValidVal >= 1) {
                    var totalAm = data.totalAmt;
                    $("#displayInfotTable").removeClass("hide");
                    $("#displayInfotTable").show();
                    $("#invoice").text(data.invoiceNo);
                    $("#amount").text(totalAm.toFixed(2));
                    if (data.payType === "CRC") {
                        $("#payType").text("CARD");
                    } else {
                        $("#payType").text(data.payType);
                    }
                    $("#name").text(data.casherName);
                    $("#cDate").text(data.createdDate);
                    $("#updatedDate").text(data.updatedOn)
                    if (data.payType === "CRC") {
                        $("#chgPayTypeBtn").val("Change To CASH");
                    } else if (data.payType === "CASH") {
                        $("#chgPayTypeBtn").val("Change To CARD");
                    }
                } else if (data.userValidVal === 0) {
                    alert("Oops," + $("#invoiceNo").val().trim() + " Invoice no is not processed by logged user...!");
                    $("#invoiceNo").focus();
                    $("#invoiceNo").select();
                }
            } else {
                alert("Acess Denied...!");
                $("#invoiceNo").focus();
                $("#invoiceNo").select();
            }
        });
    } else
    {
        alert("Oop's Inoice Number is Missing");
        $("#invoiceNo").focus();
    }

}
function changeCashToCardAction() {
    var payType;
    if (confirm("To Confirm,press OK..")) {
        if ($("#payType").text().trim() === "CARD") {
            payType = "CASH";
        } else if ($("#payType").text().trim() === "CASH") {
            payType = "CRC";
        }
        $.getJSON('updatePayTypeAction.action?invoiceNo=' + $("#invoice").text().trim(), '&payType=' + payType + '&date_time=' + $("#cDate").text(), function (data)
        {
            if (data.updateVal === 1) {
                alert("Record updated sucessfully..");
                changeCashToCard();
            } else {
                alert("Oop's !!,Update faliure..")
            }
        });

    }
}

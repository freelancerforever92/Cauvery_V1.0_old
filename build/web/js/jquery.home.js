var tabClk = 0;
var counterName = "";
$(document).ready(function()
{
    Display_LoginName();
    load_salesForm();
});

function userLogout()
{
    $.getJSON('SessionClear.action?curntLoginId=' + $("#txtlog_empid").text().trim(), function(data)
    {
        if (data.forwardPage === "homepage")
        {
            document.location.href = 'index-login.jsp';
            alert("Logout Successfully..");
        }
    });
}

function Display_LoginName()
{
    var session_value = "";
    $("#login_name").text("");
    $.getJSON('SessionName.action', function(data)
    {
        session_value = data.cursession_value;
        $("#login_name").text("");
        $("#Txtorderno").text("");
        $("#txtlog_empid").text("");
        if (session_value === "valid")
        {
            $("#login_name").text(data.displayname);
            $("#Txtorderno").text(data.salesOrderNo);
            $("#txtlog_empid").text(data.loginempId);
            counterName = data.counterName;
        }
        else if (session_value === "in-valid" || session_value === null)
        {
            $("#login_name").text("");
        }
    });
}

function load_addNewEmployee()
{
    $("#content_right").empty();
    $("#content_right").load('newEmployeeDetails.jsp');
}

function load_cancelHistory() {
    $("#content_right").empty();
    $("#content_right").load('cashbill/cashbill-salesordercancel.jsp');
}

function load_salesForm()
{
//    var isCounterType = $("#counterType").val().trim();
//
//    if (isCounterType == "Billets/RM")
//    {
//        $("#content_right").empty();
//        $("#content_right").load('user_jsp/sandalWood.jsp');
//    }
//    else
//    {
    //if (tabClk !== 1)
    //{
    $("#content_right").empty();
    //loadimage();
    //Display_LoginName();
    $("#content_right").load('user_jsp/sales-home.jsp');
    //tabClk = 1;
    //}
    //else {
    //}
    //}
}

function clearCancellingFields()
{
    $("#txtCancellingCounterBillNo").val("");
}

function loadCancel_historyPage() {
    $("#content_right").load('user_jsp/cancel-history.jsp');
}

function getCancel_historyForm() {
    $("#cancelcounterbillHistory").hide();
    if ($("#txtCancellingSalesOrderNo").val() !== "")
    {
        $.getJSON("getCancel_historyAction.action?txtCancellingSalesOrderNo=" + $("#txtCancellingSalesOrderNo").val().trim(), function(data)
        {
            if (data.inoiceCountVal > 0)
            {
                if (data.canceledHistoryVal > 0) {
                    $("#cancelcounterbillHistory").show();
                    $("#craftGroup").text(data.craftName);
                    $("#ta").text(data.netAmount);
                    $("#cd").text(data.cancelDate);
                    $("#pd").text(data.processedDate);
                    $("#processedby").text(data.empNameProcessedBy);
                    $("#cacncelby").text(data.empNameCanceledBy);
                    $("#son").text(data.getSalesOrderNumbercanceled);
                    $("#canreason").text(data.cancelReason);
                }
                else {
                    alert("Sales order number is not cancelled");
                    $("#txtCancellingSalesOrderNo").focus();
                }
            } else {
                alert("Enter valid Sales number");
                $("#txtCancellingCounterBillNo").focus();
            }
        });
    } else {
        alert("Invoice Number Missing");
        $("#txtCancellingSalesOrderNo").focus();
    }
}

function loadSalesOrderCancelForm()
{
    $("#content_right").empty();
    //loadimage();
    //$("#content_right").load('cashbill_user_jsp/cashbill-salesordercancel.jsp');
    $("#content_right").load('cashbill/cashbill-salesordercancel.jsp');
}

function loadCounterReportLayout()
{
    $("#content_right").empty();
    //loadimage();
    $("#content_right").load('user_jsp/counter-summary-report-home.jsp');
}

function loadCounterReportGrid() {
    $("#CounterContentArea").empty();
    $("#CounterContentArea").load('user_jsp/counter-summary-report.jsp');
    $.getJSON('getCounterSummaryTotal.action', function(data)
    {
        if (data.loginUserType !== "all") {
            $("#CounterExport").show();
        } else if (data.loginUserType === "all") {
            $("#CounterExport").show();
        }
        $("#totalSumValue").text("");
        $("#totalSumValue").text(Math.round(data.counterTotalSum).toFixed(2));

        $("#counterBillTotal").text("");
        $("#counterBillTotal").text(data.totalCounterBill);
    });
}

function filterCounterBillRecord()
{
    if ($("#counterFromDate").val() !== "" && $("#counterToDate").val() !== "")
    {
        var selectedCancelFlag = $("#cancelledOptionParameter").find("option:selected").val().trim();
        $("#CounterContentArea").load('CounterRecordFromTo.action?&counterButtonType=' + $("#counterReportSearchButton").val().trim(), '?&counterFromDate=' + $("#counterFromDate").val().trim() + '&counterToDate=' + $("#counterToDate").val().trim() + '&selectedFlag=' + selectedCancelFlag.trim());
        $.getJSON("getCounterSummaryTotal.action?filterButtonType=" + $("#counterReportSearchButton").val().trim(), '?&filterFromDate=' + $("#counterFromDate").val().trim() + '&filterToDate=' + $("#counterToDate").val().trim() + '&selectedSearchByFlag=' + selectedCancelFlag.trim(), function(data)
        {
            $("#totalSumValue").text("");
            $("#totalSumValue").text(Math.round(data.counterTotalSum).toFixed(2));
            $("#counterBillTotal").text("");
            $("#counterBillTotal").text(data.totalCounterBill);
        });
    }
    else
    {
        alert("Oops!!,please select from & to date");
        $("#counterFromDate").focus();
    }
}
function craftwiseCounterReport() {
    if ($("#counterFromDate").val() !== "" && $("#counterToDate").val() !== "")
    {
        var selectedCancelFlag = $("#cancelledOptionParameter").find("option:selected").val().trim();
        $("#CounterContentArea").load('CraftWiseCounterReports.action?&counterButtonType=' + $("#counterViewInGrid").val().trim(), '?&counterFromDate=' + $("#counterFromDate").val().trim() + '&counterToDate=' + $("#counterToDate").val().trim() + '&selectedFlag=' + selectedCancelFlag.trim());
        $.getJSON("CraftWiseCounterReportView.action?filterButtonType=" + $("#counterViewInGrid").val().trim(), '?&filterFromDate=' + $("#counterFromDate").val().trim() + '&filterToDate=' + $("#counterToDate").val().trim() + '&selectedSearchByFlag=' + selectedCancelFlag.trim(), function(data)
        {
            $("#totalSumValue").text("");
            $("#totalSumValue").text(Math.round(data.counterTotalSum).toFixed(2));
            $("#counterBillTotal").text("");
            $("#counterBillTotal").text(data.totalCounterBill);
        });
    } else {
        alert("Oops!!,please select from & to date");
        $("#counterFromDate").focus();
    }
}

function CounterExportDailyReport()
{
    $.getJSON('dailyCounterReportToExcel.action', $("#CounterrwiseAccordionForm").serialize(), function(data) {
        if (data.rtn == 1) {
            window.open("Downloads/" + data.counterXcelFileName);
            //alert("Data exported successfully");
            $("#counterFromDate").val("");
            $("#counterToDate").val("");
            $("#cancelledOptionParameter").val("");
            $("#cancelledOptionParameter").val("Select");
        }
        else {
            alert("No records to display");
        }
    });

}


function load_CounterBillForm()
{
    $("#content_right").empty();
    //loadimage();
    $("#content_right").load('user_jsp/sales-home.jsp');
}




function loadCancellingReasons()
{
    $.getJSON('cancelReasonTypes.action', function(data)
    {
        $("#txtReasonForCancel").empty();
        $.each(data.cancellingReasonTos, function(i, item)
        {
            $("#txtReasonForCancel").append($("<option></option>").attr("value", item.reasonNo).text(item.reasonDesc));
        });
    });
}

function load_salesHistory()
{
    tabClk = 0;
    $("#content_right").empty();
    //loadimage();
    $("#content_right").load('SalesHistory.jsp');
}



function load_CashBill()
{
    $("#content_right").empty();
    //loadimage();
    $("#content_right").load('user_jsp/cash-Billing.jsp');
}
function loadingProgressCircle()
{
    $("#progressImage").empty();
    $('#progressImage').html('<div class="loadimage"></div><div id="waiting"><center><img src="images/ProgressBar.gif"></center></div>');
    $("#progressImage").show();
}

function loadimage() {
    $("#content_right").empty();
    $('#content_right').html('<div class="loadimgnr"></div><div id="waiter"><center><img src="images/ProgressBar.gif"></center></div>');
    $("#content_right").show();
}

function showCustInfo()
{
    xWinOpen('CustomerInfo.jsp');
}

function showSettingsPage()
{
    settingsWinOpen('adminSettings.jsp');
}

var xChildWindow = null;
function xWinOpen(sUrl)
{
    var features = "left=500,top=120,width=400,height=420,location=false,directories=false,toolbar=false,menubars=false,scrollbars=false,resize=false";
    if (xChildWindow && !xChildWindow.closed) {
        xChildWindow.location.href = sUrl;
    }
    else {
        xChildWindow = window.open(sUrl, "CustomerInfo.jsp", features);
    }
    xChildWindow.focus();
    return false;
}

var xSettingsChildWindow = null;
function settingsWinOpen(sUrl)
{
    var features = "left=80,top=135,width=1100,height=420,location=false,directories=false,toolbar=false,menubars=false,scrollbars=false,resize=false";
    if (xSettingsChildWindow && !xSettingsChildWindow.closed) {
        xSettingsChildWindow.location.href = sUrl;
    }
    else {
        xSettingsChildWindow = window.open(sUrl, "adminSettings.jsp", features);
    }
    xSettingsChildWindow.focus();
    return false;
}


function getInfoCounterBill() {
    $("#counterbillDetail").hide();
    if ($("#txtCancellingCounterBillNo").val() !== "") {
        $.getJSON("getInfoCounterBillAction.action?txtCancellingCounterBillNo=" + $("#txtCancellingCounterBillNo").val().trim(), function(data)
        {
            if (data.counterBillInfoVal > 0) {
                $("#tb").empty();
                $("#nme").text(data.empName);
                $("#date").text(data.date);
                $("#packval").text(data.packValue);
                $("#totalamt").text(data.totalAmt);
                $("#textamt").text("Rupees" + " " + data.textAmt);
                if (data.paymentType != null) {
                    $("#cashCollectedAs").text(data.paymentType);
                    document.getElementById('cashCollectedAs').style.color = "black";
                } else {
                    $("#cashCollectedAs").text("CASH NOT COLLECTED");
                    document.getElementById('cashCollectedAs').style.color = "red";
                }
                if (data.cashProcessedBy != null) {
                    $("#cashProcessedBy").text(data.cashProcessedBy);
                    document.getElementById('cashProcessedBy').style.color = "black";
                } else {
                    $("#cashProcessedBy").text("CASH NOT COLLECTED");
                    document.getElementById('cashProcessedBy').style.color = "red";
                }

                var count = 0;
                $.each(data.counterBillList, function(i, item)
                {
                    count = count + 1;
                    var dynamicTr = '<tr id="cashierDetail-tr-id" class="cashierDetail-tr-class"><td>' + count + '</td>  <td>' + item.matrialNo + '</td><td>' + item.vendor + '</td><td>' + item.description + ' </td><td style="text-align: right;">' + Math.round(item.qty).toFixed(2) + '</td><td style="text-align: right;">' + Math.round(item.rate).toFixed(2) + '</td> <td>' + Math.round(item.value).toFixed(2) + '</td>   <td style="text-align: right;">' + Math.round(item.discountValue).toFixed(2) + '<br> ' + Math.round(item.discountPercentage).toFixed(2) + '%</td>  <td style="text-align: right;">' + parseFloat(item.vattValue).toFixed(2) + ' <br> ' + parseFloat(item.vattPercentage).toFixed(2) + '%</td> <td style="text-align: right;">' + Math.round(item.calculatedValue).toFixed(2) + '</td></tr>';
                    $("#tb").append(dynamicTr);
                });
                $("#counterbillDetail").show();
            }
            else {
                alert("Enter valid Sales number");
                $("#txtCancellingCounterBillNo").focus();
            }
        });
    }
    else {
        alert("Oop's Counter Bill Number Missing..");
        $("#txtCancellingCounterBillNo").focus();
    }
}
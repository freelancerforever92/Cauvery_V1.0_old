/*CANCELLING PROCESS FOR COUNTER-BILL*/
function checkSalesOrderCancelStatus()
{
    var rcv_CancelCounterBillNo = $("#txtCancellingCounterBillNo").val();
    if (rcv_CancelCounterBillNo !== "")
    {
        $.getJSON('counterBillNoCancelStatus.action?cancelSalesOrderNo=' + rcv_CancelCounterBillNo, function(data)
        {
            if (data.dbOrderNoCancelStatus == 1)
            {
                alert("SalesOrderNo Already Cancelled");
                $("#txtReasonForCancel").val("");
                $("#txtCancellingCounterBillNo").val("");
                $("#txtCancellingCounterBillNo").focus();
            }
        });
    }
}

function cancelSalesOrderDetails()
{
    var rcvdReason = $("#txtReasonForCancel").val().trim();
    var rcvdReasonNo = $("#txtReasonForCancel").find("option:selected").val().trim();
    var rcvCancelOrderNo = $("#txtCancellingCounterBillNo").val().trim();
    if (rcvCancelOrderNo !== "")
    {
        if (rcvdReason !== "")
        {
            if (confirm("Cancel This SalesOrder ?"))//COMFIRMING WIHT THE USER FOR REMOVAL OF THE ROW
            {
                $.getJSON('cancelSalesOrderNo.action?cancelSalesOrderNo=' + rcvCancelOrderNo + "&cancellingReasonSno=" + rcvdReasonNo, function(data)
                {
                    if (data.isCashCollected === false)
                    {
                        if (data.isCancelledBy === true)
                        {
                            if (data.cancelledInfoStatus === true)
                            {
                                alert("SalesOrder Has Been Cancelled Successfully");
                                $("#txtReasonForCancel").val("");
                                $("#txtCancellingCounterBillNo").val("");
                                $("#content_right").empty();
                                //Display_LoginName();
                                $("#content_right").load('user_jsp/sales-home.jsp');
                            }
                            else if (data.cancelledInfoStatus === false)
                            {
                                alert("Oops!,Process Failure..Try Again");
                            }
                        }
                        else if (data.isCancelledBy === false)
                        {
                            alert("Oops!,Process failure " + rcvCancelOrderNo + " has been creadted by " + data.orderCreatedBy.trim() + "");
                            $("#txtCancellingCounterBillNo").val("");
                        }
                    }
                    else if (data.isCashCollected === true) {
                        //alert("Oops!,Cash Payment for " + rcvCancelOrderNo + " is happend,Unable to cancel it..");
                        alert("Oops!,Cash is already collected for " + rcvCancelOrderNo.trim() + "");
                        $("#txtCancellingCounterBillNo").val("");
                        $("#txtCancellingCounterBillNo").focus();
                    }
                });
            }
        }
        else
        {
            alert("Reason For Cancelling Is Mandatory");
            $("#txtReasonForCancel").focus();
        }
    }
    else
    {
        alert("SalesOrderNo Is Missing..!");
        $("#txtCancellingCounterBillNo").focus();
    }
}

/*CANCELLING PROCESS FOR COUNTER-BILL*/
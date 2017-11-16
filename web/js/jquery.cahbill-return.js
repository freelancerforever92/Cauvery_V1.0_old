$(document).ready(function()
{

});

function fetchSalesOrderDetails()
{
    var rcvd_ReturnCounterBillNo = "";
    rcvd_ReturnCounterBillNo = $("#txtCounterBillNoReturn").val().trim();
    if (rcvd_ReturnCounterBillNo !== "")
    {
        $.get('fetchingSalesOrderDetailsCashbill.action?salesOrderNo=' + rcvd_ReturnCounterBillNo, function(data)
        {
            $("#pruchase_tbody").append(data);
        });
    }
}
function totalCouponValue()
{
    var TotalValue = 0;
    var CouponTableSize = $('#coupon-sales-table-body tr').length;
    if (CouponTableSize >= 1)
    {
        for (var i = 1; i <= CouponTableSize; i++)
        {
            TotalValue = parseInt(TotalValue) + parseInt($("#couponRate" + i).text());
        }
        $("#couponTotalAmt").val("");
        $("#couponTotalAmt").val(TotalValue);
    }
}

function validateCustomerInfo()
{
    var custFname = $("#fname");
    var custLname = $("#lname");
    var custAddrs = $("#addrss");

    if (custFname.val() === "")
    {
        $("#div-customerInfo").show();
        //alert("Customer Name Is Missing");
        custFname.focus();
        return false;
    }
    /*if (custLname.val() === "")
     {
     alert("Coupon From Value Is Missing");
     custLname.focus();
     return false;
     }
     
     if (custAddrs.val() === "")
     {
     alert("Coupon From Value Is Missing");
     custAddrs.focus();
     return false;
     }*/
    return  true;
}



function sellCoupons()
{
    var totalCouponTableSize = $('#coupon-sales-table-body tr').length;
    var packedValue = "";
    var aryCouponType = [], aryCouponNo = [];
    var tot = 0;
    var couponTyp = "";
    var couponNos = "";
    var insCustFname;
    var insCustLname;
    var insCustAddrs;
    var couponId = $("#txtCashBillOrderno").text();
    if (validateCustomerInfo())
    {
        insCustFname = $("#fname");
        insCustLname = $("#lname");
        insCustAddrs = $("#addrss");
        if (insCustLname.val() === "")
        {
            insCustLname = "";
        }
        else
        {
            insCustLname = insCustLname.val();
        }
        if (insCustAddrs.val() === "")
        {
            insCustAddrs = "";
        }
        else
        {
            insCustAddrs = insCustAddrs.val();
        }

        if (totalCouponTableSize >= 1)
        {
            for (var i = 1; i <= totalCouponTableSize; i++)
            {
                couponTyp = $('#' + "couponType" + i).text();
                couponNos = $('#' + "couponNo" + i).text();

                aryCouponType.push(couponTyp);
                aryCouponNo.push(couponNos);

                tot = parseInt(tot) + parseInt($("#couponRate" + i).text());
            }

            for (var j = 0; j < totalCouponTableSize; j++)
            {
                if (packedValue === "")
                {
                    packedValue = insCustFname.val() + "," + couponId + "," + insCustLname + "*" + insCustAddrs + "*" + aryCouponType[j].trim() + "*" + aryCouponNo[j].trim();
                    //alert("Single value :  " + packedValue);
                }
                else
                {
                    packedValue = packedValue + "," + aryCouponType[j].trim() + "*" + aryCouponNo[j].trim();
                    //alert("multiple value :  " + packedValue);
                }
            }
            //alert("Packed value :  " + packedValue);
            if (packedValue !== "")
            {
                pushSellingCouponDetails(packedValue, totalCouponTableSize, couponId);
            }
            $("#couponTotalAmt").val("");
            $("#couponTotalAmt").val(tot);
        }
    }
    else
    {
        alert("Customer Data Is Missing...");
    }
}


function pushSellingCouponDetails(rcvPval, tableRowCount, rcvCouponId)
{
    //var seprateCouponSalesOrderNo=rcvCouponId.split('.');
    //var onlyCouponSalesOrderNo=seprateCouponSalesOrderNo[1];
    if (rcvPval !== "" || rcvPval !== null)
    {
        if (tableRowCount !== "" || tableRowCount !== null || tableRowCount !== 0)
        {
            $.getJSON("sellCoupons.action?sellingCouponData=" + rcvPval, function(data)
            {
                if (data.couponCreatedStatus == "insertedCoupon")
                {
                    //xSalesPrintPage("print_jsp/coupon-sales-print.jsp?couponSalesNo=" +onlyCouponSalesOrderNo);//CALLING coupon-sales-print FOR PRINTING THE COUPON-SALES DETAILS
                    xSalesPrintPage("print_jsp/coupon-sales-print.jsp?couponSalesNo=" + rcvCouponId);//CALLING coupon-sales-print FOR PRINTING THE COUPON-SALES DETAILS
                    couponSalesOrdeNo();
                    clearCreatedCouponInfo();
                    load_CashBillSalesForm();
                }
            });
        }
    }
}

function couponSalesOrdeNo()
{
    $.getJSON('couponSalesBillNo.action', function(data)
    {
        $("#txtlog_empid").text("");
        $("#txtCashBillOrderno").text("");
        //$("#txtCashBillOrderno").text("Coupon." + data.couponSrlNo.trim());
        $("#txtCashBillOrderno").text(data.couponSrlNo.trim());
    });
}

function clearCreatedCouponInfo()
{
    var totalCouponTableSize = $('#coupon-sales-table-body tr').length;
    $('#coupon-sales-table-body tr').remove();


}



var xSalesChildWindow = null;
function xSalesPrintPage(sUrl)
{
    var features = "left=500,top=120,width=400,height=420,location=false,directories=false,toolbar=false,menubars=false,scrollbars=false,resize=false";
    if (xSalesChildWindow && !xSalesChildWindow.closed) {
        xSalesChildWindow.location.href = sUrl;
    }
    else {
        xSalesChildWindow = window.open(sUrl, "CustomerInfo.jsp", features);
    }
    xSalesChildWindow.focus();
    return false;
}

function removeSelectedCoupon(couponThisValue, couponId)
{
    var a = $(".chk:checked").size();
    if (a > 1)
    {
        var chkArray = [];
        if (confirm("Remove This Coupon No ? "))
        {
            var rmvCouponId = 1;
            $(".chk:checked").each(function() {
                chkArray.push($(this).val());
                $("#couponTr" + chkArray[rmvCouponId - 1]).remove();
                rmvCouponId++;
            });
            reOrdering_CouponTableValues();
            totalCouponValue();
        }
    }
    else
    {
        if (confirm("Remove This Coupon No ? "))
        {
            $("#couponTr" + couponId).remove();
            reOrdering_CouponTableValues();
            totalCouponValue();
        }
    }
}


function reOrdering_CouponTableValues()
{
    var couponTr = $('.classCouponTr');
    var indxCouponTr = 1;
    $.each(couponTr, function()
    {
        $(this).attr('id', 'couponTr' + indxCouponTr);
        $(this).attr('name', 'couponTr' + indxCouponTr);
        indxCouponTr++;
    });

    var checkBoxTr = $('.classCheckBoxCouponNo');
    var indxChecboxTr = 1;
    $.each(checkBoxTr, function()
    {
        $(this).attr('id', 'couponTr' + indxChecboxTr);
        $(this).attr('name', 'couponTr' + indxChecboxTr);
        indxChecboxTr++;
    });

    var couponTypeTr = $('.classCouponType');
    var indxCouponType = 1;
    $.each(couponTypeTr, function()
    {
        $(this).attr('id', 'couponType' + indxCouponType);
        $(this).attr('name', 'couponType' + indxCouponType);
        indxCouponType++;
    });

    var couponNoTr = $('.classCouponNo');
    var indxCouponNo = 1;
    $.each(couponNoTr, function()
    {
        $(this).attr('id', 'couponNo' + indxCouponNo);
        $(this).attr('name', 'couponNo' + indxCouponNo);
        indxCouponNo++;
    });

    var couponDescpTr = $('.classCouponDescp');
    var indxCouponDescp = 1;
    $.each(couponDescpTr, function()
    {
        $(this).attr('id', 'couponDescp' + indxCouponDescp);
        $(this).attr('name', 'couponDescp' + indxCouponDescp);
        indxCouponDescp++;
    });

    var couponRateTr = $('.classCouponRate');
    var indxCouponRate = 1;
    $.each(couponRateTr, function()
    {
        $(this).attr('id', 'couponRate' + indxCouponRate);
        $(this).attr('name', 'couponRate' + indxCouponRate);
        indxCouponRate++;
    });
}
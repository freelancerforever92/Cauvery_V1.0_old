$(document).ready(function()
{
    clearAll();

});
function createCouponValidation()
{
    var coupontype = $("#couponType");
    var couponNo = $("#couponNo");
    var couponDescription = $("#couponDescription");
    var couponRate = $("#couponRate");
    if (coupontype.val() === "")
    {
        alert("Coupon Type Is Missing");
        coupontype.focus();
        return false;
    }

    if (couponNo.val() === "")
    {
        alert("Coupon No Is Missing");
        couponNo.focus();
        return false;
    }
    if (couponDescription.val() === "")
    {
        alert("Coupon Description Is Missing");
        couponDescription.focus();

        return false;
    }
    if (couponRate === "")
    {
        alert("Coupon Rate Is Missing");
        couponRate.focus();
        return false;
    }
    return true;
}


function createCoupon()
{

    if (createCouponValidation())
    {
        $.getJSON('creatCouponDetails.action', $("#couponCreationForm").serialize(), function(data)
        {
            if (data.isError > 0)
            {
                alert("Coupon Has Created Sucessfully");
                clearAll();
            }
            else
            {
                alert("!Opps Some DB Error -- Try Again--");
            }
        });
    }
}

function couponTypesDropDown()
{
    $.getJSON('couponTypesList.action', function(data)
    {
        $.each(data.couponTypeList, function(i, item)
        {
            $("#couponType").append($("<option></option>").attr("value", item.couponId).text(item.couponType));
        });
    });
}

function couponlistValidate()
{
    var couponNoFtom = $("#couponNoFrom");
    var couponNoTo = $("#couponNoTo");
    if (couponNoFtom.val() === "")
    {
        alert("Coupon From Value Is Missing");
        couponNoFtom.focus();
        return false;
    }
    if (couponNoTo.val() === "")
    {
        alert("Coupon to Value  Is Missing");
        couponNoTo.focus();
        return false;
    }
    return  true;
}


function listCoupons()
{
    var couponTyp = "";
    if (couponlistValidate())
    {
        var selectedCouponTypeId = $("#couponType").find("option:selected").val();
        if (selectedCouponTypeId !== "" || selectedCouponTypeId !== null)
        {
            $.get('couponDetails.action?couponId=' + selectedCouponTypeId, $("#couponSalesForm").serialize(), function(data)
            {
                $("#coupon-sales-table-body").append(data);
                reOrdering_CouponTableValues();
                calculateTotalCouponValue();
                clearAllValues();
            });
        }
    }
}

function calculateTotalCouponValue()
{
    var tot = 0;
    var totalCouponTableSize = $('#coupon-sales-table-body tr').length;
    if (totalCouponTableSize >= 1)
    {
        for (var i = 1; i <= totalCouponTableSize; i++)
        {
            tot = parseInt(tot) + parseInt($("#couponRate" + i).text());
        }
        $("#couponTotalAmt").val("");
        $("#couponTotalAmt").val(tot);
        $("#couponTotalNos").val("");
        $("#couponTotalNos").val(totalCouponTableSize);
    }
}

function clearAllValues()
{
    $("#couponType").val("Select");
    $("#couponFrom").val("");
    $("#couponTo").val("");
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

function coustomerData()
{
    $("#fname").val("");
    $("#lname").val("");
    $("#addrss").val("");
}

function clearAll()
{
    $("#couponType").val("");
    $("#couponNo").val("");
    $("#couponDescription").val("");
    $("#couponRate").val("");
}
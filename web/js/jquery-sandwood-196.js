var swInitialVatValue = 0;
var swCalculatedDisCountValue = 0;
var indexValue = 1;
$(document).ready(function()
{
    $('#SwMaterialId').focus();
    $('#SwMaterialId').keypress("keypress", function(e) {
        if (e.keyCode === 13) {
            $("#sWVendorlId").focus();
        }
    });

    $('#sWVendorlId').keypress("keypress", function(e) {
        if (e.keyCode === 13) {
            $("#sWQuantityId").focus();
        }
    });

    $('#sWQuantityId').keypress("keypress", function(e)
    {
        var materialIdSw = $('#SwMaterialId').val();
        var vendorIdSw = $('#sWVendorlId').val();
        var quantitySw = $('#sWQuantityId').val();
        if (e.keyCode === 13)
        {
            if (materialIdSw !== "")
            {
                if (vendorIdSw !== "")
                {
                    if (quantitySw !== "")
                    {
                        if (quantitySw > 0)
                        {
                            loadSwMaterialDetails();
                        }
                        else if (quantitySw <= 0)
                        {
                            alert("Invaild Quantity");
                            $('#sWQuantityId').val("");
                            $('#sWQuantityId').focus();
                        }
                    }
                    else
                    {
                        alert("Oops,Quantity Id Is Missing");
                        $('#sWQuantityId').focus();
                    }
                }
                else
                {
                    alert("Oops,Vendor Id Is Missing");
                    $('#sWVendorlId').focus();
                }
            }
            else
            {
                alert("Oops,Material Id Is Missing");
                $('#SwMaterialId').focus();
            }
        }
    });
});


function validateSwMaterial()
{
    var swMaterialId = $('#SwMaterialId').val().trim();
    if (swMaterialId !== "")
    {
        $.getJSON('sandalWoodValidateMaterial.action?materialId=' + swMaterialId, function(data)
        {
            if (data.materialCraftName == "SB")
            {

            }
            else
            {
                alert("Oops,MaterialId Is Not Billets");
                $('#SwMaterialId').val("");
                $('#SwMaterialId').focus();
            }
        });
    }
}

function validateVendor()
{
    var vendorId = $("#sWVendorlId").val().trim();
    if (vendorId !== "")
    {
        $.getJSON('sandalWoodValidateVendor.action?vendorId=' + vendorId, function(data)
        {
            if (data.vendorCountValue > 0)
            {
            }
            else if (data.vendorCountValue <= 0)
            {
                alert("In-vaild Vendor-Id");
                $("#sWVendorlId").val("");
                $("#sWVendorlId").focus();
            }
        });
    }
}

function loadSwMaterialDetails()
{
    var swMatrialId = $('#SwMaterialId').val();
    var swVendorId = $('#sWVendorlId').val();
    var swQuantity = $('#sWQuantityId').val();
    var dbSwMaterialPrc;
    var dbSwMaterialDescription;
    var dbSwMaterialTaxValue;
    var swTotalValue;
    var swGrandTotalValue = "";
    var hiddenTotalVal = $("#swGrandTotal").val().trim();
    $.getJSON('fetchSwDetails.action', $("#SandalWoodForm").serialize(), function(data)
    {
        dbSwMaterialPrc = data.swMaterialPrice;
        dbSwMaterialDescription = data.swMaterialDescription;
        dbSwMaterialTaxValue = data.swMaterialTaxValue;
        swTotalValue = parseFloat(dbSwMaterialPrc) * parseFloat(swQuantity);
        swTotalValue = swVatCalculation(swTotalValue, dbSwMaterialTaxValue);

        insSwMaterialDetails(swMatrialId, dbSwMaterialDescription, swQuantity, dbSwMaterialPrc, swTotalValue, dbSwMaterialTaxValue, swVendorId);

        if ($("#swGrandTotal").val() === "")
        {
            $("#swGrandTotal").val(swTotalValue);
            $("#swBillAmt").val(swTotalValue);
        }
        else if ($("#swGrandTotal").val() !== "")
        {
            swGrandTotalValue = parseFloat(hiddenTotalVal) + parseFloat(swTotalValue);
            $("#swGrandTotal").val("");
            $("#swBillAmt").val("");
            $("#swGrandTotal").val(parseFloat(swGrandTotalValue).toFixed(2));
            $("#swBillAmt").val(parseFloat(swGrandTotalValue).toFixed(2));
        }
//        $("#SwMaterialId").val("");
//        $("#sWVendorlId").val("");
//        $("#sWQuantityId").val("");
    });
}

function swVatCalculation(billValue, vatPercentageValue)
{
    var rcvSwDiscountValue = $("#sWDiscountPercentage").val().trim();
    var billAmtWithDiscount;
    var initialBillWithVatValue;

    if (rcvSwDiscountValue !== "" && rcvSwDiscountValue !== 0)
    {
        swInitialVatValue = 0;
        swCalculatedDisCountValue = 0;
        if (rcvSwDiscountValue > 0)
        {
            swCalculatedDisCountValue = Math.round(billValue * rcvSwDiscountValue / 100);
            billAmtWithDiscount = Math.round(billValue - swCalculatedDisCountValue);
            swInitialVatValue = Math.round(parseFloat(billAmtWithDiscount) * parseFloat(vatPercentageValue) / 100);
            initialBillWithVatValue = Math.round(billAmtWithDiscount + swInitialVatValue);
            initialBillWithVatValue = initialBillWithVatValue.toFixed(2);
        }
    }
    else if (rcvSwDiscountValue === "")
    {
        swInitialVatValue = 0;
        swCalculatedDisCountValue = 0;
        swInitialVatValue = Math.round(parseFloat(billValue) * parseFloat(vatPercentageValue) / 100);
        initialBillWithVatValue = Math.round(billValue + swInitialVatValue);
        initialBillWithVatValue = initialBillWithVatValue.toFixed(2);
    }
    return initialBillWithVatValue;
}

function swDisCountCalculation()
{
    var sandalWoodTotalValue = 0;
    var sandalWoodRowVatValue;
    var sandalWoodRowPriceValue;
    var sandalWoodRowDiscountValue;
    var sandalWoodRowPriceValueWithVat;
    var sandalWoodRowPriceValueWithDiscount;

    var sandalWoodGrandTotalAmt = $("#swGrandTotal").val().trim();
    var sandalWoodpckingCharge = $("#swPackingCharge").val().trim();
    var sandalWoodTableRowCount = $('#sandalWood-pruchase_tbody tr').length;
    var sandalWoodDiscountPercentage = $("#sWDiscountPercentage").val().trim();

    if (sandalWoodGrandTotalAmt !== "")
    {
        if (sandalWoodDiscountPercentage !== "")
        {
            if (sandalWoodDiscountPercentage > 0)
            {
                if (!(sandalWoodDiscountPercentage >= 5))
                {
                    if ((document.getElementById('light').style.display == "none") && (document.getElementById('fade').style.display == "none"))
                    {
                        $(".swDiscountApproval").hide();
                        $("#swTxtApproval").val(".");
                        $("#sandalWoodApprovalText").val("");
                    }

                    if (sandalWoodTableRowCount > 1)
                    {
                        sandalWoodTotalValue = 0;
                        for (var prodLopVal = 1; prodLopVal <= sandalWoodTableRowCount; prodLopVal++)
                        {
                            sandalWoodRowPriceValue = Math.round($("#dynamicTxtQuantity" + prodLopVal).val() * $("#dynamicTxtRate" + prodLopVal).val());

                            sandalWoodRowDiscountValue = Math.round(sandalWoodRowPriceValue * sandalWoodDiscountPercentage / 100);

                            $("#dynamicTxtValue" + prodLopVal).val("");
                            $("#dynamicTxtValue" + prodLopVal).val(sandalWoodRowDiscountValue);

                            sandalWoodRowPriceValueWithDiscount = Math.round(sandalWoodRowPriceValue - sandalWoodRowDiscountValue);

                            sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValueWithDiscount * $("#dynamicTxtVat" + prodLopVal).val() / 100);

                            $("#dynamicTxtVatValue" + prodLopVal).val("");
                            $("#dynamicTxtVatValue" + prodLopVal).val(sandalWoodRowVatValue);


                            sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValueWithDiscount + sandalWoodRowVatValue);

                            if (sandalWoodRowPriceValueWithVat > 0)
                            {
                                $("#dynamicTxtValue" + prodLopVal).val("");
                                $("#dynamicTxtValue" + prodLopVal).val(sandalWoodRowPriceValueWithVat.toFixed(2));

                                sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicTxtValue" + prodLopVal).val()));

                                if (sandalWoodpckingCharge != "" || sandalWoodpckingCharge != 0)
                                {
                                    var totWitPckageValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt(sandalWoodpckingCharge));
                                    $("#swGrandTotal").val("");
                                    $("#swGrandTotal").val(totWitPckageValue.toFixed(2));

                                    $("#swBillAmt").val("");
                                    $("#swBillAmt").val(totWitPckageValue.toFixed(2));
                                }
                                else
                                {
                                    $("#swGrandTotal").val("");
                                    $("#swGrandTotal").val(sandalWoodTotalValue.toFixed(2));

                                    $("#swBillAmt").val("");
                                    $("#swBillAmt").val(sandalWoodTotalValue.toFixed(2));
                                }
                            }
                            else
                            {
                                $("#sWDiscountPercentage").val("");
                                sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValue * $("#dynamicTxtVatValue" + prodLopVal).val() / 100);
                                sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValue + sandalWoodRowVatValue);

                                $("#dynamicTxtValue" + prodLopVal).val("");
                                $("#dynamicTxtValue" + prodLopVal).val(sandalWoodRowPriceValueWithVat.toFixed(2));

                                sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicTxtValue" + prodLopVal).val()));
                                //alert("-ve  :  " + sandalWoodTotalValue);

                                $("#swGrandTotal").val("");
                                $("#swGrandTotal").val(sandalWoodTotalValue.toFixed(2));

                                $("#swBillAmt").val("");
                                $("#swBillAmt").val(sandalWoodTotalValue.toFixed(2));
                            }
                        }
                    }
                    else if (sandalWoodTableRowCount == 1)
                    {
                        sandalWoodTotalValue = 0;
                        sandalWoodRowPriceValue = Math.round($("#dynamicTxtQuantity1").val() * $("#dynamicTxtRate1").val());
                        sandalWoodRowDiscountValue = Math.round(sandalWoodRowPriceValue * sandalWoodDiscountPercentage / 100);

                        $("#dynamicTxtValue1").val("");
                        $("#dynamicTxtValue1").val(sandalWoodRowDiscountValue);

                        sandalWoodRowPriceValueWithDiscount = Math.round(sandalWoodRowPriceValue - sandalWoodRowDiscountValue);

                        sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValueWithDiscount * $("#dynamicTxtVat1").val() / 100);

                        $("#dynamicTxtVatValue1").val("");
                        $("#dynamicTxtVatValue1").val(sandalWoodRowVatValue);

                        sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValueWithDiscount + sandalWoodRowVatValue);

                        if (sandalWoodRowPriceValueWithVat > 0)
                        {
                            $("#dynamicTxtValue1").val("");
                            $("#dynamicTxtValue1").val(sandalWoodRowPriceValueWithVat.toFixed(2));

                            sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicTxtValue1").val()));

                            if (sandalWoodpckingCharge != "" || sandalWoodpckingCharge != 0)
                            {
                                var totWitPckageValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt(sandalWoodpckingCharge));
                                $("#swGrandTotal").val("");
                                $("#swGrandTotal").val(totWitPckageValue.toFixed(2));

                                $("#swBillAmt").val("");
                                $("#swBillAmt").val(totWitPckageValue.toFixed(2));
                            }
                            else
                            {
                                $("#swGrandTotal").val("");
                                $("#swGrandTotal").val(sandalWoodTotalValue.toFixed(2));

                                $("#swBillAmt").val("");
                                $("#swBillAmt").val(sandalWoodTotalValue.toFixed(2));
                            }
                        }
                        else
                        {
                            sandalWoodTotalValue = 0;
                            $("#sWDiscountPercentage").val("");
                            sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValue * $("#dynamicTxtVatValue1").val() / 100);
                            sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValue + sandalWoodRowVatValue);

                            $("#dynamicTxtValue1").val("");
                            $("#dynamicTxtValue1").val(sandalWoodRowPriceValueWithVat.toFixed(2));

                            sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicTxtValue1").val()));

                            $("#swGrandTotal").val("");
                            $("#swGrandTotal").val(sandalWoodTotalValue.toFixed(2));

                            $("#swBillAmt").val("");
                            $("#swBillAmt").val(sandalWoodTotalValue.toFixed(2));
                        }
                    }
                }
                else
                {
                    $("#sWDiscountPercentage").focus();
                    //$(".disApproval").show();
                    //$("#txtApproval").val("Oral approval for discount is present");
                    $("#txtApproval").val("");
                    $("#discountApprovalText").val("");
                    document.getElementById('light').style.display = 'block';
                    document.getElementById('fade').style.display = 'block';

                    if ((document.getElementById('light').style.display == "none") && (document.getElementById('fade').style.display == "none"))
                    {
                        $(".swDiscountApproval").hide();
                        $("#swTxtApproval").val(".");
                        $("#sandalWoodApprovalText").val("");
                    }

                    if (sandalWoodTableRowCount > 1)
                    {
                        sandalWoodTotalValue = 0;
                        for (var prodLopVal = 1; prodLopVal <= sandalWoodTableRowCount; prodLopVal++)
                        {
                            sandalWoodRowPriceValue = Math.round($("#dynamicTxtQuantity" + prodLopVal).val() * $("#dynamicTxtRate" + prodLopVal).val());

                            sandalWoodRowDiscountValue = Math.round(sandalWoodRowPriceValue * sandalWoodDiscountPercentage / 100);

                            $("#dynamicTxtValue" + prodLopVal).val("");
                            $("#dynamicTxtValue" + prodLopVal).val(sandalWoodRowDiscountValue);

                            sandalWoodRowPriceValueWithDiscount = Math.round(sandalWoodRowPriceValue - sandalWoodRowDiscountValue);

                            sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValueWithDiscount * $("#dynamicTxtVat" + prodLopVal).val() / 100);

                            $("#dynamicTxtVatValue" + prodLopVal).val("");
                            $("#dynamicTxtVatValue" + prodLopVal).val(sandalWoodRowVatValue);


                            sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValueWithDiscount + sandalWoodRowVatValue);

                            if (sandalWoodRowPriceValueWithVat > 0)
                            {
                                $("#dynamicTxtValue" + prodLopVal).val("");
                                $("#dynamicTxtValue" + prodLopVal).val(sandalWoodRowPriceValueWithVat.toFixed(2));

                                sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicTxtValue" + prodLopVal).val()));

                                if (sandalWoodpckingCharge != "" || sandalWoodpckingCharge != 0)
                                {
                                    var totWitPckageValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt(sandalWoodpckingCharge));
                                    $("#swGrandTotal").val("");
                                    $("#swGrandTotal").val(totWitPckageValue.toFixed(2));

                                    $("#swBillAmt").val("");
                                    $("#swBillAmt").val(totWitPckageValue.toFixed(2));
                                }
                                else
                                {
                                    $("#swGrandTotal").val("");
                                    $("#swGrandTotal").val(sandalWoodTotalValue.toFixed(2));

                                    $("#swBillAmt").val("");
                                    $("#swBillAmt").val(sandalWoodTotalValue.toFixed(2));
                                }
                            }
                            else
                            {
                                $("#sWDiscountPercentage").val("");
                                sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValue * $("#dynamicTxtVatValue" + prodLopVal).val() / 100);
                                sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValue + sandalWoodRowVatValue);

                                $("#dynamicTxtValue" + prodLopVal).val("");
                                $("#dynamicTxtValue" + prodLopVal).val(sandalWoodRowPriceValueWithVat.toFixed(2));

                                sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicTxtValue" + prodLopVal).val()));
                                //alert("-ve  :  " + sandalWoodTotalValue);

                                $("#swGrandTotal").val("");
                                $("#swGrandTotal").val(sandalWoodTotalValue.toFixed(2));

                                $("#swBillAmt").val("");
                                $("#swBillAmt").val(sandalWoodTotalValue.toFixed(2));
                            }
                        }
                    }
                    else if (sandalWoodTableRowCount == 1)
                    {
                        sandalWoodTotalValue = 0;
                        sandalWoodRowPriceValue = Math.round($("#dynamicTxtQuantity1").val() * $("#dynamicTxtRate1").val());
                        sandalWoodRowDiscountValue = Math.round(sandalWoodRowPriceValue * sandalWoodDiscountPercentage / 100);

                        $("#dynamicTxtValue1").val("");
                        $("#dynamicTxtValue1").val(sandalWoodRowDiscountValue);

                        sandalWoodRowPriceValueWithDiscount = Math.round(sandalWoodRowPriceValue - sandalWoodRowDiscountValue);

                        sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValueWithDiscount * $("#dynamicTxtVat1").val() / 100);

                        $("#dynamicTxtVatValue1").val("");
                        $("#dynamicTxtVatValue1").val(sandalWoodRowVatValue);

                        sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValueWithDiscount + sandalWoodRowVatValue);

                        if (sandalWoodRowPriceValueWithVat > 0)
                        {
                            $("#dynamicTxtValue1").val("");
                            $("#dynamicTxtValue1").val(sandalWoodRowPriceValueWithVat.toFixed(2));

                            sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicTxtValue1").val()));

                            if (sandalWoodpckingCharge != "" || sandalWoodpckingCharge != 0)
                            {
                                var totWitPckageValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt(sandalWoodpckingCharge));
                                $("#swGrandTotal").val("");
                                $("#swGrandTotal").val(totWitPckageValue.toFixed(2));

                                $("#swBillAmt").val("");
                                $("#swBillAmt").val(totWitPckageValue.toFixed(2));
                            }
                            else
                            {
                                $("#swGrandTotal").val("");
                                $("#swGrandTotal").val(sandalWoodTotalValue.toFixed(2));

                                $("#swBillAmt").val("");
                                $("#swBillAmt").val(sandalWoodTotalValue.toFixed(2));
                            }
                        }
                        else
                        {
                            sandalWoodTotalValue = 0;
                            $("#sWDiscountPercentage").val("");
                            sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValue * $("#dynamicTxtVatValue1").val() / 100);
                            sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValue + sandalWoodRowVatValue);

                            $("#dynamicTxtValue1").val("");
                            $("#dynamicTxtValue1").val(sandalWoodRowPriceValueWithVat.toFixed(2));

                            sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicTxtValue1").val()));

                            $("#swGrandTotal").val("");
                            $("#swGrandTotal").val(sandalWoodTotalValue.toFixed(2));

                            $("#swBillAmt").val("");
                            $("#swBillAmt").val(sandalWoodTotalValue.toFixed(2));
                        }
                    }
                }
            }
        }
        else
        {
            if (sandalWoodTableRowCount > 1)
            {
                sandalWoodTotalValue = 0;
                for (var prodLopVal2 = 1; prodLopVal2 <= sandalWoodTableRowCount; prodLopVal2++)
                {
                    sandalWoodRowPriceValue = Math.round($("#dynamicTxtQuantity" + prodLopVal2).val() * $("#dynamicTxtRate" + prodLopVal2).val());
                    sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValue * $("#dynamicTxtVat" + prodLopVal2).val() / 100);

                    $("#dynamicTxtVatValue" + prodLopVal2).val("");
                    $("#dynamicTxtVatValue" + prodLopVal2).val(sandalWoodRowVatValue);

                    sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValue + sandalWoodRowVatValue);

                    $("#dynamicTxtValue" + prodLopVal2).val("");
                    $("#dynamicTxtValue" + prodLopVal2).val(0);

                    if (sandalWoodRowPriceValueWithVat > 0)
                    {
                        $("#dynamicTxtValue" + prodLopVal2).val("");
                        $("#dynamicTxtValue" + prodLopVal2).val(sandalWoodRowPriceValueWithVat.toFixed(2));

                        sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicTxtValue" + prodLopVal2).val()));

                        $("#swGrandTotal").val("");
                        $("#swGrandTotal").val(sandalWoodTotalValue.toFixed(2));

                        $("#swBillAmt").val("");
                        $("#swBillAmt").val(sandalWoodTotalValue.toFixed(2));
                    }
                    else
                    {
                        alert("Undefined Vat Value");
                    }
                }
            }
            else if (sandalWoodTableRowCount == 1)
            {
                sandalWoodTotalValue = 0;

                $("#txtDiscountValue1").val("");
                $("#txtDiscountValue1").val(0);

                sandalWoodRowPriceValue = Math.round($("#dynamicTxtQuantity1").val() * $("#dynamicTxtRate1").val());
                sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValue * $("#dynamicTxtVat1").val() / 100);

                $("#dynamicTxtVatValue1").val("");
                $("#dynamicTxtVatValue1").val(sandalWoodRowVatValue);

                sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValue + sandalWoodRowVatValue);
                if (sandalWoodRowPriceValueWithVat > 0)
                {
                    $("#dynamicTxtValue1").val("");
                    $("#dynamicTxtValue1").val(sandalWoodRowPriceValueWithVat.toFixed(2));

                    sandalWoodTotalValue = Math.round($("#dynamicTxtValue1").val());

                    $("#swGrandTotal").val("");
                    $("#swGrandTotal").val(sandalWoodTotalValue.toFixed(2));


                    $("#swBillAmt").val("");
                    $("#swBillAmt").val(sandalWoodTotalValue.toFixed(2));
                }
                else
                {
                    alert("Undefined Vatt Value");
                }
            }
        }
    }
}

function insSwMaterialDetails(rcvMaterialId, rcvDescription, rcvQuantity, rcvRate, rcvCalcuValue, rcvVatPercentage, rcvVendorId)
{
    var swVatPer;
    var swVarValue;
    var swTotalQuantity;
    var swNewPriceValue;
    var swDiscountValue;
    var swTotalQuantity;
    var swTotalValueWithVat;
    var swTotalValueWithDiscount;

    var swDiscountPercentage = $("#sWDiscountPercentage").val().trim();
    var sandalWoodRowCount = $('#sandalWood-pruchase_tbody tr').length;
    if (sandalWoodRowCount === 0)
    {
        //DIRECTLY INSERTING SINGLE ROW AT THE FIRST TIME
        $("#sandalWood-pruchase_tbody").append('<tr id="tr' + indexValue + '" class="swfieldwrapper"><td style="width: 5px;text-align: center"><label id="swlblSno' + indexValue + '" for ="sno' + indexValue + '" class="swfieldSno" name="swlblSno' + indexValue + '">' + indexValue + '</label></td><td style="width: 15px; text-align: center"><input type="text" readonly="true" name="dynamicTxtMaterial' + indexValue + '" style="width:110px;" id="dynamicTxtMaterial' + indexValue + '" class="swfieldTxtMaterial" value="' + rcvMaterialId + '"/></td><td style="width: 15%; text-align: center"><textarea readonly="true" class="textarea.span1 swfieldTxtDescription" name="dynamicTxtDescription' + indexValue + '"  id="dynamicTxtDescription' + indexValue + '" >' + rcvDescription + '</textarea></td><td style="width: 25px; text-align: center"><input type="text" style="width:70px;" readonly="true" name="dynamicTxtVendor' + indexValue + '"  id="dynamicTxtVendor' + indexValue + '" class="swfieldTxtVendor" value="' + rcvVendorId + '"/></td><td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="dynamicTxtQuantity' + indexValue + '"  id="dynamicTxtQuantity' + indexValue + '" class="swfieldTxtQuantity" value="' + rcvQuantity + '" onkeyup="resetQuantity(this,' + indexValue + ');"/></td><td style="width: 25px; text-align: right"><input type="text" style="width:50px;" readonly="true" name="dynamicTxtRate' + indexValue + '"  id="dynamicTxtRate' + indexValue + '" class="swfieldTxtRate" value="' + rcvRate + '"/></td><td style="width: 25px; text-align: center;"><input type="text" style="width:40px;" readonly="true" name="dynamicTxtVat' + indexValue + '"  id="dynamicTxtVat' + indexValue + '" class="fieldTxtVatPer" value="' + rcvVatPercentage + '"/></td>  <td style="width: 25px; text-align: center;"><input type="text" style="width:40px;" readonly="true" name="dynamicTxtVatValue' + indexValue + '"  id="dynamicTxtVatValue' + indexValue + '" class="swfieldTxtVatValue" value="' + swInitialVatValue + '"/></td>     <td style="width: 25px; text-align: center;display: none;"><input type="text" style="width:40px;" readonly="true" name="dynamicsWDiscountPercentageValue' + indexValue + '"  id="dynamicsWDiscountPercentageValue' + indexValue + '" class="swfieldsWDiscountPercentageValue" value="' + swCalculatedDisCountValue + '"/></td>  <td style="width: 25px; text-align: right"><input type="text" style="width:55px;" readonly="true" name="dynamicTxtValue' + indexValue + '"  id="dynamicTxtValue' + indexValue + '" class="swfieldTxtValue" style="text-align: right" value="' + rcvCalcuValue + '"/></td><td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="dynamictrEdit' + indexValue + '" id="dynamictrEdit' + indexValue + '" class="swfieldPencil" onclick="dynamicEditItem(this,' + indexValue + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="dynamictrRemove' + indexValue + '" class="swfieldMinus" onclick="swRemovingSelectedItem(this,' + indexValue + ');" /> </td></tr>');
        indexValue++;
        reOrderingDynamicTrValues();
    }
    else if (sandalWoodRowCount >= 1)
    {
        var iLoop = 1;
        while (iLoop <= sandalWoodRowCount)
        {
            if (swDiscountPercentage !== "")
            {
                if (swDiscountPercentage > 0)
                {
                    swNewPriceValue = Math.round($("#dynamicTxtQuantity" + iLoop).val() * $("#dynamicTxtRate" + iLoop).val());

                    swDiscountValue = Math.round(swNewPriceValue * swDiscountPercentage / 100);

                    $("#dynamicsWDiscountPercentageValue" + iLoop).val("");
                    $("#dynamicsWDiscountPercentageValue" + iLoop).val(swDiscountValue);

                    swTotalValueWithDiscount = Math.round(swNewPriceValue - swDiscountValue);

                    swVarValue = Math.round(swTotalValueWithDiscount * $("#dynamicTxtVat" + iLoop).val() / 100);

                    swTotalValueWithVat = Math.round(swTotalValueWithDiscount + swVarValue);

                    $("#dynamicTxtValue" + iLoop).val("");
                    $("#dynamicTxtValue" + iLoop).val(swTotalValueWithVat.toFixed(2));
                }

            }
            else
            {
                $("#dynamicsWDiscountPercentageValue" + iLoop).val("");
                $("#dynamicsWDiscountPercentageValue" + iLoop).val(0);

                swNewPriceValue = Math.round($("#dynamicTxtQuantity" + iLoop).val() * $("#dynamicTxtRate" + iLoop).val());

                swVarValue = Math.round(swNewPriceValue * $("#dynamicTxtVat" + iLoop).val() / 100);

                swTotalValueWithVat = Math.round(swNewPriceValue + swVarValue);

                $("#dynamicTxtValue" + iLoop).val("");
                $("#dynamicTxtValue" + iLoop).val(swTotalValueWithVat.toFixed(2));
            }
            iLoop++;
        }
        $("#sandalWood-pruchase_tbody").append('<tr id="tr' + indexValue + '" class="swfieldwrapper"><td style="width: 5px;text-align: center"><label id="swlblSno' + indexValue + '" for ="sno' + indexValue + '" class="swfieldSno" name="swlblSno' + indexValue + '">' + indexValue + '</label></td><td style="width: 15px; text-align: center"><input type="text" readonly="true" name="dynamicTxtMaterial' + indexValue + '" style="width:110px;" id="dynamicTxtMaterial' + indexValue + '" class="swfieldTxtMaterial" value="' + rcvMaterialId + '"/></td><td style="width: 15%; text-align: center"><textarea readonly="true" class="textarea.span1 swfieldTxtDescription" name="dynamicTxtDescription' + indexValue + '"  id="dynamicTxtDescription' + indexValue + '" >' + rcvDescription + '</textarea></td><td style="width: 25px; text-align: center"><input type="text" style="width:70px;" readonly="true" name="dynamicTxtVendor' + indexValue + '"  id="dynamicTxtVendor' + indexValue + '" class="swfieldTxtVendor" value="' + rcvVendorId + '"/></td><td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="dynamicTxtQuantity' + indexValue + '"  id="dynamicTxtQuantity' + indexValue + '" class="swfieldTxtQuantity" value="' + rcvQuantity + '" onkeyup="resetQuantity(this,' + indexValue + ');"/></td><td style="width: 25px; text-align: right"><input type="text" style="width:50px;" readonly="true" name="dynamicTxtRate' + indexValue + '"  id="dynamicTxtRate' + indexValue + '" class="swfieldTxtRate" value="' + rcvRate + '"/></td><td style="width: 25px; text-align: center;"><input type="text" style="width:40px;" readonly="true" name="dynamicTxtVat' + indexValue + '"  id="dynamicTxtVat' + indexValue + '" class="fieldTxtVatPer" value="' + rcvVatPercentage + '"/></td>  <td style="width: 25px; text-align: center;"><input type="text" style="width:40px;" readonly="true" name="dynamicTxtVatValue' + indexValue + '"  id="dynamicTxtVatValue' + indexValue + '" class="swfieldTxtVatValue" value="' + swInitialVatValue + '"/></td>     <td style="width: 25px; text-align: center;display: none;"><input type="text" style="width:40px;" readonly="true" name="dynamicsWDiscountPercentageValue' + indexValue + '"  id="dynamicsWDiscountPercentageValue' + indexValue + '" class="swfieldsWDiscountPercentageValue" value="' + swCalculatedDisCountValue + '"/></td>  <td style="width: 25px; text-align: right"><input type="text" style="width:55px;" readonly="true" name="dynamicTxtValue' + indexValue + '"  id="dynamicTxtValue' + indexValue + '" class="swfieldTxtValue" style="text-align: right" value="' + rcvCalcuValue + '"/></td><td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="dynamictrEdit' + indexValue + '" id="dynamictrEdit' + indexValue + '" class="swfieldPencil" onclick="dynamicEditItem(this,' + indexValue + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="dynamictrRemove' + indexValue + '" class="swfieldMinus" onclick="swRemovingSelectedItem(this,' + indexValue + ');" /> </td></tr>');
        indexValue++;
        reOrderingDynamicTrValues();
    }
}

function swRemovingSelectedItem(swRcvKeyId, swRcvTrId)
{
    var recentRowCount;
    var sandalWoodTotalValue = 0;
    var replacingValue = "";
    if (confirm("Remove This Item?"))//COMFIRMING WIHT THE USER FOR REMOVAL OF THE ROW
    {
        var trTxtValue = $("#dynamicTxtValue" + swRcvTrId).val().toString();
        var trQuantity = $("#dynamicTxtQuantity" + swRcvTrId).val().toString();
        var trMaterial = $("#dynamicTxtMaterial" + swRcvTrId).val().toString();
        var packingCharge = $("#swPackingCharge").val().trim();

        $(swRcvKeyId).closest('tr[id]').remove();//REMOVING THE SPECIFIC ROW FROM THE PRO-TABLE

        recentRowCount = $('#sandalWood-pruchase_tbody tr').length;
        reOrderingDynamicTrValues();
        if (recentRowCount > 1)
        {
            for (var loopVar = 1; loopVar <= recentRowCount; loopVar++)
            {
                sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicTxtValue" + loopVar).val()));
            }
        }
        else if (recentRowCount === 1)
        {
            sandalWoodTotalValue = Math.round($("#dynamicTxtValue1").val());
        }
        else if (recentRowCount === 0)
        {
            sandalWoodTotalValue = 0.00;
        }

        if (packingCharge !== "")
        {
            replacingValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt(packingCharge));
            $("#swGrandTotal").val("");
            $("#swBillAmt").val("");
            $("#swGrandTotal").val(replacingValue.toFixed(2));
            $("#swBillAmt").val(replacingValue.toFixed(2));
        }
        else if (packingCharge == "")
        {
            replacingValue = Math.round(parseInt(sandalWoodTotalValue));
            $("#swGrandTotal").val("");
            $("#swBillAmt").val("");
            $("#swGrandTotal").val(replacingValue.toFixed(2));
            $("#swBillAmt").val(replacingValue.toFixed(2));
        }

        $('#SwMaterialId').focus();
    }
}
function resetQuantity(rcvIndexthis, rcvIndexValue)
{
    var swPurchaseTableRowCount = $('#sandalWood-pruchase_tbody tr').length;
    //var summaryTableRowCount = $('#summary-table-body tr').length;

    var lopValue = 1;
    var swGrandBillAmt;
    var calculateVatQty;
    var prodPriceWithDiscount;
    var smRecentTotalValue = 0;
    var swGrandBillAmount = "";
    var swCurrentTotalValue = 0;
    var calculateDiscountValueQty;
    var calculatedProductPrice = "";
    var calculatedProductValue = "";
    var swTotalValueWithPackingCharge;

    var swInsPackingCharges = $("#swPackingCharge").val().trim();
    var swInsDiscountAmount = $("#sWDiscountPercentage").val().trim();
    var dynamicProductRate = $("#dynamicTxtRate" + rcvIndexValue).val().trim();//INDIVIDUAL PRD VALUE
    var dynamicVatPercentage = $("#dynamicTxtVat" + rcvIndexValue).val().trim();
    var dynamicQuantityValue = $("#dynamicTxtQuantity" + rcvIndexValue).val().trim();//QUANTITY

    if (dynamicQuantityValue !== "")
    {
//        if (!(dynamicQuantityValue == "0.0"))
//        {
        if (swInsDiscountAmount !== "")
        {
            calculatedProductPrice = 0;
            calculateVatQty = 0;
            calculatedProductValue = 0;
            calculatedProductPrice = Math.round(parseFloat(dynamicQuantityValue) * parseFloat(dynamicProductRate));
            calculateDiscountValueQty = Math.round(calculatedProductPrice * swInsDiscountAmount / 100);

            $("#dynamicTxtValue" + rcvIndexValue).val("");
            $("#dynamicTxtValue" + rcvIndexValue).val(calculateDiscountValueQty);

            prodPriceWithDiscount = calculatedProductPrice - calculateDiscountValueQty;

            calculateVatQty = Math.round(prodPriceWithDiscount * dynamicVatPercentage / 100);
            calculatedProductValue = Math.round(parseFloat(prodPriceWithDiscount) + parseFloat(calculateVatQty));

            $("#dynamicTxtVatValue" + rcvIndexValue).val("");
            $("#dynamicTxtVatValue" + rcvIndexValue).val(calculateVatQty);

            $("#dynamicTxtValue" + rcvIndexValue).val("");
            $("#dynamicTxtValue" + rcvIndexValue).val(calculatedProductValue.toFixed(2));
        }
        else
        {
            calculatedProductPrice = 0;
            calculateVatQty = 0;
            calculatedProductValue = 0;

            $("#dynamicTxtValue" + rcvIndexValue).val("");
            $("#dynamicTxtValue" + rcvIndexValue).val(0);

            calculatedProductPrice = Math.round(parseFloat(dynamicQuantityValue) * parseFloat(dynamicProductRate));
            calculateVatQty = Math.round(calculatedProductPrice * dynamicVatPercentage / 100);

            $("#dynamicTxtVatValue" + rcvIndexValue).val("");
            $("#dynamicTxtVatValue" + rcvIndexValue).val(calculateVatQty);

            calculatedProductValue = parseFloat(calculatedProductPrice) + parseFloat(calculateVatQty);

            $("#dynamicTxtValue" + rcvIndexValue).val("");
            $("#dynamicTxtValue" + rcvIndexValue).val(calculatedProductValue.toFixed(2));
        }

        if (swPurchaseTableRowCount > 1)
        {
            while (lopValue <= swPurchaseTableRowCount)
            {
                smRecentTotalValue = parseFloat(smRecentTotalValue) + parseFloat($("#dynamicTxtValue" + lopValue).val());
                lopValue++;
            }
            swGrandBillAmt = Math.round(smRecentTotalValue.toFixed(2));
        }
        else if (swPurchaseTableRowCount === 1)
        {
            swGrandBillAmt = Math.round(calculatedProductValue.toFixed(2));
        }

        if (swInsPackingCharges != "" || swInsPackingCharges != 0)
        {
            swTotalValueWithPackingCharge = Math.round(parseInt(swGrandBillAmt) + parseInt(swInsPackingCharges));
            $("#swGrandTotal").val("");
            $("#swGrandTotal").val(swTotalValueWithPackingCharge.toFixed(2));

            $("#swBillAmt").val("");
            $("#swBillAmt").val(swTotalValueWithPackingCharge.toFixed(2));
        }
        else
        {
            $("#swGrandTotal").val("");
            $("#swGrandTotal").val(swGrandBillAmt.toFixed(2));

            $("#swBillAmt").val("");
            $("#swBillAmt").val(swGrandBillAmt.toFixed(2));
        }
    }
    else
    {
        $("#dynamicTxtQuantity" + rcvIndexValue).val("");
        $("#dynamicTxtValue" + rcvIndexValue).val("0.0");
    }
}

function reOrderingDynamicTrValues()
{
//RE-ORDERING ID PARAMETER VALUES FOR TR
    var trValue = $('.swfieldwrapper');
    var countTr = 1;
    $.each(trValue, function()
    {
        $(this).attr('id', 'tr' + countTr);
        countTr++;
    });

    //RE-ORDERING EDIT ID,NAME & TEXT PARAMETER VALUES FOR SNO
    var ValueSno = $('.swfieldSno');
    var countSno = 1;
    $.each(ValueSno, function()
    {
        $(this).attr('id', 'swlblSno' + countSno);
        $(this).attr('name', 'swlblSno' + countSno);
        $(this).attr('for', 'swlblSno' + countSno);
        $(this).attr('id', 'swlblSno' + countSno).text(countSno);
        countSno++;
    });

    //RE-ORDERING MATERIAL ID & NAME
    var Materialfields = $('.swfieldTxtMaterial');
    var countMaterialValue = 1;
    $.each(Materialfields, function() {
        $(this).attr('id', 'dynamicTxtMaterial' + countMaterialValue);
        $(this).attr('name', 'dynamicTxtMaterial' + countMaterialValue);
        countMaterialValue++;
    });

    //RE-ORDERING EDIT METHOD ID,VALUES FOR DESCRIPTION
    var ValueDescription = $('.swfieldTxtDescription');
    var countDesc = 1;
    $.each(ValueDescription, function()
    {
        $(this).attr('id', 'dynamicTxtDescription' + countDesc);
        $(this).attr('name', 'dynamicTxtDescription' + countDesc);
        countDesc++;
    });

    //RE-ORDERING QUANTITY ID & NAME
    var Quantityfields = $('.swfieldTxtQuantity');
    var countQuantityValue = 1;
    $.each(Quantityfields, function()
    {
        $(this).attr('id', 'dynamicTxtQuantity' + countQuantityValue);
        $(this).attr('name', 'dynamicTxtQuantity' + countQuantityValue);
        $(this).attr('onkeyup', "resetQuantity(this," + countQuantityValue + ")");
        countQuantityValue++;
    });

    //RE-ORDERING MATERIAL-RATE ID & NAME
    var Ratefields = $('.swfieldTxtRate');
    var countRateValue = 1;
    $.each(Ratefields, function() {
        $(this).attr('id', 'dynamicTxtRate' + countRateValue);
        $(this).attr('name', 'dynamicTxtRate' + countRateValue);
        countRateValue++;
    });

    //RE-ORDERING MATERIAL-TOTALVALUE ID & NAME
    var Valuefields = $('.swfieldTxtValue');
    var countValue = 1;
    $.each(Valuefields, function() {
        $(this).attr('id', 'dynamicTxtValue' + countValue);
        $(this).attr('name', 'dynamicTxtValue' + countValue);
        countValue++;
    });

    //RE-ORDERING VATVALUE ID & NAME
    var VatValueFields = $('.swfieldTxtVatValue');
    var countVatValue = 1;
    $.each(VatValueFields, function() {
        $(this).attr('id', 'dynamicTxtVatValue' + countVatValue);
        $(this).attr('name', 'dynamicTxtVatValue' + countVatValue);
        countVatValue++;
    });

    //RE-ORDERING DISCOUNT-VALUE ID & NAME
    var DiscountValueFields = $('.swfieldsWDiscountPercentageValue');
    var DiscountValue = 1;
    $.each(DiscountValueFields, function() {
        $(this).attr('id', 'dynamicsWDiscountPercentageValue' + DiscountValue);
        $(this).attr('name', 'dynamicsWDiscountPercentageValue' + DiscountValue);
        DiscountValue++;
    });

    //RE-ORDERING EDIT METHOD ID,VALUES FOR STOCK
    var ValueStock = $('.fieldTxtVatPer');
    var countStock = 1;
    $.each(ValueStock, function()
    {
        $(this).attr('id', 'dynamicTxtVat' + countStock);
        $(this).attr('name', 'dynamicTxtVat' + countStock);
        countStock++;
    });

    //RE-ORDERING VENDOR ID & NAME
    var Vendorfields = $('.swfieldTxtVendor');
    var countVendorValue = 1;
    $.each(Vendorfields, function() {
        $(this).attr('id', 'dynamicTxtVendor' + countVendorValue);
        $(this).attr('name', 'dynamicTxtVendor' + countVendorValue);
        countVendorValue++;
    });

    //RE-ORDERING EDIT METHOD ID,NAME & ONCLICK PARAMETER VALUES FOR EDIT
    var ValuePen = $('.swfieldPencil');
    var countPenValue = 1;
    $.each(ValuePen, function() {
        $(this).attr('id', 'dynamictrEdit' + countPenValue);
        $(this).attr('name', 'dynamictrEdit' + countPenValue);
        $(this).attr('onclick', "dynamicEditItem(this," + countPenValue + ")");
        countPenValue++;
    });

    //RE-ORDERING EDIT METHOD ID,NAME & ONCLICK PARAMETER VALUES FOR REMOVE
    var valueMinus = $('.swfieldMinus');
    var countMinus = 1;//
    $.each(valueMinus, function()
    {
        $(this).attr('id', 'dynamictrRemove' + countMinus);
        $(this).attr('onclick', "swRemovingSelectedItem(this," + countMinus + ")");
        countMinus++;
    });
}















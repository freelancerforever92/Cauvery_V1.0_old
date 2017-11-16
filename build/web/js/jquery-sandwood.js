var swInitialVatValue = 0;
var swCalculatedDisCountValue = 0;
var indexValue = 1;
$(document).ready(function()
{
    $('#SwMaterialId').focus();
    $("#swTxtApproval").val("");
    $("#swTxtApproval").hide();


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
        insSwSummaryDetails(swMatrialId, swQuantity, "100");

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
    var sandalWoodRowVatValue;
    var sandalWoodRowPriceValue;
    var sandalWoodTotalValue = 0;
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

//                            $("#dynamicNetValue" + prodLopVal).val("");
//                            $("#dynamicNetValue" + prodLopVal).val(sandalWoodRowDiscountValue);
                            $("#dynamicsWDiscountPercentageValue" + prodLopVal).val("");
                            $("#dynamicsWDiscountPercentageValue" + prodLopVal).val(sandalWoodRowDiscountValue);

                            sandalWoodRowPriceValueWithDiscount = Math.round(sandalWoodRowPriceValue - sandalWoodRowDiscountValue);

                            sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValueWithDiscount * $("#dynamicTxtVat" + prodLopVal).val() / 100);

                            $("#dynamicTxtVatValue" + prodLopVal).val("");
                            $("#dynamicTxtVatValue" + prodLopVal).val(sandalWoodRowVatValue);


                            sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValueWithDiscount + sandalWoodRowVatValue);

                            if (sandalWoodRowPriceValueWithVat > 0)
                            {
                                $("#dynamicNetValue" + prodLopVal).val("");
                                $("#dynamicNetValue" + prodLopVal).val(sandalWoodRowPriceValueWithVat.toFixed(2));

                                sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicNetValue" + prodLopVal).val()));

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

                                $("#dynamicNetValue" + prodLopVal).val("");
                                $("#dynamicNetValue" + prodLopVal).val(sandalWoodRowPriceValueWithVat.toFixed(2));

                                sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicNetValue" + prodLopVal).val()));
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
                        //alert("Dis 2 :   " + sandalWoodRowDiscountValue);

                        $("#dynamicsWDiscountPercentageValue1").val("");
                        $("#dynamicsWDiscountPercentageValue1").val(sandalWoodRowDiscountValue);
                        //$("#dynamicNetValue1").val(sandalWoodRowDiscountValue);
                        sandalWoodRowPriceValueWithDiscount = Math.round(sandalWoodRowPriceValue - sandalWoodRowDiscountValue);

                        sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValueWithDiscount * $("#dynamicTxtVat1").val() / 100);

                        $("#dynamicTxtVatValue1").val("");
                        $("#dynamicTxtVatValue1").val(sandalWoodRowVatValue);

                        sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValueWithDiscount + sandalWoodRowVatValue);

                        if (sandalWoodRowPriceValueWithVat > 0)
                        {
                            $("#dynamicNetValue1").val("");
                            $("#dynamicNetValue1").val(sandalWoodRowPriceValueWithVat.toFixed(2));

                            sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicNetValue1").val()));

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

                            $("#dynamicNetValue1").val("");
                            $("#dynamicNetValue1").val(sandalWoodRowPriceValueWithVat.toFixed(2));

                            sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicNetValue1").val()));

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
                    $(".swDiscountApproval").show();
                    $("#swTxtApproval").val("");
                    document.getElementById('light').style.display = 'block';
                    document.getElementById('fade').style.display = 'block';

//                    if ((document.getElementById('light').style.display == "none") && (document.getElementById('fade').style.display == "none"))
//                    {
//                        $(".swDiscountApproval").hide();
//                        $("#swTxtApproval").val(".");
//                        $("#sandalWoodApprovalText").val("");
//                    }

                    if (sandalWoodTableRowCount > 1)
                    {
                        sandalWoodTotalValue = 0;
                        for (var prodLopVal = 1; prodLopVal <= sandalWoodTableRowCount; prodLopVal++)
                        {
                            sandalWoodRowPriceValue = Math.round($("#dynamicTxtQuantity" + prodLopVal).val() * $("#dynamicTxtRate" + prodLopVal).val());

                            sandalWoodRowDiscountValue = Math.round(sandalWoodRowPriceValue * sandalWoodDiscountPercentage / 100);

                            $("#dynamicNetValue" + prodLopVal).val("");
                            $("#dynamicNetValue" + prodLopVal).val(sandalWoodRowDiscountValue);

                            sandalWoodRowPriceValueWithDiscount = Math.round(sandalWoodRowPriceValue - sandalWoodRowDiscountValue);

                            sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValueWithDiscount * $("#dynamicTxtVat" + prodLopVal).val() / 100);

                            $("#dynamicTxtVatValue" + prodLopVal).val("");
                            $("#dynamicTxtVatValue" + prodLopVal).val(sandalWoodRowVatValue);


                            sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValueWithDiscount + sandalWoodRowVatValue);

                            if (sandalWoodRowPriceValueWithVat > 0)
                            {
                                $("#dynamicNetValue" + prodLopVal).val("");
                                $("#dynamicNetValue" + prodLopVal).val(sandalWoodRowPriceValueWithVat.toFixed(2));

                                sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicNetValue" + prodLopVal).val()));

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

                                $("#dynamicNetValue" + prodLopVal).val("");
                                $("#dynamicNetValue" + prodLopVal).val(sandalWoodRowPriceValueWithVat.toFixed(2));

                                sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicNetValue" + prodLopVal).val()));
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

                        $("#dynamicNetValue1").val("");
                        $("#dynamicNetValue1").val(sandalWoodRowDiscountValue);

                        sandalWoodRowPriceValueWithDiscount = Math.round(sandalWoodRowPriceValue - sandalWoodRowDiscountValue);

                        sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValueWithDiscount * $("#dynamicTxtVat1").val() / 100);

                        $("#dynamicTxtVatValue1").val("");
                        $("#dynamicTxtVatValue1").val(sandalWoodRowVatValue);

                        sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValueWithDiscount + sandalWoodRowVatValue);

                        if (sandalWoodRowPriceValueWithVat > 0)
                        {
                            $("#dynamicNetValue1").val("");
                            $("#dynamicNetValue1").val(sandalWoodRowPriceValueWithVat.toFixed(2));

                            sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicNetValue1").val()));

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

                            $("#dynamicNetValue1").val("");
                            $("#dynamicNetValue1").val(sandalWoodRowPriceValueWithVat.toFixed(2));

                            sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicNetValue1").val()));

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
            $(".swDiscountApproval").hide();
            $("#swTxtApproval").val(".");
            $("#sandalWoodApprovalText").val("");
            if (sandalWoodTableRowCount > 1)
            {
                sandalWoodTotalValue = 0;
                for (var prodLopVal2 = 1; prodLopVal2 <= sandalWoodTableRowCount; prodLopVal2++)
                {
                    $("#dynamicsWDiscountPercentageValue" + prodLopVal2).val("");
                    $("#dynamicsWDiscountPercentageValue" + prodLopVal2).val("0");
                    sandalWoodRowPriceValue = Math.round($("#dynamicTxtQuantity" + prodLopVal2).val() * $("#dynamicTxtRate" + prodLopVal2).val());
                    sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValue * $("#dynamicTxtVat" + prodLopVal2).val() / 100);

                    $("#dynamicTxtVatValue" + prodLopVal2).val("");
                    $("#dynamicTxtVatValue" + prodLopVal2).val(sandalWoodRowVatValue);

                    sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValue + sandalWoodRowVatValue);

                    $("#dynamicNetValue" + prodLopVal2).val("");
                    $("#dynamicNetValue" + prodLopVal2).val(0);

                    if (sandalWoodRowPriceValueWithVat > 0)
                    {
                        $("#dynamicNetValue" + prodLopVal2).val("");
                        $("#dynamicNetValue" + prodLopVal2).val(sandalWoodRowPriceValueWithVat.toFixed(2));

                        sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicNetValue" + prodLopVal2).val()));

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

                $("#dynamicNetValue1").val("");
                $("#dynamicNetValue1").val(0);

                $("#dynamicsWDiscountPercentageValue1").val("");
                $("#dynamicsWDiscountPercentageValue1").val("0");

                sandalWoodRowPriceValue = Math.round($("#dynamicTxtQuantity1").val() * $("#dynamicTxtRate1").val());
                sandalWoodRowVatValue = Math.round(sandalWoodRowPriceValue * $("#dynamicTxtVat1").val() / 100);

                $("#dynamicTxtVatValue1").val("");
                $("#dynamicTxtVatValue1").val(sandalWoodRowVatValue);

                sandalWoodRowPriceValueWithVat = Math.round(sandalWoodRowPriceValue + sandalWoodRowVatValue);
                if (sandalWoodRowPriceValueWithVat > 0)
                {
                    $("#dynamicNetValue1").val("");
                    $("#dynamicNetValue1").val(sandalWoodRowPriceValueWithVat.toFixed(2));

                    sandalWoodTotalValue = Math.round($("#dynamicNetValue1").val());

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
        //$("#sandalWood-pruchase_tbody").append('<tr id="tr' + indexValue + '" class="swfieldwrapper"><td style="width: 5px;text-align: center"><label id="swlblSno' + indexValue + '" for ="sno' + indexValue + '" class="swfieldSno" name="swlblSno' + indexValue + '">' + indexValue + '</label></td><td style="width: 15px; text-align: center"><input type="text" readonly="true" name="dynamicTxtMaterial' + indexValue + '" style="width:110px;" id="dynamicTxtMaterial' + indexValue + '" class="swfieldTxtMaterial" value="' + rcvMaterialId + '"/></td><td style="width: 15%; text-align: center"><textarea readonly="true" class="textarea.span1 swfieldTxtDescription" name="dynamicTxtDescription' + indexValue + '"  id="dynamicTxtDescription' + indexValue + '" >' + rcvDescription + '</textarea></td><td style="width: 25px; text-align: center"><input type="text" style="width:70px;" readonly="true" name="dynamicTxtVendor' + indexValue + '"  id="dynamicTxtVendor' + indexValue + '" class="swfieldTxtVendor" value="' + rcvVendorId + '"/></td><td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="dynamicTxtQuantity' + indexValue + '"  id="dynamicTxtQuantity' + indexValue + '" class="swfieldTxtQuantity" value="' + rcvQuantity + '" onkeyup="resetQuantity(this,' + indexValue + ');"/></td><td style="width: 25px; text-align: right"><input type="text" style="width:50px;" readonly="true" name="dynamicTxtRate' + indexValue + '"  id="dynamicTxtRate' + indexValue + '" class="swfieldTxtRate" value="' + rcvRate + '"/></td><td style="width: 25px; text-align: center;"><input type="text" style="width:40px;" readonly="true" name="dynamicTxtVat' + indexValue + '"  id="dynamicTxtVat' + indexValue + '" class="fieldTxtVatPer" value="' + rcvVatPercentage + '"/></td>  <td style="width: 25px; text-align: center;"><input type="text" style="width:40px;" readonly="true" name="dynamicTxtVatValue' + indexValue + '"  id="dynamicTxtVatValue' + indexValue + '" class="swfieldTxtVatValue" value="' + swInitialVatValue + '"/></td>     <td style="width: 25px; text-align: center;display: none;"><input type="text" style="width:40px;" readonly="true" name="dynamicsWDiscountPercentageValue' + indexValue + '"  id="dynamicsWDiscountPercentageValue' + indexValue + '" class="swfieldsWDiscountPercentageValue" value="' + swCalculatedDisCountValue + '"/></td>  <td style="width: 25px; text-align: right"><input type="text" style="width:55px;" readonly="true" name="dynamicNetValue' + indexValue + '"  id="dynamicNetValue' + indexValue + '" class="swfieldTxtValue" style="text-align: right" value="' + rcvCalcuValue + '"/></td><td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="dynamictrEdit' + indexValue + '" id="dynamictrEdit' + indexValue + '" class="swfieldPencil" onclick="dynamicEditItem(this,' + indexValue + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="dynamictrRemove' + indexValue + '" class="swfieldMinus" onclick="swRemovingSelectedItem(this,' + indexValue + ');" /> </td></tr>');
        $("#sandalWood-pruchase_tbody").append('<tr id="tr' + indexValue + '" class="swfieldwrapper"><td style="width: 5px;text-align: center"><label id="swlblSno' + indexValue + '" for ="sno' + indexValue + '" class="swfieldSno" name="swlblSno' + indexValue + '">' + indexValue + '</label></td><td style="width: 15px; text-align: center"><input type="text" readonly="true" name="dynamicTxtMaterial' + indexValue + '" style="width:110px;" id="dynamicTxtMaterial' + indexValue + '" class="swfieldTxtMaterial" value="' + rcvMaterialId + '"/></td><td style="width: 15%; text-align: center"><textarea readonly="true" class="textarea.span1 swfieldTxtDescription" name="dynamicTxtDescription' + indexValue + '"  id="dynamicTxtDescription' + indexValue + '" >' + rcvDescription + '</textarea></td><td style="width: 25px; text-align: center"><input type="text" style="width:70px;" readonly="true" name="dynamicTxtVendor' + indexValue + '"  id="dynamicTxtVendor' + indexValue + '" class="swfieldTxtVendor" value="' + rcvVendorId + '"/></td><td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="dynamicTxtQuantity' + indexValue + '"  id="dynamicTxtQuantity' + indexValue + '" class="swfieldTxtQuantity" value="' + rcvQuantity + '" onkeyup="resetQuantity(this,' + indexValue + ');"/></td><td style="width: 25px; text-align: right"><input type="text" style="width:50px;" readonly="true" name="dynamicTxtRate' + indexValue + '"  id="dynamicTxtRate' + indexValue + '" class="swfieldTxtRate" value="' + rcvRate + '"/></td><td style="width: 25px; text-align: center;"><input type="text" style="width:40px;" readonly="true" name="dynamicTxtVat' + indexValue + '"  id="dynamicTxtVat' + indexValue + '" class="fieldTxtVatPer" value="' + rcvVatPercentage + '"/></td>  <td style="width: 25px; text-align: center;"><input type="text" style="width:40px;" readonly="true" name="dynamicTxtVatValue' + indexValue + '"  id="dynamicTxtVatValue' + indexValue + '" class="swfieldTxtVatValue" value="' + swInitialVatValue + '"/></td>     <td style="width: 25px; text-align: center;display: none;"><input type="text" style="width:40px;" readonly="true" name="dynamicsWDiscountPercentageValue' + indexValue + '"  id="dynamicsWDiscountPercentageValue' + indexValue + '" class="swfieldsWDiscountPercentageValue" value="' + swCalculatedDisCountValue + '"/></td>  <td style="width: 25px; text-align: right"><input type="text" style="width:55px;" readonly="true" name="dynamicNetValue' + indexValue + '"  id="dynamicNetValue' + indexValue + '" class="swfieldTxtValue" style="text-align: right" value="' + rcvCalcuValue + '"/></td><td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="dynamictrEdit' + indexValue + '" id="dynamictrEdit' + indexValue + '" class="swfieldPencil" onclick="dynamicEditItem(this,' + indexValue + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="dynamictrRemove' + indexValue + '" class="swfieldMinus" onclick="swRemovingSelectedItem(this,' + indexValue + ');" /> </td></tr>');
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

                    $("#dynamicNetValue" + iLoop).val("");
                    $("#dynamicNetValue" + iLoop).val(swTotalValueWithVat.toFixed(2));
                }

            }
            else
            {
                $("#dynamicsWDiscountPercentageValue" + iLoop).val("");
                $("#dynamicsWDiscountPercentageValue" + iLoop).val(0);

                swNewPriceValue = Math.round($("#dynamicTxtQuantity" + iLoop).val() * $("#dynamicTxtRate" + iLoop).val());

                swVarValue = Math.round(swNewPriceValue * $("#dynamicTxtVat" + iLoop).val() / 100);

                swTotalValueWithVat = Math.round(swNewPriceValue + swVarValue);

                $("#dynamicNetValue" + iLoop).val("");
                $("#dynamicNetValue" + iLoop).val(swTotalValueWithVat.toFixed(2));
            }
            iLoop++;
        }
        $("#sandalWood-pruchase_tbody").append('<tr id="tr' + indexValue + '" class="swfieldwrapper"><td style="width: 5px;text-align: center"><label id="swlblSno' + indexValue + '" for ="sno' + indexValue + '" class="swfieldSno" name="swlblSno' + indexValue + '">' + indexValue + '</label></td><td style="width: 15px; text-align: center"><input type="text" readonly="true" name="dynamicTxtMaterial' + indexValue + '" style="width:110px;" id="dynamicTxtMaterial' + indexValue + '" class="swfieldTxtMaterial" value="' + rcvMaterialId + '"/></td><td style="width: 15%; text-align: center"><textarea readonly="true" class="textarea.span1 swfieldTxtDescription" name="dynamicTxtDescription' + indexValue + '"  id="dynamicTxtDescription' + indexValue + '" >' + rcvDescription + '</textarea></td><td style="width: 25px; text-align: center"><input type="text" style="width:70px;" readonly="true" name="dynamicTxtVendor' + indexValue + '"  id="dynamicTxtVendor' + indexValue + '" class="swfieldTxtVendor" value="' + rcvVendorId + '"/></td><td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="dynamicTxtQuantity' + indexValue + '"  id="dynamicTxtQuantity' + indexValue + '" class="swfieldTxtQuantity" value="' + rcvQuantity + '" onkeyup="resetQuantity(this,' + indexValue + ');"/></td><td style="width: 25px; text-align: right"><input type="text" style="width:50px;" readonly="true" name="dynamicTxtRate' + indexValue + '"  id="dynamicTxtRate' + indexValue + '" class="swfieldTxtRate" value="' + rcvRate + '"/></td><td style="width: 25px; text-align: center;"><input type="text" style="width:40px;" readonly="true" name="dynamicTxtVat' + indexValue + '"  id="dynamicTxtVat' + indexValue + '" class="fieldTxtVatPer" value="' + rcvVatPercentage + '"/></td>  <td style="width: 25px; text-align: center;"><input type="text" style="width:40px;" readonly="true" name="dynamicTxtVatValue' + indexValue + '"  id="dynamicTxtVatValue' + indexValue + '" class="swfieldTxtVatValue" value="' + swInitialVatValue + '"/></td>     <td style="width: 25px; text-align: center;display: none;"><input type="text" style="width:40px;" readonly="true" name="dynamicsWDiscountPercentageValue' + indexValue + '"  id="dynamicsWDiscountPercentageValue' + indexValue + '" class="swfieldsWDiscountPercentageValue" value="' + swCalculatedDisCountValue + '"/></td>  <td style="width: 25px; text-align: right"><input type="text" style="width:55px;" readonly="true" name="dynamicNetValue' + indexValue + '"  id="dynamicNetValue' + indexValue + '" class="swfieldTxtValue" style="text-align: right" value="' + rcvCalcuValue + '"/></td><td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="dynamictrEdit' + indexValue + '" id="dynamictrEdit' + indexValue + '" class="swfieldPencil" onclick="dynamicEditItem(this,' + indexValue + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="dynamictrRemove' + indexValue + '" class="swfieldMinus" onclick="swRemovingSelectedItem(this,' + indexValue + ');" /> </td></tr>');
        indexValue++;
        reOrderingDynamicTrValues();
    }
}

function insSwSummaryDetails(rcvSummaryMaterialId, rcvSummaryQuantity, rcvSummaryStock)
{
    var rowData = "";
    var swSummaryTrCount = $('#sandalWood-summry-table tr').length;
    if (swSummaryTrCount == 2)
    {
        rowData = '<tr id="summary-tr-id1" class="summary-tr-class"> <td id="summary-MaterialId-1" style="text-align: center;" class="summary-MaterialId-class1">' + rcvSummaryMaterialId + '</td> <td id="summary-Quantity-1" style="text-align: center;" class="summary-Quantity-class1">' + rcvSummaryQuantity + '</td>  <td id="summary-Stock-1" style="text-align: center;" class="summary-Stock-class1">' + rcvSummaryStock + '</td> ';
    }
    else if (swSummaryTrCount > 2)
    {
        var quantityInTable = $("#summary-Quantity-1").text();
        var totalQty = parseFloat(quantityInTable) + parseFloat(rcvSummaryQuantity);
        $("#summary-Quantity-1").text("");
        $("#summary-Quantity-1").text(totalQty.toFixed(2));
    }
    $("#sandalWood-summry-table").append(rowData);
}



function hideApprovalDialogue()
{
    if (!($("#sandalWoodApprovalText").val().trim() == ""))
    {
        document.getElementById('light').style.display = 'none';
        document.getElementById('fade').style.display = 'none';
        $("#swTxtApproval").val($("#sandalWoodApprovalText").val().trim());
    }
    else
    {
        alert("Oops,Approval Text Is Missing...!!!");
        $("#sandalWoodApprovalText").focus();
        document.getElementById('sandalWoodApprovalText').style.borderColor = "red";
    }
}

function swCancelDiscount()
{
    $("#sWDiscountPercentage").val("");
    $("#swTxtApproval").val("");
    $("#swTxtApproval").hide();
    var swAuthCancelRowVatValue;
    var swAuthCancelRowPriceValue;
    var swAuthCancelTotalValue = 0;
    var swAuthCancelRowDiscountValue;
    var swAuthCancelRowPriceValueWithVat;
    var swAuthCancelRowPriceValueWithDiscount;
    var swAuthCancelRowCount = $('#sandalWood-pruchase_tbody tr').length;
    var swAuthCancelPackingCharge = $("#swPackingCharge").val().trim();

    if (swAuthCancelRowCount > 1)
    {
        swAuthCancelTotalValue = 0;
        for (var swCancelLopValue = 1; swCancelLopValue <= swAuthCancelRowCount; swCancelLopValue++)
        {
            swAuthCancelRowPriceValue = Math.round($("#dynamicTxtQuantity" + swCancelLopValue).val() * $("#dynamicTxtRate" + swCancelLopValue).val());

            swAuthCancelRowVatValue = Math.round(swAuthCancelRowPriceValue * $("#dynamicTxtVat" + swCancelLopValue).val() / 100);

            $("#dynamicTxtVatValue" + swCancelLopValue).val("");
            $("#dynamicTxtVatValue" + swCancelLopValue).val(swAuthCancelRowVatValue);

            swAuthCancelRowPriceValueWithVat = Math.round(swAuthCancelRowPriceValue + swAuthCancelRowVatValue);
            /*doubt*/
            $("#dynamicNetValue" + swCancelLopValue).val("");
            $("#dynamicNetValue" + swCancelLopValue).val(0);

            if (swAuthCancelRowPriceValueWithVat > 0)
            {
                $("#dynamicNetValue" + swCancelLopValue).val("");
                $("#dynamicNetValue" + swCancelLopValue).val(swAuthCancelRowPriceValueWithVat.toFixed(2));

                swAuthCancelTotalValue = Math.round(parseInt(swAuthCancelTotalValue) + parseInt($("#dynamicNetValue" + swCancelLopValue).val()));
                if (swAuthCancelTotalValue != "" || swAuthCancelTotalValue != 0)
                {
                    if (swAuthCancelPackingCharge != "" || swAuthCancelPackingCharge != 0)
                    {
                        var swTotalWitPckageValue = Math.round(parseInt(swAuthCancelTotalValue) + parseInt(swAuthCancelPackingCharge));
                        $("#swGrandTotal").val("");
                        $("#swGrandTotal").val(swTotalWitPckageValue.toFixed(2));

                        $("#swBillAmt").val("");
                        $("#swBillAmt").val(swTotalWitPckageValue.toFixed(2));
                    }
                    else
                    {
                        $("#swGrandTotal").val("");
                        $("#swGrandTotal").val(swAuthCancelTotalValue.toFixed(2));

                        $("#swBillAmt").val("");
                        $("#swBillAmt").val(swAuthCancelTotalValue.toFixed(2));
                    }
                }
                else
                {
                    //                    $("#swGrandTotal").val("");
                    //                    $("#swGrandTotal").val(swAuthCancelTotalValue.toFixed(2));
                    //
                    //                    $("#swBillAmt").val("");
                    //                    $("#swBillAmt").val(swAuthCancelTotalValue.toFixed(2));
                }
            }
            else
            {
                swAuthCancelRowVatValue = Math.round(swAuthCancelRowPriceValue * $("#dynamicTxtVatValue" + swCancelLopValue).val() / 100);
                swAuthCancelRowPriceValueWithVat = Math.round(swAuthCancelRowPriceValue + swAuthCancelRowVatValue);

                $("#dynamicNetValue" + swCancelLopValue).val("");
                $("#dynamicNetValue" + swCancelLopValue).val(swAuthCancelRowPriceValueWithVat.toFixed(2));

                swAuthCancelTotalValue = Math.round(parseInt(swAuthCancelTotalValue) + parseInt($("#dynamicNetValue" + swCancelLopValue).val()));
                if (swAuthCancelTotalValue != "" || swAuthCancelTotalValue != 0)
                {
                    if (swAuthCancelPackingCharge != "" || swAuthCancelPackingCharge != 0)
                    {
                        swTotalWitPckageValue = Math.round(parseInt(swAuthCancelTotalValue) + parseInt(swAuthCancelPackingCharge));
                        $("#swGrandTotal").val("");
                        $("#swGrandTotal").val(swTotalWitPckageValue.toFixed(2));

                        $("#swBillAmt").val("");
                        $("#swBillAmt").val(swTotalWitPckageValue.toFixed(2));
                    }
                    else
                    {
                        $("#swGrandTotal").val("");
                        $("#swGrandTotal").val(swAuthCancelTotalValue.toFixed(2));

                        $("#swBillAmt").val("");
                        $("#swBillAmt").val(swAuthCancelTotalValue.toFixed(2));
                    }
                }
                else
                {
                    //                    $("#swGrandTotal").val("");
                    //                    $("#swGrandTotal").val(swAuthCancelTotalValue.toFixed(2));
                    //
                    //                    $("#swBillAmt").val("");
                    //                    $("#swBillAmt").val(swAuthCancelTotalValue.toFixed(2));
                }
            }
        }
    }
    else if (swAuthCancelRowCount == 1)
    {
        swAuthCancelRowPriceValue = Math.round($("#dynamicTxtQuantity1").val() * $("#dynamicTxtRate1").val());

        swAuthCancelRowVatValue = Math.round(swAuthCancelRowPriceValue * $("#dynamicTxtVat1").val() / 100);

        swAuthCancelRowPriceValueWithVat = Math.round(swAuthCancelRowPriceValue + swAuthCancelRowVatValue);

        if (swAuthCancelRowPriceValueWithVat > 0)
        {
            $("#dynamicNetValue1").val("");
            $("#dynamicNetValue1").val(swAuthCancelRowPriceValueWithVat.toFixed(2));
            if (swAuthCancelPackingCharge != "" || swAuthCancelPackingCharge != 0)
            {
                swTotalWitPckageValue = Math.round(parseInt(swAuthCancelRowPriceValueWithVat) + parseInt(swAuthCancelPackingCharge));
                $("#swGrandTotal").val("");
                $("#swGrandTotal").val(swTotalWitPckageValue.toFixed(2));

                $("#swBillAmt").val("");
                $("#swBillAmt").val(swTotalWitPckageValue.toFixed(2));
            }
            else
            {
                $("#swGrandTotal").val("");
                $("#swGrandTotal").val(swAuthCancelRowPriceValueWithVat.toFixed(2));

                $("#swBillAmt").val("");
                $("#swBillAmt").val(swAuthCancelRowPriceValueWithVat.toFixed(2));
            }
        }
        else
        {

        }
    }
    document.getElementById('light').style.display = 'none';
    document.getElementById('fade').style.display = 'none';
}

function billAmtWithPackingCharge()
{
    var pckGarndTotalAmount = $("#swGrandTotal").val().trim();
    var pckDiscountAmount = $("#sWDiscountPercentage").val().trim();
    var pckPackingCharge = $("#swPackingCharge").val().trim();
    var swProdTableRowCount = $('#sandalWood-pruchase_tbody tr').length;
    var swTotalWithNoPackingChrg = 0;

    if (pckGarndTotalAmount !== "")
    {
        if (pckPackingCharge !== "")
        {
            $("#swBillAmt").val((parseFloat(pckGarndTotalAmount) + parseFloat(pckPackingCharge)).toFixed(2));
        }
        else
        {
            if (swProdTableRowCount > 1)
            {
                for (var swPckLop = 1; swPckLop <= swProdTableRowCount; swPckLop++)
                {
                    swTotalWithNoPackingChrg = Math.round(parseInt(swTotalWithNoPackingChrg) + parseInt($("#dynamicNetValue" + swPckLop).val()));
                }
                $("#swGrandTotal").val("");
                $("#swGrandTotal").val(swTotalWithNoPackingChrg.toFixed(2));

                $("#swBillAmt").val("");
                $("#swBillAmt").val(swTotalWithNoPackingChrg.toFixed(2));
            }
            else if (swProdTableRowCount == 1)
            {
                var totValue1 = $("#dynamicNetValue1").val().trim();
                $("#swGrandTotal").val("");
                $("#swGrandTotal").val(totValue1);

                $("#swBillAmt").val("");
                $("#swBillAmt").val(totValue1);
            }
        }
    }
}

function insertSwTableProducts()
{
    var swAryMtrl = [];
    var swAryDesc = [];
    var swAryQuantity = [];
    var swAryRate = [];
    var swAryValue = [];
    var swAryDiscountPer = [];
    var swAryDiscountValue = [];
    var swAryVatPer = [];
    var swAryVatValue = [];
    var swAryVendor = [];

    var swPshMtrl = "";
    var swPshDesc = "";
    var swPshQuantity = "";
    var swPshRate = "";
    var swPshValue = "";
    var swPshVatPer = "";
    var swPshVendor = "";
    var packagedSwData = "";


    var swLoopVal = 1;
    var swEmpId = "";
    var swVatValue = "";
    var swBillAmount = "";
    var swShowRoomId = "";
    var swPaymentType = "";
    var boolenQtyValue = 0;
    var swSalesOrderNo = "";
    var swDiscountValue = "";
    var swNetBillAmount = "";
    var swPackingChargeValue = "";
    var swDiscountApprovalText = "";
    var swTableRowNos = $('#sandalWood-pruchase_tbody tr').length;

    swShowRoomId = "2000";
    swPaymentType = "Cash";
    swEmpId = $("#txtlog_empid").text();
    swNetBillAmount = $("#swGrandTotal").val().trim();
    swSalesOrderNo = $("#Txtorderno").text();
    swBillAmount = $("#swBillAmt").val().trim();


    if ($("#sWDiscountPercentage").val() !== "")//CHECKING THE DISCOUNT VALUE IS ENTERED OR NOT
    {
        swDiscountValue = $("#sWDiscountPercentage").val();
        for (var isDiscountloop = 1; isDiscountloop <= swTableRowNos; isDiscountloop++)
        {
            swAryDiscountPer.push($("#sWDiscountPercentage").val());
        }

        if ($("#swTxtApproval").val() !== "")
        {
            swDiscountApprovalText = $("#swTxtApproval").val().trim();
        }
        else if ($("#swTxtApproval").val() == "")
        {
            $("#swTxtApproval").val(".");
            $("#swTxtApproval").hide();
            swDiscountApprovalText = $("#swTxtApproval").val().trim();
        }
    }
    else
    {
        swDiscountValue = 0;
        for (var isNoDiscountloop = 1; isNoDiscountloop <= swTableRowNos; isNoDiscountloop++)
        {
            swAryDiscountPer.push(swDiscountValue);
        }
        $("#swTxtApproval").val(".");
    }


    if ($("#swPackingCharge").val() !== "")//CHECKING THE PACKINGH-CHARGES IS ENTERED OR NOT
    {
        swPackingChargeValue = $("#swPackingCharge").val().trim();
    }
    else
    {
        swPackingChargeValue = 0;//ASSINGING DEFAULT VALUE FOR PACKINGCHARGE,IF PACKINGCHARGE IS NOT ENTERED
    }

    for (var iVar = 1; iVar <= swTableRowNos; iVar++)
    {
        swPshMtrl = $("#dynamicTxtMaterial" + iVar).val().toString();
        swAryMtrl.push(swPshMtrl);

        swPshDesc = $("#dynamicTxtDescription" + iVar).val().toString();
        swAryDesc.push(swPshDesc);

        swPshVendor = $("#dynamicTxtVendor" + iVar).val().toString();
        swAryVendor.push(swPshVendor);

        swPshQuantity = $("#dynamicTxtQuantity" + iVar).val().toString();
        swAryQuantity.push(swPshQuantity);

        swPshRate = $("#dynamicTxtRate" + iVar).val().toString();
        swAryRate.push(swPshRate);

        swPshVatPer = $("#dynamicTxtVat" + iVar).val().toString();
        swAryVatPer.push(swPshVatPer);

        swVatValue = $("#dynamicTxtVatValue" + iVar).val().toString();
        swAryVatValue.push(swVatValue);

        swAryDiscountPer.push(swDiscountValue);

        swDiscountValue = $("#dynamicsWDiscountPercentageValue" + iVar).val().toString();
        swAryDiscountValue.push(swDiscountValue);

        swPshValue = $("#dynamicNetValue" + iVar).val().toString();
        swAryValue.push(swPshValue);
    }
    swDiscountApprovalText = $("#swTxtApproval").val().trim();

    for (var jVar = 0; jVar < swTableRowNos; jVar++)
    {
        if (packagedSwData === "")
        {
            packagedSwData = swSalesOrderNo + "-" + swEmpId + "-" + swPaymentType + "-" + swShowRoomId + "-" + swDiscountApprovalText + "-" + swPackingChargeValue + "-" + swNetBillAmount + "-" + swBillAmount + "-" + swAryMtrl[jVar] + "-" + swAryDesc[jVar] + "-" + swAryQuantity[jVar] + "-" + swAryRate[jVar] + "-" + swAryVatPer[jVar] + "-" + swAryVatValue[jVar] + "-" + swAryDiscountPer[jVar] + "-" + swAryDiscountValue[jVar] + "-" + swAryValue[jVar] + "-" + swAryVendor[jVar];
            alert("SINGLE   ----->  :  " + packagedSwData);
        }
        else {
            packagedSwData = packagedSwData + "," + swAryMtrl[jVar] + "-" + swAryDesc[jVar] + "-" + swAryQuantity[jVar] + "-" + swAryRate[jVar] + "-" + swAryVatPer[jVar] + "-" + swAryVatValue[jVar] + "-" + swAryDiscountPer[jVar] + "-" + swAryDiscountValue[jVar] + "-" + swAryValue[jVar] + "-" + swAryVendor[jVar];
            alert("MULTIPLE ----->  :  " + packagedSwData);
        }
    }
}

function swRemovingSelectedItem(swRcvKeyId, swRcvTrId)
{
    var recentRowCount;
    var replacingValue = "";
    var sandalWoodTotalValue = 0;
    var swSummaryRowCount = $('#sandalWood-summry-table tr').length;

    if (confirm("Remove This Item?"))//COMFIRMING WIHT THE USER FOR REMOVAL OF THE ROW
    {
        var trTxtValue = $("#dynamicNetValue" + swRcvTrId).val().toString();
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
                sandalWoodTotalValue = Math.round(parseInt(sandalWoodTotalValue) + parseInt($("#dynamicNetValue" + loopVar).val()));
            }
        }
        else if (recentRowCount === 1)
        {
            sandalWoodTotalValue = Math.round($("#dynamicNetValue1").val());
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

        if (recentRowCount > 0)
        {
            var summaryQuantityValue = $("#summary-Quantity-1").text();
            var totalQty = parseFloat(summaryQuantityValue) - parseFloat(trQuantity);
            $("#summary-Quantity-1").text("");
            $("#summary-Quantity-1").text(totalQty.toFixed(2));
        }
        else
        {
            $("#summary-tr-id1").remove();
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

//            $("#dynamicNetValue" + rcvIndexValue).val("");
//            $("#dynamicNetValue" + rcvIndexValue).val(calculateDiscountValueQty);
            $("#dynamicsWDiscountPercentageValue" + rcvIndexValue).val("");
            $("#dynamicsWDiscountPercentageValue" + rcvIndexValue).val(calculateDiscountValueQty);

            prodPriceWithDiscount = calculatedProductPrice - calculateDiscountValueQty;

            calculateVatQty = Math.round(prodPriceWithDiscount * dynamicVatPercentage / 100);
            calculatedProductValue = Math.round(parseFloat(prodPriceWithDiscount) + parseFloat(calculateVatQty));

            $("#dynamicTxtVatValue" + rcvIndexValue).val("");
            $("#dynamicTxtVatValue" + rcvIndexValue).val(calculateVatQty);

            $("#dynamicNetValue" + rcvIndexValue).val("");
            $("#dynamicNetValue" + rcvIndexValue).val(calculatedProductValue.toFixed(2));
        }
        else
        {
            calculatedProductPrice = 0;
            calculateVatQty = 0;
            calculatedProductValue = 0;

//            $("#dynamicNetValue" + rcvIndexValue).val("");
//            $("#dynamicNetValue" + rcvIndexValue).val(0);
            $("#dynamicsWDiscountPercentageValue" + rcvIndexValue).val("");
            $("#dynamicsWDiscountPercentageValue" + rcvIndexValue).val(0);

            calculatedProductPrice = Math.round(parseFloat(dynamicQuantityValue) * parseFloat(dynamicProductRate));
            calculateVatQty = Math.round(calculatedProductPrice * dynamicVatPercentage / 100);

            $("#dynamicTxtVatValue" + rcvIndexValue).val("");
            $("#dynamicTxtVatValue" + rcvIndexValue).val(calculateVatQty);

            calculatedProductValue = parseFloat(calculatedProductPrice) + parseFloat(calculateVatQty);

            $("#dynamicNetValue" + rcvIndexValue).val("");
            $("#dynamicNetValue" + rcvIndexValue).val(calculatedProductValue.toFixed(2));
        }

        if (swPurchaseTableRowCount > 1)
        {
            while (lopValue <= swPurchaseTableRowCount)
            {
                smRecentTotalValue = parseFloat(smRecentTotalValue) + parseFloat($("#dynamicNetValue" + lopValue).val());
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
        $("#dynamicNetValue" + rcvIndexValue).val("0.0");
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
        $(this).attr('id', 'dynamicNetValue' + countValue);
        $(this).attr('name', 'dynamicNetValue' + countValue);
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















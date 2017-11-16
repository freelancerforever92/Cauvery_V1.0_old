var disCuntCalValue = 0, prodPurchase_Count = 0, cal_val = 0, calBill_Amt = 0, pck_calval = 0, calcu_vatVatValue = 0, flagValue = 0, wo = 0;
var submitted = 0;
var index_value = 1;
var initialVatValue = 0;
var sapStockCondition = 0;
var calcuDisCountValue = 0;
var customerInfoInsValue = 0;
var customerInfoSaveEditText = 0;
var materialCraftGroup;
var genericCounterName;
$(document).ready(function()
{
    $(".disApproval").hide();
    $(".optnAddRemove").hide();
    $("#btnSavePrint").attr("disabled", true);
    $("#salesBackDate").attr("disabled", true);
    //genericCounterName = $("#navigatedCounterName").find("option:selected").text().trim();
    genericCounterName = $("#navigatedCounterName").val();
    if (genericCounterName == "Select") {
        $("#navigatedCounterName").focus();
        document.getElementById('navigatedCounterName').style.borderColor = "red";
        $("#Txtmaterial").attr("disabled", true);
        $("#Txtvendor").attr("disabled", true);
        $("#Txtquantity").attr("disabled", true);
    }
    $('#enableDisableBackDated').click(function() {
        if ($(this).is(":checked")) {
            $("#salesBackDate").attr("disabled", false);
            $("#enableManualBil").attr("disabled", false);

            $("#Txtmaterial").val("89070430");
        }
        else {
            $('#salesBackDate').val('');
            $("#salesBackDate").attr("disabled", true);
            $("#enableManualBil").attr("disabled", true);
            $("#Txtmaterial").val("");
            $("#enableManualBil").val("");
        }
    });

    /*
     * 04-09-2015
     * Displaying Backdated Option Only For Super Users.
     */
    $.getJSON('SessionName.action', function(data) {
        if (data.logusrtype === "salesCounter_block") {
            document.getElementById('control-backDated').style.display = "none";
        }
    });

    /*
     fillPaymentType();
     $(".visible_currency").hide();
     $(".visible_CouponRedun").hide();
     */

    /*(11-06-2015)-PRANESH-FOR MOVING CURSOR AT THE LAST OF THE MATERIAL ID,WHEN BACK DATED OPTION IS CLICKED*/
    $('#enableManualBil').keypress("keypress", function(e) {
        if (e.keyCode === 13) {
            if ($('#enableDisableBackDated').is(":checked")) {
                var txt = $("#Txtmaterial");
                var txtLen = $("#Txtmaterial").val().length;
                $(txt).focus();
                var txtRange = $("#Txtmaterial").get(0).createTextRange();
                txtRange.moveStart("character", txtLen);
                txtRange.moveEnd("character", txtLen);
                txtRange.select();
            }
        }
    });

    $('#Txtmaterial').keypress("keypress", function(e) {
        if (e.keyCode === 13) {
            $("#Txtvendor").focus();
        }
    });

    $('#Txtvendor').keypress("keypress", function(e) {
        if (e.keyCode === 13) {
            $("#Txtquantity").focus();
        }
    });

    $('#Txtquantity').keypress("keypress", function(e)
    {
        var txtMaterial = "", txtVendor = "", txtQuantity = "";
        txtMaterial = $("#Txtmaterial").val().trim();
        txtVendor = $("#Txtvendor").val().trim();
        txtQuantity = $("#Txtquantity").val().trim();
        if (e.keyCode === 13)
        {
            if (txtMaterial !== "" || txtMaterial !== null)
            {
                if (txtVendor !== "" || txtVendor !== null)
                {
                    if (txtQuantity !== "" || txtQuantity !== null)
                    {
                        if (txtQuantity > 0 && txtQuantity !== null)
                        {
                            loadMaterialDetails();
                            reset_GrandTotal_On_Dyanamic_Quantity_Updating();
                            $("#Txtmaterial").focus();
                            if ($('#enableDisableBackDated').is(":checked")) {
                                $("#Txtmaterial").val("");
                                $("#Txtmaterial").val("89070430");
                            }
                            else {
                                $("#Txtmaterial").val("");
                            }
                            $("#Txtvendor").val("");
                            $("#Txtquantity").val("");
                            $("#btnSavePrint").attr("disabled", false);
                        }
                        else if (txtQuantity === 0)
                        {
                            alert("Quantity Must Be Greater Than 0");
                            $("#Txtquantity").val("");
                            $("#Txtquantity").focus();
                        }
                    }
                    else
                    {
                        alert("Quantity Is Missing !!");
                        $("#Txtquantity").focus();
                    }
                }
                else
                {
                    alert("Vendor-Id is missing !!");
                    $("#Txtvendor").focus();
                }
            }
            else
            {
                alert("Material-Id is missing !!");
                $("#Txtmaterial").focus();
            }
            return false; // prevent the button click from happening
        }
    });
});

//VALIDATING MATERIAL-ID
function validate_material()
{
    var selectedCounterName;
    $.getJSON('SessionName.action', function(data) {
        if (data.logusrtype !== "all") {
            //counterName = $("#navigatedCounterName").find("option:selected").text().trim();
            selectedCounterName = $("#navigatedCounterName").val();
        } else if (data.logusrtype === "all") {
            //counterName = $("#navigatedCounterNameSA").find("option:selected").text().trim();
            selectedCounterName = $("#navigatedCounterNameSA").val();
        }
        //var selectedCounterName = $("#navigatedCounterName").find("option:selected").text().trim();
        $("#hiddenNavigatedCounterName").val("");
        $("#hiddenNavigatedCounterName").val(selectedCounterName);
        var material_id = $("#Txtmaterial").val().trim();
        if (material_id !== "")
        {
            $.getJSON('validateMaterialId.action', $("#Salesform").serialize(), function(data)
            {
                if (data.isMaterialIdVaild != false)
                {
                    if (data.isSameCraftGroup != false)
                    {
                        $("#Txtvendor").focus();
                    }
                    else
                    {
                        alert("\t\t\t\tOops !!! \n\n This Material is not acceptable in this counter");
                        $("#Txtmaterial").val("");
                        $("#Txtmaterial").focus();
                    }
                }
                else if (data.isMaterialIdVaild == false)
                {
                    if ($('#enableDisableBackDated').is(":checked")) {
                        $("#Txtmaterial").val("");
                        $("#Txtmaterial").val("89070430");
                    }
                    else {
                        alert("\tOops !!!  \n\nInvaild MaterialId");
                        $("#Txtmaterial").val("");
                        $("#Txtmaterial").focus();
                    }

                }
            });
        }
    });
}

$('#Txtmaterial').keypress("keypress", function(e)
{
    if (e.keyCode === 32) {
        $("#TxtDiscount").focus();
        $("#Txtmaterial").val("").trim();
    }
});
//VALIDATING VENDOR-ID
function validate_vendor_old()
{
    var vendor_id = $("#Txtvendor").val().trim();
    if (vendor_id !== "")
    {
        $.getJSON('validateVendor.action', $("#Salesform").serialize(), function(data)
        {
            /*VALIDATION FOR SAME CRAFT GROUP VENDOR FOR ENTERED MATERIAL CRAFT GROUP*/
            /*TEMP-BLOCKED-14.08.2015-PRANESH*/
            if (data.isVendorValid === true) {
                if (data.isVendorCgBoolean !== true) {
                    alert("\t\t\t\t\tOops !!!,\n\nVendorid " + $("#Txtvendor").val().trim() + " doesnot belong to Material Group.");
                    $("#Txtvendor").val("");
                    $("#Txtvendor").focus();
                }
            }
            else
            if (data.isVendorValid === false) {
                alert("In-vaild Vendor-Id");
                $("#Txtvendor").val("");
                $("#Txtvendor").focus();
            }
        });
    }
}

function validate_vendor()
{
    var vendor_id = $("#Txtvendor").val().trim();
    if (vendor_id !== "")
    {
        $.getJSON('validateVendor.action', $("#Salesform").serialize(), function(data)
        {
            /*VALIDATION FOR SAME CRAFT GROUP VENDOR FOR ENTERED MATERIAL CRAFT GROUP*/
            if (data.isVendorValid === true) {
                if (data.isVendorCgBoolean === true) {
                    // alert("\t\t\tOops !!!\n\n Material & Vendor doesn't match..\n");
                    $("#Txtquantity").val("");
                    $("#Txtquantity").focus();
                } else
                {
                    alert("\t\t\tOops !!!\n\n Material & Vendor doesn't match..\n");
                    $("#Txtvendor").val("");
                    $("#Txtvendor").focus();

                }
            }
            else
            if (data.isVendorValid === false) {
                alert("\t\t\tOops !!!\n\n Invaild Vendorid " + $("#Txtvendor").val().trim() + "...!!\n");
                $("#Txtvendor").val("");
                $("#Txtvendor").focus();
            }
        });
    }
}

var dynaTxtQty = "", dynaRate = "", editRowid = "";

function loadMaterialDetails()
{
    var dbProdPrc = "";
    var dbProdDesc = "";
    var dbProdTaxValue = "";
    var dbProdGSTValue = "";                                                                             // FOR GST
    var backDatedYYYY = 0;
    var backDatedDD = 0;
    var backDatedMM = 0;                                                                                  // FOR GST
    var defaultBackDatedDate = "0000-01-01 01:01:01";                                                     // FOR GST
    var isVatApply = 0;

    var MetrialId = $("#Txtmaterial").val().trim();
    var VendorId = $("#Txtvendor").val().trim();
    var GrandTotal = $("#GrndTotal").val().trim();
    var Quantity = $("#Txtquantity").val().trim();
    var discountPercentage = $("#TxtDiscount").val().trim();                                              // FOR GST
    var indivTotalVal = "";
    var HidnTotalVal;
    var totalStock = "";
    var vendorStock = "";
    $.getJSON('loadPurchaseProductDetails.action', $("#Salesform").serialize(), function(data)
    {
        sapStockCondition = data.stockCondition;
        //if (data.liveStockStaus == true)/*BLOCKED BY PRANESH 21.04.15*/
        if (data.liveStockStaus === 1)
        {
            dbProdPrc = data.materialPrice;
            dbProdPrc = dbProdPrc.toFixed(2);
            dbProdDesc = data.materialDescription;
            dbProdTaxValue = data.materailTaxValue;
            dbProdGSTValue = data.materialCraftGST;
            materialCraftGroup = data.materialCraftGroup;

            totalStock = data.materialStock;
            vendorStock = data.vendorStock;
            indivTotalVal = parseFloat(dbProdPrc) * parseFloat(Quantity);
            /* START,CODE FOR APPLYING GST */
            if ($("#enableDisableBackDated").is(':checked')) {
                if ($("#salesBackDate").val().trim() !== "" && $("#enableManualBil").val().trim() !== "") {
                    defaultBackDatedDate = $("#salesBackDate").val().trim();
                    var backDatedSplitter = defaultBackDatedDate.split('-');
                    backDatedYYYY = backDatedSplitter[0];
                    backDatedMM = backDatedSplitter[1];
                    backDatedDD = backDatedSplitter[2];
                    if ((backDatedYYYY <= 2017) && (backDatedMM <= 6 || backDatedMM <= 06) && (backDatedDD <= 30)) {
                        indivTotalVal = vatCalculation(indivTotalVal, dbProdTaxValue);                                  // FOR - GST
                        isVatApply = 1;
                        /* VAT WILL CALCULATED IF THE SALES IS ON or BEFORE 30-06-2017 */
                    } else {
                        indivTotalVal = indivTotalVal.toFixed(2);                                                       // FOR - GST
                        isVatApply = 0;
                        /* VAT WILL NOT BE CALCULATED IF THE SALES IS ON or BEFORE 30-06-2017 */
                        /* GST WILL BE CALCULATED IF THE SALES IS AFTER 30-06-2017 */
                    }
                } else {
                    indivTotalVal = indivTotalVal.toFixed(2);                                                           // FOR - GST
                    isVatApply = 0;
                    /* VAT WILL NOT BE CALCULATED IF THE SALES IS ON or BEFORE 30-06-2017 */
                    /* GST WILL BE CALCULATED IF THE SALES IS AFTER 30-06-2017 */
                }
            } else {

                /* START,CODE FOR APPLYING GST   */
                if (discountPercentage != "") {
                    indivTotalVal = discountWithGST(indivTotalVal, discountPercentage);
                }
                /* END,CODE FOR APPLYING GST   */
                indivTotalVal = indivTotalVal.toFixed(2);                                                               // FOR - GST
                isVatApply = 0;
                /* VAT WILL NOT BE CALCULATED IF THE SALES IS ON or BEFORE 30-06-2017 */
                /* GST WILL BE CALCULATED IF THE SALES IS AFTER 30-06-2017 */
            }

            if (wo === 0)
            {
                insertRow(MetrialId, dbProdDesc, Quantity, dbProdPrc, indivTotalVal, dbProdTaxValue, VendorId, vendorStock);
                /* START, CODE FOR GST */
                if (isVatApply == 1) {
                    $('.vatTD').css('display', '');
                    $("#hidden-craft-gst-percentage").val("0.00");                                                                        // FOR - GST
                } else if (isVatApply == 0) {
                    $('.vatTD').css('display', 'none');
                    $("#hidden-craft-gst-percentage").val(dbProdGSTValue);                                                                // FOR - GST
                }
                isVatApply = 0;
                /* END, CODE FOR GST */
                insertRowIntoSummayTable(MetrialId, Quantity, totalStock);

                $("#hdnProdPrice").val(indivTotalVal);
                if ($("#GrndTotal").val() === "")
                {
                    isPackingCharge();
                }
                else if ($("#GrndTotal").val() !== "")
                {
                    isPackingCharge();
                }

            }
            else if (wo === 1)
            {
                var UpdatedprodValue = "";
                var UpdatedGrndBill = "";
                var updatedTotalValue = 0;
                var iLop = 1;

                var TablerowCount = $('#pruchase_tbody tr').length;//TABLE ROW COUNT
                if (dynaTxtQty !== "")//DYNAMIC QTY SHOULD BE EMPTY
                {
                    UpdatedprodValue = Quantity * dynaRate;//QTY * INDIVIDUAL PRD VALUE

                    $("#txtValue" + editRowid).val("");
                    $("#txtValue" + editRowid).val(UpdatedprodValue);

                    if (TablerowCount > 1)
                    {
                        while (iLop <= TablerowCount)
                        {
                            updatedTotalValue = updatedTotalValue + parseInt($("#txtValue" + iLop).val());//CALCULATING TOTAL VALUE BY ADDING ALL THE FIELD VALUE
                            iLop++;
                        }
                        UpdatedGrndBill = Math.round(updatedTotalValue);
                    }
                    else if (TablerowCount === 1)
                    {
                        UpdatedGrndBill = Math.round(UpdatedprodValue);
                    }
                }

                $("#txtQuantity" + editRowid).val("");
                $("#txtQuantity" + editRowid).val(Quantity);

                $("#GrndTotal").val("");
                $("#GrndTotal").val(UpdatedGrndBill).toFixed(2);

                $("#BillTotal").val("");
                $("#BillTotal").val(UpdatedGrndBill).toFixed(2);

                wo = 0;//RESETING THE FLAG-VARABILE wo TO 0
                editRowid = "";
                UpdatedGrndBill = "";
                UpdatedprodValue = "";

                $('#Txtvendor').val("");//REMOVING THE VENDOR VALUE
                $('#Txtmaterial').val("");//REMOVING THE MATERIAL VALUE
                $('#Txtquantity').val("");//REMOVING THE QUANTITY VALUE

                $('#Txtvendor').attr("disabled", false);//DISENABLING VENDOR FIELD
                $('#Txtmaterial').attr("disabled", false);//DISENABLING MATERIAL FIELD
                $('#Txtquantity').attr("disabled", false);//DISENABLING QUANTITY FIELD

                $('#Txtvendor').attr("readonly", false);//DISENABLING VENDOR READONLY PROP
                $('#Txtmaterial').attr("readonly", false);//DISENABLING MATERIAL READONLY PROP
                $('#Txtquantity').attr("readonly", false);//DISENABLING QUANTITY READONLY PROP
            }
        } else if (data.liveStockStaus === -1) {
            alert("Oops !!!,Entered quantity is greater than avaiable quantity..,");
            $("#Txtmaterial").val("");
            $("#Txtvendor").val("");
            $("#Txtquantity").val("");
            $("#Txtmaterial").focus();
        } else if (data.liveStockStaus === -2) {
            alert("Oops !!!,Stock is not avaiable..,");
            $("#Txtmaterial").val("");
            $("#Txtvendor").val("");
            $("#Txtquantity").val("");
            $("#Txtmaterial").focus();
        }
        /*
         $("#Txtmaterial").val("");
         $("#Txtvendor").val("");
         $("#Txtquantity").val("");
         alert("Oops !!,Check internet connection and try again..");
         */
    });
//insertRowIntoSummayTable(MetrialId, Quantity, "100");
}

function discountWithGST(BillAmount, DiscountPercentage) {
    var discountValueWithGst = 0;
    if (BillAmount > 0 && DiscountPercentage > 0) {
        discountValueWithGst = Math.round(BillAmount * DiscountPercentage / 100);
        BillAmount = Math.round(BillAmount - discountValueWithGst);
    }
    return BillAmount;
}

function vatCalculation(billValue, vatPercentageValue)
{
    var billAmtWithDiscount;
    var initialBillWithVatValue;
    var insDiscountValue = $("#TxtDiscount").val().trim();

    if (insDiscountValue !== "" && insDiscountValue !== 0)
    {
        initialVatValue = 0;
        calcuDisCountValue = 0;
        if (insDiscountValue > 0)
        {
            calcuDisCountValue = Math.round(billValue * insDiscountValue / 100);
            billAmtWithDiscount = Math.round(billValue - calcuDisCountValue);
            //initialVatValue = Math.round(parseFloat(billAmtWithDiscount) * parseFloat(vatPercentageValue) / 100);
            initialVatValue = (parseFloat(billAmtWithDiscount) * parseFloat(vatPercentageValue) / 100);//LINE MODIFIED DATE 09-11-2014.
            //initialBillWithVatValue = Math.round(billAmtWithDiscount + initialVatValue);
            initialBillWithVatValue = (billAmtWithDiscount + initialVatValue);//LINE MODIFIED DATE 09-11-2014.
            initialBillWithVatValue = initialBillWithVatValue.toFixed(2);//06112014-CHANGED TO TWO DECMIAL PLACES
        }
    }
    else if (insDiscountValue === "")
    {
        initialVatValue = 0;
        calcuDisCountValue = 0;
        initialVatValue = (parseFloat(billValue) * parseFloat(vatPercentageValue) / 100);//LINE MODIFIED DATE 09-11-2014.
        //initialBillWithVatValue = Math.round(billValue + initialVatValue);MODIFIED @ DATE 11-11-2014.
        initialBillWithVatValue = (billValue + initialVatValue);
        initialBillWithVatValue = initialBillWithVatValue.toFixed(2);//06112014-CHANGED TO TWO DECMIAL PLACES
    }
    return initialBillWithVatValue;
}

function gstCalculation(billAmount, craftGSTPercentage) {
    /* START,CODE FOR APPLYING GST   */
    var gstValue = parseFloat(billAmount) * parseFloat(craftGSTPercentage) / 100;
    var actualGST = gstValue / 2;                                                            //  FOR - GST
    actualGST = actualGST.toFixed(2)
    $("#cgst-value").val(actualGST);                                                         //  FOR - GST
    $("#sgst-value").val(actualGST);                                                          //  FOR - GST
    billAmount = parseFloat(billAmount) + parseFloat(actualGST) + parseFloat(actualGST); //  FOR - GST
    return billAmount;
    /* END,CODE FOR APPLYING GST   */
}


function discountCalculation()
{
    var rowVatValue = 0;
    var rowPriceValue;
    var totalValue = 0;
    var rowDiscountValue;
    var rowPriceValueWithVat;
    var rowPriceValueWithDiscount;

    var pckingCharge = $("#PckCharges").val().trim();
    var insGrandTotalAmt = $("#GrndTotal").val().trim();
    var productTableRowCount = $('#pruchase_tbody tr').length;
    var insDisCountPercentage = $("#TxtDiscount").val().trim();
    var totalWithGst = 0;                                                                  // FOR GST
    var craftGstPercentage = $("#hidden-craft-gst-percentage").val().trim();               // FOR GST
    if (insGrandTotalAmt !== "")
    {
        if (insDisCountPercentage !== "")
        {
            if (insDisCountPercentage > 0)
            {
                if (/*insDisCountPercentage > 1 &&*/!(insDisCountPercentage > 5))
                {
                    $("#txtApproval").val("");
                    if ((document.getElementById('light').style.display === "none") && (document.getElementById('fade').style.display === "none"))
                    {
                        $(".disApproval").hide();
                        $("#txtApproval").val(".");
                        $("#discountApprovalText").val("");
                    }

                    if (productTableRowCount > 1)
                    {
                        //calcuDisCountValue=0;
                        totalValue = 0;
                        for (var prodLopVal = 1; prodLopVal <= productTableRowCount; prodLopVal++)
                        {
                            //rowPriceValue = Math.round($("#txtQuantity" + prodLopVal).val() * $("#txtRate" + prodLopVal).val()); MODIFIED @ 11-11-2014
                            rowPriceValue = ($("#txtQuantity" + prodLopVal).val() * $("#txtRate" + prodLopVal).val());

                            //rowDiscountValue = Math.round(rowPriceValue * insDisCountPercentage / 100); MODIFIED @ 11-11-2014
                            rowDiscountValue = (rowPriceValue * insDisCountPercentage / 100);

                            $("#txtDiscountValue" + prodLopVal).val("");
                            $("#txtDiscountValue" + prodLopVal).val(rowDiscountValue.toFixed(2));

                            //rowPriceValueWithDiscount = Math.round(rowPriceValue - rowDiscountValue);MODIFIED @ 13.11.2014
                            rowPriceValueWithDiscount = (rowPriceValue - rowDiscountValue);

                            $("#txtVatValue" + prodLopVal).val("");
                            if (craftGstPercentage === "0.00") {
                                //rowVatValue = Math.round(rowPriceValueWithDiscount * $("#txtVat" + prodLopVal).val() / 100); MODIFIED @ 11-11-2014
                                rowVatValue = (rowPriceValueWithDiscount * $("#txtVat" + prodLopVal).val() / 100);
                                $("#txtVatValue" + prodLopVal).val(rowVatValue.toFixed(2));
                            } else if (craftGstPercentage > 0) {
                                $("#txtVatValue" + prodLopVal).val("0.00");
                            }

                            //rowPriceValueWithVat = Math.round(rowPriceValueWithDiscount + rowVatValue);MODIFIED @ 11-11-2014
                            rowPriceValueWithVat = (rowPriceValueWithDiscount + rowVatValue);
                            if (rowPriceValueWithVat > 0)
                            {
                                $("#txtValue" + prodLopVal).val("");
                                $("#txtValue" + prodLopVal).val(rowPriceValueWithVat.toFixed(2));

                                //totalValue = Math.round(parseInt(totalValue) + parseInt($("#txtValue" + prodLopVal).val()));MODIFIED @ 13-11-2014
                                totalValue = (parseFloat(totalValue) + parseFloat($("#txtValue" + prodLopVal).val()));
                                /* START,CODE FOR APPLYING GST   */
                                if (craftGstPercentage > 0) {
                                    totalWithGst = gstCalculation(totalValue, craftGstPercentage);  // FOR GST
                                }
                                /* END,CODE FOR APPLYING GST   */

                                if (pckingCharge !== "")
                                {
                                    var totWitPckageValue = 0;
                                    if (!(pckingCharge <= 0))
                                    {
                                        /* START,CODE FOR APPLYING GST   */
                                        if (craftGstPercentage === "0.00") {
                                            totWitPckageValue = Math.round(parseFloat(totalValue) + parseFloat(pckingCharge));
                                        } else if (craftGstPercentage > 0) {
                                            totWitPckageValue = Math.round(parseFloat(totalWithGst) + parseFloat(pckingCharge));
                                        }
                                        /* END,CODE FOR APPLYING GST   */

                                        totWitPckageValue = Math.round(totWitPckageValue);
                                        $("#GrndTotal").val("");
                                        //$("#GrndTotal").val(Math.round(totalValue).toFixed(2));//=========1
                                        $("#GrndTotal").val((totalValue).toFixed(2));//=========1

                                        $("#BillTotal").val("");
                                        $("#BillTotal").val(totWitPckageValue.toFixed(2));
                                    }
                                }
                                else
                                {
                                    $("#GrndTotal").val("");
                                    //$("#GrndTotal").val(Math.round(totalValue).toFixed(2)); // For GST
                                    $("#GrndTotal").val((totalValue).toFixed(2));

                                    $("#BillTotal").val("");
                                    /* START,CODE FOR APPLYING GST   */
                                    if (craftGstPercentage === "0.00") {
                                        $("#BillTotal").val(Math.round(totalValue).toFixed(2));
                                    } else if (craftGstPercentage > 0) {
                                        $("#BillTotal").val(Math.round(totalWithGst).toFixed(2));
                                    }
                                    /* END,CODE FOR APPLYING GST   */
                                }
                            }
                            else
                            {
                                $("#TxtDiscount").val("");
                                //rowVatValue = Math.round(rowPriceValue * $("#txtVat" + prodLopVal).val() / 100);MODIFIED @ 13-11-2014
                                //rowPriceValueWithVat = Math.round(rowPriceValue + rowVatValue);MODIFIED @ 13-11-2014

                                rowVatValue = (rowPriceValue * $("#txtVat" + prodLopVal).val() / 100);
                                rowPriceValueWithVat = (rowPriceValue + rowVatValue);

                                $("#txtValue" + prodLopVal).val("");
                                $("#txtValue" + prodLopVal).val(rowPriceValueWithVat.toFixed(2));

                                totalValue = Math.round(parseFloat(totalValue) + parseFloat($("#txtValue" + prodLopVal).val()));

                                $("#GrndTotal").val("");
                                $("#GrndTotal").val(Math.round(totalValue.toFixed(2)));

                                $("#BillTotal").val("");
                                $("#BillTotal").val(Math.round(totalValue.toFixed(2)));
                            }
                        }
                    }
                    else if (productTableRowCount === 1)
                    {
                        totalValue = 0;
                        //rowPriceValue = Math.round($("#txtQuantity1").val() * $("#txtRate1").val());MODIFIED @ 13-11-2014
                        //rowDiscountValue = Math.round(rowPriceValue * insDisCountPercentage / 100);MODIFIED @ 13-11-2014

                        rowPriceValue = ($("#txtQuantity1").val() * $("#txtRate1").val());
                        rowDiscountValue = (rowPriceValue * insDisCountPercentage / 100);

                        $("#txtDiscountValue1").val("");
                        $("#txtDiscountValue1").val(rowDiscountValue.toFixed(2));

                        //rowPriceValueWithDiscount = Math.round(rowPriceValue - rowDiscountValue);MODIFIED @ 13-11-2014
                        rowPriceValueWithDiscount = (rowPriceValue - rowDiscountValue);

                        if (craftGstPercentage === "0.00") {
                            // CALCULATE VAT
                            //rowVatValue = Math.round(rowPriceValueWithDiscount * $("#txtVat1").val() / 100);MODIFIED @ 13-11-2014
                            rowVatValue = (rowPriceValueWithDiscount * $("#txtVat1").val() / 100);
                            rowPriceValueWithVat = (rowPriceValueWithDiscount + rowVatValue);
                        } else if (craftGstPercentage > 0) {
                            /* START,CODE FOR APPLYING GST   */
                            rowVatValue = gstCalculation(rowPriceValueWithDiscount, craftGstPercentage);  // FOR GST
                            /* END,CODE FOR APPLYING GST   */
                            rowPriceValueWithVat = rowVatValue;
                        }
                        $("#txtVatValue1").val("");
                        $("#txtVatValue1").val(rowVatValue.toFixed(2));

                        //rowPriceValueWithVat = Math.round(rowPriceValueWithDiscount + rowVatValue);MODIFIED @ 13-11-2014

                        if (rowPriceValueWithVat > 0)
                        {
                            $("#txtValue1").val("");
                            if (craftGstPercentage === "0.00") {
                                $("#txtValue1").val(rowPriceValueWithVat.toFixed(2));
                            } else if (craftGstPercentage > 0) {
                                $("#txtValue1").val(rowPriceValueWithDiscount.toFixed(2));
                            }

                            //totalValue = Math.round(parseFloat(totalValue) + parseFloat($("#txtValue1").val()));
                            totalValue = parseFloat(totalValue) + parseFloat($("#txtValue1").val());                   // Updated For GST

                            if (pckingCharge !== "")
                            {
                                if (!(pckingCharge <= 0))
                                {
                                    //var totWitPckageValue = Math.round(parseInt(totalValue) + parseInt(pckingCharge));MODIFIED @ 13-11-2014
                                    var totWitPckageValue = Math.round(parseFloat(totalValue) + parseFloat(pckingCharge));

                                    $("#GrndTotal").val("");
                                    $("#GrndTotal").val(totalValue.toFixed(2));

                                    $("#BillTotal").val("");
                                    $("#BillTotal").val(totWitPckageValue.toFixed(2));
                                }
                            }
                            else
                            {
                                $("#GrndTotal").val("");
                                $("#GrndTotal").val(totalValue.toFixed(2));
                                if (craftGstPercentage === "0.00") {
                                    $("#BillTotal").val("");
                                    $("#BillTotal").val(Math.round(totalValue).toFixed(2));
                                } else if (craftGstPercentage > 0) {
                                    $("#BillTotal").val("");
                                    $("#BillTotal").val(Math.round(rowPriceValueWithVat).toFixed(2));
                                }
                            }
                        }
                        else
                        {
                            totalValue = 0;
                            $("#TxtDiscount").val("");
                            //rowVatValue = Math.round(rowPriceValue * $("#txtVat1").val() / 100);MODIFIED @ 13-11-2014
                            //rowPriceValueWithVat = Math.round(rowPriceValue + rowVatValue);MODIFIED @ 13-11-2014

                            rowVatValue = (rowPriceValue * $("#txtVat1").val() / 100);
                            rowPriceValueWithVat = (rowPriceValue + rowVatValue);

                            $("#txtValue1").val("");
                            $("#txtValue1").val(rowPriceValueWithVat.toFixed(2));

                            //totalValue = Math.round(parseInt(totalValue) + parseInt($("#txtValue1").val()));MODIFIED @ 13-11-2014
                            totalValue = Math.round(parseFloat(totalValue) + parseFloat($("#txtValue1").val()));

                            $("#GrndTotal").val("");
                            $("#GrndTotal").val(totalValue.toFixed(2));

                            $("#BillTotal").val("");
                            $("#BillTotal").val(totalValue.toFixed(2));
                        }
                    }
                }
                else
                {

                    $("#txtApproval").val("");
                    document.getElementById('light').style.display = 'block';
                    document.getElementById('fade').style.display = 'block';
                    $("#discountApprovalText").val("");
                    $("#discountApprovalText").focus();
                    var gstBillAmount = 0;
                    if ((document.getElementById('light').style.display === "none") && (document.getElementById('fade').style.display === "none"))
                    {
                        $(".disApproval").hide();
                        $("#txtApproval").val(".");
                        $("#discountApprovalText").val("");
                    }
                    if (productTableRowCount > 1)
                    {
                        //calcuDisCountValue=0;
                        totalValue = 0;
                        for (var prodLopVal = 1; prodLopVal <= productTableRowCount; prodLopVal++)
                        {
                            //rowPriceValue = Math.round($("#txtQuantity" + prodLopVal).val() * $("#txtRate" + prodLopVal).val());MODIFIED @ 13-11-2014
                            //rowDiscountValue = Math.round(rowPriceValue * insDisCountPercentage / 100);MODIFIED @ 13-11-2014
                            rowPriceValue = ($("#txtQuantity" + prodLopVal).val() * $("#txtRate" + prodLopVal).val());
                            rowDiscountValue = (rowPriceValue * insDisCountPercentage / 100);

                            $("#txtDiscountValue" + prodLopVal).val("");
                            $("#txtDiscountValue" + prodLopVal).val(rowDiscountValue);

                            //rowPriceValueWithDiscount = Math.round(rowPriceValue - rowDiscountValue);MODIFIED @ 13-11-2014
                            //rowVatValue = Math.round(rowPriceValueWithDiscount * $("#txtVat" + prodLopVal).val() / 100);MODIFIED @ 13-11-2014

                            rowPriceValueWithDiscount = (rowPriceValue - rowDiscountValue);
                            $("#txtVatValue" + prodLopVal).val("");

                            /* START,CODE FOR APPLYING GST   */
                            if (craftGstPercentage === "0.00") {
                                // VAT CALCULATION
                                rowVatValue = (rowPriceValueWithDiscount * $("#txtVat" + prodLopVal).val() / 100);
                                $("#txtVatValue" + prodLopVal).val(rowVatValue);
                                rowPriceValueWithVat = (rowPriceValueWithDiscount + rowVatValue);
                            } else if (craftGstPercentage > 0) {
                                // GST CALCULATION
                                $("#txtVatValue" + prodLopVal).val("0.00");
                                rowPriceValueWithVat = rowPriceValueWithDiscount;
                            }
                            /* END,CODE FOR APPLYING GST   */

                            //rowPriceValueWithVat = Math.round(rowPriceValueWithDiscount + rowVatValue);MODIFIED @ 13-11-2014

                            if (rowPriceValueWithVat > 0)
                            {
                                // alert("TEST 0 :: "+rowPriceValueWithVat);
                                $("#txtValue" + prodLopVal).val("");
                                $("#txtValue" + prodLopVal).val(rowPriceValueWithVat.toFixed(2));

                                totalValue = Math.round(parseFloat(totalValue) + parseFloat($("#txtValue" + prodLopVal).val()));
                                /* START,CODE FOR APPLYING GST   */
                                if (craftGstPercentage > 0) {
                                    gstBillAmount = gstCalculation(totalValue, craftGstPercentage);  // FOR GST
                                }
                                /* END,CODE FOR APPLYING GST   */
                                if (pckingCharge !== "")
                                {
                                    if (!(pckingCharge <= 0))
                                    {
                                        var totWitPckageValue = Math.round(parseFloat(totalValue) + parseFloat(pckingCharge));
                                        $("#GrndTotal").val("");
                                        $("#GrndTotal").val(totWitPckageValue.toFixed(2));

                                        $("#BillTotal").val("");
                                        /* START,CODE FOR APPLYING GST   */
                                        if (craftGstPercentage === "0.00") {
                                            $("#BillTotal").val(totWitPckageValue.toFixed(2));
                                        } else if (craftGstPercentage > 0) {
                                            $("#BillTotal").val(gstBillAmount.toFixed(2));
                                        }
                                        /* END,CODE FOR APPLYING GST   */
                                    }
                                }
                                else
                                {
                                    $("#GrndTotal").val("");
                                    $("#GrndTotal").val(totalValue.toFixed(2));

                                    $("#BillTotal").val("");
                                    /* START,CODE FOR APPLYING GST   */
                                    if (craftGstPercentage === "0.00") {
                                        $("#BillTotal").val(totalValue.toFixed(2));
                                    } else if (craftGstPercentage > 0) {
                                        $("#BillTotal").val(gstBillAmount.toFixed(2));
                                    }
                                    /* END,CODE FOR APPLYING GST   */
                                }
                            }
                            else
                            {
                                $("#TxtDiscount").val("");
                                //rowVatValue = Math.round(rowPriceValue * $("#txtVat" + prodLopVal).val() / 100);MODIFIED @ 13-11-2014
                                //rowPriceValueWithVat = Math.round(rowPriceValue + rowVatValue);MODIFIED @ 13-11-2014

                                rowVatValue = (rowPriceValue * $("#txtVat" + prodLopVal).val() / 100);
                                rowPriceValueWithVat = (rowPriceValue + rowVatValue);

                                $("#txtValue" + prodLopVal).val("");
                                $("#txtValue" + prodLopVal).val(rowPriceValueWithVat.toFixed(2));

                                totalValue = Math.round(parseFloat(totalValue) + parseFloat($("#txtValue" + prodLopVal).val()));

                                $("#GrndTotal").val("");
                                $("#GrndTotal").val(totalValue.toFixed(2));

                                $("#BillTotal").val("");
                                $("#BillTotal").val(totalValue.toFixed(2));
                            }
                        }
                    }
                    else if (productTableRowCount === 1)
                    {
                        totalValue = 0;
                        //rowPriceValue = Math.round($("#txtQuantity1").val() * $("#txtRate1").val());MODIFIED @ 13-11-2014
                        //rowDiscountValue = Math.round(rowPriceValue * insDisCountPercentage / 100);MODIFIED @ 13-11-2014

                        rowPriceValue = ($("#txtQuantity1").val() * $("#txtRate1").val());
                        rowDiscountValue = (rowPriceValue * insDisCountPercentage / 100);

                        $("#txtDiscountValue1").val("");
                        $("#txtDiscountValue1").val(rowDiscountValue);

                        //rowPriceValueWithDiscount = Math.round(rowPriceValue - rowDiscountValue);MODIFIED @ 13-11-2014
                        rowPriceValueWithDiscount = (rowPriceValue - rowDiscountValue);

                        //rowVatValue = Math.round(rowPriceValueWithDiscount * $("#txtVat1").val() / 100); MODIFIED @ 13 - 11 - 2014
                        $("#txtVatValue1").val("");
                        if (craftGstPercentage === "0.00") {
                            rowVatValue = (rowPriceValueWithDiscount * $("#txtVat1").val() / 100);
                            $("#txtVatValue1").val(rowVatValue);
                            rowPriceValueWithVat = (rowPriceValueWithDiscount + rowVatValue);
                        } else if (craftGstPercentage > 0) {
                            $("#txtVatValue1").val("0.00");
                            rowPriceValueWithVat = rowPriceValueWithDiscount;
                        }

                        //rowPriceValueWithVat = Math.round(rowPriceValueWithDiscount + rowVatValue); MODIFIED @ 13 - 11 - 2014

                        if (rowPriceValueWithVat > 0)
                        {

                            $("#txtValue1").val("");
                            $("#txtValue1").val(rowPriceValueWithVat.toFixed(2));

                            totalValue = Math.round(parseFloat(totalValue) + parseFloat($("#txtValue1").val()));

                            if (pckingCharge !== "")
                            {
                                if (!(pckingCharge <= 0))
                                {
                                    var totWitPckageValue = Math.round(parseFloat(totalValue) + parseFloat(pckingCharge));
                                    $("#GrndTotal").val("");
                                    $("#GrndTotal").val(totWitPckageValue.toFixed(2));

                                    $("#BillTotal").val("");
                                    $("#BillTotal").val(totWitPckageValue.toFixed(2));
                                }
                            }
                            else
                            {
                                $("#GrndTotal").val("");
                                $("#GrndTotal").val(totalValue.toFixed(2));

                                $("#BillTotal").val("");
                                $("#BillTotal").val(totalValue.toFixed(2));
                            }
                        }
                        else
                        {
                            totalValue = 0;
                            $("#TxtDiscount").val("");
                            //rowVatValue = Math.round(rowPriceValue * $("#txtVat1").val() / 100);MODIFIED @ 13 - 11 - 2014
                            //rowPriceValueWithVat = Math.round(rowPriceValue + rowVatValue);MODIFIED @ 13 - 11 - 2014

                            rowVatValue = (rowPriceValue * $("#txtVat1").val() / 100);
                            rowPriceValueWithVat = (rowPriceValue + rowVatValue);

                            $("#txtValue1").val("");
                            $("#txtValue1").val(rowPriceValueWithVat.toFixed(2));

                            totalValue = Math.round(parseFloat(totalValue) + parseFloat($("#txtValue1").val()));

                            $("#GrndTotal").val("");
                            $("#GrndTotal").val(totalValue.toFixed(2));

                            $("#BillTotal").val("");
                            $("#BillTotal").val(totalValue.toFixed(2));
                        }
                    }
                }
            }
        }
        else
        {
            $("#txtApproval").val("");
            //IF DISCOUNT IS NOT ENTERED,CALCULATE VAT AND REPLACE THE PRICE VALUE WITH VAT
            if (productTableRowCount > 1)
            {
                totalValue = 0;
                var gstTotalWithoutDiscount = 0;
                for (var prodLopVal2 = 1; prodLopVal2 <= productTableRowCount; prodLopVal2++)
                {
                    //rowPriceValue = Math.round($("#txtQuantity" + prodLopVal2).val() * $("#txtRate" + prodLopVal2).val());MODIFIED @ 13-11-2014
                    //rowVatValue = Math.round(rowPriceValue * $("#txtVat" + prodLopVal2).val() / 100);MODIFIED @ 13-11-2014
                    rowPriceValue = ($("#txtQuantity" + prodLopVal2).val() * $("#txtRate" + prodLopVal2).val());
                    rowVatValue = (rowPriceValue * $("#txtVat" + prodLopVal2).val() / 100);

                    $("#txtVatValue" + prodLopVal2).val("");

                    /* START,CODE FOR APPLYING GST   */
                    if (craftGstPercentage === "0.00") {
                        $("#txtVatValue" + prodLopVal2).val(rowVatValue.toFixed(2));
                        rowPriceValueWithVat = (rowPriceValue + rowVatValue);
                    } else if (craftGstPercentage > 0) {
                        $("#txtVatValue" + prodLopVal2).val("0.00");
                        rowPriceValueWithVat = (rowPriceValue + 0);
                    }
                    /* END,CODE FOR APPLYING GST   */

                    //rowPriceValueWithVat = Math.round(rowPriceValue + rowVatValue);MODIFIED @ 13-11-2014

                    $("#txtDiscountValue" + prodLopVal2).val("");
                    $("#txtDiscountValue" + prodLopVal2).val(0);
                    if (rowPriceValueWithVat > 0)
                    {
                        $("#txtValue" + prodLopVal2).val("");
                        $("#txtValue" + prodLopVal2).val(rowPriceValueWithVat.toFixed(2));

                        //totalValue = Math.round(parseInt(totalValue) + parseInt($("#txtValue" + prodLopVal2).val()));MODIFIED @ 13-11-2014
                        totalValue = parseFloat(totalValue) + parseFloat($("#txtValue" + prodLopVal2).val());

                        if (craftGstPercentage > 0) {
                            /* START,CODE FOR APPLYING GST   */
                            gstTotalWithoutDiscount = gstCalculation(totalValue, craftGstPercentage);  // FOR GST
                            /* END,CODE FOR APPLYING GST   */
                        }

                        if (pckingCharge !== "")
                        {
                            var totWitPckageValue = 0;
                            if (!(pckingCharge <= 0))
                            {
                                if (craftGstPercentage === "0.00") {
                                    totWitPckageValue = (parseFloat(totalValue) + parseFloat(pckingCharge));
                                } else if (craftGstPercentage > 0) {
                                    totWitPckageValue = (parseFloat(gstTotalWithoutDiscount) + parseFloat(pckingCharge));
                                }

                                $("#GrndTotal").val("");
                                $("#GrndTotal").val(Math.round(totalValue).toFixed(2));//=====3

                                $("#BillTotal").val("");
                                $("#BillTotal").val(Math.round(totWitPckageValue).toFixed(2));
                            }
                        }
                        else
                        {
                            $("#GrndTotal").val("");
                            $("#GrndTotal").val(Math.round(totalValue).toFixed(2));

                            $("#BillTotal").val("");

                            /* START,CODE FOR APPLYING GST   */
                            if (craftGstPercentage === "0.00") {
                                $("#BillTotal").val(Math.round(totalValue).toFixed(2));
                            } else if (craftGstPercentage > 0) {
                                $("#BillTotal").val(Math.round(gstTotalWithoutDiscount).toFixed(2));
                            }
                            /* END,CODE FOR APPLYING GST   */
                        }
                    }
                    else
                    {
                        alert("Undefined Vat Value");
                    }
                }
            }
            else if (productTableRowCount === 1)
            {
                totalValue = 0;
                $("#txtDiscountValue1").val("");
                $("#txtDiscountValue1").val(0);

                //rowPriceValue = Math.round($("#txtQuantity1").val() * $("#txtRate1").val());MODIFIED @ 13-11-2014
                //rowVatValue = Math.round(rowPriceValue * $("#txtVat1").val() / 100);MODIFIED @ 13-11-2014
                rowPriceValue = ($("#txtQuantity1").val() * $("#txtRate1").val());

                if (craftGstPercentage === "0.00") {
                    rowPriceValueWithVat = (rowPriceValue + rowVatValue);
                    rowVatValue = (rowPriceValue * $("#txtVat1").val() / 100);
                } else if (craftGstPercentage > 0) {
                    /* START,CODE FOR APPLYING GST   */
                    rowVatValue = gstCalculation(rowPriceValue, craftGstPercentage);  // FOR GST
                    rowPriceValueWithVat = rowVatValue;
                    /* END,CODE FOR APPLYING GST   */
                }
                $("#txtVatValue1").val("");
                $("#txtVatValue1").val(rowVatValue.toFixed(2));

                //rowPriceValueWithVat = Math.round(rowPriceValue + rowVatValue);MODIFIED @ 13-11-2014

                if (rowPriceValueWithVat > 0)
                {
                    $("#txtValue1").val("");
                    $("#GrndTotal").val("");
                    if (craftGstPercentage === "0.00") {
                        $("#txtValue1").val(rowPriceValueWithVat.toFixed(2));
                        //totalValue = Math.round($("#txtValue1").val());                 // FOR GST
                        totalValue = $("#txtValue1").val();
                        $("#GrndTotal").val(totalValue.toFixed(2));
                    } else if (craftGstPercentage > 0) {
                        $("#txtValue1").val(rowPriceValue.toFixed(2));
                        // $("#GrndTotal").val(Math.round(rowPriceValue).toFixed(2));    // FOR GST
                        $("#GrndTotal").val(rowPriceValue.toFixed(2));
                        rowVatValue = gstCalculation(rowPriceValue, craftGstPercentage);  // FOR GST
                        totalValue = Math.round(rowVatValue);
                    }

                    if (pckingCharge > 0) {
                        totalValue = Number(totalValue) + Number(pckingCharge);
                    }

                    $("#BillTotal").val("");
                    $("#BillTotal").val(Math.round(totalValue).toFixed(2));
                }
                else
                {
                    alert("Undefined Vatt Value");
                }
            }
        }
    }
}

function refreshRowQty(rowIndexthis, rowIndexValue) {
    var rcv_Rate = parseFloat($("#txtRate" + rowIndexValue).val().trim());
    if (rcv_Rate === 0) {
        alert("Enter the amount greater than 0");
        $("#btnSavePrint").attr("disabled", "disabled");
    } else {
        $("#btnSavePrint").attr("disabled", false);
    }
    var updatingRowQty = parseFloat($("#txtQuantity" + rowIndexValue).val().trim());
    var avaiableVendorQty = parseFloat($("#txtVendorStock" + rowIndexValue).val().trim());
    if (avaiableVendorQty !== 0 && sapStockCondition === 1) {//INTERNET IS THERE,STOCK VALIDATION IS "ON".
        if (updatingRowQty <= avaiableVendorQty) {
            reset_Quantity(rowIndexthis, rowIndexValue);
            document.getElementById('txtQuantity' + rowIndexValue).style.borderColor = "";
        } else if (updatingRowQty > avaiableVendorQty) {
            alert("Oops !!!,Entered quantity is greater than avaiable quantity..,");
            $("#txtQuantity" + rowIndexValue).select();
            document.getElementById('txtQuantity' + rowIndexValue).style.borderColor = "red";
        }
    } else if (avaiableVendorQty !== 0 && sapStockCondition === 0) {//STOCK VALIDATION IS "ON",BUT INTERNET IS NOT THERE.
        reset_Quantity(rowIndexthis, rowIndexValue);
    } else if (avaiableVendorQty === 0 && sapStockCondition === 0) {
        reset_Quantity(rowIndexthis, rowIndexValue);
    } else if (avaiableVendorQty === 0 && sapStockCondition === 1) {
        reset_Quantity(rowIndexthis, rowIndexValue);
    }
}
//DYNAMICALLY UPDATEING THE QUANTITY
function reset_Quantity(rcv_indxthis, rcv_indxValue)
{
    var newTotalValue = 0, GrandBillAmt = "", dynamic_QtyValue = "", dynamic_ProductRate = "", calcu_ProductPrice = "", calcu_ProductValue = "";
    var Table_rowCount = $('#pruchase_tbody tr').length, lop = 1;
    var summaryTableRowCount = $('#summary-table-body tr').length;//SUMMARY-TABLE ROW COUNT

    var totValue = 0;
    var calculateVat_qty;
    var prodPriceWithDiscount;
    var totValueWithPackingCharge;
    var calculateDiscountValue_qty;
    var gstCraftValue = $("#hidden-craft-gst-percentage").val().trim();
    var inputDisCountAmt = $("#TxtDiscount").val().trim();
    var insPackingCharges = $("#PckCharges").val().trim();
    dynamic_ProductRate = $("#txtRate" + rcv_indxValue).val().trim();//INDIVIDUAL PRD VALUE
    dynamic_QtyValue = $("#txtQuantity" + rcv_indxValue).val().trim();//QUANTITY
    var dynamic_VatPercentage = $("#txtVat" + rcv_indxValue).val().trim();
    var updatingRowQty = parseFloat($("#txtQuantity" + rcv_indxValue).val().trim());
    var avaiableVendorQty = parseFloat($("#txtVendorStock" + rcv_indxValue).val().trim());
    if (dynamic_QtyValue !== "")
    {
        if (!(dynamic_QtyValue <= 0))
        {
            if (summaryTableRowCount > 0)
            {
                //if (updatingRowQty <= avaiableVendorQty)
                //{document.getElementById('txtQuantity' + rcv_indxValue).style.borderColor = "";
                /*IMPLEMENTED BY PRANESH 21.04.15*/
                var sumryLoopValue = 1;
                while (sumryLoopValue <= summaryTableRowCount)
                {
                    if (($("#summary-matril-" + sumryLoopValue).text()) === ($("#txtMaterial" + rcv_indxValue).val()))
                    {
                        if (materialCraftGroup === "SB")
                        {
                            if (Table_rowCount > 1)
                            {
                                totValue = 0;
                                for (var smmryiLop = 1; smmryiLop <= Table_rowCount; smmryiLop++)
                                {
                                    totValue += parseFloat($("#txtQuantity" + smmryiLop).val());
                                }
                                totValue = truncateDecimals(totValue, 3);
                                $("#summary-quantity-" + sumryLoopValue).text("");
                                $("#summary-quantity-" + sumryLoopValue).text(totValue);
                            } else if (Table_rowCount === 1) {
                                $("#summary-quantity-1").text("");
                                $("#summary-quantity-1").text($("#txtQuantity1").val());
                            }
                        } else {
                            if (Table_rowCount > 1) {
                                reset_SummaryTableQuantity(rcv_indxValue);
                            } else if (Table_rowCount === 1) {
                                $("#summary-quantity-1").text("");
                                $("#summary-quantity-1").text($("#txtQuantity1").val());
                            }
                        }
                    } else {
                        reset_SummaryTableQuantity(rcv_indxValue);
                    }
                    sumryLoopValue++;
                }
                /*} else {
                 alert("Oops !!!,Entered quantity is greater than avaiable quantity..,");
                 $("#txtQuantity" + rcv_indxValue).select();
                 document.getElementById('txtQuantity' + rcv_indxValue).style.borderColor = "red";
                 }*/
            }

            if (inputDisCountAmt !== "")
            {
                if (inputDisCountAmt > 0)
                {
                    calcu_ProductPrice = 0;
                    calculateVat_qty = 0;
                    calcu_ProductValue = 0;
                    calcu_ProductPrice = Math.round(parseFloat(dynamic_QtyValue) * parseFloat(dynamic_ProductRate));
                    //calculateDiscountValue_qty = Math.round(calcu_ProductPrice * inputDisCountAmt / 100);
                    calculateDiscountValue_qty = (calcu_ProductPrice * inputDisCountAmt / 100);

                    $("#txtDiscountValue" + rcv_indxValue).val("");
                    $("#txtDiscountValue" + rcv_indxValue).val(calculateDiscountValue_qty);

                    prodPriceWithDiscount = calcu_ProductPrice - calculateDiscountValue_qty;
                    prodPriceWithDiscount = prodPriceWithDiscount.toFixed(2);
                    //calculateVat_qty = Math.round(prodPriceWithDiscount * dynamic_VatPercentage / 100);MODIFIED @ 11-11-2014
                    $("#txtVatValue" + rcv_indxValue).val("");

                    /*START,CODE FOR APPLYING GST  */
                    if (gstCraftValue === "0.00") {
                        calculateVat_qty = (prodPriceWithDiscount * dynamic_VatPercentage / 100);
                        calcu_ProductValue = parseFloat(prodPriceWithDiscount) + parseFloat(calculateVat_qty);
                        $("#txtVatValue" + rcv_indxValue).val(calculateVat_qty);
                    } else if (gstCraftValue > 0) {
                        $("#txtVatValue" + rcv_indxValue).val("0.00");
                        calcu_ProductValue = prodPriceWithDiscount;
                    }
                    /*END,CODE FOR APPLYING GST  */

                    //$("#txtVatValue" + rcv_indxValue).val(calculateVat_qty.toFixed(2));/*MODIFIED @ 28-04-2015*/
                    //calcu_ProductValue = calcu_ProductValue.toFixed(3);

                    $("#txtValue" + rcv_indxValue).val("");
                    $("#txtValue" + rcv_indxValue).val(calcu_ProductValue);
                    //$("#txtValue" + rcv_indxValue).val(calcu_ProductValue.toFixed(2));/*MODIFIED @ 28-04-2015*/
                }
            }
            else
            {
                /*if (updatingRowQty <= avaiableVendorQty) {*/
                /*IMPLEMENTED BY PRANESH 21.04.15*/
                calcu_ProductPrice = 0;
                calculateVat_qty = 0;
                calcu_ProductValue = 0;

                $("#txtDiscountValue" + rcv_indxValue).val("");
                $("#txtDiscountValue" + rcv_indxValue).val(0);

                calcu_ProductPrice = Math.round(parseFloat(dynamic_QtyValue) * parseFloat(dynamic_ProductRate));
                calcu_ProductValue = calcu_ProductPrice.toFixed(2);
                //calculateVat_qty = Math.round(calcu_ProductPrice * dynamic_VatPercentage / 100);MODIFIED @ 11-11-2014
                $("#txtVatValue" + rcv_indxValue).val("");                                                 //  FOR - GST
                /*START,CODE FOR APPLYING GST  */
                if (gstCraftValue === "0.00") {
                    calculateVat_qty = (calcu_ProductPrice * dynamic_VatPercentage / 100);                 //  FOR - GST
                    $("#txtVatValue" + rcv_indxValue).val(calculateVat_qty);                               //  FOR - GST
                    calcu_ProductValue = parseFloat(calcu_ProductPrice) + parseFloat(calculateVat_qty);    //  FOR - GST
                } else if (gstCraftValue > 0) {
                    $("#txtVatValue" + rcv_indxValue).val("0.00");                                         //  FOR - GST
                }
                /*END,CODE FOR APPLYING GST  */
                //calcu_ProductValue = calcu_ProductValue.toFixed(3);
                $("#txtValue" + rcv_indxValue).val("");
                $("#txtValue" + rcv_indxValue).val(calcu_ProductValue);
                /*}*/
            }

            if (Table_rowCount > 1)
            {
                while (lop <= Table_rowCount)
                {
                    newTotalValue = parseFloat(newTotalValue) + parseFloat($("#txtValue" + lop).val());
                    lop++;
                }
                // GrandBillAmt = Math.round(newTotalValue).toFixed(2);                           // FOR GST
                GrandBillAmt = newTotalValue.toFixed(2);
            }
            else if (Table_rowCount === 1)
            {
                if (updatingRowQty <= avaiableVendorQty) {
                    //GrandBillAmt = Math.round(calcu_ProductValue).toFixed(2);                    // FOR GST
                    GrandBillAmt = calcu_ProductValue.toFixed(2);
                } else {
                    /*IMPLEMENTED BY PRANESH 21.04.15*/
                    //GrandBillAmt = Math.round($("#txtValue" + rcv_indxValue).val()).toFixed(2);  // FOR GST
                    GrandBillAmt = $("#txtValue" + rcv_indxValue).val().toFixed(2);
                }
            }

            if (insPackingCharges !== "")
            {
                if (insPackingCharges > 0)
                {

                    $("#GrndTotal").val("");
                    $("#GrndTotal").val(GrandBillAmt);

                    /*START,CODE FOR APPLYING GST  */
                    if (gstCraftValue === "0.00") {
                        totValueWithPackingCharge = Math.round(parseFloat(GrandBillAmt) + parseFloat(insPackingCharges));
                    } else if (gstCraftValue > 0) {
                        totValueWithPackingCharge = gstCalculation(GrandBillAmt, gstCraftValue);  // FOR GST
                        totValueWithPackingCharge = Math.round(parseFloat(totValueWithPackingCharge) + parseFloat(insPackingCharges));
                    }
                    /* END,CODE FOR APPLYING GST  */

                    totValueWithPackingCharge = totValueWithPackingCharge.toFixed(2);
                    $("#BillTotal").val("");
                    $("#BillTotal").val(totValueWithPackingCharge);
                }
            }
            else
            {

                $("#GrndTotal").val("");
                $("#GrndTotal").val(GrandBillAmt);

                /*START,CODE FOR APPLYING GST  */
                if (gstCraftValue > 0) {
                    GrandBillAmt = gstCalculation(GrandBillAmt, gstCraftValue);  // FOR GST
                }
                /* END,CODE FOR APPLYING GST  */
                GrandBillAmt = Math.round(GrandBillAmt).toFixed(2);
                $("#BillTotal").val("");
                $("#BillTotal").val(GrandBillAmt);
            }
        }
        else
        {
            alert("In-valid Quantity...!!!");
            $("#txtQuantity" + rcv_indxValue).val("");
        }
    }
//reset_GrandTotal_On_Dyanamic_Quantity_Updating();
//NEED TO IMPLEMENT CODE FOR CHECKING WHETHER DISCOUNT,PACKING CHARGES ARE THERE
}

function reset_SummaryTableQuantity(rowIndexValue)
{

    var Table_rowCount = $('#pruchase_tbody tr').length;
    var summaryRowCount = $('#summary-table-body tr').length;
    var unMatchRowQty = $("#txtQuantity" + rowIndexValue).val().trim();
    var materialName = $("#txtMaterial" + rowIndexValue).val().trim();
    var lopVar = 1;
    var lopVar2 = 1;
    var summedValue = 0;
    if (Table_rowCount >= 1)
    {
        while (lopVar <= Table_rowCount)
        {
            if ($("#txtMaterial" + lopVar).val().trim() === materialName)
            {
                summedValue = parseFloat(summedValue) + parseFloat($("#txtQuantity" + lopVar).val().trim());
                summedValue = truncateDecimals(summedValue, 3);
            }
            lopVar++;
        }
    }

    if (summaryRowCount >= 1)
    {
        while (lopVar2 <= summaryRowCount)
        {
            if ($("#summary-matril-" + lopVar2).text() === materialName)
            {
                $("#summary-quantity-" + lopVar2).text("");
                $("#summary-quantity-" + lopVar2).text(summedValue);
            }
            lopVar2++;
        }
    }
}

function insertRowIntoSummayTable(matrilsId, quantitys, stocks)
{
    var i = 1;
    var j = 1;
    var truncated;
    var quantity = 0;
    var rowData = "";
    var flag = false;
    var changeQuantity = parseFloat(quantitys);
    var rowCount = $('#pruchase_tbody tr').length;
    var summaryRowCount = $('#summary-table-body tr').length;
    if (summaryRowCount === 0)
    {
        rowData = '<tr id="summary-tr-id1" class="summary-tr-class"><td id="summary-matril-1" style="text-align: center;" class="summary-matril-class">' + matrilsId + '</td> <td id="summary-quantity-1" style="text-align: center;" class="summary-quantity-class">' + quantitys + '</td> <td id="summary-stock-1" style="text-align: center;" class="summary-stock-class">' + stocks + '</td>';
        $("#summary-table-body").append(rowData);
        reOrder_SummaryTable();
    }
    else if (summaryRowCount >= 1)
    {
        while (i <= summaryRowCount)
        {
            if (matrilsId === $("#summary-matril-" + i).text())
            {
                var changeQty = parseFloat(changeQuantity) + parseFloat($("#summary-quantity-" + i).text());
                changeQty = truncateDecimals(changeQty, 3);
                $("#summary-quantity-" + i).text("");
                $("#summary-quantity-" + i).text(changeQty);
                flag = false;
                break;
            }
            else
            {
                flag = true;
            }
            i++;
        }
    }
    if (!flag === false)
    {
        rowData = '<tr id="summary-tr-id' + i + '" class="summary-tr-class"><td id="summary-matril-' + i + '" style="text-align: center;" class="summary-matril-class">' + matrilsId + '</td> <td id="summary-quantity-' + i + '" style="text-align: center;" class="summary-quantity-class">' + quantitys + '</td> <td id="summary-stock-' + i + '" style="text-align: center;" class="summary-stock-class">' + stocks + '</td>';
        $("#summary-table-body").append(rowData);
        reOrder_SummaryTable();
    }
}


function insertRow(rcv_Mtrl, rcv_Desc, rcv_Quantity, rcv_Rate, rcv_Valu, rcv_VatPercentage, rcv_Vendor, rcvVendorStock) {
    var varValu;
    var totQuantity;
    var newPriceValu;
    var disCountValu;
    var rptSame = 0;
    var totValueWithVat;
    var totValueWithDiscount;
    var rowCount = $('#pruchase_tbody tr').length;//GETTING THE TOTAL ROWS IN THE TABLE
    var disCountPer = $("#TxtDiscount").val().trim();
    $("#navigatedCounterName").attr("disabled", "disabled");//15-11-2014

    var GSTOfCraft = $("#hidden-craft-gst-percentage").val();

    if (rowCount === 0)
    {
        //DIRECTLY INSERTING SINGLE ROW AT THE FIRST TIME
        /*BLOCKED BY GPR 23.04.2015 */
        //$("#pruchase_tbody").append('<tr id="tr' + index_value + '" class="fieldwrapper"> <td style="width: 5px;text-align: center"><label id="lblSno' + index_value + '" for ="sno' + index_value + '" class="fieldSNO" name="lblSno' + index_value + '">' + index_value + '</label></td>  <td style="width: 15px; text-align: center"> <input type="text" readonly="true" name="txtMaterial' + index_value + '" style="width:110px;" id="txtMaterial' + index_value + '" class="fieldTxtMaterial" value="' + rcv_Mtrl + '"/> </td>  <td style="width: 15%; text-align: center">  <textarea readonly="true" class="textarea.span1 fieldTxtDescription" name="txtDescription' + index_value + '"  id="txtDescription' + index_value + '" class="fieldTxtDescription">' + rcv_Desc + '</textarea> </td>  <td style="width: 20px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendor' + index_value + '"  id="txtVendor' + index_value + '" class="fieldTxtVendor" value="' + rcv_Vendor + '"/> </td>  <td style="width: 25px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendorStock' + index_value + '"  id="txtVendorStock' + index_value + '" class="fieldTxtVendorStock" value="' + rcvVendorStock + '"/> </td>  <td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="txtQuantity' + index_value + '"  id="txtQuantity' + index_value + '" class="fieldTxtQuantity" value="' + rcv_Quantity + '"onkeydown="number(event)"  onkeyup="reset_Quantity(this,' + index_value + ');"/> </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:50px;" readonly="true" name="txtRate' + index_value + '"  id="txtRate' + index_value + '" class="fieldTxtRate" value="' + rcv_Rate + '"/>  </td>  <td style="width: 25px; text-align: center;">  <input type="text" style="width:40px;" readonly="true" name="txtVat' + index_value + '"  id="txtVat' + index_value + '" class="fieldTxtStock" value="' + rcv_VatPercentage + '"/> </td>  <td style="width: 25px; text-align: center;">  <input type="text" style="width:40px;" readonly="true" name="txtVatValue' + index_value + '"  id="txtVatValue' + index_value + '" class="fieldTxtVatValue" value="' + initialVatValue.toFixed(2) + '"/> </td> <td style="width: 25px; text-align: center;display: none;">  <input type="text" style="width:40px;" readonly="true" name="txtDiscountValue' + index_value + '"  id="txtDiscountValue' + index_value + '" class="fieldTxtDiscountValue" value="' + calcuDisCountValue + '"/>  </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:55px;" readonly="true" name="txtValue' + index_value + '"  id="txtValue' + index_value + '" class="fieldTxtValue" style="text-align: right" value="' + rcv_Valu + '"/>  </td>  <td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="trEdit' + index_value + '" id="trEdit' + index_value + '" class="fieldPencil" onclick="editItem(this,' + index_value + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="trRemove' + index_value + '" class="fieldMinus" onclick="removingSelectedItem(this,' + index_value + ');" /> </td></tr>');
        /*BLOCKED BY GPR 23.04.2015 */
        var isTender = $("#thisIsTender").val();
        if (isTender === 'tender') {
            $("#pruchase_tbody").append('<tr id="tr' + index_value + '" class="fieldwrapper"> <td style="width: 5px;text-align: center"><label id="lblSno' + index_value + '" for ="sno' + index_value + '" class="fieldSNO" name="lblSno' + index_value + '">' + index_value + '</label></td>  <td style="width: 15px; text-align: center"> <input type="text" readonly="true" name="txtMaterial' + index_value + '" style="width:110px;" id="txtMaterial' + index_value + '" class="fieldTxtMaterial" value="' + rcv_Mtrl + '"/> </td>  <td style="width: 15%; text-align: center">  <textarea readonly="true" class="textarea.span1 fieldTxtDescription" name="txtDescription' + index_value + '"  id="txtDescription' + index_value + '" class="fieldTxtDescription">' + rcv_Desc + '</textarea> </td>  <td style="width: 20px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendor' + index_value + '"  id="txtVendor' + index_value + '" class="fieldTxtVendor" value="' + rcv_Vendor + '"/> </td>  <td style="width: 25px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendorStock' + index_value + '"  id="txtVendorStock' + index_value + '" class="fieldTxtVendorStock" value="' + rcvVendorStock + '"/> </td>  <td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="txtQuantity' + index_value + '"  id="txtQuantity' + index_value + '" class="fieldTxtQuantity" value="' + rcv_Quantity + '"onkeydown="number(event)"  onkeyup="refreshRowQty(this,' + index_value + ');"/> </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:50px;"  name="txtRate' + index_value + '"  id="txtRate' + index_value + '" class="fieldTxtRate" value="' + rcv_Rate + '"onkeydown="number(event)"  onkeyup="refreshRowQty(this,' + index_value + ');"/>  </td>  <td style="width: 25px; text-align: center; display:none;" class = "vatTD">  <input type="text" style="width:40px;" readonly="true" name="txtVat' + index_value + '"  id="txtVat' + index_value + '" class="fieldTxtStock" value="' + rcv_VatPercentage + '"/> </td>  <td style="width: 25px; text-align: center;display:none;" class = "vatTD">  <input type="text" style="width:40px;" readonly="true" name="txtVatValue' + index_value + '"  id="txtVatValue' + index_value + '" class="fieldTxtVatValue" value="' + initialVatValue.toFixed(2) + '"/> </td> <td style="width: 25px; text-align: center;display: none;">  <input type="text" style="width:40px;" readonly="true" name="txtDiscountValue' + index_value + '"  id="txtDiscountValue' + index_value + '" class="fieldTxtDiscountValue" value="' + calcuDisCountValue + '"/>  </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:55px;" readonly="true" name="txtValue' + index_value + '"  id="txtValue' + index_value + '" class="fieldTxtValue" style="text-align: right" value="' + rcv_Valu + '"/>  </td>  <td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="trEdit' + index_value + '" id="trEdit' + index_value + '" class="fieldPencil" onclick="editItem(this,' + index_value + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="trRemove' + index_value + '" class="fieldMinus" onclick="removingSelectedItem(this,' + index_value + ');" /> </td></tr>');
            var rcv_Rate = parseFloat($("#txtRate" + index_value).val().trim());
            if (rcv_Rate === 0) {
                $("#btnSavePrint").attr("disabled", "disabled");
            }
        } else {
            $("#pruchase_tbody").append('<tr id="tr' + index_value + '" class="fieldwrapper"> <td style="width: 5px;text-align: center"><label id="lblSno' + index_value + '" for ="sno' + index_value + '" class="fieldSNO" name="lblSno' + index_value + '">' + index_value + '</label></td>  <td style="width: 15px; text-align: center"> <input type="text" readonly="true" name="txtMaterial' + index_value + '" style="width:110px;" id="txtMaterial' + index_value + '" class="fieldTxtMaterial" value="' + rcv_Mtrl + '"/> </td>  <td style="width: 15%; text-align: center">  <textarea readonly="true" class="textarea.span1 fieldTxtDescription" name="txtDescription' + index_value + '"  id="txtDescription' + index_value + '" class="fieldTxtDescription">' + rcv_Desc + '</textarea> </td>  <td style="width: 20px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendor' + index_value + '"  id="txtVendor' + index_value + '" class="fieldTxtVendor" value="' + rcv_Vendor + '"/> </td>  <td style="width: 25px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendorStock' + index_value + '"  id="txtVendorStock' + index_value + '" class="fieldTxtVendorStock" value="' + rcvVendorStock + '"/> </td>  <td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="txtQuantity' + index_value + '"  id="txtQuantity' + index_value + '" class="fieldTxtQuantity" value="' + rcv_Quantity + '"onkeydown="number(event)"  onkeyup="refreshRowQty(this,' + index_value + ');"/> </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:50px;" readonly="true" name="txtRate' + index_value + '"  id="txtRate' + index_value + '" class="fieldTxtRate" value="' + rcv_Rate + '"/>  </td>  <td style="width: 25px; text-align: center; display:none;" class = "vatTD">  <input type="text" style="width:40px;" readonly="true" name="txtVat' + index_value + '"  id="txtVat' + index_value + '" class="fieldTxtStock" value="' + rcv_VatPercentage + '"/> </td>  <td style="width: 25px; text-align: center;display:none;" class = "vatTD">  <input type="text" style="width:40px;" readonly="true" name="txtVatValue' + index_value + '"  id="txtVatValue' + index_value + '" class="fieldTxtVatValue" value="' + initialVatValue.toFixed(2) + '"/> </td> <td style="width: 25px; text-align: center;display: none;">  <input type="text" style="width:40px;" readonly="true" name="txtDiscountValue' + index_value + '"  id="txtDiscountValue' + index_value + '" class="fieldTxtDiscountValue" value="' + calcuDisCountValue + '"/>  </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:55px;" readonly="true" name="txtValue' + index_value + '"  id="txtValue' + index_value + '" class="fieldTxtValue" style="text-align: right" value="' + rcv_Valu + '"/>  </td>  <td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="trEdit' + index_value + '" id="trEdit' + index_value + '" class="fieldPencil" onclick="editItem(this,' + index_value + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="trRemove' + index_value + '" class="fieldMinus" onclick="removingSelectedItem(this,' + index_value + ');" /> </td></tr>');
        }

        index_value++;
        reOrderingVales();
    }
    else if (rowCount >= 1)
    {
        var i = 1;
        while (i <= rowCount)
        {
            rptSame = rptSame + parseInt($("#txtValue" + i).val().toString());
            if ((rcv_Mtrl === $("#txtMaterial" + i).val().toString()) && (rcv_Vendor === $("#txtVendor" + i).val().toString()))
            {
                if (disCountPer !== "")
                {
                    if (disCountPer > 0)
                    {
                        var avaiableVendorStock = parseFloat($("#txtVendorStock" + i).val());
                        totQuantity = parseFloat($("#txtQuantity" + i).val()) + parseFloat(rcv_Quantity);

                        if (avaiableVendorStock !== 0 && sapStockCondition === 1) {
                            if (totQuantity <= avaiableVendorStock) {
                                $("#txtQuantity" + i).val("");
                                $("#txtQuantity" + i).val(totQuantity);

                                newPriceValu = Math.round($("#txtQuantity" + i).val() * $("#txtRate" + i).val());
                                //disCountValu = Math.round(newPriceValu * disCountPer / 100);
                                disCountValu = (newPriceValu * disCountPer / 100);

                                $("#txtDiscountValue" + i).val("");
                                $("#txtDiscountValue" + i).val(disCountValu);

                                //totValueWithDiscount = Math.round(newPriceValu - disCountValu);
                                totValueWithDiscount = (newPriceValu - disCountValu);

                                //varValu = Math.round(totValueWithDiscount * $("#txtVat" + i).val() / 100);

                                /* START,CODE FOR APPLYING GST   */
                                    if (GSTOfCraft === "0.00") {
                                        varValu = (totValueWithDiscount * $("#txtVat" + i).val() / 100);
                                        totValueWithVat = (totValueWithDiscount + varValu);
                                    } else if (GSTOfCraft > 0) {
                                        totValueWithVat = totValueWithDiscount;
                                        varValu = gstCalculation(totValueWithDiscount, GSTOfCraft);  // FOR GST
                                    }
                                /* END ,CODE FOR APPLYING GST   */

                                $("#txtVatValue" + i).val("");
                                //$("#txtVatValue" + i).val(rcv_Valu);
                                $("#txtVatValue" + i).val(varValu);

                                //totValueWithVat = Math.round(totValueWithDiscount + varValu);
                               
                                $("#txtValue" + i).val("");
                                $("#txtValue" + i).val(totValueWithVat.toFixed(2));
                            } else if (totQuantity > avaiableVendorStock) {
                                alert("Oops !!!,Insufficient Stock..,");
                                document.getElementById('txtQuantity' + i).style.borderColor = "red";
                            }
                        } else if (avaiableVendorStock !== 0 && sapStockCondition === 0) {
                            $("#txtQuantity" + i).val("");
                            $("#txtQuantity" + i).val(totQuantity);

                            newPriceValu = Math.round($("#txtQuantity" + i).val() * $("#txtRate" + i).val());

                            //disCountValu = Math.round(newPriceValu * disCountPer / 100);
                            disCountValu = (newPriceValu * disCountPer / 100);

                            $("#txtDiscountValue" + i).val("");
                            $("#txtDiscountValue" + i).val(disCountValu);

                            //totValueWithDiscount = Math.round(newPriceValu - disCountValu);
                            totValueWithDiscount = (newPriceValu - disCountValu);

                            //varValu = Math.round(totValueWithDiscount * $("#txtVat" + i).val() / 100);

                            /* START,CODE FOR APPLYING GST   */
                                if (GSTOfCraft === "0.00") {
                                    varValu = (totValueWithDiscount * $("#txtVat" + i).val() / 100);
                                    totValueWithVat = (totValueWithDiscount + varValu);
                                } else if (GSTOfCraft > 0) {
                                    totValueWithVat = totValueWithDiscount;
                                    varValu = gstCalculation(totValueWithDiscount, GSTOfCraft);  // FOR GST
                                }
                            /* END ,CODE FOR APPLYING GST   */

                            $("#txtVatValue" + i).val("");
                            //$("#txtVatValue" + i).val(rcv_Valu);
                            $("#txtVatValue" + i).val(varValu);

                            //totValueWithVat = Math.round(totValueWithDiscount + varValu);
                            
                            $("#txtValue" + i).val("");
                            $("#txtValue" + i).val(totValueWithVat.toFixed(2));
                        } else if (avaiableVendorStock === 0 && sapStockCondition === 0) {
                            $("#txtQuantity" + i).val("");
                            $("#txtQuantity" + i).val(totQuantity);

                            newPriceValu = Math.round($("#txtQuantity" + i).val() * $("#txtRate" + i).val());

                            //disCountValu = Math.round(newPriceValu * disCountPer / 100);
                            disCountValu = (newPriceValu * disCountPer / 100);

                            $("#txtDiscountValue" + i).val("");
                            $("#txtDiscountValue" + i).val(disCountValu);

                            //totValueWithDiscount = Math.round(newPriceValu - disCountValu);
                            totValueWithDiscount = (newPriceValu - disCountValu);

                            //varValu = Math.round(totValueWithDiscount * $("#txtVat" + i).val() / 100);

                            /* START,CODE FOR APPLYING GST   */
                                if (GSTOfCraft === "0.00") {
                                    varValu = (totValueWithDiscount * $("#txtVat" + i).val() / 100);
                                    totValueWithVat = (totValueWithDiscount + varValu);
                                } else if (GSTOfCraft > 0) {
                                    totValueWithVat = totValueWithDiscount;
                                    varValu = gstCalculation(totValueWithDiscount, GSTOfCraft);  // FOR GST
                                }
                            /* END ,CODE FOR APPLYING GST   */

                            $("#txtVatValue" + i).val("");
                            //$("#txtVatValue" + i).val(rcv_Valu);
                            $("#txtVatValue" + i).val(varValu);

                            //totValueWithVat = Math.round(totValueWithDiscount + varValu);

                            $("#txtValue" + i).val("");
                            $("#txtValue" + i).val(totValueWithVat.toFixed(2));
                        } else if (avaiableVendorStock === 0 && sapStockCondition === 1) {
                            $("#txtQuantity" + i).val("");
                            $("#txtQuantity" + i).val(totQuantity);

                            newPriceValu = Math.round($("#txtQuantity" + i).val() * $("#txtRate" + i).val());

                            //disCountValu = Math.round(newPriceValu * disCountPer / 100);
                            disCountValu = (newPriceValu * disCountPer / 100);

                            $("#txtDiscountValue" + i).val("");
                            $("#txtDiscountValue" + i).val(disCountValu);

                            //totValueWithDiscount = Math.round(newPriceValu - disCountValu);
                            totValueWithDiscount = (newPriceValu - disCountValu);

                            //varValu = Math.round(totValueWithDiscount * $("#txtVat" + i).val() / 100);

                            /* START,CODE FOR APPLYING GST   */
                                if (GSTOfCraft === "0.00") {
                                    varValu = (totValueWithDiscount * $("#txtVat" + i).val() / 100);
                                    totValueWithVat = (totValueWithDiscount + varValu);
                                } else if (GSTOfCraft > 0) {
                                    totValueWithVat = totValueWithDiscount;
                                    varValu = gstCalculation(totValueWithDiscount, GSTOfCraft);  // FOR GST
                                }
                            /* END ,CODE FOR APPLYING GST   */

                            $("#txtVatValue" + i).val("");
                            //$("#txtVatValue" + i).val(rcv_Valu);
                            $("#txtVatValue" + i).val(varValu);

                            //totValueWithVat = Math.round(totValueWithDiscount + varValu);

                            $("#txtValue" + i).val("");
                            $("#txtValue" + i).val(totValueWithVat.toFixed(2));
                        }
                    }
                }
                else
                {
                    var presentVendorStock = parseFloat($("#txtVendorStock" + i).val());
                    totQuantity = parseFloat($("#txtQuantity" + i).val()) + parseFloat(rcv_Quantity);
                    if (presentVendorStock !== 0 && sapStockCondition === 1) {
                        if (totQuantity <= presentVendorStock) {
                            //alert("Calculated qty is less");
                            $("#txtQuantity" + i).val("");
                            $("#txtQuantity" + i).val(totQuantity);

                            $("#txtDiscountValue" + i).val("");
                            $("#txtDiscountValue" + i).val(0);

                            //newPriceValu = Math.round($("#txtQuantity" + i).val() * $("#txtRate" + i).val());
                            newPriceValu = (parseFloat($("#txtQuantity" + i).val()) * parseFloat($("#txtRate" + i).val()));

                            //varValu = Math.round(newPriceValu * $("#txtVat" + i).val() / 100);

                            /* START,CODE FOR APPLYING GST   */
                                if (GSTOfCraft === "0.00") {
                                    varValu = (newPriceValu * $("#txtVat" + i).val() / 100);
                                    totValueWithVat = (parseFloat(newPriceValu) + parseFloat(varValu));
                                } else if (GSTOfCraft > 0) {
                                    totValueWithVat = newPriceValu;
                                    varValu = gstCalculation(newPriceValu, GSTOfCraft);  // FOR GST
                                }
                            /* END ,CODE FOR APPLYING GST   */

                            $("#txtVatValue" + i).val("");
                            //$("#txtVatValue" + i).val(rcv_Valu);
                            $("#txtVatValue" + i).val(varValu);

                            //totValueWithVat = Math.round(parseFloat(newPriceValu) + parseFloat(varValu));
                            
                            $("#txtValue" + i).val("");
                            $("#txtValue" + i).val(totValueWithVat.toFixed(2));
                            document.getElementById('txtQuantity' + i).style.borderColor = "";
                        } else if (totQuantity > presentVendorStock) {
                            alert("Oops !!!,Entered quantity is greater than avaiable quantity..,");
                            document.getElementById('txtQuantity' + i).style.borderColor = "red";
                        }
                    } else if (presentVendorStock !== 0 && sapStockCondition === 0) {
                        $("#txtQuantity" + i).val("");
                        $("#txtQuantity" + i).val(totQuantity);

                        $("#txtDiscountValue" + i).val("");
                        $("#txtDiscountValue" + i).val(0);

                        //newPriceValu = Math.round($("#txtQuantity" + i).val() * $("#txtRate" + i).val());
                        newPriceValu = (parseFloat($("#txtQuantity" + i).val()) * parseFloat($("#txtRate" + i).val()));

                        //varValu = Math.round(newPriceValu * $("#txtVat" + i).val() / 100);

                        /* START,CODE FOR APPLYING GST   */
                            if (GSTOfCraft === "0.00") {
                                varValu = (newPriceValu * $("#txtVat" + i).val() / 100);
                                totValueWithVat = (parseFloat(newPriceValu) + parseFloat(varValu));
                            } else if (GSTOfCraft > 0) {
                                totValueWithVat = newPriceValu;
                                varValu = gstCalculation(newPriceValu, GSTOfCraft);  // FOR GST
                            }
                        /* END ,CODE FOR APPLYING GST   */

                        $("#txtVatValue" + i).val("");
                        //$("#txtVatValue" + i).val(rcv_Valu);
                        $("#txtVatValue" + i).val(varValu);

                        //totValueWithVat = Math.round(parseFloat(newPriceValu) + parseFloat(varValu));
                        
                        $("#txtValue" + i).val("");
                        $("#txtValue" + i).val(totValueWithVat.toFixed(2));
                    } else if (presentVendorStock === 0 && sapStockCondition === 0) {
                        $("#txtQuantity" + i).val("");
                        $("#txtQuantity" + i).val(totQuantity);

                        $("#txtDiscountValue" + i).val("");
                        $("#txtDiscountValue" + i).val(0);

                        //newPriceValu = Math.round($("#txtQuantity" + i).val() * $("#txtRate" + i).val());
                        newPriceValu = (parseFloat($("#txtQuantity" + i).val()) * parseFloat($("#txtRate" + i).val()));

                        //varValu = Math.round(newPriceValu * $("#txtVat" + i).val() / 100);
                        
                        /* START,CODE FOR APPLYING GST   */
                            if (GSTOfCraft === "0.00") {
                                varValu = (newPriceValu * $("#txtVat" + i).val() / 100);
                                totValueWithVat = (parseFloat(newPriceValu) + parseFloat(varValu));
                            } else if (GSTOfCraft > 0) {
                                totValueWithVat = newPriceValu;
                                varValu = gstCalculation(newPriceValu, GSTOfCraft);  // FOR GST
                            }
                        /* END ,CODE FOR APPLYING GST   */
                        
                        $("#txtVatValue" + i).val("");
                        //$("#txtVatValue" + i).val(rcv_Valu);
                        $("#txtVatValue" + i).val(varValu);

                        //totValueWithVat = Math.round(parseFloat(newPriceValu) + parseFloat(varValu));

                        $("#txtValue" + i).val("");
                        $("#txtValue" + i).val(totValueWithVat.toFixed(2));

                    } else if (presentVendorStock === 0 && sapStockCondition === 1) {
                        $("#txtQuantity" + i).val("");
                        $("#txtQuantity" + i).val(totQuantity);

                        $("#txtDiscountValue" + i).val("");
                        $("#txtDiscountValue" + i).val(0);

                        //newPriceValu = Math.round($("#txtQuantity" + i).val() * $("#txtRate" + i).val());
                        newPriceValu = (parseFloat($("#txtQuantity" + i).val()) * parseFloat($("#txtRate" + i).val()));

                        //varValu = Math.round(newPriceValu * $("#txtVat" + i).val() / 100);

                        /* START,CODE FOR APPLYING GST   */
                            if (GSTOfCraft === "0.00") {
                                varValu = (newPriceValu * $("#txtVat" + i).val() / 100);
                                totValueWithVat = (parseFloat(newPriceValu) + parseFloat(varValu));
                            } else if (GSTOfCraft > 0) {
                                totValueWithVat = newPriceValu;
                                varValu = gstCalculation(newPriceValu, GSTOfCraft);  // FOR GST
                            }
                        /* END ,CODE FOR APPLYING GST   */

                        $("#txtVatValue" + i).val("");
                        //$("#txtVatValue" + i).val(rcv_Valu);
                        $("#txtVatValue" + i).val(varValu);

                        //totValueWithVat = Math.round(parseFloat(newPriceValu) + parseFloat(varValu));

                        $("#txtValue" + i).val("");
                        $("#txtValue" + i).val(totValueWithVat.toFixed(2));
                    }

                }
                flagValue = 0;//RESETTING FLAG VALUE TO THE PREVIOUS STATE
                break;
            }
            else
            {
                if (i === rowCount)
                {
                    flagValue = 1;//SETTING FLAG VALUE TO 1 IF NO MATERIAL & VENDOR ID'S MATCH WITH THE VALUES IN EXISTINGs IN TABLE
                }
            }
            i++;
        }

        if (flagValue === 1)//INSERTING A NEW ROW
        {
            /*BLOCKED BY GPR 23.04.2015 */
            //$("#pruchase_tbody").append('<tr id="tr' + index_value + '" class="fieldwrapper"> <td style="width: 5px;text-align: center"><label id="lblSno' + index_value + '" for ="sno' + index_value + '" class="fieldSNO" name="lblSno' + index_value + '">' + index_value + '</label></td>  <td style="width: 15px; text-align: center"> <input type="text" readonly="true" name="txtMaterial' + index_value + '" style="width:110px;" id="txtMaterial' + index_value + '" class="fieldTxtMaterial" value="' + rcv_Mtrl + '"/> </td>  <td style="width: 15%; text-align: center">  <textarea readonly="true" class="textarea.span1 fieldTxtDescription" name="txtDescription' + index_value + '"  id="txtDescription' + index_value + '" class="fieldTxtDescription">' + rcv_Desc + '</textarea> </td>  <td style="width: 25px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendor' + index_value + '"  id="txtVendor' + index_value + '" class="fieldTxtVendor" value="' + rcv_Vendor + '"/> </td>  <td style="width: 25px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendorStock' + index_value + '"  id="txtVendorStock' + index_value + '" class="fieldTxtVendorStock" value="' + rcvVendorStock + '"/> </td>  <td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="txtQuantity' + index_value + '"  id="txtQuantity' + index_value + '" class="fieldTxtQuantity" value="' + rcv_Quantity + '"onkeydown="number(event)"  onkeyup="reset_Quantity(this,' + index_value + ');"/> </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:50px;" readonly="true" name="txtRate' + index_value + '"  id="txtRate' + index_value + '" class="fieldTxtRate" value="' + rcv_Rate + '"/>  </td>  <td style="width: 25px; text-align: center;">  <input type="text" style="width:40px;" readonly="true" name="txtVat' + index_value + '"  id="txtVat' + index_value + '" class="fieldTxtStock" value="' + rcv_VatPercentage + '"/> </td>  <td style="width: 25px; text-align: center;">  <input type="text" style="width:40px;" readonly="true" name="txtVatValue' + index_value + '"  id="txtVatValue' + index_value + '" class="fieldTxtVatValue" value="' + initialVatValue.toFixed(2) + '"/> </td> <td style="width: 25px; text-align: center;display: none;">  <input type="text" style="width:40px;" readonly="true" name="txtDiscountValue' + index_value + '"  id="txtDiscountValue' + index_value + '" class="fieldTxtDiscountValue" value="' + calcuDisCountValue + '"/>  </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:55px;" readonly="true" name="txtValue' + index_value + '"  id="txtValue' + index_value + '" class="fieldTxtValue" style="text-align: right" value="' + rcv_Valu + '"/>  </td>  <td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="trEdit' + index_value + '" id="trEdit' + index_value + '" class="fieldPencil" onclick="editItem(this,' + index_value + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="trRemove' + index_value + '" class="fieldMinus" onclick="removingSelectedItem(this,' + index_value + ');" /> </td></tr>');
            /*BLOCKED BY GPR 23.04.2015 */
            // $("#pruchase_tbody").append('<tr id="tr' + index_value + '" class="fieldwrapper"> <td style="width: 5px;text-align: center"><label id="lblSno' + index_value + '" for ="sno' + index_value + '" class="fieldSNO" name="lblSno' + index_value + '">' + index_value + '</label></td>  <td style="width: 15px; text-align: center"> <input type="text" readonly="true" name="txtMaterial' + index_value + '" style="width:110px;" id="txtMaterial' + index_value + '" class="fieldTxtMaterial" value="' + rcv_Mtrl + '"/> </td>  <td style="width: 15%; text-align: center">  <textarea readonly="true" class="textarea.span1 fieldTxtDescription" name="txtDescription' + index_value + '"  id="txtDescription' + index_value + '" class="fieldTxtDescription">' + rcv_Desc + '</textarea> </td>  <td style="width: 25px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendor' + index_value + '"  id="txtVendor' + index_value + '" class="fieldTxtVendor" value="' + rcv_Vendor + '"/> </td>  <td style="width: 25px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendorStock' + index_value + '"  id="txtVendorStock' + index_value + '" class="fieldTxtVendorStock" value="' + rcvVendorStock + '"/> </td>  <td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="txtQuantity' + index_value + '"  id="txtQuantity' + index_value + '" class="fieldTxtQuantity" value="' + rcv_Quantity + '"onkeydown="number(event)"  onkeyup="refreshRowQty(this,' + index_value + ');"/> </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:50px;" readonly="true" name="txtRate' + index_value + '"  id="txtRate' + index_value + '" class="fieldTxtRate" value="' + rcv_Rate + '"/>  </td>  <td style="width: 25px; text-align: center;">  <input type="text" style="width:40px;" readonly="true" name="txtVat' + index_value + '"  id="txtVat' + index_value + '" class="fieldTxtStock" value="' + rcv_VatPercentage + '"/> </td>  <td style="width: 25px; text-align: center;">  <input type="text" style="width:40px;" readonly="true" name="txtVatValue' + index_value + '"  id="txtVatValue' + index_value + '" class="fieldTxtVatValue" value="' + initialVatValue.toFixed(2) + '"/> </td> <td style="width: 25px; text-align: center;display: none;">  <input type="text" style="width:40px;" readonly="true" name="txtDiscountValue' + index_value + '"  id="txtDiscountValue' + index_value + '" class="fieldTxtDiscountValue" value="' + calcuDisCountValue + '"/>  </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:55px;" readonly="true" name="txtValue' + index_value + '"  id="txtValue' + index_value + '" class="fieldTxtValue" style="text-align: right" value="' + rcv_Valu + '"/>  </td>  <td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="trEdit' + index_value + '" id="trEdit' + index_value + '" class="fieldPencil" onclick="editItem(this,' + index_value + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="trRemove' + index_value + '" class="fieldMinus" onclick="removingSelectedItem(this,' + index_value + ');" /> </td></tr>');
            var isTender = $("#thisIsTender").val();

            if (isTender === 'tender') {
                $("#pruchase_tbody").append('<tr id="tr' + index_value + '" class="fieldwrapper"> <td style="width: 5px;text-align: center"><label id="lblSno' + index_value + '" for ="sno' + index_value + '" class="fieldSNO" name="lblSno' + index_value + '">' + index_value + '</label></td>  <td style="width: 15px; text-align: center"> <input type="text" readonly="true" name="txtMaterial' + index_value + '" style="width:110px;" id="txtMaterial' + index_value + '" class="fieldTxtMaterial" value="' + rcv_Mtrl + '"/> </td>  <td style="width: 15%; text-align: center">  <textarea readonly="true" class="textarea.span1 fieldTxtDescription" name="txtDescription' + index_value + '"  id="txtDescription' + index_value + '" class="fieldTxtDescription">' + rcv_Desc + '</textarea> </td>  <td style="width: 20px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendor' + index_value + '"  id="txtVendor' + index_value + '" class="fieldTxtVendor" value="' + rcv_Vendor + '"/> </td>  <td style="width: 25px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendorStock' + index_value + '"  id="txtVendorStock' + index_value + '" class="fieldTxtVendorStock" value="' + rcvVendorStock + '"/> </td>  <td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="txtQuantity' + index_value + '"  id="txtQuantity' + index_value + '" class="fieldTxtQuantity" value="' + rcv_Quantity + '"onkeydown="number(event)"  onkeyup="refreshRowQty(this,' + index_value + ');"/> </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:50px;"  name="txtRate' + index_value + '"  id="txtRate' + index_value + '" class="fieldTxtRate" value="' + rcv_Rate + '"onkeydown="number(event)"  onkeyup="refreshRowQty(this,' + index_value + ');"/>  </td>  <td style="width: 25px; text-align: center;display:none;" class = "vatTD">  <input type="text" style="width:40px;" readonly="true" name="txtVat' + index_value + '"  id="txtVat' + index_value + '" class="fieldTxtStock" value="' + rcv_VatPercentage + '"/> </td>  <td style="width: 25px; text-align: center;display:none;" class = "vatTD">  <input type="text" style="width:40px;" readonly="true" name="txtVatValue' + index_value + '"  id="txtVatValue' + index_value + '" class="fieldTxtVatValue" value="' + initialVatValue.toFixed(2) + '"/> </td> <td style="width: 25px; text-align: center;display: none;">  <input type="text" style="width:40px;" readonly="true" name="txtDiscountValue' + index_value + '"  id="txtDiscountValue' + index_value + '" class="fieldTxtDiscountValue" value="' + calcuDisCountValue + '"/>  </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:55px;" readonly="true" name="txtValue' + index_value + '"  id="txtValue' + index_value + '" class="fieldTxtValue" style="text-align: right" value="' + rcv_Valu + '"/>  </td>  <td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="trEdit' + index_value + '" id="trEdit' + index_value + '" class="fieldPencil" onclick="editItem(this,' + index_value + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="trRemove' + index_value + '" class="fieldMinus" onclick="removingSelectedItem(this,' + index_value + ');" /> </td></tr>');
            } else {
                $("#pruchase_tbody").append('<tr id="tr' + index_value + '" class="fieldwrapper"> <td style="width: 5px;text-align: center"><label id="lblSno' + index_value + '" for ="sno' + index_value + '" class="fieldSNO" name="lblSno' + index_value + '">' + index_value + '</label></td>  <td style="width: 15px; text-align: center"> <input type="text" readonly="true" name="txtMaterial' + index_value + '" style="width:110px;" id="txtMaterial' + index_value + '" class="fieldTxtMaterial" value="' + rcv_Mtrl + '"/> </td>  <td style="width: 15%; text-align: center">  <textarea readonly="true" class="textarea.span1 fieldTxtDescription" name="txtDescription' + index_value + '"  id="txtDescription' + index_value + '" class="fieldTxtDescription">' + rcv_Desc + '</textarea> </td>  <td style="width: 20px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendor' + index_value + '"  id="txtVendor' + index_value + '" class="fieldTxtVendor" value="' + rcv_Vendor + '"/> </td>  <td style="width: 25px; text-align: center">  <input type="text" style="width:70px;" readonly="true" name="txtVendorStock' + index_value + '"  id="txtVendorStock' + index_value + '" class="fieldTxtVendorStock" value="' + rcvVendorStock + '"/> </td>  <td style="width: 20px; text-align: center"><input type="text" style="width:40px;" name="txtQuantity' + index_value + '"  id="txtQuantity' + index_value + '" class="fieldTxtQuantity" value="' + rcv_Quantity + '"onkeydown="number(event)"  onkeyup="refreshRowQty(this,' + index_value + ');"/> </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:50px;" readonly="true" name="txtRate' + index_value + '"  id="txtRate' + index_value + '" class="fieldTxtRate" value="' + rcv_Rate + '"/>  </td>  <td style="width: 25px; text-align: center;display:none;" class = "vatTD">  <input type="text" style="width:40px;" readonly="true" name="txtVat' + index_value + '"  id="txtVat' + index_value + '" class="fieldTxtStock" value="' + rcv_VatPercentage + '"/> </td>  <td style="width: 25px; text-align: center;display:none;" class = "vatTD">  <input type="text" style="width:40px;" readonly="true" name="txtVatValue' + index_value + '"  id="txtVatValue' + index_value + '" class="fieldTxtVatValue" value="' + initialVatValue.toFixed(2) + '"/> </td> <td style="width: 25px; text-align: center;display: none;">  <input type="text" style="width:40px;" readonly="true" name="txtDiscountValue' + index_value + '"  id="txtDiscountValue' + index_value + '" class="fieldTxtDiscountValue" value="' + calcuDisCountValue + '"/>  </td>  <td style="width: 25px; text-align: right">  <input type="text" style="width:55px;" readonly="true" name="txtValue' + index_value + '"  id="txtValue' + index_value + '" class="fieldTxtValue" style="text-align: right" value="' + rcv_Valu + '"/>  </td>  <td style="text-align: center">  <!--<img src="./images/pencil.gif" width: 20px;" style="margin-right:20px" name="trEdit' + index_value + '" id="trEdit' + index_value + '" class="fieldPencil" onclick="editItem(this,' + index_value + ');"/>--> <img src="./images/minus-circle.gif" width: 20px;" id="trRemove' + index_value + '" class="fieldMinus" onclick="removingSelectedItem(this,' + index_value + ');" /> </td></tr>');
            }
            index_value++;
            flagValue = 0;//RESETTING FLAG VALUE TO THE PREVIOUS STATE
            //isPackingCharge();
            reOrderingVales();
        }
    }
    submitted = 0;
}

function isPackingCharge()
{
    var billTotalAmt = 0;
    var billAmtWithPackingAmt;
    var GSTOfCraft = $("#hidden-craft-gst-percentage").val();
    var tblrowCount = $('#pruchase_tbody tr').length;//GETTING THE TOTAL ROWS IN THE TABLE
    var packingAmount = $("#PckCharges").val().trim();
    if (packingAmount !== "")
    {
        if (tblrowCount > 1)
        {
            for (var lop = 1; lop <= tblrowCount; lop++)
            {
                billTotalAmt = Math.round(parseFloat(billTotalAmt) + parseFloat($("#txtValue" + lop).val()));
            }

            $("#GrndTotal").val("");
            $("#GrndTotal").val((billTotalAmt).toFixed(2));
            /* START,CODE FOR APPLYING GST   */
            billTotalAmt = gstCalculation(billTotalAmt, GSTOfCraft);  // FOR GST
            /* END,CODE FOR APPLYING GST   */
            billAmtWithPackingAmt = parseFloat(billTotalAmt) + parseFloat(packingAmount);
            $("#BillTotal").val("");
            $("#BillTotal").val(Math.round(billAmtWithPackingAmt).toFixed(2));
        }
        else if (tblrowCount === 1)
        {
            var grandTotal = $("#txtValue1").val().trim();
            $("#GrndTotal").val("");
            $("#GrndTotal").val(grandTotal);

            /* START,CODE FOR APPLYING GST   */
            billTotalAmt = gstCalculation(grandTotal, GSTOfCraft);              // FOR GST
            /* END,CODE FOR APPLYING GST   */

            billTotalAmt = Math.round(parseFloat(billTotalAmt) + parseFloat(packingAmount));
            $("#BillTotal").val("");
            $("#BillTotal").val((billTotalAmt).toFixed(2));
        }
    }
    else
    {
        if (tblrowCount > 1)
        {
            for (var lop = 1; lop <= tblrowCount; lop++)
            {
                billTotalAmt = parseFloat(billTotalAmt) + parseFloat($("#txtValue" + lop).val());
            }
            $("#GrndTotal").val("");
            $("#GrndTotal").val(Math.round(billTotalAmt).toFixed(2));

            /* START,CODE FOR APPLYING GST   */
            billTotalAmt = gstCalculation(billTotalAmt, GSTOfCraft);                      // FOR GST
            /* END,CODE FOR APPLYING GST   */

            $("#BillTotal").val("");
            $("#BillTotal").val(Math.round(billTotalAmt).toFixed(2));

        }
        else if (tblrowCount === 1)
        {
            //billTotalAmt = Math.round(parseInt($("#txtValue1").val()));MODIFIED @ 11-11-2014.
            // billTotalAmt = Math.round($("#txtValue1").val());                                        // FOR GST
            billTotalAmt = $("#txtValue1").val();
            $("#GrndTotal").val("");
            //$("#GrndTotal").val((billTotalAmt).toFixed(2));
            $("#GrndTotal").val(billTotalAmt);                                                          // FOR GST

            /* START,CODE FOR APPLYING GST   */
            billTotalAmt = gstCalculation(billTotalAmt, GSTOfCraft);                               // FOR GST
            /* END,CODE FOR APPLYING GST   */
            $("#BillTotal").val("");
            $("#BillTotal").val(Math.round(billTotalAmt).toFixed(2));
        }
    }
}


function editItem(rcvEditId, rcvQtyTrId)
{
    $(".optnAddRemove").show();
    wo = 1;
    var inTxtMaterial = "", inTxtVendor = "", inTxtQty = "", editdynamicMaterial = "", editdyRate = "", editdynamicVendor = "";

    if (rcvQtyTrId !== "")
    {
        editRowid = rcvQtyTrId;
    }
    else
    {
        editRowid = "";
    }


    inTxtQty = $("#Txtquantity").val().trim();
    inTxtVendor = $("#Txtvendor").val().trim();
    inTxtMaterial = $("#Txtmaterial").val().trim();


    editdynamicMaterial = $("#txtMaterial" + rcvQtyTrId).val().toString();
    //alert("Material :  " + editdynamicMaterial);

    editdynamicVendor = $("#txtVendor" + rcvQtyTrId).val().toString();
    //alert("Vendor   :  " + editdynamicVendor);

    dynaTxtQty = $("#txtQuantity" + rcvQtyTrId).val().toString();
    //alert("Quantity :  " + dyQtyVal);

    dynaRate = $("#txtRate" + rcvQtyTrId).val().toString();
    //alert(dynaRate);

    if ((inTxtMaterial !== "") && (inTxtVendor !== "") && (inTxtQty !== ""))
    {
        $("#Txtmaterial").val("");
        $("#Txtvendor").val("");
        $("#Txtquantity").val("");
        if ((editdynamicMaterial !== "") && (editdynamicVendor !== "") && (dynaTxtQty !== "") && (dynaRate !== ""))
        {
            $('#Txtmaterial').attr("disabled", true);
            $('#Txtvendor').attr("disabled", true);

            $('#Txtmaterial').attr("readonly", true);
            $('#Txtvendor').attr("readonly", true);

            $("#Txtmaterial").val(editdynamicMaterial);
            $("#Txtvendor").val(editdynamicVendor);
            $("#Txtquantity").val(dynaTxtQty);
            $("#Txtquantity").select();
        }
    }
    else
    {
        if ((editdynamicMaterial !== "") && (editdynamicVendor !== "") && (dynaTxtQty !== "") && (dynaRate !== ""))
        {
            $('#Txtvendor').attr("disabled", true);
            $('#Txtmaterial').attr("disabled", true);

            $('#Txtvendor').attr("readonly", true);
            $('#Txtmaterial').attr("readonly", true);

            $("#Txtquantity").select();
            $("#Txtmaterial").val(editdynamicMaterial);
            $("#Txtvendor").val(editdynamicVendor);
            $("#Txtquantity").val(dynaTxtQty);
        }
    }
}

function removingSelectedItem(rcvKeyId, rcvTrId)
{
    var newRowCount;
    var reduValu = "";
    var summaryTable_RowCount = $('#summary-table-body tr').length;

    var sameMtrlCount = 1;
    var purProdLopVar = 1;

    if (prodRowCount > 1)
    {
        while (purProdLopVar <= prodRowCount)
        {
            for (var iloop = 1; iloop <= prodRowCount; iloop++)
            {
                for (var jloop = iloop + 1; jloop <= prodRowCount; jloop++)
                {
                    if (($("#txtMaterial" + iloop).val()) === ($("#txtMaterial" + jloop).val()))
                    {
                        sameMtrlCount++;
                        break;
                    }
                }
            }
            purProdLopVar++;
            break;
        }
    }

    if (confirm("Remove This Item ?"))//COMFIRMING WIHT THE USER FOR REMOVAL OF THE ROW
    {
        var trValue = $("#txtValue" + rcvTrId).val().toString();
        var trQty = $("#txtQuantity" + rcvTrId).val().toString();
        var trMrtl = $("#txtMaterial" + rcvTrId).val().toString();
        var packingAmt = $("#PckCharges").val().trim();
        var discountPercentageValue = $("#TxtDiscount").val().trim();
        var selectedCraftGST = $("#hidden-craft-gst-percentage").val();
        var totValue = 0;
        $(rcvKeyId).closest('tr[id]').remove();//REMOVING THE SPECIFIC ROW FROM THE PRO-TABLE

        newRowCount = $('#pruchase_tbody tr').length;
        newRowCount = newRowCount - 1;
        reOrderingVales();
        $('#Txtmaterial').focus();

        var prodRowCount = $('#pruchase_tbody tr').length;

        if (prodRowCount > 1)
        {
            for (var lopVar = 1; lopVar <= prodRowCount; lopVar++)
            {
                //totValue = Math.round(parseFloat(totValue) + parseFloat($("#txtValue" + lopVar).val()));
                totValue = (parseFloat(totValue) + parseFloat($("#txtValue" + lopVar).val()));
            }
            $("#GrndTotal").val("");
            $("#GrndTotal").val(Math.round(totValue).toFixed(2));
            $("#BillTotal").val("");
            $("#BillTotal").val(Math.round(totValue).toFixed(2));

        }
        else if (prodRowCount == 1)
        {
            totValue = $("#txtValue1").val();
            totValue = Math.round(totValue).toFixed(2);
        }
        else if (prodRowCount == 0)
        {
            totValue = 0.00;
            functionClearAll();
            $("#cgst-value").val("0.00");                                                 //  FOR - GST
            $("#sgst-value").val("0.00");                                                  //  FOR - GST

            $("#btnSavePrint").attr("disabled", true);
            $("#navigatedCounterName").removeAttr('disabled');//15-11-2014
        }
        if (prodRowCount !== 0)
        {
            if (packingAmt !== "")
            {
                $("#GrndTotal").val("");
                $("#GrndTotal").val(Math.round(totValue).toFixed(2));
                /* START,CODE FOR APPLYING GST   */
                reduValu = gstCalculation(totValue, selectedCraftGST);                               // FOR GST
                /* END,CODE FOR APPLYING GST   */
                reduValu = Math.round(parseFloat(reduValu) + parseFloat(packingAmt)).toFixed(2);
                $("#BillTotal").val("");
                $("#BillTotal").val(Math.round(reduValu).toFixed(2));
            }
            else if (packingAmt === "")
            {
                reduValu = Math.round(parseFloat(totValue).toFixed(2));
                $("#GrndTotal").val("");
                $("#GrndTotal").val(reduValu.toFixed(2));
                /* START,CODE FOR APPLYING GST   */
                reduValu = gstCalculation(reduValu, selectedCraftGST);  // FOR GST
                /* END,CODE FOR APPLYING GST   */
                $("#BillTotal").val("");
                $("#BillTotal").val(Math.round(reduValu).toFixed(2));
            }
        }


        var summaryTotal = 0;
        var sumryLopVar = 1;
        var sumryLopVar2 = 1;
        var updateSummaryQtyValue = "";

        if (sameMtrlCount > 0)
        {
            if (sameMtrlCount > 1)
            {
                if (summaryTable_RowCount >= 1)
                {
                    while (sumryLopVar <= summaryTable_RowCount)
                    {
                        if ($("#summary-matril-" + sumryLopVar).text() === trMrtl)
                        {
                            updateSummaryQtyValue = parseInt($("#summary-quantity-" + sumryLopVar).text()) - parseInt(trQty);
                            //alert("Update Total Value :   " + updateSummaryQtyValue);
                            if (updateSummaryQtyValue === 0)
                            {
                                $("#summary-quantity-" + sumryLopVar).text("");
                                $("#summary-quantity-" + sumryLopVar).text(updateSummaryQtyValue);
                                $("#summary-matril-" + sumryLopVar).remove();
                                $("#summary-stock-" + sumryLopVar).remove();
                                $("#summary-tr-id" + sumryLopVar).remove();
                            }
                            else
                            {
                                $("#summary-quantity-" + sumryLopVar).text("");
                                $("#summary-quantity-" + sumryLopVar).text(updateSummaryQtyValue);
                                reOrder_SummaryTable();
                            }
                        }
                        sumryLopVar++;
                    }
                }
            }
            else if (sameMtrlCount === 1)
            {
                if (summaryTable_RowCount > 1)
                {
                    while (sumryLopVar2 <= summaryTable_RowCount)
                    {
                        if ($("#summary-matril-" + sumryLopVar2).text() === trMrtl)
                        {
                            updateSummaryQtyValue = parseInt($("#summary-quantity-" + sumryLopVar2).text()) - parseInt(trQty);
                            if (updateSummaryQtyValue === 0)
                            {
                                $("#summary-quantity-" + sumryLopVar2).text("");
                                $("#summary-quantity-" + sumryLopVar2).text(updateSummaryQtyValue);
                                $("#summary-matril-" + sumryLopVar2).remove();
                                $("#summary-stock-" + sumryLopVar2).remove();
                                $("#summary-tr-id" + sumryLopVar2).remove();
                            }
                            else
                            {
                                $("#summary-quantity-" + sumryLopVar2).text("");
                                $("#summary-quantity-" + sumryLopVar2).text(updateSummaryQtyValue);
                            }
                            reOrder_SummaryTable();
                        }
                        sumryLopVar2++;
                    }
                }
                else if (summaryTable_RowCount === 1)
                {
                    if (materialCraftGroup !== "SB")
                    {
                        $("#summary-tr-id1").remove();
                        reOrder_SummaryTable();
                    }
                    else
                    {
                        if (prodRowCount > 1)
                        {
                            for (var Lop = 1; Lop <= prodRowCount; Lop++)
                            {
                                summaryTotal += parseFloat($("#txtQuantity" + Lop).val());
                            }
                            summaryTotal = truncateDecimals(summaryTotal, 3);
                            $("#summary-quantity-1").text("");
                            $("#summary-quantity-1").text(summaryTotal);
                        }
                        else if (prodRowCount === 1)
                        {
                            $("#summary-quantity-1").text("");
                            $("#summary-quantity-1").text($("#txtQuantity1").val());
                        }
                    }
                }
            }
        }
    }
}

truncateDecimals = function(number, digits)
{
    var multiplier = Math.pow(10, digits), adjustedNum = number * multiplier, truncatedNum = Math[adjustedNum < 0 ? 'ceil' : 'floor'](adjustedNum);
    return truncatedNum / multiplier;
};


function reOrder_SummaryTable()
{

    var sumryIdValue = $('.summary-id-class');
    var sumryCountId = 1;
    $.each(sumryIdValue, function()
    {
        $(this).attr('id', 'summary-id-' + sumryCountId);
        sumryCountId++;
    });

    var sumryTrValue = $('.summary-tr-class');
    var sumryCountTr = 1;
    $.each(sumryTrValue, function()
    {
        $(this).attr('id', 'summary-tr-id' + sumryCountTr);
        sumryCountTr++;
    });

    var sumryMtrlTrValue = $('.summary-matril-class');
    var sumryMtrlCountTr = 1;
    $.each(sumryMtrlTrValue, function()
    {
        $(this).attr('id', 'summary-matril-' + sumryMtrlCountTr);
        sumryMtrlCountTr++;
    });

    var summaryDescriptionClass = $('.summary-description-class');
    var sumryDescriptionCountTr = 1;
    $.each(summaryDescriptionClass, function()
    {
        $(this).attr('id', 'summary-description-' + sumryDescriptionCountTr);
        sumryDescriptionCountTr++;
    });

    var summaryQuantityClass = $('.summary-quantity-class');
    var sumryQuantityCountTr = 1;
    $.each(summaryQuantityClass, function()
    {
        $(this).attr('id', 'summary-quantity-' + sumryQuantityCountTr);
        sumryQuantityCountTr++;
    });


    var summarRateClass = $('.summary-rate-class');
    var sumryRateCountTr = 1;
    $.each(summarRateClass, function()
    {
        $(this).attr('id', 'summary-rate-' + sumryRateCountTr);
        sumryRateCountTr++;
    });


    var summarValueClass = $('.summary-value-class');
    var sumryValueCountTr = 1;
    $.each(summarValueClass, function()
    {
        $(this).attr('id', 'summary-value-' + sumryValueCountTr);
        sumryValueCountTr++;
    });

    var summarStockClass = $('.summary-stock-class');
    var sumryStockCountTr = 1;
    $.each(summarStockClass, function()
    {
        $(this).attr('id', 'summary-stock-' + sumryStockCountTr);
        sumryStockCountTr++;
    });
}

function reOrderingVales()
{
    //RE-ORDERING ID PARAMETER VALUES FOR TR
    var trValue = $('.fieldwrapper');
    var countTr = 1;
    $.each(trValue, function()
    {
        $(this).attr('id', 'tr' + countTr);
        countTr++;
    });

    //RE-ORDERING EDIT ID,NAME & TEXT PARAMETER VALUES FOR SNO
    var ValueSno = $('.fieldSNO');
    var countSno = 1;
    $.each(ValueSno, function()
    {
        $(this).attr('id', 'lblSno' + countSno);
        $(this).attr('name', 'lblSno' + countSno);
        $(this).attr('for', 'lblSno' + countSno);
        $(this).attr('id', 'lblSno' + countSno).text(countSno);
        countSno++;
    });

    //RE-ORDERING MATERIAL ID & NAME
    var Materialfields = $('.fieldTxtMaterial');
    var countMaterialValue = 1;
    $.each(Materialfields, function() {
        $(this).attr('id', 'txtMaterial' + countMaterialValue);
        $(this).attr('name', 'txtMaterial' + countMaterialValue);
        countMaterialValue++;
    });

    //RE-ORDERING EDIT METHOD ID,VALUES FOR DESCRIPTION
    var ValueDescription = $('.fieldTxtDescription');
    var countDesc = 1;
    $.each(ValueDescription, function()
    {
        $(this).attr('id', 'txtDescription' + countDesc);
        $(this).attr('name', 'txtDescription' + countDesc);
        countDesc++;
    });

    //RE-ORDERING QUANTITY ID & NAME
    var Quantityfields = $('.fieldTxtQuantity');
    var countQuantityValue = 1;
    $.each(Quantityfields, function()
    {
        $(this).attr('id', 'txtQuantity' + countQuantityValue);
        $(this).attr('name', 'txtQuantity' + countQuantityValue);
        //$(this).attr('onkeyup', "reset_Quantity(this," + countQuantityValue + ")");/* BLOCKED BY GPR 23.04.2015*/
        $(this).attr('onkeyup', "refreshRowQty(this," + countQuantityValue + ")");
        countQuantityValue++;
    });

    //RE-ORDERING MATERIAL-RATE ID & NAME
    var Ratefields = $('.fieldTxtRate');
    var countRateValue = 1;
    $.each(Ratefields, function() {
        $(this).attr('id', 'txtRate' + countRateValue);
        $(this).attr('name', 'txtRate' + countRateValue);
        countRateValue++;
    });

    //RE-ORDERING MATERIAL-TOTALVALUE ID & NAME
    var Valuefields = $('.fieldTxtValue');
    var countValue = 1;
    $.each(Valuefields, function() {
        $(this).attr('id', 'txtValue' + countValue);
        $(this).attr('name', 'txtValue' + countValue);
        countValue++;
    });

    //RE-ORDERING VATVALUE ID & NAME
    var VatValueFields = $('.fieldTxtVatValue');
    var countVatValue = 1;
    $.each(VatValueFields, function() {
        $(this).attr('id', 'txtVatValue' + countVatValue);
        $(this).attr('name', 'txtVatValue' + countVatValue);
        countVatValue++;
    });

    //RE-ORDERING DISCOUNT-VALUE ID & NAME
    var DiscountValueFields = $('.fieldTxtDiscountValue');
    var DiscountValue = 1;
    $.each(DiscountValueFields, function() {
        $(this).attr('id', 'txtDiscountValue' + DiscountValue);
        $(this).attr('name', 'txtDiscountValue' + DiscountValue);
        DiscountValue++;
    });

    //RE-ORDERING EDIT METHOD ID,VALUES FOR STOCK
    var ValueStock = $('.fieldTxtStock');
    var countStock = 1;
    $.each(ValueStock, function()
    {
        $(this).attr('id', 'txtVat' + countStock);
        $(this).attr('name', 'txtVat' + countStock);
        countStock++;
    });

    //RE-ORDERING VENDOR ID & NAME
    var Vendorfields = $('.fieldTxtVendor');
    var countVendorValue = 1;
    $.each(Vendorfields, function() {
        $(this).attr('id', 'txtVendor' + countVendorValue);
        $(this).attr('name', 'txtVendor' + countVendorValue);
        countVendorValue++;
    });


    //RE-ORDERING VENDOR-Stock ID & NAME
    var VendorStockfield = $('.fieldTxtVendorStock');
    var countVendorStockValue = 1;
    $.each(VendorStockfield, function() {
        $(this).attr('id', 'txtVendorStock' + countVendorStockValue);
        $(this).attr('name', 'txtVendorStock' + countVendorStockValue);
        countVendorStockValue++;
    });


    //RE-ORDERING EDIT METHOD ID,NAME & ONCLICK PARAMETER VALUES FOR EDIT
    var ValuePen = $('.fieldPencil');
    var countPenValue = 1;
    $.each(ValuePen, function() {
        $(this).attr('id', 'trEdit' + countPenValue);
        $(this).attr('name', 'trEdit' + countPenValue);
        $(this).attr('onclick', "editItem(this," + countPenValue + ")");
        countPenValue++;
    });

    //RE-ORDERING EDIT METHOD ID,NAME & ONCLICK PARAMETER VALUES FOR REMOVE
    var valueMinus = $('.fieldMinus');
    var countMinus = 1;//
    $.each(valueMinus, function()
    {
        $(this).attr('id', 'trRemove' + countMinus);
        $(this).attr('onclick', "removingSelectedItem(this," + countMinus + ")");
        countMinus++;
    });
}

function reset_GrandTotal_On_Dyanamic_Quantity_Updating() {
    var grandTotal_OnDynamicaQtyUpdaing = "", discountAmount_OnDynamicQtyUpdaing = "", packingCharge_OnDynamicaQtyUpdaing = "";
    grandTotal_OnDynamicaQtyUpdaing = $("#GrndTotal").val().trim();
    discountAmount_OnDynamicQtyUpdaing = $("#TxtDiscount").val().trim();
    packingCharge_OnDynamicaQtyUpdaing = $("#PckCharges").val().trim();
    if (grandTotal_OnDynamicaQtyUpdaing !== "")
    {
        if (discountAmount_OnDynamicQtyUpdaing !== "") {
            if (discountAmount_OnDynamicQtyUpdaing > 0)
            {
                cal_val = "";
                cal_val = grandTotal_OnDynamicaQtyUpdaing * (discountAmount_OnDynamicQtyUpdaing / 100);
                if (packingCharge_OnDynamicaQtyUpdaing !== "")
                {
                    calBill_Amt = "";
                    calBill_Amt = parseFloat(grandTotal_OnDynamicaQtyUpdaing - parseFloat(cal_val) + parseFloat(packingCharge_OnDynamicaQtyUpdaing));
                    $("#BillTotal").val("");
                    $("#BillTotal").val(calBill_Amt);
                }
                else
                {
                    calBill_Amt = "";
                    calBill_Amt = parseFloat(grandTotal_OnDynamicaQtyUpdaing) - parseFloat(cal_val);
                    $("#BillTotal").val("");
                    $("#BillTotal").val(calBill_Amt);
                }
            }
        }
        else if (packingCharge_OnDynamicaQtyUpdaing !== "")
        {
            $("#BillTotal").val("");
            $("#BillTotal").val(Math.round(parseFloat(grandTotal_OnDynamicaQtyUpdaing) + parseFloat(packingCharge_OnDynamicaQtyUpdaing)));
        }
        else if (packingCharge_OnDynamicaQtyUpdaing == "" && discountAmount_OnDynamicQtyUpdaing == "")
        {
            $("#BillTotal").val("");
            $("#BillTotal").val(Math.round(grandTotal_OnDynamicaQtyUpdaing));
        }
    }
}

function resetBillAmtWithPackageValue()
{
    var pck_GarndTotalAmount = "", pck_PackingCharge = "";
    pck_GarndTotalAmount = $("#GrndTotal").val().trim();
    var totalBillAmount = $("#BillTotal").val().trim();

    pck_PackingCharge = $("#PckCharges").val().trim();
    var prodTableRowCount = $('#pruchase_tbody tr').length;
    var gstCraftValue = $("#hidden-craft-gst-percentage").val().trim();               // FOR GST
    var totWithNoPackingChrg = 0;


    if (pck_GarndTotalAmount != "")
    {
        if (pck_PackingCharge != "")
        {
            if (pck_PackingCharge >= 0)
            {
                /* START,CODE FOR APPLYING GST   */
                pck_GarndTotalAmount = gstCalculation(pck_GarndTotalAmount, gstCraftValue);  // FOR GST
                console.log("Grand total    : " + pck_GarndTotalAmount);
                console.log("Packing charge : " + pck_PackingCharge);
                /* END,CODE FOR APPLYING GST   */
                var totWithPackingCharge = Math.round(parseFloat(pck_GarndTotalAmount) + parseFloat(pck_PackingCharge)).toFixed(2);
                console.log("Bill Amount        : " + totWithPackingCharge);
                $("#BillTotal").val(totWithPackingCharge);
            }
            else
            {
                alert("Oops !! Packing cannot be lessthan zero");
                $("#PckCharges").val("");
                $("#PckCharges").focus();
                document.getElementById('PckCharges').style.borderColor = "red";
                $("#BillTotal").val(Math.round(pck_GarndTotalAmount).toFixed(2));
            }
        }
        else
        {
            if (prodTableRowCount > 1)
            {
                for (var pckLop = 1; pckLop <= prodTableRowCount; pckLop++)
                {
                    totWithNoPackingChrg = (parseFloat(totWithNoPackingChrg) + parseFloat($("#txtValue" + pckLop).val()));
                }

                $("#GrndTotal").val("");
                //$("#GrndTotal").val(Math.round(totWithNoPackingChrg).toFixed(2));            // FOR GST
                $("#GrndTotal").val(totWithNoPackingChrg.toFixed(2));

                /* START,CODE FOR APPLYING GST   */
                totWithNoPackingChrg = gstCalculation(totWithNoPackingChrg, gstCraftValue);  // FOR GST
                /* END,CODE FOR APPLYING GST   */

                $("#BillTotal").val("");
                $("#BillTotal").val(Math.round(totWithNoPackingChrg).toFixed(2));
            }
            else
            {
                //var totValue = Math.round($("#txtValue1").val().trim()).toFixed(2);   // FOR GST
                var totValue = $("#txtValue1").val().trim();
                $("#GrndTotal").val("");
                $("#GrndTotal").val(totValue);

                /* START,CODE FOR APPLYING GST   */
                totValue = gstCalculation(totValue, gstCraftValue);                  // FOR GST
                /* END,CODE FOR APPLYING GST   */

                $("#BillTotal").val("");
                $("#BillTotal").val(Math.round(totValue).toFixed(2));
            }
        }
    }
}

function resetBillAmtWithVatValue(valueFromTableforVat)
{
    var vatGrndtotAmt = $("#GrndTotal").val().trim();
    var vatDisCuntAmt = $("#TxtDiscount").val().trim();
    var vatPckCharges = $("#PckCharges").val().trim();
    var vatVatPercentage = $("#TxtVat").val().trim();

    if (vatVatPercentage !== "")
    {
        calcu_vatVatValue = "";
        //pck_calval = calcDiscout_grndTotAmt * (calcDiscout_disCuntAmt / 100);
        calcu_vatVatValue = valueFromTableforVat * (vatVatPercentage / 100);
    }
    else
    {
        calcu_vatVatValue = 0;
    }
    return calcu_vatVatValue;
}

function calculateDiscountPriceValue(valueFromProdTable)
{
    var prdTableRowCount = $('#pruchase_tbody tr').length;
    var totPriceVal;
    if (prdTableRowCount > 1)
    {
        totPriceVal;
    }
    return pck_calval;
}

function checkIsBackDated() {
    if ($("#enableDisableBackDated").is(':checked')) {
        if ($("#salesBackDate").val().trim() !== "" && $("#enableManualBil").val().trim() !== "")
        {
            insBookedProducts($("#salesBackDate").val().trim() + " " + '01:01:01', $("#enableManualBil").val().trim());
        } else if ($("#salesBackDate").val().trim() === "" || $("#enableManualBil").val().trim() === "") {
            alert("Oops !!! BackDated Date (or) Manual bill no missing..");
            //insBookedProducts("0000-01-01");
            $("#salesBackDate").focus();
        }
    } else {
        insBookedProducts("0000-01-01", "0000");
        $('#salesBackDate').val('');
        $('#enableManualBil').val('');
    }
}

function insBookedProducts(backDatedDate, enableManualBil)
{
    if (submitted != 1)
    {
        submitted = 1;
        var aryMtrl = [];
        var aryDesc = [];
        var aryQuantity = [];
        var aryRate = [];
        var aryValue = [];
        var aryDiscountPer = [];
        var aryDiscountValue = [];
        var aryVatPer = [];
        var aryVatValue = [];
        //var aryStock = [];
        var aryVendor = [];

        var pstMtrl = "";
        var pstDesc = "";
        var pstQuantity = "";
        var pstRate = "";
        var pstValue = "";
        var pstVatPer = "";
        //var pstStock = "";
        var pstVendor = "";
        var packagedDataInfo = "";

        var discountApprovalText = "";
        var netBillAmt = "";
        //var discuntValueForPer = "";
        //var discountPercentageValue = "";
        var salesOrderNo = "";
        var empId = "";
        var paymentType = "";
        var showRoomId = "";
        var disCountValue = "";
        var vatValue = "";
        var pckChargeValue = "";
        var billAmountValue = "";

        var tblRowCount = $('#pruchase_tbody tr').length, lopValue = 1, BolQtyValue = 0;//

        //showRoomId = "2000";
        //paymentType = "Cash";
        empId = $("#txtlog_empid").text();
        var gstPercentage = $("#hidden-craft-gst-percentage").val().trim();
        var sgst = $("#sgst-value").val().trim();

        netBillAmt = $("#GrndTotal").val().trim();
        salesOrderNo = $("#Txtorderno").text();
        billAmountValue = $("#BillTotal").val().trim();

        if ($("#TxtDiscount").val() !== "")//CHECKING THE DISCOUNT VALUE IS ENTERED OR NOT
        {
            disCountValue = $("#TxtDiscount").val().trim();
            for (var isDisCuntloop = 1; isDisCuntloop <= tblRowCount; isDisCuntloop++)
            {
                aryDiscountPer.push($("#TxtDiscount").val());
            }
            if ($("#txtApproval").val() !== "")
            {
                discountApprovalText = $("#txtApproval").val().trim();
            }
            else if ($("#txtApproval").val() === "")
            {
                $("#txtApproval").val(".");
                $("#hdnDisCountValue").val("");
                $("#hdnDisCountValue").val(0);
                discountApprovalText = $("#txtApproval").val().trim();
            }
        }
        else
        {
            disCountValue = 0;
            for (var isNoDisCuntloop = 1; isNoDisCuntloop <= tblRowCount; isNoDisCuntloop++)
            {
                aryDiscountPer.push(disCountValue);
            }
            $("#txtApproval").val(".");
            $("#hdnDisCountValue").val("");
            $("#hdnDisCountValue").val(0);//ASSINGING DEFAULT VALUE FOR DISCOUNT,IF DISCOUNT IS NOT ENTERED
        }

        if ($("#PckCharges").val() !== "")//CHECKING THE PACKINGH-CHARGES IS ENTERED OR NOT
        {
            pckChargeValue = $("#PckCharges").val().trim();
        }
        else
        {
            pckChargeValue = 0;//ASSINGING DEFAULT VALUE FOR PACKINGCHARGE,IF PACKINGCHARGE IS NOT ENTERED
        }

        for (var i = 1; i <= tblRowCount; i++)
        {
            pstMtrl = $("#txtMaterial" + i).val().toString();
            aryMtrl.push(pstMtrl);

            pstDesc = $("#txtDescription" + i).val().toString();
            aryDesc.push(pstDesc);

            pstVendor = $("#txtVendor" + i).val().toString();
            aryVendor.push(pstVendor);

            pstQuantity = $("#txtQuantity" + i).val().toString();
            aryQuantity.push(pstQuantity);

            pstRate = $("#txtRate" + i).val().toString();
            aryRate.push(pstRate);

            pstVatPer = $("#txtVat" + i).val().toString();
            aryVatPer.push(pstVatPer);

            vatValue = $("#txtVatValue" + i).val().toString();
            aryVatValue.push(vatValue);

            aryDiscountPer.push(disCountValue);

            disCountValue = $("#txtDiscountValue" + i).val().toString();
            aryDiscountValue.push(disCountValue);

            pstValue = $("#txtValue" + i).val().toString();
            aryValue.push(pstValue);
        }
        discountApprovalText = $("#txtApproval").val().trim();
        for (var j = 0; j < tblRowCount; j++)
        {
            if (packagedDataInfo === "") {
                //packagedDataInfo = salesOrderNo + "-" + empId + "-" + paymentType + "-" + showRoomId + "-" + discountApprovalText + "-" + pckChargeValue + "-" + netBillAmt + "-" + billAmountValue + "-" + aryMtrl[j] + "-" + aryDesc[j] + "-" + aryQuantity[j] + "-" + aryRate[j] + "-" + aryVatPer[j] + "-" + aryVatValue[j] + "-" + aryDiscountPer[j] + "-" + aryDiscountValue[j] + "-" + aryValue[j] + "-" + aryVendor[j];
                packagedDataInfo = empId + "-" + discountApprovalText + "-" + pckChargeValue + "-" + netBillAmt + "-" + billAmountValue + "-" + gstPercentage + "-" + sgst + "-" + aryMtrl[j] + "-" + aryDesc[j] + "-" + aryQuantity[j] + "-" + aryRate[j] + "-" + aryVatPer[j] + "-" + aryVatValue[j] + "-" + aryDiscountPer[j] + "-" + aryDiscountValue[j] + "-" + aryValue[j] + "-" + aryVendor[j];
                //alert("SINGLE   ----->  :  " + packagedDataInfo);
            }
            else {
                packagedDataInfo = packagedDataInfo + "," + aryMtrl[j] + "-" + aryDesc[j] + "-" + aryQuantity[j] + "-" + aryRate[j] + "-" + aryVatPer[j] + "-" + aryVatValue[j] + "-" + aryDiscountPer[j] + "-" + aryDiscountValue[j] + "-" + aryValue[j] + "-" + aryVendor[j];
                //alert("MULTIPLE ----->  :  " + packagedDataInfo);
            }
        }

        if (packagedDataInfo !== "")
        {
            if (tblRowCount > 1)
            {
                while (lopValue <= tblRowCount)
                {
                    if (($("#txtQuantity" + lopValue).val()) !== "")
                    {
                        BolQtyValue = 0;
                    }
                    else
                    {
                        BolQtyValue = 1;
                        alert("Oops !!!,Quantity Value Is Missing");
                        $("#txtQuantity" + lopValue).focus();
                        document.getElementById('txtQuantity' + lopValue).style.borderColor = "red";
                        break;
                    }
                    lopValue++;
                }
                if (BolQtyValue !== 1)
                {
                    pushPurchaseDataToDb(packagedDataInfo, backDatedDate, enableManualBil);
                }
                BolQtyValue = 0;
            }
            else if (tblRowCount === 1)
            {
                if (($("#txtQuantity1").val()) !== "")
                {
                    pushPurchaseDataToDb(packagedDataInfo, backDatedDate, enableManualBil);
                }
                else
                {
                    alert("Quantity Value Is Missing");
                    $("#txtQuantity1").focus();
                    document.getElementById('txtQuantity1').style.borderColor = "red";
                }
            }
        }
        flagValue = 0;
        index_value = 1;
        prodPurchase_Count = 0;
        $("#hdnDisCountValue").val("");
    }
    else
    {
        alert("Already Submitted");
    }
}

function pushPurchaseDataToDb(pVal, pBackDatedDate, pEnableManualBil)
{
    $("#TxtPurchaseProd").val(pVal);
    //var bol = insCustomerInfo();
    //alert("Customer Info Ins Status :  " + bol);
    //if (insCustomerInfo())
    //{
    $.getJSON('SessionName.action', function(data) {
        if (data.logusrtype !== "all") {
            //counterName = $("#navigatedCounterName").find("option:selected").text().trim();
            counterName = $("#navigatedCounterName").val();
        } else if (data.logusrtype === "all") {
            //counterName = $("#navigatedCounterNameSA").find("option:selected").text().trim();
            counterName = $("#navigatedCounterNameSA").val();
        }
        if (counterName !== "")
        {
            $.getJSON('PostingPurchaseProd.action?totPurchaseProd=' + $("#TxtPurchaseProd").val(), "&backDatedDate=" + pBackDatedDate.trim() + '&enableManualBil=' + pEnableManualBil.trim(), function(ele)
            {
                if (ele.pstatus == 1)
                {
                    alert("Invoice " + ele.salesOrderNo + " has been generated successfully");
                    $("#btnSavePrint").attr("disabled", true);
//                //if ($("#enableDisableBackDated").is(':checked')) {
//                    $('#salesBackDate').val('');
//                    $("#cashBackDate").attr("disabled", true);
//                    $("#enableDisableBackDated").attr('checked', false);
//                //}
                    insCustomerInfo(ele.salesOrderNo);
                    if (ele.tempVariable == 1)
                    {
                        xWinOpen("print_jsp/purchase-order-print.jsp?&salesOrderNo=" + ele.salesOrderNo + "&counterType=" + counterName);
                    }
                    functionClearAll();
                    Display_LoginName();
                    $("#Txtmaterial").focus();
                }
                else if (ele.pstatus == -2)
                {
                    alert("Process failure,Try again..");
                }
                else if (ele.pstatus == -1)
                {
                    alert("Process failure,Try again..");
                }
            });
        }
        else
        {
            alert("Process failure,Try again...");
        }
        //}
    });
}

var xChildWindow = null;
function xWinOpen(sUrl)
{
    //var features = "left=100,top=5,width=1100,height=1200,location=false,directories=false,toolbar=false,menubars=false,scrollbars=false,resize=false";
    var features = "left=0,top=7,width=1000,height=610,location=false,directories=false,toolbar=false,menubars=false,scrollbars=false,resize=false";
    if (xChildWindow && !xChildWindow.closed) {
        xChildWindow.location.href = sUrl;
    }
    else {
        xChildWindow = window.open(sUrl, "CustomerInfo.jsp", features);
    }
    xChildWindow.focus();
    return false;
}

function Fun_Reprint()
{
    $("#billNumber").focus();
    var rePrintBillNo = $("#billNumber").val().trim();
    if (rePrintBillNo !== "")
    {
        $.getJSON('rePrint.action?reprintBillno=' + rePrintBillNo, function(data)
        {
            if (data.cancelledStatusFlag !== true)
            {
                if (data.rePrintReportStatus == true)
                {
                    xWinOpen("print_jsp/purchase-order-print.jsp?salesOrderNo=" + rePrintBillNo + "&counterType=" + counterName);
                    document.getElementById('lightReprint').style.display = 'none';
                    document.getElementById('fade').style.display = 'none';
                    $("#billNumber").val("");
                }
                else if (data.rePrintReportStatus == false)
                {
                    alert("Oops,Sales-OrderNumber DoesNot Exists...");
                    $("#billNumber").val("");
                    $("#billNumber").focus();
                    document.getElementById('billNumber').style.borderColor = "red";
                }
            }
            else if (data.cancelledStatusFlag == true)
            {
                alert("Oops! Sorry SalesOrderNumber " + rePrintBillNo + " is Cancelled");
                $("#billNumber").val("");
                document.getElementById('lightReprint').style.display = 'none';
                document.getElementById('fade').style.display = 'none';
            }
        });
    }
    else
    {
        alert("Enter Bill Number..");
        $("#billNumber").focus();
    }
}


function resetcustomerInfoSaveText()
{
    var customerName;
    customerName = $("#custFname").val().trim();
    if (!(customerName == ""))
    {
        if (!(customerName <= 0 || customerName >= 0))
        {
            customerInfoSaveEditText = 1;

            document.getElementById('customerInfoLight').style.display = 'none';
            document.getElementById('fadeCustomerInfo').style.display = 'none';
        }
    }
}

function showCustomerInfoDialog()
{
    if (customerInfoSaveEditText == 0)
    {
        document.getElementById('customerInfoLight').style.display = 'block';
        document.getElementById('fadeCustomerInfo').style.display = 'block';
        $("#custInfoSave").val("");
        $("#custInfoSave").val("Save");
        $("#custFname").focus();
    }
    else if (customerInfoSaveEditText == 1)
    {
        document.getElementById('customerInfoLight').style.display = 'block';
        document.getElementById('fadeCustomerInfo').style.display = 'block';
        $("#custInfoSave").val("");
        $("#custInfoSave").val("Edit");
        $("#custFname").focus();
    }
}

function insCustomerInfo(createdSalesOrderNumber)
{
    var rtn;
    var custName;
    custName = $("#custFname").val().trim();

    var custContactNumber;
    custContactNumber = $("#custCNo").val().trim();

    var salesOrderNo = createdSalesOrderNumber;
    //salesOrderNo = $("#Txtorderno").text().trim();

    if (!(custName == ""))
    {
        if (!(custName >= 0 || custName <= 0))
        {
            if (!(salesOrderNo == ""))
            {
                $.getJSON('insCustomerData.action?orderNumber=' + salesOrderNo, $("#customerInfoForm").serialize(), function(data)
                {
                    if (data.customerInfoInsStatus == 1)
                    {
                        $("#custFname").val("");
                        $("#custLname").val("");
                        $("#custCNo").val("");
                        return true;
                    }
                });
            }
            else
            {
                alert("Oops !!!,Process failure");
                document.getElementById('customerInfoLight').style.display = 'block';
                document.getElementById('fadeCustomerInfo').style.display = 'block';
                return false;
                //customerInfoInsValue=0;
            }
        }
        else
        {
            alert("Oops,In-valid entry for Customername..try again");
            $("#custName").focus();
            document.getElementById('customerInfoLight').style.display = 'block';
            document.getElementById('fadeCustomerInfo').style.display = 'block';
            return false;
            //customerInfoInsValue=0;
        }
    }
    else
    {
        return true;
        //customerInfoInsValue=1;
    }
    return true;
//alert("Out Value  :   "+customerInfoInsValue);
}

function reSetIndivEditOper()
{
    if (confirm("Cancel This Process?"))//COMFIRMING WITH THE USER FOR CANCELLING UPDATE INDIVDUAL QTY VALUE IN THE TABLE
    {
        $('#Txtvendor').val("");
        $('#Txtmaterial').val("");
        $('#Txtquantity').val("");

        $('#Txtvendor').attr("disabled", false);
        $('#Txtmaterial').attr("disabled", false);
        $('#Txtquantity').attr("disabled", false);

        $('#Txtvendor').attr("readonly", false);
        $('#Txtmaterial').attr("readonly", false);
        $('#Txtquantity').attr("readonly", false);

        $("#mtrlAdd").prop("checked", false);
        $("#mtrlRmv").prop("checked", false);
        $(".optnAddRemove").hide();
    }
}

function functionClearAll()
{
    var currentTableRowCount = $('#pruchase_tbody tr').length;
    currentTableRowCount = currentTableRowCount - 1;
    for (var i = 1; i <= currentTableRowCount; i++)//currentTableRowCount     for (var i = 1; i <= currentTableRowCount; i++)
    {
        $("#txtMaterial" + i).val("");
        $("#txtDescription" + i).val("");
        $("#txtQuantity" + i).val("");
        $("#txtRate" + i).val("");
        $("#txtValue" + i).val("");
        $("#txtVat" + i).val("");
        $("#txtVatValue" + i).val("");
        $("#txtDiscountValue" + i).val("");
        $("vtxtVendor" + i).val("");
    }
    $("#summary-table-body").html("");
    $('#purchase_prodTable tr:not(:first)').remove();
    //$('#pruchase_tbody tr:not(:first)').remove();
    $("#TxtPurchaseProd").val("");

    $("#TxtDiscount").val("");
    $(".disApproval").hide();
    $("#txtApproval").val("");
    $("#PckCharges").val("");
    $("#GrndTotal").val("0.00");
    $("#BillTotal").val("0.00");
    $("#hdnProdPrice").val("");
    $("#hdnTotalPrice").val("");
    $("#tableCountValue").val("");
    $("#custName").val("");
    $("#custCNo").val("");
    $("#hidden-craft-gst-percentage").val("0.00");
    $("#sgst-value").val("0.00");
    $("#cgst-value").val("0.00");
    $("#hidden-craft-gst-percentage").val("");                                                //  FOR - GST
    customerInfoSaveEditText = 0;
    customerInfoInsValue = 0;
    $("#Txtmaterial").focus();
    $("#Txtmaterial").val("");
    $("#Txtvendor").val("");
    $("#Txtquantity").val("");
    $("#navigatedCounterName").removeAttr('disabled');
    if ($("#enableDisableBackDated").is(':checked')) {
        $('#salesBackDate').val('');
        $("#salesBackDate").attr("disabled", true);
        $('#enableManualBil').val('');
        $("#enableManualBil").attr("disabled", true);
        $("#enableDisableBackDated").attr('checked', false);
    }
}

function clearDiscountTextValue()
{
    $("#TxtDiscount").val("");
}

function loadimage() {
    $("#content_right").empty();
    $('#content_right').html('<div class="loadimgnr"></div><div id="waiter"><center><img src="images/ProgressBar.gif"></center></div>');
    $("#content_right").show();
}

function autheDiscount()
{

    var authenticatedRowVatValue;
    var authenticatedRowPriceValue;
    var authenticatedTotalValue = 0;
    var authenticatedRowDiscountValue;
    var authenticatedRowPriceValueWithVat;
    var authenticatedRowPriceValueWithDiscount;

    var authenticatedRowCount = $('#pruchase_tbody tr').length;
    var authenticatedPackingCharge = $("#PckCharges").val().trim();
    var authenticatedDiscountGrandTotal = $("#GrndTotal").val().trim();
    var discountApprovalText = $("#discountApprovalText").val().trim();
    var authenticatedDiscountPercentage = $("#TxtDiscount").val().trim();
    var totalValueAfterGST = 0;
    var craftwiseGstPercentage = $("#hidden-craft-gst-percentage").val().trim();               // FOR GST

    if (discountApprovalText !== "")
    {
        $("#txtApproval").val("");
        $(".disApproval").show();
        $("#txtApproval").val(discountApprovalText.trim());

        if (authenticatedRowCount > 1)
        {
            authenticatedTotalValue = 0;

            for (var autheProdLopVal = 1; autheProdLopVal <= authenticatedRowCount; autheProdLopVal++)
            {
                authenticatedRowPriceValue = Math.round($("#txtQuantity" + autheProdLopVal).val() * $("#txtRate" + autheProdLopVal).val());//CALCULATING PRICE VALUE (QTY*INDIVIDUAL RATE)

                //authenticatedRowDiscountValue = Math.round(authenticatedRowPriceValue * authenticatedDiscountPercentage / 100);//CALCULATING DISCOUNT VALUE
                authenticatedRowDiscountValue = (authenticatedRowPriceValue * authenticatedDiscountPercentage / 100);//CALCULATING DISCOUNT VALUE

                $("#txtDiscountValue" + autheProdLopVal).val("");
                $("#txtDiscountValue" + autheProdLopVal).val(authenticatedRowDiscountValue.toFixed(2));//ASSIGING CALCULATED DISCOUNT VALUE

                //authenticatedRowPriceValueWithDiscount = Math.round(authenticatedRowPriceValue - authenticatedRowDiscountValue);//TOTAL VALUE AFTER DISCOUNT
                authenticatedRowPriceValueWithDiscount = (authenticatedRowPriceValue - authenticatedRowDiscountValue);//TOTAL VALUE AFTER DISCOUNT
                if (craftwiseGstPercentage === "0.00") {
                    //authenticatedRowVatValue = Math.round(authenticatedRowPriceValueWithDiscount * $("#txtVat" + autheProdLopVal).val() / 100);//CALCULATING VAT VALUE
                    authenticatedRowVatValue = (authenticatedRowPriceValueWithDiscount * $("#txtVat" + autheProdLopVal).val() / 100);//CALCULATING VAT VALUE

                    $("#txtVatValue" + autheProdLopVal).val("");
                    $("#txtVatValue" + autheProdLopVal).val(authenticatedRowVatValue.toFixed(2));//ASSIGNING VAT VALUE

                    //authenticatedRowPriceValueWithVat = Math.round(authenticatedRowPriceValueWithDiscount + authenticatedRowVatValue);//TOTAL WITH DISOUNT + VAT VALUE
                    authenticatedRowPriceValueWithVat = (authenticatedRowPriceValueWithDiscount + authenticatedRowVatValue);//TOTAL WITH DISOUNT + VAT VALUE
                } else if (craftwiseGstPercentage > 0) {
                    $("#txtVatValue" + autheProdLopVal).val("0.00");
                    authenticatedRowPriceValueWithVat = authenticatedRowPriceValueWithDiscount;
                }

                if (authenticatedRowPriceValueWithVat > 0)//VALIDATING
                {
                    $("#txtValue" + autheProdLopVal).val("");
                    $("#txtValue" + autheProdLopVal).val(authenticatedRowPriceValueWithVat.toFixed(2));

                    //authenticatedTotalValue = Math.round(parseFloat(authenticatedTotalValue) + parseFloat($("#txtValue" + autheProdLopVal).val()));
                    authenticatedTotalValue = (parseFloat(authenticatedTotalValue) + parseFloat($("#txtValue" + autheProdLopVal).val())); // FOR GST

                    if (craftwiseGstPercentage === "0.00") {
                        totalValueAfterGST = authenticatedTotalValue;
                    } else if (craftwiseGstPercentage > 0) {
                        totalValueAfterGST = gstCalculation(authenticatedTotalValue, craftwiseGstPercentage);  // FOR GST
                    }

                    if (authenticatedPackingCharge != "")//VALIDATING IS PACKING CHARGE PRESENT
                    {
                        if (!(authenticatedPackingCharge <= 0))//VALIDATING PACKING CHARGE IS NOT LESS THAN OR EQUAL TO ZERO
                        {
                            var totWitPckageValue = Math.round(parseFloat(totalValueAfterGST) + parseFloat(authenticatedPackingCharge));

                            $("#GrndTotal").val("");
                            $("#GrndTotal").val(totWitPckageValue.toFixed(2));

                            $("#BillTotal").val("");
                            $("#BillTotal").val(totWitPckageValue.toFixed(2));

                        }
                    }
                    else
                    {
                        $("#GrndTotal").val("");
                        $("#GrndTotal").val(authenticatedTotalValue.toFixed(2));

                        $("#BillTotal").val("");
                        if (craftwiseGstPercentage === "0.00") {
                            $("#BillTotal").val(authenticatedTotalValue.toFixed(2));
                        } else if (craftwiseGstPercentage > 0) {
                            $("#BillTotal").val(Math.round(totalValueAfterGST).toFixed(2));
                        }
                    }
                }
                else
                {
                    $("#TxtDiscount").val("");
                    //authenticatedRowVatValue = Math.round(authenticatedRowPriceValue * $("#txtVat" + autheProdLopVal).val() / 100);
                    //authenticatedRowPriceValueWithVat = Math.round(authenticatedRowPriceValue + authenticatedRowVatValue);

                    authenticatedRowVatValue = (authenticatedRowPriceValue * $("#txtVat" + autheProdLopVal).val() / 100);
                    authenticatedRowPriceValueWithVat = (authenticatedRowPriceValue + authenticatedRowVatValue);

                    $("#txtValue" + autheProdLopVal).val("");
                    $("#txtValue" + autheProdLopVal).val(authenticatedRowPriceValueWithVat.toFixed(2));

                    //authenticatedTotalValue = Math.round(parseInt(authenticatedTotalValue) + parseInt($("#txtValue" + autheProdLopVal).val()));
                    authenticatedTotalValue = (parseFloat(authenticatedTotalValue) + parseFloat($("#txtValue" + autheProdLopVal).val()));

                    $("#GrndTotal").val("");
                    $("#GrndTotal").val(authenticatedTotalValue.toFixed(2));

                    $("#BillTotal").val("");
                    $("#BillTotal").val(authenticatedTotalValue.toFixed(2));
                }
            }
            document.getElementById('light').style.display = 'none';
            document.getElementById('fade').style.display = 'none';
        }
        else if (authenticatedRowCount == 1)
        {
            authenticatedTotalValue = 0;
            authenticatedRowPriceValue = Math.round($("#txtQuantity1").val() * $("#txtRate1").val());
            //authenticatedRowDiscountValue = Math.round(authenticatedRowPriceValue * authenticatedDiscountPercentage / 100);
            authenticatedRowDiscountValue = (authenticatedRowPriceValue * authenticatedDiscountPercentage / 100);

            $("#txtDiscountValue1").val("");
            $("#txtDiscountValue1").val(authenticatedRowDiscountValue.toFixed(2));

            //authenticatedRowPriceValueWithDiscount = Math.round(authenticatedRowPriceValue - authenticatedRowDiscountValue);
            authenticatedRowPriceValueWithDiscount = (authenticatedRowPriceValue - authenticatedRowDiscountValue);

            //authenticatedRowVatValue = Math.round(authenticatedRowPriceValueWithDiscount * $("#txtVat1").val() / 100);

            /* START,CODE FOR APPLYING GST   */
            $("#txtVatValue1").val("");
            if (craftwiseGstPercentage === "0.00") {
                authenticatedRowVatValue = (authenticatedRowPriceValueWithDiscount * $("#txtVat1").val() / 100);
                $("#txtVatValue1").val(authenticatedRowVatValue.toFixed(2));
                authenticatedRowPriceValueWithVat = (authenticatedRowPriceValueWithDiscount + authenticatedRowVatValue);
            } else if (craftwiseGstPercentage > 0) {
                $("#txtVatValue1").val("0.00");
                authenticatedRowPriceValueWithVat = authenticatedRowPriceValueWithDiscount;
            }
            /* END,CODE FOR APPLYING GST   */

            //authenticatedRowPriceValueWithVat = Math.round(authenticatedRowPriceValueWithDiscount + authenticatedRowVatValue);


            if (authenticatedRowPriceValueWithVat > 0)
            {
                $("#txtValue1").val("");
                $("#txtValue1").val(authenticatedRowPriceValueWithVat.toFixed(2));

                authenticatedTotalValue = Math.round(parseFloat(authenticatedTotalValue) + parseFloat($("#txtValue1").val()));

                if (authenticatedPackingCharge != "")
                {
                    if (!(authenticatedPackingCharge <= 0))
                    {
                        var totWitPckageValue = 0;
                        $("#GrndTotal").val("");
                        /* START,CODE FOR APPLYING GST   */
                        if (craftwiseGstPercentage === "0.00") {
                            totWitPckageValue = Math.round(parseFloat(authenticatedTotalValue) + parseFloat(authenticatedPackingCharge));
                            $("#GrndTotal").val(totWitPckageValue.toFixed(2));
                        } else if (craftwiseGstPercentage > 0) {
                            $("#GrndTotal").val(authenticatedRowPriceValueWithVat.toFixed(2));
                        }

                        $("#BillTotal").val("");
                        if (craftwiseGstPercentage > 0) {
                            totWitPckageValue = gstCalculation(totWitPckageValue, craftwiseGstPercentage);  // FOR GST
                        }
                        $("#BillTotal").val(Math.round(totWitPckageValue).toFixed(2));
                        /* END,CODE FOR APPLYING GST   */
                    }
                }
                else
                {
                    $("#GrndTotal").val("");
                    $("#GrndTotal").val(authenticatedTotalValue.toFixed(2));

                    $("#BillTotal").val("");
                    if (craftwiseGstPercentage > 0) {
                        authenticatedTotalValue = gstCalculation(authenticatedTotalValue, craftwiseGstPercentage);  // FOR GST
                    }
                    $("#BillTotal").val(Math.round(authenticatedTotalValue).toFixed(2));
                }
            }
            else
            {
                authenticatedTotalValue = 0;
                $("#TxtDiscount").val("");
                //authenticatedRowVatValue = Math.round(authenticatedRowPriceValue * $("#txtVat1").val() / 100);
                authenticatedRowVatValue = (authenticatedRowPriceValue * $("#txtVat1").val() / 100);
                //authenticatedRowPriceValueWithVat = Math.round(authenticatedRowPriceValue + authenticatedRowVatValue);
                authenticatedRowPriceValueWithVat = (authenticatedRowPriceValue + authenticatedRowVatValue);

                $("#txtValue1").val("");
                $("#txtValue1").val(authenticatedRowPriceValueWithVat.toFixed(2));

                authenticatedTotalValue = Math.round(parseFloat(authenticatedTotalValue) + parseFloat($("#txtValue1").val()));

                $("#GrndTotal").val("");
                $("#GrndTotal").val(authenticatedTotalValue.toFixed(2));

                $("#BillTotal").val("");
                $("#BillTotal").val(authenticatedTotalValue.toFixed(2));
            }
            document.getElementById('light').style.display = 'none';
            document.getElementById('fade').style.display = 'none';
        }
    }
    else
    {
        alert("Oops !!!,,Missing discount dpproval text...!!");
        $("#discountApprovalText").focus();
        document.getElementById('discountApprovalText').style.borderColor = "red";
    }
}
function autheDiscountCancel()
{
    //if (confirm("Close This Dialogue?"))
    //{
    $("#disPwd").val("");
    $("#disUname").val("");
    $("#TxtDiscount").val("");
    $(".disApproval").hide();
    $("#txtApproval").val(".");

    var authCancelRowCount = $('#pruchase_tbody tr').length;
    var authCancelPackingCharge = $("#PckCharges").val().trim();
    var authCancelTotalValue = 0;
    var authCancelRowVatValue;
    var authCancelRowPriceValue;
    var authCancelRowDiscountValue;
    var authCancelRowPriceValueWithVat;
    var authCancelRowPriceValueWithDiscount;
    var craftwiseGstPercentage = $("#hidden-craft-gst-percentage").val().trim();               // FOR GST

    $("#TxtDiscount").val("");
    if (authCancelRowCount > 1)
    {
        authCancelTotalValue = 0;
        for (var cancelLopValue = 1; cancelLopValue <= authCancelRowCount; cancelLopValue++)
        {
            authCancelRowPriceValue = Math.round($("#txtQuantity" + cancelLopValue).val() * $("#txtRate" + cancelLopValue).val());

            $("#txtVatValue" + cancelLopValue).val("");

            /* START,CODE FOR APPLYING GST   */
            if (craftwiseGstPercentage === "0.00") {
                //authCancelRowVatValue = Math.round(authCancelRowPriceValue * $("#txtVat" + cancelLopValue).val() / 100);
                authCancelRowVatValue = (authCancelRowPriceValue * $("#txtVat" + cancelLopValue).val() / 100);
                $("#txtVatValue" + cancelLopValue).val(authCancelRowVatValue.toFixed(2));
            } else if (craftwiseGstPercentage > 0) {
                authCancelRowVatValue = 0;
                $("#txtVatValue" + cancelLopValue).val("0.00");
            }
            /* END,CODE FOR APPLYING GST   */

            //authCancelRowPriceValueWithVat = Math.round(authCancelRowPriceValue + authCancelRowVatValue);
            authCancelRowPriceValueWithVat = (authCancelRowPriceValue + authCancelRowVatValue);

            $("#txtDiscountValue" + cancelLopValue).val("");
            $("#txtDiscountValue" + cancelLopValue).val(0);

            if (authCancelRowPriceValueWithVat > 0)
            {
                $("#txtValue" + cancelLopValue).val("");
                $("#txtValue" + cancelLopValue).val(authCancelRowPriceValueWithVat.toFixed(2));
                //authCancelTotalValue = Math.round(parseInt(authCancelTotalValue) + parseInt($("#txtValue" + cancelLopValue).val()));
                authCancelTotalValue = Math.round(parseFloat(authCancelTotalValue) + parseFloat($("#txtValue" + cancelLopValue).val()));

                if (authCancelTotalValue != "")
                {
                    if (!(authCancelTotalValue <= 0))
                    {
                        if (authCancelPackingCharge != "")
                        {
                            if (!(authCancelPackingCharge <= 0))
                            {
                                var authCancelTotalWithPckageValue = 0;
                                $("#GrndTotal").val("");
                                //$("#GrndTotal").val(authCancelTotalWithPckageValue.toFixed(2));
                                $("#GrndTotal").val(Math.round(authCancelTotalWithPckageValue).toFixed(2));

                                /* START,CODE FOR APPLYING GST   */
                                if (craftwiseGstPercentage === "0.00") {
                                    //var authCancelTotalWithPckageValue = Math.round(parseInt(authCancelTotalValue) + parseInt(authCancelPackingCharge));
                                    authCancelTotalWithPckageValue = Math.round(parseFloat(authCancelTotalValue) + parseFloat(authCancelPackingCharge));
                                } else if (craftwiseGstPercentage > 0) {
                                    authCancelTotalWithPckageValue = gstCalculation(authCancelTotalValue, craftwiseGstPercentage);  // FOR GST
                                }
                                /* END,CODE FOR APPLYING GST   */

                                $("#BillTotal").val("");
                                //$("#BillTotal").val(authCancelTotalWithPckageValue.toFixed(2));
                                $("#BillTotal").val(Math.round(authCancelTotalWithPckageValue).toFixed(2));
                            }
                        }
                        else
                        {
                            $("#GrndTotal").val("");
                            //$("#GrndTotal").val(authCancelTotalValue.toFixed(2));
                            $("#GrndTotal").val(Math.round(authCancelTotalValue).toFixed(2));

                            $("#BillTotal").val("");
                            //$("#BillTotal").val(authCancelTotalValue.toFixed(2));
                            $("#BillTotal").val(Math.round(authCancelTotalValue).toFixed(2));
                        }
                    }
                }
            }
        }
    }
    else if (authCancelRowCount === 1)
    {
        authCancelRowPriceValue = Math.round($("#txtQuantity1").val() * $("#txtRate1").val());
        $("#txtVatValue1").val("");

        /* START,CODE FOR APPLYING GST   */
        if (craftwiseGstPercentage === "0.00") {
            //authCancelRowVatValue = Math.round(authCancelRowPriceValue * $("#txtVat1").val() / 100);
            authCancelRowVatValue = (authCancelRowPriceValue * $("#txtVat1").val() / 100);
            //authCancelRowPriceValueWithVat = Math.round(authCancelRowPriceValue + authCancelRowVatValue);
            authCancelRowPriceValueWithVat = (authCancelRowPriceValue + authCancelRowVatValue);
            $("#txtVatValue1").val(authCancelRowVatValue.toFixed(2));
        } else if (craftwiseGstPercentage > 0) {
            $("#txtVatValue1").val("0.00");
            authCancelRowPriceValueWithVat = authCancelRowPriceValue;
        }
        /* END,CODE FOR APPLYING GST   */

        $("#txtValue1").val("");
        $("#txtValue1").val(authCancelRowPriceValueWithVat.toFixed(2));

        $("#GrndTotal").val("");
        //$("#GrndTotal").val(authCancelRowPriceValueWithVat.toFixed(2));
        $("#GrndTotal").val(Math.round(authCancelRowPriceValueWithVat).toFixed(2));

        $("#BillTotal").val("");

        /* START,CODE FOR APPLYING GST   */
        if (craftwiseGstPercentage === "0.00") {
            //$("#BillTotal").val(authCancelRowPriceValueWithVat.toFixed(2));
            $("#BillTotal").val(Math.round(authCancelRowPriceValueWithVat).toFixed(2));
        } else if (craftwiseGstPercentage > 0) {
            authCancelRowPriceValueWithVat = gstCalculation(authCancelRowPriceValueWithVat, craftwiseGstPercentage);  // FOR GST
            $("#BillTotal").val(Math.round(authCancelRowPriceValueWithVat).toFixed(2));
        }
        /* END,CODE FOR APPLYING GST   */
    }

    document.getElementById('light').style.display = 'none';
    document.getElementById('fade').style.display = 'none';
    $("#TxtDiscount").focus();
//}
}
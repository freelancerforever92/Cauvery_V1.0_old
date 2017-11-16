function loadSalesCounter() {
    $("#content_right").empty();
    $("#cashBill_ContentDiv").empty();
    $("#cashBill_ContentDiv").hide();
    var isHidden = $('#navigatedCounterNameSA').is(':hidden');
    var isCounterNameHidden = $('.currentCounterName').is(':hidden');
    if (isHidden && isCounterNameHidden) {
        //var selectedCounterType = $("#navigatedCounterNameSA").find("option:selected").text().trim();
        var selectedCounterType = $("#navigatedCounterNameSA").val();
        if (selectedCounterType !== "Select") {
            $("#navigatedCounterNameSA").val('Select');
        }
        $("#navigatedCounterNameSA").show();
        $(".currentCounterName").show();
    }
    $("#content_right").load('user_jsp/sales-home.jsp');
}

function loadCashCounter() {
    $("#content_right").empty();
    $("#cashBill_ContentDiv").empty();
    $("#cashBill_ContentDiv").show();
    var isVisible = $('#navigatedCounterNameSA').is(':visible');
    var isCounterNameVisible = $('.currentCounterName').is(':visible');
    if (isVisible && isCounterNameVisible) {
        //var isSelectedCounterType = $("#navigatedCounterNameSA").find("option:selected").text().trim();
        var isSelectedCounterType = $("#navigatedCounterNameSA").val();
        if (isSelectedCounterType !== "Select") {
            $("#navigatedCounterNameSA").val('Select');
        }
        $("#navigatedCounterNameSA").hide();
        $(".currentCounterName").hide();
    }
    $("#content_right").load($("#cashBill_ContentDiv").load('cashbill/cash-Billing.jsp'));
    //$("#content_right").load('cashbill/cash-Billing.jsp');
}
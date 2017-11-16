$(document).ready(function()
{
    load_Showrooms();
});

function loadCounterNumber()
{
    $.getJSON('fillBranchCounter.action', function(data)
    {
        $("#counterNos").empty();
        $.each(data.counterNumberses, function(i, item)
        {
            $("#counterNos").append($("<option></option>").attr("value", item).text(item.counterNos));
        });
    });
}


function validatePlantId()
{
    var branchCode = $("#plantId").val().trim();
    if (branchCode !== "")
    {
        $.getJSON('validateBranchId.action?branchCode=' + $("#plantId").val().trim(), function(data)
        {
            if (data.isBranchExits >= 1)
            {
                loadCounterNumber();
            }
            else
            {
                alert("Oops,PlantId doesnot exits...!!");
                $("#plantId").val("");
                $("#plantId").focus();
            }
        });
    }
}
function getSelectedCounterName()
{
    var selectedCounterText = $("#counterNos :selected").text().trim();
    if (selectedCounterText !== "") {
        if (selectedCounterText !== "Select") {
            $("#selectedCounterText").val("");
            $("#selectedCounterText").val(selectedCounterText);
        }
        else {
            alert("Oops!!!,Select counter type & try again..");
            $("#counterNos").focus();
        }
    } else {
        alert("Please try again");
    }

}

function checkLoginCounterType(e)
{
    var optionSelectedCounterType = $("#selectedCounterText").val();
    if (!optionSelectedCounterType == "")
    {
        return true;
    }
    else
    {
        e.preventDefault();
        alert("Please Select the counter type again...");
        return false;
    }
}

function loadingUserForgotPassword()
{
    document.location.href = 'user-password-reset.jsp';
}
function redirectUserLogin() {
    document.location.href = 'index-login.jsp';
}

function backToLogin()
{
    window.history.back();
}
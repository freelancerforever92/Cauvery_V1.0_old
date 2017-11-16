
function adminLogout()
{
    $.getJSON('superAdminLogout.action', function(data)
    {
        if (data.redirectAdminPage === "superAdminHome") {
            document.location.href = '../admin_pages/index.jsp';
            alert("Logout Successfully..");
        }
    });
}

//function changeadminPwd()
//{
//    if ($("#superUname").val().trim() !== "")
//    {
//        if ($("#supernewpwd").val().trim() !== "")
//        {
//            if ($("#cpwd").val().trim() !== "")
//            {
//                if ($("#supernewpwd").val().trim() === $("#cpwd").val().trim())
//                {
//                    $.getJSON('adminChangePwdAction.action', $("#adminChangePwd").serialize(), function(data)
//                    {
//                        alert("Successfully Password Changed")
//                    });
//                }
//                else {
//                    alert("Oop's Password Not Matching");
//                }
//
//            }
//            else {
//                alert("Oop's Conform Password Missing");
//            }
//
//        }
//        else {
//            alert("Oop's New Password Missing");
//        }
//    }
//    else {
//        alert("Oop's User Name Missing");
//    }
//
//
//}


function loadForgotPassword()
{
    document.location.href = '../user-password-reset.jsp';
}

function userRole()
{
    if ($("#userRoleOption").find("option:selected").text() !== "Select") {
        if ($("#userRoleOption").find("option:selected").text() === "Single Role") {
            $(".userCounterOption").show();
        } else {
            $(".userCounterOption").hide();
        }
    }
}
function changeuserRole() {
    if ($("#userRoleOption").find("option:selected").text() !== "Select") {
        if ($("#userRoleOption").find("option:selected").text() === "Single Role") {
            $(".userCounterOption").show();
        } else {
            $(".userCounterOption").hide();
            $("#selectedUserRole").val("all");
        }
    }
}
function createNewEmployee()
{
    $.getJSON('createNewCounterUser.action', $("#userCreationForm").serialize(), function(data)
    {
        if (data.isUserExits === true) {
            alert("Personal id already exists!!");
            $("#employeePersonalNumber").focus();
            $("#employeePersonalNumber").select();
        } else {
            if (data.employeeCreatedStatus === true) {
                alert("Successfully,User has been created");
                $("#superContentArea").empty();
                $("#superContentArea").load('../adminPage/admin-usermanage.jsp');
                clearAllValues();
            } else {
                alert("Oops,User not been created");
            }
        }
    });
}

function validNewUserInputs()
{
    $("#selectedUserRole").val("");
    if ($("#employeeFname").val().trim() !== "")
    {
        if ($("#employeePersonalNumber").val().trim() !== "")
        {
            if ($("#userRole").find("option:selected").val() !== "select")
            {
//alert($("#userRole").val())
                createNewEmployee();
            } else {
                alert("Oops,Select User role");
                $("#userRole").focus();
            }
        } else {
            alert("Oops,Missing User number");
            $("#employeePersonalNumber").focus();
        }
    } else {
        alert("Oops,Missing User name");
        $("#employeeFname").focus();
    }
}

function getUserInfo() {

    if ($("#empUserid").val().trim() !== "")
    {
        $("#userRes").hide();
        $("#userProgress").show();
        $.getJSON('getEmpInfo.action?empId=' + $("#empUserid").val().trim(), function(data)
        {
            if (data.isAccess > 0) {
                $("#userProgress").hide();
                $("#userRes").show();
                $("#empId").val(data.empId);
                $("#empname").val(data.empname);
                $("#emprole").val(data.emprole);
                $("#role").val(data.emprole);
            }
            else if (data.isAccess <= 0) {
                alert("User Id Not Exist.");
                $("#userRes").hide();
                $("#userProgress").hide();
                $("#empUserid").select();
            }
        });
    }
    else {
        alert("Oops,Missing UserId");
        $("#empUserid").focus();
    }
}

function  updateUserInputs()
{
    if ($("#empname").val().trim() !== "")
    {
        if (confirm("Click ok to Update User Info")) {
            $.getJSON('updateEmpInfo.action', $("#userUpdationForm").serialize(), function(data)
            {
                alert("Successfully, User Info Updated");
                clearUserDetails();
                $("#userRes").hide();
            });
        }
    }
    else {
        alert("Oops,User Name Missing");
        $("#empname").focus();
    }
}

function clearUserDetails()
{
    $("#emprole").val("");
    $("#empId").val("");
    $("#empUserid").val("");
    $("#userRoleOption").val("select");
    $("#empname").val("");
    $(".userCounterOption").hide();
    $("#employeeRoleOption").prop('checked', false);
    $("#userRes").hide();
    $("#empUserid").focus();
}

function clearAllValues()
{
    $("#employeeFname").val("");
    $("#employeeLname").val("");
    $("#employeePersonalNumber").val("");
    $("#userRole").val("select");
    $(".userCounterOption").hide();
    $("#employeeRoleOption").prop('checked', false);
    $("#employeeRoleOption").val("");
    $("#reasonDesc").val(" ");
}

function getMaterialInfo() {
    if ($("#materialId").val().trim() !== "")
    {
        $("#materialRes").hide();
        $("#materialProgress").show();
        $.getJSON('getMatInfo.action?matId=' + $("#materialId").val().trim(), function(data) {
            if (data.isMatExt === 2)
            {
                $("#materialProgress").hide();
                $("#materialRes").show();
                $("#matId").val(data.matId);
                $("#changeCraftGroup").empty();
                $("#changeCraftGroup").append($("<option></option>").attr("value", "select").text("- Select CraftGroup -"));
                $.each(data.craftGroupList, function(i, item)
                {
                    $("#changeCraftGroup").append($("<option></option>").attr("value", item).text(item));
                });
                $("#matDesc").val(data.matDesc);
                $("#cGroup").val(data.craftGroup);
                $("#craftGroup").val(data.craftGroup);
                $("#plantNo").val(data.plantNo);
                $("#storageLoc").val(data.storageLoc);
                $("#distChanelNo").val(data.distChanelNo);
                $("#price").val((data.price).toFixed(2));
            }
            else
            {
                alert("Material Id Not Available");
                $("#materialRes").hide();
                $("#materialProgress").hide();
                $("#materialId").focus();
            }
        });
    }
    else {
        alert("Oop's Material Id Missing");
        $("#materialId").focus();
    }
}


function  updateMatInputs()
{
    if ($("#matDesc").val().trim() !== "")
    {
        if ($("#storageLoc").val().trim() !== "")
        {
            if ($("#distChanelNo").val().trim() !== "")
            {
                if ($("#price").val().trim() !== "")
                {
                    if (confirm("Click OK to continue?")) {

                        $.getJSON('updateMatInfo.action', $("#materialUpdationForm").serialize(), function(data)
                        {
                            alert("Successfully, Material Info Updated");
                            clearMatDetails();
                        });
                    }
                }
                else {
                    alert("Oop's Price Missing");
                    $("#price").focus();
                }

            }
            else {
                alert("Oop's Distributiom Chanel No Missing");
                $("#distChanelNo").focus();
            }

        }
        else {
            alert("Oop's Storage Location Missing");
            $("#storageLoc").focus();
        }
    }
    else {
        alert("Oop's Material Description Missing");
        $("#matDesc").focus();
    }


}


function clearMatDetails()
{
    $("#materialId").val("");
    $("#matDesc").val("");
    $("#craftGroup").val("");
    $("#changeCraftGroup").val("select");
    $("#plantNo").val("");
    $("#storageLoc").val("");
    $("#distChanelNo").val("");
    $("#price").val("");
    $("#materialRes").hide();
    $("#materialId").focus();
}

function  getVendorInfo() {
    if ($("#venId").val().trim() !== "")
    {
        $("#vendorRes").hide();
        $("#vendorInfoProgress").show();
        $.getJSON('getVenInfo.action?vendorId=' + $("#venId").val().trim(), function(data) {
            if (data.valAcess === 1)
            {
                $("#vendorInfoProgress").hide();
                $("#vendorRes").show();
                $("#vendorId").val(data.vendorId);
                $("#vendorAccountGroup").val(data.vendorAccountGroup);
                $("#vTitle").val(data.vendorTitle);
                $("#vendorTitle").val(data.vendorTitle);
                $("#vendorName").val(data.vendorName);
                $("#vendorName2").val(data.vendorName2);
                $("#vendorName3").val(data.vendorName3);
                $("#vendorName4").val(data.vendorName4);
                $("#vendorSearchTerm").val(data.vendorSearchTerm);
                $("#vendorAddress1").val(data.vendorAddress1);
                $("#vendorAddress2").val(data.vendorAddress2);
                $("#vendorAddress3").val(data.vendorAddress3);
                $("#vendorAddress4").val(data.vendorAddress4);
                $("#vendorCity").val(data.vendorCity);
                $("#vendorPinCode").val(data.vendorPinCode);
                $("#vendorDistrict").val(data.vendorDistrict);
                $("#vendorState").val(data.vendorState);
                $("#vendorTelNo").val(data.vendorTelNo);
                $("#vendorMobileNo").val(data.vendorMobileNo);
                $("#vendorFaxNo").val(data.vendorFaxNo);
                $("#vendorEmailId").val(data.vendorEmailId);
            }
            else {
                alert("Vendor Id  is Not there");
                $("#vendorRes").hide();
                $("#vendorInfoProgress").hide();
                $("#venId").focus();
            }
        });
    }

    else {
        alert("Oop's Vendor Id Missing");
        $("#venId").focus();
    }
}

function updateVenInputs() {
    if ($("#vendorAccountGroup").val().trim() !== "")
    {
        if ($("#vendorName").val().trim() !== "")
        {
            if ($("#vendorAddress1").val().trim() !== "")
            {
                updateVendorInputs();
            }

            else {
                alert("Oop's Address1 Missing");
                $("#vendorAddress1").focus();
            }
        }
        else {
            alert("Oop's Vendor Name Missing");
            $("#vendorName").focus();
        }
    }
    else {
        alert("Oop's Account Group Missing");
        $("#vendorAccountGroup").focus();
    }
}

function updateVendorInputs()
{
    if (confirm("Click OK to continue?")) {

        $.getJSON('updateVendorInfo.action', $("#vendorUpdationForm").serialize(), function(data)
        {
            alert("Successfully, Material Info Updated");
            clearVendorDetails();
        });
    }
}
function clearVendorDetails() {
    $("#vendorAccountGroup").val("");
    $("#vendorTitle").val("");
    $("#vendorName").val("");
    $("#vendorName2").val("");
    $("#vendorName3").val("");
    $("#vendorName4").val("");
    $("#vendorSearchTerm").val("");
    $("#vendorAddress1").val("");
    $("#vendorAddress2").val("");
    $("#vendorAddress3").val("");
    $("#vendorAddress4").val("");
    $("#vendorCity").val("");
    $("#vendorPinCode").val("");
    $("#vendorDistrict").val("");
    $("#vendorState").val("");
    $("#vendorTelNo").val("");
    $("#vendorMobileNo").val("");
    $("#vendorFaxNo").val("");
    $("#vendorEmailId").val("");
    $("#titleOption").val("select");
    $("#venId").val("");
    $("#venId").focus();
    $("#vendorRes").hide();
}


function  getBranchCode()
{
    $.getJSON('branchCode.action', function(data)
    {
        $("#branchCode").val(data.plantId);
    });
}

function createCounter()
{
    if ($("#counterName").val().trim() !== "")
    {
        if ($("#counterId").val().trim() !== "")
        {
            if ($("#counterLegacyNo").val().trim() !== "")
            {
                $.getJSON('createCounter.action', $("#counterCreationForm").serialize(), function(data)
                {


                    if (data.count <= 0)
                    {
                        if (data.legacyCount <= 0)
                        {
                            if (data.checkCountId <= 0)
                            {
                                if (data.isCounterCreated >= 1) {
                                    alert("Successfully Counter has been created");
                                    clearCounterInfo();
                                } else if (data.isCounterCreated <= 0) {
                                    alert("Oops,process failure");
                                }
                            }
                            else {
                                alert("Oops Counter Id Already Exist");
                                $("#counterId").focus();
                            }

                        }
                        else {
                            alert("Oops Counter Legacy No Already Exist");
                            $("#counterLegacyNo").focus();
                        }

                    }
                    else {
                        alert("Oops Counter Name Already Exist");
                        $("#counterName").focus();
                    }

                });
            }
            else {
                alert("Oop's Counter Legacy No Missing");
            }

        }
        else {
            alert("Oop's Counter Id Missing");
        }

    }
    else {
        alert("Oop's Counter Name Missing");
    }

}
function clearCounterInfo() {
    $("#counterName").val("");
    $("#counterId").val("");
    $("#counterLegacyNo").val("");
    $("#counId").focus();
    $("#counterRes").hide();
}


function editCounter()
{
    if ($("#counId").val().trim() !== "")
    {
        if ($("#counterName").val().trim() !== "")
        {
            if ($("#counterLegacyNo").val().trim() !== "")
            {
                if (confirm("Click OK to continue?")) {
                    $.getJSON('updateCountInfo.action', $("#counterUpdationForm").serialize(), function(data)
                    {
                        if (data.count === 0)
                        {
                            if (data.legacyCount === 0)
                            {
                                alert("Successfully,Counter Info Updated");
                                clearCounterInfo();
                            }
                            else {
                                alert("Oops,Legacy Count Already Exist..");
                                $("#counterLegacyNo").focus();
                            }
                        }
                        else {
                            alert("Oops,Counter Name Already Exist...");
                            $("#counterName").focus();
                        }
                    });
                }
            }
            else {
                alert("Oop's Counter Legacy No Missing");
                $("#counterLegacyNo").focus();
            }
        }
        else {
            alert("Oop's Counter Name Missing");
            $("#counterName").focus();
        }
    }
    else {
        alert("Oop's Counter Id Missing");
        $("#counId").focus();
    }

}

function getCounterInfo() {
    if ($("#counId").val().trim() !== "")
    {
        $("#counterRes").hide();
        $("#counterInfoProgress").show();
        $.getJSON('getCountInfo.action?counterId=' + $("#counId").val().trim(), function(data) {
            if (data.valAcess === 2)
            {
                $("#counterInfoProgress").hide();
                $("#counterRes").show();
                $("#counterId").val(data.counterId);
                $("#counterName").val(data.counterName);
                $("#counterLegacyNo").val(data.counterLegacyNo);
            }
            else {
                alert("Counter Id  is Not there");
                $("#counterRes").hide();
                $("#counterInfoProgress").hide();
                $("#counId").focus();
            }
        });
    }
    else {
        alert("Oop's Counter Id Missing");
        $("#counterRes").hide();
        $("#counId").focus();
    }
}

function createQuestion() {
    if ($("#securityQuestion").val() !== "") {
        $.getJSON('createQuestionAction.action?securityQuestion=' + $("#securityQuestion").val().trim(), function(data)
        {
            if (data.questionsCount <= 0)
            {
                if (data.questionsRowsCount < data.questionslimitValue)
                {
                    if (data.isQuestionCreated >= 1)
                    {
                        alert("Question Successfully Created");
                        clearQuestionInfo();
                    }
                    else {
                        alert("Oops,Process failure...");
                    }
                }
                else {
                    var questionCountValue = data.questionslimitValue - 1;
                    alert("Sorry you are not able to create more than " + questionCountValue + " questions");
                    $("#securityQuestion").focus();
                }
            }
            else {
                alert("Question allready exist");
                $("#securityQuestion").focus();
            }
        });
    }
    else {
        alert("Oop's Security Question is Missing");
        $("#securityQuestion").focus();
    }
}


function clearQuestionInfo() {
    $("#securityQuestion").val("");
}


function validNewReasonInputs() {
    if ($("#reasonDesc").val().trim() !== "") {
        $.getJSON('CreateCancelReason.action?reasonDesc=' + $("#reasonDesc").val().trim(), function(data)
        {
            if (data.countExixt <= 0) {
                if (data.countConcat <= data.reasonslimitValue) {
                    if (data.isCounterCreated === 1) {
                        alert("Reason Sucesfully Created...!!");
                        $("#reasonDesc").val(" ");
                    }
                    else {
                        alert("Sorry you are not able to create more than  " + data.reasonslimitValue + " Reasons ");
                        $("#reasonDesc").focus();
                    }
                }
                else {
                    alert("Sorry you are not able to create more than  " + data.reasonslimitValue + " Reasons");
                    $("#reasonDesc").focus();
                }
            }
            else {
                alert("Reason already exists...!!");
                $("#reasonDesc").focus();
            }
        });
    } else {
        alert("Oops,Missing Reason Field");
        $("#reasonDesc").focus();
    }
}
function createSIR() {
//    if ($("#salesintimationReportName").val() !== "") {

    if ($("#salesintimationReportName").find("option:selected").val() !== "select") {
        if ($("#salesintimationReportAliasName").val() !== "") {
            
         //   alert($("#salesintimationReportName").find("option:selected").text());
            
            $.getJSON('createSIRAction.action?siReportNme=' + $("#salesintimationReportName").find("option:selected").text().trim(), "&siReportAliasNme=" + $("#salesintimationReportAliasName").val().trim(), function(data)
            {
                if (data.validationSIRVal === 1) {
                    if (data.checkAliasNameVal === 1) {
                        if (data.createSIRTVal > 0) {
                            alert("Succefully Created");
                            clearSIRInfo();
                        }
                        else {
                            alert("Creation Failed");
                            $("#salesintimationReportName").focus();
                        }

                    }
                    else {
                        alert("Alias Name existed");
                        $("#salesintimationReportAliasName").focus();
                    }
                }
                else {
                    alert("Report Name Not valid");
                    $("#salesintimationReportName").focus();
                }

            });

        }
        else {
            alert("Oops,Missing Report Alias Name");
            $("#salesintimationReportAliasName").focus();
        }
    }
    else {
        alert("Oops,Missing Report Name");
        $("#salesintimationReportName").focus();
    }

}

function clearSIRInfo() {
    $("#salesintimationReportName").val("");
    $("#salesintimationReportAliasName").val("");
    $("#salesintimationReportName").focus();
}
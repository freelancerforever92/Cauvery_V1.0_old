function loadSecurityQuestions()
{
    $.getJSON('passwordSecurityQuestions.action', function(data)
    {
        $("#user_security_questions").empty();
        $.each(data.pqs, function(i, item)
        {
            $("#user_security_questions").append($("<option></option>").attr("value", item.questionPk).text(item.securityQuestions));
        });
    });
}

function checkingPasswordIsRefreshed() {
    if ($("#user_userName").val().trim() != "")
    {
        $.getJSON('checkPasswordRefreshed.action?passwordResetUserName=' + $("#user_userName").val().trim(), function(data)
        {
            if (data.passwordRefreshedStatus == true) {
                $("#user_reset_button").val("");
                $("#user_reset_button").val("Set Password");
            } else if (data.passwordRefreshedStatus == false) {
                $("#user_reset_button").val("");
                $("#user_reset_button").val("Reset Password");
            }
        });
    }
}

function resetUserPassword()
{
    if ($("#user_userName").val().trim() !== "") {
        if ($("#user_security_questions").find("option:selected").text() !== "Select") {
            if ($("#user_security_answer").val().trim() !== "") {
                if ($("#user_new_password").val().trim() !== "") {
                    if ($("#user_confirm_password").val().trim() !== "") {
                        resetUserPasswordProcess();
                    } else {
                        alert("Enter confirm password..");
                        $("#user_confirm_password").focus();
                    }
                } else {
                    alert("Enter password..");
                    $("#user_new_password").focus();
                }
            } else {
                alert("Enter security answer..");
                $("#user_security_answer").focus();
            }
        } else {
            alert("Select security question..");
        }
    }
    else {
        alert("Enter username..");
        $("#user_userName").focus();
    }
}

function resetUserPasswordProcess() {
    var newPassword = $("#user_new_password").val().trim();
    var confirmPassword = $("#user_confirm_password").val().trim();
    if (newPassword == confirmPassword) {
        $("#buttonValue").val($("#user_reset_button").val().trim());
        $.getJSON('userPasswordReset.action', $("#userPasswordResetForm").serialize(), function(data)
        {
            if (data.user_password_status == 1) {
                alert("Password reseted successfully");
                clearAll();
                backToUserLogin();
            } else if (data.user_password_status == -1) {
                alert("Username not avaiable");
                $("#user_userName").focus();
            } else if (data.user_password_status == -2) {
                alert("Invalid username or security question");
            }
        });
    } else {
        alert("Password & confirm password is not equal..");
        $("#user_new_password").val("");
        $("#user_confirm_password").val("");
        $("#user_new_password").focus();
    }
}

function clearAll()
{
    $("#user_userName").val("");
    $("#user_security_questions").find("option:selected").text("Select");
    //$("#user_security_questions").find("option:selected").val("Select");
    $("#user_security_answer").val("");
    $("#user_new_password").val("");
    $("#user_confirm_password").val("");
    
}

function backToUserLogin()
{
    document.location.href = 'index-login.jsp';
}

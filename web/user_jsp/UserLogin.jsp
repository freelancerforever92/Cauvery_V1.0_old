<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Cauvery-Login</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <link rel="stylesheet" type="text/css" href="css/niceforms-default.css" />
        <script src="js/jquery.validate.js" type="text/javascript"></script>
        <script type="text/javascript">
            $.validator.setDefaults(
                    {
                    });
            $(document).ready(function()
            {
                load_BranchCodes();
                $("#userLogin").validate(
                        {
                            rules:
                                    {
                                        txtUname:
                                                {
                                                    required: true
                                                },
                                        txtPassword:
                                                {
                                                    required: true
                                                }
                                    },
                            messages:
                                    {
                                        txtUname:
                                                {
                                                    required: "Please Enter Username"
                                                },
                                        txtPassword:
                                                {
                                                    required: "Please Enter Password"
                                                }
                                    }
                        });

            });

            function load_BranchCodes()
            {
                $.getJSON('BranchCode', function(data)
                {
                    $("#counterNos").empty();
                    $.each(data.counterCodes, function(i, item)
                    {
                        i = i + 1;
                        $("#counterNos").append($("<option></option>").attr("value", i).text(item));
                    });
                });
            }
        </script>
    </head>
    <body>
        <div id="main_container">
            <div class="header_login">
                <div class="logo">
                    <a href="#">
                        <img src="images/cauvery.jpg" alt="" title="" border="0" style="margin-left: 20px;" />
                    </a></div>
            </div>
            <div class="login_form">
                <a href="#" class="forgot_pass">Forgot password</a> 
                <s:form id="userLogin" name="userLogin" action="Login" method="post" class="niceform">
                    <%--<input id="errValue" name="errValue" value="<s:property value="errMsg"/>"/>--%>
                    <fieldset style="margin-left: 100px;">

                        <table cellpadding="9" width="50%" border="0"align="center">
                            <tr>
                                <td>
                                    <label for="branchcode" style="margin-top: 10px;">Branch Code</label>
                                </td>
                                <td>
                                    <select id="counterNos" name="counterNos" style="width: 164px;height: 33px;">
                                        <option value="0" selected="0">Select</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="email" style="margin-top: 10px;">Username:</label>
                                </td>
                                <td>
                                    <input type="text" name="txtUname" id="txtUname" autocomplete="off" size="25" style="margin-top: 10px;"/>
                                </td>
                            </tr>
                            <tr >
                                <td>
                                    <label for="password" style="margin-top: 450px;" >Password:</label>
                                </td>
                                <td>
                                    <input type="password" name="txtPassword" id="txtPassword" autocomplete="off" size="25" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="submit" name="btnSubmit" id="btnSubmit" value="Login" onclick="Chek();"style="margin-top: 20px;margin-left: 100px;"/>
                                </td>
                                <td>
                                    <input type="reset" name="btnSubmit" id="btnSubmit" value="Cancel" style="margin-top: 20px;margin-left: 10px;"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>

                </s:form>
            </div>  

            <div class="footer_login">

                <div class="left_footer_login">CAUVERY | Powered by | Unisoft |<s:property value="errMsg"/></div>
                <div class="right_footer_login">
                    <a href="#">

                    </a>
                </div>

            </div>

        </div>		
    </body>
</html>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-json-tags" %>
<%@taglib prefix="sjq" uri="/struts-jquery-grid-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Cauvery -Reset Password</title>
        <link rel="icon" href="./images/cauvery.jpg" rel="Cauvery" type="image/x-icon"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="admin_pages/css/custom_styles.css" />
        <script type="text/javascript" src="js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="js/jquery-user-password-reset.js"></script> 

        <script type="text/javascript">
            $(document).ready(function()
            {
                loadSecurityQuestions();
            });
            function backToLogin()
            {
                window.history.back();
            }
        </script>
        <style>        
            .head-sec{background-color: #ffffff;}
        </style>
    </head>
    <body class="admin_login_bg">
        <!-- Header Section [Start] -->
        <div class="clearfix head-sec">
            <div class="row-fluid">
                <div class="span3"> <a href="#"><img src="images/cauvery.jpg" alt="Admin" width="66" /></a> </div>
                <div class="span6">
                    <div class="clearfix t-c" style="color: #0480be">   
                        <span style="font-family: serif;font-weight: bold;font-size: medium;color: #942a25">CAUVERY</span><br>  
                        Karnataka State Arts and Craft Emporium<br>
                        ( Unit of Karnataka State Handicrafts Development Corpn.LTD )<br>
                        ( A Government of Karnataka Enterprise )<br>
                        #49,Mahatma Gandhi Road,Bangalore-560001<br>
                    </div>
                </div>
                <div class="span3">
<!--                    <div class="clearfix pull-right" style="margin-top: 20px;">
                        Date Time  : <span id="lblTime" style=" font-weight:bold">
                        </span>
                    </div>-->
                </div>
            </div>
        </div>
        <!-- Header Section [End] -->

<!--        <div id="progressImage"></div>-->


        <div class="reset_pwd">
            <h2><span>Cauvery POS</span> - Reset Password</h2>
            <form id="userPasswordResetForm" name="user-passwordreset-form" method="post">
                <input type="hidden" id="buttonValue" name="buttonValue"/>

                <div class="row-fluid">
                    <div class="span4">
                        <label>User Id :</label>
                    </div>
                    <div class="span8">
                        <input type="text" id="user_userName" name="user_userName" class="input-block-level" autocomplete="off" onblur="checkingPasswordIsRefreshed();"/>
                    </div>                        
                </div>

                <div class="row-fluid">
                    <div class="span4">
                        <label>Security Question :</label>
                    </div>
                    <div class="span8">
                        <select id="user_security_questions" class="input-block-level" name="user_security_questions" title="Select Question">
                            <option value="0">Select</option>
                        </select>
                    </div>                        
                </div>
                <div class="row-fluid">
                    <div class="span4">
                        <label>Answer :</label>
                    </div>
                    <div class="span8">
                        <textarea id="user_security_answer" class="input-block-level" name="user_security_answer" autocomplete="off" rows="2" ></textarea>
                    </div>                        
                </div>
                <div class="row-fluid">
                    <div class="span4">
                        <label>New Password :</label>
                    </div>
                    <div class="span8">
                        <input type="password" id="user_new_password" name="user_new_password" class="input-block-level" autocomplete="off"/>
                    </div>                        
                </div>
                <div class="row-fluid">
                    <div class="span4">
                        <label>Confirm Password :</label>
                    </div>
                    <div class="span8">
                        <input type="password" id="user_confirm_password" name="user_confirm_password" class="input-block-level" autocomplete="off"/>
                    </div>                        
                </div>
                <div class="row-fluid" style="margin-top: 10px;">
                    <div class="span4">
                        
                    </div>
                    <div class="span3">
                        <a onclick="backToLogin();" href="javascript:void(0)">&larr; Back to Login</a>
<!--                        <input type="button" onclick="backToLogin();" class="btn btn-danger" value="Back to Ligin"/>-->
                    </div>
                    <div class="span2">
                        <input type="button" id="user-cancel-button" name="user-cancel-button" class="btn btn-warning" value="Cancel" onclick="clearAll();" />
                    </div> 
                    <div class="span3">
                        <input type="button" id="user_reset_button" name="user_reset_button" class="btn btn-info" value="Reset Password" onclick="resetUserPassword();"/>
                    </div>
                </div>               
            </form>
        </div>
    </body>
</html>


<%-- 
    Document   : index
    Created on : Feb 26, 2014, 9:03:18 AM
    Author     : unicare
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cauvery</title>
        <script src="js/jquery.min.js" type="text/javascript"></script>
        <script src="js/jquery.validate.js" type="text/javascript"></script>
        <script src="js/niceforms.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/key.js"></script>

        <link rel="stylesheet" href="css/menu.css" type="text/css"/>
        <link rel="stylesheet" href="css/style_old.css" type="text/css"/>
        <link rel="stylesheet" href="css/niceforms-default.css" type="text/css"/>
        <link rel="stylesheet" href="css/netdna.bootstrapcdn.com_bootstrap_3.0.0_css_bootstrap.min.css" type="text/css"/>
        <link rel="stylesheet" href="css/style.mini.css" type="text/css"/>
        <script type="text/javascript">
            $(document).ready(function()
            {
                var now = new Date();
                var curr_datetime = "";
                curr_datetime = now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();
                $('#Txtlogo_datetime').text(curr_datetime);
                Display_LoginName();
            });

            function Display_LoginName()
            {
                var session_value = "";
                $("#login_name").text("");
                $.getJSON('SessionName.action', function(data)
                {
                    session_value = data.cursession_value;
                    //alert(session_value);
                    if (session_value === "valid")
                    {
                        $("#login_name").text(data.displayname);
                        $("#Txtorderno").text(data.salesOrderNo);
                        $("#Txtlog_empid").text(data.loginempId);
                    }
                    else if (session_value === "in-valid" || session_value === null)
                    {
                        $("#login_name").text("");
                    }
                });
            }


            function userLogout()
            {
                $.getJSON('SessionClear.action', function(data)
                {
                    if (data.forwardPage === "homepage")
                    {
                        alert("Logout Successfully..");
                        document.location.href = 'UserLogin.jsp';
                    }
                });
            }


            function load_Sales()
            {
                $("#contentBlock").empty();
                $("#contentBlock").load('SalesFrom.jsp');
            }

            function button_submit()
            {
                $.getJSON('samplesubmit.action', $("#sampleform").serialize(), function(data)
                {
                    alert("------------>  :  " + data.rtn);
                });
            }
        </script>
    </head>
    <body>
        <form id="sampleform" name="sampleform" method="post">
            <div class="container">
                <div class="row">
                    <div style="width: 1300px;">
                        <div style="width: 30%; float: left; margin-left: 14px;">
                            <img src="./images/cauvery.jpg" height="180"/>
                        </div>
                        <div style="width: 32%;float: left;margin-left:65px;">
                            <center>
                                <p><b>CAUVERY</b></p>
                                <p><b>Karnataka State Arts & Crafts Emporium,</b></p>
                                <p><b># 49, Mahatma Gandhi Road, Bangalore-560001.</b></p>
                                <p><b>Phone   : 91-80-25581118        Fax :91-80-25325491</b></p>
                                <p><b>Email   : Cauveryempmg@vsnl.net</b></p>
                                <p><b>Website : www.cauverycrafts.com</b></p>
                            </center>
                        </div>
                        <div>
                            <div style="width: 21%;float: right;margin-right: -55px;">
                                <table>
                                    <tr>
                                        <td><label>Login DateTime : </label></td>
                                        <td>
                                            <label id="Txtlogo_datetime" name="Txtlogo_datetime" style="margin-left: 10px;"></label>
                                        </td>
                                    </tr>
                                    <tr >
                                        <td><label>Employee Id    :</label></td>
                                        <td>
                                            <label id="Txtlog_empid" name="Txtlog_empid" style="margin-left: 10px;"></label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><label>Sales Order No :</label></td>
                                        <td>
                                            <label id="Txtorderno" name="Txtorderno" style="margin-left: 10px;"></label>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                        <div style="margin-left: 30px;" class="pull-left">
                            <h1 class="heading"><strong></strong></h1>        
                            <h3><%--online project source code portal--%></h3>
                        </div>
                    </div>
                    <div class="col-xs-12 col-lg-3 col-sm-3">
                        <div class="pull-right">

                        </div>
                    </div>    
                </div>
                <div style="margin-left: 1100px;">Welcome <label id="login_name"></label> | <a href="#" class="logout" onclick="userLogout();">Logout</a>
                </div>
                <div class="navbar navbar-default">
                    <!--<div class="container">-->
                    <div class="navbar-collapse in" style="height: auto;">
                        <ul class="nav navbar-nav">
                            <li id="menuProject">
                                <a href="#" onclick="load_Sales();"><b>Coupon Sale</b></a>
                            </li>
                            <li id="menuProjectIdea">
                                <a href="#"><b>Customer Details</b></a>
                            </li>
                            <li id="menuQuestion">
                                <a href="#"><b>Sales History</b></a>
                            </li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <%--<li><a href="#">Login</a></li>--%>
                        </ul>
                    </div><!--/.nav-collapse -->
                    <!--</div>-->
                </div>           
                <div class="row">
                    <div class="col-xs-12 col-sm-3">
                        <div id="contentBlock">

                        </div>                
                    </div>
                </div>
                <div id="footer">
                    <div class="container">
                        <%--<p class="muted credit">©<a href="#">powered by Cauvery.</a></p>--%>
                    </div>
                </div>
        </form>
    </body>
</html>

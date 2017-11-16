<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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
        <script src="<c:url value='/js/jquery-1.10.1.min.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-transition.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-alert.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-modal.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-dropdown.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-scrollspy.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-tab.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-tooltip.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-popover.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-button.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-collapse.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-carousel.js'/>"></script>
        <script src="<c:url value='/js/bootstrap-typeahead.js'/>"></script>
        <script src="<c:url value='/js/jquery.home.js'/>"></script>
        <script src="<c:url value='/js/sales.home.js'/>"></script>
        <script src="<c:url value='/js/key.js'/>"></script>
        <script src="<c:url value='/js/salesorder-cancelling.js'/>"></script>
        <script src="<c:url value='/js/jquery.cashbill.js'/>"></script>
        <script src="<c:url value='/js/jquery.printPage.js'/>"></script> 
<% SimpleDateFormat d=new SimpleDateFormat("dd/MM/yyyy");
        Date now=new Date();
        String[] Str=d.format(now).split("/");
        String day=Str[0];
        String month=Str[1];
        String year=Str[2];
        %>
        <!-- Le styles -->
        <link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
        <link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.css'/>" type="text/css" />
        <link rel="stylesheet" href="<c:url value='/css/style.css'/>" type="text/css" />
        <link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.min.css'/>" type="text/css" />

        <script type="text/javascript">
            /*
             function setCookie(cname, cvalue) {
             var d = new Date();
             d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
             var expires = "expires=" + d.toGMTString();
             document.cookie = cname + "=" + cvalue + "; " + expires;
             }
             */

            /*
             function getCookie(name) {
             var result = document.cookie.match(new RegExp(name + '=([^;]+)'));
             return result[1];
             }
             */


            $(document).ready(function()
            {
                
                loadCounterNumber();
            });
            function ShowCurrentTime() {
                var dt = new Date();
                var currentDate = dt.getDate();
                var currentMonth = dt.getMonth( ) + 1;
                var currentYear = dt.getFullYear();
                var x1 = (currentDate < 10 ? '0' : '') + currentDate + " / " + (currentMonth < 10 ? '0' : '') + currentMonth + " / " + currentYear;
                document.getElementById("lblTime").innerHTML =  <%=day%>+" / "+<%=month%>+" / "+<%=year%>  + " - " + dt.toLocaleTimeString('en-GB'); 
                window.setTimeout("ShowCurrentTime()", 1000); // Here 1000(milliseconds) means one 1 Sec  
            }
            function loadCounterNumber() {
                $.getJSON('fillBranchCounter.action', function(data)
                {
                    $("#navigatedCounterName").empty();
                    $("#navigatedCounterName").append($("<option></option>").attr("value", "Select").text("Select"));
                    $.each(data.counterNumberses, function(i, item)
                    {
                        $("#navigatedCounterName").append($("<option></option>").attr("value", item.counterName).text(item.counterLegacyNumber));
                    });
                });
            }

            function genericSelectedCounter()
            {
                //var currentCounter = $("#navigatedCounterName").find("option:selected").text().trim();
                var currentCounter = $("#navigatedCounterName").val().trim();
                if (currentCounter !== "Select") {
                    $("#Txtmaterial").attr("disabled", false);
                    $("#Txtvendor").attr("disabled", false);
                    $("#Txtquantity").attr("disabled", false);
                    $("#Txtmaterial").focus();
                    if ($("#Txtmaterial").val().trim() !== "") {
                        $("#Txtmaterial").val("");
                        $("#Txtmaterial").focus();
                    }
                    $("#displayCounterName").text("");
                    $("#displayCounterName").text(currentCounter + " " + "COUNTER");
                    document.getElementById('navigatedCounterName').style.borderColor = "";
                    $.getJSON('sendGenericSelectedCounterName.action?changedCounterType=' + currentCounter, function(data) {

                    });
                }
                else {
                    alert("Please select counter type");
                    $("#Txtmaterial").val("");
                    $("#displayCounterName").text("");
                    $("#displayCounterName").text("-SELECT COUNTER-");
                    $("#navigatedCounterName").focus();
                    $("#Txtmaterial").attr("disabled", true);
                    $("#Txtvendor").attr("disabled", true);
                    $("#Txtquantity").attr("disabled", true);
                }
            }

        </script>
        <style type="text/css">
            .black_overlay{
                display: none;
                position: fixed;
                top: 0%;
                left: 0%;
                width: auto;
                height: auto;
                background-color:#b0c4de;
                z-index:1001;
                -moz-opacity: 0.8;
                opacity:.80;
                filter: alpha(opacity=80);
            }
            .white_content {
                display: none;
                position: fixed;
                top: 30%;
                left: 40%;
                width: 16%;
                height: 20%;
                padding: 16px;
                border: 2px solid #00FF00;
                background-color: white;
                z-index:1002;
                overflow: auto;
            }
        </style>
        <style type="text/css">

            .white_contentcustomerInfo {
                display: none;
                position: fixed;
                top: 30%;
                left: 35%;
                width: 28%;
                height: 30%;
                padding: 16px;
                border: 2px solid #ffff00; 
                background-color: #F5F5F5;
                z-index:1002;
                overflow: auto;
            }
        </style>
        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
                  <script src="<c:url value='/js/html5.js'/>"></script>
                <![endif]-->

        <link rel="icon" href="./images/cauvery.jpg" rel="Cauvery" type="image/x-icon"/>
        <title>Cauvery V1.0</title>
    </head>
    <body onload="ShowCurrentTime();">
        <img src="./images/cauvery.jpg"  height="80" width="60"  class="pull-left" style="margin-left: 15px;margin-top: 5px"/>
        <div style="text-align: center; width: 90%;">
            <address style="color: #0480be">   
                <span style="font-family: serif;font-weight: bold;font-size: medium;color: #942a25">CAUVERY</span><br>  
                Karnataka State Arts and Crafts Emporium<br>
                #49, Mahatma Gandhi Road, Bangalore-560001<br>
                ( Unit of Karnataka State Handicrafts Development Corp.Ltd., <br>
                A Government of Karnataka Enterprise )<br>
            </address>
            <div style="width: 22%; float: right;margin-right: -12%;margin-top: -8%;text-align: left">
                <%--Employee Id :<span id="txtlog_empid" name="txtlog_empid"></span> <br>--%>
                <input type="hidden" id="txtlog_empid" name="txtlog_empid"/>
                <%--CounterBill No : <span id="Txtorderno" name="Txtorderno"></span> <br>--%>
                Date Time           : <span id="lblTime" style="font-weight:bold"></span>
                <select id="navigatedCounterName" name="navigatedCounterName" style="width: 215px;height: 33px;margin-top: 16px;"  onchange="genericSelectedCounter();">
                    <option value="0" selected="0">Select</option>
                </select>
            </div>
            <div style="float: right;margin-top:-25px;margin-right:-88px;">
                <label><b>Your In : </b>
                    <b style="color: red;" id="displayCounterName"><s:property value="loginCounterName"/></b>
                </label>
            </div>
                
               

            <s:if test="#session.User_type=='tenderCounter'">
                <h3 id="counterTypeTender" style="color:red;text-transform: capitalize;">Tender Counter</h3>
                <input type="hidden" id="thisIsTender" hidden="true" value="tender"/>
            </s:if>
            <s:else>
                <h3 id="counterTypeTender" style="color:red;text-transform: capitalize;">Sales Counter</h3>
              <input type="hidden" id="thisIsTender" hidden="true" value="sales"/>
            </s:else>
        </div>
        <div id="progressImage"></div>
        <div class="navbar ">
            <input type="text" id="counterType" name="counterType" style="display: none;"value="<s:property value="navCounterName"/>"/>
            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse"data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a> <a class="brand" href="#">Cauvery</a>
                    <div class="btn-group pull-right">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#"> 
                            <i class="icon-user">
                            </i>&nbsp;&nbsp;&nbsp;<s:property value="disLogName"/> <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <%--
                            <li><a href="#">Profile</a></li>
                            <li class="divider"></li>
                            --%>
                            <li><a href="javascript:void(0)" onclick="userLogout();">Sign Out</a></li>
                        </ul>
                        <%--
                    <a class="btn icon-bar" href="#"> 
                        <i class=""></i>&nbsp;&nbsp;&nbsp;<span class="caret"></span>
                    </a>
                        --%>

                    </div>
                    <div class="nav-collapse">
                        <ul class="nav">
                            <%--<li><a href="javascript:void(0)" onclick="load_salesForm();">Counter Bill</a></li>-
                            <%--<li><a href="javascript:void(0)" onclick="load_salesForm();">Counter Bill</a></li>
                            <li><a href="javascript:void(0)" onclick="load_salesHistory();">Sales History</a></li>
                            <li><a href="javascript:void(0)" onclick="showCustInfo();">Customer Info</a></li>--%>
                            <%--<li><a href="javascript:void(0)" onclick="load_CashBill();">Cash Bill</a></li>--%>
                            <%--
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Reports<b class="caret"></b>
                                </a>
                                <ul class="dropdown-menu">
                                    <li class=""><a href="#mysql">Reports By Date</a></li>
                                </ul>
                            </li>
                            --%>
                        </ul>
                    </div>
                    <!--/.nav-collapse -->
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div class="row-fluid">
                <div class="row-fluid">
                    <div id="content_right">

                    </div>
                </div>
                <!--/row-->
                <!--/span-->
            </div>
            <!--/row-->
            <div class="modal-footer">
                <center>
                    Fax :91-80-25325491<br> 
                    Email   : Cauveryempmg@vsnl.net<br> 
                    Website : <a href="http://www.cauveryhandicrafts.net">http://www.cauveryhandicrafts.net</a>
                </center>
            </div>
        </div>
    </body>
</html>


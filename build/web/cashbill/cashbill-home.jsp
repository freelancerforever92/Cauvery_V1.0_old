<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <script src="<c:url value='/js/jquery.cashbill.js'/>"></script>
        <script src="<c:url value='/js/jquery.cahbill-return.js'/>"></script>
        <script src="<c:url value='/js/jquery-coupon-creation.js'/>"></script>
        <script src="<c:url value='/js/jquery-coupon-sales.js'/>"></script>
        <script src="<c:url value='/js/key.js'/>"></script>
        <!-- Le styles -->
        <link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>" type="text/css"/>
        <link rel="stylesheet" href="<c:url value='/css/font-awesome.min.css'/>" type="text/css"/>
        
        <link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.css'/>" type="text/css" />
        <link rel="stylesheet" href="<c:url value='/css/style.css'/>"type="text/css" />
<% SimpleDateFormat d=new SimpleDateFormat("dd/MM/yyyy");
        Date now=new Date();
        String[] Str=d.format(now).split("/");
        String day=Str[0];
        String month=Str[1];
        String year=Str[2];
        %>
        <script type="text/javascript">
            /* function setCookie(cname, cvalue) {
             var d = new Date();
             d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
             var expires = "expires=" + d.toGMTString();
             document.cookie = cname + "=" + cvalue + "; " + expires;
             }
             
             function getCookie(name) {
             var result = document.cookie.match(new RegExp(name + '=([^;]+)'));
             return result[1];
             }
             */
            function cashBillShowCurrentTime() {
                var dt = new Date();
                var currentDate = dt.getDate();
                var currentMonth = dt.getMonth( ) + 1;
                var currentYear = dt.getFullYear();
                var x1 = (currentDate < 10 ? '0' : '') + currentDate + " / " + (currentMonth < 10 ? '0' : '') + currentMonth + " / " + currentYear;
               document.getElementById("cashBillLabelTime").innerHTML = <%=day%>+" / "+<%=month%>+" / "+<%=year%> + " - " + dt.toLocaleTimeString('en-GB');
                window.setTimeout("cashBillShowCurrentTime()", 1000); // Here 1000(milliseconds) means one 1 Sec  
            }
        </script>
        <style type="text/css">

            .HidTextField
            {
                visibility: hidden;
            }
            #txtApproval
            {
                border: none;
            }
            .black_overlay{
                display: none;
                position: absolute;
                top: 0%;
                left: 0%;
                width: 100%;
                height: 100%;
                background-color: black;
                z-index_value:1001;
                -moz-opacity: 0.8;
                opacity:.80;
                filter: alpha(opacity=80);
            }
            .white_content {
                display: none;
                position: absolute;
                top: 30%;
                left: 38%;
                width: 18%;
                height: 3%;
                padding: 16px;
                /*border: 16px solid orange;
                border-width: medium;*/
                background-color: white;
                z-index_value:1002;
            }
            .black_overlay2{
                display: none;
                position: absolute;
                top: 0%;
                left: 0%;
                width: 100%;
                height: 100%;
                background-color: black;
                z-index_value:1001;
                -moz-opacity: 0.8;
                opacity:.80;
                filter: alpha(opacity=80);
            }
            .white_content2 {
                display: none;
                position: absolute;
                top: 30%;
                left: 38%;
                width: 20%;
                height:25%;
                padding: 16px;
                /*border: 16px solid orange;
                border-width: medium;*/
                background-color: white;
                z-index_value:1002;
            }
            .web_dialog_titlecolor
            {   
                margin-top: -15px;
                margin-left: -16px;
                width: 101%;
                /*#244894*/
                background-color: #f0ad0a;
                padding:14px;
                color: White;
                font-weight:bold;
            }
        </style>
        <link rel="icon" href="./images/cauvery.jpg" rel="Cauvery" type="image/x-icon"/>
        <title>Cauvery V1.0</title>
        <sj:head jquerytheme="excite-bike"/>
    </head>
    <body onload="cashBillShowCurrentTime();">
        <img src="./images/cauvery.jpg"  height="80" width="60"  class="pull-left" style="margin-left: 15px;margin-top: 5px"/>
        <div style="text-align: center; width: 90%;">
            <address style="color: #0480be">   
                <span style="font-family: serif;font-weight: bold;font-size: medium;color: #942a25">CAUVERY</span><br>  
                Karnataka State Arts and Crafts Emporium<br>
                #49, Mahatma Gandhi Road, Bangalore-560001<br>
                ( Unit of Karnataka State Handicrafts Development Corp.Ltd., <br>
                A Government of Karnataka Enterprise)<br>
            </address>
            <div style="width: 20%; float: right;margin-right: -12%;margin-top: -8%;text-align: left">
                <%--Employee Id :<span id="txtlog_empid" name="txtlog_empid"></span> <br>
                Order No : <span id="txtCashBillOrderno" name="txtCashBillOrderno"></span> <br>--%>
                Date Time           : <span id="cashBillLabelTime" style=" font-weight:bold"></span>
            </div>

        </div>
        <div id="progressImage"></div>
        <div class="navbar ">
            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a> <a class="brand" href="#" onclick="load_CashBillSalesForm();">Cauvery</a>
                    <div class="btn-group pull-right">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#"> 
                            <i class="icon-user"></i>&nbsp;&nbsp;&nbsp;<s:property value="disLogName"/> <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:void(0)" onclick="userLogout();">Sign Out</a></li>
                        </ul>
                    </div>
                    <div class="nav-collapse">
                        <ul class="nav">
                            <%--   TEMP-BLOCKED FOR DEMO 
                            <li><a href="javascript:void(0)" onclick="loadCreateCouponPage();">Create Coupon</a></li>
                            <li><a href="javascript:void(0)" onclick="loadCouponSalesPage();">Coupon Sales</a></li>
                            --%>
                            <%--<li><a href="javascript:void(0)" onclick="load_salesForm();">Counter Bill</a></li>
                            <li><a href="javascript:void(0)" onclick="load_salesHistory();">Sales History</a></li>
                            <li><a href="javascript:void(0)" onclick="showCustInfo();">Customer Info</a></li>
                            <li><a href="javascript:void(0)" onclick="load_CashBill();">Cash Bill</a></li>
                            --%>
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
                    <div id="cashBill_ContentDiv">

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


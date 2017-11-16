<%@page import="com.DAO.DaoClass"%>
<%@taglib prefix="html" uri="/struts-tags"%>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="icon" href="./images/cauvery.jpg" rel="Cauvery" type="image/x-icon"/>
    <title>Cauvery-Login</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="admin_pages/css/custom_styles.css" />
    <script type="text/javascript" src="js/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.indexPage.js"></script>
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
                <!--                <div class="clearfix pull-right" style="margin-top: 20px;">
                                    Date Time  : <span id="lblTime" style=" font-weight:bold">
                                    </span>
                                </div>-->
            </div>
        </div>
    </div>
    <!-- Header Section [End] -->
    <div class="user_login">
        <h2><span>Cauvery POS</span> - Login</h2>
        <html:form id="showRoomId" name="userLogin" class="form login-form" action="Login" method="post">
            <input type="hidden" id="selectedCounterText" name="selectedCounterText" style="display: none;"/>
            <input type="text" id="plantId" name="plantId" readonly="true" placeholder="<%=DaoClass.getPlantId()%>" value="<%=DaoClass.getPlantId()%>"  class="input-block-level"><%--value="<%=DaoClass.getPlantId()%>"  onblur="validatePlantId();"--%>
            <%--
            <select id="counterNos" name="counterNos" onblur="getSelectedCounterName();" class="input-block-level">
                <option value="0" selected="0">Select</option>
            </select>
            --%>
            <input type="text" name="txtUname" autocomplete="off"  class="input-block-level"  placeholder="User Name"/>
            <input type="password" name="txtPassword" autocomplete="off" class="input-block-level"   placeholder="Password"/>
            <div class="row-fluid">
                <div class="span6">
                    <a href="javascript:void(0)" onclick="loadingUserForgotPassword();">Forgot Password?</a>
                </div>
                <div class="span6">
                    <input style="width: 110px;height: 32px;padding: 0px; font-size: 16px; font-weight: 600;margin-bottom: 0px;" type="submit" name="btnSubmit" value="Login"  class="btn btn-info pull-right"><%--onclick="checkLoginCounterType(event);"--%>
                </div>
            </div>
        </html:form>
    </div> 
</body>
</html>
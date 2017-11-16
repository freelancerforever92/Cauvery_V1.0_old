<%-- 
    Document   : admin-index
    Created on : Aug 22, 2014, 3:41:52 PM
    Author     : Pranesh
--%>
<head>
    <meta charset="utf-8">
    <title>Cauvery - Super Admin</title>
    <link rel="icon" href="../images/cauvery.jpg" rel="Cauvery" type="image/x-icon"/>

    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/custom_styles.css" />   
    <script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
    <script src="../js/jquery-admin-index.js" type="text/javascript"></script>
    <style>
        .prof{display: none;}
        .head-sec{background-color: #ffffff;}
    </style>

</head>
<body class="admin_login_bg">
    <%@include file="admin_header.jsp"%>
    <div class="admin_login">
        <h2><span>Cauvery POS</span> -  Admin Login </h2>
        <form id="superAdminForm" name="superAdminForm" action="superLoginAction" method="post">
            <input type="text" id="superUname" name="superUname" autocomplete="off"  class="input-block-level"  placeholder="User Name" >
            <input type="password" id="superPassword" name="superPassword" autocomplete="off" class="input-block-level" placeholder="Password" > 
            <div class="row-fluid">
                <div class="span6">
                    <a onclick="loadForgotPassword();" href="javascript:void(0)">Forgot Password?</a>
                </div>
                <div class="span6">
                    <input style="width: 110px;height: 32px;padding: 0px; font-size: 16px; font-weight: 600;margin-bottom: 0px;" type="submit" id="superLogin" name="superLogin" class="btn btn-info pull-right" value="Login">
                </div>
            </div>
        </form>
    </div> 
</body>
</html>

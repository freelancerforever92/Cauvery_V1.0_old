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
<!--    <script>
        function backToLogin()
            {
                window.history.back();
            }
        
    </script>-->
    
    
    
    

</head>
<body class="admin_login_bg">
    <%@include file="admin_header.jsp"%>
    <div class="admin_login">
        <h2>Change Password</h2>
        <form id="adminChangePwd" name="adminChangePwd">
            <input type="text" id="superUname" name="superUname" autocomplete="off"  class="input-block-level"  placeholder="User Name" >
            <input type="text" id="supernewpwd" name="supernewpwd" autocomplete="off"  class="input-block-level"  placeholder="New Password" >
            <input type="password" id="cpwd" name="cpwd" autocomplete="off" class="input-block-level" placeholder="Confirm Password" > 
            <div class="row-fluid">
                <div class="span5">
<!--                    <a href="javascript:void(0)" onclick="backToLogin();">&larr; Back to Login</a>-->
                </div>
                <div class="span7">
                    <input style="min-width: 110px;height: 32px;padding: 0px 5px; font-size: 16px; font-weight: 600;margin-bottom: 0px;" type="button" id="superLogin" onclick="changeadminPwd();" name="superLogin"  class="btn btn-primary pull-right" value="Submit">
                </div>
            </div>
        </form>
    </div> 
</body>
</html>

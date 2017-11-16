

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <%@taglib prefix="s" uri="/struts-tags" %>
        <%@taglib prefix="sj" uri="/struts-jquery-tags"%>
        <%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Cauvery - Super Admin</title>
         <link rel="icon" href="../images/cauvery.jpg" rel="Cauvery" type="image/x-icon"/>
        
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/custom_styles.css" />   
        <script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
        <script src="../js/jquery-admin-index.js" type="text/javascript"></script>
        <script>
            $(document).ready(function() {
                $("#manageUser").addClass("active");
            });
        </script>
        
        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
              <script src="<c:url value='../js/html5.js'/>"></script>
            <![endif]-->
<sj:head jquerytheme="start"/>
    </head>
    <body>
       <jsp:include page="commonJsp.jsp" /> 
        <div class="clearfix body-sec">

            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li class="active"><a href="index_admin.jsp">User List</a></li>
                    <li><a href="admin_create_user.jsp">Create User</a></li>
                    <li><a href="admin_edit_user.jsp">Change User</a></li>
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">
                <%@include file="admin-usermanage.jsp"%> 
            </div>
        </div>
    </body>
</html>
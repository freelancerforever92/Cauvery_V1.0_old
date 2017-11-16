

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cauvery - Super Admin</title>
        <link rel="icon" href="../images/cauvery.jpg" rel="Cauvery" type="image/x-icon"/>
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/custom_styles.css" />   
        <script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
        <script src="../js/jquery-admin-index.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function()
            {
                $("#manageUser").addClass("active");
                $(".userCounterOption").hide();

            });
        </script>
    </head>
    <body>
        <jsp:include page="commonJsp.jsp" /> 
        <div class="clearfix body-sec">
            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li><a href="index_admin.jsp">User List</a></li>
                    <li class="active"><a href="admin_create_user.jsp">Create User</a></li>
                    <li><a href="admin_edit_user.jsp">Change User</a></li>
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">
                <div id="newUserCreationDiv">                   
                    <div class="clearfix create-user">
                        <form id="userCreationForm" name="userCreationForm">                           
                            <div class="row-fluid">
                                <div class="span4">
                                    <label>First Name :</label>
                                </div>
                                <div class="span8">
                                    <input type="text" id="employeeFname" name="employeeFname" autocomplete="off" class="input-block-level"/>    
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span4">
                                    <label>Last Name :</label>
                                </div>
                                <div class="span8">
                                    <input type="text" id="employeeLname" name="employeeLname" autocomplete="off" class="input-block-level"/>
                                </div>
                            </div>               
                            <div class="row-fluid">
                                <div class="span4">
                                    <label>User Id :</label>
                                </div>
                                <div class="span8">
                                    <input type="text" id="employeePersonalNumber" name="employeePersonalNumber" class="input-block-level"/>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span4">
                                    <label>User Role :</label>
                                </div>
                                <div class="span8">                    
                                    <select id="userRole" class="input-block-level" name="userRole" onchange="userRole();">
                                        <option value="select">-Select Role-</option>
                                        <option value="salesCounter">Sales</option>
                                        <option value="cashCounter">Cash</option>
                                        <option value="tenderCounter">TenderCounter</option>
                                        <option value="all">Both</option>
                                    </select>
                                    <!--                                    <div class="userCounterOption">
                                                                            <div class="row-fluid">
                                                                                <div class="span6">
                                                                                    <label class="radio">
                                                                                        <span class="userCounterOption">
                                                                                            <input type="radio" id="employeeRoleOption" name="employeeRoleOption" value="salesCounter">SalesCounter
                                                                                        </span>
                                                                                    </label>
                                                                                </div>
                                                                                <div class="span6">
                                                                                    <label class="radio">
                                                                                        <span class="userCounterOption">
                                                                                            <input type="radio" id="employeeRoleOption" name="employeeRoleOption" value="cashCounter"/>CashCounter
                                                                                        </span>
                                                                                    </label>
                                                                                </div> 
                                                                            </div>
                                                                        </div>-->
                                </div>
                            </div>
                            <div>
                            </div>
                            <div class="row-fluid" style="padding-top: 10px;">
                                <div class="span4"></div>
                                <div class="span4"> <input style="width: 100px;" type="button" id="user_create_cancel_button" name="user_create_cancel_button" class="btn btn-warning " value="Cancel" onclick="clearAllValues();" /></div>

                                <div class="span4"><input style="width: 100px;" type="button" id="user_create_button" name="user_create_button" class="btn btn-info pull-right"  value="Create User" onclick="validNewUserInputs();"/></div>
                            </div> 
                        </form>
                    </div>                
                </div>

            </div>

        </div>
    </body>
</html>
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
                $("#userRes").hide();
                $("#userProgress").hide();

            });
            function chaneRolefun()
            {
                if ($("#userRoleOption").find("option:selected").val() !== "select")
                {
                    $("#emprole").val($("#userRoleOption").find("option:selected").val());
                }
                else {
                    $("#emprole").val($("#role").val());
                }

            }
        </script>
    </head>
    <body>
        <jsp:include page="commonJsp.jsp" /> 

        <div class="clearfix body-sec">

            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li><a href="index_admin.jsp">User List</a></li>
                    <li><a href="admin_create_user.jsp">Create User</a></li>
                    <li class="active"><a href="admin_edit_user.jsp">Change User</a></li>
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">
                <div class="row-fluid">
                    <div class="span4">
                        <label class="pull-right">User Id :</label>
                    </div>
                    <div class="span4">
                        <input type="text" id="empUserid" name="empUserid" class="input-block-level"/>
                    </div>
                    <div class="span4">
                        <div id="editUser" onclick="getUserInfo();" class="search pull-left">
                            <span class="icon-search icon-white"></span>
                        </div>
                    </div>
                </div>

                <div id="userProgress"> 
                    <p>Searching for User...</p>
                    <img alt="uploadingMaterial" src="bootstrap/img/progress_bar.gif"/></div>
                <div id="userRes">
                    <form id="userUpdationForm" name="userUpdationForm">
                        <div class="row-fluid dis-n">
                            <input type="text" id="empId" name="empId" class="input-block-level"/>
                        </div>
                        <div class="row-fluid">
                            <div class="span4">
                                <label>Name :</label>
                            </div>
                            <div class="span8">
                                <input type="text" id="empname" name="empname" autocomplete="off" class="input-block-level"/>    
                            </div>
                        </div>

                        <div class="row-fluid">
                            <div class="span4">
                                <label>User Role :</label>
                            </div>
                            <div class="span4">
                                <input type="hidden" id="role">
                                <input type="text" id="emprole" name="emprole" class="input-block-level" readonly/>
                            </div>
                            <div class="span4">                    
                                <select onchange="chaneRolefun();" id="userRoleOption" class="input-block-level" name="userRoleOption">
                                    <option value="select">-Select Role-</option>
                                    <option value="salesCounter">Sales</option>
                                    <option value="cashCounter">Cash</option>
                                    <option value="tenderCounter">TenderCounter</option>
                                    <option value="all">Both</option>
                                </select>                            
                            </div>
                        </div>
                        <div>
                        </div>
                        <div class="row-fluid" style="padding-top: 10px;"> 
                            <div class="span4"></div>
                            <div class="span4"> <input style="width: 100px;" type="button" id="user_update_cancel_button" name="user_create_cancel_button" class="btn btn-warning" value="Cancel" onclick="clearUserDetails();" /></div>
                            <div class="span4"><input style="width: 100px;" type="button" id="user_update_button" name="user_create_button" class="btn btn-primary pull-right"  value="Update" onclick="updateUserInputs();"/></div>
                        </div>  
                    </form>
                </div>


            </div>

        </div>
    </body>
</html>
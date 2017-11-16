
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html>
<html>
    <head>        
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">   
    </head>
    <body>
        <div class="navbar-fixed-top">
            <div class="clearfix head-sec">
                <div class="row-fluid">
                    <div class="span3"> <a href="#"><img src="../images/cauvery.jpg" alt="Admin" width="66" /></a> </div>
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
<!--                        <div class="clearfix pull-right">
                            Date Time  : <span id="lblTime" style=" font-weight:bold">

                            </span>
                        </div>-->
                    </div>
                </div>
            </div>
            <div class="clearfix pull-right">
                <div class="clearfix prof">
                    <ul class="clearfix list-s">
                        <li><i class="icon-user"></i> Admin</span>
                            <ul class="dropdown-menu"> 
                                <li><a href="javascript:void(0)" onclick="adminLogout();"><i class="icon-off"></i> Log Out</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>

            </div> 
        </div>
    </body>
</html>

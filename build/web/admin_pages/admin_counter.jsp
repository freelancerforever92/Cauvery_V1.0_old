<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cauvery - Super Admin</title>
        <link rel="icon" href="../images/cauvery.jpg" rel="Cauvery" type="image/x-icon"/>

        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/custom_styles.css" />   
        <script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
        <script src="../js/jquery-admin-index.js" type="text/javascript"></script>
        <sj:head jquerytheme="start"/>
        <script>
             $(document).ready(function() {
                $("#manageCounter").addClass("active");
            });
        </script>
    </head>
    <body>
       <jsp:include page="commonJsp.jsp" /> 
        <div class="clearfix body-sec">

            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li class="active"><a href="admin_counter.jsp">Counter List</a></li>
                    <li><a href="admin_create_counter.jsp">Create Counter</a></li>
                    <li><a href="admin_edit_counter.jsp">Change Counter Info</a></li>
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">

                <s:url id="countDetailUrl" action="counterDetailAction"></s:url>
                <sjg:grid
                    id="countdisplayList"
                    dataType="json"
                    href="%{countDetailUrl}"
                    loadonce="true"
                    pager="true"
                    gridModel="countdisplayList"
                    rowList="20,100"                   
                    rownumbers="true"
                    navigator="true"
                    navigatorSearch="false"                    
                    cssStyle="font-size:12px;"
                    draggable="false" 
                    hoverrows="false"
                    navigatorAdd="false"
                    navigatorDelete="false"
                    navigatorEdit="false"
                    navigatorRefresh="false"
                    sortable="true"
                    viewrecords="true"
                    shrinkToFit="false"
                    
                    height="400"
                    autowidth="true"                
                    >
                   <sjg:gridColumn name="counterName" width="300" index="counterName" title="Counter Name" />
                   <sjg:gridColumn name="counterId" width="180" index="counterId" align="center" title="Counter Id" />
                   <sjg:gridColumn name="counterLegacyNo" width="200" index="counterLegacyNo" align="center" title="Counter Legacy No" />
                   
                   <sjg:gridColumn name="counterCreatedDate" width="200" index="counterCreatedDate" align="center" title="Created Date" />
                   <sjg:gridColumn name="counterUpdatedDate" width="200" index="counterUpdatedDate" align="center" title="Updated Date" />
                </sjg:grid>
            </div>
        </div>
    </body>
</html>
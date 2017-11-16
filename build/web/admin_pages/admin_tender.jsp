

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
        <sj:head jquerytheme="start"/>
        <script>
            $(document).ready(function() {
                $("#manageTender").addClass("active");
            });
        </script>
    </head>
    <body>
        <jsp:include page="commonJsp.jsp" /> 

        <div class="clearfix body-sec">

            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li class="active"><a href="admin_tender.jsp">Tender List</a></li>
                    <li><a href="admin_create_tender.jsp">Upload Tender</a></li>
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">
                <s:url id="tendDetailUrl" action="tenderDetailaction"></s:url>
                <sjg:grid
                    id="matList"
                    dataType="json"
                    href="%{tendDetailUrl}"
                    loadonce="true"
                    pager="true"
                    gridModel="matList"
                    rowList="10,50,100,1000,4000,10000"                    
                    rownumbers="true"
                    navigator="true"
                    navigatorSearch="false"
                    onPagingTopics="grdcourseopt"
                    navigatorSearchOptions="{sopt:['eq']}"
                    cssStyle="font-size:12px;"
                    draggable="false" 
                    hoverrows="false"
                    navigatorAdd="false"
                    navigatorDelete="false"
                    navigatorEdit="false"
                    navigatorRefresh="false"
                    sortable="true"
                    viewrecords="true"
                    shrinkToFit="true"
                    height="400"                   
                    autowidth="true"                
                    >
                    <sjg:gridColumn name="matno" width="120" index="matno" align="center" title="Material No" />
                    <sjg:gridColumn name="matdes" width="225" index="plantId" title="Material Description" />
                    <sjg:gridColumn name="craftgroup" width="70" index="craftgroup" align="center" title="Craft Group" />
                    <sjg:gridColumn name="storageloc" width="60" align="center" index="storageloc" title="Storage Location"/>
                    <sjg:gridColumn name="vendor_id" width="100" align="center" index="vendor_id" title="Vendor Id"/>
                    <sjg:gridColumn name="qty" width="120" align="center" index="qty" title="Quantity"/>
                    <sjg:gridColumn name="value" width="60"  index="price" title="Standard Price"/>
                     <sjg:gridColumn name="text" width="60"  index="text" title="Description"/>
                     <sjg:gridColumn name="price" width="60"  index="price" title="Price"/>
                    <sjg:gridColumn name="createdDate" width="160" align="center" index="createdDate" title="Created Date"/>
                    <sjg:gridColumn name="updatedDate" width="160" align="center" index="updatedDate" title="Updated Date"/>
                </sjg:grid>
            </div>
        </div>
    </body>
</html>
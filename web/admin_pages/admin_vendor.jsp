

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
                $("#manageVendor").addClass("active");
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
                    <li class="active"><a href="admin_vendor.jsp">Vendor List</a></li>
                    <li><a href="admin_create_vendor.jsp">Upload Vendor</a></li>
                    <li><a href="admin_edit_vendor.jsp">Change Vendor</a></li>
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">
                <s:url id="vendorDetailUrl" action="vendorDetailaction"></s:url>
                <sjg:grid
                    id="vendorSec"
                    dataType="json"
                    href="%{vendorDetailUrl}"
                    loadonce="true"
                    pager="true"
                    gridModel="vendorList"
                    rowList="10,50,100,1000,4000"
                    rowNum="16"
                    rownumbers="true"
                    navigator="true"
                    navigatorSearch="true"
                    onPagingTopics="grdcourseopt"
                    navigatorSearchOptions="{sopt:['eq']}"
                    cssStyle="font-size:12px;"
                    draggable="false" 
                    hoverrows="false"
                    navigatorAdd="false"
                    navigatorDelete="false"
                    navigatorEdit="false"
                    navigatorRefresh="false"
                    sortable="false"
                    viewrecords="true"
                    shrinkToFit="false"
                    height="400"
                    autowidth="true"                
                    >
                    <sjg:gridColumn name="vendorId" width="70" index="vendorId" align="center" title="Vendor Id" />
                    <sjg:gridColumn name="vendorAccountGroup" width="90" index="vendorAccountGroup" align="center" title="Account Group" />
                    <sjg:gridColumn name="vendorTitle" width="60" index="vendorTitle" align="center" title="Title" />
                    <sjg:gridColumn name="vendorName" width="160" index="vendorName" title="Name" />
                    <sjg:gridColumn name="vendorName2" width="160" index="vendorName2"  title="Name2" />
                    <sjg:gridColumn name="vendorName3" width="60" index="vendorName3" align="center" title="Name3" />
                    <sjg:gridColumn name="vendorName4" width="60" index="vendorName4" align="center" title="Name4" />
                    <sjg:gridColumn name="vendorSearchTerm" width="100" index="vendorSearchTerm" align="center" title="Search Term" />
                    <sjg:gridColumn name="vendorAddress1" width="360" index="vendorAddress1" title="Address1" />
                    <sjg:gridColumn name="vendorAddress2" width="60" index="vendorAddress2" align="center" title="Address2" />
                    <sjg:gridColumn name="vendorAddress3" width="60" index="vendorAddress3" align="center" title="Address3" />
                    <sjg:gridColumn name="vendorAddress4" width="60" index="vendorAddress4" align="center" title="Address4" />
                    <sjg:gridColumn name="vendorCity" width="90" index="vendorCity" align="center" title="City" />
                    <sjg:gridColumn name="vendorPinCode" width="90" index="vendorPinCode" align="center" title="PinCode" />
                    <sjg:gridColumn name="vendorDistrict" width="90" index="vendorDistrict" align="center" title="District" />
                    <sjg:gridColumn name="vendorState" width="90" index="vendorState" align="center" title="State" />
                    <sjg:gridColumn name="vendorTelNo" width="110" index="vendorTelNo" align="center" title="Telephone No" />
                    <sjg:gridColumn name="vendorMobileNo" width="110" index="vendorMobileNo" align="center" title="Mobile No" />
                    <sjg:gridColumn name="vendorFaxNo" width="90" index="vendorFaxNo" align="center" title="Fax No" />
                    <sjg:gridColumn name="vendorEmailId" width="90" index="vendorEmailId" align="center" title="Email Id" />
                    <sjg:gridColumn name="vendorCreatedDate" width="180" index="vendorCreatedDate" align="center" title="Created Date" />
                    <sjg:gridColumn name="vendorUpdatedDate" width="180" index="vendorUpdatedDate" align="center" title="Updated Date" />
                </sjg:grid>

            </div>
        </div>
    </body>
</html>
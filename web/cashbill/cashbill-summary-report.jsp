<%-- 
    Document   : index
    Created on : Oct 31, 2014, 11:07:43 AM
    Author     : pranesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script>
            $(document).ready(function()
            {
                $("#userProgress").hide();
//                $("#cashBillGrid").width(($(window).width() - ($(window).width() * 0.15)));
//                $("#cashBillGrid").height($(window).height() - ($(window).height() * 0.20));
            });


        </script>
    </head>
    <body>
        <div id="cashBillGrid">
            <%--
            <input type="button" id="export" name="export" class="btn btn-success" value="Export to Excel" onclick="exportDailyReport();" style="margin-bottom: 5px;"/>
            --%>
            <input type="button" id="export" name="export" class="btn btn-danger" value="Back" style="margin-bottom: 5px;" onclick="load_CashBillSalesForm();"/>
            <s:url id="summaryGridUrl" action="getCashBillSummaryDetails">
                <%--<s:param name="gridParameter"><s:property value="cbToDate"/>#<s:property value="cbFromDate"/>#<s:property value="cashBillReportButton"/></s:param>--%>
                <s:param name="gridParameter"><s:property value="cbToDate"/>#<s:property value="cbFromDate"/>#<s:property value="reportPaymentType"/>#<s:property value="cashBillReportButton"/></s:param>
            </s:url>
            <sjg:grid
                id="gridCashSummaryview"
                dataType="json"
                href="%{summaryGridUrl}"
                loadonce="true"
                pager="true"
                gridModel="cashBillSummaryList"
                rowList="10,20,30,40"
                rowNum="100"
                rownumbers="true"
                navigator="true"
                navigatorSearch="true"
                onPagingTopics="grdqustopt"
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
                autowidth="false"
                height="400"
                onBeforeTopics="gtGCBe"
                width="1000"
                >
                <sjg:gridColumn name="cashBillNumber" width="120" title="CashBill Number" align="center" index="cashBillNumber"/>
                <sjg:gridColumn name="counterBillNumber" width="120" title="CounterBill Number" align="center" index="counterBillNumber"/>
                <sjg:gridColumn name="empId" width="80" title="Employee Id" align="center" index="empId"/>
                <sjg:gridColumn name="paymentType" width="100" title="Payment Type" align="center" index="paymentType"/>
                <sjg:gridColumn name="totalBillAmount" width="120"  formatter="integer" formatoptions="0.00" title="Bill Amount" align="right" index="totalBillAmount"/>
                <sjg:gridColumn name="createdDateTime" width="190" title="Created DateTime" align="center" index="createdDateTime"/>
                <sjg:gridColumn name="plantId" width="80" title="PlantId" align="center" index="plantId"/>
            </sjg:grid>
        </div>
            
    </body>
</html>

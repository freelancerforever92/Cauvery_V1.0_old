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
                $("#CounterBillGridItem").width(($(window).width() - ($(window).width() * 0.15)));
                $("#CounterBillGridItem").height($(window).height() - ($(window).height() * 0.20));
            });
        </script>
    </head>
    <body>
        <div id="CounterBillGridItem" style="margin-top:-500px;">
            <%--
            <input type="button" id="export" name="export" class="btn btn-success" value="Export to Excel" onclick="exportDailyReport();" style="margin-bottom: 5px;"/>
            --%>
            <input type="button" id="export" name="export" class="btn btn-danger" value="Back" style="margin-bottom: 5px;" onclick="load_CounterBillForm();"/>
            <s:url id="counterSummaryGridUrl" action="getCounterBillSummaryDetails">
                <s:param name="counterGridParameter"><s:property value="counterFromDate"/>#<s:property value="counterToDate"/>#<s:property value="counterReportButton"/>#<s:property value="selectedFlag"/></s:param>
            </s:url>
            <sjg:grid
                id="gridCounterSummaryView"
                dataType="json"
                href="%{counterSummaryGridUrl}"
                loadonce="true"
                pager="true"
                gridModel="counterBillSummaryData"
                rowList="10,20,30,40"
                rowNum="100"
                rownumbers="true"
                navigator="true"
                onPagingTopics="grdqustopt"
                navigatorSearchOptions="{sopt:['eq']}"
                cssStyle="font-size:12px;"
                draggable="false" 
                hoverrows="false"
                navigatorAdd="false"
                navigatorEdit="false"
                navigatorDelete="false"
                navigatorRefresh="false"
                navigatorSearch="false"
                sortable="true"
                viewrecords="true"
                shrinkToFit="false"
                autowidth="false"
                height="400"
                onBeforeTopics="gtGCBe"
                width="1000"
                >
                <sjg:gridColumn name="salesOrderNumber" width="90" title="CounterBill No" align="left" index="salesOrderNumber"/>
                <sjg:gridColumn name="manualBillNumber" width="90" title="ManualBill No" align="left" index="manualBillNumber"/>
                <sjg:gridColumn name="itemValue" width="60" title="Item No" align="center" index="itemValue"/>
                <sjg:gridColumn name="materialNumber" width="140" title="Material Id" align="center" index="materialNumber"/>
                <sjg:gridColumn name="materialDescription" width="200" title="Material Description" align="left" index="materialDescription"/>
                <sjg:gridColumn name="materialCraftGroup" width="100" title="Craft Group" align="center" index="materialCraftGroup"/>
                <sjg:gridColumn name="quantity" width="70"  formatter="integer" formatoptions="0.00" title="Quantity" align="right" index="quantity"/>                
                <sjg:gridColumn name="calculatedValue" width="80"  formatter="integer" formatoptions="0.00" title="Bill Amt" align="right" index="calculatedValue"/>
                <sjg:gridColumn name="employeeId" width="70" title="EmpId" align="center" index="employeeId"/>
                <sjg:gridColumn name="showRoomId" width="70" title="PlantId" align="center" index="showRoomId"/>
                <sjg:gridColumn name="discountPercentage" width="80"  formatter="integer" formatoptions="0.00" title="Discount %" align="right" index="discountPercentage"/>
                <sjg:gridColumn name="discountValue" width="90"  formatter="integer" formatoptions="0.00" title="Discount Value" align="right" index="discountValue"/>
                <sjg:gridColumn name="vatPercentage" width="80"  formatter="integer" formatoptions="0.00" title="Vat %" align="right" index="vatPercentage"/>
                <sjg:gridColumn name="vatValue" width="90"  formatter="integer" formatoptions="0.00" title="Vat Value" align="right" index="vatValue"/>
                <sjg:gridColumn name="packingCharge" width="90"  formatter="integer" formatoptions="0.00" title="Packing Charge" align="right" index="packingCharge"/>
                <sjg:gridColumn name="dateTime" width="140" title="Created DateTime" align="center" index="dateTime"/>
            </sjg:grid>
        </div>

    </body>
</html>

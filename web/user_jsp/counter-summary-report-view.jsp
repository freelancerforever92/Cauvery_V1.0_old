<%-- 
    Document   : counter-summary-report-view
    Created on : Jan 29, 2015, 4:09:09 PM
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
            <input type="button" id="export" name="export" class="btn btn-danger" value="Back" style="margin-bottom: 5px;" onclick="load_CounterBillForm();"/>
            <b style="margin-left: 250px;">COUNTER WISE SALES SUMMARY REPORT [ CASH & CARD ] </b>
            <s:url id="counterSummaryGridView1" action="CraftWiseCounterReportView">
                <s:param name="counterGridParameter"><s:property value="counterFromDate"/>#<s:property value="counterToDate"/>#<s:property value="counterReportButton"/>#<s:property value="selectedFlag"/></s:param>
            </s:url>
            <sjg:grid
                id="counterSummaryGridView1"
                dataType="json"
                href="%{counterSummaryGridView1}"
                loadonce="true"
                pager="true"
                gridModel="counterBillSummaryViewData"
                rowList="10,20,30,40"
                rowNum="100"
                navigator="true"
                onPagingTopics="grdqustopt"
                navigatorSearchOptions="{sopt:['eq']}"
                cssStyle="font-size:12px;"
                draggable="false" 
                hoverrows="false"
                rownumbers="false"
                navigatorAdd="false"
                navigatorEdit="false"
                navigatorSearch="false"
                navigatorDelete="false"
                navigatorRefresh="false"
                sortable="false"
                viewrecords="true"
                shrinkToFit="false"
                autowidth="false"
                height="400"
                onBeforeTopics="gtGCBe"
                >
                <sjg:gridColumn name="counterno" dataType="Long" width="90" title="Counter No" align="left" sortable="false" index="counterno"/>
               <%--<sjg:gridColumn name="counter" width="160" title="Counter" align="center" sortable="false"index="counter"/>--%>
                <sjg:gridColumn name="grossamount" dataType="Long" formatter="integer" width="100" title="Gross Amount" align="right" sortable="false" index="grossamount"/>
                <sjg:gridColumn name="discamount" dataType="Long" formatter="integer" width="100" title="Disc Amount" align="right" sortable="false" index="discamount"/>
                <sjg:gridColumn name="netamount" dataType="Long" formatter="integer" width="100" title="Net Amount" align="right" sortable="false" index="netamount "/>
                <sjg:gridColumn name="vatamount" dataType="Long" formatter="integer" width="100" title="VAT Amount" align="right" sortable="false" index="vatamount"/>
                <sjg:gridColumn name="packamount" dataType="Long" formatter="integer" width="100" title="Pack Amount" align="right" sortable="false" index="packamount"/>
                <sjg:gridColumn name="totalsales" dataType="Long" formatter="integer" width="100" title="Sales Amount" align="right" sortable="false" index="totalsales"/>
            </sjg:grid>
        </div>

    </body>
</html>


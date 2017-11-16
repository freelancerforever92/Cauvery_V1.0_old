<%@page import="com.pos.updated.reports.UpdatedDcsrEmpWise"%>
<%@page import="com.Action.CashBill.DcsrEmpWise"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.to.CashHeaderDCSR"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.to.CashHeaderDTO"%>
<%@page import="com.Action.CashBill.CashDailySummaryView" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
<link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.css'/>" type="text/css" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            function exportToexcel(e)
            {
                window.open('data:application/vnd.ms-excel,' + encodeURIComponent($('#shwDCSARE').html()));
                e.preventDefault();
            }
            /*$(function() {
             $("#btnExport").click(function(e) {
             window.open('data:application/vnd.ms-excel,' + encodeURIComponent($('#shwDCSARE').html()));
             e.preventDefault();
             });
             });*/

            $(document).ready(function() {
                $("#userProgress").hide();
                $("#shwDCSARE").width(($(window).width() - ($(window).width() * 0.24)));
                $("#shwDCSARE").height($(window).height() - ($(window).height() * 0.30));
            });
        </script>
        <style>
            .UCRBport #shwDCSARE{min-width: 1050px!important;max-height:530px!important; }
        </style>
    </head>
    <%
        String dcsrReportAbsEmpFromDate = "";
        String dcsrReportAbsEmpToDate = "";
        Date todaydate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-YYYY");
        request.setAttribute("TDate", ft.format(todaydate));
        SimpleDateFormat saveFt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        request.setAttribute("saveFileName", saveFt.format(todaydate));
        String toDate = request.getParameter("cbToDate").trim();
        String[] SplitToDate = toDate.split("-");
        String fromDate = request.getParameter("cbFromDate").trim();
       // String vendorIds = request.getParameter("vendorIds").trim();
        //String vendortype = request.getParameter("vendorType").trim();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date tempFromDate = simpleDateFormat.parse(fromDate);
            Date tempToDate = simpleDateFormat.parse(toDate);

            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-YYYY");
            dcsrReportAbsEmpFromDate = outputDateFormat.format(tempFromDate);
            dcsrReportAbsEmpToDate = outputDateFormat.format(tempToDate);
        } catch (Exception ex) {
            System.out.println("Parse Exception");
        }

        String paymentType = request.getParameter("reportPaymentType").trim();
        String sessionId = request.getParameter("loggedSessionId").trim();
        request.setAttribute("toDate", toDate);
        request.setAttribute("SplitToDateLong", SplitToDate[2]);
        if (!((fromDate.equals("")) || (fromDate.equalsIgnoreCase(null)))) {
            if (!((toDate.equals("")) || (toDate.equalsIgnoreCase(null)))) {
                if (!((paymentType.equals("")) || (paymentType.equalsIgnoreCase(null)))) {
                    if (!((sessionId.equals("")) || (sessionId.equalsIgnoreCase(null)))) {
                       // request.setAttribute("header", DcsrEmpWise.displayCashDailySummaryAbstractEmployeeReportDCSRAE(fromDate, toDate, paymentType, sessionId, vendorIds, vendortype));
                        request.setAttribute("header", UpdatedDcsrEmpWise.updatedDcsrEmpWiseNewReport(fromDate, toDate, paymentType, sessionId));
                    }
                }
            }
        }
        int dcsrEMPAbsSNo = 1;
    %>
    <body>
        
        <div class="clearfix UCRBport">
            <input type="button" id="dcsraeBck" name="dcsraeBck" class="btn btn-danger" value="Back" style="margin-bottom: 5px;" onclick="load_CashBillSalesForm();"/>
            <div id="shwDCSARE" style="overflow: scroll;">
                <display:table name="requestScope.header" class="table table-striped display-table"  export="true" sort="list" id="head">
                    <display:setProperty name="export.excel" value="true" />
                    <display:setProperty name="export.excel.filename" value="DCSR-Employeewise-Abstract-${param.reportPaymentType}-${saveFileName}.xls"/>
                    <display:setProperty name="export.pdf" value="true" />
                    <display:setProperty name="export.pdf.filename" value="DCSR-Employeewise-Abstract-${param.reportPaymentType}-${saveFileName}.pdf"/>
                    <display:setProperty name="export.rtf" value="false" />
                    <display:setProperty name="export.rtf.filename" value="DCSR-Employeewise-Abstract-${param.reportPaymentType}-${saveFileName}.rtf"/>
                    <display:setProperty name="export.csv" value="false" />
                    <display:setProperty name="export.csv.filename" value="DCSR-Employeewise-Abstract-${param.reportPaymentType}-${saveFileName}.csv"/>
                    <display:caption media="html">CAUVERY<br>Karnataka State Arts & Crafts Emporium,No 49,MG Road,Bengaluru <br> DCSR Abstract Employee Wise FROM <b> <%=dcsrReportAbsEmpFromDate%> to <%=dcsrReportAbsEmpToDate%></b> <br> Pay-Type :<b> ${param.reportPaymentType} </b> Generated Date  :<b> <c:out value="${TDate}"  /></b> </display:caption>
                    <display:caption media="excel pdf rtf">CAUVERY                                                                                                                              Karnataka State Arts & Crafts Emporium, No 49,MG Road,Bengaluru                                   DCSR Abstract Employee Wise FROM  <%=dcsrReportAbsEmpFromDate%> to <%=dcsrReportAbsEmpToDate%>                                                    Pay-Type  : ${param.reportPaymentType}  Generated Date  : <c:out value="${TDate}"  /> </display:caption>
                     <c:if test="${head.empName !='Sub-total' }">
                        <c:if test="${head.empName !='Grand-Total' }">
                            <display:column value="<%=dcsrEMPAbsSNo++%>" title="S.No" />    
                        </c:if>
                    </c:if>
                    <display:column property="empName" title="Emp Name" class="myrow" group="1"/>   
                    <display:column property="dcsrDate" title=" Date "/>
                    <display:column property="grossAmountFloat" title="Gross Amt"  format="{0,number,0.00} "  total="true"/>
                    <display:column property="discAmountFloat" title="Dis Amt"  format="{0,number,0.00} "  total="true"/>
                    <display:column property="netAmountFloat" title="Net Amt"  format="{0,number,0.00} "  total="true"/>
                    <display:column property="vatAmountFloat" title="VAT Amt"  format="{0,number,0.00} "  total="true"/>
                    <display:column property="packAmountFloat" title="Pkg Amt"  format="{0,number,0.00} "  total="true"/>
                    <display:column property="cashBillAmountFloat" title="Total Amt"  format="{0,number,0.00} "  total="true"/>
                    <display:footer media="html">
                        <td colspan="9">COUNTER SUMMARY FOR <%=dcsrEMPAbsSNo - 1%> INVOICES</td>
                    </display:footer>
                    <display:footer media="excel pdf rtf">
                        COUNTER SUMMARY FOR <%=dcsrEMPAbsSNo - 1%> INVOICES 
                    </display:footer>
                </display:table>
                <input class="btnPrintDisplay" type="button" onclick="printDiv('shwDCSARE')" value="PRINT" />
            </div>
        </div>
    </body>

</html>

<%-- 
    Document   : cashbill-summary-daily-report-view
    Created on : Jan 1, 2015, 12:59:12 PM
    Author     : pranesh
--%>
<%@page import="com.pos.updated.reports.UpdatedCasherWise"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.to.CashHeaderDTO"%>
<%@page import="com.Action.CashBill.CashDailySummaryView" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
<link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.css'/>" type="text/css" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            $(document).ready(function() {
                $("#userProgress").hide();
                $("#shwCSDRV").width(($(window).width() - ($(window).width() * 1.34)));
                $("#shwCSDRV").height($(window).height() - ($(window).height() * 0.30));
                //getRunDateTime();
            });
            $.getJSON('SessionName.action', function(data) {
                if (data.logusrtype !== "all") {
                    $(".cwsr-Export").hide();
                } else if (data.logusrtype === "all") {
                    $(".cwsr-Export").show();
                }
            });
            function exportCWSR() {
                window.open('data:application/vnd.ms-excel,' + encodeURIComponent($('#shwCSDRV').html()));
                e.preventDefault();
            }
        </script>
        <style>
            .cwsrreport #shwCSDRV{min-width: 1050px!important;max-height:540px!important; }
        </style>
    </head>
    <%
        String dcsrReportViewFromDate = "";
        String dcsrReportViewtToDate = "";
        String toDate = request.getParameter("cbToDate").trim();
        String fromDate = request.getParameter("cbFromDate").trim();

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date tempFromDate = simpleDateFormat.parse(fromDate);
            Date tempToDate = simpleDateFormat.parse(toDate);

            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-YYYY");
            dcsrReportViewFromDate = outputDateFormat.format(tempFromDate);
            dcsrReportViewtToDate = outputDateFormat.format(tempToDate);
        } catch (Exception ex) {
            System.out.println("Parse Exception");
        }

        String paymentType = request.getParameter("reportPaymentType").trim();
        String sessionId = request.getParameter("loggedSessionId").trim();
        Date todaydate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-YYYY");
        
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String runDate = day + "-" + month + "-" + year;
        request.setAttribute("TDate", runDate);
        
        //request.setAttribute("TDate", ft.format(todaydate));
        SimpleDateFormat saveFt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        request.setAttribute("saveFileName", saveFt.format(todaydate));
        if (!((fromDate.equals("")) || (fromDate.equalsIgnoreCase(null)))) {
            if (!((toDate.equals("")) || (toDate.equalsIgnoreCase(null)))) {
                if (!((paymentType.equals("")) || (paymentType.equalsIgnoreCase(null)))) {
                    if (!((sessionId.equals("")) || (sessionId.equalsIgnoreCase(null)))) {
                       // List cwsrList = CashDailySummaryView.displayCashDailySummaryReport(fromDate, toDate, paymentType, sessionId);
                        List cwsrList = UpdatedCasherWise.updatedEmpWiseNewReport(fromDate, toDate, paymentType, sessionId);
                        request.setAttribute("header", cwsrList);
                    }
                }
            }
        }
        int cwsrSNO = 1;
    %>
    <body>
        <div class="clearfix cwsrreport">
            <input type="button" id="cwsrBck" name="cwsrBck" class="btn btn-danger" value="Back" style="margin-bottom: 5px;" onclick="load_CashBillSalesForm();"/>
            <div id="shwCSDRV" style="overflow: scroll;">
                <display:table name="requestScope.header"  class="table table-striped display-table" export="true" sort="list" id="head"  >
                    <display:setProperty name="export.excel" value="true" />
                    <display:setProperty name="export.excel.filename" value="Cashierwise-Summary-Report-${param.reportPaymentType}-${saveFileName}.xls"/>
                    <display:setProperty name="export.pdf" value="true" />
                    <display:setProperty name="export.pdf.filename" value="Cashierwise-Summary-Report-${param.reportPaymentType}-${saveFileName}.pdf"/>
                    <display:setProperty name="export.rtf" value="false" />
                    <display:setProperty name="export.rtf.filename" value="Cashierwise-Summary-Report-${param.reportPaymentType}-${saveFileName}.rtf"/>
                    <display:setProperty name="export.csv" value="false" />
                    <display:setProperty name="export.csv.filename" value="Cashierwise-Summary-Report-${param.reportPaymentType}-${saveFileName}.csv"/>
                    <display:caption media="html">CAUVERY<br>Karnataka State Arts & Crafts Emporium,No 49,MG Road,Bengaluru <br> CASHIER WISE SUMMARY REPORT FROM  <b><%=dcsrReportViewFromDate%> to <%=dcsrReportViewtToDate%></b> <br/> Pay-Type :<b>${param.reportPaymentType}</b>   Generated Date : <b>${TDate}</b> </display:caption>
                    <display:caption media="excel pdf rtf">CAUVERY                                                                                                                              Karnataka State Arts & Crafts Emporium, No 49,MG Road,Bengaluru                                   CASHIER WISE SUMMARY REPORT FROM  <%=dcsrReportViewFromDate%> to <%=dcsrReportViewtToDate%>                                 Pay-Type : ${param.reportPaymentType} Generated Date  : ${TDate} </display:caption>
                   <c:if test="${head.counterNoName !='Sub-Total' }">
                        <c:if test="${head.counterNoName !='Grand-Total' }">
                            <display:column value="<%=cwsrSNO++%>" title="S.No" />    
                        </c:if>
                    </c:if>
                    <display:column property="counterNoName" title="          " group="1"/>
                    <display:column property="craftGroupName" title="CRAFT-GROUP"/>
                    <display:column property="cashInvoiceNumber" title="INVOICE-NO"/>                    
                    <display:column property="manualBillNumber" title="MANUALBILL-NO" />
                 <%-- <display:column property="cashBillAmountWROFloat"  title="Bill Amt WROF"  format="{0,number,0.00} "/> --%>
                    <display:column property="cashBillAmountFloat"  title="Bill Amt"  format="{0,number,0.00} "/>
                    <display:column property="processedDate" title="DATE"/>
                    <display:footer media="html">
                        <td colspan="6">COUNTER SUMMARY FOR <%=cwsrSNO - 1%> INVOICES</td>
                    </display:footer>
                    <display:footer media="excel pdf rtf">
                        COUNTER SUMMARY FOR <%=cwsrSNO - 1%> INVOICES 
                    </display:footer>
                </display:table>
                <input class="btnPrintDisplay" type="button" onclick="printDiv('shwCSDRV')" value="PRINT" />
            </div>
        </div>
    </body>
</html>

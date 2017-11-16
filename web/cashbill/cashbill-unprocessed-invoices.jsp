<%-- 
    Document   : cashbill-unprocessed-invoices
    Created on : Apr 3, 2015, 4:51:38 PM
    Author     : pranesh
--%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.Action.CashBill.UnprocessedInvoiceCC"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
<link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.css'/>" type="text/css" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            $(document).ready(function() {
                $("#userProgress").hide();
//                $("#displayUnProcessedInvoices").width(($(window).width() - ($(window).width() * 0.24)));
//                $("#displayUnProcessedInvoices").height($(window).height() - ($(window).height() * 0.30));
            });
            $.getJSON('SessionName.action', function(data) {
                if (data.logusrtype !== "all") {
                    $(".upi-Export").hide();
                } else if (data.logusrtype === "all") {
                    $(".upi-Export").show();
                }
            });
        </script>
        <style>
            .UCRBport #displayUnProcessedInvoices{min-width: 1050px!important;max-height:530px!important; }
        </style>
    </head>

    <%
        String ucbReportFromDate = "";
        String ucbReportToDate = "";
        String upiToDate = request.getParameter("cbToDate").trim();
        String upiFromDate = request.getParameter("cbFromDate").trim();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date tempFromDate = simpleDateFormat.parse(upiFromDate);
            Date tempToDate = simpleDateFormat.parse(upiToDate);

            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-YYYY");
            ucbReportFromDate = outputDateFormat.format(tempFromDate);
            ucbReportToDate = outputDateFormat.format(tempToDate);
        } catch (Exception ex) {
            System.out.println("Parse Exception");
        }
        Date todaydate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-YYYY");
        
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String todate = day + "-" + month + "-" + year;
        request.setAttribute("TDate", todate);
        
        SimpleDateFormat saveFt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        //request.setAttribute("saveFileName", saveFt.format(todate));
        request.setAttribute("saveFileName", todate);
        if (!((upiFromDate.equals("")) || (upiFromDate.equalsIgnoreCase(null)))) {
            if (!((upiToDate.equals("")) || (upiToDate.equalsIgnoreCase(null)))) {
                List unProcessedInvoiceList = UnprocessedInvoiceCC.displayUncollectedInvoices(upiFromDate, upiToDate);
                request.setAttribute("invoiceList", unProcessedInvoiceList);
            }
        }
        int ucbSNO = 1;
    %>
    <body>
        <div class="clearfix UCRBport">
            <input type="button" id="ucbBck" name="ucbBck" class="btn btn-danger" value="Back" style="margin-bottom: 5px;" onclick="load_CashBillSalesForm();"/>
            <div id="displayUnProcessedInvoices" style="overflow: scroll;">
                <display:table name="requestScope.invoiceList" class="table table-striped display-table" export="true" sort="list" id="head"  >
                    <display:setProperty name="export.excel" value="true" />
                    <display:setProperty name="export.excel.filename" value="Un-Collected-Invoices-${saveFileName}.xls"/>

                    <display:setProperty name="export.pdf" value="true" />
                    <display:setProperty name="export.pdf.filename" value="Un-Collected-Invoices-${saveFileName}.pdf"/>

                    <display:setProperty name="export.rtf" value="false" />
                    <display:setProperty name="export.rtf.filename" value="Un-Collected-Invoices-${saveFileName}.rtf"/>

                    <display:setProperty name="export.csv" value="false" />
                    <display:setProperty name="export.csv.filename" value="Un-Collected-Invoices-${saveFileName}.csv"/>
                    <display:caption media="html">CAUVERY<br>Karnataka State Arts & Crafts Emporium,No 49,MG Road,Bengaluru <br> UN-COLLECTED INVOICES FROM  <b><%=ucbReportFromDate%> to <%=ucbReportToDate%></b> <br>   Generated Date  : <b>${TDate}</b> </display:caption>
                    <display:caption media="excel pdf rtf">CAUVERY                                                                                                                              Karnataka State Arts & Crafts Emporium, No 49,MG Road,Bengaluru                                   UN-COLLECTED INVOICES FROM  <%=ucbReportFromDate%> to <%=ucbReportToDate%>                                                      Generated Date  : ${TDate} </display:caption>
                    <c:if test="${(head.craftGroup !='GRAND - TOTAL') && (!fn:contains(head.craftGroup, 'Sub-Total'))}">
                        <display:column value="<%=ucbSNO++%>" title="S.No" />    
                    </c:if>
                    <display:column property="craftGroup" title="CraftGroup" group="1"/>
                    <display:column property="invoiceNumber" title="INVOICE-NO"/>
                    <%-- <display:column property="manualBilNumber" title="MANUALBILL-NO" />--%>
                    <display:column property="grossAmt" title="Gross-Amt" format="{0,number,0.00} "/>
                    <display:column property="disAmt" title="Disc-Amt" format="{0,number,0.00} "/>
                    <display:column property="netAmt" title="Net-Amt" format="{0,number,0.00} "/>
                    <display:column property="vatAmt" title="VAT-Amt" format="{0,number,0.00} "/>

                    <display:column property="packAmt" title="Pck-Amt" format="{0,number,0.00} "/>
                    <display:column property="totalAmt" title="Total-Amt" format="{0,number,0.00} "/>
                    <display:column property="dateTimeValue" title=" DateTime"/>
                    <display:column property="counterUserName" title="ProcessedBy" />
                    <display:footer media="html">
                        <td colspan="11">COUNTER SUMMARY FOR <%=ucbSNO - 1%> INVOICES</td>
                    </display:footer>
                    <display:footer media="excel pdf rtf">
                        COUNTER SUMMARY FOR <%=ucbSNO - 1%> INVOICES 
                    </display:footer>
                </display:table>
                <input class="btnPrintDisplay" type="button" onclick="printDiv('displayUnProcessedInvoices')" value="PRINT" />
            </div>
        </div>     
    </body>
</html>

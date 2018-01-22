<%@page import="com.pos.updated.reports.UpdatedCounterWise"%>
<%@page import="com.Action.CashBill.DcsrCounterWise"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<script type="text/javascript">
    $(document).ready(function() {
        $("#userProgress").hide();
        $("#displaySisSummary").width(($(window).width() - ($(window).width() * 0.24)));
        $("#displaySisSummary").height($(window).height() - ($(window).height() * 0.30));

        var itm = document.getElementsByClassName("exportlinks");
        var cln = itm.cloneNode(true);
        document.getElementById("exportClone").appendChild(cln);

    });
</script>
<style>
    .shwDCWSR #shwDCWSR{min-width: 1050px!important;max-height:530px!important; }
</style>
<%
    String reportingSummaryFromDate = "";
    String reportingSummaryToDate = "";
    Date todaydate = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("dd-MM-YYYY");
    request.setAttribute("TDate", ft.format(todaydate));
    SimpleDateFormat saveFt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    request.setAttribute("saveFileName", saveFt.format(todaydate));
    String toDate = request.getParameter("cbToDate").trim();
    String fromDate = request.getParameter("cbFromDate").trim();
    //String intimationName = request.getParameter("intimationName").trim().toUpperCase();

    try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tempFromDate = simpleDateFormat.parse(fromDate);
        Date tempToDate = simpleDateFormat.parse(toDate);

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-YYYY");
        reportingSummaryFromDate = outputDateFormat.format(tempFromDate);
        reportingSummaryToDate = outputDateFormat.format(tempToDate);
    } catch (Exception ex) {
        System.out.println("Parse Exception");
    }
    String paymentType = request.getParameter("reportPaymentType").trim();
    String sessionId = request.getParameter("loggedSessionId").trim();
    //String typeValue = request.getParameter("typeValue").trim();
    String craftGroups = request.getParameter("craftGroups").trim();
    //String vendorIds = request.getParameter("vendorIds").trim();

    if (!((fromDate.equals("")) || (fromDate.equalsIgnoreCase(null)))) {
        if (!((toDate.equals("")) || (toDate.equalsIgnoreCase(null)))) {
            if (!((paymentType.equals("")) || (paymentType.equalsIgnoreCase(null)))) {
                if (!((sessionId.equals("")) || (sessionId.equalsIgnoreCase(null)))) {
                    //if (!((typeValue.equals("")) || (typeValue.equalsIgnoreCase(null)))) {
                    // request.setAttribute("header", DcsrCounterWise.DcsrCounterWiseReport(fromDate, toDate, paymentType, sessionId, craftGroups));
                    request.setAttribute("header", UpdatedCounterWise.newUpdatedCounterWiseReportPos(fromDate, toDate, paymentType, sessionId, craftGroups));
                    //   }
                }
            }
        }
    }
%>

<div class="clearfix shwDCWSR">
    <input type="button" id="dsctSISBackButton" name="dsctSISBackButton" class="btn btn-danger" value="Back" style="margin-bottom: 5px;" onclick="load_CashBillSalesForm();"/>
    <div id="exportClone"/>
    <div id="shwDCWSR" style="overflow: scroll;">
        <display:table name="requestScope.header"  style="margin:10px;margin-top:15px"  class="table table-striped display-table" export="true" sort="list" id="head"  >
            <display:setProperty name="export.excel" value="true"/>
            <display:setProperty name="export.excel.filename" value="${param.reportPaymentType}-${saveFileName}.xls"/>
            <display:setProperty name="export.pdf" value="true" />
            <display:setProperty name="export.pdf.filename" value="${param.reportPaymentType}-${saveFileName}.pdf"/>
            <display:setProperty name="export.rtf" value="false" />
            <display:setProperty name="export.rtf.filename" value="${param.reportPaymentType}-${saveFileName}.rtf"/>
            <display:setProperty name="export.csv" value="false" />
            <display:setProperty name="export.csv.filename" value="${param.reportPaymentType}-${saveFileName}.csv"/>
            <display:caption media="html">CAUVERY<br>Karnataka State Arts & Crafts Emporium,No 49,MG Road,Bengaluru <br> COUNTER WISE FROM <b> <%=fromDate%> to <%=toDate%></b> <br> Pay-Type : <b>${param.reportPaymentType}</b>  Run Date  : <b><c:out value="${TDate}"  /></b> </display:caption>
            <display:caption media="excel pdf rtf">CAUVERY                                                                                                                              Karnataka State Arts & Crafts Emporium, No 49,MG Road,Bengaluru                                   DCSR FROM  <%=fromDate%> to <%=toDate%>                                                                                              Pay-Type : ${param.reportPaymentType}  Generated Date : <c:out value="${TDate}"  /> </display:caption>

            <display:column property="dcsrDate" title="Counter-Name"/>
            <display:column property="grossAmountFloat" title="Gross Amt" format="{0,number,0.00} "   />
            <display:column property="discAmountFloat" title="Dis Amt"  format="{0,number,0.00} " />
            <display:column property="netAmountFloat" title="Net Amt"  format="{0,number,0.00} "  />

            <c:choose>
                 <c:when test="${head.fromDD le 30 && head.fromYY le 2017 &&( head.fromMM le 6 || head.fromMM le 06 )}">
                    <display:column property="vatAmountFloat" title="VAT Amt"  format="{0,number,0.00} "  />
                </c:when>
                <c:otherwise>
                    <display:column property="sgstAmountFloat" title="SGST Amt"  format="{0,number,0.00} "  />
                    <display:column property="cgstAmountFloat" title="CGST Amt"  format="{0,number,0.00} "  />
                </c:otherwise>
            </c:choose>

            <display:column property="packAmountFloat" title="Pkg Amt"  format="{0,number,0.00} " />
            <display:column property="cashBillAmountFloat" title="Total Amt"  format="{0,number,0.00} " />

        </display:table>
        <input class="btnPrintDisplay" type="button" onclick="printDiv('shwDCWSR')" value="PRINT" />
    </div>
</div>
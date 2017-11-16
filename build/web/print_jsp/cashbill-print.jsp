<%@page import="com.to.PurchaseOrderHeaderTo"%>
<%@page import="com.to.CashBillHeaderTo"%>
<%@page import="com.model.GenericModule"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
<link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/style.css'/>"
<script src="../js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="../js/jquery-latest.min.js" type="text/javascript"></script>
<script src="../js/jquery-barcode.js" type="text/javascript"></script>
<script src="../js/jquery-barcode.min.js" type="text/javascript"></script>
<script src="../js/prototype.min.js" type="text/javascript"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <script language="javascript" type="text/javascript">
            $(document).ready(function()
            {
                var salesOrderNumber = $("#hiddenCashBillOrderNumber").val().trim();
                $("#cashBillBarCode").barcode(salesOrderNumber, "codabar", {barWidth: 2, barHeight: 30});
            });
            window.print();
        </script>
    </head>

    <%
        String cashBillNo = request.getParameter("cashBillNo");
        CashBillHeaderTo headerTo = GenericModule.getCashBillOrderPrint(cashBillNo);
        request.setAttribute("header", headerTo);
    %>

    <body>
        <input type="text" id="hiddenCashBillOrderNumber" name="hiddenCashBillOrderNumber" value="${requestScope.header.salesOrderNo}" style="display: none"/>
        <title style="display: none;">Cash Bill - ${requestScope.header.salesOrderNo}</title>
        <div id="printDivID">
            <table border="0px" style="width:1020px; border: 1px;"  cellspacing="0px" cellpadding="0px">
                <thead>
                    <tr>
                        <td width="25%">
                            <img src="../images/cauvery.jpg" height="110" width="60" style="float: left;" />
                        </td>
                        <td style="text-align: center;position: inherit;margin-left: 850px;">
                            <p style="font-size: 18px;float: right;margin-top: -20px;margin-right: -280px;text-align: left;">
                                Grams   : HANDICRAFTS<br>
                                Phone   : 91-80-25581118<br>
                                Fax     : 91-80-25325491<br>
                                Email   : cauveryempmg@vsnl.net<br>
                                Website : www.cauverycrafts.com<br>
                                TIN NO  : 29520088675<br>
                                CST NO  : 00551697 [06/06/90]<br>
                            </p>
                            <address style="font-style: normal;text-align:center;font-size: 22px;font-family: Times New Roman;margin-top: 7px;">
                                <h4 style="font-size:25px;margin-top: 5px;font-family: Times New Roman">CAUVERY</h4><br>
                                <b> Karnataka State Arts and Crafts Emporium</b><br><br>
                                #49, Mahatma Gandhi Road, Bangalore-560001.<br><br>
                                (Unit of Karnataka State Handicrafts Development Corp.Ltd.,<br><br>
                                A Government of Karnataka Enterprise )<br><br>
                                <h4 style="font-size: 16px;">CONSOLIDATED CASH-INVOICE</h4>
                            </address>
                        </td>
                        <td width="25%" style="display: none">Grams:<br> Phone:<br> Fax:<br>
                            Email:<br>
                        </td>
                    </tr>
                    <%--<tr>
                        <td colspan="3" align="center"><h4>TAX INVOICE - CASH</h4></td>
                    </tr>
                    --%>
                    <tr>
                        <td colspan="0" style="font-size:22px;">CashBill No: <b>${requestScope.header.salesOrderNo}</b></td>
                        <%--<td></td>--%>
                        <td id="cashBillBarCode"></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="font-size: 18px;">Name & Address : ${requestScope.header.customeName}
                            <%--
                            ${requestScope.header.streetOne},
                            ${requestScope.header.streetTwo},
                            ${requestScope.header.country},
                            ${requestScope.header.state},
                            ${requestScope.header.city},
                            ${requestScope.header.zipCode}--%>
                        </td>
                        <td style="font-size:20px;"><b style="margin-left: -105px;">Date : ${requestScope.header.dateTime}  Hrs</b></td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td colspan="3">
                            <table border="1px" style="width:1020px;margin-left: 2px;">
                                <tr>
                                    <th style="font-size:22px;width: 2%">S.No</th>
                                    <th style="font-size:22px;width: 5%;">Material</th>
                                    <th style="font-size:18px;width: 6%;">Description</th>
                                    <th style="font-size:22px;width: 2%;">Qty</th>
                                    <th style="font-size:22px;width: 5%;">Rate</th>
                                    <th style="font-size:22px;width: 5%;">Value</th>
                                    <th style="font-size:22px;width: 3%;">Discount</th>
                                    <th style="font-size:22px;width: 3%;">VAT</th>
                                    <th style="font-size:22px;width: 5%;">Net Amt</th>
                                </tr>
                                <c:set var="cashBillSno" value="0"/>
                                <c:forEach var="line" items="${requestScope.header.billLineTos}" >
                                    <tr>
                                        <td colspan="9" align="center" style="font-size:22px;"><b>CounterBill No : ${line.billCounterNo}</b></td>
                                    </tr>
                                    <c:forEach var="line1" items="${line.billLineDetailTos}">
                                        <c:set var="packingCharge" scope="session" value="${line1.packingValue}"/>
                                        <c:set var="cashBillSno" value="${cashBillSno+1}"/>
                                        <tr>
                                            <td align="center" style="font-size:22px;">${cashBillSno}</td>
                                            <td align="center" style="width: 8%;font-size:22px;">${line1.matrialNo}</td>
                                            <td style="width: 15%;font-size:18px;">${line1.description}</td>
                                            <td align="center" style="width: 3%;font-size:22px;"><fmt:formatNumber pattern="#00.000"  value="${line1.qty}"/></td>
                                            <td align="center" style="width: 5%;font-size:22px;"><fmt:formatNumber pattern="#0.00"  value="${line1.rate}"/></td>
                                            <td align="center" style="width: 5%;font-size:22px;"><fmt:formatNumber pattern="#0.00"  value="${line1.value}"/></td>
                                            <td align="center" style="width: 6%;font-size:22px"><fmt:formatNumber pattern="#0.00"  value=" ${line1.discountValue}"/><br>(${line1.discountPercentage})%</td>
                                            <td align="center" style="width: 6%;font-size:22px;"><fmt:formatNumber pattern="#0.00"  value="${line1.vatValue}"/><br>(${line1.vatPercentage})%</td>
                                            <td align="right" style="width: 8%;font-size:22px;">
                                                <fmt:formatNumber pattern="#0.00"  value="${line1.value-line1.discountValue+line1.vatValue}"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <tr><td colspan="10"><label style="font-size:22px;float: left;">Packing / Gift Charge :   </label><b style="float: right;font-size: 20px"><fmt:formatNumber pattern="#0.00"  value="${packingCharge}"/></b></td></tr>
                                        </c:forEach>
                                        <%--
                                        <tr>
                                                <td align="center" colspan="8">Discount</td>
                                                <td>${line1.discountPercentage}</td>
                                        </tr>
                                        
                                        <tr>
                                            <td align="center" colspan="8">Packing Charge/Gift</td>
                                            <td></td>
                                        </tr>
                                        --%>
                                        <c:set var="redemValue" scope="session" value="${requestScope.header.couponRedemValue}"/>
                                        <c:if test="${redemValue>0}">
                                    <tr>
                                        <td align="right" colspan="7" style="font-size:22px;">
                                            Coupon Redemption : <br><br>
                                            Rupees   ${requestScope.header.billAmountWord}     ${ " Only" }</td>
                                        <td style="font-size:22px;">Total Amount</td>
                                        <td style="text-align: right;font-size:22px;">
                                            <fmt:formatNumber pattern="#0.00" type="number"  value="${requestScope.header.couponRedemValue}"/><br><br>
                                            <fmt:formatNumber pattern="#0.00" type="number"  value="${requestScope.header.netBillAmount+((requestScope.header.netBillAmount%1>0.5)?(1-(requestScope.header.netBillAmount%1))%1:-(requestScope.header.netBillAmount%1))}" maxFractionDigits="0"/>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${redemValue==0}">
                                    <tr>
                                        <td align="right" colspan="7" style="font-size:22px;">Rupees ${requestScope.header.billAmountWord}   ${ " Only" }</td>
                                        <td style="font-size:22px;">Total Amount</td>
                                        <td style="text-align: right;font-size:22px;">
                                            <fmt:formatNumber pattern="#0.00" type="number"  value="${requestScope.header.netBillAmount+((requestScope.header.netBillAmount%1>0.5)?(1-(requestScope.header.netBillAmount%1))%1:-(requestScope.header.netBillAmount%1))}" maxFractionDigits="0"/>
                                        </td>
                                    </tr>
                                </c:if>
                            </table>
                        </td>

                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="2"></td>
                        <!--                    
                              <td colspan="2">E & OE Time : 16:15:19 <br> Goods once
                                                sold cannot be taken back or exchanged.<br> 
                                                TIN No.29520088675 <br>
                                                CST NO0.00551697[06/06/90]
                                            </td>-->
                        <td style="font-size:22px;text-align: right;">
                            <br><br>Billed By  <br><br>${requestScope.header.empName}<br>
                        </td>
                    </tr>
                    <%--
                    <tr>
                        <td colspan="3">Orally approval taken by Manager For Discount<br><br><br><br>
                            Manager Signature</td>
                    </tr>
                    --%>
                    <tr>
                        <td colspan="3"><br><br></td>
                    </tr>
                    <%--
                    <tr>
                        <td colspan="3" style="font-size: 20px;">For Online Shopping,Visit : www.cauveryhandicrafts.net</td><br>
                </tr>
                    --%>
                </tfoot>
            </table>
            <div style="font-size: 22px;">
                P.S.: <%--<p style="margin-left: 3%;">1.The Cheque/Demand Draftshould be in favour of Cauvery, KSA & C Emporium,Bangalore</p>--%><br>
                <br><p style="margin-left: 3%;margin-top: 1px;">1.Goods once sold cannot be taken back or exchanged.</p><br>
                <%--<p style="margin-left: 3%;margin-top: -2%;">2.Articles are sent at customer's risk.</p><br>
                <p style="margin-left: 3%;margin-top: -2%;">4.Payment should be made within 7 days.</p><br>
                <p style="margin-left: 3%;margin-top: 3px;">Received the above articles correctly and in good condition.</p><br>
                <table style="margin-top: -1%;margin-left: 3%;">
                    <tr>
                        <td><label style="width: 145%;font-size: 75%;">Signature of the Customer</label></td>
                        <td><label style="margin-left:45%;font-size: 75%;">Manager/D.G.M</label></td>
                        <td><label style="margin-left:45%;font-size: 75%;">Supervisor/SalesAsst</label> </td>
                    </tr>
                </table>
                --%>
            </div>
        </div>
        <div style="text-align: center;display: none">
            <input type="button" id="printButton" class="btn btn-success" value="Print Page" onclick="printPurchaseOrder();"/>
        </div>
    </body>
</html>
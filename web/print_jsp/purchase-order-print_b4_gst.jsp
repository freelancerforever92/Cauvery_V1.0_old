<%@page import="com.to.PurchaseOrderHeaderTo"%>
<%@page import="com.model.GenericModule"%>

<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
<link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/style.css'/>" type="text/css" />
<script src="../js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="../js/jquery-latest.min.js" type="text/javascript"></script>
<script src="../js/jquery-barcode.js" type="text/javascript"></script>
<script src="../js/jquery-barcode.min.js" type="text/javascript"></script>
<script src="../js/prototype.min.js" type="text/javascript"></script>
<html>
    <head>
        <script language="javascript" type="text/javascript">
            $(document).ready(function()
            {
                var salesOrderNumber = $("#hiddenSalesOrderNumber").val().trim();
                $("#salesOrderBarCode").barcode(salesOrderNumber, "codabar", {barWidth: 1.5, barHeight: 20});
//                $('tr:nth-child(even)').css("margin-top", "200px");
            });
            window.print();
        </script>
    </head>
    <%
        String purchaseOrderNo = request.getParameter("salesOrderNo");
        String counterType = request.getParameter("counterType");
        PurchaseOrderHeaderTo headerTo = GenericModule.getPurchaseOrderPrint(purchaseOrderNo, counterType);
        request.setAttribute("header", headerTo);
    %>
    <link rel="icon" href="./images/cauvery.jpg" rel="Cauvery" type="image/x-icon"/>
    <input type="hidden" id="hiddenSalesOrderNumber" name="hiddenSalesOrderNumber" value="${requestScope.header.salesOrderNo}" style="display: none"/>
    <title style="display: none;">Invoice No - ${requestScope.header.salesOrderNo}</title>
    <body>
        <div style="padding-top: 120px; width: 100%;">


            <div style="clear: both;overflow: hidden;">
                <div style="width: 65%; float: left;">
                    <div style="padding-left: 40%;">
                        <h3>TAX INVOICE - CASH</h3>
                        <h3>${requestScope.header.counterTitle}</h3>
                    </div>
                </div>

                <div style="width: 35%;float: left;">
                   <!--
                    <h5> TIN NO  : 29520088675</h5>
                    <h5> CST NO  : 00551697 [06/06/90]</h5>
                   -->
                   <h5> CIN   : U75112KA 1964SGC001526</h5>
                   <h5> GSTIN : 29AAACK5852D1ZK</h5>
                </div>
            </div>

            <div style="clear: both;overflow: hidden; padding-top: 10px;">
                <div style="width: 65%; float: left;">
                    <c:set var="manualBilNo" scope="session" value="${requestScope.header.manualBillNumber}"/>
                    <c:if test="${ not empty manualBilNo && manualBilNo !='0000'}">
                        <h3>Invoice No :${requestScope.header.salesOrderNo}</h3> <h6>( Manual BillNo : <b>${requestScope.header.manualBillNumber} )</h6>
                    </c:if>
                    <c:if test="${manualBilNo==null || manualBilNo =='0000'}">
                        <h3>Invoice No : ${requestScope.header.salesOrderNo}</h3>
                    </c:if>


                </div>  
                <div style="width: 35%;float: left;">
                    <p  id="salesOrderBarCode"></p>
                </div>
            </div>

            <div style="clear: both;overflow: hidden; padding-top: 5px;">
                <div style="width: 65%; float: left; font-size: 12px;">
                    <p> Name & Address : ${requestScope.header.customeName}</p>
                </div>  
                <div style="width: 35%;float: left;">
                    <p>Date : ${requestScope.header.dateTime} Hrs</p>
                </div>
            </div>
            <div style="clear:both;overflow:hidden;">
                <table  border="1px" style="width:90%;text-align: center; font-size: 12px;">
                    <tr>
                        <th>S.No</th>
                        <th>Material</th>
                        <th>Description</th>
                        <th>Qty</th>
                        <th>Rate</th>
                        <th>Value</th>
                        <th>Discount</th>
                        <th>VAT</th>
                        <th>Amount</th>
                    </tr>
                    <c:set var="counterSno" value="0"/>
                    <c:forEach var="line" items="${requestScope.header.purchaseOrderLineTos}">
                        <c:set var="counterSno" value="${counterSno+1}"/>
                        <c:set var="sessionDiscountValue" scope="session" value="${line.discountPercentage}"/>
                       
                         <c:if test="${counterSno==7 || counterSno ==14}">
                             <tr style="height: 360px; border: none!important;">
                                 <td colspan="9">&nbsp;</td>
                             </tr>
                             <tr style="border: none!important; text-align: left;font-weight: bold; font-size: 18px;padding:0px;"><td colspan="9"><p style="padding: 8px 0px 0px 0px;">Invoice No : ${requestScope.header.salesOrderNo}</p></td></tr>
                             <tr>
                              </c:if>
                              <c:if test="${counterSno!=6}"> 
                                  <tr>
                              </c:if>
                        
                            <td><b>${counterSno}</b></td>
                            <td><b>${line.matrialNo}</b><br>-------------<br>${line.vendor}</td>
                            <td><b>${line.description}</b></td>
                            <td><b><fmt:formatNumber pattern="#0.000"  value="${line.qty}"/></b></td>
                            <td><b><fmt:formatNumber pattern="#0.00"  value="${line.rate}"/></b></td>
                            <td><b><fmt:formatNumber pattern="#0.00"  value="${line.value}"/></b></td>
                            <td><b><fmt:formatNumber pattern="#0.00"  value="${line.discountValue}"/></b><br><b><fmt:formatNumber pattern="#0.00"  value="${line.discountPercentage}"/>%</b></td>
                            <td><b><fmt:formatNumber pattern="#0.00"  value="${line.vattValue}"/></b><br><b>${line.vattPercentage}% </b></td>
                            <td>
                                <b><fmt:formatNumber pattern="#0.00"  value="${line.calculatedValue}"/></b>
                            </td>
                        </tr>
                        
                    </c:forEach>
                    <tr>
                        <td colspan="8"><label>Packing / Gift Charge </label></td>
                        <td><fmt:formatNumber pattern="#0.00"  value="${requestScope.header.packageCharge}"/></td>
                    </tr>
                    <tr>
                        <td colspan="6"></td>
                        <!--<td colspan="2"><h4>Total Amount</h4></td>-->
                        <td colspan="2"><h4>Total Value of Supply Including Taxes</h4></td>
                        <td><h3><fmt:formatNumber pattern="#0.00"  value="${requestScope.header.billAmount}"/></h3></td>
                    </tr>
                </table>
            </div>

            <div style="clear: both;overflow: hidden; padding: 10px 0px 20px 0px;">
                <div style="width: 80%; float: left;">
                    <p>
                        Rupees  ${requestScope.header.billAmountWord}${"   Only   "}

                    </p>
                    <p style="font-size: 40px;padding:10px 0px 0px 30px;"><fmt:formatNumber pattern="#0.00"  value="${requestScope.header.billAmount}"/></p>
                </div>

                <div style="width: 20%;float: right;">
                    <h5>Billed By</h5>
                    <h5>${requestScope.header.empName}</h5>
                </div>
            </div>
           
                <p>Manager / DGM<br>
                P.S. : Goods once sold cannot be taken back or exchanged.</p>
        </div>

    </body>
</html>
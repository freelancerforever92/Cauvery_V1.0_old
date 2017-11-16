<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<form id="cashBillingForm" name="cashBillingForm" method="post" >
    <legend>CASH BILL-Return</legend>

    <div class="control-group">
        <input type="button" id="cashbillReturn_clearbutton" name="cashbillReturn_clearbutton" class="btn btn-info" onclick="" value="Clear" style="position: relative;float: right;"/>
        <input type="button" id="cashbill_Backbutton" name="cashbill_Backbutton" class="btn btn-info" onclick="load_CashBillSalesForm();" value="Back" style="position: relative;float: right;margin-right: 5px;"/>
        <table>
            <tr>
                <td>
                    <label class="control-label" for="input01">Counter Bill</label>
                </td>
                <td>
                    <input type="text" class="input-medium" id="txtCounterBillNoReturn" name="txtCounterBillNoReturn" style="margin-left: 50px;" title="Please Enter Material ID" onblur="fetchCounterBill_And_Amt();" >
                    <input type="button" id="cashbillReturn_clearbutton" name="cashbillReturn_clearbutton" class="btn btn-info" value="Enter" onclick="fetchSalesOrderDetails();" style="position: relative;margin-bottom: 10px;"/>
                </td>
            </tr>
        </table>
        <div id="PruchasedProductList_table" style="width: 70%; float: left; height: 400px; overflow: scroll; vertical-align: top;">
            <table id="cashbill_PruchasedProductList" class="table" name="cashbill_PruchasedProductList" border="2">
                <thead>
                    <tr>
                        <th style="text-align: center">S.No</th>
                        <th style="text-align: center">Material</th>
                        <th style="text-align: center">Quantity</th>
                        <th style="text-align: center">Rate</th>
                        <th style="text-align: center">Net Value</th>
                        <th style="text-align: center">Vendor</th>
                        <th style="text-align: center">Action</th>
                    </tr>
                </thead>
                <tbody id="pruchase_tbody"></tbody>

            </table>
        </div>
    </div>
</form>
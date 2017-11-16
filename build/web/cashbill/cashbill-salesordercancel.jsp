<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<head>
    <script type="text/javascript">
        $(document).ready(function ()
        {
            $("#txtCancellingCounterBillNo").focus();
            $("#counterbillDetail").hide();
            loadCancellingReasons();


        });

        $('html').bind('keypress', function (e)
        {
            if (e.keyCode == 13)
            {
                return false;
            }
        });
    </script>
    <style>
        .dis-n{display: none;}
        .counterbillTable{height: 280px;overflow-y: auto; width: 100%; border: #cccccc 1px solid}
    </style>
</head>
<form id="cashBillingForm" name="cashBillingForm" method="post" >
    <legend>Counter Bill-Cancellation
        <input type="button" id="cashbillCancel_ClearButton" name="cashbillCancel_ClearButton" class="btn btn-danger" onclick="clearCancellingFields();" value="Clear" style="position: relative;float: right;"/>
        <input type="button" id="cashbillCancel_Backbutton" name="cashbillCancel_Backbutton" class="btn btn-success" onclick="load_salesForm();" value="Back" style="position: relative;float: right;margin-right: 5px;"/>
        <input type="button" id="cashbillHistory_Button" name="cashbillHistory_Button" class="btn btn-warning" value="Cancel History" onclick="loadCancel_historyPage();" style="position: relative;float: right;margin-right: 5px;"/>
    </legend>
    <div class="row">
        <div class="span3">
            <div class="row-fluid" style="padding-top: 18px;">
                <div class="span3"><label class="control-label" for="input01">Counter Bill</label></div>
                <div class="span6"><input type="text" class="input-medium" id="txtCancellingCounterBillNo" name="txtCancellingCounterBillNo"  title="Enter Salesorderno" onblur="checkSalesOrderCancelStatus();" ></div>
                <div class="span3"><input type="button" value="Search"  class="btn btn-primary" onclick="getInfoCounterBill();"></div>
            </div>
            <div class="row-fluid">
                <div class="span3"><label class="control-label" for="input01">Reason</label></div>
                <div class="span6"><select id="txtReasonForCancel" name="txtReasonForCancel" class="input-medium">
                        <option >Select</option>
                    </select></div>
                <div class="span3"><input type="button" id="cashbillCancel_EnterButton" name="cashbillCancel_EnterButton" class="btn btn-success" onclick="cancelSalesOrderDetails();" value="Submit"/></div>
            </div>

            <!--                <tr>
                                                    <td style="float: right;">
                                                        <input type="button" id="cashbillCancel_EnterButton" name="cashbillCancel_EnterButton" class="btn btn-success" onclick="cancelSalesOrderDetails();" value="Submit" style="margin-bottom: 10px;margin-left: 50px;"/>
                                                    </td>
                                </tr>
            -->
        </div>
        <div class="span9">
            <div id="counterbillDetail" class="clearfix"> 
                <div class="clearfix pull-right" style="margin: 10px 0;">Created DateTime <span id="date"></span></div>
                <div class="counterbillTable">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>S.No</th>
                                <th>Material</th>
                                <th>Vendor</th>
                                <th>Description</th>
                                <th>Qty</th>
                                <th>Rate</th>
                                <th>Value</th>
                                <th>Discount</th>
                                <th>VAT</th>
                                <th>Amount</th>
                            </tr>
                        </thead>
                        <tbody id="tb"></tbody>
                        <tfoot>
                            <tr>
                                <td colspan="7"></td>
                                <td colspan="3"><label style="font-size:14px; text-align: right;">Packing / Gift Charge: <b><span id="packval"></span></b> </label> </td>
                            </tr>
                            <tr>
                                <td colspan="7"><b><span  id="textamt"></b></span></td>
                                <td colspan="3">
                                    <label style="font-size:14px;text-align: right;">Total Amount:<b><span id="totalamt"></span></b> </label>
                                </td>
                            </tr>
                        </tfoot>
                    </table>

                    <div class="row-fluid">
                        <div class="span6"><div style="padding-left: 15px;">Payment Type     : <b><span id="cashCollectedAs"></span></b></div></div>
                        <div class="span6"> <div class="pull-right">Billed By : <b><span id="nme"></span></b></div></div>
                        <div class="span6"><div style="margin-left: -13px;">Cash Collected By : <b><span id="cashProcessedBy"></b></span></div></div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</form>
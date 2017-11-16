
<form id="cancelledHistoryBillNo" name="cashBillingForm" method="post" >
    <legend>Counter Bill-Cancellation History
        <input type="button" id="cashbillCancel_Backbutton" name="cashbillCancel_Backbutton" class="btn btn-success" onclick="load_salesForm();" value="Back" style="position: relative;float: right;margin-right: 5px;"/>
    </legend>

</form>
<form id="cancelledHistoryBillNo" name="cashBillingForm" method="post" >
    <div class="row" >
        <div class="span3">
            <div class="control-group">
                <div class="span3"> <label class="control-label" for="input01">Counter Bill</label></div>
                <div class="span7"> <input type="text" class="input-medium" id="txtCancellingSalesOrderNo" name="txtCancellingSalesOrderNo"></div>
                <div class="span2"><input type="button" value="Search" class="btn btn-primary" onclick="getCancel_historyForm();"></div>
            </div>
        </div>
        <div class="span9">
            <div id="cancelcounterbillHistory" class="clearfix hide" > 
                <table class="table table-bordered">
                    <tr>
                        <th>Craft</th>
                        <th>Sales Order NO</th>
                        <th>Total Amount</th>
                        <th>Created By</th>
                        <th>Created Date</th> 
                        <th>Cancelled By</th>
                        <th>Cancelled Date</th>
                        <th>Cancel Reason</th>
                    </tr>
                    <tr>
                        <td id="craftGroup" style="font-weight: bold;color: red;"></td>
                        <td id="son" style="font-weight: bold;color: red;"></td>
                        <td id="ta" style="font-weight: bold;color: red;"></td>
                        <td id="processedby"></td>
                        <td id="pd"></td>
                        <td id="cacncelby"></td>
                        <td id="cd"></td>
                        <td id="canreason" style="font-weight: bold;color: red;"></td>
                    </tr>
                </table>

            </div>
        </div>
    </div>


</form>
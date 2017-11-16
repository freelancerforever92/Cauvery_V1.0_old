<%@taglib prefix="s" uri="/struts-tags" %>
<s:iterator value="couponDetailsTos">
    <tr id="couponTr<s:property value="%{couponNo}"/>" class="classCouponTr" name="couponTr<s:property value="%{couponNo}"/>">
        
        <td style="width: 5px;" class="classCheckBoxCouponNo">
            <input type="checkbox" class="chk" id="checkValue<s:property value="%{couponNo}"/>" name="checkValue<s:property value="%{couponNo}"/>" value="<s:property value="%{couponNo}"/>"/>
        </td>
        
        <td id="couponType<s:property value="%{couponNo}"/>" class="classCouponType">
            <s:property value="%{couponType}"/>
        </td>
        
        <td style="text-align: center;" id="couponNo<s:property value="%{couponNo}"/>" class="classCouponNo">
            <s:property value="%{couponNo}"/>
        </td>
        
        <td id="couponDescp<s:property value="%{couponNo}"/>" class="classCouponDescp">
            <s:property value="%{couponDescp}" />
        </td>
        
        <td style="text-align: center;" id="couponRate<s:property value="%{couponNo}"/>" class="classCouponRate">
            <s:property value="%{couponRate}"/>
        </td>
        
        <td style="text-align: center;" class="classRemoveCoupon">
            <input type="button" id="removeCoupon<s:property value="%{couponNo}"/>" name="removeCoupon<s:property value="%{couponNo}"/>" onclick="removeSelectedCoupon(this,<s:property value="%{couponNo}"/>);" value="Delete"/>
        </td>
    </tr>
</s:iterator>
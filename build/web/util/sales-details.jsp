<%@taglib prefix="s" uri="/struts-tags" %>
<s:iterator value="salesHistoryTos" status="alStatus" >
    <tr>
        <td><s:property value="%{item}" /></td>
        <td><s:property value="%{material}" /></td>
        <td><s:property value="%{quantity}" /></td>
        <td><s:property value="%{price}" /></td>
        <td><s:property value="%{netAmount}" /></td>
        <td><s:property value="%{vendor}" /></td>
        <td><input type="button" value="Cancel"/></td>
    </tr>
</s:iterator>
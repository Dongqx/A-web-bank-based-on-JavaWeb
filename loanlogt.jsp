<%@ page language="java" contentType="text/html;" pageEncoding="gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>��ҳ��ʾ�����¼</title>
    <script type="text/javascript">
        function select() {
        	var curPage=document.getElementById("curPage").value;
			location.href="/ssh2-netBank/transaction/loanlist1?pager.curPage="+curPage;
        }
    </script>
</head>
<body>
    <div align="center">
        <table width="650" border="1" cellpadding="0" cellspacing="0">
        <tbody align="center">
            <tr>
                <td colspan="5" style="font-size: 20px; color: red;" align="center">�����¼һ����</td>
            </tr>
            <tr>
                <td width="50">���</td>
                <td width="80">������</td>
                <td width="80">�������</td>
                <td>��������</td>
            </tr>
            <s:iterator value="#request.logs" status="status">
                <tr>
                    <td><s:property value="#status.count"/></td>
                    <td><s:property value="loan_money"/></td>
                    <td><s:property value="huan_money"/></td>
                    <td><s:property value="datetime"/></td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
        <!-- ��ҳ�����Ӳ��� -->
        <table>
            <tr>
                <td></td>
                <td>
                    <s:if test="pager.curPage > 1">
                        <a href='/ssh2-netBank/transaction/loanlist1?pager.curPage=1&id=${account.accountid} & status.id=${status.id}'>��ҳ</a>&nbsp;&nbsp;
                        <a href='/ssh2-netBank/transaction/loanlist1?pager.curPage=${pager.curPage-1}&id=${account.accountid} & status.id=${status.id}'>��һҳ</a>&nbsp;&nbsp;
                    </s:if>
                </td>
                <td>
                    <s:if test="pager.curPage < pager.pageCount">
                        <a href='/ssh2-netBank/transaction/loanlist1?pager.curPage=${pager.curPage+1}&id=${account.accountid} & status.id=${status.id}'>��һҳ</a>&nbsp;&nbsp;
                        <a href='/ssh2-netBank/transaction/loanlist1?pager.curPage=${pager.pageCount}&id=${account.accountid} & status.id=${status.id}'>βҳ</a>&nbsp;&nbsp;
                    </s:if>
                </td>
                <td>
                                                ��${pager.curPage}/${pager.pageCount}ҳ&nbsp;&nbsp;ת��
                    <select onchange="select()" id="curPage">
                        <s:iterator>
                            <s:if test="#status.count == pager.curPage">
                                <option value="${status.count}" selected="selected">${status.count}</option>
                            </s:if>
                            <s:else>
                                <option value="${status.count}">${status.count}</option>
                            </s:else>
                        </s:iterator>
                    </select>ҳ
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
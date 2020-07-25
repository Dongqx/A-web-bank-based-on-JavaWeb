<%@ page language="java" contentType="text/html;" pageEncoding="gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>��ҳ��ʾ���׼�¼</title>
    <script type="text/javascript">
        function select() {
        	var curPage=document.getElementById("curPage").value;
			location.href="/ssh2-netBank/transaction/list?pager.curPage="+curPage;
        }
    </script>
    <link href="../css/bootstrap.css" type="text/css" rel="stylesheet" >
</head>
<body>
    <div align="center">
    	<table class="table table-striped table-bordered table-hover table-condensed">
			<caption style="font-size: 20px; color: red;">���׼�¼һ����</caption>
			<thead>
				<tr>
					<th>���</th>
					<th>�Է��˻�</th>
	                <th>���׽��</th>
	                <th>��������</th>
	                <th>��������</th>
				</tr>
			</thead>
			<tbody>
	          <s:iterator value="#request.logs" status="status">
                <tr>
                    <td><s:property value="#status.count"/></td>
                    <s:if test="otherid==#session.user.accountid && transactionType.name!='ȡ��'">
                        <td><s:property value="account.accountid"/></td>
                        <td><s:property value="tr_money"/></td>
                    </s:if>
                    <s:else>
                        <td><s:property value="otherid"/></td>
                        <td><s:property value="tr_money"/></td>
                    </s:else>
                    <td><s:property value="transactionType.name"/></td>
                    <td><s:property value="datetime"/></td>
                </tr>
           	 </s:iterator>
            </tbody>
		</table>

        <!-- ��ҳ�����Ӳ��� -->
        <table>
            <tr>
                <td>
                    <s:if test="pager.curPage > 1">
                        <a href='/ssh2-netBank/transaction/list?pager.curPage=1'>��ҳ</a>&nbsp;&nbsp;
                        <a href='/ssh2-netBank/transaction/list?pager.curPage=${pager.curPage-1}'>��һҳ</a>&nbsp;&nbsp;
                    </s:if>
                </td>
                <td>
                    <s:if test="pager.curPage < pager.pageCount">
                        <a href='/ssh2-netBank/transaction/list?pager.curPage=${pager.curPage+1}'>��һҳ</a>&nbsp;&nbsp;
                        <a href='/ssh2-netBank/transaction/list?pager.curPage=${pager.pageCount}'>βҳ</a>&nbsp;&nbsp;
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
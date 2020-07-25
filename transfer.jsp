<%@ page language="java" contentType="text/html;" pageEncoding="gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>ת��</title>
    <script type="text/javascript">
    
        //��ȡʱ�� 
	    function disptime(){
	    	var now=new Date();
	    	
	    	var year=now.getFullYear();
	    	var month=now.getMonth()+1;
	    	var date=now.getDate();
	    	var hour=now.getHours();
	    	var minute=now.getMinutes();
	    	var second =now.getSeconds();
	    		document.getElementById("datetime").value=year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
	    		//year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
	    		setTimeout("disptime()", 1000);
	    	}    
    
        //�ж��û�����Ĵ�����Ƿ����
        function transfer() {
        	var money = document.getElementById("tr_money").value;
        	var otherid = document.getElementById("otherid").value;
        	//alert(money.length);
        	if(money.length>0) {
        		if(!(money.search(/^[\+\-]?\d+\.?\d*$/)==0)) {
    				document.getElementById("errormoney").innerHTML="���зǷ��ַ�!";
    				return false;
    			}else {
    				if(parseFloat(money)==0) {
    					document.getElementById("errormoney").innerHTML="���������0!";
    					return false;
    				}
    				if(otherid.length>0){
    					return confirm("ȷ��Ҫת���� ");
    				}else {
    	        		document.getElementById("error_otherid").innerHTML="�Է��˻�����Ϊ�գ�";
    	        		return false;
    	        	}
    			}
        	} else {
        		document.getElementById("errormoney").innerHTML="����Ϊ�գ�";
        		return false;
        	}
        }
        
    </script>
    <link href="../css/bootstrap.css" type="text/css" rel="stylesheet" >
    <link href="css/bootstrap.css" type="text/css" rel="stylesheet" >
</head>
<body onload="disptime()">
	<form action="/ssh2-netBank/transaction/transfer" style="width: 100%; margin-left: 35%; margin-top: 20px;" name="myform" method="post" onsubmit="return transfer()">
	  <div class="form-group" style="width: 20%;">
	    <div class="input-group">
	      <div class="input-group-addon">ת�˽��</div>
	      <input type="text" class="form-control" name="log.tr_money" id="tr_money" placeholder="Amount">
	    </div>
	     <span id="errormoney" style="color:red;"></span>
	  </div>
	  
	    <div class="form-group" style="width: 20%;">
	    <div class="input-group">
	      <div class="input-group-addon">�Է��˻�</div>
	      <input type="text" class="form-control" name="log.otherid" id="otherid" placeholder="������Է�id">
	    </div>
	      <span id="error_otherid" style="color:red;"></span>
	  </div>
	  
	  <div class="form-group" style="width: 20%;">
	  	<label for="datetime"></label>
	    <div class="input-group">
	      <div class="input-group-addon">ת��ʱ��</div>
	      <input type="text" class="form-control" name="log.datetime" id="datetime">
	    </div>
	  </div>
	  <button type="submit" class="btn btn-primary" style="width: 240px">ת  ��</button>
	</form>
</body>
</html>
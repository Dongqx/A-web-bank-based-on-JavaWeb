<%@ page language="java" contentType="text/html;" pageEncoding="gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>ȡ��</title>
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
        function withdraw() {
        	var money = document.getElementById("tr_money").value;
        	//alert(money.length);
        	if(money.length>0) {
        		if(!(money.search(/^[\+\-]?\d+\.?\d*$/)==0)) {
    				document.getElementById("errormoney").innerHTML="���зǷ��ַ�!";
    				return false;
    			}else {
    				if(parseFloat(money)<=0) {
    					document.getElementById("errormoney").innerHTML="���������0!";
    					return false;
    				}
    				return confirm("ȷ��Ҫȡ���� ");
    			}
        	} else {
        		document.getElementById("errormoney").innerHTML="����Ϊ�գ�";
        		return false;
        	}
        }
        
    </script>
    <link href="css/bootstrap.css" type="text/css" rel="stylesheet" >
</head>
<body onload="disptime()">
<form action="/ssh2-netBank/transaction/withdraw" style="width: 100%; margin-left: 35%; margin-top: 20px;" name="myform" method="post" onsubmit="return withdraw()">
	  <div class="form-group" style="width: 20%;">
	    <div class="input-group">
	      <div class="input-group-addon">ȡ����</div>
	      <input type="text" class="form-control" name="log.tr_money" id="tr_money" placeholder="Amount">
	    </div>
	    <span id="errormoney" style="color:red;"></span>
	  </div>
	  
	  <div class="form-group" style="width: 20%;">
	  	<label for="datetime"></label>
	    <div class="input-group">
	      <div class="input-group-addon">ȡ��ʱ��</div>
	      <input type="text" class="form-control" name="log.datetime" id="datetime">
	    </div>
	  </div>
	  <button type="submit" class="btn btn-primary" style="width: 240px">ȡ  ��</button>
	</form>
</body>
</html>
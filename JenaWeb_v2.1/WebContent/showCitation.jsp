<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Eagle-i Citation</title>
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet" type="text/css"  href="css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="fonts/font-awesome/css/font-awesome.css">
<link href="css/owl.carousel.css" rel="stylesheet" media="screen">
<link href="css/owl.theme.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"  href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/animate.min.css">
<link rel="stylesheet" type="text/css" href="css/custom.css">
<script src="https://code.jquery.com/jquery-2.2.2.min.js"></script>

<style>
h1.capitalize 
{
	text-transform: capitalize;
}
</style>

</head>
<body>
<%
String citeResult = (String)session.getAttribute("CiteResult");
%>
<br/>
<br/>

<div style="position: relative; top: 50px; padding: 20px;">
<TABLE style="border:1px dashed black;" SIZE=1000 HEIGHT=100 bgcolor="#D3D3D3"><TR><TD><%=citeResult %></TD></TR></TABLE>
</div>

<div>
<a style="position: absolute; top: 20px; left: 20px" href="/JenaWeb">[X]Return</a>
</div>

<div style="position: relative; top: 100px; left: -20px; padding: 20px;" align = "center">
<p><ul class="list-inline">	
<a href="http://dblp.uni-trier.de/pers/hb/c/Crawford:Diane" target="_blank"><img alt="" src="http://dblp.uni-trier.de/img/bibtex.dark.16x16.png" class="icon">BibTeX(Constructing)</a> 
<a href="showXML.jsp"><img alt="" src="http://dblp.uni-trier.de/img/xml.dark.16x16.png" class="icon">XML</a> 
<a href="http://dblp.uni-trier.de/rec/ris/conf/a4cloud/2014.ris"><img alt="" src="http://dblp.uni-trier.de/img/endnote.dark.16x16.png" class="icon">RIS(Constructing)</a>
</ul></p></div>

</body>
</html>
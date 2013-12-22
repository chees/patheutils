<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>
<body>
hi bye yoblaatyodoh

<script>
var oReq = new XMLHttpRequest();
oReq.onload = function() {
	var movies = JSON.parse(this.responseText);
	for (var i = 0; i < movies.length; i++) {
		var m = movies[i];
		document.write('<div>' + m.title + ' ' + m.rating + '</div>');
	}
};
oReq.open("get", "http://patheutils.chees.info/movies2013-12-22.json");
oReq.send();
</script>
</body>
</html>

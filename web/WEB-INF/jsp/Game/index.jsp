<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${userTicket.userName}</title>
        <script src="<c:url value="/resources/default/js/jquery-1.9.1.js" />"></script>
        <style>
            .tema:hover{
                border-color:#080808;
            }
        </style>
        <script>
            function waiting(temaId) {
                $("#content").load('<c:url value="/Game/waiting.htm?temaId=' + temaId + '" />');
            }
        </script>
    </head>
    <body>
        <c:forEach items="${listRfTema}" var="rfTema">
            <div class="col-md-4">
                <a href="#" onclick="waiting(${rfTema.id})">
                    <div class="tema alert alert-info text-center box-shadow">
                        ${rfTema.description}
                    </div>
                </a>
            </div>
        </c:forEach>
    </body>
</html>

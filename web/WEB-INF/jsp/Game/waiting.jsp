<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${userTicket.userName}</title>
        <script src="<c:url value="/resources/default/js/jquery-1.9.1.js" />"></script>
        <script src="<c:url value="/resources/default/js/waiting.js" />"></script>
    </head>
    <body>
        <div class="col-md-12 text-center">
            <h2>Tema: ${rfTema.description}</h2>
        </div>
        <div class="col-md-6 text-center">
            <div class="panel panel-body box-shadow" style="height: 200px;">
                <h2>${userTicket.userName}</h2>
                <button class="btn btn-primary btn-lg" name="ready" value="Siap">Siap</button>
            </div>
        </div>
        <div class="col-md-6 text-center">
            <div class="panel panel-body box-shadow" style="height: 200px;">
                <div id="otherPlayer"></div>
            </div>
        </div>
        <div id="popup" hidden="true"></div>
    </body>
</html>
<script>
    init('<c:url value="/waitingEndPoint" />', '${userTicket.userName}', '<c:url value="/Game/start.htm" />');
</script>
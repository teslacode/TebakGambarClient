<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tebak Gambar</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap-theme.min.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/jquery-ui/css/jquery-ui.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/default/css/style.css" />">
        <script src="<c:url value="/resources/default/js/jquery-1.9.1.js" />"></script>
        <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/resources/jquery-ui/js/jquery-ui.js" />"></script>
        <script>
            $(function () {
                $.ajax({
                    url: '<c:url value="/index.htm" />',
                    type: "POST",
                    error: function () {
                        alert('ERROR');
                    },
                    success: function (data) {
                        $("#content").html(data);
                    }
                });
            });
        </script>
    </head>
    <body>
        <div id="menu">
            <nav id="menu" class="navbar navbar-default navbar-static-top" role="navigation">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbarCollapse">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="<c:url value="/index.htm"/>"><span class="glyphicon glyphicon-home"> BERANDA</span></a>
                    </div>
                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="navbarCollapse">
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="#">Selamat Datang, ${userTicket.userName}</a></li>
                            <li><a href="<c:url value="/logout.htm"/>"><span class="glyphicon glyphicon-log-out"> KELUAR</span></a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        <div id="content">
            <img src="<c:url value="/resources/default/images/loading.gif"/>"/>
        </div>
    </body>
</html>
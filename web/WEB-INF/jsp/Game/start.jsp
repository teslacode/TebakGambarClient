<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${userTicket.userName}</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap-theme.min.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/jquery-ui/css/jquery-ui.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/default/css/style.css" />">
        <script src="<c:url value="/resources/default/js/jquery-1.9.1.js" />"></script>
        <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/resources/jquery-ui/js/jquery-ui.js" />"></script>
        <script src="<c:url value="/resources/default/js/start.js" />"></script>
        <style>
            .soal .col-xs-3{
                padding: 0;
            }
            .potongan-gambar{
                background: grey;
                text-align: center; 
                height: 150px;
            }
            .potongan-gambar:hover{
                cursor: pointer;
                background: ghostwhite;
            }
        </style>
        <script>
            $(function () {
                $('input[name="jawaban"]').focus();
                $("form").submit(function (e) {
                    e.preventDefault();
                    postJawaban(($('input[name="jawaban"]').val()));
                    $('input[name="jawaban"]').val("");
                    $('input[name="jawaban"]').focus();
                });
            });
        </script>
    </head>
    <body>
        <div class="col-md-4">
            <div class="panel panel-body box-shadow">
                <div id="timeTurn" class="progress progress-striped">
                    <div class="progress-bar " role="progressbar" style="width: 100%"></div>
                </div>
                <h2>${userTicket.userName}</h2> <h2 id="nilai">Nilai: 0</h2>
                <form>
                    <input class="form-control" type="text" name="jawaban"/>
                </form>
                <div id="message" class="text-left" style="overflow: auto; height: 35%;"></div>
                <div id="otherPlayer">
                    <h2 class="userName"></h2> <h2 class="nilai">Nilai: 0</h2>
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <div class="panel panel-body box-shadow">
                <div id="timeSoal" class="progress progress-striped">
                    <div class="progress-bar " role="progressbar" style="width: 100%"></div>
                </div>
                <c:forEach items="${listSoal}" var="soal">
                    <div id="${soal.index}" class="soal" hidden="true">
                        <h3>Soal no ${soal.index + 1} - Tema: ${soal.rfTema.description}</h3>
                        <div class="row-fluid clearfix">
                            <div id="${soal.index}00" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 0, 0, $(this))"><h1>1</h1></div>
                            <div id="${soal.index}10" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 1, 0, $(this))"><h1>2</h1></div>
                            <div id="${soal.index}20" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 2, 0, $(this))"><h1>3</h1></div>
                            <div id="${soal.index}30" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 3, 0, $(this))"><h1>4</h1></div>
                        </div>
                        <div class="row-fluid clearfix">
                            <div id="${soal.index}01" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 0, 1, $(this))"><h1>5</h1></div>
                            <div id="${soal.index}11" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 1, 1, $(this))"><h1>6</h1></div>
                            <div id="${soal.index}21" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 2, 1, $(this))"><h1>7</h1></div>
                            <div id="${soal.index}31" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 3, 1, $(this))"><h1>8</h1></div>
                        </div>
                        <div class="row-fluid clearfix">
                            <div id="${soal.index}02" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 0, 2, $(this))"><h1>9</h1></div>
                            <div id="${soal.index}12" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 1, 2, $(this))"><h1>10</h1></div>
                            <div id="${soal.index}22" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 2, 2, $(this))"><h1>11</h1></div>
                            <div id="${soal.index}32" class="potongan-gambar col-xs-3" onclick="getPotonganGambar(${soal.index}, 3, 2, $(this))"><h1>12</h1></div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div id="popup" hidden="true"></div>
    </body>
</html>
<script>
    init('<c:url value="/startEndPoint" />', '${userTicket.userName}',
            '<c:url value="/Game/checkJawaban.htm" />', '<c:url value="/index.htm" />',
            '<c:url value="/Game/getGambar.htm" />', ${player.turn}, '<c:url value="/resources/default/images/loading.gif" />');
    setInterval(timer, 1000);
</script>
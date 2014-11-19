<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap-theme.min.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/jquery-ui/css/jquery-ui.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/default/css/style.css" />">
        <script src="<c:url value="/resources/default/js/jquery-1.9.1.js" />"></script>
        <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/resources/jquery-ui/js/jquery-ui.js" />"></script>
        <style>
            .vertical-center {
                min-height: 100%;  /* Fallback for browsers do NOT support vh unit */
                min-height: 100vh; /* These two lines are counted as one :-)       */
                display: flex;
                align-items: center;
            }
        </style>
    </head>
    <body>
        <div class="vertical-center">
            <div class="container">
                <div class="col-md-12 text-center">
                    <div id="myCarousel" class="carousel slide col-md-12" data-interval="3000" data-ride="carousel">
                        <!-- Carousel indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="2"></li>
                            <li data-target="#myCarousel" data-slide-to="3"></li>
                            <li data-target="#myCarousel" data-slide-to="4"></li>
                        </ol>   
                        <!-- Carousel items -->
                        <div class="carousel-inner">
                            <div class="active item">
                                <h2>Selamat Datang</h2>
                                <h3>Anda memasuki area bermain Tebak Gambar</h3>
                                <!--                                <div class="carousel-caption">
                                                                    <h3>First slide label</h3>
                                                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                                                </div>-->
                            </div>
                            <div class="item">
                                <h2>Masukan Nama</h2>
                                <h3>Masukan nama Anda pada kotak yang berada di bawah untuk tanda pengenal Anda.</h3>
                                <h3>Tekan enter untuk melanjutkan.</h3>
                            </div>
                            <div class="item">
                                <h2>Pilih Tema</h2>
                                <h3>Pilih tema sebagai acuan soal yang akan kamu jawab. Ada yang acak juga loh.</h3>
                            </div>
                            <div class="item">
                                <h2>Ruang Tunggu</h2>
                                <h3>Tunggu lawan yang akan ikut bermain melawan kamu.</h3>
                            </div>
                            <div class="item">
                                <h2>Mulai Permainan</h2>
                                <h3>Permainan terdiri dari 5 soal.</h3>
                                <h3>4 Soal akan diberikan kesempatan masing-masing pemain untuk menjawab dan buka gambar.</h3>
                                <h3>1 Soal terakhir, gambar akan dibuka secara berurutan otomatis, dan kamu harus jawab secepatnya.</h3>
                            </div>
                        </div>
                        <!-- Carousel nav -->
                        <a class="carousel-control left" href="#myCarousel" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                        </a>
                        <a class="carousel-control right" href="#myCarousel" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right"></span>
                        </a>
                    </div>
                    <div class="col-md-12">
                        <form method="POST">
                            <input id="userName" class="form-control input-lg" type="text" name="userName" placeholder="Masukan nama & Tekan enter untuk melanjutkan"/>
                            <!--                            <button id="login" class="btn btn-primary btn-lg" name="login" value="Login" style="width: 19%;">Main!</button>-->
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

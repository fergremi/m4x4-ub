<%@page language="java" import="practicaDAW.*" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <title>MAX AUB</title>
    <link href="style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="libcapas.js"></script>
    <script type="text/javascript" src="comprobaciones.js"></script>
</head>
<body>
    <div id="topPan">
        <div id="topimagePan"></div>
        <ul>
            <li class="home"><a href="#" class="menu" onclick="Cargar('inicio.html','leftPan');return false">INICIO</a></li>
            <li class="home"><a href="#"class="menu" onclick="Cargar('banco.html','leftPan');return false">BANCO DE LIBROS</a></li>
            <li class="home"><a href="#"class="menu" onclick="Cargar('directiva.html','leftPan');return false">DIRECTIVA</a></li>
            <li class="home"><a href="#"class="menu" onclick="Cargar('calendario.html','leftPan');return false">CALENDARIO ESCOLAR</a></li>
            <li class="home"><a href="#"class="menu" onclick="Cargar('libros.html','leftPan');return false">LIBROS ACADEMICOS</a></li>
            <li class="home"><a href="#" class="menu" onclick="Cargar('contacto.html','leftPan');return false">CONTACTO</a></li>
        </ul>   
        <p>&nbsp;</p>
        <p>MAX AUB</p>
        <p>&nbsp;</p>
        <p>BANCO DE LIBROS</p>
    </div>
    <div id="bodyPan"><div class="inner_copy"></div>
        <div id="leftPan">
            <h2>Introduccion</h2>
            <h2><span>MAX AUB</span></h2>
            <p>&nbsp;</p>
            <p>Redactar una breve introduccion para el inicio de la web</p>
        </div>
        <div id="rightPan">
            
            <form name="form_login" action="#" class="form1"method="post" onsubmit="ProcesarForm(this,'login','leftPan');return false;">>
                <h2>Login</h2>
                <label>Usuario:</label>
                <input type="text" maxlength="20" name="usuario" size="20" autofocus="" onblur="comprobarUsuario();"/>
                <label>Contrase&ntilde;a:</label>
                <input type="password" maxlength="20" name="pass" size="20" autofocus="" onblur="comprobarClave();"/>
                <input name="OK" type="submit" class="botton" id="OK" value="OK" />
                <label class="label1"><a href="#" onclick="Cargar('formulario.html','leftPan');return false" </a></li>No estas registrado?</label>
                
          </form>

            
        <h3>Enlaces de interes</h3>
            <ul>
                <li><a href="http://www.cece.gva.es/" target="_blank">www.cece.gva.es</a></li>
                <li><a href="http://mestreacasa.gva.es/web/ceipmaxaub/1" target="_blank">www.mestreacasa.gva.es</a></li>
                <li><a href="https://ampamaxaub.wordpress.com/" target="_blank">www.ampamaxaub.wordpress.com</a></li>
            </ul>
        </div>
    </div>
    <div id="bodyBottomPan"></div>
    <div id="footerPan">
        <ul>
            <li><a href="#" class="menu" onclick="Cargar('inicio.html','leftPan');return false">INICIO</a></li>
            <li><a href="#"class="menu" onclick="Cargar('banco.html','leftPan');return false">BANCO DE LIBROS</a></li>
            <li><a href="#"class="menu" onclick="Cargar('info.html','leftPan');return false">INFORMACION</a></li>
            <li><a href="#"class="menu" onclick="Cargar('calendario.html','leftPan');return false">CALENDARIO ESCOLAR</a></li>
            <li><a href="#"class="menu" onclick="Cargar('libros.html','leftPan');return false">LIBROS ACADEMICOS</a></li>
            <li><a href="#" class="menu" onclick="Cargar('contacto.html','leftPan');return false">CONTACTO</a></li>
        </ul><div class="fclear">
    	</div>
	</div>
</body>
</html>

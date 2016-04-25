//Javascript

function comprobarUsuario()
{
    if (document.form_login.usuario.value != "")
    {
        return true;
    }
    else 
    {
        alert("Rellene el campo: Usuario");
        document.form_login.usuario.focus();
        return false;
    }
}

function comprobarClave()
{
    if (document.form_login.pass.value != "")
    {
        return true;
    }
    else 
    {
        alert("Rellene el campo: Contraseña");
        document.form_login.pass.focus();
        return false;
    }
}

function comprobarNombre(){
	if (document.form_registro.nombre.value != "")
            return true;
	else {
            alert("Rellene el campo: Nombre");
            document.form_envio.nombre.focus();
            return false;
        }
}
function comprobarDescripcion(){
	if (document.form_registro.descripcion.value != "")
            return true;
	else {
            alert("Rellene el campo: Descripción");
            document.form_prod.descripcion.focus();
            return false;
        }
}
function comprobarPrecio(){
	if (document.form_prod.precio.value != "")
            return true;
	else {
            alert("Rellene el campo: Precio");
            document.form_prod.precio.focus();
            return false;
        }
}
function comprobarStock(){
	if (document.form_prod.stock.value != "")
            return true;
	else {
            alert("Rellene el campo: Stock");
            document.form_prod.stock.focus();
            return false;
        }
}
function comprobarImagen(){
	if (document.form_prod.imagen.value != "")
            return true;
	else {
            alert("Rellene el campo: Imagen");
            document.form_prod.imagen.focus();
            return false;
        }
}  
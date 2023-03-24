
public class Archivo {
	
	 String idSubLote;
	 String base64;
	 String nombre;
	 
	
	 
	public Archivo(String idSubLote, String base64, String nombre) {
		super();
		this.idSubLote = idSubLote;
		this.base64 = base64;
		this.nombre = nombre;
	}
	
	
	public String getIdSubLote() {
		return idSubLote;
	}
	public void setIdSubLote(String idSubLote) {
		this.idSubLote = idSubLote;
	}
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public String toString() {
		return "Archivo [idSubLote=" + idSubLote + ", base64=" + base64 + ", nombre=" + nombre + "]";
	}
	 
	
	
	
	 

}

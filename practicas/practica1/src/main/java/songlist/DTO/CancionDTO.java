package songlist.DTO;

public class CancionDTO {
    public Long id;
    public String titulo;
    public String cantante;
    public int anio;

    public CancionDTO(Long id, String titulo, String cantante, int anio) {
        this.id = id;
        this.titulo = titulo;
        this.cantante = cantante;
        this.anio = anio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCantante() {
        return cantante;
    }

    public void setCantante(String cantante) {
        this.cantante = cantante;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
}

package com.jodra.taranis;

public class TiempoRecyclerContenedor {
    private String hora;
    private String temperatura;
    private String imagen;
    private String velocidadDelViento;

    public TiempoRecyclerContenedor(String hora, String temperatura, String imagen, String velocidadDelViento) {
        this.hora = hora;
        this.temperatura = temperatura;
        this.imagen = imagen;
        this.velocidadDelViento = velocidadDelViento;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getVelocidadDelViento() {
        return velocidadDelViento;
    }

    public void setVelocidadDelViento(String velocidadDelViento) {
        this.velocidadDelViento = velocidadDelViento;
    }
}

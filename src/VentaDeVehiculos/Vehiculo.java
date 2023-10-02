package VentaDeVehiculos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*
* Esta es la clase que que almacena en la base de datos
**/
@DatabaseTable(tableName = "vehiculos")
public class Vehiculo {
    @DatabaseField (id = true)
    private String placa; // Llave primaria
    @DatabaseField
    private String marca;
    @DatabaseField
    private String modelo;
    @DatabaseField
    private int year;
    @DatabaseField
    private int ejes;
    @DatabaseField
    private float cilindrada;
    @DatabaseField
    private double valor;

    public Vehiculo(String placa, String marca, String modelo, int year, int ejes, float cilindrada, double valor) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.year = year;
        this.ejes = ejes;
        this.cilindrada = cilindrada;
        this.valor = valor;
    }

    public Vehiculo() {
        // Constructor vacío, necesario para ORMLite
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEjes() {
        return ejes;
    }

    public void setEjes(int ejes) {
        this.ejes = ejes;
    }

    public float getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(float cilindrada) {
        this.cilindrada = cilindrada;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}

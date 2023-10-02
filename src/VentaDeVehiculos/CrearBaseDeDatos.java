package VentaDeVehiculos;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class CrearBaseDeDatos {
    public static void main(String[] args) throws Exception {
      String url ="jdbc:h2:file:./vehiculo";
      //conectar con la base de datos
        ConnectionSource con = new JdbcConnectionSource(url);
        //Configurar la tabla a traves de un DAO (Data Access Object)
        Dao<Vehiculo,String> tabladevehiculos=
                DaoManager.createDao(con, Vehiculo.class);
        //crear archivo donde se almacenan los datos de los vehiculos
        TableUtils.createTable(tabladevehiculos);
        System.out.println("Reporte actualizado exitosamente!");
        // Ejemplo de operaciones CRUD
        VentaVehiculos ventaVehiculos = new VentaVehiculos();
        // Agregar un nuevo vehículo
        Vehiculo nuevoVehiculo = new Vehiculo("ABC123", "Toyota", "Corolla", 2020, 2, 2.0f, 25000.0);
        ventaVehiculos.agregarVehiculo(tabladevehiculos, nuevoVehiculo);

        // Obtener la lista de todas las placas de vehículos
        List<String> placas = ventaVehiculos.obtenerPlacasDeVehiculos(tabladevehiculos);
        System.out.println("Placas de los vehículos en venta:");
        for (String placa : placas) {
            System.out.println(placa);
        }

        // Obtener la información detallada de un vehículo dado su número de placa
        String placaBuscada = "ABC123";
        Vehiculo vehiculoEncontrado = ventaVehiculos.obtenerVehiculoPorPlaca(tabladevehiculos, placaBuscada);
        if (vehiculoEncontrado != null) {
            System.out.println("Información del vehículo encontrado:");
            System.out.println(vehiculoEncontrado);
        } else {
            System.out.println("No se encontró ningún vehículo con la placa " + placaBuscada);
        }

        // Ordenar la lista de vehículos por modelo, marca o año
        List<Vehiculo> vehiculosOrdenadosPorModelo = ventaVehiculos.ordenarVehiculosPorModelo(tabladevehiculos);
        List<Vehiculo> vehiculosOrdenadosPorMarca = ventaVehiculos.ordenarVehiculosPorMarca(tabladevehiculos);
        List<Vehiculo> vehiculosOrdenadosPorAnio = ventaVehiculos.ordenarVehiculosPorAnio(tabladevehiculos);

        // Hacer una búsqueda de placas usando el modelo y el año del vehículo
        String modeloBuscado = "Corolla";
        int anioBuscado = 2020;
        List<String> placasEncontradas = ventaVehiculos.buscarPlacasPorModeloYAnio(tabladevehiculos, modeloBuscado, anioBuscado);
        System.out.println("Placas encontradas para el modelo " + modeloBuscado + " y el año " + anioBuscado + ":");
        for (String placa : placasEncontradas) {
            System.out.println(placa);
        }

        // Comprar un vehículo (eliminarlo de la lista de vehículos en venta)
        String placaAComprar = "ABC123";
        boolean compraExitosa = ventaVehiculos.comprarVehiculo(tabladevehiculos, placaAComprar);
        if (compraExitosa) {
            System.out.println("Vehículo con placa " + placaAComprar + " comprado con éxito.");
        } else {
            System.out.println("No se pudo comprar el vehículo con placa " + placaAComprar + ".");
        }

        // Disminuir en un 10% el precio de los vehículos que tienen un valor mayor a una cantidad dada
        double valorMaximo = 20000.0;
        ventaVehiculos.disminuirPrecioDeVehiculosCaros(tabladevehiculos, valorMaximo);

        // Localizar el vehículo más antiguo
        Vehiculo vehiculoMasAntiguo = ventaVehiculos.encontrarVehiculoMasAntiguo(tabladevehiculos);
        System.out.println("Vehículo más antiguo:");
        if (vehiculoMasAntiguo != null) {
            System.out.println(vehiculoMasAntiguo);
        } else {
            System.out.println("No se encontró ningún vehículo.");
        }

        // Localizar el vehículo más potente (el de más cilindrada)
        Vehiculo vehiculoMasPotente = ventaVehiculos.encontrarVehiculoMasPotente(tabladevehiculos);
        System.out.println("Vehículo más potente:");
        if (vehiculoMasPotente != null) {
            System.out.println(vehiculoMasPotente);
        } else {
            System.out.println("No se encontró ningún vehículo.");
        }

        // Localizar el vehículo más barato (el de menor precio)
        Vehiculo vehiculoMasBarato = ventaVehiculos.encontrarVehiculoMasBarato(tabladevehiculos);
        System.out.println("Vehículo más barato:");
        if (vehiculoMasBarato != null) {
            System.out.println(vehiculoMasBarato);
        } else {
            System.out.println("No se encontró ningún vehículo.");
        }

        // Cerrar la conexión a la base de datos al finalizar
        con.close();

    }
}


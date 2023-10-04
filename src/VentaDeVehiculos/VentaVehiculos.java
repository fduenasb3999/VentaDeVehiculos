package VentaDeVehiculos;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class VentaVehiculos {

    public VentaVehiculos(){

    }

    // Métodos para las operaciones requeridas

    // Agregar un nuevo vehículo a la venta
    public  void agregarVehiculo(Dao<Vehiculo, String> vehiculoDao, Vehiculo vehiculo) throws SQLException {
        vehiculoDao.create(vehiculo);
    }

    // Obtener la lista de todas las placas de vehículos
    public  List<String> obtenerPlacasDeVehiculos(Dao<Vehiculo, String> vehiculoDao) throws SQLException {
        List<String> placas = vehiculoDao.queryRaw("SELECT placa FROM vehiculos").getResults().stream()
                .map(row -> row[0]) // Obtener el primer elemento (placa) de cada fila
                .collect(Collectors.toList());
        return placas;
    }

    // Obtener la información detallada de un vehículo dado su número de placa
    public  String obtenerVehiculoPorPlaca(Dao<Vehiculo, String> vehiculoDao, String placa) throws SQLException {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo = vehiculoDao.queryForId(placa);
        return vehiculo.toString();
    }

    // Ordenar la lista de vehículos por modelo
    public  List<Vehiculo> ordenarVehiculosPorModelo(Dao<Vehiculo, String> vehiculoDao) throws SQLException {
        List<Vehiculo> vehiculos = vehiculoDao.queryForAll();
        Collections.sort(vehiculos, Comparator.comparing(Vehiculo::getModelo));
        return vehiculos;
    }

    // Ordenar la lista de vehículos por marca
    public  List<Vehiculo> ordenarVehiculosPorMarca(Dao<Vehiculo, String> vehiculoDao) throws SQLException {
        List<Vehiculo> vehiculos = vehiculoDao.queryForAll();
        Collections.sort(vehiculos, Comparator.comparing(Vehiculo::getMarca));
        return vehiculos;
    }

    // Ordenar la lista de vehículos por año
    public  List<Vehiculo> ordenarVehiculosPorAnio(Dao<Vehiculo, String> vehiculoDao) throws SQLException {
        List<Vehiculo> vehiculos = vehiculoDao.queryForAll();
        Collections.sort(vehiculos, Comparator.comparingInt(Vehiculo::getYear));
        return vehiculos;
    }

    // Hacer una búsqueda de placas usando el modelo y el año del vehículo
    public  List<String> buscarPlacasPorModeloYAnio(Dao<Vehiculo, String> vehiculoDao, String modelo, int anio) throws SQLException {
        List<String> placasEncontradas = new ArrayList<>();

        // Crear un constructor de consultas para la clase Vehiculo
        QueryBuilder<Vehiculo, String> queryBuilder = vehiculoDao.queryBuilder();

        // Obtener el objeto Where para agregar condiciones a la consulta
        Where<Vehiculo, String> where = queryBuilder.where();

        // Agregar condiciones a la consulta
        ((Where<?, ?>) where).eq("modelo", modelo).and().eq("year", anio);

        // Ejecutar la consulta y obtener los resultados
        List<Vehiculo> resultados = queryBuilder.query();

        // Extraer las placas de los resultados
        for (Vehiculo vehiculo : resultados) {
            placasEncontradas.add(vehiculo.getPlaca());
        }

        return placasEncontradas;
    }

    // Comprar un vehículo (eliminarlo de la lista de vehículos en venta)
    public  boolean comprarVehiculo(Dao<Vehiculo, String> vehiculoDao, String placa) throws SQLException {
        Vehiculo vehiculo = vehiculoDao.queryForId(placa);
        if (vehiculo != null) {
            vehiculoDao.delete(vehiculo);
            return true;
        } else {
            return false;
        }
    }

    // Disminuir en un 10% el precio de los vehículos que tienen un valor mayor a una cantidad dada
    public  void disminuirPrecioDeVehiculosCaros(Dao<Vehiculo, String> vehiculoDao, double valorMaximo) throws SQLException {
        List<Vehiculo> vehiculosCaros = vehiculoDao.queryBuilder().where().ge("valor", valorMaximo).query();
        for (Vehiculo vehiculo : vehiculosCaros) {
            double nuevoValor = vehiculo.getValor() * 0.9; // Disminuir en un 10%
            vehiculo.setValor(nuevoValor);
            vehiculoDao.update(vehiculo);
        }
    }

    // Localizar el vehículo más antiguo
    public  Vehiculo encontrarVehiculoMasAntiguo(Dao<Vehiculo, String> vehiculoDao) throws SQLException {
        return vehiculoDao.queryBuilder().orderBy("year", true).queryForFirst();
    }

    // Localizar el vehículo más potente (el de más cilindrada)
    public  Vehiculo encontrarVehiculoMasPotente(Dao<Vehiculo, String> vehiculoDao) throws SQLException {
        return vehiculoDao.queryBuilder().orderBy("cilindrada", false).queryForFirst();
    }

    // Localizar el vehículo más barato (el de menor precio)
    public  Vehiculo encontrarVehiculoMasBarato(Dao<Vehiculo, String> vehiculoDao) throws SQLException {
        return vehiculoDao.queryBuilder().orderBy("valor", true).queryForFirst();
    }
}


package VentaDeVehiculos;



import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;


import java.util.List;
import java.util.Scanner;

public class CrearBaseDeDatos {
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        String url = "jdbc:h2:file:./vehiculo";
        // Conectarnos con la base de datos
        ConnectionSource con = new JdbcConnectionSource(url);

        // Configurar la tabla a través de un DAO (Data Access Object)
        Dao<Vehiculo, String> tablaDeVehiculos = DaoManager.createDao(con, Vehiculo.class);

        int opcion = 0;

        do {
            System.out.println("====== MENU =======");
            System.out.println("0. Salir");
            System.out.println("1. Agregar un nuevo vehículo");
            System.out.println("2. Mostrar todas las placas de vehículos en venta");
            System.out.println("3. Obtener información detallada de un vehículo por placa");
            System.out.println("4. Ordenar la lista de vehículos por modelo");
            System.out.println("5. Ordenar la lista de vehículos por marca");
            System.out.println("6. Ordenar la lista de vehículos por año");
            System.out.println("7. Buscar placas por modelo y año");
            System.out.println("8. Comprar un vehículo");
            System.out.println("9. Disminuir en un 10% el precio de los vehículos caros");
            System.out.println("10. Localizar el vehículo más antiguo");
            System.out.println("11. Localizar el vehículo más potente");
            System.out.println("12. Localizar el vehículo más barato");

            System.out.print("Entre su opción: ");
            opcion = teclado.nextInt();
            teclado.nextLine(); // Consumir la línea en blanco después de nextInt()
            VentaVehiculos ventaVehiculos = new VentaVehiculos();

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese los datos del nuevo vehículo:");
                    System.out.print("Placa: ");
                    String placa = teclado.nextLine();
                    System.out.print("Marca: ");
                    String marca = teclado.nextLine();
                    System.out.print("Modelo: ");
                    String modelo = teclado.nextLine();
                    System.out.print("Año: ");
                    int year = teclado.nextInt();
                    System.out.print("Ejes: ");
                    int ejes = teclado.nextInt();
                    System.out.print("Cilindrada: ");
                    float cilindrada = teclado.nextFloat();
                    System.out.print("Valor: ");
                    double valor = teclado.nextDouble();

                    Vehiculo nuevoVehiculo = new Vehiculo(placa, marca, modelo, year, ejes, cilindrada, valor);
                    ventaVehiculos.agregarVehiculo(tablaDeVehiculos, nuevoVehiculo);
                    System.out.println("Vehículo agregado con éxito.");
                    break;

                case 2:
                    List<String> placas = ventaVehiculos.obtenerPlacasDeVehiculos(tablaDeVehiculos);
                    System.out.println("Placas de los vehículos en venta:");
                    for (String placaVehiculo : placas) {
                        System.out.println(placaVehiculo);
                    }
                    break;

                case 3:
                    System.out.print("Ingrese la placa del vehículo a consultar: ");
                    String placaConsulta = teclado.nextLine();
                    String vehiculoConsultado = ventaVehiculos.obtenerVehiculoPorPlaca(tablaDeVehiculos, placaConsulta);
                    if (vehiculoConsultado != null) {
                        System.out.println("Información del vehículo:");
                        System.out.println(vehiculoConsultado);
                    } else {
                        System.out.println("No se encontró ningún vehículo con la placa " + placaConsulta);
                    }
                    break;

                case 4:
                    List<Vehiculo> vehiculosPorModelo = ventaVehiculos.ordenarVehiculosPorModelo(tablaDeVehiculos);
                    mostrarVehiculos(vehiculosPorModelo);
                    break;

                case 5:
                    List<Vehiculo> vehiculosPorMarca = ventaVehiculos.ordenarVehiculosPorMarca(tablaDeVehiculos);
                    mostrarVehiculos(vehiculosPorMarca);
                    break;

                case 6:
                    List<Vehiculo> vehiculosPorAnio = ventaVehiculos.ordenarVehiculosPorAnio(tablaDeVehiculos);
                    mostrarVehiculos(vehiculosPorAnio);
                    break;

                case 7:
                    System.out.print("Ingrese el modelo: ");
                    String modeloBusqueda = teclado.nextLine();
                    System.out.print("Ingrese el año: ");
                    int anioBusqueda = teclado.nextInt();
                    List<String> placasBuscadas = ventaVehiculos.buscarPlacasPorModeloYAnio(tablaDeVehiculos, modeloBusqueda, anioBusqueda);
                    System.out.println("Placas encontradas para el modelo " + modeloBusqueda + " y el año " + anioBusqueda + ":");
                    for (String placaEncontrada : placasBuscadas) {
                        System.out.println(placaEncontrada);
                    }
                    break;

                case 8:
                    System.out.print("Ingrese la placa del vehículo a comprar: ");
                    String placaCompra = teclado.nextLine();
                    boolean compraExitosa = ventaVehiculos.comprarVehiculo(tablaDeVehiculos, placaCompra);
                    if (compraExitosa) {
                        System.out.println("Vehículo con placa " + placaCompra + " comprado con éxito.");
                    } else {
                        System.out.println("No se pudo comprar el vehículo con placa " + placaCompra + ".");
                    }
                    break;

                case 9:
                    System.out.print("Ingrese el valor máximo para la disminución de precios: ");
                    double valorMaximo = teclado.nextDouble();
                    ventaVehiculos.disminuirPrecioDeVehiculosCaros(tablaDeVehiculos, valorMaximo);
                    System.out.println("Precios disminuidos con éxito.");
                    break;

                case 10:
                    Vehiculo vehiculoMasAntiguo =ventaVehiculos.encontrarVehiculoMasAntiguo(tablaDeVehiculos);
                    if (vehiculoMasAntiguo != null) {
                        System.out.println("Vehículo más antiguo:");
                        System.out.println(vehiculoMasAntiguo);
                    } else {
                        System.out.println("No se encontró ningún vehículo.");
                    }
                    break;

                case 11:
                    Vehiculo vehiculoMasPotente = ventaVehiculos.encontrarVehiculoMasPotente(tablaDeVehiculos);
                    if (vehiculoMasPotente != null) {
                        System.out.println("Vehículo más potente:");
                        System.out.println(vehiculoMasPotente);
                    } else {
                        System.out.println("No se encontró ningún vehículo.");
                    }
                    break;

                case 12:
                    Vehiculo vehiculoMasBarato = ventaVehiculos.encontrarVehiculoMasBarato(tablaDeVehiculos);
                    if (vehiculoMasBarato != null) {
                        System.out.println("Vehículo más barato:");
                        System.out.println(vehiculoMasBarato);
                    } else {
                        System.out.println("No se encontró ningún vehículo.");
                    }
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 0);

        // Cerrar la conexión a la base de datos al finalizar
        con.close();
    }

    private static void mostrarVehiculos(List<Vehiculo> vehiculos) {
        System.out.println("Lista de vehículos:");
        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
    }
}



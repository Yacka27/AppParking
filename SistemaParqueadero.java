import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/* ============================
   CLASE VEHÍCULO
============================ */
class Vehiculo {
    String placa;
    String tipo;
    Date fechaEntrada;
    Date fechaSalida;

    public Vehiculo(String placa, String tipo) {
        this.placa = placa;
        this.tipo = tipo;
        this.fechaEntrada = new Date();
    }

    public void registrarSalida() {
        this.fechaSalida = new Date();
    }
}

/* ============================
   CLASE USUARIO
============================ */
class Usuario {
    String id;
    String nombre;

    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}

/* ============================
   CLASE CELDA
============================ */
class Celda {
    int numero;
    boolean disponible = true;

    public Celda(int numero) {
        this.numero = numero;
    }

    public void ocupar() {
        this.disponible = false;
    }

    public void liberar() {
        this.disponible = true;
    }
}

/* ============================
   CLASE PAGO
============================ */
class Pago {
    String idPago;
    String idUsuario;
    double monto;
    Date fecha;

    public Pago(String idPago, String idUsuario, double monto) {
        this.idPago = idPago;
        this.idUsuario = idUsuario;
        this.monto = monto;
        this.fecha = new Date();
    }

    public void imprimirComprobante() {
        System.out.println("\n=== COMPROBANTE DE PAGO ===");
        System.out.println("ID Pago: " + idPago);
        System.out.println("Usuario: " + idUsuario);
        System.out.println("Monto: $" + monto);
        System.out.println("Fecha: " + fecha.toString());
        System.out.println("============================\n");
    }
}

/* ============================
   SISTEMA PRINCIPAL
============================ */
public class SistemaParqueadero {

    static Map<String, Vehiculo> vehiculos = new HashMap<>();
    static Map<String, Usuario> usuarios = new HashMap<>();
    static List<Celda> celdas = new ArrayList<>();
    static List<Pago> pagos = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Registrar entrada vehículo");
            System.out.println("2. Registrar salida vehículo");
            System.out.println("3. Registrar usuario");
            System.out.println("4. Registrar celda");
            System.out.println("5. Consultar celdas disponibles");
            System.out.println("6. Registrar pago");
            System.out.println("7. Consultar historial de pagos");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Entrada inválida. Intente nuevamente.");
                continue;
            }

            switch (opcion) {
                case 1 -> registrarEntrada();
                case 2 -> registrarSalida();
                case 3 -> registrarUsuario();
                case 4 -> registrarCelda();
                case 5 -> consultarCeldas();
                case 6 -> registrarPago();
                case 7 -> consultarPagos();
                case 8 -> {
                    System.out.println("Saliendo del sistema...");
                    salir = true;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
        sc.close();
    }

    /* ============================
       MÉTODOS DEL SISTEMA
    ============================ */

    static void registrarEntrada() {
        System.out.print("Placa: ");
        String placa = sc.nextLine().trim();

        System.out.print("Tipo (carro/moto): ");
        String tipo = sc.nextLine().trim();

        Vehiculo v = new Vehiculo(placa, tipo);
        vehiculos.put(placa, v);

        System.out.println("Entrada registrada correctamente.");
    }

    static void registrarSalida() {
        System.out.print("Placa del vehículo: ");
        String placa = sc.nextLine().trim();

        Vehiculo v = vehiculos.get(placa);

        if (v != null) {
            v.registrarSalida();
            System.out.println("Salida registrada correctamente.");
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }

    static void registrarUsuario() {
        System.out.print("ID Usuario: ");
        String id = sc.nextLine().trim();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();

        usuarios.put(id, new Usuario(id, nombre));

        System.out.println("Usuario registrado exitosamente.");
    }

    static void registrarCelda() {
        System.out.print("Número de la celda: ");
        int numero;

        try {
            numero = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Número inválido.");
            return;
        }

        celdas.add(new Celda(numero));
        System.out.println("Celda registrada correctamente.");
    }

    static void consultarCeldas() {
        System.out.println("\n--- CELDAS DISPONIBLES ---");
        for (Celda c : celdas) {
            if (c.disponible) {
                System.out.println("Celda #" + c.numero);
            }
        }
    }

    static void registrarPago() {
        System.out.print("ID del pago: ");
        String idPago = sc.nextLine().trim();

        System.out.print("ID Usuario: ");
        String idUsuario = sc.nextLine().trim();

        System.out.print("Monto: ");

        double monto;
        try {
            monto = Double.parseDouble(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Monto inválido.");
            return;
        }

        Pago p = new Pago(idPago, idUsuario, monto);
        pagos.add(p);

        p.imprimirComprobante();
    }

    static void consultarPagos() {
        System.out.print("ID Usuario: ");
        String id = sc.nextLine().trim();

        System.out.println("\n--- PAGOS DEL USUARIO ---");
        for (Pago p : pagos) {
            if (p.idUsuario.equals(id)) {
                p.imprimirComprobante();
            }
        }
    }
}

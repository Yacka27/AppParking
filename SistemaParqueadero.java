import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/* ============================
   CLASE VEHICULO
============================ */
class Vehiculo {
    String placa;
    String tipo;
    Date fechaEntrada;
    Date fechaSalida;
    int celdaAsignada;

    public Vehiculo(String placa, String tipo, int celdaAsignada) {
        this.placa = placa;
        this.tipo = tipo;
        this.celdaAsignada = celdaAsignada;
        this.fechaEntrada = new Date();
    }

    public void registrarSalida() {
        this.fechaSalida = new Date();
    }

    public long getTiempoEnMinutos() {
        if (fechaSalida == null) return 0;
        return (fechaSalida.getTime() - fechaEntrada.getTime()) / 60000;
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
        disponible = false;
    }

    public void liberar() {
        disponible = true;
    }
}

/* ============================
   CLASE PAGO (ITERACIÓN 3)
============================ */
class Pago {
    String idPago;
    Usuario usuario;
    Vehiculo vehiculo;
    double monto;
    Date fechaPago;

    public Pago(String idPago, Usuario usuario, Vehiculo vehiculo, double monto) {
        this.idPago = idPago;
        this.usuario = usuario;
        this.vehiculo = vehiculo;
        this.monto = monto;
        this.fechaPago = new Date();
    }

    public void imprimirComprobante() {
        System.out.println("\n=== COMPROBANTE DE PAGO ===");
        System.out.println("ID Pago: " + idPago);
        System.out.println("Usuario: " + usuario.nombre);
        System.out.println("Placa vehículo: " + vehiculo.placa);
        System.out.println("Celda: " + vehiculo.celdaAsignada);
        System.out.println("Monto: $" + monto);
        System.out.println("Fecha: " + fechaPago);
        System.out.println("==========================\n");
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

    static final double TARIFA_POR_MINUTO = 100; // tarifa base

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
            System.out.println("7. Consultar pagos por usuario");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Opción inválida.");
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
                case 8 -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
        sc.close();
    }

    /* ============================
       MÉTODOS ITERACIÓN 1 Y 2
    ============================ */

    static void registrarEntrada() {
        Celda celdaLibre = celdas.stream()
                .filter(c -> c.disponible)
                .findFirst()
                .orElse(null);

        if (celdaLibre == null) {
            System.out.println("No hay celdas disponibles.");
            return;
        }

        System.out.print("Placa: ");
        String placa = sc.nextLine();
        System.out.print("Tipo (carro/moto): ");
        String tipo = sc.nextLine();

        celdaLibre.ocupar();
        Vehiculo v = new Vehiculo(placa, tipo, celdaLibre.numero);
        vehiculos.put(placa, v);

        System.out.println("Vehículo ingresado en celda " + celdaLibre.numero);
    }

    static void registrarSalida() {
        System.out.print("Placa del vehículo: ");
        String placa = sc.nextLine();
        Vehiculo v = vehiculos.get(placa);

        if (v == null) {
            System.out.println("Vehículo no encontrado.");
            return;
        }

        v.registrarSalida();
        liberarCelda(v.celdaAsignada);
        System.out.println("Salida registrada.");
    }

    static void registrarUsuario() {
        System.out.print("ID Usuario: ");
        String id = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        usuarios.put(id, new Usuario(id, nombre));
        System.out.println("Usuario registrado.");
    }

    static void registrarCelda() {
        System.out.print("Número de celda: ");
        int num = Integer.parseInt(sc.nextLine());
        celdas.add(new Celda(num));
        System.out.println("Celda registrada.");
    }

    static void consultarCeldas() {
        System.out.println("Celdas disponibles:");
        for (Celda c : celdas) {
            if (c.disponible) {
                System.out.println("Celda #" + c.numero);
            }
        }
    }

    /* ============================
       ITERACIÓN 3 – GESTIÓN DE PAGOS
    ============================ */

    static void registrarPago() {
        System.out.print("ID Pago: ");
        String idPago = sc.nextLine();

        System.out.print("ID Usuario: ");
        String idUsuario = sc.nextLine();
        Usuario usuario = usuarios.get(idUsuario);

        if (usuario == null) {
            System.out.println("Usuario no existe.");
            return;
        }

        System.out.print("Placa del vehículo: ");
        String placa = sc.nextLine();
        Vehiculo vehiculo = vehiculos.get(placa);

        if (vehiculo == null || vehiculo.fechaSalida == null) {
            System.out.println("El vehículo no ha registrado salida.");
            return;
        }

        long minutos = vehiculo.getTiempoEnMinutos();
        double monto = minutos * TARIFA_POR_MINUTO;

        Pago pago = new Pago(idPago, usuario, vehiculo, monto);
        pagos.add(pago);
        pago.imprimirComprobante();
    }

    static void consultarPagos() {
        System.out.print("ID Usuario: ");
        String id = sc.nextLine();

        for (Pago p : pagos) {
            if (p.usuario.id.equals(id)) {
                p.imprimirComprobante();
            }
        }
    }

    static void liberarCelda(int numeroCelda) {
        for (Celda c : celdas) {
            if (c.numero == numeroCelda) {
                c.liberar();
                break;
            }
        }
    }
}

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

class Usuario {
    String id;
    String nombre;

    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}

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
        System.out.println("=== COMPROBANTE DE PAGO ===");
        System.out.println("ID Pago: " + idPago);
        System.out.println("Usuario: " + idUsuario);
        System.out.println("Monto: $" + monto);
        System.out.println("Fecha: " + fecha.toString());
    }
}

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
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarEntrada();
                    break;
                case 2:
                    registrarSalida();
                    break;
                case 3:
                    registrarUsuario();
                    break;
                case 4:
                    registrarCelda();
                    break;
                case 5:
                    consultarCeldas();
                    break;
                case 6:
                    registrarPago();
                    break;
                case 7:
                    consultarPagos();
                    break;
                case 8:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    static void registrarEntrada() {
        System.out.print("Placa: ");
        String placa = sc.nextLine();
        System.out.print("Tipo (carro/moto): ");
        String tipo = sc.nextLine();
        Vehiculo v = new Vehiculo(placa, tipo);
        vehiculos.put(placa, v);
        System.out.println("Entrada registrada.");
    }

    static void registrarSalida() {
        System.out.print("Placa: ");
        String placa = sc.nextLine();
        Vehiculo v = vehiculos.get(placa);
        if (v != null) {
            v.registrarSalida();
            System.out.println("Salida registrada.");
        } else {
            System.out.println("Vehículo no encontrado.");
        }
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
        int num = sc.nextInt();
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

    static void registrarPago() {
        System.out.print("ID Pago: ");
        String idPago = sc.nextLine();
        System.out.print("ID Usuario: ");
        String idUsuario = sc.nextLine();
        System.out.print("Monto: ");
        double monto = sc.nextDouble();
        sc.nextLine();
        Pago p = new Pago(idPago, idUsuario, monto);
        pagos.add(p);
        p.imprimirComprobante();
    }

    static void consultarPagos() {
        System.out.print("ID Usuario: ");
        String id = sc.nextLine();
        for (Pago p : pagos) {
            if (p.idUsuario.equals(id)) {
                p.imprimirComprobante();
            }
        }
    }
}

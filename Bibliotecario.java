import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Bibliotecario extends Libros {
    private static final String FILE_NAME = "bibliotecario.txt";

    private String nombre;
    private String correo;
    private String numeroId;

    public Bibliotecario() {
    }

    public Bibliotecario(String nombre, String correo, String numeroId) {
        this.nombre = nombre;
        this.correo = correo;
        this.numeroId = numeroId;
    }

    public void crearBibliotecario() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre del bibliotecario: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el correo del bibliotecario: ");
        String correo = scanner.nextLine();

        String numeroId = generarNumeroIdAleatorioB();

        try {
            FileWriter writer = new FileWriter(FILE_NAME, true);
            writer.write(nombre + "," + correo + "," + numeroId + "\n");
            writer.close();

            System.out.println("El bibliotecario ha sido creado exitosamente!");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
        }
    }

    public void mostrarBibliotecarios() {
        try {
            File file = new File(FILE_NAME);
            Scanner scanner = new Scanner(file);

            System.out.println("Bibliotecarios registrados:");
            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(",");
                System.out.println("|Nombre: " + datos[0]);
                System.out.println("|Correo: " + datos[1]);
                System.out.println("|Numero de Identificacion: " + datos[2]);
                System.out.println();
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado.");
        }
    }

    public void logBibliotecario() {
        Scanner scanner = new Scanner(System.in);
        String respuesta;

        System.out.println("¿Eres un nuevo bibliotecario? (Si/No)");
        respuesta = scanner.next();

        if (respuesta.equalsIgnoreCase("no")) {
            mostrarBibliotecarios();
            System.out.print("Ingrese su Numero de Identificacion: ");
            String numeroId = scanner.next();

            try {
                File file = new File(FILE_NAME);
                Scanner fileScanner = new Scanner(file);
                boolean bibliotecarioEncontrado = false;

                while (fileScanner.hasNextLine()) {
                    String[] datos = fileScanner.nextLine().split(",");
                    if (datos[2].equals(numeroId)) {
                        bibliotecarioEncontrado = true;
                        break;
                    }
                }

                fileScanner.close();

                if (bibliotecarioEncontrado) {
                    System.out.println("Identificacion valida. Acceso permitido.");
                    menuBibliotecario();
                } else {
                    System.out.println("Identificacion no valida. Acceso denegado.");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Archivo no encontrado.");
            }
        } else {
            crearBibliotecario();
            menuBibliotecario();
        }
    }

    private String generarNumeroIdAleatorioB() {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(9000) + 1000;
        return String.valueOf(numeroAleatorio);
    }

    public void bajaLibro() {
        Scanner scanner = new Scanner(System.in);
        String quitarIsbn = "";

        System.out.println("Ingrese el ISBN del libro que desea quitar: ");
        quitarIsbn = scanner.next();

        super.quitarLibro(quitarIsbn);
        menuBibliotecario();
    }

    public void agregarEjemplar() {
        Scanner scanner = new Scanner(System.in);

        int cantidad;
        String isbn = "";

        System.out.println("Ingrese el ISBN del libro al cual le agregara ejemplares: ");
        isbn = scanner.next();
        System.out.println("Ahora ingrese la cantidad de ejemplares que se agregaran: ");
        cantidad = scanner.nextInt();

        super.agregarEjemplares(cantidad, isbn);
        menuBibliotecario();
    }

    public void menuBaja() {
        Scanner scanner = new Scanner(System.in);

        int opcion;

        do {
            System.out.println("| Motivo de baja:          |");
            System.out.println("============================");
            System.out.println("| 1. Maltrato              |");
            System.out.println("| 2. Robo                  |");
            System.out.println("| 3. Antigüedad            |");
            System.out.println("| Seleccione una opcion:   |");
            System.out.println("============================");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    bajaLibro();
                    break;
                case 2:
                    bajaLibro();
                    break;
                case 3:
                    bajaLibro();
                    break;
                default:
                    System.out.println("opcion invalida.");
                    break;
            }
        } while (opcion < 1 || opcion > 3);
    }

    public void bListaEspera() {
        Alumno alumno = new Alumno();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el AlumnoId: ");
        String alumnoId = scanner.next();

        //alumno.quitarListaEspera(alumnoId);
        menuBibliotecario();
    }

    public void librero() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ISBN del libro: ");
        String isbn = scanner.nextLine();

        System.out.print("Ingrese el titulo del libro: ");
        String titulo = scanner.nextLine();

        System.out.print("Ingrese el autor del libro: ");
        String autor = scanner.nextLine();

        System.out.print("Ingrese la editorial del libro: ");
        String editorial = scanner.nextLine();

        System.out.print("Ingrese el anio de publicacion del libro: ");
        int anioPublicacion = scanner.nextInt();

        System.out.println("Ingrese los ejemplares");
        int ejemplares = scanner.nextInt();

        // Aquí puedes agregar el código necesario para guardar el nuevo libro en la lista de libros
        try {
            FileWriter writer = new FileWriter("libros.txt", true);
            writer.write(isbn + "," + titulo + "," + autor + "," + editorial + "," + anioPublicacion + "," + ejemplares + ",0" + "\n");
            writer.close();

            System.out.println("El libro ha sido agregado exitosamente!");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo libros.txt");
        }
    }

    public void menuBibliotecario() {
        Scanner scanner = new Scanner(System.in);
        int op;
        boolean flag = true;

        do {
            System.out.println("***************Menu de bibliotecario*****************");
            System.out.println("=====================================================");
            System.out.println("| 1. Mostar libros registrados                      |");
            System.out.println("| 2. Aniadir ejemplares                             |");
            System.out.println("| 3. Dar de baja un libro                           |");            
            System.out.println("| 4. Ver lista de espera                            |");
            System.out.println("| 5. Mostrar los bibliotecarios registrados         |");
            System.out.println("| 6. Agregar un nuevo libro                         |");
            System.out.println("| 7. Volver al menu principal                       |");
            System.out.println("| 8. Salir                                          |");
            System.out.println("=====================================================");
            op = scanner.nextInt();

            switch (op) {
                case 1:
                    mostrarLibros();
                    break;
                case 2:
                    agregarEjemplar();
                    break;
                case 3:
                    bajaLibro();
                    break;
                case 4:
                    bListaEspera();
                    break;
                case 5:
                    mostrarBibliotecarios();
                    break;
                case 6:
                    librero();
                    break;
                case 7:
                    System.out.println("Regresando al menu principal...");
                    flag = false;
                    return;
                case 8:
                    System.out.println("Cerrando programa...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("opcion invalida.");
                    break;
            }
        } while (flag);
    }
}

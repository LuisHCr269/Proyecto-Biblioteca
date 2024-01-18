import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;

public class Alumno extends Libros {

    private String nombre;
    private String correo;
    private String numeroTelefono;
    private String numeroId;
    private int cantidadLibros;

    private static int contadorAlumnos = 0;
    private static Alumno AlumnoActual;
    private static int contadorEspera = 0;

    private ArrayList<String> librosPrestados = new ArrayList<>();

    public Alumno() {
    }

    public Alumno(String nombre, String correo, String numeroTelefono, String numeroId) {
        super();
        this.nombre = nombre;
        this.correo = correo;
        this.numeroTelefono = numeroTelefono;
        this.numeroId = numeroId;
        this.cantidadLibros = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getNumeroId() {
        return numeroId;
    }

    public int getCantidadLibros() {
        return cantidadLibros;
    }

    public void mostrarLibrosPrestados() {
        if (cantidadLibros == 0) {
            System.out.println("No tienes libros prestados.");
            return;
        }

        System.out.println("Libros prestados: ");
        try {
            File file = new File("libros.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(",");
                if (librosPrestados.contains(datos[0])) {
                    System.out.println(".- ISBN: " + datos[0] + ", Titulo: " + datos[1] + ", Autor: " + datos[2] + ", Editorial: "
                            + datos[3]);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
    }


    public void crearAlumno() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa tu nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingresa un correo electronico: ");
        String correo = scanner.nextLine();

        System.out.print("Ingresa tu numero de telefono: ");
        String numeroTelefono = scanner.nextLine();

        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("alumnos.txt"))) {
            while (br.readLine() != null) {
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        contadorAlumnos = contador + 1;
        String numeroId = String.valueOf(contadorAlumnos);

        AlumnoActual = new Alumno(nombre, correo, numeroTelefono, numeroId);
        guardarAlumno(AlumnoActual);

        System.out.println("Tu ID es: " + numeroId);
    }

    public void guardarAlumno(Alumno alumno) {
        try {
            FileWriter writer = new FileWriter("alumnos.txt", true);
            StringBuilder sb = new StringBuilder();
            sb.append(alumno.getNombre()).append(",");
            sb.append(alumno.getCorreo()).append(",");
            sb.append(alumno.getNumeroTelefono()).append(",");
            sb.append(alumno.getNumeroId()).append(",");

            // Agregar los ISBN de los libros prestados al texto
            for (int i = 0; i < 3; i++) {
                String libroPrestado;
                if (i < alumno.librosPrestados.size()) {
                    libroPrestado = alumno.librosPrestados.get(i);
                } else {
                    libroPrestado = "0";
                }
                sb.append(libroPrestado).append(",");
            }

            sb.append("\n");

            writer.write(sb.toString());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logAlumno() {
        Scanner scanner = new Scanner(System.in);
        String numIdentificacion = "";
        String palabra = "";
        boolean alumnoEncontrado = false;

        do {
            System.out.print("¿Eres alumno nuevo? (Si/No): ");
            palabra = scanner.next();

            if (palabra.equalsIgnoreCase("Si")) {
                crearAlumno();
                menuAlumno();
                break;
            } else if (palabra.equalsIgnoreCase("No")) {
                mostrarAlumnos();

                do {
                    System.out.print("Ingresa tu numero de identificacion: ");
                    numIdentificacion = scanner.next();

                    if (existeAlumno(numIdentificacion)) {
                        alumnoEncontrado = true;
                        break;
                    } else {
                        System.out.println("Numero de identificacion no valido. Intente nuevamente.");
                    }
                } while (!alumnoEncontrado);

                menuAlumno();
                break;
            } else {
                System.out.println("opcion invalida. Intente nuevamente.");
            }
        } while (true);
    }

    public void menuAlumno() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("------- Menu del Alumno -------");
            System.out.println("===============================");
            System.out.println("|1. Mostrar libros prestados  |");
            System.out.println("|2. Prestamo de libro         |");
            System.out.println("|3. Devolver libro            |");
            System.out.println("|4. Salir                     |");
            System.out.println("|Ingrese una opcion:          |");
            System.out.println("===============================");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    mostrarLibrosPrestados();
                    break;
                case 2:
                    prestarLibro();
                    break;
                case 3:
                    devolverLibro();
                    break;
                case 4:
                    System.out.println("Saliendo del menu del alumno...");
                    return;
                default:
                    System.out.println("opcion invalida. Intente nuevamente.");
            }
        } while (true);
    }

    public void mostrarAlumnos() {
        try {
            File file = new File("alumnos.txt");
            Scanner scanner = new Scanner(file);

            System.out.println("Lista de alumnos:");
            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(",");
                if (datos.length >= 4) {
                    System.out.println("| Nombre: " + datos[0]);
                    System.out.println("| Correo: " + datos[1]);
                    System.out.println("| Numero de telefono: " + datos[2]);
                    System.out.println("| Numero de identificacion: " + datos[3]);
                    System.out.println("--------------------");
                } else {
                    System.out.println("Datos de alumno incompletos en la linea actual.");
    }
}
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo de alumnos.");
        }
    }

    public boolean existeAlumno(String numeroId) {
        try {
            File file = new File("alumnos.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(",");
                if (datos.length >= 4 && datos [3].equals(numeroId)) {
                    scanner.close();
                    return true;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo de alumnos.");
        }

        return false;
    }

    public void prestarLibro() {
        if (cantidadLibros >= 3) {
            System.out.println("Lo sentimos. Ya tienes el máximo de libros prestados.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ISBN del libro: ");
        String isbn = scanner.nextLine();

        try {
            File file = new File("libros.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean libroEncontrado = false;

            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (Integer.parseInt(datos[0]) == Integer.parseInt(isbn)) {
                    if (Integer.parseInt(datos[6]) < Integer.parseInt(datos[5])) {
                        librosPrestados.add(isbn);  // Agregar el ISBN al ArrayList
                        int prestamos = Integer.parseInt(datos[6]);
                        prestamos++;
                        datos[6] = String.valueOf(prestamos);
                        libroEncontrado = true;
                        System.out.println("Libro prestado exitosamente.");
                        cantidadLibros++; // Incrementar la cantidad de libros prestados

                        /*datos.setFechaPrestamo(LocalDate.now());
                        // Calcular la fecha de entrega
                        LocalDate fechaEntrega = datos.getFechaPrestamo().plusDays(5);

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        System.out.println("Debes entregar el libro el " + fechaEntrega.format(formatter));*/
                        //guardar("libreria.dat");

                        /*libro.setFechaPrestamo(LocalDate.now());
                        // Calcular la fecha de entrega
                        LocalDate fechaEntrega = libro.getFechaPrestamo().plusDays(5);

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        System.out.println("Debes entregar el libro el " + fechaEntrega.format(formatter));
                        //guardar("libreria.dat");
                        
                    } else {
                        System.out.println("Ejemplares no disponibles para préstamo.");
                    }
                }
                writer.write(String.join(",", datos));
                writer.newLine();
            }

            reader.close();
            writer.close();

            if (libroEncontrado) {
                file.delete();
                tempFile.renameTo(file);
                // Actualizar el archivo de alumnos.txt
                actualizarAlumno();
            } else {
                tempFile.delete();
                System.out.println("Libro no encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer o escribir en el archivo.");
        }
    }*/

        } else {
            System.out.println("El libro no está disponible actualmente. Se ha agregado a la lista de espera.");
                                contadorEspera++;
                                // Agregar el ISBN a la lista de espera
                                BufferedWriter esperaWriter = new BufferedWriter(new FileWriter("listaEspera.txt", true));
                                esperaWriter.write(isbn);
                                esperaWriter.newLine();
                                esperaWriter.close();
                            }
                        }
                        writer.write(String.join(",", datos));
                        writer.newLine();
                    }

                    if (!libroEncontrado) {
                        System.out.println("No se encontró un libro con ese ISBN.");
                    }

                    reader.close();
                    writer.close();

                    file.delete();
                    tempFile.renameTo(file);

                } catch (FileNotFoundException e) {
                    System.out.println("Archivo de libros no encontrado.");
                } catch (IOException e) {
                    System.out.println("Error al leer/escribir el archivo.");
                }
            }

    private void actualizarAlumno() {
        try {
            File file = new File("alumnos.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos[3].equals(numeroId)) {
                    datos[4] = String.valueOf(cantidadLibros);
                }
                writer.write(String.join(",", datos));
                writer.newLine();
            }

            reader.close();
            writer.close();

            file.delete();
            tempFile.renameTo(file);
        } catch (IOException e) {
            System.out.println("Error al leer o escribir en el archivo.");
        }
    }


    public void devolverLibro() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ISBN del libro a devolver: ");
        String isbn = scanner.nextLine();

        if (librosPrestados.contains(isbn)) {
            librosPrestados.remove(isbn); // Remover el ISBN del ArrayList
            cantidadLibros--; // Decrementar la cantidad de libros prestados

            try {
                File file = new File("libros.txt");
                File tempFile = new File("temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String line;

                while ((line = reader.readLine()) != null) {
                    String[] datos = line.split(",");
                    if (Integer.parseInt(datos[0]) == Integer.parseInt(isbn)) {
                        int prestamos = Integer.parseInt(datos[6]);
                        prestamos = prestamos - 1;
                        datos[6] = String.valueOf(prestamos);
                    }
                    writer.write(String.join(",", datos));
                    writer.newLine();
                }

                reader.close();
                writer.close();

                file.delete();
                tempFile.renameTo(file);
                actualizarAlumno(); // Llamar a actualizarAlumno() aquí

                System.out.println("Libro devuelto exitosamente.");
            } catch (IOException e) {
                System.out.println("Error al leer o escribir en el archivo.");
            }
        } else {
            System.out.println("No tienes prestado un libro con ese ISBN.");
        }
    }

}

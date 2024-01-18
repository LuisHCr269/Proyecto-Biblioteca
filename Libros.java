import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDate;

public class Libros {
    private static final String FILE_NAME = "libros.txt";

    private String titulo, autor, editorial, isbn;
    private int ejemplares, prestados = 0;
    private LocalDate fechaPrestamo;

    public Libros() {

    }

    public Libros(String isbn, String titulo, String autor, String editorial, int ejemplares) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.ejemplares = ejemplares;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(int ejemplares) {
        this.ejemplares = ejemplares;
    }

    public int getPrestados() {
        return prestados;
    }

    public void setPrestados(int prestados) {
        this.prestados = prestados;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void crearLibro() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ISBN del libro: ");
        String isbn = scanner.nextLine();

        System.out.print("Ingrese el titulo del libro: ");
        String titulo = scanner.nextLine();

        System.out.print("Ingrese el autor del libro: ");
        String autor = scanner.nextLine();

        System.out.print("Ingrese la editorial del libro: ");
        String editorial = scanner.nextLine();

        System.out.print("Ingrese los ejemplares: ");
        int ejemplares = scanner.nextInt();

        Libros nuevoLibro = new Libros(isbn, titulo, autor, editorial, ejemplares);

        agregarLibro(nuevoLibro);
    }

    public void agregarLibro(Libros libro) {
        try {
            FileWriter writer = new FileWriter(FILE_NAME, true);
            writer.write(
                    libro.getIsbn() + "," + libro.getTitulo() + "," + libro.getAutor() + "," + libro.getEditorial()
                            + "," + libro.getEjemplares() + libro.getPrestados() + "\n");
            writer.close();
            System.out.println("Libro agregado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
        }
    }

    public Libros extraerLibro(String isbn) {
        try {
            File file = new File(FILE_NAME);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(",");
                if (datos[0].equals(isbn)) {
                    Libros libroEncontrado = new Libros(datos[0], datos[1], datos[2], datos[3],
                            Integer.parseInt(datos[4]));
                    scanner.close();
                    return libroEncontrado;
                }
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }

        return null; // No se encontró ningún libro con el ISBN especificado
    }

    public void agregarEjemplares(int cantidad, String isbn) {
        try {
            File file = new File(FILE_NAME);
            Scanner scanner = new Scanner(file);
            StringBuilder contenidoNuevo = new StringBuilder();

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split(",");
                if (datos[0].equals(isbn)) {
                    int ejemplaresActuales = Integer.parseInt(datos[5]);
                    ejemplaresActuales += cantidad;
                    datos[6] = "0";
                    linea = datos[0] + "," + datos[1] + "," + datos[2] + "," + datos[3] + "," + datos [4] + "," + ejemplaresActuales + "," + datos[6];
                }
                contenidoNuevo.append(linea).append("\n");
            }

            scanner.close();

            FileWriter writer = new FileWriter(FILE_NAME);
            writer.write(contenidoNuevo.toString());
            writer.close();

            System.out.println("Se han agregado " + cantidad + " ejemplares del libro con ISBN " + isbn);
            mostrarLibros();
        } catch (IOException e) {
            System.out.println("Error al leer o escribir en el archivo.");
        }
    }

    public void quitarLibro(String isbn) {
        try {
            File file = new File(FILE_NAME);
            Scanner scanner = new Scanner(file);
            StringBuilder contenidoNuevo = new StringBuilder();

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split(",");
                if (!datos[0].equals(isbn)) {
                    contenidoNuevo.append(linea).append("\n");
                }
            }

            scanner.close();

            FileWriter writer = new FileWriter(FILE_NAME);
            writer.write(contenidoNuevo.toString());
            writer.close();

            System.out.println("Se ha dado de baja el libro con ISBN " + isbn);
            mostrarLibros();
        } catch (IOException e) {
            System.out.println("Error al leer o escribir en el archivo.");
        }
    }

    public void mostrarLibros() {
        try {
            File file = new File(FILE_NAME);
            Scanner scanner = new Scanner(file);

            System.out.println("Lista de libros:");

            int contador = 1;
            while (scanner.hasNextLine()) {
            String[] datos = scanner.nextLine().split(",");
            
            // Verificar si datos tiene la cantidad correcta de elementos
            if (datos.length >= 7) {
                System.out.println(contador + ".- ISBN: " + datos[0] + ", Titulo: " + datos[1] + ", Autor: "
                        + datos[2] + ", Editorial: " + datos[3] + ", Anio: " + datos[4] + ", Ejemplares: " + datos[5] + ", Prestados: " + datos[6]);
                contador++;
            } else {
                System.out.println("Error: la línea no contiene todos los datos esperados.");
            }

        }

            scanner.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }

        }



    /*public void mostrarLibros() {
        try {
            File file = new File(FILE_NAME);
            Scanner scanner = new Scanner(file);

            System.out.println("Lista de libros:");

            int contador = 1;
            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(",");
                System.out.println(contador + ".- ISBN: " + datos[0] + ", Titulo: " + datos[1] + ", Autor: "
                        + datos[2] + ", Editorial: " + datos[3] + ", Anio: " + datos[4] + ", Ejemplares: " + datos[5] + ", Prestados: " + datos[6]);
                contador++;
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
    }*/
}



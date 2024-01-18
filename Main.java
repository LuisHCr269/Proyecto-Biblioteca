import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int op = 0;
        boolean flag = true;
        Alumno user = new Alumno();
        Libros libro = new Libros();
        Bibliotecario bibliotecario = new Bibliotecario();

        System.out.println("");
        System.out.println("Bienvenido a la Biblioteca JOSE VASCONCELOS ");
        System.out.println("");

        do {
            System.out.println("Selecciona una forma de ingresar: ");
            menu();
            op= scanner.nextInt();
            if (op == 1){
                libro.mostrarLibros();
                user.logAlumno();
                
            } else if (op == 2) {
                libro.mostrarLibros();
                bibliotecario.logBibliotecario();
                
            } else if (op == 3) {
                flag = false; //Para terminar el programa
            }
            else{
                System.out.println("Opcion invalida");

            }
        }while(flag);


    }

    public static void menu(){
        System.out.println("|===================|");
        System.out.println("| 1. Alumno         |"); 
        System.out.println("| 2. Bibliotecario  |");
        System.out.println("| 3. Salir          |");
        System.out.println("|===================|");
    }
}
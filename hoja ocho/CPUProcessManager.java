import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CPUProcessManager {

    // Método para manejar la atención de procesos utilizando la implementación proporcionada por Java Collection Framework
    public static void manageWithPriorityQueue(String fileName) {
        try {
            PriorityQueue<Proceso> priorityQueue = new PriorityQueue<>();
            Scanner scanner = new Scanner(new File(fileName));

            // Leer los datos del archivo y agregar los procesos a la cola de prioridad
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                String nombreProceso = data[0];
                String nombreUsuario = data[1];
                int valorNice = Integer.parseInt(data[2]);
                priorityQueue.add(new Proceso(nombreProceso, nombreUsuario, valorNice));
            }
            scanner.close();

            // Atender los procesos en orden de prioridad
            while (!priorityQueue.isEmpty()) {
                Proceso proceso = priorityQueue.poll();
                System.out.println(proceso.getNombreProceso() + "," + proceso.getNombreUsuario() +
                                   "," + proceso.getValorNice() + ", PR = " + (20 + proceso.getValorNice()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para manejar la atención de procesos utilizando la implementación HeapUsingIterativeBinaryTree
    public static void manageWithHeap(String fileName) {
        HeapUsingIterativeBinaryTree<Integer, Proceso> heap = new HeapUsingIterativeBinaryTree<>(Integer::compare);
        try {
            Scanner scanner = new Scanner(new File(fileName));

            // Leer los datos del archivo y agregar los procesos al heap
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                String nombreProceso = data[0];
                String nombreUsuario = data[1];
                int valorNice = Integer.parseInt(data[2]);
                heap.Insert(20 + valorNice, new Proceso(nombreProceso, nombreUsuario, valorNice));
            }
            scanner.close();

            // Atender los procesos en orden de prioridad
            while (heap.count() > 0) {
                Proceso proceso = heap.remove();
                System.out.println(proceso.getNombreProceso() + "," + proceso.getNombreUsuario() +
                                   "," + proceso.getValorNice() + ", PR = " + (20 + proceso.getValorNice()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fileName = "procesos.txt";
        System.out.println("Atendiendo procesos con PriorityQueue:");
        manageWithPriorityQueue(fileName);
        System.out.println("\nAtendiendo procesos con HeapUsingIterativeBinaryTree:");
        manageWithHeap(fileName);
    }
}

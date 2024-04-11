import java.util.ArrayList;
import java.util.Comparator;

public class HeapUsingIterativeBinaryTree<P, V> implements IHeap<P, V> {

    private int _count;
    private TreeNode<P, V> _root;
    private Comparator<P> _priorityComparator;

    public HeapUsingIterativeBinaryTree(Comparator<P> priorityComparator) {
        _count = 0;
        _priorityComparator = priorityComparator;
    }

    @Override
    public void Insert(P priority, V value) {
        TreeNode<P, V> newNode = new TreeNode<>(priority, value);
        
        if (count() == 0) { //Inserto en la raiz
            _count++;
            _root = newNode;
        } else {
            _count++;
            
            byte[] movements = convertToBinary(_count);
            
            int index = 1;
            TreeNode<P, V> current = _root;
            
            while (index < movements.length) {
                
                if (index == movements.length - 1) {
                    if (movements[index] == 0) { //Inserto en el hijo izquierdo
                        current.setLeft(newNode);
                    } else { //Inserto en el hijo derecho
                        current.setRight(newNode);
                    }
                    newNode.setParent(current);
                } else {
                    if (movements[index] == 0) { //Inserto en el hijo izquierdo
                        current = current.getLeft();
                    } else { //Inserto en el hijo derecho
                        current = current.getRight();
                    }
                }
                
                index++;
            }
            
            //Hacer Swap
            current = newNode;
            while ((current != null) && (current.getParent() != null)) {
                swap(current);
                current = current.getParent();
            }
        }
    }
    
    private byte[] convertToBinary(int value) {
        
        ArrayList<Byte> listBytes = new ArrayList<>();
        
        while (value > 0) {
            listBytes.add((byte)(value % 2));
            value = value / 2;
        }
        
        byte[] binaryBytes = new byte[listBytes.size()]; 
        
        for (int i = listBytes.size() - 1; i >= 0; i--) {
            binaryBytes[listBytes.size() - 1 - i] = listBytes.get(i);  
        }
        
        return binaryBytes;
    }
    
    private void swap(TreeNode<P, V> currentNode) {
        if (currentNode != null) {
            if (currentNode.getParent() != null) { //No es la raiz
                int result = _priorityComparator.compare(currentNode.getPriority(), currentNode.getParent().getPriority());
                if (result > 0) { //Si el hijo es mayor que el padre
                    P tempPriority = currentNode.getPriority();
                    V tempValue = currentNode.getValue();
                    
                    currentNode.setPriority(currentNode.getParent().getPriority());
                    currentNode.setValue(currentNode.getParent().getValue());
                    
                    currentNode.getParent().setPriority(tempPriority);
                    currentNode.getParent().setValue(tempValue);
                }
            }
        }
    }
    
    @Override
    public V get() {
        if (isEmpty())
            return null;
        else 
            return _root.getValue();
    }

    @Override
    public V remove() {
        
        if (isEmpty()) {
            return null;
        }
        
        if (count() == 1) {
            _count--;
            TreeNode<P, V> tempNode = _root;
            _root = null;
            return tempNode.getValue();
        }
        
        //Si hay más de un elemento
        
        byte[] movements = convertToBinary(_count);
        
        int index = 1;
        TreeNode<P, V> current = _root;
        TreeNode<P, V> nodeToBeDeleted = null;
        
        while (index < movements.length) {
            
            if (index == movements.length - 1) {
                if (movements[index] == 0) { //Inserto en el hijo izquierdo
                    nodeToBeDeleted = current.getLeft();
                } else { //Inserto en el hijo derecho
                    nodeToBeDeleted = current.getRight();
                }
                
            } else {
                if (movements[index] == 0) { //Inserto en el hijo izquierdo
                    current = current.getLeft();
                } else { //Inserto en el hijo derecho
                    current = current.getRight();
                }
            }
            
            index++;
        }
        
        //Hacer Swap de la hoja con la raiz
        P tempPriority = nodeToBeDeleted.getPriority();
        V tempValue = nodeToBeDeleted.getValue();
        
        nodeToBeDeleted.setPriority(_root.getPriority());
        nodeToBeDeleted.setValue(_root.getValue());
        
        _root.setPriority(tempPriority);
        _root.setValue(tempValue);
        
        
        //Eliminar el nodo hoja
        tempPriority = nodeToBeDeleted.getPriority();
        tempValue = nodeToBeDeleted.getValue();
        
        TreeNode<P, V> parent = nodeToBeDeleted.getParent(); 
        if (parent.getLeft() == nodeToBeDeleted)
            parent.setLeft(null);
        else 
            parent.setRight(null);
        
        
        //Buscar el lugar de inserción
        current = _root;
        
        while (current != null) {
            
            boolean currentHasLeftChild = current.getLeft() != null;
            boolean currentHasRightChild = current.getRight() != null;
            
            if (currentHasLeftChild && currentHasRightChild) { //Tiene a los dos hijos
                
                int result = _priorityComparator.compare(current.getLeft().getPriority(), current.getRight().getPriority());
                
                if (result == 0) { //Son iguales
                    
                    result = _priorityComparator.compare(current.getPriority(), current.getLeft().getPriority());
                    if (result < 0) {
                        P tempPriority2 = current.getPriority();
                        V tempValue2 = current.getValue();
                        
                        current.setPriority(current.getLeft().getPriority());
                        current.setValue(current.getLeft().getValue());
                        
                        current.getLeft().setPriority(tempPriority2);
                        current.getLeft().setValue(tempValue2);
                        
                        current = current.getLeft();
                    } else {
                        break;
                    }
                    
                } else if (result > 0){ //El hijo izquierdo es mayor
                    result = _priorityComparator.compare(current.getPriority(), current.getLeft().getPriority());
                    if (result < 0) {
                        P tempPriority2 = current.getPriority();
                        V tempValue2 = current.getValue();
                        
                        current.setPriority(current.getLeft().getPriority());
                        current.setValue(current.getLeft().getValue());
                        
                        current.getLeft().setPriority(tempPriority2);
                        current.getLeft().setValue(tempValue2);
                        current = current.getLeft();
                    } else {
                        break;
                    }
                } else {
                    result = _priorityComparator.compare(current.getPriority(), current.getRight().getPriority());
                    if (result < 0) {
                        P tempPriority2 = current.getPriority();
                        V tempValue2 = current.getValue();
                        
                        current.setPriority(current.getRight().getPriority());
                        current.setValue(current.getRight().getValue());
                        
                        current.getRight().setPriority(tempPriority2);
                        current.getRight().setValue(tempValue2);
                        current = current.getRight();
                    } else {
                        break;
                    }
                }
                
            } else if (!currentHasLeftChild && !currentHasRightChild){ //No tiene hijos
                break;
            } else if (currentHasLeftChild){ //Solo tiene hijo izquierdo
                int result = _priorityComparator.compare(current.getPriority(), current.getLeft().getPriority());
                if (result < 0) {
                    P tempPriority2 = current.getPriority();
                    V tempValue2 = current.getValue();
                    
                    current.setPriority(current.getLeft().getPriority());
                    current.setValue(current.getLeft().getValue());
                    
                    current.getLeft().setPriority(tempPriority2);
                    current.getLeft().setValue(tempValue2);
                } else {
                    break;
                }
            } else { //Solo tiene hijo derecho
                int result = _priorityComparator.compare(current.getPriority(), current.getRight().getPriority());
                if (result < 0) {
                    P tempPriority2 = current.getPriority();
                    V tempValue2 = current.getValue();
                    
                    current.setPriority(current.getRight().getPriority());
                    current.setValue(current.getRight().getValue());
                    
                    current.getRight().setPriority(tempPriority2);
                    current.getRight().setValue(tempValue2);
                } else {
                    break;
                }
            }
        }
        
        //Reducir la cantidad
        _count--;
        
        return tempValue;
    }

    @Override
    public int count() {
        return _count;
    }

    @Override
    public boolean isEmpty() {
        return _count == 0;
    }
}
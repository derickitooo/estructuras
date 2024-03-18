package metodos;

import java.util.List;
import java.util.function.Function;

import Interfaces.IMetodos;
import Interpretador.Aritmeticas;

public class InformacionMetodos {
    private String operadores;
    private List<String> nombreArgumentos;
    private Function<IMetodos, Aritmeticas> Interno;

    public InformacionMetodos(String operadores, List<String> nombreArgumentos, Function<IMetodos, Aritmeticas> Interno) {
        this.operadores = operadores;
        this.nombreArgumentos = nombreArgumentos;
        this.Interno = Interno;
    }

    public String getOperadores() {
        return operadores;
    }

    public List<String> getNombreArgumentos() {
        return nombreArgumentos;
    }

    public Function<IMetodos, Aritmeticas> getInterno() {
        return Interno;
    }
}

import simpy
import random
import statistics
import matplotlib.pyplot as plt

#   Algoritmos y estructuras/ Hoja de trabajo 6
# Derick Delva 211669

RANDOM_SEED = 42
random.seed(RANDOM_SEED)

class SistemaOperativo:
    def __init__(self, env, num_procesos, intervalo_llegada):
        self.env = env
        self.cpu = simpy.Resource(env, capacity=1)
        self.ram = simpy.Container(env, init=100, capacity=100)
        self.procesos = num_procesos
        self.intervalo_llegada = intervalo_llegada
        self.tiempos_en_computadora = []

    def llegada_proceso(self):
        for i in range(self.procesos):
            yield self.env.timeout(random.expovariate(1.0 / self.intervalo_llegada))
            self.env.process(self.proceso("Proceso %d" % i))

    def proceso(self, nombre):
        memoria_necesaria = random.randint(1, 10)
        instrucciones_a_realizar = 3
        with self.ram.get(memoria_necesaria) as req:
            yield req

            llegada = self.env.now
            print("%s llegó a la computadora en %.2f" % (nombre, llegada))

            with self.cpu.request() as req_cpu:
                yield req_cpu
                print("%s inicia ejecución en %.2f" % (nombre, self.env.now))
                while instrucciones_a_realizar > 0:
                    yield self.env.timeout(1)  # Simulamos una unidad de tiempo de ejecución
                    instrucciones_a_realizar -= 1

                print("%s deja la CPU en %.2f" % (nombre, self.env.now))

                if random.randint(1, 21) == 1:
                    # 1/21 de probabilidad de ir a Waiting
                    tiempo_espera = random.randint(1, 21)
                    print("%s va a Waiting por %d unidades de tiempo" % (nombre, tiempo_espera))
                    yield self.env.timeout(tiempo_espera)
                    print("%s regresa a Ready en %.2f" % (nombre, self.env.now))

            salida = self.env.now
            tiempo_en_computadora = salida - llegada
            print("%s termina en %.2f. Tiempo en la computadora: %.2f" % (nombre, salida, tiempo_en_computadora))
            self.tiempos_en_computadora.append(tiempo_en_computadora)
            self.ram.put(memoria_necesaria)

def ejecutar_simulacion(num_procesos, intervalo_llegada):
    env = simpy.Environment()
    sistema_operativo = SistemaOperativo(env, num_procesos, intervalo_llegada)
    env.process(sistema_operativo.llegada_proceso())
    env.run()

    promedio_tiempo = statistics.mean(sistema_operativo.tiempos_en_computadora)
    desviacion_estandar = statistics.stdev(sistema_operativo.tiempos_en_computadora)

    return promedio_tiempo, desviacion_estandar

def graficar_resultados(intervalos, promedios, desviaciones):
    plt.plot(intervalos, promedios, marker='o', linestyle='-', color='b', label='Promedio de Tiempo')
    plt.errorbar(intervalos, promedios, yerr=desviaciones, linestyle='None', marker='o', color='b')
    plt.xlabel('Número de Procesos')
    plt.ylabel('Tiempo Promedio en la Computadora')
    plt.title('Simulación de Sistema Operativo')
    plt.legend()
    plt.show()

# Simulación con diferentes números de procesos y tiempo de llegada
num_procesos_lista = [25, 50, 100, 150, 200]
intervalos_llegada_lista = [10, 5, 1]

resultados_promedio = []
resultados_desviacion = []

for intervalo in intervalos_llegada_lista:
    promedios = []
    desviaciones = []

    for num_procesos in num_procesos_lista:
        promedio, desviacion = ejecutar_simulacion(num_procesos, intervalo)
        promedios.append(promedio)
        desviaciones.append(desviacion)

    resultados_promedio.append(promedios)
    resultados_desviacion.append(desviaciones)

# Graficar resultados
for i, intervalo in enumerate(intervalos_llegada_lista):
    graficar_resultados(num_procesos_lista, resultados_promedio[i], resultados_desviacion[i])

#
class SistemaOperativoDosProcesadores(SistemaOperativo):
    def __init__(self, env, num_procesos, intervalo_llegada):
        super().__init__(env, num_procesos, intervalo_llegada)
        self.cpu = simpy.Resource(env, capacity=2)  # Cambio a 2 procesadores

def ejecutar_simulacion_dos_procesadores(num_procesos, intervalo_llegada):
    env = simpy.Environment()
    sistema_operativo = SistemaOperativoDosProcesadores(env, num_procesos, intervalo_llegada)
    env.process(sistema_operativo.llegada_proceso())
    env.run()

    promedio_tiempo = statistics.mean(sistema_operativo.tiempos_en_computadora)
    desviacion_estandar = statistics.stdev(sistema_operativo.tiempos_en_computadora)

    return promedio_tiempo, desviacion_estandar

# Simulación con dos procesadores después de cambiar la velocidad del procesador
num_procesos_lista = [25, 50, 100, 150, 200]

resultados_promedio_dos_procesadores = []
resultados_desviacion_dos_procesadores = []

for num_procesos in num_procesos_lista:
    promedio, desviacion = ejecutar_simulacion_dos_procesadores(num_procesos, 10)  # Cambio a intervalo de 10
    resultados_promedio_dos_procesadores.append(promedio)
    resultados_desviacion_dos_procesadores.append(desviacion)

# Graficar resultados con dos procesadores
plt.plot(num_procesos_lista, resultados_promedio_dos_procesadores, marker='o', linestyle='-', color='r', label='Promedio de Tiempo (2 procesadores)')
plt.errorbar(num_procesos_lista, resultados_promedio_dos_procesadores, yerr=resultados_desviacion_dos_procesadores, linestyle='None', marker='o', color='r')

plt.xlabel('Número de Procesos')
plt.ylabel('Tiempo Promedio en la Computadora')
plt.title('Simulación de Sistema Operativo con Dos Procesadores')
plt.legend()
plt.show()

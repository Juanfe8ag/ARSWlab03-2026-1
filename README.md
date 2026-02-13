# ARSW Lab03 2026-1
## Autor: Juan Felipe Ochoa Guerrero

## Primera parte: Busy_wait vs Wait_notify
Para responder la primera pregunta se ejecutó el programa con el modo 'spin'. Este modo, según los datos proyectados por JVisualVM,
en los 20 segundos de ejecución tuvo picos de hasta el 5% de CPU. Esto se debe a que el consumidor siempre está activo
revisando si hay algún objeto en cola para poder consumirlo, lo que hace que el 100% del tiempo de ejecución el productor
esté consumiendo recursos.

Luego, ejecutando el programa con el modo 'monitor', los datos indicaron que solo se usó un máximo de 1% de CPU en el proceso,
esto debido a que la espera destacada en el anterior punto fue eliminada y reemplazada por un tiempo donde el hilo consumidor
pasa a estado de waiting. 

La explicación posterior para cuando se cambian también los tiempos de espera de consumidor y productor son:

- Productor lento/Consumidor rápido: El productor hace un notifyAll() para cuando añade un producto a la cola, mientras no 
pase eso todos los hilos consumidores estarán en estado waiting.
- Productor rápido/Consumidor lento: El productor tiene un límite de artículos en la cola, si llega a ese límite, entrará en estado
waiting hasta que algún consumidor, valga la redundancia, consuma un objeto, donde con un notifyAll() activará al productor para que 
vuelva a llenar el stock.

## Segunda parte: Búsqueda distribuida en BlacklistSearch (Lab01)
Para la implementación de esta búsqueda se cambia el tipo de contador a un AtomicInteger, este se compartirá con los hilos que hacen la búsqueda
y cuando lleguen al número límite de la alarma, todos los hilos pararán. El hecho de que sea Atomic hace que no haya condición de carrera.

Link al repositorio del Lab01: https://github.com/Juanfe8ag/ARSWLab012026

## Tercera parte: Sincronización y Deadlocks con Highlander Simulator
Lo primero fue calcular el invariante con N inmortales por H cantidad de vida. Al ejecutar el método de pausa, los resultados dan en un rango
de entre 20 y 90. Muy alejado del real. Esto se da porque con cada pelea la vida total se reduce en 5. Además, en muchos puntos se daba un invariante
negativo porque varios inmortales quedaban con -5 de vida. Por eso, sin implementaciones el invariante era vidaTotal - numeroPeleas * 5.
De esa forma el invariante se cumple en todas las pausas y reanudaciones.

Para remover a los inmortales muertos se cambia el ArrayList que los contenía por una colección ThreadSafe, en este caso, CopyOnWriteArrayList. Además, en
la región bloqueada luego del combate se realiza una verificación de vida. Si tiene 0 o menos, se eliminará de la lista.
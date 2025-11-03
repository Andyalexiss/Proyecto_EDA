import time
from typing import Callable, Any


class Timer:
    """Clase para la medición precisa de tiempo de ejecución."""

    def __init__(self):
        self._inicio: float = 0.0
        self._fin: float = 0.0
        self._tiempo_total: float = 0.0

    def start(self) -> None:
        """Inicia el contador de tiempo."""
        self._inicio = time.perf_counter()

    def stop(self) -> None:
        """Detiene el contador de tiempo y calcula la duración."""
        self._fin = time.perf_counter()
        self._tiempo_total = self._fin - self._inicio

    def get_duration(self) -> float:
        """Devuelve el tiempo transcurrido en segundos."""
        return self._tiempo_total

    def measure_function(self, func: Callable, *args: Any, **kwargs: Any) -> Any:
        """Mide la ejecución de una función y devuelve su resultado y el tiempo."""
        self.start()
        # Ejecuta la función con sus argumentos
        resultado = func(*args, **kwargs)
        self.stop()
        return self.get_duration(), resultado
import math
from typing import List

# CLASE: NormalizedUtility
class NormalizedUtility:
    # Ana
    # Atributos de la Clase
    
    def __init__(self, app_name: str, review_id: int, timestamp_created: int, 
                 recommended: bool, votes_helpful: int):
        
        self.app_name = app_name
        self.review_id = review_id
        self.timestamp_created = timestamp_created
        self.recommended = recommended
        self.votes_helpful = votes_helpful
        
        # Atributos extra que no se reciben por el constructor (se inicializan en 0.0)
        self.weighted_vote_score = 0.0
        self.utilidad_normalizada = self._calcular_utilidad_normalizada()

    def _calcular_utilidad_normalizada(self) -> float:
        # Calcula la utilidad normalizada.
        base = (1.0 if self.recommended else 0.0) \
               + (self.votes_helpful / 10.0) \
               + (self.weighted_vote_score * 2)
        return min(base / 3.0, 1.0)

    # Getters y __repr__ (simplificado para el código)
    def get_app_name(self) -> str: return self.app_name
    def get_review_id(self) -> int: return self.review_id
    def get_timestamp_created(self) -> int: return self.timestamp_created
    def get_votes_helpful(self) -> int: return self.votes_helpful
    def get_utilidad_normalizada(self) -> float: return self.utilidad_normalizada

    def __repr__(self) -> str:
        # Imprime los datos
        return f"App: {self.app_name}, ID: {self.review_id}, Time: {self.timestamp_created}"
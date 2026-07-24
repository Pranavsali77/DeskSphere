from pydantic import BaseModel

class TicketClassificationRequest(BaseModel):
    title: str
    description: str


class TicketClassificationResponse(BaseModel):
    predictedCategory: str
    confidence: float
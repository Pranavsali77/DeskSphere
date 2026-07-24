from fastapi import APIRouter

from app.nlu.ticket_classifier import predict_category
from app.api.schemas import (
    TicketClassificationRequest,
    TicketClassificationResponse,
)

router = APIRouter()


@router.post(
    "/predict-category",
    response_model=TicketClassificationResponse
)
def predict_ticket_category(
    request: TicketClassificationRequest
):

    result = predict_category(
        request.title,
        request.description
    )

    return TicketClassificationResponse(
        predictedCategory=result["predictedCategory"],
        confidence=result["confidence"]
    )
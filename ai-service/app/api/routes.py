from fastapi import APIRouter

from app.api.schemas import ChatRequest
from app.api.schemas import ChatResponse

from app.chatbot.chatbot import Chatbot

from app.nlu.intent import detect_intent

from app.troubleshooting.troubleshooter import troubleshoot

from app.ticket.ticket_creator import create_ticket

router = APIRouter()

bot = Chatbot()


@router.get("/health")
def health():

    return {

        "status": "UP",

        "service": "DeskSphere AI Service"

    }


@router.post("/chat")

def chat(request: ChatRequest):

    answer = bot.chat(request.message)

    return ChatResponse(

        response=answer

    )
    
@router.post("/intent")
def intent(request: ChatRequest):

    return detect_intent(request.message)

@router.post("/troubleshoot")
def ai_troubleshoot(request: ChatRequest):

    answer = troubleshoot(request.message)

    return {
        "solution": answer
    }

@router.post("/auto-ticket")
def auto_ticket(request: ChatRequest):

    result = create_ticket(request.message)

    return result
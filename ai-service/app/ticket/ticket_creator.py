import json

from app.chatbot.groq import ask_groq
from app.ticket.prompts import AUTO_TICKET_PROMPT


def create_ticket(message: str):

    prompt = f"""
{AUTO_TICKET_PROMPT}

User Issue:

{message}
"""

    response = ask_groq(prompt)

    print("========== AUTO TICKET ==========")
    print(response)
    print("================================")

    try:
        return json.loads(response)

    except Exception:

        return {
            "title": "General IT Issue",
            "description": message,
            "category": "Software",
            "priority": "Medium",
            "department": "IT Support",
            "ticketRequired": True
        }
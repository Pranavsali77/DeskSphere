import requests

from app.config.settings import settings
from app.chatbot.prompt import SYSTEM_PROMPT

URL = "https://api.groq.com/openai/v1/chat/completions"


def ask_groq(message: str):

    headers = {
        "Authorization": f"Bearer {settings.GROQ_API_KEY}",
        "Content-Type": "application/json"
    }

    payload = {
        "model": settings.MODEL,
        "messages": [
            {
                "role": "system",
                "content": SYSTEM_PROMPT
            },
            {
                "role": "user",
                "content": message
            }
        ]
    }

    response = requests.post(
        URL,
        headers=headers,
        json=payload,
        timeout=30
    )

    print("Status:", response.status_code)
    print("Response:", response.text)

    if response.status_code != 200:
        raise Exception(response.text)

    data = response.json()

    return data["choices"][0]["message"]["content"]
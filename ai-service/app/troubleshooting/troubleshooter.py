from app.chatbot.groq import ask_groq
from app.troubleshooting.prompts import SYSTEM_PROMPT


def troubleshoot(issue: str):

    prompt = f"""
{SYSTEM_PROMPT}

Issue:

{issue}
"""

    return ask_groq(prompt)
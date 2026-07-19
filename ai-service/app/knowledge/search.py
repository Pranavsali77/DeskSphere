from app.chatbot.groq import ask_groq
from app.knowledge.prompts import KNOWLEDGE_PROMPT


def search_knowledge(user_question: str,
                     knowledge_article: str):

    prompt = f"""
{KNOWLEDGE_PROMPT}

Knowledge Base

{knowledge_article}

User Question

{user_question}

Answer:
"""

    return ask_groq(prompt)
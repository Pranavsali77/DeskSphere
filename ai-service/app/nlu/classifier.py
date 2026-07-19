from app.chatbot.groq import ask_groq


def classify_issue(message: str):

    prompt = f"""
You are an AI IT Service Desk classifier.

Analyze the IT issue.

Return ONLY a valid JSON object.

Rules:
- Do NOT explain.
- Do NOT write any text before the JSON.
- Do NOT write any text after the JSON.
- Do NOT use ```json or ```.

Return exactly this format:

{{
    "category": "Hardware | Software | Network | Email | Security | Access | Other",
    "priority": "Low | Medium | High | Critical",
    "department": "IT Support | Network Team | Security Team | HR | Admin",
    "ticketRequired": true,
    "reason": "Short reason"
}}

User Issue:
{message}
"""

    return ask_groq(prompt)
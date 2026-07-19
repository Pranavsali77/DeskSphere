from app.chatbot.groq import ask_groq


class Chatbot:

      def chat(self, message: str):
        return ask_groq(message)
from fastapi import FastAPI

from app.api.routes import router

app = FastAPI(

    title="DeskSphere AI",

    version="1.0.0"

)

app.include_router(router)


@app.get("/")
def home():

    return {

        "message": "DeskSphere AI Service Running"

    }
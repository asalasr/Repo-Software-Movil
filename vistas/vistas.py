from flask import request
from ..modelos import db,User, UsuarioSchema
from flask_restful import Resource
from flask_jwt_extended import jwt_required, create_access_token, get_jwt_identity
import sys


usuario_schema = UsuarioSchema()


class VistaLogIn(Resource):

    def post(self):
        try:
            usuario = User.query.filter(User.email == request.json["email"], User.password == request.json["password"]).first()
            db.session.commit()
            if usuario is None:
                return "El usuario no existe", 404
            else:
                token_de_acceso = create_access_token(identity = usuario.id)
                return {"mensaje":"Inicio de sesión exitoso", "token": token_de_acceso}
        except Exception:
            e = sys.exc_info()[1]
            print(e.args[0])
            return {"mensaje":"Error", "error":e.args[0]}, 404


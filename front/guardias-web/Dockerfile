FROM node:alpine

WORKDIR /web

COPY . .

RUN npm i -g @angular/cli@17

RUN npm i

EXPOSE 4200

ENTRYPOINT ["ng", "serve", "--ssl", "--host", "0.0.0.0"]

FROM node:20-slim AS build

WORKDIR /app
COPY package*.json yarn.lock ./
RUN yarn install --frozen-lockfile
COPY . .

RUN yarn build

FROM nginx:1.25.5

WORKDIR /usr/share/nginx/html

RUN rm -rf ./*

COPY --from=build /app/dist .
COPY ./nginx/default.conf.template /etc/nginx/templates/

EXPOSE 80
FROM node:20-alpine AS build
WORKDIR /app

ARG VITE_KC_URL=http://localhost:8081
ARG VITE_KC_REALM=bis-demo
ARG VITE_KC_CLIENT=bis-frontend
ENV VITE_KC_URL=${VITE_KC_URL}
ENV VITE_KC_REALM=${VITE_KC_REALM}
ENV VITE_KC_CLIENT=${VITE_KC_CLIENT}

COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx","-g","daemon off;"]

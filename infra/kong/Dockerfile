
FROM kong:3.6
USER root
RUN apk add --no-cache git unzip luarocks lua5.1-dev gcc musl-dev openssl-dev
RUN luarocks install kong-oidc 1.4.4-1
ENV KONG_PLUGINS=bundled,oidc
USER kong

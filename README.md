# 📦 HubSpot Technical Test

Aplicação Java com Spring Boot, que integra com a API do HubSpot para autenticação OAuth 2.0, possibilita de criação de contatos e recepção de eventos via webhook.

---

## 🚀 Funcionalidades

- 🔐 Autenticação via OAuth 2.0 com HubSpot
- 📬 Criação de contatos no CRM HubSpot (API v3)
- 📥 Recebimento de eventos via webhook (ex: contato criado)
- 🧠 Rate Limiter inteligente: lida de forma segura com 10 requisições/segundo
- 🔁 Refresh automático do `access_token` via `refresh_token`
- 🌐 Swagger UI para teste dos endpoints

---

## ⚙️ Como executar localmente

### 1. Pré-requisitos

- Java 17
- Maven 3.8+
- Conta de desenvolvedor no HubSpot
- App OAuth registrado no HubSpot
- Webhook criado e ativo, que está inscrito no evento `contact.creation`


---

### 2. Clonar o projeto

```bash
git clone https://github.com/samuel-oliveira-dev/hubspot.git
```

---

### 3. Criar o arquivo `.env`

Crie um arquivo `.env` na raiz do projeto:

```env
ENV_HUBSPOT_CLIENT_ID=SEU_CLIENT_ID
ENV_HUBSPOT_CLIENT_SECRET=SEU_CLIENT_SECRET
```


---

### 4. Rodar a aplicação

```bash
mvn spring-boot:run
```
> O comando deve ser rodado na raiz do projeto. Após isso, estará disponível em:

```
http://localhost:8081
```

---

## 📘 Documentação da API

Acesse o Swagger em:

```
http://localhost:8081/swagger-ui.html
```

---

## 🔑 Fluxo OAuth

1. Acesse `GET /auth/url` para obter a URL de login
2. Faça login com sua conta HubSpot
3. O HubSpot redireciona para `/auth/callback` com o `code`
4. O backend troca o `code` pelo `access_token` e salva na sessão
5. As próximas requisições usarão o token armazenado

---

## 📬 Webhooks

Configure no HubSpot:

- Endpoint: `https://seu-endpoint-ngrok.ngrok.io/webhooks/hubspot`
- Evento: `contact.creation`

A aplicação vai receber e logar no console os eventos recebidos, se forem do tipo `contact.creation`.

---

## 📈 Testar o rate limit

Para testar o controle de requisições (10 por segundo):

```bash
seq 1 11 | xargs -I{} -P11 curl -X POST http://localhost:8081/contacts \
  -H "Content-Type: application/json" \
  -d '{"firstname":"Burst","lastname":"Test","email":"burst{}@teste.com"}'
```

A aplicação aplicará `Thread.sleep` automático se necessário.

---

## 🔄 Refresh do token

Quando o `access_token` expira, o backend usa automaticamente o `refresh_token` para obter um novo token e atualizar a sessão.



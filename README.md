# Lambda Trigger SQS - Processa Nota

Projeto Java para AWS Lambda que realiza trigger com AWS SQS, processa mensagens da fila de NFCe, e envio os dados via requisição HTTP POST para outro serviço.

---

## Arquitetura

```
SQS (registra-nfce) → Lambda (processa-nfce) → HTTP POST (serviço externo)
```

---

## Estrutura do Projeto

```
├── create-services-configs/
│   └── COMMANDS.md          # Comandos AWS CLI para LocalStack
├── events/
│   └── event.json           # Evento de teste para AWS SAM
├── policies/
│   └── lambda-role-trust-policy.json  # Políticas IAM para execução do Lambda
├── processa-nota-usuario/
│   ├── pom.xml              # Configurações Maven
│   └── src/
│       └── main/java/poupacompra/processanota/
│           └── App.java     # Handler principal do Lambda
├── template.yaml            # Template AWS SAM
└── samconfig.toml           # Configurações do SAM CLI
```

---

## Pré-requisitos

### Desenvolvimento Local

- **Java 21**
- **LocalStack** - Para simular os serviços AWS localmente
- **AWS CLI** - Configurado para apontar ao LocalStack
- **AWS SAM CLI** - Para testes e debug do Lambda com eventos

### Deploy na AWS

- Apenas o arquivo `.jar` da aplicação

---

## Desenvolvimento Local

### 1. Iniciar LocalStack

Certifique-se de que o LocalStack está rodando em sua máquina.

### 2. Criar os Serviços

Os comandos para criar a fila SQS, roles IAM e a função Lambda estão documentados em:

📄 [create-services-configs/COMMANDS.md](create-services-configs/COMMANDS.md)

### 3. Build do Projeto

```bash
cd processa-nota-usuario
mvn clean package
```

### 4. Testar com AWS SAM

```bash
sam local invoke --event events/event.json
```

---

## Deploy na AWS

Para deploy nos servidores da AWS, basta realizar o upload do arquivo `.jar` gerado:

```
processa-nota-usuario/target/processa-nota-usuario-1.0.jar
```

---

## Políticas IAM

O diretório `policies/` contém as políticas necessárias para a execução do Lambda:

- `lambda-role-trust-policy.json` - Política de confiança para a role do Lambda

Estas políticas são utilizadas via AWS CLI ao criar os recursos.

---

## Logs

Para verificar os logs da execução no LocalStack:

```bash
# Listar log streams
awslocal logs describe-log-streams --log-group-name /aws/lambda/processa-nfce

# Consultar eventos de log
awslocal logs get-log-events \
    --log-group-name /aws/lambda/processa-nfce \
    --log-stream-name "<LOG_STREAM_NAME>"
```

---

## Tecnologias

- Java 21
- AWS Lambda
- AWS SQS
- AWS SAM
- LocalStack

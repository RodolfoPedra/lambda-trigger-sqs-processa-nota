# Comandos AWS CLI para LocalStack

> Coleção de comandos utilizados para criar e gerenciar serviços no LocalStack.

---

## 1. SQS - Simple Queue Service

### Criar fila SQS

```bash
awslocal sqs create-queue --queue-name registra-nfce
```

### Obter ARN da fila

```bash
awslocal sqs get-queue-attributes \
    --queue-url http://sqs.eu-central-1.localhost.localstack.cloud:4566/000000000000/registra-nfce \
    --attribute-names QueueArn
```

### Receber mensagens da fila

```bash
awslocal sqs receive-message \
    --queue-url http://sqs.eu-central-1.localhost.localstack.cloud:4566/000000000000/registra-nfce \
    --attribute-names All \
    --message-attribute-names All
```

---

## 2. IAM - Identity and Access Management

### Criar role para Lambda

```bash
awslocal iam create-role \
    --role-name lambda-sqs-notas \
    --region eu-central-1 \
    --assume-role-policy-document file:///home/rodolfo/projetos/poupacompra/poupa-compra-scrapping/lambda-sqs-java/policies/lambda-role-trust-policy.json
```

---

## 3. Lambda

### Criar função Lambda

```bash
awslocal lambda create-function \
    --function-name processa-nfce \
    --runtime java21 \
    --zip-file fileb:///home/rodolfo/projetos/poupacompra/poupa-compra-scrapping/lambda-trigger-sqs-processa-nota/processa-nota-usuario/target/processa-nota-usuario-1.0.jar \
    --handler poupacompra.processanota.App::handleRequest \
    --role arn:aws:iam::000000000000:role/lambda-sqs-notas
```

### Criar trigger SQS para Lambda

```bash
awslocal lambda create-event-source-mapping \
    --event-source-arn arn:aws:sqs:eu-central-1:000000000000:registra-nfce \
    --function-name processa-nfce \
    --batch-size 10 \
    --region eu-central-1
```

---

## 4. CloudWatch Logs

### Listar log streams

```bash
awslocal logs describe-log-streams \
    --log-group-name /aws/lambda/processa-nfce
```

### Consultar eventos de log

> Substitua o `log-stream-name` pelo valor obtido no comando anterior.

```bash
awslocal logs get-log-events \
    --log-group-name /aws/lambda/processa-nfce \
    --log-stream-name "2026/02/26/[\$LATEST]c2818070020c262dd288e563782e6c5c"
```